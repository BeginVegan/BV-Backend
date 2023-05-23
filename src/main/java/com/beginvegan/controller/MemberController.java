package com.beginvegan.controller;

import com.beginvegan.dto.MemberDTO;
import com.beginvegan.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("member/*")
public class MemberController {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private MemberService memberServiceervice;

    /**
     * 테스트용 메소드입니다. DB 연동 테스트
     *
     * @param session
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<?> getMemberAll(HttpSession session) {
        log.info("getMemberAll 시작");
        List<MemberDTO> memberList = memberServiceervice.getMemberAll();
        log.info("getMemberAll 끝");
        return new ResponseEntity<>(memberList, HttpStatus.OK);
    }

    /**
     * 테스트용 메소드입니다. DB 연동 테스트
     *
     * @param session
     * @return
     */
    @GetMapping("{name}")
    public ResponseEntity<?> searchMemberByName(@PathVariable String name, HttpSession session) {
        log.info("searchMemberByName 시작");
        MemberDTO member = memberServiceervice.searchMemberByName(name);
        log.info("searchMemberByName 끝");
        return new ResponseEntity<>(member, HttpStatus.OK);
    }


}
