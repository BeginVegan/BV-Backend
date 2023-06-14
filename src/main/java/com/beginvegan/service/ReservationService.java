package com.beginvegan.service;

import com.beginvegan.controller.PaymentController;
import com.beginvegan.dto.MemberDTO;
import com.beginvegan.dto.PaymentDTO;
import com.beginvegan.dto.PointDTO;
import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.repository.MemberRepository;
import com.beginvegan.repository.PaymentRepository;
import com.beginvegan.repository.ReservationRepository;
import com.beginvegan.util.TimeUtil;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("reservationService")
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final TossService tossService;

    public ReservationService(ReservationRepository reservationRepository, PaymentRepository paymentRepository, MemberRepository memberRepository, TossService tossService) {
        this.reservationRepository = reservationRepository;
        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
        this.tossService = tossService;
    }

    public ReservationDTO findReservationByReservationNo(Integer reservationNo) throws FindException {
        return reservationRepository.selectReservationByReservationNo(reservationNo);
    }

    public List<ReservationDTO> findAllReservation() throws FindException {
        return reservationRepository.selectAllReservation();
    }

    public List<ReservationDTO> findAllReservationByMemberEmail(String memberEmail) throws FindException {
        return reservationRepository.selectALLReservationByMemberEmail(memberEmail);
    }

    private void changePoint(ReservationDTO reservationDTO, String pointDiv, boolean isPlus) throws FindException, ModifyException, ParseException, AddException {
        MemberDTO memberDTO = memberRepository.selectMemberByMemberEmail(reservationDTO.getMemberEmail());
        int pointResult = memberDTO.getMemberPoint() + (isPlus ? +reservationDTO.getReservationDiscount() : -reservationDTO.getReservationDiscount());

        memberDTO.setMemberPoint(pointResult);
        memberRepository.updateMemberPoint(memberDTO);

        PointDTO pointDTO = PointDTO.builder()
                .memberEmail(memberDTO.getMemberEmail())
                .pointDiv(pointDiv)
                .pointTime(TimeUtil.toDateTime(new Date()))
                .pointChange(reservationDTO.getReservationDiscount())
                .pointResult(pointResult)
                .build();

        memberRepository.insertPoint(pointDTO);
    }

    @Transactional
    public ReservationDTO addReservation(ReservationDTO reservationDTO, Optional<String> impUid) throws Exception {
        ReservationDTO reservationInfo = reservationRepository.insertReservation(reservationDTO);

        if(impUid.isPresent()) {
            PaymentDTO paymentDTO = PaymentDTO.builder()
                    .impUid(impUid.get())
                    .memberEmail(reservationInfo.getMemberEmail())
                    .reservationNo(reservationInfo.getReservationNo())
                    .paymentPrice(reservationInfo.getReservationTotalPrice() - reservationInfo.getReservationDiscount())
                    .paymentStatus("결제")
                    .build();

            //String memberEmail = (String) session.getAttribute("memberEmail");
            String memberEmail = paymentDTO.getMemberEmail();
            int amount = paymentDTO.getPaymentPrice(); // 실제로 유저가 결제한 금액

            //아임포트 서버쪽에 결제된 정보 조회.
            IamportResponse<Payment> lookUp = tossService.paymentLookup(impUid.get());
            tossService.verifyIamportService(lookUp, memberEmail, amount, paymentDTO.getReservationNo());

            //포인트 수정
            changePoint(reservationInfo, "Purchase", false);
        }

        return reservationInfo;
    }

    public ReservationDTO modifyReservation(ReservationDTO reservationDTO) throws Exception {
        cancelReservation(reservationDTO.getReservationNo());
        ReservationDTO reservationInfo = addReservation(reservationDTO, null);

        return reservationInfo;
    }

    @Transactional
    public Integer cancelReservation(int reservationNo) throws ModifyException, FindException, IamportResponseException, IOException, ParseException, AddException {
        String impUid = paymentRepository.selectPaymentByReservationNo(reservationNo).getImpUid();
        ReservationDTO reservationDTO = reservationRepository.selectReservationByReservationNo(reservationNo);

        tossService.cancelImpPayment(impUid);
        reservationRepository.cancelReservation(reservationNo);
        paymentRepository.cancelPayment(reservationNo);

        changePoint(reservationDTO, "Purchase Cancel", true);

        return reservationNo;
    }

    public Integer deleteReservation(int ReservationNo) throws RemoveException {
        reservationRepository.deleteReservation(ReservationNo);
        return ReservationNo;
    }
}
