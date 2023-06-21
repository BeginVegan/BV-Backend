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
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service("memberService")
public class MemberService {
    private static final int MEMBER_INITIAL_POINT = 100;
    private static final String MEMBER_ROLE_NORMAL = "normal";
    private static final String MEMBER_ROLE_ADMIN = "admin";


    @Autowired
    private MemberRepository memberRepository;

    // 테스트 로그인용
    @Generated
    public MemberDTO loginTest(HttpSession session, HashMap<String, Object> param) {
        MemberDTO memberInfo;
        try {
            memberInfo = memberRepository.selectMemberByMemberEmail((String) param.get("email"));
        } catch (FindException e) {
            memberInfo = new MemberDTO();
            memberInfo.setMemberEmail((String) param.get("email"));
            memberInfo.setMemberName("테스트 멤버");
            memberInfo.setMemberPoint(100);
            memberInfo.setMemberRole("테스터 : DB와 연동되지 않는 유저입니다.");
        }

        //세션에 이메일과 토큰 값 저장
        session.setAttribute("memberEmail", memberInfo.getMemberEmail());
        session.setAttribute("memberName", memberInfo.getMemberName());
        session.setAttribute("memberRole", memberInfo.getMemberRole());
        session.setAttribute("accessToken", "no accessToken");

        return memberInfo;
    }
    /**
     * 로그인 한다. KAKAO API
     * @param session 클라이언트의 세션
     * @param accessToken API accessToken
     * @throws Exception 로그인 과정 중 오류에의해 발생
     */

    public MemberDTO loginKakao(HttpSession session, String accessToken) throws AddException, FindException, IOException {
        log.info("login 시작 - Kakao API");

        // Kakao API 요청 : 멤버 정보를 가져온다.
        MemberDTO memberInfo = GetKakaoAccount.getMemberInfo(accessToken);

        // DB에 저장된 멤버 정보 조회
        try {
            MemberDTO memberExistInfo = memberRepository.selectMemberByMemberEmail(memberInfo.getMemberEmail());
            // 멤버의 닉네임 정보 업데이트
            if(!memberInfo.getMemberName().equals(memberExistInfo.getMemberName())) {
                memberExistInfo.setMemberName(memberInfo.getMemberName());
                memberRepository.updateMember(memberExistInfo);
            }
        } catch (Exception e) {
            addMember(memberInfo);
        }

        MemberDTO memberExistInfo = memberRepository.selectMemberByMemberEmail(memberInfo.getMemberEmail());
        //세션에 이메일과 토큰 값 저장
        session.setAttribute("memberEmail", memberExistInfo.getMemberEmail());
        session.setAttribute("memberName", memberExistInfo.getMemberName());
        session.setAttribute("memberRole", memberExistInfo.getMemberRole());
        session.setAttribute("accessToken", accessToken);

        log.info("login 완료 - Kakao API");

        return memberExistInfo;
    }

    /**
     * 로그인 한다. Google API
     * @param session 클라이언트의 세션
     * @param accessToken accessToken
     * @throws Exception 로그인 과정 중 오류에의해 발생
     */

    public MemberDTO loginGoogle(HttpSession session, String accessToken) throws AddException, FindException, IOException {
        log.info("login 시작 - Google API");

        // Kakao API 요청 : 멤버 정보를 가져온다.
        MemberDTO memberInfo = GetGoogleAccount.getMemberInfo(accessToken);

        // DB에 저장된 멤버 정보 조회
        try {
            MemberDTO memberExistInfo = memberRepository.selectMemberByMemberEmail(memberInfo.getMemberEmail());
            // 멤버의 닉네임 정보 업데이트
            if(!memberInfo.getMemberName().equals(memberExistInfo.getMemberName())) {
                memberExistInfo.setMemberName(memberInfo.getMemberName());
                memberRepository.updateMember(memberExistInfo);
            }
        } catch (Exception e) {
            addMember(memberInfo);
        }

        MemberDTO memberExistInfo = memberRepository.selectMemberByMemberEmail(memberInfo.getMemberEmail());
        //세션에 이메일과 토큰 값 저장
        session.setAttribute("memberEmail", memberExistInfo.getMemberEmail());
        session.setAttribute("memberName", memberExistInfo.getMemberName());
        session.setAttribute("memberRole", memberExistInfo.getMemberRole());
        session.setAttribute("accessToken", accessToken);

        log.info("login 완료 - Google API");

        return memberExistInfo;
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
        memberInfo.setMemberRole(MEMBER_ROLE_NORMAL);
        memberRepository.insertMember(memberInfo);
    }

    /**
     * 모든 멤버의 정보를 반환한다.
     * @return 모든 멤버의 정보 리스트
     * @throws FindException 멤버 정보가 없을 경우 발생
     */
    public List<MemberDTO> findAllMember() throws FindException {
        return memberRepository.selectMemberAll();
    }

    /**
     * 멤버의 포인트를 변경하고, 내역을 기록한다.
     * @param pointInfo 멤버 정보와 포인트 내역
     * @throws ModifyException 멤버 정보 수정에 실패할 경우 발생
     */
    public void modifyMemberPoint(PointDTO pointInfo) throws ModifyException, AddException {
        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail(pointInfo.getMemberEmail());
        memberInfo.setMemberPoint(pointInfo.getPointResult());
        memberRepository.updateMemberPoint(memberInfo);
        memberRepository.insertPoint(pointInfo);
    }

    /**
     * 모든 멤버의 포인트를 지급한다.
     * @param pointInfo 추가할 포인트 내역
     * @throws ModifyException 멤버 정보 수정에 실패할 경우 발생
     */
    public void modifyAllMemberPoint(PointDTO pointInfo) throws ModifyException, AddException {
        memberRepository.updateMemberPointAll(pointInfo.getPointChange());
        memberRepository.insertPointAll(pointInfo);
    }

    /**
     * 멤버의 권한 정보를 관리자로 변경한다.
     * @param memberEmail 수정할 멤버 정보
     * @throws ModifyException 멤버 정보 수정에 실패할 경우 발생
     */
    public void modifyMemberRoleAdmin(String memberEmail) throws ModifyException {
        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail(memberEmail);
        memberInfo.setMemberRole(MEMBER_ROLE_ADMIN);
        memberRepository.updateMemberRole(memberInfo);
    }

    /**
     * 멤버의 권한 정보를 회원으로 변경한다.
     * @param memberEmail 수정할 멤버 정보
     * @throws ModifyException 멤버 정보 수정에 실패할 경우 발생
     */
    public void modifyMemberRoleNormal(String memberEmail) throws ModifyException {
        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setMemberEmail(memberEmail);
        memberInfo.setMemberRole(MEMBER_ROLE_NORMAL);
        memberRepository.updateMemberRole(memberInfo);
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
     * 멤버 관련 정보를 DB에서 삭제한다.
     * @param memberEmail 삭제할 멤버의 이메일
     * @throws RemoveException 멤버 정보 삭제에 실패할 경우 발생
     */
    public void removeMember(String memberEmail) throws RemoveException {
        memberRepository.deleteMember(memberEmail);
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

    /**
     * 이메일과 식당번호로 즐겨찾기 여부를 반환한다.
     * @param memberEmail 조회할 멤버의 이메일
     * @param restaurantNo 조회할 식당 번호
     * @return 즐겨찾기 여부
     */
    public boolean isMemberBookmarkedRestaurant(String memberEmail, String restaurantNo) {
        return memberRepository.selectBookmarkByMemberEmailAndRestaurntNo(memberEmail, restaurantNo);
    }
}
