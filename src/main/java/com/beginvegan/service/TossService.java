package com.beginvegan.service;

import com.beginvegan.dto.PaymentDTO;
import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.dto.ReservationMenuDTO;
import com.beginvegan.repository.PaymentRepository;
import com.beginvegan.repository.ReservationRepository;
import com.beginvegan.util.TimeUtil;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("TossService")
public class TossService {

    /** Iamport 결제 검증 컨트롤러 **/
    private IamportClient iamportClient;
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    @Value("${imp.key}")
    private String impKey;

    @Value("${imp.secret}")
    private String impSecret;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(impKey, impSecret);
    }

    public IamportResponse<Payment> paymentLookup(String impUid) throws IamportResponseException, IOException {
        return iamportClient.paymentByImpUid(impUid);
    }

    public IamportResponse<Payment> cancelImpPayment(String impUid) throws IamportResponseException, IOException{
        IamportResponse<Payment> lookUp = paymentLookup(impUid);
        CancelData data = cancelData(lookUp); //취소데이터 셋업
        return iamportClient.cancelPaymentByImpUid(data); // iamport 취소
    }
    public TossService(PaymentRepository paymentRepository, ReservationRepository reservationRepository) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
    }

    /**
     * 아임포트 서버쪽 결제내역과 DB에 물건가격을 비교하는 서비스.
     * 다름 -> 예외 발생시키고 예외처리
     * 같음 -> 결제정보를 DB에 저장 (Payment 테이블)
     * @param lookUp (아임포트쪽 결제 내역 조회 정보)
     * @param reservationNo (DB에서 예약메뉴 가격을 알기위한 예약 번호)
     * @throws Exception
     */
    @Transactional
    public PaymentDTO verifyIamportService(IamportResponse<Payment> lookUp, String memberEmail, int amount, int reservationNo) throws Exception {
        ReservationDTO reservationDTO = reservationRepository.selectReservationByReservationNo(reservationNo);
        List<ReservationMenuDTO> menus = reservationDTO.getReservationMenuList();
        int totalMenuPrice = -reservationDTO.getReservationDiscount();

        for(ReservationMenuDTO menu : menus) {
            totalMenuPrice += menu.getMenuPrice() * menu.getReservationMenuCount();
        }

        // 실제로 결제된 금액과 아임포트 서버쪽 결제내역 금액과 같은지 확인
        // BigDecimal - 주로 금융쪽에서 정확한 값표현을 위해씀.
        // int형으로 비교해주기 위해 형변환 필요.
        if(lookUp.getResponse().getAmount().intValue() != amount)
            throw new Exception("실제 결제된 금액과 아임포트 결제 금액이 다름");

        // DB에서 메뉴가격과 실제 결제금액이 일치하는지 확인하기. 만약 다르면 예외 발생시키기.
        if(amount != totalMenuPrice)
            throw new Exception("물건 가격과 결제 금액이 다름");

        PaymentDTO paymentInfo = PaymentDTO.builder()
                .impUid(lookUp.getResponse().getImpUid())
                .reservationNo(reservationNo)
                .memberEmail(memberEmail)
                .paymentTime(TimeUtil.toDateTime(new Date()))
                .paymentPrice(lookUp.getResponse().getAmount().intValue())
                .paymentStatus("결제")
                .build();

        return paymentRepository.insertPayment(paymentInfo);
    }

    /**
     * 결제 취소할때 필요한 파라미터들을
     * CancelData에 셋업해주고 반환함.
     * @param lookUp
     * @return code
     */
    @Transactional
    public CancelData cancelData(IamportResponse<Payment> lookUp) {
        CancelData data = new CancelData(lookUp.getResponse().getImpUid(),true);
        data.setReason(lookUp.getResponse().getImpUid() + "환불");
        data.setChecksum(lookUp.getResponse().getAmount());
        data.setRefund_bank(lookUp.getResponse().getCardCode());
        data.setRefund_account(lookUp.getResponse().getCardName());
        return data;
    }

}
