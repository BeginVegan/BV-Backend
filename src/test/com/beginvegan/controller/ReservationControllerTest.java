package com.beginvegan.controller;

import com.beginvegan.dto.MemberDTO;
import com.beginvegan.dto.MenuDTO;
import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.dto.ReservationMenuDTO;
import com.beginvegan.service.ReservationService;
import com.beginvegan.util.TimeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    private static MockHttpSession session;

    private static ReservationDTO reservationInfo;

    private static MenuDTO menuInfo;

    @BeforeAll
    public static void init() throws ParseException {
        menuInfo = new MenuDTO();
        menuInfo.setRestaurantNo(1);
        menuInfo.setMenuName("테스트 음식");
        menuInfo.setMenuPrice(10000);
        menuInfo.setMenuCategory("식사");
        menuInfo.setMenuDetail("맛있는 테스트");
        menuInfo.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/1/%E1%84%83%E1%85%A1%E1%84%8B%E1%85%AE%E1%86%AB%E1%84%85%E1%85%A9%E1%84%83%E1%85%B3.jpeg");

        List<ReservationMenuDTO> reservationMenuList = new ArrayList<>();
        reservationMenuList.add(new ReservationMenuDTO(1, menuInfo.getMenuNo(), 1));

        reservationInfo = ReservationDTO.builder()
                .reservationNo(1)
                .restaurantNo(1)
                .memberEmail("test@naver.com")
                .reservationTime(TimeUtil.toDateTime(new Date()))
                .reservationVisitTime(TimeUtil.toDateTime(new Date()))
                .reservationType("매장")
                .reservationDiscount(1000)
                .reservationTotalPrice(5000)
                .reservationPeople(4)
                .reservationStatus("예약")
                .reservationMenuList(reservationMenuList)
                .build();
    }

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        session.setAttribute("memberEmail", "test@naver.com");
    }

    @Test
    @DisplayName("예약번호에 해당하는 예약을 가져온다.")
    void reservationOne() throws Exception {
        // given
        given(reservationService.findReservationByReservationNo(reservationInfo.getReservationNo())).willReturn(reservationInfo);
        String reservationInfoJson = objectMapper.writeValueAsString(reservationInfo);

        // when
        String url = "http://localhost:3000" + port + "/reservation" + "/" + reservationInfo.getReservationNo();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(url);

        // then
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(reservationInfoJson));
    }

    @Test
    @DisplayName("모든 예약을 가져온다.")
    void findAllUser() throws Exception {
        // given
        List<ReservationDTO> reservationDTOList = new ArrayList<>();
        given(reservationService.findAllReservation()).willReturn(reservationDTOList);
        String reservationInfoListJson = objectMapper.writeValueAsString(reservationDTOList);

        // when
        String url = "http://localhost:3000" + port + "/reservation/list";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get(url);

        // then
        mvc.perform(requestBuilder)
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(reservationInfoListJson));
    }

    @Test
    @DisplayName("유저의 모든 예약을 가져온다.")
    void reservationList() throws Exception {
        // given
        List<ReservationDTO> reservationDTOList = new ArrayList<>();
        given(reservationService.findAllReservation()).willReturn(reservationDTOList);
        String reservationInfoListJson = objectMapper.writeValueAsString(reservationDTOList);

        // when
        String url = "http://localhost:3000" + port + "/reservation/list/memberEmail";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(url)
                .session(session);

        // then
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(reservationInfoListJson));
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void reservationAdd() throws Exception {
        // given
        given(reservationService.addReservation(reservationInfo, Optional.empty())).willReturn(reservationInfo);
        String reservationInfoJson = objectMapper.writeValueAsString(reservationInfo);

        // when
        String url = "http://localhost:3000" + port + "/reservation";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(reservationInfoJson);

        // then
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(reservationInfoJson));
    }

    @Test
    @DisplayName("예약을 수정한다.")
    void reservationModify() throws Exception {
        // given
        given(reservationService.modifyReservation(reservationInfo)).willReturn(reservationInfo);
        String reservationInfoJson = objectMapper.writeValueAsString(reservationInfo);

        // when
        String url = "http://localhost:3000" + port + "/reservation";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(reservationInfoJson);

        // then
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(reservationInfoJson));
    }


    @Test
    @DisplayName("예약을 취소한다.")
    void reservationCancel() throws Exception {
        // given
        given(reservationService.cancelReservation(reservationInfo.getReservationNo())).willReturn(reservationInfo.getReservationNo());

        // when
        String url = "http://localhost:3000" + port + "/reservation/cancel";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(url)
                .param("reservationNo",reservationInfo.getReservationNo()+"");

        // then
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(reservationInfo.getReservationNo()+""));
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void reservationRemove() throws Exception {
        // given
        given(reservationService.deleteReservation(reservationInfo.getReservationNo())).willReturn(reservationInfo.getReservationNo());

        // when
        String url = "http://localhost:3000" + port + "/reservation" + "/" + reservationInfo.getReservationNo();;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(url);

        // then
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(reservationInfo.getReservationNo()+""));
    }
}