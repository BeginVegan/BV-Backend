package com.beginvegan.service;

import com.beginvegan.dto.MemberDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.repository.MemberRepository;
import com.beginvegan.util.KakaoAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Slf4j
@Service("memberService")
public class MemberService {
    private int MEMBER_INITIAL_POINT = 100;

    @Autowired
    private MemberRepository memberRepository;

    /**
     * 로그인 한다.
     * @param session 클라이언트의 세션
     * @param accessToken API accessToken
     * @throws Exception 로그인 과정 중 오류에의해 발생
     */
    public void login(HttpSession session, String accessToken) throws Exception {
        log.info("login 시작");

        // Kakao API 요청 : 멤버 정보를 가져온다.
        MemberDTO memberInfo = KakaoAPI.getMemberInfo(accessToken);

        // DB에 저장된 회원 정보 조회
        try {
            MemberDTO memberExistInfo = memberRepository.selectMemberByMemberEmail(memberInfo.getMemberEmail());
            // 멤버의 닉네임 정보 업데이트
            if(!memberInfo.getMemberName().equals(memberExistInfo.getMemberName())) {
                memberExistInfo.setMemberName(memberInfo.getMemberName());
                memberRepository.updateMember(memberExistInfo);
            }
        } catch (FindException e) {
            memberRepository.insertMember(memberInfo);
        }
        //세션에 이메일과 토큰 값 저장
        session.setAttribute("memberEmail", memberInfo.getMemberEmail());
        session.setAttribute("accessToken", accessToken);

        log.info("login 완료");
    }

    /**
     * 로그아웃 한다.
     * @param session 클라이언트의 세션
     */
    public void logout(HttpSession session) {
        session.invalidate();
    }

    /**
     * 멤버의 정보를 DB에 추가한다.
     * @param memberInfo 추가할 멤버 정보
     * @throws AddException 멤버 추가에 실패할 경우 발생
     */
    public void addMember(MemberDTO memberInfo) throws AddException {
        memberInfo.setMemberPoint(MEMBER_INITIAL_POINT);
        memberRepository.insertMember(memberInfo);
    }

    /**
     * DB의 멤버의 정보를 수정한다.
     * @param memberInfo 추가할 멤버 정보
     * @throws ModifyException 멤버 정보 수정에 실패할 경우 발생
     */
    public void modifyMember(MemberDTO memberInfo) throws ModifyException {
        memberRepository.updateMember(memberInfo);
    }

    /**
     * 멤버 관련 정보를 DB에서 삭제한다.
     * @param session 클라이언트의 세션
     * @param memberEmail 삭제할 멤버의 이메일
     * @throws RemoveException 멤버 정보 삭제에 실패할 경우 발생
     */
    public void removeMember(HttpSession session, String memberEmail) throws RemoveException {
        memberRepository.deleteMember(memberEmail);
        session.invalidate();
    }

    /**
     * 이메일로 해당 멤버의 정보를 반환한다.
     * @param memberEmail 찾고자 하는 멤버의 이메일
     * @return 멤버의 정보
     * @throws FindException 멤버 정보를 찾는데 실패할 경우 발생
     */
    public MemberDTO findMemberByMemberEmail(String memberEmail) throws FindException {
        return memberRepository.selectMemberByMemberEmail(memberEmail);
    }


}
