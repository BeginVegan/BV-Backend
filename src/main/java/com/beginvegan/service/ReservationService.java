package com.beginvegan.service;

import com.beginvegan.controller.PaymentController;
import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.repository.PaymentRepository;
import com.beginvegan.repository.ReservationRepository;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service("reservationService")
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final TossService tossService;

    public ReservationService(ReservationRepository reservationRepository, PaymentRepository paymentRepository, TossService tossService) {
        this.reservationRepository = reservationRepository;
        this.paymentRepository = paymentRepository;
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

    public ReservationDTO addReservation(ReservationDTO reservationDTO) throws AddException {
        reservationRepository.insertReservation(reservationDTO);
        return reservationDTO;
    }

    public ReservationDTO modifyReservation(ReservationDTO reservationDTO) throws ModifyException, IamportResponseException, IOException, FindException, AddException {
        String impUid = paymentRepository.selectPaymentByReservationNo(reservationDTO.getReservationNo()).getImpUid();

        ReservationDTO reservationInfo = reservationRepository.insertReservation(reservationDTO);
        reservationRepository.cancelReservation(reservationDTO.getReservationNo());
        tossService.cancelImpPayment(impUid);
        reservationRepository.updateReservation(reservationInfo);

        return reservationInfo;
    }

    @Transactional
    public Integer cancelReservation(int reservationNo) throws ModifyException, FindException, IamportResponseException, IOException {
        String impUid = paymentRepository.selectPaymentByReservationNo(reservationNo).getImpUid();

        tossService.cancelImpPayment(impUid);
        reservationRepository.cancelReservation(reservationNo);
        paymentRepository.cancelPayment(reservationNo);

        return reservationNo;
    }

    public Integer deleteReservation(int ReservationNo) throws RemoveException {
        reservationRepository.deleteReservation(ReservationNo);
        return ReservationNo;
    }
}
