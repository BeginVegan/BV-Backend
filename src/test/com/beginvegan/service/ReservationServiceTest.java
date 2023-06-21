package com.beginvegan.service;

import com.beginvegan.dto.*;
import com.beginvegan.repository.*;
import com.beginvegan.service.ReservationService;
import com.beginvegan.service.TossService;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private TossService tossService;

    @DisplayName("findAllReservation 테스트")
    @Test
    void findAllReservation_shouldCallRepositoryMethod() throws Exception {
        // given
        List<ReservationDTO> reservationDTOList =  new ArrayList<>();
        given(reservationRepository.selectAllReservation()).willReturn(reservationDTOList);

        // when
        reservationService.findAllReservation();

        // then
        verify(reservationRepository).selectAllReservation();
    }

    @DisplayName("findReservationByReservationNo 테스트")
    @Test
    void findReservationByReservationNo_shouldCallRepositoryMethod() throws Exception {
        // given
        int testReservationNo = 1;
        ReservationDTO reservationInfo =  new ReservationDTO();
        given(reservationRepository.selectReservationByReservationNo(testReservationNo)).willReturn(reservationInfo);

        // when
        reservationService.findReservationByReservationNo(testReservationNo);

        // then
        verify(reservationRepository).selectReservationByReservationNo(testReservationNo);
    }

    @DisplayName("findAllReservationByMemberEmail 테스트")
    @Test
    void findAllReservationByMemberEmail_shouldCallRepositoryMethod() throws Exception {
        // given
        String testMemberEmail = "test@naver.com";
        List<ReservationDTO> reservationDTOList =  new ArrayList<>();
        given(reservationRepository.selectALLReservationByMemberEmail(testMemberEmail)).willReturn(reservationDTOList);

        // when
        reservationService.findAllReservationByMemberEmail(testMemberEmail);

        // then
        verify(reservationRepository).selectALLReservationByMemberEmail(testMemberEmail);
    }

    @DisplayName("modifyReservation 테스트")
    @Test
    void modifyReservation_shouldCallRepositoryMethod() throws Exception {
        // given
        ReservationDTO reservationInfo =  new ReservationDTO();
        reservationInfo.setReservationNo(1);

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setMemberEmail("test@naver.com");
        Mockito.when(reservationRepository.selectReservationByReservationNo(1))
                .thenReturn(reservationDTO);

        PaymentDTO paymentDTO = new PaymentDTO();
        Mockito.when(paymentRepository.selectPaymentByReservationNo(1))
                .thenReturn(paymentDTO);


        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail("test@naver.com");
        memberInfo.setMemberName("TestMan");
        memberInfo.setMemberPoint(10000);
        String testMemberEmail = "test@naver.com";

        Mockito.when(memberRepository.selectMemberByMemberEmail(testMemberEmail))
                .thenReturn(memberInfo);

        // when
        reservationService.modifyReservation(reservationInfo);

        // then
        verify(reservationRepository).insertReservation(reservationInfo);
    }

    @DisplayName("deleteReservation 테스트")
    @Test
    void deleteReservation_shouldCallRepositoryMethod() throws Exception {
        // given
        int testReservationNo = 1;
        given(reservationRepository.deleteReservation(testReservationNo)).willReturn(testReservationNo);

        // when
        reservationService.deleteReservation(testReservationNo);

        // then
        verify(reservationRepository).deleteReservation(testReservationNo);
    }

    @DisplayName("addReservation 테스트")
    @Test
    void addReservation_shouldCallRepositoryMethod() throws Exception {
        // given
        ReservationDTO reservationInfo =  new ReservationDTO();
        given(reservationRepository.insertReservation(reservationInfo)).willReturn(reservationInfo);

        // when
        reservationService.addReservation(reservationInfo, Optional.empty());

        // then
        verify(reservationRepository).insertReservation(reservationInfo);
    }

    @DisplayName("addReservationWithImpUid 테스트")
    @Test
    void addReservationWithImpUid_shouldCallRepositoryMethod() throws Exception {
        // given
        ReservationDTO reservationInfo =  new ReservationDTO();
        reservationInfo.setMemberEmail("test@naver.com");
        given(reservationRepository.insertReservation(reservationInfo)).willReturn(reservationInfo);

        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail("test@naver.com");
        memberInfo.setMemberName("TestMan");
        memberInfo.setMemberPoint(10000);
        String testMemberEmail = "test@naver.com";

        Mockito.when(memberRepository.selectMemberByMemberEmail(testMemberEmail))
                .thenReturn(memberInfo);
        // when
        reservationService.addReservation(reservationInfo, Optional.of("imp1234"));

        // then
        verify(reservationRepository).insertReservation(reservationInfo);
    }

    @DisplayName("cancelReservation 테스트")
    @Test
    void cancelReservation_shouldCallRepositoryMethod() throws Exception {
        // given
        int testReservationNo = 1;
        given(reservationRepository.cancelReservation(testReservationNo))
                .willReturn(testReservationNo);

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setMemberEmail("test@naver.com");
        Mockito.when(reservationRepository.selectReservationByReservationNo(1))
                .thenReturn(reservationDTO);

        PaymentDTO paymentDTO = new PaymentDTO();
        Mockito.when(paymentRepository.selectPaymentByReservationNo(1))
                .thenReturn(paymentDTO);

        Mockito.when(paymentRepository.cancelPayment(testReservationNo))
                .thenReturn(testReservationNo);

        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail("test@naver.com");
        memberInfo.setMemberName("TestMan");
        memberInfo.setMemberPoint(10000);
        String testMemberEmail = "test@naver.com";

        Mockito.when(memberRepository.selectMemberByMemberEmail(testMemberEmail))
                .thenReturn(memberInfo);

        // when
        reservationService.cancelReservation(testReservationNo);

        // then
        verify(reservationRepository).cancelReservation(testReservationNo);
    }
}