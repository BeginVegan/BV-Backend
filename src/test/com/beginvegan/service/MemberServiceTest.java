package com.beginvegan.service;

import com.beginvegan.dto.MemberDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
class MemberServiceTest {

    // 아래 값 6개는 테스트 전 발급 받은 실제 내용으로 변경할 것
    private final String KAKAO_ACCESS_TOKEN = "EmuvMSsNyBZFiMxNvXXm3l2nn41wwRofZJFyU6YTCioljwAAAYhrmVUA";
    private final String FROM_KAKAO_API_EMAIL = "2802453608@kakao.com";
    private final String FROM_KAKAO_API_NAME = "성찬민";
    private final String GOOGLE_CRDENTIAL = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjYwODNkZDU5ODE2NzNmNjYxZmRlOWRhZTY0NmI2ZjAzODBhMDE0NWMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJuYmYiOjE2ODU0Mjc1OTgsImF1ZCI6Ijc5MTU3NzcwMzkzLXZkMmZ0czNjM2tkOW5iam5iNzJxcWpoNm1lbnM0ZmRnLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTEyNDk5OTQ2NjgwNTMzOTQ0MzY3IiwiZW1haWwiOiJiZWhvbmVzdHdheUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXpwIjoiNzkxNTc3NzAzOTMtdmQyZnRzM2Mza2Q5bmJqbmI3MnFxamg2bWVuczRmZGcuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJuYW1lIjoiY2hhbm1pbiBzdW5nIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FBY0hUdGRkYzNPM2lMdnFYejhQcWQ5cEJabTR2SFVyeXV0STZQeklPQkZNPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6ImNoYW5taW4iLCJmYW1pbHlfbmFtZSI6InN1bmciLCJpYXQiOjE2ODU0Mjc4OTgsImV4cCI6MTY4NTQzMTQ5OCwianRpIjoiMDBkNzUzMGY1NDM2YWE2ZWMyYTAxMGEwYWM4MTViZWU2MDk0YWZiZCJ9.X5boArHd0RgiIDuGDXNuMeURQ2VWWwnSr-1mDlN9NsarO5yIjIDuNANAOsrtZS8kcb59bqGhMvCip6GvH3nt3Th3-Tp3F80KZxdcDYeqqjxyxdoP-bZrb8I-1n1QtZFl6YqTvZUdOm5Fr8AbdHhEN0JNQf16oEc8BRX6eLGUhnkaWWvOHISREzG9_3Siexn_V5AKsg7bSPRU_gq_Yc1tU6lfb-2IdO8yhVf3nC34LyySUxEIr609hSuvcT9KidyP3NE4COx3dThGymGSeagXbHaQ53zXUYXMqfbXWvW2hD4eOVypMmDMziTu7FIJJRzq1LeYxb8niEt8IvB8HBCVAw";
    private final String FROM_GOOGLE_ACCOUNT_EMAIL = "behonestway@gmail.com";
    private final String FROM_GOOGLE_ACCOUNT_NAME = "chanmin sung";


    @Autowired
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private MockHttpSession mockSession;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Kakao login 테스트")
    @Test
    void kakao_login_shouldUpdateMemberAndSetSessionAttributes() throws Exception {

        // Mock member info from Kakao API
        MemberDTO mockMemberInfo = new MemberDTO();
        mockMemberInfo.setMemberEmail(FROM_KAKAO_API_EMAIL);
        mockMemberInfo.setMemberName(FROM_KAKAO_API_NAME);

        // Mock existing member info in the repository
        MemberDTO mockExistingMemberInfo = new MemberDTO();
        mockExistingMemberInfo.setMemberEmail(FROM_KAKAO_API_EMAIL);
        mockExistingMemberInfo.setMemberName(FROM_KAKAO_API_NAME);

        // Call the login method
        memberService.loginKakao(mockSession, KAKAO_ACCESS_TOKEN);

        // Mock repository method to select existing member by email
        when(memberRepository.selectMemberByMemberEmail(FROM_KAKAO_API_EMAIL))
                .thenReturn(mockExistingMemberInfo);

        // Change inserted member name
        mockMemberInfo.setMemberName("updateTestName");
        memberRepository.updateMember(mockMemberInfo);
        mockMemberInfo.setMemberName(FROM_KAKAO_API_NAME);

        // Call the login method for Update member name
        memberService.loginKakao(mockSession, KAKAO_ACCESS_TOKEN);

        // Verify that the member repository methods were called
        verify(memberRepository, times(2)).selectMemberByMemberEmail(FROM_KAKAO_API_EMAIL);
        verify(memberRepository, times(1)).updateMember(mockExistingMemberInfo);

        // Verify that the member info is updated with the new name
        assertEquals(mockMemberInfo.getMemberName(), mockExistingMemberInfo.getMemberName());

        // Verify that the session attributes are set
        verify(mockSession, times(2)).setAttribute("memberEmail", mockMemberInfo.getMemberEmail());
        verify(mockSession, times(2)).setAttribute("accessToken", KAKAO_ACCESS_TOKEN);

        memberRepository.deleteMember(FROM_KAKAO_API_EMAIL);
        memberService.logout(mockSession);

    }

    @DisplayName("Google login 테스트")
    @Test
    void google_login_shouldUpdateMemberAndSetSessionAttributes() throws Exception {

        // Mock member info from Kakao API
        MemberDTO mockMemberInfo = new MemberDTO();
        mockMemberInfo.setMemberEmail(FROM_GOOGLE_ACCOUNT_EMAIL);
        mockMemberInfo.setMemberName(FROM_GOOGLE_ACCOUNT_NAME);

        // Mock existing member info in the repository
        MemberDTO mockExistingMemberInfo = new MemberDTO();
        mockExistingMemberInfo.setMemberEmail(FROM_GOOGLE_ACCOUNT_EMAIL);
        mockExistingMemberInfo.setMemberName(FROM_GOOGLE_ACCOUNT_NAME);

        // Call the login method
        memberService.loginGoogle(mockSession, GOOGLE_CRDENTIAL);

        // Mock repository method to select existing member by email
        when(memberRepository.selectMemberByMemberEmail(FROM_GOOGLE_ACCOUNT_EMAIL))
                .thenReturn(mockExistingMemberInfo);

        // Change inserted member name
        mockMemberInfo.setMemberName("updateTestName");
        memberRepository.updateMember(mockMemberInfo);
        mockMemberInfo.setMemberName(FROM_GOOGLE_ACCOUNT_NAME);

        // Call the login method for Update member name
        memberService.loginGoogle(mockSession, GOOGLE_CRDENTIAL);

        // Verify that the member repository methods were called
        verify(memberRepository, times(2)).selectMemberByMemberEmail(FROM_GOOGLE_ACCOUNT_EMAIL);
        verify(memberRepository, times(1)).updateMember(mockExistingMemberInfo);

        // Verify that the member info is updated with the new name
        assertEquals(mockMemberInfo.getMemberName(), mockExistingMemberInfo.getMemberName());

        // Verify that the session attributes are set
        verify(mockSession, times(2)).setAttribute("memberEmail", mockMemberInfo.getMemberEmail());
        verify(mockSession, times(2)).setAttribute("googleCredential", GOOGLE_CRDENTIAL);

        memberRepository.deleteMember(FROM_GOOGLE_ACCOUNT_EMAIL);
        memberService.logout(mockSession);

    }

    @DisplayName("logout 테스트")
    @Test
    void logout_shouldInvalidateSession() {
        //Arrange
        MockHttpSession session = new MockHttpSession();
        // Act
        memberService.logout(session);

        // Assert
        assertTrue(session.isInvalid());
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
        memberInfo.setMemberName("Test User Modify");

        // Act
        memberService.modifyMember(memberInfo);

        // Assert
        verify(memberRepository, times(1)).updateMember(memberInfo);
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
}
