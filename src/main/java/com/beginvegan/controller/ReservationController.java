package com.beginvegan.controller;

import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.service.ReservationService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("reservation")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("{reservationNo}")
    public ResponseEntity<?> reservationOne(@PathVariable Integer reservationNo) throws FindException {
        ReservationDTO reservation = reservationService.findReservationByReservationNo(reservationNo);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @GetMapping("list")
    public ResponseEntity<?> reservationList() throws FindException {
        List<ReservationDTO> reservations = reservationService.findAllReservation();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("list/memberEmail")
    public ResponseEntity<?> reservationList(HttpSession session) throws FindException {
        List<ReservationDTO> reservations = reservationService.findAllReservationByMemberEmail((String) session.getAttribute("memberEmail"));
        log.info(reservations.toString());
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> reservationAdd(@RequestBody ReservationDTO reservationDTO) throws AddException {
        return new ResponseEntity<>(reservationService.addReservation(reservationDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> reservationModify(@RequestBody ReservationDTO reservationDTO) throws ModifyException, IamportResponseException, FindException, IOException, AddException {
        return new ResponseEntity<>(reservationService.modifyReservation(reservationDTO), HttpStatus.OK);
    }

    @PutMapping("cancel")
    public ResponseEntity<?> reservationCancel(@RequestParam int reservationNo) throws ModifyException, IamportResponseException, IOException, FindException {
        return new ResponseEntity<>(reservationService.cancelReservation(reservationNo), HttpStatus.OK);
    }

    @DeleteMapping("{reservationNo}")
    public ResponseEntity<?> reservationRemove(@PathVariable int reservationNo) throws RemoveException {
        return new ResponseEntity<>(reservationService.deleteReservation(reservationNo), HttpStatus.OK);
    }
}
