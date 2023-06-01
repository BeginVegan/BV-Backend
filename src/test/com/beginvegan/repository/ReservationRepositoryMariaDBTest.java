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
        ReservationDTO result = reservationRepository.selectReservationByReservationNo(NextReservationNo);
        assertThat(result).isNull();
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