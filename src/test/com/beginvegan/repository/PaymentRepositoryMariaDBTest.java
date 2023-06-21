package com.beginvegan.repository;

import com.beginvegan.dto.MemberDTO;
import com.beginvegan.dto.PaymentDTO;
import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.dto.ReservationMenuDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.util.TimeUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentRepositoryMariaDBTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MemberRepository memberRepository;

    private static MemberDTO memberInfo;

    private static ReservationDTO reservationInfo;

    private static PaymentDTO paymentInfo;

    @BeforeAll
    public static void beforeAll() throws ParseException {
        memberInfo = new MemberDTO();
            memberInfo.setMemberName("테스트");
            memberInfo.setMemberEmail("test@naver.com");
            memberInfo.setMemberPoint(100000);
            memberInfo.setMemberRole("normal");

        paymentInfo = new PaymentDTO().builder()
                .impUid("imp_1234")
                .memberEmail(memberInfo.getMemberEmail())
                .paymentTime(TimeUtil.toDateTime(new Date()))
                .paymentPrice(9000)
                .paymentStatus("결제")
                .build();

        reservationInfo = new ReservationDTO().builder()
                .memberEmail(memberInfo.getMemberEmail())
                .restaurantNo(1)
                .reservationTime(TimeUtil.toDateTime(new Date()))
                .reservationVisitTime(TimeUtil.toDateTime(new Date()))
                .reservationType("매장")
                .reservationPeople(2)
                .reservationDiscount(1000)
                .reservationTotalPrice(10000)
                .reservationStatus("예약")
                .build();
    }

    @BeforeEach
    public void beforeEach() throws AddException {
        int NextReservationNo = reservationRepository.selectNextReservationNo();

        memberRepository.insertMember(memberInfo);

        List<ReservationMenuDTO> reservationMenuList = new ArrayList<>();
        ReservationMenuDTO reservationMenuDTO = new ReservationMenuDTO(1, 1, "메뉴1", 10000, "카테고리1", "메뉴1 설명", "https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg", NextReservationNo, 1);
        reservationMenuList.add(reservationMenuDTO);

        reservationInfo.setReservationNo(NextReservationNo);
        reservationInfo.setReservationMenuList(reservationMenuList);
        reservationRepository.insertReservation(reservationInfo);

        paymentInfo.setReservationNo(NextReservationNo);
    }

    @Test
    @DisplayName("impUid에 해당하는 결제정보를 가져온다.")
    @Transactional
    void selectPaymentByImpUid_shouldSuccessfully() throws AddException, FindException {
        // given

        // when
        paymentRepository.insertPayment(paymentInfo);

        // then
        PaymentDTO result = paymentRepository.selectPaymentByImpUid(paymentInfo.getImpUid());
        assertThat(result).isEqualTo(paymentInfo);
    }

    @Test
    @DisplayName("예약 번호에 해당하는 결제정보를 가져온다.")
    @Transactional
    void selectPaymentByReservationNo_shouldSuccessfully() throws AddException, FindException {
        // given

        // when
        paymentRepository.insertPayment(paymentInfo);

        // then
        PaymentDTO result = paymentRepository.selectPaymentByReservationNo(paymentInfo.getReservationNo());
        assertThat(result).isEqualTo(paymentInfo);
    }

    @Test
    @DisplayName("모든 결제정보를 가져온다.")
    @Transactional
    void selectAllPayment_shouldSuccessfully() throws AddException, FindException {
        // given
        int originalSize = paymentRepository.selectAllPayment().size();

        // when
        paymentRepository.insertPayment(paymentInfo);

        // then
        List<PaymentDTO> result = paymentRepository.selectAllPayment();
        assertThat(result.size()).isEqualTo(originalSize + 1);
    }

    @Test
    @DisplayName("유저의 모든 결제정보를 가져온다.")
    @Transactional
    void selectALLPaymentByMemberEmail_shouldSuccessfully() throws AddException, FindException {
        // given
        assertThrows(FindException.class, () -> {
            paymentRepository.selectALLPaymentByMemberEmail(paymentInfo.getMemberEmail());
        });

        // when
        paymentRepository.insertPayment(paymentInfo);

        // then
        List<PaymentDTO> result = paymentRepository.selectALLPaymentByMemberEmail(paymentInfo.getMemberEmail());
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("결제정보를 추가한다.")
    @Transactional
    void insertPayment_shouldSuccessfully() throws AddException, FindException {
        // given

        // when
        paymentRepository.insertPayment(paymentInfo);

        // then
        PaymentDTO result = paymentRepository.selectPaymentByReservationNo(paymentInfo.getReservationNo());
        assertThat(result).isEqualTo(paymentInfo);
    }

    @Test
    @DisplayName("결제정보를 수정한다.")
    @Transactional
    void updatePayment_shouldSuccessfully() throws FindException, AddException, ModifyException {
        // given
        paymentRepository.insertPayment(paymentInfo);
        PaymentDTO updatePaymentInfo = paymentInfo;
        updatePaymentInfo.setPaymentPrice(20000);

        // when
        paymentRepository.updatePayment(updatePaymentInfo);

        // then
        PaymentDTO result = paymentRepository.selectPaymentByImpUid(paymentInfo.getImpUid());
        assertThat(result.getPaymentPrice()).isEqualTo(updatePaymentInfo.getPaymentPrice());
    }

    @Test
    @DisplayName("결제를 취소한다.")
    @Transactional
    void cancelPayment_shouldSuccessfully() throws FindException, ModifyException, AddException {
        // given
        paymentRepository.insertPayment(paymentInfo);

        // when
        paymentRepository.cancelPayment(paymentInfo.getReservationNo());

        // then
        PaymentDTO result = paymentRepository.selectPaymentByReservationNo(paymentInfo.getReservationNo());
        assertThat(result.getPaymentStatus()).isEqualTo("취소");
    }

    @Test
    @DisplayName("결제를 삭제한다.")
    @Transactional
    void deletePayment_shouldSuccessfully() throws FindException, RemoveException {
        // given

        // when
        paymentRepository.deletePayment(paymentInfo.getImpUid());

        // then
        assertThrows(FindException.class, () -> {
            paymentRepository.selectPaymentByImpUid(paymentInfo.getImpUid());
        });
    }

}