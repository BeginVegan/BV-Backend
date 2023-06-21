package com.beginvegan.controller;

import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.service.ReservationService;
import com.beginvegan.util.TimeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    private static ReservationDTO reservationInfo;

    @BeforeAll
    public static void init() throws ParseException {
        reservationInfo = ReservationDTO.builder()
                .restaurantNo(1)
                .memberEmail("test@naver.com")
                .reservationTime(TimeUtil.toDateTime(new Date()))
                .reservationVisitTime(TimeUtil.toDateTime(new Date()))
                .reservationType("매장")
                .reservationDiscount(1000)
                .reservationTotalPrice(5000)
                .reservationPeople(4)
                .reservationStatus("예약")
                .build();
    }

    @Test
    @DisplayName("모든 예약을 가져온다.")
    void findAllUser() throws Exception {
        // given
        List<ReservationDTO> reservationDTOList = new ArrayList<>();
        given(reservationService.findAllReservation()).willReturn(reservationDTOList);
        String reservationInfoJson = objectMapper.writeValueAsString(reservationDTOList);

        // when
        String url = "http://localhost:3000" + port + "/reservation/list";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get(url);

        // then
        mvc.perform(requestBuilder)
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(reservationInfoJson));
    }

}