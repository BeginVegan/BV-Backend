package com.beginvegan.repository;

import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.dto.ReservationMenuDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.util.TimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
class ReservationRepositoryMariaDBTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @Transactional
    void insertReservation_shouldAddReservationSuccessfully() throws AddException {

        //given
        int NextReservationNo = reservationRepository.selectNextReservationNo();

        List<ReservationMenuDTO> reservationMenuList = new ArrayList<>();
        reservationMenuList.add(new ReservationMenuDTO(NextReservationNo, 1, 1));
        reservationMenuList.add(new ReservationMenuDTO(NextReservationNo, 2, 1));

        ReservationDTO reservation = new ReservationDTO(
                NextReservationNo,
                "asdf@naver.com",
                1,
                TimeUtil.toDateTime("2023-05-23 00:00:00"),
                TimeUtil.toDateTime("2023-05-23 00:00:00"),
                "매장",
                2,
                1000,
                5000,
                "예약",
                reservationMenuList
        );

        //when
        ReservationDTO reservation2 = reservationRepository.insertReservation(reservation);

        //then
        assertThat(reservation).isEqualTo(reservation2);
    }

    @Test
    @Transactional
    void selectReservation_shouldSelectReservationSuccessfully() throws FindException, AddException {

        //given
        int NextReservationNo = reservationRepository.selectNextReservationNo();

        List<ReservationMenuDTO> reservationMenuList = new ArrayList<>();
        ReservationMenuDTO reservationMenuDTO = new ReservationMenuDTO(1, 1, "메뉴1", 10000, "카테고리1", "메뉴1 설명", "https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg", NextReservationNo, 1);

        reservationMenuList.add(reservationMenuDTO);

        ReservationDTO reservation = new ReservationDTO(
                NextReservationNo,
                "asdf@naver.com",
                1,
                TimeUtil.toDateTime("2023-05-23 00:00:00"),
                TimeUtil.toDateTime("2023-05-23 00:00:00"),
                "매장",
                2,
                1000,
                5000,
                "예약",
                reservationMenuList
        );

        //when
        reservationRepository.insertReservation(reservation);

        //then
        ReservationDTO result = reservationRepository.selectReservationByReservationNo(NextReservationNo);
        assertThat(result).isEqualTo(reservation);
    }

    @Test
    @Transactional
    void selectReservation_shouldSelectReservationCatchException() throws FindException, AddException {

        //given
        int NextReservationNo = reservationRepository.selectNextReservationNo();

        //when


        //then
        assertThrows(FindException.class, () -> {
            reservationRepository.selectReservationByReservationNo(NextReservationNo);
        });
    }

    @Test
    @Transactional
    void selectAllReservation_shouldSelectAllReservationSuccessfully() throws FindException, AddException {
        //given
        int originalReservationSize = reservationRepository.selectAllReservation().size();
        int NextReservationNo = reservationRepository.selectNextReservationNo();

        List<ReservationMenuDTO> reservationMenuList = new ArrayList<>();
        ReservationMenuDTO reservationMenuDTO = new ReservationMenuDTO(1, 1, "메뉴1", 10000, "카테고리1", "메뉴1 설명", "https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg", NextReservationNo, 1);

        reservationMenuList.add(reservationMenuDTO);

        ReservationDTO reservation = new ReservationDTO(
                NextReservationNo,
                "asdf@naver.com",
                1,
                TimeUtil.toDateTime("2023-05-23 00:00:00"),
                TimeUtil.toDateTime("2023-05-23 00:00:00"),
                "매장",
                2,
                1000,
                5000,
                "예약",
                reservationMenuList
        );

        NextReservationNo = reservationRepository.selectNextReservationNo();

        List<ReservationMenuDTO> reservationMenuList2 = new ArrayList<>();
        ReservationMenuDTO reservationMenuDTO2 = new ReservationMenuDTO(1, 1, "메뉴1", 10000, "카테고리1", "메뉴1 설명", "https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg", NextReservationNo, 1);

        reservationMenuList2.add(reservationMenuDTO2);

        ReservationDTO reservation2 = new ReservationDTO(
                NextReservationNo,
                "asdf@naver.com",
                1,
                TimeUtil.toDateTime("2023-06-23 00:00:00"),
                TimeUtil.toDateTime("2023-06-23 00:00:00"),
                "매장",
                4,
                7000,
                8000,
                "예약",
                reservationMenuList2
        );

        NextReservationNo = reservationRepository.selectNextReservationNo();

        List<ReservationMenuDTO> reservationMenuList3 = new ArrayList<>();
        ReservationMenuDTO reservationMenuDTO3 = new ReservationMenuDTO(1, 1, "메뉴1", 10000, "카테고리1", "메뉴1 설명", "https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg", NextReservationNo, 1);

        reservationMenuList3.add(reservationMenuDTO);

        ReservationDTO reservation3 = new ReservationDTO(
                NextReservationNo,
                "asdf@naver.com",
                1,
                TimeUtil.toDateTime("2023-07-23 00:00:00"),
                TimeUtil.toDateTime("2023-07-23 00:00:00"),
                "매장",
                3,
                4000,
                8000,
                "예약",
                reservationMenuList3
        );

        //when
        reservationRepository.insertReservation(reservation);
        reservationRepository.insertReservation(reservation2);
        reservationRepository.insertReservation(reservation3);
        List<ReservationDTO> result = reservationRepository.selectAllReservation();

        //then
        assertThat(originalReservationSize + 3).isEqualTo(result.size());
    }

    @Test
    @Transactional
    void selectALLReservationByMemberEmail_shouldSelectAllALLReservationByMemberEmailSuccessfully() throws FindException, AddException {
        //given
        int originalReservationSize = reservationRepository.selectALLReservationByMemberEmail("asdf@naver.com").size();
        int NextReservationNo = reservationRepository.selectNextReservationNo();

        List<ReservationMenuDTO> reservationMenuList = new ArrayList<>();
        ReservationMenuDTO reservationMenuDTO = new ReservationMenuDTO(1, 1, "메뉴1", 10000, "카테고리1", "메뉴1 설명", "https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg", NextReservationNo, 1);

        reservationMenuList.add(reservationMenuDTO);

        ReservationDTO reservation = new ReservationDTO(
                NextReservationNo,
                "asdf@naver.com",
                1,
                TimeUtil.toDateTime("2023-05-23 00:00:00"),
                TimeUtil.toDateTime("2023-05-23 00:00:00"),
                "매장",
                2,
                1000,
                5000,
                "예약",
                reservationMenuList
        );

        NextReservationNo = reservationRepository.selectNextReservationNo();

        List<ReservationMenuDTO> reservationMenuList2 = new ArrayList<>();
        ReservationMenuDTO reservationMenuDTO2 = new ReservationMenuDTO(1, 1, "메뉴1", 10000, "카테고리1", "메뉴1 설명", "https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg", NextReservationNo, 1);

        reservationMenuList2.add(reservationMenuDTO2);

        ReservationDTO reservation2 = new ReservationDTO(
                NextReservationNo,
                "asdf@naver.com",
                1,
                TimeUtil.toDateTime("2023-06-23 00:00:00"),
                TimeUtil.toDateTime("2023-06-23 00:00:00"),
                "매장",
                4,
                7000,
                8000,
                "예약",
                reservationMenuList2
        );

        NextReservationNo = reservationRepository.selectNextReservationNo();

        List<ReservationMenuDTO> reservationMenuList3 = new ArrayList<>();
        ReservationMenuDTO reservationMenuDTO3 = new ReservationMenuDTO(1, 1, "메뉴1", 10000, "카테고리1", "메뉴1 설명", "https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg", NextReservationNo, 1);

        reservationMenuList3.add(reservationMenuDTO3);

        ReservationDTO reservation3 = new ReservationDTO(
                NextReservationNo,
                "aaaa@gmail.com",
                1,
                TimeUtil.toDateTime("2023-07-23 00:00:00"),
                TimeUtil.toDateTime("2023-07-23 00:00:00"),
                "매장",
                3,
                4000,
                8000,
                "예약",
                reservationMenuList3
        );

        //when
        reservationRepository.insertReservation(reservation);
        reservationRepository.insertReservation(reservation2);
        reservationRepository.insertReservation(reservation3);
        List<ReservationDTO> result = reservationRepository.selectALLReservationByMemberEmail("asdf@naver.com");

        //then
        assertThat(originalReservationSize + 2).isEqualTo(result.size());
    }

    @Test
    @Transactional
    void cancelReservation_shouldCancelReservationSuccessfully() throws ModifyException, AddException, FindException {
        //given
        int NextReservationNo = reservationRepository.selectNextReservationNo();

        List<ReservationMenuDTO> reservationMenuList = new ArrayList<>();
        ReservationMenuDTO reservationMenuDTO = new ReservationMenuDTO(1, 1, "메뉴1", 10000, "카테고리1", "메뉴1 설명", "https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg", NextReservationNo, 1);

        reservationMenuList.add(reservationMenuDTO);

        ReservationDTO reservation = new ReservationDTO(
                NextReservationNo,
                "asdf@naver.com",
                1,
                TimeUtil.toDateTime("2023-05-23 00:00:00"),
                TimeUtil.toDateTime("2023-05-23 00:00:00"),
                "매장",
                2,
                1000,
                5000,
                "예약",
                reservationMenuList
        );

        //when
        reservationRepository.insertReservation(reservation);
        reservationRepository.cancelReservation(NextReservationNo);

        //then
        ReservationDTO result = reservationRepository.selectReservationByReservationNo(NextReservationNo);
        assertThat(result.getReservationStatus()).isEqualTo("취소");
    }


    @Test
    @Transactional
    void deleteReservation_shouldDeleteReservationSuccessfully() throws FindException, AddException, RemoveException {

        //given
        int NextReservationNo = reservationRepository.selectNextReservationNo();

        List<ReservationMenuDTO> reservationMenuList = new ArrayList<>();
        reservationMenuList.add(new ReservationMenuDTO(NextReservationNo, 1, 1));

        ReservationDTO reservation = new ReservationDTO(
                NextReservationNo,
                "asdf@naver.com",
                1,
                TimeUtil.toDateTime("2023-05-23 00:00:00"),
                TimeUtil.toDateTime("2023-05-23 00:00:00"),
                "매장",
                2,
                1000,
                5000,
                "예약",
                reservationMenuList
        );

        //when
        reservationRepository.insertReservation(reservation);
        reservationRepository.deleteReservation(NextReservationNo);

        //then
        assertThrows(FindException.class, () -> {
            reservationRepository.selectReservationByReservationNo(NextReservationNo);
        });
    }

    @Test
    @Transactional
    void modifyReservation_shouldModifyReservationSuccessfully() throws AddException, ModifyException, FindException {

        //given
        int NextReservationNo = reservationRepository.selectNextReservationNo();

        List<ReservationMenuDTO> reservationMenuList = new ArrayList<>();
        reservationMenuList.add(new ReservationMenuDTO(NextReservationNo, 1, 1));
        reservationMenuList.add(new ReservationMenuDTO(NextReservationNo, 2, 1));

        ReservationDTO reservation = new ReservationDTO(
                NextReservationNo,
                "asdf@naver.com",
                1,
                TimeUtil.toDateTime("2023-05-23 00:00:00"),
                TimeUtil.toDateTime("2023-05-23 00:00:00"),
                "매장",
                2,
                1000,
                5000,
                "예약",
                reservationMenuList
        );

        List<ReservationMenuDTO> modifyReservationMenuList = new ArrayList<>();
        modifyReservationMenuList.add(new ReservationMenuDTO(NextReservationNo, 1, 3));
        modifyReservationMenuList.add(new ReservationMenuDTO(NextReservationNo, 2, 2));
        modifyReservationMenuList.add(new ReservationMenuDTO(NextReservationNo, 3, 1));

        ReservationDTO modifyReservation = new ReservationDTO(
                NextReservationNo,
                "asdf@naver.com",
                1,
                TimeUtil.toDateTime("2023-05-23 00:00:00"),
                TimeUtil.toDateTime("2023-05-23 00:00:00"),
                "매장",
                2,
                1001,
                5001,
                "결제",
                modifyReservationMenuList
        );

        //when
        reservationRepository.insertReservation(reservation);
        reservationRepository.updateReservation(modifyReservation);

        //then
        ReservationDTO result = reservationRepository.selectReservationByReservationNo(NextReservationNo);
        assertThat(result)
            .isEqualTo(modifyReservation)
            .isNotEqualTo(reservation);
    }

}