package com.beginvegan.service;

import com.beginvegan.dto.MemberDTO;
import com.beginvegan.dto.PointDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.repository.MemberRepository;
import com.beginvegan.util.GetGoogleAccount;
import com.beginvegan.util.GetKakaoAccount;
import com.beginvegan.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;

import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
class MemberServiceTest {
    private final String KAKAO_ACCESS_TOKEN = "test1234abc";
    private final String GOOGLE_ACCESS_TOKEN = "test1234abc";
    private static final String MEMBER_ROLE_NORMAL = "normal";
    private static final String MEMBER_ROLE_ADMIN = "admin";

    private static MockedStatic<GetKakaoAccount> getKakaoAccount;

    private static MockedStatic<GetGoogleAccount> getGoogleAccount;
    @Autowired
    private MemberService memberService;

    @Mock
    private MemberService mockMemberService;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private MockHttpSession mockSession;

    @BeforeAll
    public static void beforeAll() {
        getKakaoAccount = mockStatic(GetKakaoAccount.class);
        getGoogleAccount = mockStatic(GetGoogleAccount.class);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterAll
    public static void afterAll() {
        getKakaoAccount.close();
        getGoogleAccount.close();
    }

    @DisplayName("KakaoLogin 테스트")
    @Test
    void kakao_loginTest() throws Exception {

        // Arrange
        String memberEmail = "test@example.com";
        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail(memberEmail);
        memberInfo.setMemberName("tester1");
        memberInfo.setMemberPoint(300);
        memberInfo.setMemberRole("tester");

        MemberDTO memberExistInfo = new MemberDTO();
        memberExistInfo.setMemberEmail(memberEmail);
        memberExistInfo.setMemberName("tester1");
        memberExistInfo.setMemberPoint(300);
        memberExistInfo.setMemberRole("tester");

        when(GetKakaoAccount.getMemberInfo(KAKAO_ACCESS_TOKEN)).thenReturn(memberInfo);
        when(memberRepository.selectMemberByMemberEmail(memberInfo.getMemberEmail())).thenReturn(memberExistInfo);

        // Call the login method
        memberService.loginKakao(mockSession, KAKAO_ACCESS_TOKEN);

        // Change inserted member name
        memberInfo.setMemberName("updateTestName");

        // Call the login method
        mockMemberService.loginKakao(mockSession, KAKAO_ACCESS_TOKEN);

        memberInfo.setMemberName("nameIsNotEqualTest");
        // Call the login method for Update member name
        mockMemberService.loginKakao(mockSession, KAKAO_ACCESS_TOKEN);

        // Verify that the member repository methods were called
        verify(memberRepository, times(2)).selectMemberByMemberEmail(memberInfo.getMemberEmail());
        //verify(memberRepository, times(1)).updateMember(memberExistInfo);

        // Verify that the session attributes are set
        verify(mockSession, times(1)).setAttribute("memberEmail", memberInfo.getMemberEmail());
        verify(mockSession, times(1)).setAttribute("accessToken", KAKAO_ACCESS_TOKEN);

        memberService.logout(mockSession);

    }

    @DisplayName("GoogleLogin 테스트")
    @Test
    void google_loginTest() throws Exception {

        // Arrange
        String memberEmail = "test@example.com";
        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail(memberEmail);
        memberInfo.setMemberName("tester1");
        memberInfo.setMemberPoint(300);
        memberInfo.setMemberRole("tester");

        MemberDTO memberExistInfo = new MemberDTO();
        memberExistInfo.setMemberEmail(memberEmail);
        memberExistInfo.setMemberName("tester1");
        memberExistInfo.setMemberPoint(300);
        memberExistInfo.setMemberRole("tester");

        when(GetGoogleAccount.getMemberInfo(GOOGLE_ACCESS_TOKEN)).thenReturn(memberInfo);
        when(memberRepository.selectMemberByMemberEmail(memberInfo.getMemberEmail())).thenReturn(memberExistInfo);


        // Call the login method
        memberService.loginGoogle(mockSession, GOOGLE_ACCESS_TOKEN);

        // Change inserted member name
        memberInfo.setMemberName("updateTestName");

        // Call the login method
        mockMemberService.loginGoogle(mockSession, GOOGLE_ACCESS_TOKEN);

        memberInfo.setMemberName("nameIsNotEqualTest");
        // Call the login method for Update member name
        mockMemberService.loginKakao(mockSession, GOOGLE_ACCESS_TOKEN);

        // Verify that the member repository methods were called
        verify(memberRepository, times(2)).selectMemberByMemberEmail(memberInfo.getMemberEmail());
        //verify(memberRepository, times(1)).updateMember(memberExistInfo);

        // Verify that the session attributes are set
        verify(mockSession, times(1)).setAttribute("memberEmail", memberInfo.getMemberEmail());
        verify(mockSession, times(1)).setAttribute("accessToken", GOOGLE_ACCESS_TOKEN);

        memberService.logout(mockSession);

    }

    @DisplayName("logout 테스트")
    @Test
    void logoutTest() {
    //Arrange
    MockHttpSession session = new MockHttpSession();

    // Act
    memberService.logout(session);

    // Assert
    assertTrue(session.isInvalid());
}

    @DisplayName("addMember 테스트")
    @Test
    void addMemberTest() throws AddException {
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
    void modifyMemberTest() throws ModifyException {
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
    void findMemberByMemberEmailTest() throws FindException {
        // Arrange
        String memberEmail = "test@example.com";

        // Act
        memberService.findMemberByMemberEmail(memberEmail);

        // Assert
        verify(memberRepository, times(1)).selectMemberByMemberEmail(memberEmail);
    }

    @DisplayName("removeMember 테스트")
    @Test
    void removeMemberTest() throws RemoveException {
        // Arrange
        String memberEmail = "test@example.com";
        MockHttpSession mockSession = new MockHttpSession();

        // Act
        memberService.removeMember(mockSession, memberEmail);

        // Assert
        verify(memberRepository, times(1)).deleteMember(memberEmail);
        assertTrue(mockSession.isInvalid());
    }

    @DisplayName("modifyMemberPoint 테스트")
    @Test
    void modifyMemberPointTest() throws ModifyException, ParseException, AddException {
        // Arrange
        PointDTO pointInfo = new PointDTO();
        pointInfo.setMemberEmail("test@example.com");
        pointInfo.setPointTime(TimeUtil.toDateTime(new Date()));
        pointInfo.setPointDiv("test");
        pointInfo.setPointChange(300);
        pointInfo.setPointResult(1300);

        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail(pointInfo.getMemberEmail());
        memberInfo.setMemberPoint(pointInfo.getPointResult());

        // Act
        memberService.modifyMemberPoint(pointInfo);

        // Assert
        verify(memberRepository, times(1)).updateMemberPoint(memberInfo);
        verify(memberRepository, times(1)).insertPoint(pointInfo);
    }

    @DisplayName("modifyAllMemberPoint 테스트")
    @Test
    void modifyAllMemberPointTest() throws ModifyException, ParseException, AddException {
        // Arrange
        PointDTO pointInfo = new PointDTO();
        pointInfo.setMemberEmail("test@example.com");
        pointInfo.setPointTime(TimeUtil.toDateTime(new Date()));
        pointInfo.setPointDiv("test");
        pointInfo.setPointChange(300);
        pointInfo.setPointResult(1300);

        // Act
        memberService.modifyAllMemberPoint(pointInfo);

        // Assert
        verify(memberRepository, times(1)).updateMemberPointAll(pointInfo.getPointChange());
        verify(memberRepository, times(1)).insertPointAll(pointInfo);
    }


    @DisplayName("modifyMemberRoleAdmin 테스트")
    @Test
    void modifyMemberRoleAdminTest() throws ModifyException, ParseException, AddException {
        // Arrange
        String email = "test@example.com";

        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail(email);
        memberInfo.setMemberRole(MEMBER_ROLE_ADMIN);

        // Act
        memberService.modifyMemberRoleAdmin(email);

        // Assert
        verify(memberRepository, times(1)).updateMemberRole(memberInfo);
    }


    @DisplayName("modifyMemberRoleNormal 테스트")
    @Test
    void modifyMemberRoleNormalTest() throws ModifyException, ParseException, AddException {
        // Arrange
        String email = "test@example.com";

        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail(email);
        memberInfo.setMemberRole(MEMBER_ROLE_NORMAL);

        // Act
        memberService.modifyMemberRoleNormal(email);

        // Assert
        verify(memberRepository, times(1)).updateMemberRole(memberInfo);
    }


    @DisplayName("findAllMember 테스트")
    @Test
    void findAllMemberTest() throws FindException {

        // Act
        memberService.findAllMember();

        // Assert
        verify(memberRepository, times(1)).selectMemberAll();
    }

    @DisplayName("removeMember 테스트")
    @Test
    void removeMemberByMemberEmailTest() throws RemoveException {
        // Arrange
        String email = "test@example.com";

        // Act
        memberService.removeMember(email);

        // Assert
        verify(memberRepository, times(1)).deleteMember(email);

    }

    @DisplayName("isMemberBookmarkedRestaurant 테스트")
    @Test
    void isMemberBookmarkedRestaurantTest() {
        // Arrange
        String email = "test@example.com";
        String restaurantNo = "1";

        // Act
        memberService.isMemberBookmarkedRestaurant(email, restaurantNo);

        // Assert
        verify(memberRepository, times(1)).selectBookmarkByMemberEmailAndRestaurntNo(email, restaurantNo);
    }
}
