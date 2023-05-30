package com.beginvegan.service;

import com.beginvegan.dto.MemberDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class MemberServiceTest {

    // 테스트 전 발급 받은 실제 토큰과 내용으로 변경할 것
    private final String ACCESS_TOKEN = "jqTEdQ8YMY4SUCB4b6by6bcey9jHkFn4cuK8Y0eACinI2AAAAYhqk7dz";
    private final String FROM_API_EMAIL = "2802453608@kakao.com";
    private final String FROM_API_NAME = "성찬민";

    @Autowired
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("login 테스트")
    @Test
    void login_shouldUpdateMemberAndSetSessionAttributes() throws Exception {
        // Mock member info from Kakao API
        MemberDTO mockMemberInfo = new MemberDTO();
        mockMemberInfo.setMemberEmail(FROM_API_EMAIL);
        mockMemberInfo.setMemberName(FROM_API_NAME);

        // Mock existing member info in the repository
        MemberDTO mockExistingMemberInfo = new MemberDTO();
        mockExistingMemberInfo.setMemberEmail(FROM_API_EMAIL);
        mockExistingMemberInfo.setMemberName(FROM_API_NAME);

        // Call the login method
        memberService.login(session, ACCESS_TOKEN);

        // Mock repository method to select existing member by email
        when(memberRepository.selectMemberByMemberEmail(FROM_API_EMAIL))
                .thenReturn(mockExistingMemberInfo);

        // Change inserted member name
        mockMemberInfo.setMemberName("updateTestName");
        memberRepository.updateMember(mockMemberInfo);
        mockMemberInfo.setMemberName(FROM_API_NAME);

        // Call the login method for Update member name
        memberService.login(session, ACCESS_TOKEN);

        // Verify that the member repository methods were called
        verify(memberRepository, times(2)).selectMemberByMemberEmail(FROM_API_EMAIL);
        verify(memberRepository, times(1)).updateMember(mockExistingMemberInfo);

        // Verify that the member info is updated with the new name
        assertEquals(mockMemberInfo.getMemberName(), mockExistingMemberInfo.getMemberName());

        // Verify that the session attributes are set
        verify(session, times(2)).setAttribute("memberEmail", mockMemberInfo.getMemberEmail());
        verify(session, times(2)).setAttribute("accessToken", ACCESS_TOKEN);

        memberRepository.deleteMember(FROM_API_EMAIL);
        memberService.logout(session);

    }

    @DisplayName("logout 테스트")
    @Test
    void logout_shouldInvalidateSession() {
        // Arrange
        MockHttpSession mockSession = new MockHttpSession();

        // Act
        memberService.logout(mockSession);

        // Assert
        assertTrue(mockSession.isInvalid());
    }

    @DisplayName("addMember 테스트")
    @Test
    void addMember_shouldSetInitialPointAndCallRepositoryMethod() throws AddException {
        // Arrange
        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail("test@example.com");
        memberInfo.setMemberName("Test User");

        // Act
        memberService.addMember(memberInfo);

        // Assert
        verify(memberRepository, times(1)).insertMember(memberInfo);
        assertEquals(100, memberInfo.getMemberPoint());
    }

    @DisplayName("modifyMember 테스트")
    @Test
    void modifyMember_shouldCallRepositoryMethod() throws ModifyException {
        // Arrange
        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail("test@example.com");
        memberInfo.setMemberName("Test User");

        // Act
        memberService.modifyMember(memberInfo);

        // Assert
        verify(memberRepository, times(1)).updateMember(memberInfo);
    }

    @DisplayName("removeMember 테스트")
    @Test
    void removeMember_shouldCallRepositoryMethodAndInvalidateSession() throws RemoveException {
        // Arrange
        String memberEmail = "test@example.com";
        MockHttpSession mockSession = new MockHttpSession();

        // Act
        memberService.removeMember(mockSession, memberEmail);

        // Assert
        verify(memberRepository, times(1)).deleteMember(memberEmail);
        assertTrue(mockSession.isInvalid());
    }

    @DisplayName("fnidMemberByMemberEmail 테스트")
    @Test
    void findMemberByMemberEmail_shouldCallRepositoryMethod() throws FindException {
        // Arrange
        String memberEmail = "test@example.com";

        // Act
        memberService.findMemberByMemberEmail(memberEmail);

        // Assert
        verify(memberRepository, times(1)).selectMemberByMemberEmail(memberEmail);
    }

}
