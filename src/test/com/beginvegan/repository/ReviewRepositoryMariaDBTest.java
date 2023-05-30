package com.beginvegan.repository;

import com.beginvegan.dto.MemberDTO;
import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.dto.ReviewDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
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

                // 구현된 로직을 통해서 selectAllReservation() 메소드 실행
                List<ReservationDTO> reservationList = reservationRepository.selectAllReservation();

                // Assertion을 통한 테스트
                Assertions.assertNotNull(reservationList); // 반환 실패 확인
                Assertions.assertFalse(reservationList.isEmpty()); // 빈 배열 반환 확인
                Assertions.assertTrue(reservationList.contains(testReservation01)); // 테스트 케이스 1번
                Assertions.assertTrue(reservationList.contains(testReservation02)); // 테스트 케이스 2번
                Assertions.assertTrue(reservationList.contains(testReservation03)); // 테스트 케이스 3번


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
            System.out.println("!@#");

            System.out.println(testMember01.getMemberEmail());
            System.out.println(testMember02.getMemberEmail());
            System.out.println(testMember03.getMemberEmail());

            memberRepository.deleteMember(testMember01.getMemberEmail());
            memberRepository.deleteMember(testMember02.getMemberEmail());
            memberRepository.deleteMember(testMember03.getMemberEmail());
        }
    }

    @Test
    void selectAllReviewByRestaurantId() {
    }

    @Test
    void insertReview() {
    }

    @Test
    void deleteReview() {
    }

    @Test
    void updateReview() {
    }
}