package com.beginvegan.controller;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.dto.PaymentDTO;
import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.dto.ReservationMenuDTO;
import com.beginvegan.repository.ReservationRepository;
import com.beginvegan.service.PaymentService;
import com.beginvegan.service.ReservationService;
import com.beginvegan.service.TossService;
import com.beginvegan.util.TimeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private TossService tossService;

    @MockBean
    private ReservationRepository reservationRepository;

    private static MockHttpSession session;

    private static PaymentDTO paymentInfo;

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

        paymentInfo = new PaymentDTO().builder()
                .impUid("imp_852331602293")
                .reservationNo(reservationInfo.getReservationNo())
                .memberEmail("test@naver.com")
                .paymentTime(TimeUtil.toDateTime(new Date()))
                .paymentPrice(reservationInfo.getReservationTotalPrice() - reservationInfo.getReservationDiscount())
                .paymentStatus("결제")
                .build();
    }

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        session.setAttribute("memberEmail", "test@naver.com");
    }

    @Test
    @DisplayName("impUid에 해당하는 결제정보를 가져온다.")
    void paymentOneByImpUid() throws Exception {
        // given
        given(paymentService.findPaymentByImpUid(paymentInfo.getImpUid())).willReturn(paymentInfo);
        String paymentInfoJson = objectMapper.writeValueAsString(paymentInfo);

        // when
        String url = "http://localhost:3000" + port + "/payment/impUid";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(url)
                .param("impUid", paymentInfo.getImpUid());

        // then
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(paymentInfoJson));
    }

    @Test
    @DisplayName("reservationNo에 해당하는 결제정보를 가져온다.")
    void paymentOneByReservationNo() throws Exception {
        // given
        given(paymentService.findPaymentByReservationNo(paymentInfo.getReservationNo())).willReturn(paymentInfo);
        String paymentInfoJson = objectMapper.writeValueAsString(paymentInfo);

        // when
        String url = "http://localhost:3000" + port + "/payment/reservationNo";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(url)
                .param("reservationNo", String.valueOf(paymentInfo.getReservationNo()));

        // then
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(paymentInfoJson));
    }

    @Test
    @DisplayName("전체 결제정보를 가져온다.")
    void paymentList() throws Exception {
        // given
        List<PaymentDTO> paymentDTOList = new ArrayList<>();
        given(paymentService.findAllPayment()).willReturn(paymentDTOList);
        String paymentInfoListJson = objectMapper.writeValueAsString(paymentDTOList);

        // when
        String url = "http://localhost:3000" + port + "/payment/list";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(url);

        // then
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(paymentInfoListJson));
    }

    @Test
    @DisplayName("유저의전체 결제정보를 가져온다.")
    void paymentListByMemberEmail() throws Exception {
        // given
        List<PaymentDTO> paymentDTOList = new ArrayList<>();
        given(paymentService.findAllPayment()).willReturn(paymentDTOList);
        String paymentInfoListJson = objectMapper.writeValueAsString(paymentDTOList);

        // when
        String url = "http://localhost:3000" + port + "/payment/list/memberEmail";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(url)
                .session(session);

        // then
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(paymentInfoListJson));
    }

    @Test
    @DisplayName("유저의 결제정보를 수정한다.")
    void paymentModify() throws Exception {
        // given
        given(paymentService.modifyPayment(paymentInfo)).willReturn(paymentInfo);
        String paymentInfoJson = objectMapper.writeValueAsString(paymentInfo);

        // when
        String url = "http://localhost:3000" + port + "/payment";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentInfoJson);

        // then
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(paymentInfoJson));
    }

    @Test
    @DisplayName("결제정보를 추가한다.")
    void paymentAdd() throws Exception {
        // given
        given(paymentService.addPayment(paymentInfo)).willReturn(paymentInfo);
        String paymentInfoJson = objectMapper.writeValueAsString(paymentInfo);

        IamportResponse<Payment> portTest = new IamportResponse<>();
        Mockito.when(tossService.paymentLookup(paymentInfo.getImpUid()))
                .thenReturn(portTest);

        Mockito.when(tossService.verifyIamportService(portTest, paymentInfo.getMemberEmail(), paymentInfo.getPaymentPrice(), paymentInfo.getReservationNo()))
                .thenReturn(paymentInfo);

        Mockito.when(reservationRepository.selectReservationByReservationNo(paymentInfo.getReservationNo()))
                .thenReturn(reservationInfo);

        // when
        String url = "http://localhost:3000" + port + "/payment";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentInfoJson);

        // then
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(paymentInfoJson));
    }

    @Test
    @DisplayName("결제정보를 삭제한다.")
    void paymentRemove() throws Exception {
        // given
        given(paymentService.addPayment(paymentInfo)).willReturn(paymentInfo);

        IamportResponse<Payment> portTest = new IamportResponse<>();
        Mockito.when(tossService.cancelImpPayment(paymentInfo.getImpUid()))
                .thenReturn(portTest);

        // when
        String url = "http://localhost:3000" + port + "/payment";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(url)
                .param("impUid", paymentInfo.getImpUid());

        // then
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(portTest.toString()));
    }
}