package com.beginvegan.repository;

import com.beginvegan.dto.*;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class ReviewRepositoryMariaDBTest {


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

    @BeforeAll
    void setTestData() throws AddException {
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


    @DisplayName("insertReview 메소드 테스트")
    @Test
    @Transactional
    void insertReview_shouldAddReviewSuccessfully() {
        // 테스트용 리뷰 생성
        ReviewDTO review = new ReviewDTO();
        review.setMemberEmail(TEST_MEMBER_EMAIL);
        review.setReviewTime(TEST_REVIEW_TIME);
        review.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
        review.setReviewContent(TEST_REVIEW_CONTENT);
        review.setReservationNo(TEST_RESERVATION_NO);
        review.setRestaurantNo(TEST_RESTAURANT_NO);
        review.setReviewStar(TEST_REVIEW_STAR);


        try {
            // 리뷰 insert
            reviewRepository.insertReview(review);
            TEST_REVIEW_NO = review.getReviewNo();
            log.info(String.valueOf(review.getReviewNo()) + "!!!!!!!!");

            // 성공 확인
            ReviewDTO retrievedReview = reviewRepository.selectReviewByReviewNo(TEST_REVIEW_NO);
            Assertions.assertEquals(retrievedReview.getReviewContent(), review.getReviewContent());
            Assertions.assertEquals(retrievedReview.getMemberEmail(), review.getMemberEmail());

            // 삭제
            reviewRepository.deleteReview(TEST_REVIEW_NO);
        } catch (AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }


    @DisplayName("deleteReview 메소드 테스트")
    @Test
    @Transactional
    void deleteReview_shouldRemoveReviewSuccessfully() {
        // 테스트용 리뷰 생성
        ReviewDTO review = new ReviewDTO();
        review.setMemberEmail(TEST_MEMBER_EMAIL);
        review.setReviewTime(TEST_REVIEW_TIME);
        review.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
        review.setReviewContent(TEST_REVIEW_CONTENT);
        review.setReservationNo(TEST_RESERVATION_NO);
        review.setRestaurantNo(TEST_RESTAURANT_NO);
        review.setReviewStar(TEST_REVIEW_STAR);


        try {
            // 리뷰 insert
            reviewRepository.insertReview(review);
            TEST_REVIEW_NO = review.getReviewNo();

            // 삭제
            reviewRepository.deleteReview(TEST_REVIEW_NO);

            // 성공 확인 - 삭제된 리뷰를 찾을 수 없음
            ReviewDTO selectedReview = null;
            try {
                selectedReview = reviewRepository.selectReviewByReviewNo(review.getReviewNo());
            } catch (FindException e) {
                Assertions.assertEquals("해당 리뷰 정보가 없습니다", e.getMessage());
            }
            // 성공 확인 - 반환 값 검증
            Assertions.assertNull(selectedReview);
        } catch (AddException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }


    @DisplayName("updateReview 메소드 테스트")
    @Test
    @Transactional
    void updateReview_shouldUpdateReviewSuccessfully() {
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

        try {
            // 리뷰 insert
            reviewRepository.insertReview(review);
            TEST_REVIEW_NO = review.getReviewNo();

            // 성공 확인 - 삭제된 리뷰를 찾을 수 없음
            ReviewDTO selectedReview = null;
            try {
                reviewRepository.updateReview(TEST_REVIEW_NO, updateReview);
                selectedReview = reviewRepository.selectReviewByReviewNo(review.getReviewNo());
            } catch (FindException | ModifyException e) {
                Assertions.assertEquals("리뷰 수정에 실패했습니다", e.getMessage());
            }
            // 성공 확인 - 반환 값 검증
            Assertions.assertEquals(selectedReview.getReviewNo(), TEST_REVIEW_NO);
            Assertions.assertEquals(selectedReview.getReviewContent(), TEST_REVIEW_CONTENT + "수정됨");
        } catch (AddException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("selectAllReviewByMemberEmail 메소드 테스트")
    @Test
    @Transactional
    void selectAllReviewByMemberEmail_shouldReturnCorrectReview() {
        // 테스트용 리뷰 생성
        ReviewDTO review = new ReviewDTO();
        review.setMemberEmail(TEST_MEMBER_EMAIL);
        review.setReviewTime(TEST_REVIEW_TIME);
        review.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
        review.setReviewContent(TEST_REVIEW_CONTENT);
        review.setReservationNo(TEST_RESERVATION_NO);
        review.setRestaurantNo(TEST_RESTAURANT_NO);
        review.setReviewStar(TEST_REVIEW_STAR);


        try {
            // 리뷰 insert
            reviewRepository.insertReview(review);
            TEST_REVIEW_NO = review.getReviewNo();
            // 성공 확인
            List<ReviewDTO> retrievedReviewList = reviewRepository.selectAllReviewByMemberEmail(TEST_MEMBER_EMAIL);
            Assertions.assertTrue(retrievedReviewList.contains(review));

            // 삭제
            reviewRepository.deleteReview(TEST_REVIEW_NO);
        } catch (AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }


    @DisplayName("selectAllReviewByRestaurantId 메소드 테스트")
    @Test
    @Transactional
    void selectAllReviewByRestaurantId_shouldReturnCorrectReviewList() {
        // 테스트용 리뷰 생성 1
        ReviewDTO review = new ReviewDTO();
        review.setMemberEmail(TEST_MEMBER_EMAIL);
        review.setReviewTime(TEST_REVIEW_TIME);
        review.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
        review.setReviewContent(TEST_REVIEW_CONTENT);
        review.setReservationNo(TEST_RESERVATION_NO);
        review.setRestaurantNo(TEST_RESTAURANT_NO);
        review.setReviewStar(TEST_REVIEW_STAR);

        try {
            // 리뷰 insert
            reviewRepository.insertReview(review);
            TEST_REVIEW_NO = review.getReviewNo();
            // 성공 확인
            List<ReviewDTO> retrievedReviewList = reviewRepository.selectAllReviewByRestaurantId(TEST_RESTAURANT_NO);
            Assertions.assertTrue(retrievedReviewList.contains(review));
            // 삭제
            reviewRepository.deleteReview(TEST_REVIEW_NO);
        } catch (AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("selectReviewByReviewNo 메소드 테스트")
    @Test
    @Transactional
    void selectReviewByReviewNo_shouldReturnCorrectReview() {
        // 테스트용 리뷰 생성
        ReviewDTO review = new ReviewDTO();
        review.setMemberEmail(TEST_MEMBER_EMAIL);
        review.setReviewTime(TEST_REVIEW_TIME);
        review.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
        review.setReviewContent(TEST_REVIEW_CONTENT);
        review.setReservationNo(TEST_RESERVATION_NO);
        review.setRestaurantNo(TEST_RESTAURANT_NO);
        review.setReviewStar(TEST_REVIEW_STAR);

        try {
            // 리뷰 insert
            reviewRepository.insertReview(review);
            TEST_REVIEW_NO = review.getReviewNo();
            // 성공 확인
            ReviewDTO retrievedReview = reviewRepository.selectReviewByReviewNo(TEST_REVIEW_NO);
            Assertions.assertEquals(retrievedReview, review);
            // 삭제
            reviewRepository.deleteReview(TEST_REVIEW_NO);
        } catch (AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    //-------------------------------------------ECEPTION TEST PART------------------------------------------

    @DisplayName("selectAllReviewByMemberEmail 메소드 테스트 - 존재하지 않는 이메일로 리뷰 검색")
    @Test
    @Transactional
    void selectAllReviewByMemberEmail_shouldThrowExceptionWhenSearchingWithNonexistentEmail() {
        Assertions.assertThrows(FindException.class, () -> {
            reviewRepository.selectAllReviewByMemberEmail("nonexistent@email.com");  // 존재하지 않는 이메일
        });
    }

    @DisplayName("selectAllReviewByRestaurantId 메소드 테스트 - 존재하지 않는 레스토랑 아이디로 리뷰 검색")
    @Test
    @Transactional
    void selectAllReviewByRestaurantId_shouldThrowExceptionWhenSearchingWithNonexistentRestaurantId() {
        Assertions.assertThrows(FindException.class, () -> {
            reviewRepository.selectAllReviewByRestaurantId(-1);  // 존재하지 않는 레스토랑 아이디
        });
    }

    @DisplayName("selectReviewByReviewNo 메소드 테스트 - 존재하지 않는 리뷰 번호로 리뷰 검색")
    @Test
    @Transactional
    void selectReviewByReviewNo_shouldThrowExceptionWhenSearchingWithNonexistentReviewNo() {
        Assertions.assertThrows(FindException.class, () -> {
            reviewRepository.selectReviewByReviewNo(-1);  // 존재하지 않는 리뷰 번호
        });
    }


    @DisplayName("insertReview 메소드 테스트 - 중복된 리뷰 추가")
    @Test
    @Transactional
    void insertReview_shouldThrowExceptionWhenAddingDuplicateReview() {
        // 테스트용 리뷰 생성
        ReviewDTO review = new ReviewDTO();
        review.setMemberEmail(TEST_MEMBER_EMAIL);
        review.setReviewTime(TEST_REVIEW_TIME);
        review.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
        review.setReviewContent(TEST_REVIEW_CONTENT);
        review.setReservationNo(TEST_RESERVATION_NO);
        review.setRestaurantNo(TEST_RESTAURANT_NO);
        review.setReviewStar(TEST_REVIEW_STAR);

        try {
            // 리뷰 insert
            reviewRepository.insertReview(review);
            TEST_REVIEW_NO = review.getReviewNo();

            // 중복된 리뷰 추가 시도
            ReviewDTO duplicateReview = new ReviewDTO();
            duplicateReview.setMemberEmail(TEST_MEMBER_EMAIL);
            duplicateReview.setReviewTime(TEST_REVIEW_TIME);
            duplicateReview.setReviewPhotoDir(TEST_MENU_PHOTO_DIR);
            duplicateReview.setReviewContent(TEST_REVIEW_CONTENT);
            duplicateReview.setReservationNo(-1);
            duplicateReview.setRestaurantNo(TEST_RESTAURANT_NO);
            duplicateReview.setReviewStar(TEST_REVIEW_STAR);

            Assertions.assertThrows(AddException.class, () -> {
                reviewRepository.insertReview(duplicateReview);
            });

            // 삭제
            reviewRepository.deleteReview(TEST_REVIEW_NO);
        } catch (AddException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }
}