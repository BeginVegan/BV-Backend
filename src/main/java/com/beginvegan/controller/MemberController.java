package com.beginvegan.controller;

import com.beginvegan.dto.MemberDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("login/test")
    public ResponseEntity<?> loginTEST(@RequestBody HashMap<String, Object> param, HttpSession session) throws AddException, FindException, IOException {
        MemberDTO memberInfo = memberService.loginTest(session, param);
        return new ResponseEntity<>(memberInfo, HttpStatus.OK);
    }

    /**
     * 프론트의 로그인 요청 처리(카카오) MemberDB 확인 후 해당 유저의 email 정보 Session에 저장
     * @param param AccessToken 내용을 포함하는 Jason을 Map으로 받는다.
     * @param session 로그인 요청 유저의 session 정보
     * @return
     * @throws AddException
     * @throws FindException
     * @throws IOException
     */
    @PostMapping("login/kakao")
    public ResponseEntity<?> loginKakao(@RequestBody HashMap<String, Object> param, HttpSession session) throws AddException, FindException, IOException {
        MemberDTO memberInfo = memberService.loginKakao(session, (String) param.get("accessToken"));
        return new ResponseEntity<>(memberInfo, HttpStatus.OK);
    }

    @PostMapping("login/google")
    public ResponseEntity<?> loginGoogle(@RequestBody HashMap<String, Object> param, HttpSession session) throws AddException, FindException, IOException {
        MemberDTO memberInfo = memberService.loginGoogle(session, (String) param.get("accessToken"));
        return new ResponseEntity<>(memberInfo, HttpStatus.OK);
    }

    @GetMapping("logout")
    public ResponseEntity<?> logout(HttpSession session) {
        memberService.logout(session);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{memberEmail}")
    public ResponseEntity<?> memberInfoByMemberEmail(@PathVariable String memberEmail) throws FindException{
        return new ResponseEntity<>(memberService.findMemberByMemberEmail(memberEmail), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> memberAdd(@RequestBody MemberDTO memberDTO) throws AddException {
        memberService.addMember(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> memberRemove(HttpSession session) throws RemoveException {
        memberService.removeMember(session, (String) session.getAttribute("memberEmail"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> memberModify(@RequestBody MemberDTO memberDTO) throws ModifyException {
        memberService.modifyMember(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> memberDetails(HttpSession session) throws FindException {
        memberService.findMemberByMemberEmail((String) session.getAttribute("memberEmail"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TEST Controller : Session 확인용
    @GetMapping("session")
    public ResponseEntity<?> getSession(HttpSession session) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String memberEmail = (String)session.getAttribute("memberEmail");
        String memberName = (String)session.getAttribute("memberName");
        String memberRole = (String)session.getAttribute("memberRole");
        String accessToken = (String)session.getAttribute("accessToken");

        Map<String, Object> map = new HashMap<>();
        map.put("JSESSIONID 값", session.getId());
        map.put("세션 유효 시간", session.getMaxInactiveInterval()/60 + "분");
        map.put("세션 생성 일시", sdf.format(session.getCreationTime()));
        map.put("최근 접근 시간", sdf.format(session.getLastAccessedTime()));
        map.put("생성 여부 판별", session.isNew());
        map.put("현재 memberEmail", memberEmail);
        map.put("현재 memberName", memberName);
        map.put("현재 accessToken", accessToken);
        map.put("현재 memberRole", memberRole);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
