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
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemberRepositoryMariaDBTest {

    @Autowired
    private MemberRepositoryMariaDB memberRepository;

    private final String TEST_MEMBER_EMAIL = "Test@email.com";
    private final String TEST_MEMBER_NAME = "TestMan";
    private  final int TEST_MEMBER_POINT = 100;
    private final String TEST_MEMBER_ROLE = "TESTER";
    @BeforeAll
    void setTestData() throws AddException {
        // Create a test MemberDTO object
        MemberDTO testMember = new MemberDTO();
        testMember.setMemberEmail(TEST_MEMBER_EMAIL);
        testMember.setMemberName(TEST_MEMBER_NAME);
        testMember.setMemberPoint(TEST_MEMBER_POINT);
        testMember.setMemberRole(TEST_MEMBER_ROLE);

        memberRepository.insertMember(testMember);
    }

    @AfterAll
    void clearTestData() throws RemoveException {
        memberRepository.deleteMember(TEST_MEMBER_EMAIL);
    }

    @DisplayName("insertMember 메소드 테스트")
    @Transactional
    @Test
    void insertMemberTest() {
        // Create a test MemberDTO object
        MemberDTO insertTestMember = new MemberDTO();
        insertTestMember.setMemberEmail("insertTest@email.com");
        insertTestMember.setMemberName("insertedMan");
        insertTestMember.setMemberPoint(TEST_MEMBER_POINT);
        insertTestMember.setMemberRole(TEST_MEMBER_ROLE);

        try {
            // Call the insertMember() method
            memberRepository.insertMember(insertTestMember);

            // Assert that the member is added successfully by retrieving it and comparing the values
            MemberDTO retrievedMember = memberRepository.selectMemberByMemberEmail(insertTestMember.getMemberEmail());
            Assertions.assertEquals(insertTestMember, retrievedMember);

            Assertions.assertThrows(AddException.class, () -> {
                memberRepository.insertMember(insertTestMember);
            });
        } catch (AddException | FindException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("updateMember 메소드 테스트")
    @Transactional
    @Test
    void updateMemberTest() {
        // Create a update test MemberDTO info object
        MemberDTO updateTestmember = new MemberDTO();
        updateTestmember.setMemberEmail(TEST_MEMBER_EMAIL);
        updateTestmember.setMemberName("UpdatedMan");
        updateTestmember.setMemberPoint(200);
        updateTestmember.setMemberRole(TEST_MEMBER_ROLE);

        try {
            // Update the member's information
            memberRepository.updateMember(updateTestmember);

            // Retrieve the updated member from the database
            MemberDTO updatedMember = memberRepository.selectMemberByMemberEmail(TEST_MEMBER_EMAIL);

            // Assert that the member's information has been successfully updated
            Assertions.assertEquals(updateTestmember, updatedMember);

            updateTestmember.setMemberEmail("");
            Assertions.assertThrows(ModifyException.class, () -> {
                memberRepository.updateMember(updateTestmember);
            });
        } catch (FindException | ModifyException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("deleteMember 메소드 테스트")
    @Transactional
    @Test
    void deleteMemberTest() {
        // Create a test MemberDTO object
        MemberDTO deleteTestMember = new MemberDTO();
        deleteTestMember.setMemberEmail("deleteTEST@example.com");
        deleteTestMember.setMemberName("deleteMan");
        deleteTestMember.setMemberPoint(TEST_MEMBER_POINT);
        deleteTestMember.setMemberRole(TEST_MEMBER_ROLE);

        try {
            // Insert the member into the database
            memberRepository.insertMember(deleteTestMember);

            // Delete the member from the database
            memberRepository.deleteMember(deleteTestMember.getMemberEmail());

            // Try to retrieve the deleted member
            MemberDTO deletedMember = null;
            try {
                deletedMember = memberRepository.selectMemberByMemberEmail(deleteTestMember.getMemberEmail());
            } catch (FindException e) {
                Assertions.assertEquals("해당 Email과 일치하는 멤버 정보가 없습니다.", e.getMessage());
            }
            // Assert that the deleted member is not found
            Assertions.assertNull(deletedMember);

            Assertions.assertThrows(RemoveException.class, () -> {
                memberRepository.deleteMember("");
            });
        } catch (AddException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("selectMemberByMemberEmail 메소드 테스트")
    @Transactional
    @Test
    void selectMemberByMemberEmailTest() {
        try {
            // Assert that the member is added successfully by retrieving it and comparing the values
            MemberDTO retrievedMember = memberRepository.selectMemberByMemberEmail(TEST_MEMBER_EMAIL);
            Assertions.assertEquals(TEST_MEMBER_EMAIL, retrievedMember.getMemberEmail());
            Assertions.assertEquals(TEST_MEMBER_NAME, retrievedMember.getMemberName());
            Assertions.assertEquals(TEST_MEMBER_POINT, retrievedMember.getMemberPoint());
            Assertions.assertEquals(TEST_MEMBER_ROLE, retrievedMember.getMemberRole());
        } catch (FindException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("insertPoint 메소드 테스트")
    @Transactional
    @Test
    void insertPointTest() throws Exception {
        // Create a test PointDTO object
        PointDTO insertTestPoint = new PointDTO();
        insertTestPoint.setMemberEmail(TEST_MEMBER_EMAIL);
        insertTestPoint.setPointDiv("Purchase");
        insertTestPoint.setPointTime(TimeUtil.toDateTime(new Date()));
        insertTestPoint.setPointChange(50);
        insertTestPoint.setPointResult(150);

        try {
            // Insert the point into the database
            memberRepository.insertPoint(insertTestPoint);

            // Retrieve the inserted point using the selectPointTEST method
            PointDTO selectedPoint = memberRepository.selectPointTEST(insertTestPoint);

            // Assert that the selected point is not null and matches the inserted point
            Assertions.assertNotNull(selectedPoint);
            Assertions.assertEquals(insertTestPoint, selectedPoint);

            // Clean up by deleting the test point
            memberRepository.deletePointTEST(insertTestPoint.getMemberEmail(), insertTestPoint.getPointTime());

            insertTestPoint.setMemberEmail("");
            Assertions.assertThrows(AddException.class, () -> {
                memberRepository.insertPoint(insertTestPoint);
            });
        } catch (AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("selectAllPointsByMemberEmail 메소드 테스트")
    @Transactional
    @Test
    void selectAllPointsByMemberEmailTest() throws Exception {
        // Create test PointDTO objects
        PointDTO point1 = new PointDTO();
        point1.setMemberEmail(TEST_MEMBER_EMAIL);
        point1.setPointDiv("Purchase");
        point1.setPointTime(TimeUtil.toDateTime(new Date()));
        point1.setPointChange(50);
        point1.setPointResult(150);

        PointDTO point2 = new PointDTO();
        point2.setMemberEmail(TEST_MEMBER_EMAIL);
        point2.setPointDiv("Purchase");
        point2.setPointTime(TimeUtil.toDateTime(new Date()));
        point2.setPointChange(50);
        point2.setPointResult(200);

        PointDTO point3 = new PointDTO();
        point3.setMemberEmail(TEST_MEMBER_EMAIL);
        point3.setPointDiv("Purchase");
        point3.setPointTime(TimeUtil.toDateTime(new Date()));
        point3.setPointChange(150);
        point3.setPointResult(250);
        // Set other properties of the PointDTOs

        try {
            // Insert the points into the database
            memberRepository.insertPoint(point1);
            memberRepository.insertPoint(point2);
            memberRepository.insertPoint(point3);

            // Retrieve all points for the member
            List<PointDTO> pointList = memberRepository.selectAllPointsByMemberEmail(TEST_MEMBER_EMAIL);

            // Assert that the retrieved point list is not null and contains the inserted points
            Assertions.assertNotNull(pointList);
            Assertions.assertEquals(pointList.get(0), point1);
            Assertions.assertEquals(pointList.get(1), point2);
            Assertions.assertEquals(pointList.get(2), point3);


        } catch (AddException | FindException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }


    @DisplayName("insertBookmark 메소드 테스트")
    @Transactional
    @Test
    void insertBookmarkTest() {
        // Create a test BookmarkDTO object
        BookmarkDTO insertTestBookmark = new BookmarkDTO();
        insertTestBookmark.setRestaurantNo(1);
        insertTestBookmark.setMemberEmail(TEST_MEMBER_EMAIL);
        // Set other properties of the BookmarkDTO

        try {
            // Insert the bookmark into the database
            memberRepository.insertBookmark(insertTestBookmark.getRestaurantNo(), insertTestBookmark.getMemberEmail());

            // Retrieve the inserted bookmark using the selectBookmarkTEST method
            BookmarkDTO selectedBookmark = memberRepository.selectBookmarkTEST(insertTestBookmark.getRestaurantNo(), insertTestBookmark.getMemberEmail());

            // Assert that the selected bookmark is not null and matches the inserted bookmark
            Assertions.assertNotNull(selectedBookmark);
            Assertions.assertEquals(insertTestBookmark, selectedBookmark);

            insertTestBookmark.setMemberEmail("");
            Assertions.assertThrows(AddException.class, () -> {
                memberRepository.insertBookmark(insertTestBookmark.getRestaurantNo(), insertTestBookmark.getMemberEmail());
            });

        } catch (AddException | FindException  e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("deleteBookmark 메소드 테스트")
    @Transactional
    @Test
    void deleteBookmarkTest() {
        // Create a test BookmarkDTO object
        BookmarkDTO deleteTestBookmark = new BookmarkDTO();
        deleteTestBookmark.setRestaurantNo(1);
        deleteTestBookmark.setMemberEmail(TEST_MEMBER_EMAIL);
        try {
            // Insert the bookmark into the database
            memberRepository.insertBookmark(deleteTestBookmark.getRestaurantNo(), deleteTestBookmark.getMemberEmail());

            // Delete the bookmark
            memberRepository.deleteBookmark(deleteTestBookmark.getRestaurantNo(), deleteTestBookmark.getMemberEmail());

            // Try to retrieve the deleted bookmark
            BookmarkDTO selectedBookmark = memberRepository.selectBookmarkTEST(deleteTestBookmark.getRestaurantNo(), deleteTestBookmark.getMemberEmail());

            // Assert that the selected bookmark is null after deletion
            Assertions.assertNull(selectedBookmark);

            deleteTestBookmark.setMemberEmail("");
            Assertions.assertThrows(RemoveException.class, () -> {
                memberRepository.deleteBookmark(deleteTestBookmark.getRestaurantNo(), deleteTestBookmark.getMemberEmail());
            });
        } catch (AddException | FindException | RemoveException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("selectAllBookmarkByMemberEmail 메소드 테스트")
    @Transactional
    @Test
    void selectAllBookmarkByMemberEmailTest() {
        // Create test BookmarkDTO objects
        BookmarkDTO bookmark1 = new BookmarkDTO();
        bookmark1.setRestaurantNo(1);
        bookmark1.setMemberEmail(TEST_MEMBER_EMAIL);
        // Set other properties of bookmark1

        BookmarkDTO bookmark2 = new BookmarkDTO();
        bookmark2.setRestaurantNo(2);
        bookmark2.setMemberEmail(TEST_MEMBER_EMAIL);
        // Set other properties of bookmark2

        BookmarkDTO bookmark3 = new BookmarkDTO();
        bookmark3.setRestaurantNo(3);
        bookmark3.setMemberEmail(TEST_MEMBER_EMAIL);
        // Set other properties of bookmark3

        try {
            // Insert the bookmarks into the database
            memberRepository.insertBookmark(bookmark1.getRestaurantNo(), bookmark1.getMemberEmail());
            memberRepository.insertBookmark(bookmark2.getRestaurantNo(), bookmark3.getMemberEmail());
            memberRepository.insertBookmark(bookmark3.getRestaurantNo(), bookmark3.getMemberEmail());

            // Retrieve all bookmarks for the member
            List<BookmarkDTO> bookmarkList = memberRepository.selectAllBookmarkByMemberEmail(TEST_MEMBER_EMAIL);

            // Assert that the retrieved bookmark list is not null and contains the inserted bookmarks
            Assertions.assertNotNull(bookmarkList);
            Assertions.assertEquals(bookmarkList.get(0), bookmark1);
            Assertions.assertEquals(bookmarkList.get(1), bookmark2);
            Assertions.assertEquals(bookmarkList.get(2), bookmark3);

            Assertions.assertThrows(FindException.class, () -> {
                memberRepository.selectAllBookmarkByMemberEmail("");
            });
        } catch (AddException | FindException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("selectBookmarkByMemberEmailAndRestaurantNo 메소드 테스트")
    @Transactional
    @Test
    void selectBookmarkByMemberEmailAndRestaurantNo() {
        try {
            // // Insert the bookmarks into the database
            memberRepository.insertBookmark(1,TEST_MEMBER_EMAIL);


            // Retrieve bookmark for the member
            boolean selectedBookmarkTrue = memberRepository.selectBookmarkByMemberEmailAndRestaurntNo(TEST_MEMBER_EMAIL, "1");
            boolean selectedBookmarkFalse = memberRepository.selectBookmarkByMemberEmailAndRestaurntNo(TEST_MEMBER_EMAIL, "2");

            // Assert
            Assertions.assertTrue(selectedBookmarkTrue);
            Assertions.assertFalse(selectedBookmarkFalse);

        } catch (AddException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }


    @DisplayName("selectMemberAll 메소드 테스트")
    @Transactional
    @Test
    void selectMemberAll() {
        try {
            // Retrieve Member List
            MemberDTO selectMember = memberRepository.selectMemberByMemberEmail(TEST_MEMBER_EMAIL);
            List<MemberDTO> memberList = memberRepository.selectMemberAll();

            // Assert
            Assertions.assertNotNull(memberList);

            boolean isExist = false;
            for (MemberDTO find : memberList) {
                if (find.equals(selectMember)) {
                    isExist = true;
                    break;
                }
            }
           Assertions.assertTrue(isExist);

        } catch (FindException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("updateMemberPoint 메소드 테스트")
    @Transactional
    @Test
    void updateMemberPoint() {
        // Create an update test MemberDTO info object
        MemberDTO updatePointTestMember = new MemberDTO();
        updatePointTestMember.setMemberEmail(TEST_MEMBER_EMAIL);
        updatePointTestMember.setMemberPoint(500);

        try {
            // Update the member's point information
            memberRepository.updateMemberPoint(updatePointTestMember);

            // Retrieve the updated member from the database
            MemberDTO updatedMember = memberRepository.selectMemberByMemberEmail(TEST_MEMBER_EMAIL);

            // Assert that the member's point information has been successfully updated
            Assertions.assertEquals(updatePointTestMember.getMemberPoint(), 500);

            updatePointTestMember.setMemberEmail("");
            Assertions.assertThrows(ModifyException.class, () -> {
                memberRepository.updateMemberPoint(updatePointTestMember);
            });

        } catch (FindException | ModifyException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }


    @DisplayName("insertPointAll 메소드 테스트")
    @Transactional
    @Test
    void insertPointAll() throws ParseException {
        int beforeInsertPoint;
        try{
            beforeInsertPoint = memberRepository.selectAllPointsByMemberEmail(TEST_MEMBER_EMAIL).get(0).getPointResult();
        } catch (FindException e ){
            // 포인트 지급 내역이 없으면, 기본 포인트 100
            beforeInsertPoint = 100;
        }
        try {
            // Create test PointDTO objects
            PointDTO testPoint = new PointDTO();
            testPoint.setPointDiv("Purchase");
            testPoint.setPointTime(TimeUtil.toDateTime(new Date()));
            testPoint.setPointChange(500);

            // Call the insertPointAll() method
            memberRepository.insertPointAll(testPoint);

            // Assert that the member Point is updated successfully by retrieving it and comparing the values
            int afterInsertPoint = memberRepository.selectAllPointsByMemberEmail(TEST_MEMBER_EMAIL).get(0).getPointResult();
            Assertions.assertEquals(beforeInsertPoint + 500, afterInsertPoint);

        } catch (AddException | FindException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("updateMemberPointAll 메소드 테스트")
    @Transactional
    @Test
    void updateMemberPointAll() {
        try {
            int beforeUpdatePoint = memberRepository.selectMemberByMemberEmail(TEST_MEMBER_EMAIL).getMemberPoint();

            memberRepository.updateMemberPointAll(500);

            int afterUpdatePoint = memberRepository.selectMemberByMemberEmail(TEST_MEMBER_EMAIL).getMemberPoint();
            Assertions.assertEquals(beforeUpdatePoint + 500, afterUpdatePoint);
        } catch (FindException | ModifyException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }

    @DisplayName("updateMemberRole 메소드 테스트")
    @Transactional
    @Test
    void updateMemberRole() {
        try {
            MemberDTO roleUpdateTestMember = memberRepository.selectMemberByMemberEmail(TEST_MEMBER_EMAIL);
            String beforeUpdateMemberRole = memberRepository.selectMemberByMemberEmail(TEST_MEMBER_EMAIL).getMemberRole();

            roleUpdateTestMember.setMemberRole(beforeUpdateMemberRole.equals("admin") ? "normal" : "admin");

            memberRepository.updateMemberRole(roleUpdateTestMember);

            String afterUpdateMemberRole = memberRepository.selectMemberByMemberEmail(TEST_MEMBER_EMAIL).getMemberRole();
            Assertions.assertEquals(beforeUpdateMemberRole.equals("admin") ? "normal" : "admin", afterUpdateMemberRole);
        } catch (FindException | ModifyException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }
}
