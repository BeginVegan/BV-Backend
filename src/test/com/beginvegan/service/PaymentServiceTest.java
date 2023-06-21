package com.beginvegan.service;

import com.beginvegan.dto.MemberDTO;
import com.beginvegan.dto.PaymentDTO;
import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.repository.MemberRepository;
import com.beginvegan.repository.PaymentRepository;
import com.beginvegan.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@Slf4j
@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @DisplayName("findPaymentByImpUid 테스트")
    @Test
    void findPaymentByImpUid_shouldCallRepositoryMethod() throws Exception {
        // given
        PaymentDTO paymentDTO =  new PaymentDTO();
        given(paymentRepository.selectPaymentByImpUid(paymentDTO.getImpUid())).willReturn(paymentDTO);

        // when
        paymentService.findPaymentByImpUid(paymentDTO.getImpUid());

        // then
        verify(paymentRepository).selectPaymentByImpUid(paymentDTO.getImpUid());
    }

    @DisplayName("findPaymentByReservationNo 테스트")
    @Test
    void findPaymentByReservationNo_shouldCallRepositoryMethod() throws Exception {
        // given
        PaymentDTO paymentDTO =  new PaymentDTO();
        given(paymentRepository.selectPaymentByReservationNo(paymentDTO.getReservationNo())).willReturn(paymentDTO);

        // when
        paymentService.findPaymentByReservationNo(paymentDTO.getReservationNo());

        // then
        verify(paymentRepository).selectPaymentByReservationNo(paymentDTO.getReservationNo());
    }

    @DisplayName("findAllPayment 테스트")
    @Test
    void findAllPayment_shouldCallRepositoryMethod() throws Exception {
        // given
        List<PaymentDTO> paymentDTOList =  new ArrayList<>();
        given(paymentRepository.selectAllPayment()).willReturn(paymentDTOList);

        // when
        paymentService.findAllPayment();

        // then
        verify(paymentRepository).selectAllPayment();
    }

    @DisplayName("findAllPaymentByMemberEmail 테스트")
    @Test
    void findAllPaymentByMemberEmail_shouldCallRepositoryMethod() throws Exception {
        // given
        MemberDTO memberDTO = new MemberDTO();
        List<PaymentDTO> paymentDTOList =  new ArrayList<>();
        given(paymentRepository.selectALLPaymentByMemberEmail(memberDTO.getMemberEmail())).willReturn(paymentDTOList);

        // when
        paymentService.findAllPaymentByMemberEmail(memberDTO.getMemberEmail());

        // then
        verify(paymentRepository).selectALLPaymentByMemberEmail(memberDTO.getMemberEmail());
    }

    @DisplayName("addPayment 테스트")
    @Test
    void addPayment_shouldCallRepositoryMethod() throws Exception {
        // given
        PaymentDTO paymentDTO =  new PaymentDTO();
        given(paymentRepository.insertPayment(paymentDTO)).willReturn(paymentDTO);

        // when
        paymentService.addPayment(paymentDTO);

        // then
        verify(paymentRepository).insertPayment(paymentDTO);
    }

    @DisplayName("modifyPayment 테스트")
    @Test
    void modifyPayment_shouldCallRepositoryMethod() throws Exception {
        // given
        PaymentDTO paymentDTO =  new PaymentDTO();
        given(paymentRepository.updatePayment(paymentDTO)).willReturn(paymentDTO);

        // when
        paymentService.modifyPayment(paymentDTO);

        // then
        verify(paymentRepository).updatePayment(paymentDTO);
    }

    @DisplayName("deletePayment 테스트")
    @Test
    void deletePayment_shouldCallRepositoryMethod() throws Exception {
        // given
        PaymentDTO paymentDTO =  new PaymentDTO();
        given(paymentRepository.deletePayment(paymentDTO.getImpUid())).willReturn(paymentDTO.getImpUid());

        // when
        paymentService.deletePayment(paymentDTO.getImpUid());

        // then
        verify(paymentRepository).deletePayment(paymentDTO.getImpUid());
    }
}