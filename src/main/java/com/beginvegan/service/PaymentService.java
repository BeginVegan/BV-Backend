package com.beginvegan.service;

import com.beginvegan.dto.PaymentDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.repository.PaymentRepository;
import com.beginvegan.repository.ReservationRepository;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service("paymentService")
public class PaymentService {

    private final PaymentRepository paymentRepository;
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
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

    public String deletePayment(String impUid) throws RemoveException {
        return paymentRepository.deletePayment(impUid);
    }
}
