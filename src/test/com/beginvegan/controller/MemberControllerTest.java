package com.beginvegan.controller;

import com.beginvegan.dto.MemberDTO;
import com.beginvegan.dto.PointDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MemberControllerTest {
    // 아래 두개의 토큰은 테스트 마다 발급받은 실제 토큰 값으로 대체 필요
    private final String KAKAO_ACCESS_TOKEN = "test1234abc";
    private final String GOOGLE_ACCESS_TOKEN = "test1234abc";
    @Mock
    private MemberService memberService;

    @MockBean
    private MockHttpSession mockSession;

    @InjectMocks
    private MemberController memberController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockSession = new MockHttpSession();
        mockSession.setAttribute("memberEmail", "test@example.com");
    }

    @Test
    void loginKakaoTest() throws AddException, FindException, IOException {
        String memberEmail = "test@example.com";
        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail(memberEmail);

        // Given
        HashMap<String, Object> param = new HashMap<>();
        param.put("accessToken", KAKAO_ACCESS_TOKEN);
        when(memberService.loginKakao(mockSession, KAKAO_ACCESS_TOKEN)).thenReturn(memberInfo);

        // When
        ResponseEntity<?> response = memberController.loginKakao(param, mockSession);

        // Then
        verify(memberService).loginKakao(mockSession, KAKAO_ACCESS_TOKEN);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(memberInfo, response.getBody());
    }

    @Test
    void loginGoogleTest() throws AddException, FindException, IOException {
        String memberEmail = "test@example.com";
        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail(memberEmail);


        // Given
        HashMap<String, Object> param = new HashMap<>();
        param.put("accessToken", GOOGLE_ACCESS_TOKEN);
        when(memberService.loginGoogle(mockSession, GOOGLE_ACCESS_TOKEN)).thenReturn(memberInfo);

        // When
        ResponseEntity<?> response = memberController.loginGoogle(param, mockSession);

        // Then
        verify(memberService).loginGoogle(mockSession, GOOGLE_ACCESS_TOKEN);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void logoutTest() {
        // When
        ResponseEntity<?> response = memberController.logout(mockSession);

        // Then
        verify(memberService).logout(mockSession);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void memberDetailsTest() throws FindException {
        //given
        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail((String) mockSession.getAttribute("memberEmail"));
        when(memberService.findMemberByMemberEmail((String) mockSession.getAttribute("memberEmail"))).thenReturn(memberInfo);

        // When
        ResponseEntity<?> response = memberController.memberDetails(mockSession);

        // Then
        verify(memberService).findMemberByMemberEmail((String) mockSession.getAttribute("memberEmail"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(memberInfo, response.getBody());
    }

    @Test
    void memberInfoByMemberEmailTest() throws FindException {
        // Arrange
        String memberEmail = "test@example.com";
        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail(memberEmail);
        when(memberService.findMemberByMemberEmail(memberEmail)).thenReturn(memberInfo);

        // Act
        ResponseEntity<?> response = memberController.memberInfoByMemberEmail(memberEmail);

        // Assert
        verify(memberService).findMemberByMemberEmail(memberEmail);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(memberInfo, response.getBody());
    }

    @Test
    void memberListTest() throws FindException {
        // Arrange
        String memberEmail = "test@example.com";
        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail(memberEmail);

        List<MemberDTO> memberList = new ArrayList<>();
        memberList.add(memberInfo);

        when(memberService.findAllMember()).thenReturn(memberList);

        // Act
        ResponseEntity<?> response = memberController.memberList();

        // Assert
        verify(memberService).findAllMember();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(memberList, response.getBody());
    }

    @Test
    void memberAddTest() throws AddException {
        // Given
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("test@example.com");
        memberDTO.setMemberName("Test User");

        // When
        ResponseEntity<?> response = memberController.memberAdd(memberDTO);

        // Then
        verify(memberService).addMember(memberDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void memberRemoveByMemberInfoTest() throws RemoveException {
        String memberEmail = "test@example.com";
        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail(memberEmail);

        // When
        ResponseEntity<?> response = memberController.memberRemove(memberInfo);

        // Then
        verify(memberService).removeMember(memberInfo.getMemberEmail());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void memberRemoveTest() throws RemoveException {
        // When
        ResponseEntity<?> response = memberController.memberRemove(mockSession);

        // Then
        verify(memberService).removeMember(mockSession, (String) mockSession.getAttribute("memberEmail"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void memberModifyTest() throws ModifyException {
        // Given
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("test@example.com");
        memberDTO.setMemberName("Test User Modify");

        // When
        ResponseEntity<?> response = memberController.memberModify(memberDTO);

        // Then
        verify(memberService).modifyMember(memberDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void memberPointAllModifyTest() throws ModifyException, AddException {
        // Arrange
        PointDTO pointInfo = new PointDTO();
        pointInfo.setPointChange(300);

        // Act
        ResponseEntity<?> response = memberController.memberPointAllModify(pointInfo);

        // Assert
        verify(memberService).modifyAllMemberPoint(pointInfo);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void memberPointModifyTest() throws ModifyException, AddException {
        // Arrange
        PointDTO pointInfo = new PointDTO();
        pointInfo.setPointChange(300);

        // Act
        ResponseEntity<?> response = memberController.memberPointModify(pointInfo);

        // Assert
        verify(memberService).modifyMemberPoint(pointInfo);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void memberRoleAdminModifyAdminTest() throws ModifyException {
        // Arrange
        String memberEmail = "test@example.com";
        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail(memberEmail);

        // Act
        ResponseEntity<?> response = memberController.memberRoleAdminModify(memberInfo);

        // Assert
        verify(memberService).modifyMemberRoleAdmin(memberInfo.getMemberEmail());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void memberRoleAdminModifyNormalTest() throws ModifyException {
        // Arrange
        String memberEmail = "test@example.com";
        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail(memberEmail);

        // Act
        ResponseEntity<?> response = memberController.memberRoleNormalModify(memberInfo);

        // Assert
        verify(memberService).modifyMemberRoleNormal(memberInfo.getMemberEmail());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void isBookmarkTest() throws FindException {
        // Arrange
        String resNo = "1";

        when(memberService.isMemberBookmarkedRestaurant((String)mockSession.getAttribute("memberEmail"), resNo)).thenReturn(true);

        // Act
        ResponseEntity<?> response = memberController.isBookmark(resNo, mockSession);

        // Assert
        verify(memberService).isMemberBookmarkedRestaurant((String)mockSession.getAttribute("memberEmail"), resNo);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }
}