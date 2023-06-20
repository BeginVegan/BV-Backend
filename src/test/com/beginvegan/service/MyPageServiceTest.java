package com.beginvegan.service;

import com.beginvegan.dto.*;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.repository.*;
import com.beginvegan.util.TimeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@SpringBootTest
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class MyPageServiceTest {

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
    @MockBean
    private ReviewRepository mockReviewRepository;
    @Autowired
    private MyPageService myPageService;
    @Autowired
    private MemberService memberService;
    @BeforeAll
    void setTestData() throws AddException, JsonProcessingException {
        // 테스트용 멤버 생성
        MemberDTO member = new MemberDTO();
        member.setMemberEmail(TEST_MEMBER_EMAIL);
        member.setMemberName(TEST_MEMBER_NAME);
        member.setMemberPoint(TEST_MEMBER_POINT);

        memberRepository.insertMember(member);
//        memberService.addMember(member);
//        mockMemberRepository.insertMember(member);

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
                TEST_MEMBER_EMAIL,
                TEST_RESTAURANT_NO,
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

    @DisplayName("addReview 테스트 - DTO only")
    @Test
    @Transactional
    void addReview_shouldAddReviewSuccessfully_reviewDTO_Only() throws AddException, FindException, IOException, ParseException, RemoveException {
        // 테스트용 리뷰 생성
        ReviewDTO review = new ReviewDTO();
        review.setMemberEmail(TEST_MEMBER_EMAIL);
        review.setReviewTime(TEST_REVIEW_TIME);
        review.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
        review.setReviewContent(TEST_REVIEW_CONTENT);
        review.setReservationNo(TEST_RESERVATION_NO);
        review.setRestaurantNo(TEST_RESTAURANT_NO);
        review.setReviewStar(TEST_REVIEW_STAR);


        // Act
        myPageService.addReview(review, TEST_MEMBER_EMAIL, Optional.ofNullable(TEST_MULTI_PART_FILE_DTO_ONLY));

        verify(mockReviewRepository, times(1)).insertReview(review);

        //삭제
        mockReviewRepository.deleteReview(TEST_REVIEW_NO);
    }

    @DisplayName("addReview 테스트 - DTO with Image")
    @Test
    @Transactional
    void addReview_shouldAddReviewSuccessfully_reviewDTO_Image() throws AddException, FindException, IOException, ParseException, RemoveException {
        // 테스트용 리뷰 생성
        ReviewDTO review = new ReviewDTO();
        review.setMemberEmail(TEST_MEMBER_EMAIL);
        review.setReviewTime(TEST_REVIEW_TIME);
        review.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
        review.setReviewContent(TEST_REVIEW_CONTENT);
        review.setReservationNo(TEST_RESERVATION_NO);
        review.setRestaurantNo(TEST_RESTAURANT_NO);
        review.setReviewStar(TEST_REVIEW_STAR);

        myPageService.addReview(review, TEST_MEMBER_EMAIL, Optional.ofNullable(TEST_MULTI_PART_FILE_DTO_IMAGE));

        // Act
        verify(mockReviewRepository, times(1)).insertReview(review);

        //삭제
        mockReviewRepository.deleteReview(TEST_REVIEW_NO);
    }

    @DisplayName("findAllReviewByMemberEmail 테스트")
    @Test
    @Transactional
    void findAllReviewByMemberEmail_shouldReturnReviewListSuccessfully() throws AddException, FindException, IOException, ParseException, RemoveException {
        // 테스트용 리뷰 생성
        ReviewDTO review = new ReviewDTO();
        review.setMemberEmail(TEST_MEMBER_EMAIL);
        review.setReviewTime(TEST_REVIEW_TIME);
        review.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
        review.setReviewContent(TEST_REVIEW_CONTENT);
        review.setReservationNo(TEST_RESERVATION_NO);
        review.setRestaurantNo(TEST_RESTAURANT_NO);
        review.setReviewStar(TEST_REVIEW_STAR);

        myPageService.addReview(review, TEST_MEMBER_EMAIL, Optional.ofNullable(TEST_MULTI_PART_FILE_DTO_IMAGE));


        // Act
        myPageService.findAllReviewByMemberEmail(TEST_MEMBER_EMAIL);
        verify(mockReviewRepository, times(1)).selectAllReviewByMemberEmail(TEST_MEMBER_EMAIL);

        //삭제
        mockReviewRepository.deleteReview(TEST_REVIEW_NO);
    }

    @DisplayName("findAllReviewByRestaurantId 테스트")
    @Test
    @Transactional
    void findAllReviewByRestaurantId_shouldReturnReviewListSuccessfully() throws AddException, FindException, IOException, ParseException, RemoveException {
        // 테스트용 리뷰 생성
        ReviewDTO review = new ReviewDTO();
        review.setMemberEmail(TEST_MEMBER_EMAIL);
        review.setReviewTime(TEST_REVIEW_TIME);
        review.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
        review.setReviewContent(TEST_REVIEW_CONTENT);
        review.setReservationNo(TEST_RESERVATION_NO);
        review.setRestaurantNo(TEST_RESTAURANT_NO);
        review.setReviewStar(TEST_REVIEW_STAR);

        myPageService.addReview(review, TEST_MEMBER_EMAIL, Optional.ofNullable(TEST_MULTI_PART_FILE_DTO_IMAGE));

        // Act
        myPageService.findAllReviewByRestaurantId(TEST_RESTAURANT_NO);
        verify(mockReviewRepository, times(1)).selectAllReviewByRestaurantId(TEST_RESTAURANT_NO);

        //삭제
        mockReviewRepository.deleteReview(TEST_REVIEW_NO);
    }

    @DisplayName("findReviewByReviewNo 테스트")
    @Test
    @Transactional
    void findReviewByReviewNo_shouldReturnReviewSuccessfully() throws AddException, FindException, IOException, ParseException, RemoveException {
        // 테스트용 리뷰 생성
        ReviewDTO review = new ReviewDTO();
        review.setMemberEmail(TEST_MEMBER_EMAIL);
        review.setReviewTime(TEST_REVIEW_TIME);
        review.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
        review.setReviewContent(TEST_REVIEW_CONTENT);
        review.setReservationNo(TEST_RESERVATION_NO);
        review.setRestaurantNo(TEST_RESTAURANT_NO);
        review.setReviewStar(TEST_REVIEW_STAR);

        myPageService.addReview(review, TEST_MEMBER_EMAIL, Optional.ofNullable(TEST_MULTI_PART_FILE_DTO_IMAGE));

        // Act
        myPageService.findReviewByReviewNo(review.getReviewNo());
        verify(mockReviewRepository, times(1)).selectReviewByReviewNo(review.getReviewNo());

        //삭제
        mockReviewRepository.deleteReview(TEST_REVIEW_NO);
    }


    @DisplayName("removeReview 테스트")
    @Test
    @Transactional
    void removeReview_shouldRemoveReviewSuccessfully() throws AddException, FindException, IOException, ParseException, RemoveException {
        // 테스트용 리뷰 생성
        ReviewDTO review = new ReviewDTO();
        review.setMemberEmail(TEST_MEMBER_EMAIL);
        review.setReviewTime(TEST_REVIEW_TIME);
        review.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
        review.setReviewContent(TEST_REVIEW_CONTENT);
        review.setReservationNo(TEST_RESERVATION_NO);
        review.setRestaurantNo(TEST_RESTAURANT_NO);
        review.setReviewStar(TEST_REVIEW_STAR);

        myPageService.addReview(review, TEST_MEMBER_EMAIL, Optional.ofNullable(TEST_MULTI_PART_FILE_DTO_IMAGE));

        // Act
        myPageService.removeReview(review.getReviewNo());
        verify(mockReviewRepository, times(1)).deleteReview(review.getReviewNo());

        //삭제
        mockReviewRepository.deleteReview(TEST_REVIEW_NO);
    }


    @DisplayName("modifyReview 테스트 - DTO only")
    @Test
    @Transactional
    void modifyReview_shouldModifyReviewSuccessfully_ReviewDTO_Only() throws AddException, FindException, IOException, ParseException, RemoveException, ModifyException {
        // 테스트용 리뷰 생성
        ReviewDTO review = new ReviewDTO();
        review.setMemberEmail(TEST_MEMBER_EMAIL);
        review.setReviewTime(TEST_REVIEW_TIME);
        review.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
        review.setReviewContent(TEST_REVIEW_CONTENT);
        review.setReservationNo(TEST_RESERVATION_NO);
        review.setRestaurantNo(TEST_RESTAURANT_NO);
        review.setReviewStar(TEST_REVIEW_STAR);

        // 업데이트용 리뷰 생성
        ReviewDTO updateReview = new ReviewDTO();
        updateReview.setMemberEmail(TEST_MEMBER_EMAIL);
        updateReview.setReviewTime(TEST_REVIEW_TIME);
        updateReview.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
        updateReview.setReviewContent(TEST_REVIEW_CONTENT + "수정됨");
        updateReview.setReservationNo(TEST_RESERVATION_NO);
        updateReview.setRestaurantNo(TEST_RESTAURANT_NO);
        updateReview.setReviewStar(TEST_REVIEW_STAR);

        myPageService.addReview(review, TEST_MEMBER_EMAIL, Optional.ofNullable(TEST_MULTI_PART_FILE_DTO_ONLY));

        // Act
        myPageService.modifyReview(review.getReviewNo(), updateReview);
        verify(mockReviewRepository, times(1)).updateReview(review.getReviewNo(),updateReview);

        //삭제
        mockReviewRepository.deleteReview(TEST_REVIEW_NO);
    }


    @DisplayName("modifyReview 테스트 - DTO with Image")
    @Test
    @Transactional
    void modifyReview_shouldModifyReviewSuccessfully_ReviewDTO_Image() throws AddException, FindException, IOException, ParseException, RemoveException, ModifyException {
        // 테스트용 리뷰 생성
        ReviewDTO review = new ReviewDTO();
        review.setMemberEmail(TEST_MEMBER_EMAIL);
        review.setReviewTime(TEST_REVIEW_TIME);
        review.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
        review.setReviewContent(TEST_REVIEW_CONTENT);
        review.setReservationNo(TEST_RESERVATION_NO);
        review.setRestaurantNo(TEST_RESTAURANT_NO);
        review.setReviewStar(TEST_REVIEW_STAR);

        // 업데이트용 리뷰 생성
        ReviewDTO updateReview = new ReviewDTO();
        updateReview.setMemberEmail(TEST_MEMBER_EMAIL);
        updateReview.setReviewTime(TEST_REVIEW_TIME);
        updateReview.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
        updateReview.setReviewContent(TEST_REVIEW_CONTENT + "수정됨");
        updateReview.setReservationNo(TEST_RESERVATION_NO);
        updateReview.setRestaurantNo(TEST_RESTAURANT_NO);
        updateReview.setReviewStar(TEST_REVIEW_STAR);

        myPageService.addReview(review, TEST_MEMBER_EMAIL, Optional.ofNullable(TEST_MULTI_PART_FILE_DTO_IMAGE));

        // Act
        myPageService.modifyReview(review.getReviewNo(), updateReview);
        verify(mockReviewRepository, times(1)).updateReview(review.getReviewNo(),updateReview);

        //삭제
        mockReviewRepository.deleteReview(TEST_REVIEW_NO);
    }


    @DisplayName("addBookmark 테스트")
    @Test
    @Transactional
    void addBookmark_shouldAddBookmarkSuccessfully() throws AddException, FindException, IOException, ParseException, RemoveException, ModifyException {

        // 테스트용 북마크 생성
        BookmarkDTO bookmarkDTO = new BookmarkDTO();
        bookmarkDTO.setRestaurantNo(TEST_RESTAURANT_NO);
        bookmarkDTO.setMemberEmail(TEST_MEMBER_EMAIL);

        // Act
        myPageService.addBookmark(TEST_MEMBER_EMAIL,TEST_RESTAURANT_NO);

        // 검증
        List<BookmarkDTO> bookmarkList = memberRepository.selectAllBookmarkByMemberEmail(TEST_MEMBER_EMAIL);
        Assertions.assertTrue(bookmarkList.contains(bookmarkDTO));
        //삭제
        memberRepository.deleteBookmark(TEST_RESTAURANT_NO,TEST_MEMBER_EMAIL);
    }


    @DisplayName("removeBookmark 테스트")
    @Test
    @Transactional
    void removeBookmark_shouldRemoveBookmarkSuccessfully() throws AddException, FindException, IOException, ParseException, RemoveException, ModifyException {

        // 테스트용 북마크 생성
        BookmarkDTO bookmarkDTO = new BookmarkDTO();
        bookmarkDTO.setRestaurantNo(TEST_RESTAURANT_NO);
        bookmarkDTO.setMemberEmail(TEST_MEMBER_EMAIL);

        // Act
        myPageService.addBookmark(TEST_MEMBER_EMAIL,TEST_RESTAURANT_NO);

        // 검증
        List<BookmarkDTO> bookmarkList_before_delete = memberRepository.selectAllBookmarkByMemberEmail(TEST_MEMBER_EMAIL);
        Assertions.assertTrue(bookmarkList_before_delete.contains(bookmarkDTO));
        //삭제
        memberRepository.deleteBookmark(TEST_RESTAURANT_NO,TEST_MEMBER_EMAIL);

        //검증
        Assertions.assertThrows(FindException.class,()->memberRepository.selectAllBookmarkByMemberEmail(TEST_MEMBER_EMAIL));
    }

    @DisplayName("findAllBookmarkByMemberEmail 테스트")
    @Test
    @Transactional
    void findAllBookmarkByMemberEmail_shouldReturnBookmarkListSuccessfully() throws AddException, FindException, IOException, ParseException, RemoveException, ModifyException {
        // 테스트용 북마크 생성
        BookmarkDTO bookmarkDTO = new BookmarkDTO();
        bookmarkDTO.setRestaurantNo(TEST_RESTAURANT_NO);
        bookmarkDTO.setMemberEmail(TEST_MEMBER_EMAIL);

        // Act
        myPageService.addBookmark(TEST_MEMBER_EMAIL,TEST_RESTAURANT_NO);

        // 검증
        List<BookmarkDTO> bookmarkList = memberRepository.selectAllBookmarkByMemberEmail(TEST_MEMBER_EMAIL);
        Assertions.assertTrue(bookmarkList.contains(bookmarkDTO));
        Assertions.assertEquals(bookmarkList.get(0).getMemberEmail(), TEST_MEMBER_EMAIL);
        //삭제
        memberRepository.deleteBookmark(TEST_RESTAURANT_NO,TEST_MEMBER_EMAIL);
    }

    @DisplayName("findAllPointByMemberEmail 테스트")
    @Test
    @Transactional
    void findAllPointByMemberEmail_shouldReturnPointListSuccessfully() throws AddException, FindException, IOException, ParseException, RemoveException, ModifyException {
        // 테스트용 포인트 생성
        PointDTO point = new PointDTO(TEST_MEMBER_EMAIL,"TEST", TEST_RESERVATION_TIME, 1234, 1234);
        memberRepository.insertPoint(point);
        // Act
        List<PointDTO> pointList = myPageService.findAllPointByMemberEmail(TEST_MEMBER_EMAIL);
        log.info(pointList.toString() + " !!!!!");
        Assertions.assertEquals(1234, pointList.get(pointList.size() -1).getPointChange());

    }

}
