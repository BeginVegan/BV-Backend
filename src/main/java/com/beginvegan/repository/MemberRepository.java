package com.beginvegan.repository;

import com.beginvegan.dto.MemberDTO;

import java.util.List;

public interface MemberRepository {

    /**
     * 테스트용 메소드입니다. DB 연동 테스트
     * @return 모든 멤버의 정보를 리스트로 반환한다.
     */
    public List<MemberDTO> getMemberList();

    /**
     * 테스트용 메소드입니다. DB 연동 테스트
     * @param name
     * @return name 조건에 해당하는 멤버의 정보를 반환한다.
     */
    public  MemberDTO getMemberByName(String name);

}
