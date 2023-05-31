package com.beginvegan.repository;

import com.beginvegan.dto.MemberDTO;
import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.dto.ReviewDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.util.TimeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

@SpringBootTest
class ReviewRepositoryMariaDBTest {


    @Autowired
    private ReservationRepositoryMariaDB reservationRepository;
    @Autowired
    private ReviewRepositoryMariaDB reviewRepository;
    @Autowired
    private MemberRepositoryMariaDB memberRepository;

    @DisplayName("selectAllReviewByMemberEmail 레포 테스트")
    @Test
    void selectAllReviewByMemberEmail_shouldReturnCorrectReviewList() throws ParseException, RemoveException, FindException {
        // Set Time
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        Timestamp currentTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 10, 00, 00))).getTime());


        // S3 PhotoDir
        String photoDir = "https://bv-image.s3.ap-northeast-2.amazonaws.com/review/salad.jpg";


        // Create a test MemberDTO object
        MemberDTO testMember01 = new MemberDTO();
        testMember01.setMemberEmail("member_01@email.com");
        testMember01.setMemberName("John Doe");
        testMember01.setMemberPoint(100);

        MemberDTO testMember02 = new MemberDTO();
        testMember02.setMemberEmail("member_02@email.com");
        testMember02.setMemberName("John Doe");
        testMember02.setMemberPoint(100);

        MemberDTO testMember03 = new MemberDTO();
        testMember03.setMemberEmail("member_03@email.com");
        testMember03.setMemberName("John Doe");
        testMember03.setMemberPoint(100);

        try {
            // Call the insertMember() method
            memberRepository.insertMember(testMember01);
            memberRepository.insertMember(testMember02);
            memberRepository.insertMember(testMember03);


            // Assert that the member is added successfully by retrieving it and comparing the values
            MemberDTO retrievedMember01 = memberRepository.selectMemberByMemberEmail(testMember01.getMemberEmail());
            MemberDTO retrievedMember02 = memberRepository.selectMemberByMemberEmail(testMember02.getMemberEmail());
            MemberDTO retrievedMember03 = memberRepository.selectMemberByMemberEmail(testMember03.getMemberEmail());

            Assertions.assertEquals(testMember01.getMemberEmail(), retrievedMember01.getMemberEmail());
            Assertions.assertEquals(testMember02.getMemberEmail(), retrievedMember02.getMemberEmail());
            Assertions.assertEquals(testMember03.getMemberEmail(), retrievedMember03.getMemberEmail());


            // Create test Reservation Objects
            ReservationDTO testReservation01 = new ReservationDTO();
            testReservation01.setMemberEmail("member_01@email.com");
            testReservation01.setRestaurantNo(1);
            testReservation01.setReservationTime(TimeUtil.toDateTime(currentTime));
            testReservation01.setReservationVisitTime(TimeUtil.toDateTime(currentTime));
            testReservation01.setReservationType("매장");
            testReservation01.setReservationPeople(2);
            testReservation01.setReservationDiscount(1000);
            testReservation01.setReservationTotalPrice(10000);
            testReservation01.setReservationStatus("예약");

            ReservationDTO testReservation02 = new ReservationDTO();
            testReservation02.setMemberEmail("member_02@email.com");
            testReservation02.setRestaurantNo(1);
            testReservation02.setReservationTime(TimeUtil.toDateTime(currentTime));
            testReservation02.setReservationVisitTime(TimeUtil.toDateTime(currentTime));
            testReservation02.setReservationType("매장");
            testReservation02.setReservationPeople(2);
            testReservation02.setReservationDiscount(1000);
            testReservation02.setReservationTotalPrice(10000);
            testReservation02.setReservationStatus("예약");

            ReservationDTO testReservation03 = new ReservationDTO();
            testReservation03.setMemberEmail("member_03@email.com");
            testReservation03.setRestaurantNo(1);
            testReservation03.setReservationTime(TimeUtil.toDateTime(currentTime));
            testReservation03.setReservationVisitTime(TimeUtil.toDateTime(currentTime));
            testReservation03.setReservationType("매장");
            testReservation03.setReservationPeople(2);
            testReservation03.setReservationDiscount(1000);
            testReservation03.setReservationTotalPrice(10000);
            testReservation03.setReservationStatus("예약");

            try {
                // 리뷰테스트를 위해 예약 데이터를 넣는 부분
                reservationRepository.insertReservation(testReservation01);
                reservationRepository.insertReservation(testReservation02);
                reservationRepository.insertReservation(testReservation03);

//                TODO: SelectAllReservation 고쳐지면 주석 부활
//                // 구현된 로직을 통해서 selectAllReservation() 메소드 실행
//                List<ReservationDTO> reservationList = reservationRepository.selectAllReservation();
//
//                // Assertion을 통한 테스트
//                Assertions.assertNotNull(reservationList); // 반환 실패 확인
//                Assertions.assertFalse(reservationList.isEmpty()); // 빈 배열 반환 확인
//                Assertions.assertTrue(reservationList.contains(testReservation01)); // 테스트 케이스 1번
//                Assertions.assertTrue(reservationList.contains(testReservation02)); // 테스트 케이스 2번
//                Assertions.assertTrue(reservationList.contains(testReservation03)); // 테스트 케이스 3번
//

                // Create test ReviewDTO objects
                ReviewDTO testReview01 = new ReviewDTO();
                testReview01.setMemberEmail("member_01@email.com");
                testReview01.setReviewContent("테스트 리뷰 내용 1");
                testReview01.setReviewStar(1);
                testReview01.setRestaurantNo(1);
                testReview01.setReviewTime(currentTime);
                testReview01.setReviewPhotoDir(photoDir);
                testReview01.setReservationNo(testReservation01.getReservationNo());

                ReviewDTO testReview02 = new ReviewDTO();
                testReview02.setMemberEmail("member_02@email.com");
                testReview02.setReviewContent("테스트 리뷰 내용 2");
                testReview02.setReviewStar(1);
                testReview02.setRestaurantNo(1);
                testReview02.setReviewTime(currentTime);
                testReview02.setReviewPhotoDir(photoDir);
                testReview02.setReservationNo(testReservation02.getReservationNo());


                ReviewDTO testReview03 = new ReviewDTO();
                testReview03.setMemberEmail("member_03@email.com");
                testReview03.setReviewContent("테스트 리뷰 내용 3");
                testReview03.setReviewStar(1);
                testReview03.setRestaurantNo(1);
                testReview03.setReviewTime(currentTime);
                testReview03.setReviewPhotoDir(photoDir);
                testReview03.setReservationNo(testReservation03.getReservationNo());

                try {
                    reviewRepository.insertReview(testReview01);
                    reviewRepository.insertReview(testReview02);
                    reviewRepository.insertReview(testReview03);

                    // 구현된 로직을 통해서 selectAllReviewByMemberEmail() 메소드 실행
                    List<ReviewDTO> reviewList_01 = reviewRepository.selectAllReviewByMemberEmail(testMember01.getMemberEmail());
                    List<ReviewDTO> reviewList_02 = reviewRepository.selectAllReviewByMemberEmail(testMember02.getMemberEmail());
                    List<ReviewDTO> reviewList_03 = reviewRepository.selectAllReviewByMemberEmail(testMember03.getMemberEmail());


                    // Assertion을 통한 테스트
                    Assertions.assertNotNull(reviewList_01); // 반환 실패 확인
                    Assertions.assertNotNull(reviewList_02); // 반환 실패 확인
                    Assertions.assertNotNull(reviewList_03); // 반환 실패 확인

                    Assertions.assertFalse(reviewList_01.isEmpty()); // 빈 배열 반환 확인
                    Assertions.assertFalse(reviewList_02.isEmpty()); // 빈 배열 반환 확인
                    Assertions.assertFalse(reviewList_03.isEmpty()); // 빈 배열 반환 확인

                    Assertions.assertTrue(reviewList_01.contains(testReview01)); // 테스트 케이스 1번
                    Assertions.assertTrue(reviewList_02.contains(testReview02)); // 테스트 케이스 2번
                    Assertions.assertTrue(reviewList_03.contains(testReview03)); // 테스트 케이스 3번

                } catch (AddException e) {
                    e.printStackTrace();
                    Assertions.fail("Exception occurred: " + e.getMessage());
                } finally {
                    reviewRepository.deleteReview(testReview01.getReviewNo());
                    reviewRepository.deleteReview(testReview02.getReviewNo());
                    reviewRepository.deleteReview(testReview03.getReviewNo());
                }
            } catch (AddException | FindException e) {
                e.printStackTrace();
                Assertions.fail("Exception occurred: " + e.getMessage());
            } finally {

                reservationRepository.deleteReservation(testReservation01.getReservationNo());
                reservationRepository.deleteReservation(testReservation02.getReservationNo());
                reservationRepository.deleteReservation(testReservation03.getReservationNo());
            }
        } catch (AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        } finally {
            // Clean up by deleting the test member
            memberRepository.deleteMember(testMember01.getMemberEmail());
            memberRepository.deleteMember(testMember02.getMemberEmail());
            memberRepository.deleteMember(testMember03.getMemberEmail());
        }
    }

    @DisplayName("selectAllReviewByRestaurantId 레포 테스트")
    @Test
    void selectAllReviewByRestaurantId_shouldReturnCorrectReviewList() throws ParseException, RemoveException {
        // Set Time
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        Timestamp currentTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 10, 00, 00))).getTime());


        // S3 PhotoDir
        String photoDir = "https://bv-image.s3.ap-northeast-2.amazonaws.com/review/salad.jpg";


        // Create a test MemberDTO object
        MemberDTO testMember01 = new MemberDTO();
        testMember01.setMemberEmail("member_01@email.com");
        testMember01.setMemberName("John Doe");
        testMember01.setMemberPoint(100);

        MemberDTO testMember02 = new MemberDTO();
        testMember02.setMemberEmail("member_02@email.com");
        testMember02.setMemberName("John Doe");
        testMember02.setMemberPoint(100);

        MemberDTO testMember03 = new MemberDTO();
        testMember03.setMemberEmail("member_03@email.com");
        testMember03.setMemberName("John Doe");
        testMember03.setMemberPoint(100);

        try {
            // Call the insertMember() method
            memberRepository.insertMember(testMember01);
            memberRepository.insertMember(testMember02);
            memberRepository.insertMember(testMember03);


            // Assert that the member is added successfully by retrieving it and comparing the values
            MemberDTO retrievedMember01 = memberRepository.selectMemberByMemberEmail(testMember01.getMemberEmail());
            MemberDTO retrievedMember02 = memberRepository.selectMemberByMemberEmail(testMember02.getMemberEmail());
            MemberDTO retrievedMember03 = memberRepository.selectMemberByMemberEmail(testMember03.getMemberEmail());

            Assertions.assertEquals(testMember01.getMemberEmail(), retrievedMember01.getMemberEmail());
            Assertions.assertEquals(testMember02.getMemberEmail(), retrievedMember02.getMemberEmail());
            Assertions.assertEquals(testMember03.getMemberEmail(), retrievedMember03.getMemberEmail());


            // Create test Reservation Objects
            ReservationDTO testReservation01 = new ReservationDTO();
            testReservation01.setMemberEmail("member_01@email.com");
            testReservation01.setRestaurantNo(1);
            testReservation01.setReservationTime(TimeUtil.toDateTime(currentTime));
            testReservation01.setReservationVisitTime(TimeUtil.toDateTime(currentTime));
            testReservation01.setReservationType("매장");
            testReservation01.setReservationPeople(2);
            testReservation01.setReservationDiscount(1000);
            testReservation01.setReservationTotalPrice(10000);
            testReservation01.setReservationStatus("예약");

            ReservationDTO testReservation02 = new ReservationDTO();
            testReservation02.setMemberEmail("member_02@email.com");
            testReservation02.setRestaurantNo(1);
            testReservation02.setReservationTime(TimeUtil.toDateTime(currentTime));
            testReservation02.setReservationVisitTime(TimeUtil.toDateTime(currentTime));
            testReservation02.setReservationType("매장");
            testReservation02.setReservationPeople(2);
            testReservation02.setReservationDiscount(1000);
            testReservation02.setReservationTotalPrice(10000);
            testReservation02.setReservationStatus("예약");

            ReservationDTO testReservation03 = new ReservationDTO();
            testReservation03.setMemberEmail("member_03@email.com");
            testReservation03.setRestaurantNo(1);
            testReservation03.setReservationTime(TimeUtil.toDateTime(currentTime));
            testReservation03.setReservationVisitTime(TimeUtil.toDateTime(currentTime));
            testReservation03.setReservationType("매장");
            testReservation03.setReservationPeople(2);
            testReservation03.setReservationDiscount(1000);
            testReservation03.setReservationTotalPrice(10000);
            testReservation03.setReservationStatus("예약");

            try {
                // 리뷰테스트를 위해 예약 데이터를 넣는 부분
                reservationRepository.insertReservation(testReservation01);
                reservationRepository.insertReservation(testReservation02);
                reservationRepository.insertReservation(testReservation03);

//                TODO: SelectAllReservation 고쳐지면 주석 부활
//                // 구현된 로직을 통해서 selectAllReservation() 메소드 실행
//                List<ReservationDTO> reservationList = reservationRepository.selectAllReservation();
//
//                // Assertion을 통한 테스트
//                Assertions.assertNotNull(reservationList); // 반환 실패 확인
//                Assertions.assertFalse(reservationList.isEmpty()); // 빈 배열 반환 확인
//                Assertions.assertTrue(reservationList.contains(testReservation01)); // 테스트 케이스 1번
//                Assertions.assertTrue(reservationList.contains(testReservation02)); // 테스트 케이스 2번
//                Assertions.assertTrue(reservationList.contains(testReservation03)); // 테스트 케이스 3번
//

                // Create test ReviewDTO objects
                ReviewDTO testReview01 = new ReviewDTO();
                testReview01.setMemberEmail("member_01@email.com");
                testReview01.setReviewContent("테스트 리뷰 내용 1");
                testReview01.setReviewStar(1);
                testReview01.setRestaurantNo(1);
                testReview01.setReviewTime(currentTime);
                testReview01.setReviewPhotoDir(photoDir);
                testReview01.setReservationNo(testReservation01.getReservationNo());

                ReviewDTO testReview02 = new ReviewDTO();
                testReview02.setMemberEmail("member_02@email.com");
                testReview02.setReviewContent("테스트 리뷰 내용 2");
                testReview02.setReviewStar(1);
                testReview02.setRestaurantNo(1);
                testReview02.setReviewTime(currentTime);
                testReview02.setReviewPhotoDir(photoDir);
                testReview02.setReservationNo(testReservation02.getReservationNo());


                ReviewDTO testReview03 = new ReviewDTO();
                testReview03.setMemberEmail("member_03@email.com");
                testReview03.setReviewContent("테스트 리뷰 내용 3");
                testReview03.setReviewStar(1);
                testReview03.setRestaurantNo(1);
                testReview03.setReviewTime(currentTime);
                testReview03.setReviewPhotoDir(photoDir);
                testReview03.setReservationNo(testReservation03.getReservationNo());

                try {
                    reviewRepository.insertReview(testReview01);
                    reviewRepository.insertReview(testReview02);
                    reviewRepository.insertReview(testReview03);

                    // 구현된 로직을 통해서 selectAllReviewByRestaurantId() 메소드 실행
                    List<ReviewDTO> reviewList_01 = reviewRepository.selectAllReviewByRestaurantId(1);
                    List<ReviewDTO> reviewList_02 = reviewRepository.selectAllReviewByRestaurantId(1);
                    List<ReviewDTO> reviewList_03 = reviewRepository.selectAllReviewByRestaurantId(1);


                    // Assertion을 통한 테스트
                    Assertions.assertNotNull(reviewList_01); // 반환 실패 확인
                    Assertions.assertNotNull(reviewList_02); // 반환 실패 확인
                    Assertions.assertNotNull(reviewList_03); // 반환 실패 확인

                    Assertions.assertFalse(reviewList_01.isEmpty()); // 빈 배열 반환 확인
                    Assertions.assertFalse(reviewList_02.isEmpty()); // 빈 배열 반환 확인
                    Assertions.assertFalse(reviewList_03.isEmpty()); // 빈 배열 반환 확인

                    Assertions.assertTrue(reviewList_01.contains(testReview01)); // 테스트 케이스 1번
                    Assertions.assertTrue(reviewList_02.contains(testReview02)); // 테스트 케이스 2번
                    Assertions.assertTrue(reviewList_03.contains(testReview03)); // 테스트 케이스 3번

                } catch (AddException e) {
                    e.printStackTrace();
                    Assertions.fail("Exception occurred: " + e.getMessage());
                } finally {
                    reviewRepository.deleteReview(testReview01.getReviewNo());
                    reviewRepository.deleteReview(testReview02.getReviewNo());
                    reviewRepository.deleteReview(testReview03.getReviewNo());
                }
            } catch (AddException | FindException e) {
                e.printStackTrace();
                Assertions.fail("Exception occurred: " + e.getMessage());
            } finally {

                reservationRepository.deleteReservation(testReservation01.getReservationNo());
                reservationRepository.deleteReservation(testReservation02.getReservationNo());
                reservationRepository.deleteReservation(testReservation03.getReservationNo());
            }
        } catch (AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        } finally {
            // Clean up by deleting the test member
            memberRepository.deleteMember(testMember01.getMemberEmail());
            memberRepository.deleteMember(testMember02.getMemberEmail());
            memberRepository.deleteMember(testMember03.getMemberEmail());
        }
    }


    @DisplayName("selectReviewByReviewNo 레포 테스트")
    @Test
    void selectReviewByReviewNo_shouldReturnCorrectReview() throws ParseException, RemoveException {
        // Set Time
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        Timestamp currentTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 10, 00, 00))).getTime());


        // S3 PhotoDir
        String photoDir = "https://bv-image.s3.ap-northeast-2.amazonaws.com/review/salad.jpg";


        // Create a test MemberDTO object
        MemberDTO testMember01 = new MemberDTO();
        testMember01.setMemberEmail("member_01@email.com");
        testMember01.setMemberName("John Doe");
        testMember01.setMemberPoint(100);

        MemberDTO testMember02 = new MemberDTO();
        testMember02.setMemberEmail("member_02@email.com");
        testMember02.setMemberName("John Doe");
        testMember02.setMemberPoint(100);

        MemberDTO testMember03 = new MemberDTO();
        testMember03.setMemberEmail("member_03@email.com");
        testMember03.setMemberName("John Doe");
        testMember03.setMemberPoint(100);

        try {
            // Call the insertMember() method
            memberRepository.insertMember(testMember01);
            memberRepository.insertMember(testMember02);
            memberRepository.insertMember(testMember03);


            // Assert that the member is added successfully by retrieving it and comparing the values
            MemberDTO retrievedMember01 = memberRepository.selectMemberByMemberEmail(testMember01.getMemberEmail());
            MemberDTO retrievedMember02 = memberRepository.selectMemberByMemberEmail(testMember02.getMemberEmail());
            MemberDTO retrievedMember03 = memberRepository.selectMemberByMemberEmail(testMember03.getMemberEmail());

            Assertions.assertEquals(testMember01.getMemberEmail(), retrievedMember01.getMemberEmail());
            Assertions.assertEquals(testMember02.getMemberEmail(), retrievedMember02.getMemberEmail());
            Assertions.assertEquals(testMember03.getMemberEmail(), retrievedMember03.getMemberEmail());


            // Create test Reservation Objects
            ReservationDTO testReservation01 = new ReservationDTO();
            testReservation01.setMemberEmail("member_01@email.com");
            testReservation01.setRestaurantNo(1);
            testReservation01.setReservationTime(TimeUtil.toDateTime(currentTime));
            testReservation01.setReservationVisitTime(TimeUtil.toDateTime(currentTime));
            testReservation01.setReservationType("매장");
            testReservation01.setReservationPeople(2);
            testReservation01.setReservationDiscount(1000);
            testReservation01.setReservationTotalPrice(10000);
            testReservation01.setReservationStatus("예약");

            ReservationDTO testReservation02 = new ReservationDTO();
            testReservation02.setMemberEmail("member_02@email.com");
            testReservation02.setRestaurantNo(1);
            testReservation02.setReservationTime(TimeUtil.toDateTime(currentTime));
            testReservation02.setReservationVisitTime(TimeUtil.toDateTime(currentTime));
            testReservation02.setReservationType("매장");
            testReservation02.setReservationPeople(2);
            testReservation02.setReservationDiscount(1000);
            testReservation02.setReservationTotalPrice(10000);
            testReservation02.setReservationStatus("예약");

            ReservationDTO testReservation03 = new ReservationDTO();
            testReservation03.setMemberEmail("member_03@email.com");
            testReservation03.setRestaurantNo(1);
            testReservation03.setReservationTime(TimeUtil.toDateTime(currentTime));
            testReservation03.setReservationVisitTime(TimeUtil.toDateTime(currentTime));
            testReservation03.setReservationType("매장");
            testReservation03.setReservationPeople(2);
            testReservation03.setReservationDiscount(1000);
            testReservation03.setReservationTotalPrice(10000);
            testReservation03.setReservationStatus("예약");

            try {
                // 리뷰테스트를 위해 예약 데이터를 넣는 부분
                reservationRepository.insertReservation(testReservation01);
                reservationRepository.insertReservation(testReservation02);
                reservationRepository.insertReservation(testReservation03);

//                TODO: SelectAllReservation 고쳐지면 주석 부활
//                // 구현된 로직을 통해서 selectAllReservation() 메소드 실행
//                List<ReservationDTO> reservationList = reservationRepository.selectAllReservation();
//
//                // Assertion을 통한 테스트
//                Assertions.assertNotNull(reservationList); // 반환 실패 확인
//                Assertions.assertFalse(reservationList.isEmpty()); // 빈 배열 반환 확인
//                Assertions.assertTrue(reservationList.contains(testReservation01)); // 테스트 케이스 1번
//                Assertions.assertTrue(reservationList.contains(testReservation02)); // 테스트 케이스 2번
//                Assertions.assertTrue(reservationList.contains(testReservation03)); // 테스트 케이스 3번
//

                // Create test ReviewDTO objects
                ReviewDTO testReview01 = new ReviewDTO();
                testReview01.setMemberEmail("member_01@email.com");
                testReview01.setReviewContent("테스트 리뷰 내용 1");
                testReview01.setReviewStar(1);
                testReview01.setRestaurantNo(1);
                testReview01.setReviewTime(currentTime);
                testReview01.setReviewPhotoDir(photoDir);
                testReview01.setReservationNo(testReservation01.getReservationNo());

                ReviewDTO testReview02 = new ReviewDTO();
                testReview02.setMemberEmail("member_02@email.com");
                testReview02.setReviewContent("테스트 리뷰 내용 2");
                testReview02.setReviewStar(1);
                testReview02.setRestaurantNo(1);
                testReview02.setReviewTime(currentTime);
                testReview02.setReviewPhotoDir(photoDir);
                testReview02.setReservationNo(testReservation02.getReservationNo());


                ReviewDTO testReview03 = new ReviewDTO();
                testReview03.setMemberEmail("member_03@email.com");
                testReview03.setReviewContent("테스트 리뷰 내용 3");
                testReview03.setReviewStar(1);
                testReview03.setRestaurantNo(1);
                testReview03.setReviewTime(currentTime);
                testReview03.setReviewPhotoDir(photoDir);
                testReview03.setReservationNo(testReservation03.getReservationNo());

                try {
                    reviewRepository.insertReview(testReview01);
                    reviewRepository.insertReview(testReview02);
                    reviewRepository.insertReview(testReview03);

                    // 구현된 로직을 통해서 selectReviewByReviewNo() 메소드 실행
                    ReviewDTO review_retrieved_01 = reviewRepository.selectReviewByReviewNo(testReview01.getReviewNo());
                    ReviewDTO review_retrieved_02 = reviewRepository.selectReviewByReviewNo(testReview02.getReviewNo());
                    ReviewDTO review_retrieved_03 = reviewRepository.selectReviewByReviewNo(testReview03.getReviewNo());


                    // Assertion을 통한 테스트
                    Assertions.assertNotNull(review_retrieved_01); // 반환 실패 확인
                    Assertions.assertNotNull(review_retrieved_02); // 반환 실패 확인
                    Assertions.assertNotNull(review_retrieved_03); // 반환 실패 확인

                    Assertions.assertEquals(testReview01, review_retrieved_01); // 테스트 케이스 1번
                    Assertions.assertEquals(testReview02, review_retrieved_02); // 테스트 케이스 2번
                    Assertions.assertEquals(testReview03, review_retrieved_03); // 테스트 케이스 3번

                } catch (AddException e) {
                    e.printStackTrace();
                    Assertions.fail("Exception occurred: " + e.getMessage());
                } finally {
                    reviewRepository.deleteReview(testReview01.getReviewNo());
                    reviewRepository.deleteReview(testReview02.getReviewNo());
                    reviewRepository.deleteReview(testReview03.getReviewNo());
                }
            } catch (AddException | FindException e) {
                e.printStackTrace();
                Assertions.fail("Exception occurred: " + e.getMessage());
            } finally {

                reservationRepository.deleteReservation(testReservation01.getReservationNo());
                reservationRepository.deleteReservation(testReservation02.getReservationNo());
                reservationRepository.deleteReservation(testReservation03.getReservationNo());
            }
        } catch (AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        } finally {
            // Clean up by deleting the test member
            memberRepository.deleteMember(testMember01.getMemberEmail());
            memberRepository.deleteMember(testMember02.getMemberEmail());
            memberRepository.deleteMember(testMember03.getMemberEmail());
        }
    }

    @DisplayName("insertReview 레포 테스트")
    @Test
    void insertReview_shouldInsertReviewSuccessfully() throws RemoveException, ParseException {
        // select test와 동일로직
        // Set Time
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        Timestamp currentTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 10, 00, 00))).getTime());


        // S3 PhotoDir
        String photoDir = "https://bv-image.s3.ap-northeast-2.amazonaws.com/review/salad.jpg";


        // Create a test MemberDTO object
        MemberDTO testMember01 = new MemberDTO();
        testMember01.setMemberEmail("member_01@email.com");
        testMember01.setMemberName("John Doe");
        testMember01.setMemberPoint(100);

        MemberDTO testMember02 = new MemberDTO();
        testMember02.setMemberEmail("member_02@email.com");
        testMember02.setMemberName("John Doe");
        testMember02.setMemberPoint(100);

        MemberDTO testMember03 = new MemberDTO();
        testMember03.setMemberEmail("member_03@email.com");
        testMember03.setMemberName("John Doe");
        testMember03.setMemberPoint(100);

        try {
            // Call the insertMember() method
            memberRepository.insertMember(testMember01);
            memberRepository.insertMember(testMember02);
            memberRepository.insertMember(testMember03);


            // Assert that the member is added successfully by retrieving it and comparing the values
            MemberDTO retrievedMember01 = memberRepository.selectMemberByMemberEmail(testMember01.getMemberEmail());
            MemberDTO retrievedMember02 = memberRepository.selectMemberByMemberEmail(testMember02.getMemberEmail());
            MemberDTO retrievedMember03 = memberRepository.selectMemberByMemberEmail(testMember03.getMemberEmail());

            Assertions.assertEquals(testMember01.getMemberEmail(), retrievedMember01.getMemberEmail());
            Assertions.assertEquals(testMember02.getMemberEmail(), retrievedMember02.getMemberEmail());
            Assertions.assertEquals(testMember03.getMemberEmail(), retrievedMember03.getMemberEmail());


            // Create test Reservation Objects
            ReservationDTO testReservation01 = new ReservationDTO();
            testReservation01.setMemberEmail("member_01@email.com");
            testReservation01.setRestaurantNo(1);
            testReservation01.setReservationTime(TimeUtil.toDateTime(currentTime));
            testReservation01.setReservationVisitTime(TimeUtil.toDateTime(currentTime));
            testReservation01.setReservationType("매장");
            testReservation01.setReservationPeople(2);
            testReservation01.setReservationDiscount(1000);
            testReservation01.setReservationTotalPrice(10000);
            testReservation01.setReservationStatus("예약");

            ReservationDTO testReservation02 = new ReservationDTO();
            testReservation02.setMemberEmail("member_02@email.com");
            testReservation02.setRestaurantNo(1);
            testReservation02.setReservationTime(TimeUtil.toDateTime(currentTime));
            testReservation02.setReservationVisitTime(TimeUtil.toDateTime(currentTime));
            testReservation02.setReservationType("매장");
            testReservation02.setReservationPeople(2);
            testReservation02.setReservationDiscount(1000);
            testReservation02.setReservationTotalPrice(10000);
            testReservation02.setReservationStatus("예약");

            ReservationDTO testReservation03 = new ReservationDTO();
            testReservation03.setMemberEmail("member_03@email.com");
            testReservation03.setRestaurantNo(1);
            testReservation03.setReservationTime(TimeUtil.toDateTime(currentTime));
            testReservation03.setReservationVisitTime(TimeUtil.toDateTime(currentTime));
            testReservation03.setReservationType("매장");
            testReservation03.setReservationPeople(2);
            testReservation03.setReservationDiscount(1000);
            testReservation03.setReservationTotalPrice(10000);
            testReservation03.setReservationStatus("예약");

            try {
                // 리뷰테스트를 위해 예약 데이터를 넣는 부분
                reservationRepository.insertReservation(testReservation01);
                reservationRepository.insertReservation(testReservation02);
                reservationRepository.insertReservation(testReservation03);

//                TODO: SelectAllReservation 고쳐지면 주석 부활
//                // 구현된 로직을 통해서 selectAllReservation() 메소드 실행
//                List<ReservationDTO> reservationList = reservationRepository.selectAllReservation();
//
//                // Assertion을 통한 테스트
//                Assertions.assertNotNull(reservationList); // 반환 실패 확인
//                Assertions.assertFalse(reservationList.isEmpty()); // 빈 배열 반환 확인
//                Assertions.assertTrue(reservationList.contains(testReservation01)); // 테스트 케이스 1번
//                Assertions.assertTrue(reservationList.contains(testReservation02)); // 테스트 케이스 2번
//                Assertions.assertTrue(reservationList.contains(testReservation03)); // 테스트 케이스 3번
//

                // Create test ReviewDTO objects
                ReviewDTO testReview01 = new ReviewDTO();
                testReview01.setMemberEmail("member_01@email.com");
                testReview01.setReviewContent("테스트 리뷰 내용 1");
                testReview01.setReviewStar(1);
                testReview01.setRestaurantNo(1);
                testReview01.setReviewTime(currentTime);
                testReview01.setReviewPhotoDir(photoDir);
                testReview01.setReservationNo(testReservation01.getReservationNo());

                ReviewDTO testReview02 = new ReviewDTO();
                testReview02.setMemberEmail("member_02@email.com");
                testReview02.setReviewContent("테스트 리뷰 내용 2");
                testReview02.setReviewStar(1);
                testReview02.setRestaurantNo(2);
                testReview02.setReviewTime(currentTime);
                testReview02.setReviewPhotoDir(photoDir);
                testReview02.setReservationNo(testReservation02.getReservationNo());


                ReviewDTO testReview03 = new ReviewDTO();
                testReview03.setMemberEmail("member_03@email.com");
                testReview03.setReviewContent("테스트 리뷰 내용 3");
                testReview03.setReviewStar(1);
                testReview03.setRestaurantNo(3);
                testReview03.setReviewTime(currentTime);
                testReview03.setReviewPhotoDir(photoDir);
                testReview03.setReservationNo(testReservation03.getReservationNo());

                try {
                    reviewRepository.insertReview(testReview01);
                    reviewRepository.insertReview(testReview02);
                    reviewRepository.insertReview(testReview03);

                    // 구현된 로직을 통해서 selectAllReviewByRestaurantId() 메소드 실행
                    List<ReviewDTO> reviewList_01 = reviewRepository.selectAllReviewByRestaurantId(1);
                    List<ReviewDTO> reviewList_02 = reviewRepository.selectAllReviewByRestaurantId(2);
                    List<ReviewDTO> reviewList_03 = reviewRepository.selectAllReviewByRestaurantId(3);

                    // Assertion을 통한 테스트
                    Assertions.assertNotNull(reviewList_01); // 반환 실패 확인
                    Assertions.assertNotNull(reviewList_02); // 반환 실패 확인
                    Assertions.assertNotNull(reviewList_03); // 반환 실패 확인

                    Assertions.assertFalse(reviewList_01.isEmpty()); // 빈 배열 반환 확인
                    Assertions.assertFalse(reviewList_02.isEmpty()); // 빈 배열 반환 확인
                    Assertions.assertFalse(reviewList_03.isEmpty()); // 빈 배열 반환 확인

                    Assertions.assertTrue(reviewList_01.contains(testReview01)); // 테스트 케이스 1번
                    Assertions.assertTrue(reviewList_02.contains(testReview02)); // 테스트 케이스 2번
                    Assertions.assertTrue(reviewList_03.contains(testReview03)); // 테스트 케이스 3번

                } catch (AddException e) {
                    e.printStackTrace();
                    Assertions.fail("Exception occurred: " + e.getMessage());
                } finally {
                    reviewRepository.deleteReview(testReview01.getReviewNo());
                    reviewRepository.deleteReview(testReview02.getReviewNo());
                    reviewRepository.deleteReview(testReview03.getReviewNo());
                }
            } catch (AddException | FindException e) {
                e.printStackTrace();
                Assertions.fail("Exception occurred: " + e.getMessage());
            } finally {

                reservationRepository.deleteReservation(testReservation01.getReservationNo());
                reservationRepository.deleteReservation(testReservation02.getReservationNo());
                reservationRepository.deleteReservation(testReservation03.getReservationNo());
            }
        } catch (AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        } finally {
            // Clean up by deleting the test member
            memberRepository.deleteMember(testMember01.getMemberEmail());
            memberRepository.deleteMember(testMember02.getMemberEmail());
            memberRepository.deleteMember(testMember03.getMemberEmail());
        }
    }

    @DisplayName("deleteReview 레포 테스트")
    @Test
    void deleteReview_shouldRemoveReviewSuccessfully() throws ParseException, RemoveException {
        // Set Time
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        Timestamp currentTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 10, 00, 00))).getTime());


        // S3 PhotoDir
        String photoDir = "https://bv-image.s3.ap-northeast-2.amazonaws.com/review/salad.jpg";


        // Create a test MemberDTO object
        MemberDTO testMember01 = new MemberDTO();
        testMember01.setMemberEmail("member_01@email.com");
        testMember01.setMemberName("John Doe");
        testMember01.setMemberPoint(100);

        MemberDTO testMember02 = new MemberDTO();
        testMember02.setMemberEmail("member_02@email.com");
        testMember02.setMemberName("John Doe");
        testMember02.setMemberPoint(100);

        MemberDTO testMember03 = new MemberDTO();
        testMember03.setMemberEmail("member_03@email.com");
        testMember03.setMemberName("John Doe");
        testMember03.setMemberPoint(100);

        try {
            // Call the insertMember() method
            memberRepository.insertMember(testMember01);
            memberRepository.insertMember(testMember02);
            memberRepository.insertMember(testMember03);


            // Assert that the member is added successfully by retrieving it and comparing the values
            MemberDTO retrievedMember01 = memberRepository.selectMemberByMemberEmail(testMember01.getMemberEmail());
            MemberDTO retrievedMember02 = memberRepository.selectMemberByMemberEmail(testMember02.getMemberEmail());
            MemberDTO retrievedMember03 = memberRepository.selectMemberByMemberEmail(testMember03.getMemberEmail());

            Assertions.assertEquals(testMember01.getMemberEmail(), retrievedMember01.getMemberEmail());
            Assertions.assertEquals(testMember02.getMemberEmail(), retrievedMember02.getMemberEmail());
            Assertions.assertEquals(testMember03.getMemberEmail(), retrievedMember03.getMemberEmail());


            // Create test Reservation Objects
            ReservationDTO testReservation01 = new ReservationDTO();
            testReservation01.setMemberEmail("member_01@email.com");
            testReservation01.setRestaurantNo(1);
            testReservation01.setReservationTime(TimeUtil.toDateTime(currentTime));
            testReservation01.setReservationVisitTime(TimeUtil.toDateTime(currentTime));
            testReservation01.setReservationType("매장");
            testReservation01.setReservationPeople(2);
            testReservation01.setReservationDiscount(1000);
            testReservation01.setReservationTotalPrice(10000);
            testReservation01.setReservationStatus("예약");

            ReservationDTO testReservation02 = new ReservationDTO();
            testReservation02.setMemberEmail("member_02@email.com");
            testReservation02.setRestaurantNo(1);
            testReservation02.setReservationTime(TimeUtil.toDateTime(currentTime));
            testReservation02.setReservationVisitTime(TimeUtil.toDateTime(currentTime));
            testReservation02.setReservationType("매장");
            testReservation02.setReservationPeople(2);
            testReservation02.setReservationDiscount(1000);
            testReservation02.setReservationTotalPrice(10000);
            testReservation02.setReservationStatus("예약");

            ReservationDTO testReservation03 = new ReservationDTO();
            testReservation03.setMemberEmail("member_03@email.com");
            testReservation03.setRestaurantNo(1);
            testReservation03.setReservationTime(TimeUtil.toDateTime(currentTime));
            testReservation03.setReservationVisitTime(TimeUtil.toDateTime(currentTime));
            testReservation03.setReservationType("매장");
            testReservation03.setReservationPeople(2);
            testReservation03.setReservationDiscount(1000);
            testReservation03.setReservationTotalPrice(10000);
            testReservation03.setReservationStatus("예약");

            try {
                // 리뷰테스트를 위해 예약 데이터를 넣는 부분
                reservationRepository.insertReservation(testReservation01);
                reservationRepository.insertReservation(testReservation02);
                reservationRepository.insertReservation(testReservation03);

//                TODO: SelectAllReservation 고쳐지면 주석 부활
//                // 구현된 로직을 통해서 selectAllReservation() 메소드 실행
//                List<ReservationDTO> reservationList = reservationRepository.selectAllReservation();
//
//                // Assertion을 통한 테스트
//                Assertions.assertNotNull(reservationList); // 반환 실패 확인
//                Assertions.assertFalse(reservationList.isEmpty()); // 빈 배열 반환 확인
//                Assertions.assertTrue(reservationList.contains(testReservation01)); // 테스트 케이스 1번
//                Assertions.assertTrue(reservationList.contains(testReservation02)); // 테스트 케이스 2번
//                Assertions.assertTrue(reservationList.contains(testReservation03)); // 테스트 케이스 3번
//

                // Create test ReviewDTO objects
                ReviewDTO testReview01 = new ReviewDTO();
                testReview01.setMemberEmail("member_01@email.com");
                testReview01.setReviewContent("테스트 리뷰 내용 1");
                testReview01.setReviewStar(1);
                testReview01.setRestaurantNo(1);
                testReview01.setReviewTime(currentTime);
                testReview01.setReviewPhotoDir(photoDir);
                testReview01.setReservationNo(testReservation01.getReservationNo());

                ReviewDTO testReview02 = new ReviewDTO();
                testReview02.setMemberEmail("member_02@email.com");
                testReview02.setReviewContent("테스트 리뷰 내용 2");
                testReview02.setReviewStar(1);
                testReview02.setRestaurantNo(2);
                testReview02.setReviewTime(currentTime);
                testReview02.setReviewPhotoDir(photoDir);
                testReview02.setReservationNo(testReservation02.getReservationNo());


                ReviewDTO testReview03 = new ReviewDTO();
                testReview03.setMemberEmail("member_03@email.com");
                testReview03.setReviewContent("테스트 리뷰 내용 3");
                testReview03.setReviewStar(1);
                testReview03.setRestaurantNo(3);
                testReview03.setReviewTime(currentTime);
                testReview03.setReviewPhotoDir(photoDir);
                testReview03.setReservationNo(testReservation03.getReservationNo());

                try {
                    reviewRepository.insertReview(testReview01);
                    reviewRepository.insertReview(testReview02);
                    reviewRepository.insertReview(testReview03);

                    // 구현된 로직을 통해서 selectAllReviewByRestaurantId() 메소드 실행
                    List<ReviewDTO> reviewList_01 = reviewRepository.selectAllReviewByRestaurantId(1);
                    List<ReviewDTO> reviewList_02 = reviewRepository.selectAllReviewByRestaurantId(2);
                    List<ReviewDTO> reviewList_03 = reviewRepository.selectAllReviewByRestaurantId(3);

                    // Assertion을 통한 테스트
                    Assertions.assertNotNull(reviewList_01); // 반환 실패 확인
                    Assertions.assertNotNull(reviewList_02); // 반환 실패 확인
                    Assertions.assertNotNull(reviewList_03); // 반환 실패 확인

                    Assertions.assertFalse(reviewList_01.isEmpty()); // 빈 배열 반환 확인
                    Assertions.assertFalse(reviewList_02.isEmpty()); // 빈 배열 반환 확인
                    Assertions.assertFalse(reviewList_03.isEmpty()); // 빈 배열 반환 확인

                    Assertions.assertTrue(reviewList_01.contains(testReview01)); // 테스트 케이스 1번
                    Assertions.assertTrue(reviewList_02.contains(testReview02)); // 테스트 케이스 2번
                    Assertions.assertTrue(reviewList_03.contains(testReview03)); // 테스트 케이스 3번

                    try {
                        // Review delete 테스트
                        reviewRepository.deleteReview(testReview01.getReviewNo());
                        reviewRepository.deleteReview(testReview02.getReviewNo());
                        reviewRepository.deleteReview(testReview03.getReviewNo());

                        List<ReviewDTO> reviewList_afterDelete_01 = reviewRepository.selectAllReviewByRestaurantId(1);
                        List<ReviewDTO> reviewList_afterDelete_02 = reviewRepository.selectAllReviewByRestaurantId(2);
                        List<ReviewDTO> reviewList_afterDelete_03 = reviewRepository.selectAllReviewByRestaurantId(3);

                        Assertions.assertNotNull(reviewList_afterDelete_01); // 반환 실패 확인
                        Assertions.assertNotNull(reviewList_afterDelete_02); // 반환 실패 확인
                        Assertions.assertNotNull(reviewList_afterDelete_03); // 반환 실패 확인

                        Assertions.assertFalse(reviewList_afterDelete_01.contains(testReview01)); // 테스트 케이스 1번
                        Assertions.assertFalse(reviewList_afterDelete_02.contains(testReview02)); // 테스트 케이스 2번
                        Assertions.assertFalse(reviewList_afterDelete_03.contains(testReview03)); // 테스트 케이스 3번
                    } catch (RemoveException e) {
                        e.printStackTrace();
                        Assertions.fail("Exception occurred: " + e.getMessage());
                    }

                } catch (AddException e) {
                    e.printStackTrace();
                    Assertions.fail("Exception occurred: " + e.getMessage());
                } finally {
                    reviewRepository.deleteReview(testReview01.getReviewNo());
                    reviewRepository.deleteReview(testReview02.getReviewNo());
                    reviewRepository.deleteReview(testReview03.getReviewNo());
                }
            } catch (AddException | FindException e) {
                e.printStackTrace();
                Assertions.fail("Exception occurred: " + e.getMessage());
            } finally {

                reservationRepository.deleteReservation(testReservation01.getReservationNo());
                reservationRepository.deleteReservation(testReservation02.getReservationNo());
                reservationRepository.deleteReservation(testReservation03.getReservationNo());
            }
        } catch (AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        } finally {
            // Clean up by deleting the test member
            memberRepository.deleteMember(testMember01.getMemberEmail());
            memberRepository.deleteMember(testMember02.getMemberEmail());
            memberRepository.deleteMember(testMember03.getMemberEmail());
        }
    }

    @DisplayName("updateReview 레포 테스트")
    @Test
    void updateReview_shouldUpdateReviewSuccessfully() throws RemoveException, ParseException {
// Set Time
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        Timestamp currentTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 10, 00, 00))).getTime());


        // S3 PhotoDir
        String photoDir = "https://bv-image.s3.ap-northeast-2.amazonaws.com/review/salad.jpg";


        // Create a test MemberDTO object
        MemberDTO testMember01 = new MemberDTO();
        testMember01.setMemberEmail("member_01@email.com");
        testMember01.setMemberName("John Doe");
        testMember01.setMemberPoint(100);

        MemberDTO testMember02 = new MemberDTO();
        testMember02.setMemberEmail("member_02@email.com");
        testMember02.setMemberName("John Doe");
        testMember02.setMemberPoint(100);

        MemberDTO testMember03 = new MemberDTO();
        testMember03.setMemberEmail("member_03@email.com");
        testMember03.setMemberName("John Doe");
        testMember03.setMemberPoint(100);

        try {
            // Call the insertMember() method
            memberRepository.insertMember(testMember01);
            memberRepository.insertMember(testMember02);
            memberRepository.insertMember(testMember03);


            // Assert that the member is added successfully by retrieving it and comparing the values
            MemberDTO retrievedMember01 = memberRepository.selectMemberByMemberEmail(testMember01.getMemberEmail());
            MemberDTO retrievedMember02 = memberRepository.selectMemberByMemberEmail(testMember02.getMemberEmail());
            MemberDTO retrievedMember03 = memberRepository.selectMemberByMemberEmail(testMember03.getMemberEmail());

            Assertions.assertEquals(testMember01.getMemberEmail(), retrievedMember01.getMemberEmail());
            Assertions.assertEquals(testMember02.getMemberEmail(), retrievedMember02.getMemberEmail());
            Assertions.assertEquals(testMember03.getMemberEmail(), retrievedMember03.getMemberEmail());


            // Create test Reservation Objects
            ReservationDTO testReservation01 = new ReservationDTO();
            testReservation01.setMemberEmail("member_01@email.com");
            testReservation01.setRestaurantNo(1);
            testReservation01.setReservationTime(TimeUtil.toDateTime(currentTime));
            testReservation01.setReservationVisitTime(TimeUtil.toDateTime(currentTime));
            testReservation01.setReservationType("매장");
            testReservation01.setReservationPeople(2);
            testReservation01.setReservationDiscount(1000);
            testReservation01.setReservationTotalPrice(10000);
            testReservation01.setReservationStatus("예약");

            ReservationDTO testReservation02 = new ReservationDTO();
            testReservation02.setMemberEmail("member_02@email.com");
            testReservation02.setRestaurantNo(1);
            testReservation02.setReservationTime(TimeUtil.toDateTime(currentTime));
            testReservation02.setReservationVisitTime(TimeUtil.toDateTime(currentTime));
            testReservation02.setReservationType("매장");
            testReservation02.setReservationPeople(2);
            testReservation02.setReservationDiscount(1000);
            testReservation02.setReservationTotalPrice(10000);
            testReservation02.setReservationStatus("예약");

            ReservationDTO testReservation03 = new ReservationDTO();
            testReservation03.setMemberEmail("member_03@email.com");
            testReservation03.setRestaurantNo(1);
            testReservation03.setReservationTime(TimeUtil.toDateTime(currentTime));
            testReservation03.setReservationVisitTime(TimeUtil.toDateTime(currentTime));
            testReservation03.setReservationType("매장");
            testReservation03.setReservationPeople(2);
            testReservation03.setReservationDiscount(1000);
            testReservation03.setReservationTotalPrice(10000);
            testReservation03.setReservationStatus("예약");

            try {
                // 리뷰테스트를 위해 예약 데이터를 넣는 부분
                reservationRepository.insertReservation(testReservation01);
                reservationRepository.insertReservation(testReservation02);
                reservationRepository.insertReservation(testReservation03);

//                TODO: SelectAllReservation 고쳐지면 주석 부활
//                // 구현된 로직을 통해서 selectAllReservation() 메소드 실행
//                List<ReservationDTO> reservationList = reservationRepository.selectAllReservation();
//
//                // Assertion을 통한 테스트
//                Assertions.assertNotNull(reservationList); // 반환 실패 확인
//                Assertions.assertFalse(reservationList.isEmpty()); // 빈 배열 반환 확인
//                Assertions.assertTrue(reservationList.contains(testReservation01)); // 테스트 케이스 1번
//                Assertions.assertTrue(reservationList.contains(testReservation02)); // 테스트 케이스 2번
//                Assertions.assertTrue(reservationList.contains(testReservation03)); // 테스트 케이스 3번
//

                // Create test ReviewDTO objects
                ReviewDTO testReview01 = new ReviewDTO();
                testReview01.setMemberEmail("member_01@email.com");
                testReview01.setReviewContent("테스트 리뷰 내용 1");
                testReview01.setReviewStar(1);
                testReview01.setRestaurantNo(1);
                testReview01.setReviewTime(currentTime);
                testReview01.setReviewPhotoDir(photoDir);
                testReview01.setReservationNo(testReservation01.getReservationNo());

                ReviewDTO testReview02 = new ReviewDTO();
                testReview02.setMemberEmail("member_02@email.com");
                testReview02.setReviewContent("테스트 리뷰 내용 2");
                testReview02.setReviewStar(1);
                testReview02.setRestaurantNo(2);
                testReview02.setReviewTime(currentTime);
                testReview02.setReviewPhotoDir(photoDir);
                testReview02.setReservationNo(testReservation02.getReservationNo());


                ReviewDTO testReview03 = new ReviewDTO();
                testReview03.setMemberEmail("member_03@email.com");
                testReview03.setReviewContent("테스트 리뷰 내용 3");
                testReview03.setReviewStar(1);
                testReview03.setRestaurantNo(3);
                testReview03.setReviewTime(currentTime);
                testReview03.setReviewPhotoDir(photoDir);
                testReview03.setReservationNo(testReservation03.getReservationNo());

                try {
                    reviewRepository.insertReview(testReview01);
                    reviewRepository.insertReview(testReview02);
                    reviewRepository.insertReview(testReview03);

                    // 구현된 로직을 통해서 selectAllReviewByRestaurantId() 메소드 실행
                    List<ReviewDTO> reviewList_01 = reviewRepository.selectAllReviewByRestaurantId(1);
                    List<ReviewDTO> reviewList_02 = reviewRepository.selectAllReviewByRestaurantId(2);
                    List<ReviewDTO> reviewList_03 = reviewRepository.selectAllReviewByRestaurantId(3);

                    // Assertion을 통한 테스트
                    Assertions.assertNotNull(reviewList_01); // 반환 실패 확인
                    Assertions.assertNotNull(reviewList_02); // 반환 실패 확인
                    Assertions.assertNotNull(reviewList_03); // 반환 실패 확인

                    Assertions.assertFalse(reviewList_01.isEmpty()); // 빈 배열 반환 확인
                    Assertions.assertFalse(reviewList_02.isEmpty()); // 빈 배열 반환 확인
                    Assertions.assertFalse(reviewList_03.isEmpty()); // 빈 배열 반환 확인

                    Assertions.assertTrue(reviewList_01.contains(testReview01)); // 테스트 케이스 1번
                    Assertions.assertTrue(reviewList_02.contains(testReview02)); // 테스트 케이스 2번
                    Assertions.assertTrue(reviewList_03.contains(testReview03)); // 테스트 케이스 3번

                    try {
                        // Review update 테스트
                        // update ReviewInfo 생성
                        ReviewDTO reviewDTO_toUpdate_01 = new ReviewDTO();
                        reviewDTO_toUpdate_01.setMemberEmail("member_01@email.com");
                        reviewDTO_toUpdate_01.setReviewContent("테스트 리뷰 내용 1 updated");
                        reviewDTO_toUpdate_01.setReviewStar(1);
                        reviewDTO_toUpdate_01.setRestaurantNo(1);
                        reviewDTO_toUpdate_01.setReviewTime(currentTime);
                        reviewDTO_toUpdate_01.setReviewPhotoDir(photoDir);
                        reviewDTO_toUpdate_01.setReservationNo(testReservation01.getReservationNo());

                        ReviewDTO reviewDTO_toUpdate_02 = new ReviewDTO();
                        reviewDTO_toUpdate_02.setMemberEmail("member_02@email.com");
                        reviewDTO_toUpdate_02.setReviewContent("테스트 리뷰 내용 2 updated");
                        reviewDTO_toUpdate_02.setReviewStar(1);
                        reviewDTO_toUpdate_02.setRestaurantNo(2);
                        reviewDTO_toUpdate_02.setReviewTime(currentTime);
                        reviewDTO_toUpdate_02.setReviewPhotoDir(photoDir);
                        reviewDTO_toUpdate_02.setReservationNo(testReservation02.getReservationNo());

                        ReviewDTO reviewDTO_toUpdate_03 = new ReviewDTO();
                        reviewDTO_toUpdate_03.setMemberEmail("member_03@email.com");
                        reviewDTO_toUpdate_03.setReviewContent("테스트 리뷰 내용 3 updated");
                        reviewDTO_toUpdate_03.setReviewStar(1);
                        reviewDTO_toUpdate_03.setRestaurantNo(3);
                        reviewDTO_toUpdate_03.setReviewTime(currentTime);
                        reviewDTO_toUpdate_03.setReviewPhotoDir(photoDir);
                        reviewDTO_toUpdate_03.setReservationNo(testReservation03.getReservationNo());

                        // update 실행
                        reviewRepository.updateReview(testReview01.getReviewNo(), reviewDTO_toUpdate_01);
                        reviewRepository.updateReview(testReview02.getReviewNo(), reviewDTO_toUpdate_02);
                        reviewRepository.updateReview(testReview03.getReviewNo(), reviewDTO_toUpdate_03);

                        ReviewDTO review_afterUpdate_01 = reviewRepository.selectReviewByReviewNo(testReview01.getReviewNo());
                        ReviewDTO review_afterUpdate_02 = reviewRepository.selectReviewByReviewNo(testReview02.getReviewNo());
                        ReviewDTO review_afterUpdate_03 = reviewRepository.selectReviewByReviewNo(testReview03.getReviewNo());

                        Assertions.assertNotNull(review_afterUpdate_01); // 반환 실패 확인
                        Assertions.assertNotNull(review_afterUpdate_02); // 반환 실패 확인
                        Assertions.assertNotNull(review_afterUpdate_03); // 반환 실패 확인


                        Assertions.assertEquals(reviewDTO_toUpdate_01.getReviewContent(), review_afterUpdate_01.getReviewContent()); // 테스트 케이스 1번
                        Assertions.assertEquals(reviewDTO_toUpdate_02.getReviewContent(), review_afterUpdate_02.getReviewContent()); // 테스트 케이스 2번
                        Assertions.assertEquals(reviewDTO_toUpdate_03.getReviewContent(), review_afterUpdate_03.getReviewContent()); // 테스트 케이스 3번
                    } catch (ModifyException e) {
                        e.printStackTrace();
                        Assertions.fail("Exception occurred: " + e.getMessage());
                    }

                } catch (AddException e) {
                    e.printStackTrace();
                    Assertions.fail("Exception occurred: " + e.getMessage());
                } finally {
                    reviewRepository.deleteReview(testReview01.getReviewNo());
                    reviewRepository.deleteReview(testReview02.getReviewNo());
                    reviewRepository.deleteReview(testReview03.getReviewNo());
                }
            } catch (AddException | FindException e) {
                e.printStackTrace();
                Assertions.fail("Exception occurred: " + e.getMessage());
            } finally {

                reservationRepository.deleteReservation(testReservation01.getReservationNo());
                reservationRepository.deleteReservation(testReservation02.getReservationNo());
                reservationRepository.deleteReservation(testReservation03.getReservationNo());
            }
        } catch (AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        } finally {
            // Clean up by deleting the test member
            memberRepository.deleteMember(testMember01.getMemberEmail());
            memberRepository.deleteMember(testMember02.getMemberEmail());
            memberRepository.deleteMember(testMember03.getMemberEmail());
        }
    }
}