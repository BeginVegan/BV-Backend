package com.beginvegan.controller;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.service.ReservationService;
import com.beginvegan.util.TimeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("reservation/*")
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

    @GetMapping("list/{memberEmail}")
    public ResponseEntity<?> reservationList(@PathVariable String memberEmail) throws FindException {
        List<ReservationDTO> reservations = reservationService.findAllReservationByMemberEmail(memberEmail);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<?> reservationAdd(@RequestBody ReservationDTO reservationDTO) throws AddException {
        return new ResponseEntity<ReservationDTO>(reservationService.addReservation(reservationDTO), HttpStatus.CREATED);
    }

    @PostMapping("modify")
    public ResponseEntity<?> reservationModify(@RequestBody ReservationDTO reservationDTO) throws ModifyException, JsonProcessingException {
        return new ResponseEntity<ReservationDTO>(reservationService.modifyReservation(reservationDTO), HttpStatus.OK);
    }

    @PostMapping("delete")
    public ResponseEntity<?> reservationRemove(@RequestBody Map<String, String> payload) throws RemoveException {
        return new ResponseEntity<Integer>(reservationService.deleteReservation(Integer.parseInt(payload.get("reservation_no"))), HttpStatus.OK);
    }
}
