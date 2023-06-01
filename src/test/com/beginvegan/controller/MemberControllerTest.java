package com.beginvegan.controller;

import com.beginvegan.dto.MemberDTO;
import com.beginvegan.dto.PointDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.service.MemberService;
import com.beginvegan.service.MyPageService;
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
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MemberControllerTest {
    // 아래 두개의 토큰은 테스트 마다 발급받은 실제 토큰 값으로 대체 필요
    private final String KAKAO_ACCESS_TOKEN = "75KfhDBoS3KQPoyDWEGD9lbGYDPfCcnro88duGOCCj1ylwAAAYh0hfQd";
    private final String GOOGLE_CREDENTIAL = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjYwODNkZDU5ODE2NzNmNjYxZmRlOWRhZTY0NmI2ZjAzODBhMDE0NWMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJuYmYiOjE2ODU1ODE4OTYsImF1ZCI6Ijc5MTU3NzcwMzkzLXZkMmZ0czNjM2tkOW5iam5iNzJxcWpoNm1lbnM0ZmRnLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTEyNDk5OTQ2NjgwNTMzOTQ0MzY3IiwiZW1haWwiOiJiZWhvbmVzdHdheUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXpwIjoiNzkxNTc3NzAzOTMtdmQyZnRzM2Mza2Q5bmJqbmI3MnFxamg2bWVuczRmZGcuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJuYW1lIjoiY2hhbm1pbiBzdW5nIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FBY0hUdGRkYzNPM2lMdnFYejhQcWQ5cEJabTR2SFVyeXV0STZQeklPQkZNPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6ImNoYW5taW4iLCJmYW1pbHlfbmFtZSI6InN1bmciLCJpYXQiOjE2ODU1ODIxOTYsImV4cCI6MTY4NTU4NTc5NiwianRpIjoiYTUxMWRlMGI2NzFhNTZiNThjZmRkODRhNWM0NmM2NTk2MTNlM2UwOSJ9.BNA81IiIuBYOTlFf7K3UgwJKfinZbEWQ8RJCJCxbGEdO8N-b7d3CRAcfowJWqVn5KerTdoixTs_Rg-WCF-XlkgHGUm15KOxh7ze7jW6LPV0Ty8XgT62Xqw40XfeJTSXB_z5UJz1Nt248elQnADC97XJ9EnnzGDoUxsFiawK4w0phI973yxje_rscn_vdu28AqtAXDYPrOUZDFvIDCBlN8gqJ9EBnO2JBce1N-bujRope2CmmV6x3SV-a56H5MdNr7ir14j651YVtODk6rRnuKufdzDwF1N_OQShJ-pxkypwl-a68S5zaRGZIGbfUuYx43bLDwZUrPHYho-D-OHUf6Q";

    @Mock
    private MemberService memberService;

    @Mock
    private MyPageService myPageService;

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
    void loginKakao() throws AddException, FindException, IOException {
        // Given
        HashMap<String, Object> param = new HashMap<>();
        param.put("accessToken", KAKAO_ACCESS_TOKEN);

        // When
        ResponseEntity<?> response = memberController.loginKakao(param, mockSession);

        // Then
        verify(memberService).loginKakao(mockSession, KAKAO_ACCESS_TOKEN);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void loginGoogle() throws AddException, FindException, IOException {
        // Given
        HashMap<String, Object> param = new HashMap<>();
        param.put("googleCredential", GOOGLE_CREDENTIAL);

        // When
        ResponseEntity<?> response = memberController.loginGoogle(param, mockSession);

        // Then
        verify(memberService).loginGoogle(mockSession, GOOGLE_CREDENTIAL);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void logout() {
        // When
        ResponseEntity<?> response = memberController.logout(mockSession);

        // Then
        verify(memberService).logout(mockSession);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void memberAdd() throws AddException {
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
    void memberModify() throws ModifyException {
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
    void memberDetails() throws FindException {
        // When
        ResponseEntity<?> response = memberController.memberDetails(mockSession);

        // Then
        verify(memberService).findMemberByMemberEmail((String) mockSession.getAttribute("memberEmail"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void memberRemove() throws RemoveException {
        // When
        ResponseEntity<?> response = memberController.memberRemove(mockSession);

        // Then
        verify(memberService).removeMember(mockSession, (String) mockSession.getAttribute("memberEmail"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}