package com.beginvegan.service;

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
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service("paymentService")
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;

    public PaymentService(PaymentRepository paymentRepository, ReservationRepository reservationRepository, MemberRepository memberRepository) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
    }

    public PaymentDTO findPaymentByImpUid(String impUid) throws FindException {
        return paymentRepository.selectPaymentByImpUid(impUid);
    }

    public PaymentDTO findPaymentByReservationNo(int reservationNo) throws FindException {
        return paymentRepository.selectPaymentByReservationNo(reservationNo);
    }

    public List<PaymentDTO> findAllPayment() throws FindException {
        return paymentRepository.selectAllPayment();
    }

    public List<PaymentDTO> findAllPaymentByMemberEmail(String memberEmail) throws FindException {
        return paymentRepository.selectALLPaymentByMemberEmail(memberEmail);
    }

    public PaymentDTO addPayment(PaymentDTO paymentDTO) throws AddException {
        return paymentRepository.insertPayment(paymentDTO);
    }

    public PaymentDTO modifyPayment(PaymentDTO paymentDTO) throws ModifyException {
        return paymentRepository.updatePayment(paymentDTO);
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
    public String deletePayment(String impUid) throws RemoveException, FindException, ModifyException, ParseException, AddException {
        int reservationNo = paymentRepository.selectPaymentByImpUid(impUid).getReservationNo();
        ReservationDTO reservationInfo = reservationRepository.selectReservationByReservationNo(reservationNo);
        changePoint(reservationInfo, "Purchase Cancel", true);
        return paymentRepository.deletePayment(impUid);
    }
}
