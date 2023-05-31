package com.beginvegan.repository;

import com.beginvegan.dto.BookmarkDTO;
import com.beginvegan.dto.MemberDTO;
import com.beginvegan.dto.PointDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.util.TimeUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemberRepositoryMariaDBTest {

    @Autowired
    private MemberRepositoryMariaDB memberRepository;

    private final String TEST_MEMBER_EMAIL = "example@email.com";
    private final String TEST_MEMBER_NAME = "TestMan";
    private  final int TEST_MEMBER_POINT = 100;
    @BeforeAll
    void setTestData() throws AddException {
        // Create a test MemberDTO object
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail(TEST_MEMBER_EMAIL);
        memberDTO.setMemberName(TEST_MEMBER_NAME);
        memberDTO.setMemberPoint(TEST_MEMBER_POINT);

        memberRepository.insertMember(memberDTO);
    }

    @AfterAll
    void clearTestData() throws RemoveException {
        memberRepository.deleteMember(TEST_MEMBER_EMAIL);
    }


    @DisplayName("insertMember 메소드 테스트")
    @Test
    void insertMember_shouldAddMemberSuccessfully() {
        // Create a test MemberDTO object
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("insertTest@email.com");
        memberDTO.setMemberName("insertedMan");
        memberDTO.setMemberPoint(100);

        try {
            // Call the insertMember() method
            memberRepository.insertMember(memberDTO);

            // Assert that the member is added successfully by retrieving it and comparing the values
            MemberDTO retrievedMember = memberRepository.selectMemberByMemberEmail(memberDTO.getMemberEmail());
            Assertions.assertEquals(memberDTO.getMemberEmail(), retrievedMember.getMemberEmail());
            Assertions.assertEquals(memberDTO.getMemberName(), retrievedMember.getMemberName());
            Assertions.assertEquals(memberDTO.getMemberPoint(), retrievedMember.getMemberPoint());

            // Clean up by deleting the test member
            memberRepository.deleteMember(memberDTO.getMemberEmail());
        } catch (AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("updateMember 메소드 테스트")
    @Test
    void updateMember_shouldUpdateMemberSuccessfully() {
        // Create a update test MemberDTO info object
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail(TEST_MEMBER_EMAIL);
        memberDTO.setMemberName("UpdatedMan");
        memberDTO.setMemberPoint(200);

        try {
            // Update the member's information
            memberRepository.updateMember(memberDTO);

            // Retrieve the updated member from the database
            MemberDTO updatedMember = memberRepository.selectMemberByMemberEmail(TEST_MEMBER_EMAIL);

            // Assert that the member's information has been successfully updated
            Assertions.assertEquals(memberDTO.getMemberEmail(), updatedMember.getMemberEmail());
            Assertions.assertEquals(memberDTO.getMemberName(), updatedMember.getMemberName());
            Assertions.assertEquals(memberDTO.getMemberPoint(), updatedMember.getMemberPoint());

            // Rollback the member's information
            memberDTO.setMemberName(TEST_MEMBER_NAME);
            memberDTO.setMemberPoint(TEST_MEMBER_POINT);
            memberRepository.updateMember(memberDTO);
        } catch (FindException | ModifyException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("deleteMember 메소드 테스트")
    @Test
    void deleteMember_shouldRemoveMemberSuccessfully() {
        // Create a test MemberDTO object
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("deleteTEST@example.com");
        memberDTO.setMemberName("deleteMan");
        memberDTO.setMemberPoint(100);

        try {
            // Insert the member into the database
            memberRepository.insertMember(memberDTO);

            // Delete the member from the database
            memberRepository.deleteMember(memberDTO.getMemberEmail());

            // Try to retrieve the deleted member
            MemberDTO deletedMember = null;
            try {
                deletedMember = memberRepository.selectMemberByMemberEmail(memberDTO.getMemberEmail());
            } catch (FindException e) {
                Assertions.assertEquals("해당 Email과 일치하는 멤버 정보가 없습니다.", e.getMessage());
            }
            // Assert that the deleted member is not found
            Assertions.assertNull(deletedMember);
        } catch (AddException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("selectMemberByMemberEmail 메소드 테스트")
    @Test
    void selectMemberByMemberEmail_shouldReturnCorrectMember() {
        try {
            // Assert that the member is added successfully by retrieving it and comparing the values
            MemberDTO retrievedMember = memberRepository.selectMemberByMemberEmail(TEST_MEMBER_EMAIL);
            Assertions.assertEquals(TEST_MEMBER_EMAIL, retrievedMember.getMemberEmail());
            Assertions.assertEquals(TEST_MEMBER_NAME, retrievedMember.getMemberName());
            Assertions.assertEquals(TEST_MEMBER_POINT, retrievedMember.getMemberPoint());
        } catch (FindException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("insertPoint 메소드 테스트")
    @Test
    void insertPoint_shouldAddPointSuccessfully() throws Exception {
        // Create a test PointDTO object
        PointDTO pointDTO = new PointDTO();
        pointDTO.setMemberEmail(TEST_MEMBER_EMAIL);
        pointDTO.setPointDiv("Purchase");
        pointDTO.setPointTime(TimeUtil.toDateTime(new Date()));
        pointDTO.setPointChange(50);
        pointDTO.setPointResult(150);

        try {
            // Insert the point into the database
            memberRepository.insertPoint(pointDTO);

            // Retrieve the inserted point using the selectPointTEST method
            PointDTO selectedPoint = memberRepository.selectPointTEST(pointDTO);

            // Assert that the selected point is not null and matches the inserted point
            Assertions.assertNotNull(selectedPoint);
            Assertions.assertEquals(pointDTO, selectedPoint);

            // Clean up by deleting the test point
            memberRepository.deletePointTEST(pointDTO.getMemberEmail(), pointDTO.getPointTime());
        } catch (AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("selectAllPointsByMemberEmail 메소드 테스트")
    @Test
    void selectAllPointsByMemberEmail_shouldReturnCorrectPointList() throws Exception {
        // Create test PointDTO objects
        PointDTO pointDTO1 = new PointDTO();
        pointDTO1.setMemberEmail(TEST_MEMBER_EMAIL);
        pointDTO1.setPointDiv("Purchase");
        pointDTO1.setPointTime(TimeUtil.toDateTime(new Date()));
        pointDTO1.setPointChange(50);
        pointDTO1.setPointResult(150);

        PointDTO pointDTO2 = new PointDTO();
        pointDTO2.setMemberEmail(TEST_MEMBER_EMAIL);
        pointDTO2.setPointDiv("Purchase");
        pointDTO2.setPointTime(TimeUtil.toDateTime(new Date()));
        pointDTO2.setPointChange(50);
        pointDTO2.setPointResult(200);

        PointDTO pointDTO3 = new PointDTO();
        pointDTO3.setMemberEmail(TEST_MEMBER_EMAIL);
        pointDTO3.setPointDiv("Purchase");
        pointDTO3.setPointTime(TimeUtil.toDateTime(new Date()));
        pointDTO3.setPointChange(150);
        pointDTO3.setPointResult(250);
        // Set other properties of the PointDTOs

        try {
            // Insert the points into the database
            memberRepository.insertPoint(pointDTO1);
            memberRepository.insertPoint(pointDTO2);
            memberRepository.insertPoint(pointDTO3);

            // Retrieve all points for the member
            List<PointDTO> pointList = memberRepository.selectAllPointsByMemberEmail(pointDTO1.getMemberEmail());

            // Assert that the retrieved point list is not null and contains the inserted points
            Assertions.assertNotNull(pointList);
            Assertions.assertFalse(pointList.isEmpty());
            Assertions.assertTrue(pointList.contains(pointDTO1));
            Assertions.assertTrue(pointList.contains(pointDTO2));
            Assertions.assertTrue(pointList.contains(pointDTO3));

            // Clean up by deleting the test points
            memberRepository.deletePointTEST(pointDTO1.getMemberEmail(), pointDTO1.getPointTime());
            memberRepository.deletePointTEST(pointDTO2.getMemberEmail(), pointDTO2.getPointTime());
            memberRepository.deletePointTEST(pointDTO3.getMemberEmail(), pointDTO3.getPointTime());
        } catch (AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }


    @DisplayName("insertBookmark 메소드 테스트")
    @Test
    void insertBookmark_shouldAddBookmarkSuccessfully() {
        // Create a test BookmarkDTO object
        BookmarkDTO bookmarkDTO = new BookmarkDTO();
        bookmarkDTO.setRestaurantNo(1);
        bookmarkDTO.setMemberEmail(TEST_MEMBER_EMAIL);
        // Set other properties of the BookmarkDTO

        try {
            // Insert the bookmark into the database
            memberRepository.insertBookmark(bookmarkDTO.getRestaurantNo(), bookmarkDTO.getMemberEmail());

            // Retrieve the inserted bookmark using the selectBookmarkTEST method
            BookmarkDTO selectedBookmark = memberRepository.selectBookmarkTEST(bookmarkDTO.getRestaurantNo(), bookmarkDTO.getMemberEmail());

            // Assert that the selected bookmark is not null and matches the inserted bookmark
            Assertions.assertNotNull(selectedBookmark);
            Assertions.assertEquals(bookmarkDTO, selectedBookmark);

            // Clean up by deleting the test bookmark
            memberRepository.deleteBookmark(bookmarkDTO.getRestaurantNo(), bookmarkDTO.getMemberEmail());
        } catch (AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("deleteBookmark 메소드 테스트")
    @Test
    void deleteBookmark_shouldRemoveBookmarkSuccessfully() {
        // Create a test BookmarkDTO object
        BookmarkDTO bookmarkDTO = new BookmarkDTO();
        bookmarkDTO.setRestaurantNo(1);
        bookmarkDTO.setMemberEmail(TEST_MEMBER_EMAIL);
        try {
            // Insert the bookmark into the database
            memberRepository.insertBookmark(bookmarkDTO.getRestaurantNo(), bookmarkDTO.getMemberEmail());

            // Delete the bookmark
            memberRepository.deleteBookmark(bookmarkDTO.getRestaurantNo(), bookmarkDTO.getMemberEmail());

            // Try to retrieve the deleted bookmark
            BookmarkDTO selectedBookmark = memberRepository.selectBookmarkTEST(bookmarkDTO.getRestaurantNo(), bookmarkDTO.getMemberEmail());

            // Assert that the selected bookmark is null after deletion
            Assertions.assertNull(selectedBookmark);
        } catch (AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("selectAllBookmarkByMemberEmail 메소드 테스트")
    @Test
    void selectAllBookmarkByMemberEmail_shouldReturnCorrectBookmarkList() {
        // Create test BookmarkDTO objects
        BookmarkDTO bookmarkDTO1 = new BookmarkDTO();
        bookmarkDTO1.setRestaurantNo(1);
        bookmarkDTO1.setMemberEmail(TEST_MEMBER_EMAIL);
        // Set other properties of bookmarkDTO1

        BookmarkDTO bookmarkDTO2 = new BookmarkDTO();
        bookmarkDTO2.setRestaurantNo(2);
        bookmarkDTO2.setMemberEmail(TEST_MEMBER_EMAIL);
        // Set other properties of bookmarkDTO2

        BookmarkDTO bookmarkDTO3 = new BookmarkDTO();
        bookmarkDTO3.setRestaurantNo(3);
        bookmarkDTO3.setMemberEmail(TEST_MEMBER_EMAIL);
        // Set other properties of bookmarkDTO3

        try {
            // Insert the bookmarks into the database
            memberRepository.insertBookmark(bookmarkDTO1.getRestaurantNo(), bookmarkDTO1.getMemberEmail());
            memberRepository.insertBookmark(bookmarkDTO2.getRestaurantNo(), bookmarkDTO3.getMemberEmail());
            memberRepository.insertBookmark(bookmarkDTO3.getRestaurantNo(), bookmarkDTO3.getMemberEmail());

            // Retrieve all bookmarks for the member
            List<BookmarkDTO> bookmarkList = memberRepository.selectAllBookmarkByMemberEmail(TEST_MEMBER_EMAIL);

            // Assert that the retrieved bookmark list is not null and contains the inserted bookmarks
            Assertions.assertNotNull(bookmarkList);
            Assertions.assertFalse(bookmarkList.isEmpty());
            Assertions.assertTrue(bookmarkList.contains(bookmarkDTO1));
            Assertions.assertTrue(bookmarkList.contains(bookmarkDTO2));
            Assertions.assertTrue(bookmarkList.contains(bookmarkDTO3));

            // Clean up by deleting the test bookmarks
            memberRepository.deleteBookmark(bookmarkDTO1.getRestaurantNo(), bookmarkDTO1.getMemberEmail());
            memberRepository.deleteBookmark(bookmarkDTO2.getRestaurantNo(), bookmarkDTO2.getMemberEmail());
            memberRepository.deleteBookmark(bookmarkDTO3.getRestaurantNo(), bookmarkDTO3.getMemberEmail());
        } catch (AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

}
