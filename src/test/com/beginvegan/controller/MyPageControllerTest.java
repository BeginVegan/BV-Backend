package com.beginvegan.controller;

import com.beginvegan.dto.*;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.repository.*;
import com.beginvegan.service.MyPageService;
import com.beginvegan.util.TimeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class MyPageControllerTest {


    //멤버
    private final String TEST_MEMBER_EMAIL = "example@email.com";
    private final String TEST_MEMBER_NAME = "TestMan";
    private final int TEST_MEMBER_POINT = 100;
    //레스토랑
    private final String TEST_RESTAURANT_NAME = "테스트 레스토랑";
    private final String TEST_RESTAURANT_ADDRESS = "서울특별시 종로구 종로 1";
    private final String TEST_RESTAURANT_ADDRESS_GU = "종로구";
    private final double TEST_RESTAURANT_POS_X = 37.570034;
    private final double TEST_RESTAURANT_POS_Y = 126.976785;
    private final String TEST_RESTAURANT_TEL = "02-2323-1212";
    private final String TEST_RESTAURANT_DETAIL = "상세정보1";
    private final LocalTime TEST_RESTAURANT_OPEN_TIME = TimeUtil.toTime("10:00:00");
    private final LocalTime TEST_RESTAURANT_CLOSE_TIME = TimeUtil.toTime("21:30:00");
    private final int TEST_RESTAURANT_AVG_PRICE = 10000;
    private final int TEST_RESTAURANT_TABLE = 10;
    private final int TEST_RESTAURANT_TABLE_MEMBER = 4;
    private final int TEST_RESTAURANT_VEGAN_LEVEL = 2;
    private final String TEST_RESTAURANT_PHOTO_DIR = "https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg";
    private final double TEST_RESTAURANT_STAR = 4.5;
    //메뉴
    private final String TEST_MENU_NAME = "테스트 메뉴";
    private final int TEST_MENU_PRICE = 10000;
    private final String TEST_MENU_CATEGORY = "식사";
    private final String TEST_MENU_DETAIL = "테스트 메뉴 상세";
    private final String TEST_MENU_PHOTO_DIR = "https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg";
    //예약
    private final LocalDateTime TEST_RESERVATION_TIME = TimeUtil.toDateTime("2023-05-23 00:00:00");
    private final LocalDateTime TEST_RESERVATION_VISIT_TIME = TimeUtil.toDateTime("2023-05-23 00:00:00");
    private final String TEST_RESERVATION_TYPE = "매장";
    private final int TEST_RESERVATION_PEOPLE = 2;
    private final int TEST_RESERVATION_MENU_COUNT = 2;
    private final int TEST_RESERVATION_DISCOUNT = 1000;
    private final int TEST_RESERVATION_TOTAL_PRICE = 5000;
    private final String TEST_RESERVATION_STATUS = "예약";
    //리뷰
    private final LocalDateTime TEST_REVIEW_TIME = TimeUtil.toDateTime("2023-05-25 00:00:00");
    private final String TEST_REVIEW_CONTENT = "테스트용 리뷰 내용";
    private final int TEST_REVIEW_STAR = 1;

    private int TEST_RESERVATION_NO = 1;
    private int TEST_MENU_NO = 1;
    private int TEST_RESTAURANT_NO = 1;

    private int TEST_REVIEW_NO = 1;

    private MockMultipartFile TEST_MULTI_PART_FILE_DTO_ONLY = null;
    private MockMultipartFile TEST_MULTI_PART_FILE_DTO_IMAGE = null;

    private ReviewDTO TEST_REVIEW_DTO = null;

    //멤버
    @Autowired
    private MemberRepositoryMariaDB memberRepository;
    //레스토랑
    @Autowired
    private RestaurantRepositoryMariaDB restaurantRepository;
    //메뉴
    @Autowired
    private MenuRepository menuRepository;
    //예약
    @Autowired
    private ReservationRepository reservationRepository;
    //리뷰
    @Autowired
    private ReviewRepository reviewRepository;
    //세션
    @MockBean
    private MockHttpSession mockSession;

    @Autowired
    private MyPageController myPageController;

    @Autowired
    private  MyPageService myPageService;
    @BeforeAll
    void setTestData() throws AddException, JsonProcessingException {

        // 테스트용 멤버 생성
        MemberDTO member = new MemberDTO();
        member.setMemberEmail(TEST_MEMBER_EMAIL);
        member.setMemberName(TEST_MEMBER_NAME);
        member.setMemberPoint(TEST_MEMBER_POINT);

        memberRepository.insertMember(member);

        // 테스트용 레스토랑 생성
        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.setRestaurantName(TEST_RESTAURANT_NAME);
        restaurant.setRestaurantAddress(TEST_RESTAURANT_ADDRESS);
        restaurant.setRestaurantAddressGu(TEST_RESTAURANT_ADDRESS_GU);
        restaurant.setRestaurantX(TEST_RESTAURANT_POS_X);
        restaurant.setRestaurantY(TEST_RESTAURANT_POS_Y);
        restaurant.setRestaurantOpen(TEST_RESTAURANT_OPEN_TIME);
        restaurant.setRestaurantClose(TEST_RESTAURANT_CLOSE_TIME);
        restaurant.setRestaurantPhone(TEST_RESTAURANT_TEL);
        restaurant.setRestaurantDetail(TEST_RESTAURANT_DETAIL);
        restaurant.setRestaurantAvgPrice(TEST_RESTAURANT_AVG_PRICE);
        restaurant.setRestaurantTable(TEST_RESTAURANT_TABLE);
        restaurant.setRestaurantTableMember(TEST_RESTAURANT_TABLE_MEMBER);
        restaurant.setRestaurantVeganLevel(TEST_RESTAURANT_VEGAN_LEVEL);
        restaurant.setRestaurantPhotoDir(TEST_RESTAURANT_PHOTO_DIR);
        restaurant.setRestaurantStar(TEST_RESTAURANT_STAR);

        restaurantRepository.insertRestaurant(restaurant);
        TEST_RESTAURANT_NO = restaurant.getRestaurantNo();

        // 테스트용 메뉴 생성
        MenuDTO menu = new MenuDTO();
        menu.setRestaurantNo(restaurant.getRestaurantNo());
        menu.setMenuName(TEST_MENU_NAME);
        menu.setMenuPrice(TEST_MENU_PRICE);
        menu.setMenuCategory(TEST_MENU_CATEGORY);
        menu.setMenuDetail(TEST_MENU_DETAIL);
        menu.setMenuPhotoDir(TEST_MENU_PHOTO_DIR);

        TEST_MENU_NO = menuRepository.insertMenu(menu);

        // 테스트용 예약 생성
        TEST_RESERVATION_NO = reservationRepository.selectNextReservationNo();

        List<ReservationMenuDTO> reservationMenuList = new ArrayList<>();
        reservationMenuList.add(new ReservationMenuDTO(TEST_RESERVATION_NO, TEST_MENU_NO, TEST_RESERVATION_MENU_COUNT));

        ReservationDTO reservation = new ReservationDTO(
                TEST_RESERVATION_NO,
                member.getMemberEmail(),
                restaurant.getRestaurantNo(),
                TEST_RESERVATION_TIME,
                TEST_RESERVATION_VISIT_TIME,
                TEST_RESERVATION_TYPE,
                TEST_RESERVATION_PEOPLE,
                TEST_RESERVATION_DISCOUNT,
                TEST_RESERVATION_TOTAL_PRICE,
                TEST_RESERVATION_STATUS,
                reservationMenuList
        );

        reservationRepository.insertReservation(reservation);

        //테스트용 멀티파트 파일 생성

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Creating ReviewDTO
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setMemberEmail(TEST_MEMBER_EMAIL);
        reviewDTO.setReviewTime(TEST_REVIEW_TIME);
        reviewDTO.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
        reviewDTO.setReviewContent(TEST_REVIEW_CONTENT);
        reviewDTO.setReservationNo(TEST_RESERVATION_NO);
        reviewDTO.setRestaurantNo(TEST_RESTAURANT_NO);
        reviewDTO.setReviewStar(TEST_REVIEW_STAR);
        // Converting ReviewDTO to JSON
        String jsonContent = objectMapper.writeValueAsString(reviewDTO);

        MockMultipartFile reviewInfo = new MockMultipartFile("reviewDTO", "sandwich.jpg", "application/json", jsonContent.getBytes());
        MockMultipartFile reviewImage = new MockMultipartFile("reviewImage", "sandwich.jpg", "image/jpeg", "image data".getBytes());
        TEST_MULTI_PART_FILE_DTO_ONLY = reviewInfo;
        TEST_MULTI_PART_FILE_DTO_IMAGE = reviewImage;

        TEST_REVIEW_DTO = reviewDTO;
    }

    @AfterAll
    void clearTestData() throws RemoveException {
        //테스트 예약 삭제
        reservationRepository.deleteReservation(TEST_RESERVATION_NO);
        //테스트 레스토랑 삭제
        restaurantRepository.deleteRestaurant(TEST_RESTAURANT_NO);
        //테스트 멤버 삭제
        memberRepository.deleteMember(TEST_MEMBER_EMAIL);
    }

    @DisplayName("GET review/userEmail 테스트 - with image")
    @Test
    @Transactional
    void reviewList_shouldReturnCorrectHttpResponse_DTO_IMAGE() {
        try {
            // 테스트용 세션 생성
            MockitoAnnotations.openMocks(this);
            mockSession = new MockHttpSession();
            mockSession.setAttribute("memberEmail", TEST_MEMBER_EMAIL);
            // 리뷰 insert
            myPageController.reviewAdd(mockSession,TEST_REVIEW_DTO, Optional.ofNullable(TEST_MULTI_PART_FILE_DTO_IMAGE));
            TEST_REVIEW_NO = TEST_REVIEW_DTO.getReviewNo();
            // 성공 확인
            ResponseEntity responseEntity = myPageController.reviewList(mockSession);
            List<ReviewDTO> retrievedReviewList = (List<ReviewDTO>) responseEntity.getBody();
            Assertions.assertTrue(retrievedReviewList.contains(TEST_REVIEW_DTO));
            // 삭제
            reviewRepository.deleteReview(TEST_REVIEW_NO);
        } catch (IOException| ParseException| AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }
    @DisplayName("GET review/userEmail 테스트 - dto only" )
    @Test
    @Transactional
    void reviewList_shouldReturnCorrectHttpResponse_DTO_ONLY() {
        try {
            // 테스트용 세션 생성
            MockitoAnnotations.openMocks(this);
            mockSession = new MockHttpSession();
            mockSession.setAttribute("memberEmail", TEST_MEMBER_EMAIL);

            // 리뷰 insert
            myPageController.reviewAdd(mockSession,TEST_REVIEW_DTO, Optional.ofNullable(TEST_MULTI_PART_FILE_DTO_ONLY));
            TEST_REVIEW_NO = TEST_REVIEW_DTO.getReviewNo();
            // 성공 확인
            ResponseEntity responseEntity = myPageController.reviewList(mockSession);

            List<ReviewDTO> retrievedReviewList = (List<ReviewDTO>) responseEntity.getBody();
            Assertions.assertTrue(retrievedReviewList.contains(TEST_REVIEW_DTO));
            // 삭제
            reviewRepository.deleteReview(TEST_REVIEW_NO);
        } catch (IOException| ParseException| AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("GET review/restaurantId/{id} 테스트 - with image")
    @Test
    @Transactional
    void reviewList_restaurantID_shouldReturnCorrectHttpResponse_DTO_IMAGE() {
        try {
            // 테스트용 세션 생성
            MockitoAnnotations.openMocks(this);
            mockSession = new MockHttpSession();
            mockSession.setAttribute("memberEmail", TEST_MEMBER_EMAIL);

            // 리뷰 insert
            myPageController.reviewAdd(mockSession,TEST_REVIEW_DTO, Optional.ofNullable(TEST_MULTI_PART_FILE_DTO_IMAGE));
            TEST_RESTAURANT_NO = TEST_REVIEW_DTO.getRestaurantNo();
            // 성공 확인
            ResponseEntity responseEntity = myPageController.reviewList(TEST_RESTAURANT_NO, mockSession);
            List<ReviewDTO> retrievedReviewList = (List<ReviewDTO>) responseEntity.getBody();
            Assertions.assertTrue(retrievedReviewList.contains(TEST_REVIEW_DTO));
            // 삭제
            reviewRepository.deleteReview(TEST_REVIEW_NO);
        } catch (IOException| ParseException| AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("GET review/restaurantId/{id} 테스트 - dto only")
    @Test
    @Transactional
    void reviewList_restaurantID_shouldReturnCorrectHttpResponse_DTO_ONLY() {
        try {
            // 테스트용 세션 생성
            MockitoAnnotations.openMocks(this);
            mockSession = new MockHttpSession();
            mockSession.setAttribute("memberEmail", TEST_MEMBER_EMAIL);

            // 리뷰 insert
            myPageController.reviewAdd(mockSession,TEST_REVIEW_DTO, Optional.ofNullable(TEST_MULTI_PART_FILE_DTO_ONLY));
            TEST_RESTAURANT_NO = TEST_REVIEW_DTO.getRestaurantNo();
            // 성공 확인
            ResponseEntity responseEntity = myPageController.reviewList(TEST_RESTAURANT_NO, mockSession);
            List<ReviewDTO> retrievedReviewList = (List<ReviewDTO>) responseEntity.getBody();
            Assertions.assertTrue(retrievedReviewList.contains(TEST_REVIEW_DTO));
            // 삭제
            reviewRepository.deleteReview(TEST_REVIEW_NO);
        } catch (IOException| ParseException| AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }


    @DisplayName("GET review/{reviewNo} 테스트 - dto only")
    @Test
    @Transactional
    void reviewInfo_reviewNo_shouldReturnCorrectHttpResponse_DTO_ONLY() {
        try {
            // 테스트용 세션 생성
            MockitoAnnotations.openMocks(this);
            mockSession = new MockHttpSession();
            mockSession.setAttribute("memberEmail", TEST_MEMBER_EMAIL);

            // 리뷰 insert
            myPageController.reviewAdd(mockSession,TEST_REVIEW_DTO, Optional.ofNullable(TEST_MULTI_PART_FILE_DTO_ONLY));
            TEST_REVIEW_NO = TEST_REVIEW_DTO.getReviewNo();
            // 성공 확인
            ResponseEntity responseEntity = myPageController.reviewInfo(TEST_REVIEW_NO);
            ReviewDTO retrievedReview = (ReviewDTO) responseEntity.getBody();
            Assertions.assertEquals(retrievedReview, TEST_REVIEW_DTO);
            // 삭제
            reviewRepository.deleteReview(TEST_REVIEW_NO);
        } catch (IOException| ParseException| AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }


    @DisplayName("GET review/{reviewNo} 테스트 - with image")
    @Test
    @Transactional
    void reviewInfo_reviewNo_shouldReturnCorrectHttpResponse_DTO_IMAGE() {
        try {
            // 테스트용 세션 생성
            MockitoAnnotations.openMocks(this);
            mockSession = new MockHttpSession();
            mockSession.setAttribute("memberEmail", TEST_MEMBER_EMAIL);

            // 리뷰 insert
            myPageController.reviewAdd(mockSession,TEST_REVIEW_DTO, Optional.ofNullable(TEST_MULTI_PART_FILE_DTO_IMAGE));
            TEST_REVIEW_NO = TEST_REVIEW_DTO.getReviewNo();
            // 성공 확인
            ResponseEntity responseEntity = myPageController.reviewInfo(TEST_REVIEW_NO);
            ReviewDTO retrievedReview = (ReviewDTO) responseEntity.getBody();
            Assertions.assertEquals(retrievedReview, TEST_REVIEW_DTO);
            // 삭제
            reviewRepository.deleteReview(TEST_REVIEW_NO);
        } catch (IOException| ParseException| AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }



    @DisplayName("POST review/ 테스트 - dto only")
    @Test
    @Transactional
    void reviewAdd_shouldReturnCorrectHttpResponse_DTO_ONLY() {
        try {
            // 테스트용 세션 생성
            MockitoAnnotations.openMocks(this);
            mockSession = new MockHttpSession();
            mockSession.setAttribute("memberEmail", TEST_MEMBER_EMAIL);

            // 리뷰 insert
            myPageController.reviewAdd(mockSession,TEST_REVIEW_DTO, Optional.ofNullable(TEST_MULTI_PART_FILE_DTO_ONLY));
            TEST_REVIEW_NO = TEST_REVIEW_DTO.getReviewNo();
            // 성공 확인
            ResponseEntity responseEntity = myPageController.reviewInfo(TEST_REVIEW_NO);
            ReviewDTO retrievedReview = (ReviewDTO) responseEntity.getBody();
            Assertions.assertEquals(retrievedReview, TEST_REVIEW_DTO);
            // 삭제
            reviewRepository.deleteReview(TEST_REVIEW_NO);
        } catch (IOException| ParseException| AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }


    @DisplayName("POST review/ 테스트 - with image")
    @Test
    @Transactional
    void reviewAdd_shouldReturnCorrectHttpResponse_DTO_IMAGE() {
        try {
            // 테스트용 세션 생성
            MockitoAnnotations.openMocks(this);
            mockSession = new MockHttpSession();
            mockSession.setAttribute("memberEmail", TEST_MEMBER_EMAIL);

            // 리뷰 insert
            myPageController.reviewAdd(mockSession,TEST_REVIEW_DTO, Optional.ofNullable(TEST_MULTI_PART_FILE_DTO_IMAGE));
            TEST_REVIEW_NO = TEST_REVIEW_DTO.getReviewNo();
            // 성공 확인
            ResponseEntity responseEntity = myPageController.reviewInfo(TEST_REVIEW_NO);
            ReviewDTO retrievedReview = (ReviewDTO) responseEntity.getBody();
            Assertions.assertEquals(retrievedReview, TEST_REVIEW_DTO);
            // 삭제
            reviewRepository.deleteReview(TEST_REVIEW_NO);
        } catch (IOException| ParseException| AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }


    @DisplayName("DELETE review/{reviewNo} 테스트")
    @Test
    @Transactional
    void reviewRemove_shouldReturnCorrectHttpResponse() {
        try {
            // 테스트용 세션 생성
            MockitoAnnotations.openMocks(this);
            mockSession = new MockHttpSession();
            mockSession.setAttribute("memberEmail", TEST_MEMBER_EMAIL);

            // 리뷰 insert
            myPageController.reviewAdd(mockSession,TEST_REVIEW_DTO, Optional.ofNullable(TEST_MULTI_PART_FILE_DTO_IMAGE));
            TEST_REVIEW_NO = TEST_REVIEW_DTO.getReviewNo();
            // 성공 확인
            ResponseEntity responseEntity = myPageController.reviewRemove(TEST_REVIEW_NO, mockSession);
            Assertions.assertEquals(responseEntity.getStatusCode().toString(), "200 OK");


            Assertions.assertThrows(FindException.class, ()->myPageController.reviewInfo(TEST_REVIEW_NO));
            // 삭제
            reviewRepository.deleteReview(TEST_REVIEW_NO);
        } catch (IOException| ParseException| AddException  | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }


    @DisplayName("PUT review/{reviewNo} 테스트")
    @Test
    @Transactional
    void reviewModify_shouldReturnCorrectHttpResponse() {
        try {
            // 테스트용 세션 생성
            MockitoAnnotations.openMocks(this);
            mockSession = new MockHttpSession();
            mockSession.setAttribute("memberEmail", TEST_MEMBER_EMAIL);

            // 업데이트용 리뷰 생성
            ReviewDTO updateReview = new ReviewDTO();
            updateReview.setMemberEmail(TEST_MEMBER_EMAIL);
            updateReview.setReviewTime(TEST_REVIEW_TIME);
            updateReview.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
            updateReview.setReviewContent(TEST_REVIEW_CONTENT + "수정됨");
            updateReview.setReservationNo(TEST_RESERVATION_NO);
            updateReview.setRestaurantNo(TEST_RESTAURANT_NO);
            updateReview.setReviewStar(TEST_REVIEW_STAR);

            // 리뷰 insert
            myPageController.reviewAdd(mockSession,TEST_REVIEW_DTO, Optional.ofNullable(TEST_MULTI_PART_FILE_DTO_IMAGE));
            TEST_REVIEW_NO = TEST_REVIEW_DTO.getReviewNo();


            // 성공 확인
            ResponseEntity responseEntity = myPageController.reviewModify(TEST_REVIEW_NO,updateReview, mockSession);
            Assertions.assertEquals(responseEntity.getStatusCode().toString(), "200 OK");

            ResponseEntity responseEntity_after_modify = myPageController.reviewInfo(TEST_REVIEW_NO);
            ReviewDTO retrievedReview = (ReviewDTO) responseEntity_after_modify.getBody();
            Assertions.assertEquals(retrievedReview.getReviewContent(), updateReview.getReviewContent());

            // 삭제
            reviewRepository.deleteReview(TEST_REVIEW_NO);
        } catch (IOException | FindException|ParseException | RuntimeException | AddException | RemoveException | ModifyException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }


    @DisplayName("GET bookmark/userEmail 테스트")
    @Test
    @Transactional
    void bookmarkList_shouldReturnCorrectHttpResponse() {
        try {
            // 테스트용 세션 생성
            MockitoAnnotations.openMocks(this);
            mockSession = new MockHttpSession();
            mockSession.setAttribute("memberEmail", TEST_MEMBER_EMAIL);

            // 북마크 insert
            myPageController.bookmarkAdd(TEST_RESTAURANT_NO,mockSession);
            // 성공 확인
            ResponseEntity responseEntity = myPageController.bookmarkList(mockSession);
            List<BookmarkDTO> retrievedBookmarkList = (List<BookmarkDTO>) responseEntity.getBody();
            Assertions.assertFalse(retrievedBookmarkList.isEmpty());
            Assertions.assertEquals(responseEntity.getStatusCode().toString(), "200 OK");
            // 삭제
            myPageController.bookmarkRemove(TEST_RESTAURANT_NO, mockSession);
        } catch (FindException | AddException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("DELETE bookmark/{restaurantNo} 테스트")
    @Test
    @Transactional
    void bookmarkRemove_shouldReturnCorrectHttpResponse() {
        try {
            // 테스트용 세션 생성
            MockitoAnnotations.openMocks(this);
            mockSession = new MockHttpSession();
            mockSession.setAttribute("memberEmail", TEST_MEMBER_EMAIL);

            // 북마크 insert
            myPageController.bookmarkAdd(TEST_RESTAURANT_NO,mockSession);
            // 성공 확인
            ResponseEntity responseEntity = myPageController.bookmarkList(mockSession);
            List<BookmarkDTO> retrievedBookmarkList = (List<BookmarkDTO>) responseEntity.getBody();
            Assertions.assertFalse(retrievedBookmarkList.isEmpty());
            Assertions.assertEquals(responseEntity.getStatusCode().toString(), "200 OK");

            ResponseEntity responseEntity_after_remove1 = myPageController.bookmarkRemove(TEST_RESTAURANT_NO,mockSession);
            Assertions.assertEquals(responseEntity_after_remove1.getStatusCode().toString(), "200 OK");

            Assertions.assertThrows(FindException.class, ()->myPageController.bookmarkList(mockSession));
        } catch (FindException | AddException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("POST bookmark/{restaurantNo} 테스트")
    @Test
    @Transactional
    void bookmarkAdd_shouldReturnCorrectHttpResponse() {
        try {
            // 테스트용 세션 생성
            MockitoAnnotations.openMocks(this);
            mockSession = new MockHttpSession();
            mockSession.setAttribute("memberEmail", TEST_MEMBER_EMAIL);

            // 북마크 insert
            myPageController.bookmarkAdd(TEST_RESTAURANT_NO,mockSession);
            // 성공 확인
            ResponseEntity responseEntity = myPageController.bookmarkList(mockSession);
            List<BookmarkDTO> retrievedBookmarkList = (List<BookmarkDTO>) responseEntity.getBody();
            Assertions.assertFalse(retrievedBookmarkList.isEmpty());
            Assertions.assertEquals(responseEntity.getStatusCode().toString(), "200 OK");
            // 삭제
            myPageController.bookmarkRemove(TEST_RESTAURANT_NO, mockSession);
        } catch (FindException | AddException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }


    @DisplayName("GET point-history 테스트")
    @Test
    @Transactional
    void getPointHistory_shouldReturnCorrectHttpResponse() {
        // 테스트용 세션 생성
        MockitoAnnotations.openMocks(this);
        mockSession = new MockHttpSession();
        mockSession.setAttribute("memberEmail", TEST_MEMBER_EMAIL);

        Assertions.assertThrows(FindException.class, ()->myPageController.getPointHistory(mockSession));
    }

    @DisplayName("GET point-history 테스트 - null email")
    @Test
    @Transactional
    void getPointHistory_shouldReturnCorrectHttpResponse_null_email()  {
        // 테스트용 세션 생성
        try {
            MockitoAnnotations.openMocks(this);
            mockSession = new MockHttpSession();
            mockSession.setAttribute("memberEmail", null);

            ResponseEntity responseEntity =myPageController.getPointHistory(mockSession);
            String retrievedMsg = (String) responseEntity.getBody();
            Assertions.assertEquals(retrievedMsg, "User not logged in or session expired");
        } catch (FindException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("POST point-history 테스트 - null email")
    @Test
    @Transactional
    void getPointHistory_shouldReturnCorrectHttpResponse_post_null_email()  {
        // 테스트용 포인트 생성
        PointDTO pointDTO = new PointDTO(null,"TEST", TEST_RESERVATION_TIME, 1234, 1234);
        Assertions.assertThrows(FindException.class, ()->myPageController.getPointHistory(pointDTO));

    }

    @DisplayName("POST point-history 테스트")
    @Test
    @Transactional
    void getPointHistory_shouldReturnCorrectHttpResponse_post()  {
        // 테스트용 포인트 생성
        PointDTO pointDTO = new PointDTO(TEST_MEMBER_EMAIL,"TEST", TEST_RESERVATION_TIME, 1234, 1234);
        Assertions.assertThrows(FindException.class, ()->myPageController.getPointHistory(pointDTO));
    }

    @DisplayName("GET point-history 테스트 - service exception no email")
    @Test
    @Transactional
    void getPointHistory_shouldThrowException_whenServiceException()  {
        // This line tells Mockito to throw a new FindException when findAllPointByMemberEmail is called
        Assertions.assertThrows(FindException.class, ()->myPageService.findAllPointByMemberEmail(TEST_MEMBER_EMAIL));
    }


    @DisplayName("GET point-history 테스트 - service exception null")
    @Test
    @Transactional
    void getPointHistory_shouldThrowException_whenServiceException_null() {
        // This line tells Mockito to throw a new FindException when findAllPointByMemberEmail is called
        Assertions.assertThrows(FindException.class, ()->myPageService.findAllPointByMemberEmail(null));
    }
}
