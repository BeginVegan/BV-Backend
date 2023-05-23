package com.beginvegan.service;

import com.beginvegan.dto.MemberDTO;
import com.beginvegan.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("memberService")
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * 테스트용 메소드입니다. DB 연동 테스트
     * @return
     */
    public List<MemberDTO> getMemberAll() {
        return memberRepository.getMemberList();
    }

    /**
     * 테스트용 메소드입니다. DB 연동 테스트
     * @return
     */
    public MemberDTO searchMemberByName(String name) {
        return memberRepository.getMemberByName(name);
    }

}
