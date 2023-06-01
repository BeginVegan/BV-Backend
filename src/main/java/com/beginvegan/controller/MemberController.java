package com.beginvegan.controller;

import com.beginvegan.dto.MemberDTO;
import com.beginvegan.dto.PointDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.service.MemberService;
import com.beginvegan.service.MyPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("member/*")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MyPageService myPageService;
    @PostMapping("login/kakao")
    public ResponseEntity<?> loginKakao(@RequestBody HashMap<String, Object> param, HttpSession session) throws AddException, FindException, IOException {
        memberService.loginKakao(session, (String) param.get("accessToken"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("login/google")
    public ResponseEntity<?> loginGoogle(@RequestBody HashMap<String, Object> param, HttpSession session) throws AddException, FindException, IOException {
        memberService.loginGoogle(session, (String) param.get("googleCredential"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("logout")
    public ResponseEntity<?> logout(HttpSession session) {
        memberService.logout(session);
        return new ResponseEntity<>(HttpStatus.OK);
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

    @GetMapping("point-histroy")
    public ResponseEntity<?> getPointHistory(HttpSession session) throws FindException {
       List<PointDTO> pointList = myPageService.findAllPointByMemberEmail((String) session.getAttribute("memberEmail"));
        return new ResponseEntity<>(pointList, HttpStatus.OK);
    }
}
