package com.beginvegan.controller;

import com.beginvegan.dto.PaymentDTO;
import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.service.PaymentService;
import com.beginvegan.service.TossService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TossService tossService;

    @GetMapping("impUid")
    public ResponseEntity<?> paymentOne(@RequestParam String impUid) throws FindException {
        PaymentDTO payment = paymentService.findPaymentByImpUid(impUid);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @GetMapping("reservationNo")
    public ResponseEntity<?> paymentOne(@RequestParam int reservationNo) throws FindException {
        PaymentDTO payment = paymentService.findPaymentByReservationNo(reservationNo);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @GetMapping("list")
    public ResponseEntity<?> paymentList() throws FindException {
        List<PaymentDTO> payments = paymentService.findAllPayment();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("list/memberEmail")
    public ResponseEntity<?> paymentList(HttpSession session) throws FindException {
        List<PaymentDTO> payments = paymentService.findAllPaymentByMemberEmail((String) session.getAttribute("memberEmail"));
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> paymentAdd(@RequestBody PaymentDTO paymentDTO, HttpSession session) throws Exception {
        String impUid = paymentDTO.getImpUid(); // 실제 결제금액 조회위한 아임포트 서버쪽에서 id
        //String memberEmail = (String) session.getAttribute("memberEmail");
        String memberEmail = paymentDTO.getMemberEmail();
        int amount = paymentDTO.getPaymentPrice(); // 실제로 유저가 결제한 금액

        //아임포트 서버쪽에 결제된 정보 조회.
        IamportResponse<Payment> lookUp = tossService.paymentLookup(impUid);
        PaymentDTO paymentInfo = tossService.verifyIamportService(lookUp, memberEmail, amount, paymentDTO.getReservationNo());

        return new ResponseEntity<>(paymentInfo, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> paymentModify(@RequestBody PaymentDTO paymentDTO) throws ModifyException {
        PaymentDTO payment = paymentService.modifyPayment(paymentDTO);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<?> paymentRemove(@RequestParam String impUid) throws RemoveException, IamportResponseException, IOException, FindException, ModifyException, ParseException, AddException {
        IamportResponse<Payment> cancel = tossService.cancelImpPayment(impUid);
        paymentService.deletePayment(impUid); // db 삭제

        return new ResponseEntity<>(cancel.toString(), HttpStatus.OK);
    }
}
