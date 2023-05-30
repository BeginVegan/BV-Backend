package com.beginvegan.service;

import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("reservationService")
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
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

    public ReservationDTO modifyReservation(ReservationDTO reservationDTO) throws ModifyException {
        reservationRepository.updateReservation(reservationDTO);
        return reservationDTO;
    }

    public Integer deleteReservation(Integer ReservationNo) throws RemoveException {
        reservationRepository.deleteReservation(ReservationNo);
        return ReservationNo;
    }
}
