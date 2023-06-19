package com.beginvegan.controller;

import com.beginvegan.dto.MemberDTO;
import com.beginvegan.dto.PointDTO;
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

    /**
     * 테스트용 로그인 컨트롤러 입니다.
     * 테스트로 전달 받은 이메일이 DB에 존재하면 실제 유저의 정보를 반환하고,
     * 그렇지 않으면 더미 데이터를 임시로 반환합니다.
     * @param param 이메일 정보
     * @param session 로그인 요청 유저의 session 정보
     * @return 멤버 DTO 반환
     */
    @PostMapping("login/test")
    public ResponseEntity<?> loginTEST(@RequestBody HashMap<String, Object> param, HttpSession session) {
        MemberDTO memberInfo = memberService.loginTest(session, param);
        return new ResponseEntity<>(memberInfo, HttpStatus.OK);
    }

    /**
     * 로그인 요청 처리(카카오) 컨트롤러
     * @param param AccessToken 정보
     * @param session 로그인 요청 유저의 session 정보
     * @return 로그인 요청 유저의 멤버 정보
     * @throws AddException 멤버를 추가하는데 실패할 경우 발생
     * @throws FindException 멤버 정보를 찾는데 실패할 경우 발생
     * @throws IOException API 요청 실패시 발생
     */
    @PostMapping("login/kakao")
    public ResponseEntity<?> loginKakao(@RequestBody HashMap<String, Object> param, HttpSession session) throws AddException, FindException, IOException {
        MemberDTO memberInfo = memberService.loginKakao(session, (String) param.get("accessToken"));
        return new ResponseEntity<>(memberInfo, HttpStatus.OK);
    }

    /**
     * 로그인 요청 처리(구글) 컨트롤러
     * @param param AccessToken 정보
     * @param session 로그인 요청 유저의 session 정보
     * @return 로그인 요청 유저의 멤버 정보
     * @throws AddException 멤버를 추가하는데 실패할 경우 발생
     * @throws FindException 멤버 정보를 찾는데 실패할 경우 발생
     * @throws IOException API 요청 실패시 발생
     */
    @PostMapping("login/google")
    public ResponseEntity<?> loginGoogle(@RequestBody HashMap<String, Object> param, HttpSession session) throws AddException, FindException, IOException {
        MemberDTO memberInfo = memberService.loginGoogle(session, (String) param.get("accessToken"));
        return new ResponseEntity<>(memberInfo, HttpStatus.OK);
    }

    /**
     * 로그아웃 요청 처리 컨트롤러
     * @param session 요청 유저의 session 정보
     * @return session 을 무효화 시킨다.
     */
    @GetMapping("logout")
    public ResponseEntity<?> logout(HttpSession session) {
        memberService.logout(session);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 멤버의 정보 요청 담당 컨트롤러
     * @param session 요청 유저의 세션 정보
     * @return 요청 유저의 멤버 정보
     * @throws FindException 멤버를 찾는데 실패할 경우 발생
     */
    @GetMapping
    public ResponseEntity<?> memberDetails(HttpSession session) throws FindException {
        memberService.findMemberByMemberEmail((String) session.getAttribute("memberEmail"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 이메일 정보로 멤버의 정보를 반환한다.
     * @param memberEmail 조회할 멤버의 이메일
     * @return 요청 유저의 멤버 정보
     * @throws FindException 해당 멤버를 찾는데 실패할 경우 발생
     */
    @GetMapping("{memberEmail}")
    public ResponseEntity<?> memberInfoByMemberEmail(@PathVariable String memberEmail) throws FindException{
        return new ResponseEntity<>(memberService.findMemberByMemberEmail(memberEmail), HttpStatus.OK);
    }

    /**
     * 모든 멤버 정보를 반환한다.
     * @return 멤버 정보 리스트
     * @throws FindException 멤버 정보를 찾는데 실패할 경우 발생
     */
    @GetMapping("list")
    public ResponseEntity<?> memberList() throws FindException{
        return new ResponseEntity<>(memberService.findAllMember(), HttpStatus.OK);
    }


    /**
     * 멤버를 가입 요청 담당 컨트롤러
     * @param memberDTO 추가할 멤버의 정보
     * @return 상태 200 응답
     * @throws AddException 멤버 추가에 실패할 경우 발생
     */
    @PostMapping
    public ResponseEntity<?> memberAdd(@RequestBody MemberDTO memberDTO) throws AddException {
        memberService.addMember(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 멤버 탈퇴 요청 담당 컨트롤러(관리자 용)
     * @param memberInfo 탈퇴 처리할 멤버의 이메일
     * @return 상태 200 응답
     * @throws RemoveException 멤버 삭제에 실패할 경우 발생
     */
    @DeleteMapping("delete")
    public ResponseEntity<?> memberRemove(@RequestBody MemberDTO memberInfo) throws RemoveException {
        memberService.removeMember(memberInfo.getMemberEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 멤버 탈퇴 요청 담당 컨트롤러
     * @param session 요청 유저의 세션 정보
     * @return 상태 200 응답
     * @throws RemoveException 멤버 삭제에 실패할 경우 발생
     */
    @DeleteMapping
    public ResponseEntity<?> memberRemove(HttpSession session) throws RemoveException {
        memberService.removeMember(session, (String) session.getAttribute("memberEmail"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 멤버 정보 수정 요청 담당 컨트롤러
     * @param memberDTO 수정 내용
     * @return 상태 200 응답
     * @throws ModifyException 멤버 수정에 실패할 경우 발생
     */
    @PutMapping
    public ResponseEntity<?> memberModify(@RequestBody MemberDTO memberDTO) throws ModifyException {
        memberService.modifyMember(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 모든 멤버에게 포인트를 지급한다.
     * @param pointInfo 지급할 포인트 내역
     * @throws ModifyException 포인트 지급 실패시 발생
     */
    @PutMapping("addAllPoint")
    public ResponseEntity<?> memberPointAllModify(@RequestBody PointDTO pointInfo) throws ModifyException, AddException {
        memberService.modifyAllMemberPoint(pointInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 멤버의 프인트를 변경한다.
     * @param pointInfo 포인트 내역, 멤버 정보
     * @return 상태 200 응답
     * @throws ModifyException 권한 변경에 실패할 경우 발생
     */
    @PutMapping("modifyPoint")
    public ResponseEntity<?> memberPointModify(@RequestBody PointDTO pointInfo) throws ModifyException, AddException {
        memberService.modifyMemberPoint(pointInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 멤버의 권한을 관리자로 변경한다.
     * @param memberInfo 변경할 멤버의 계정
     * @return 상태 200 응답
     * @throws ModifyException 권한 변경에 실패할 경우 발생
     */
    @PutMapping("role/admin")
    public ResponseEntity<?> memberRoleAdminModify(@RequestBody MemberDTO memberInfo) throws ModifyException {
        memberService.modifyMemberRoleAdmin(memberInfo.getMemberEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 멤버의 권한을 회원으로 변경한다.
     * @param memberInfo 변경할 멤버의 계정
     * @return 상태 200 응답
     * @throws ModifyException 권한 변경에 실패할 경우 발생
     */
    @PutMapping("role/normal")
    public ResponseEntity<?> memberRoleNormalModify(@RequestBody MemberDTO memberInfo) throws ModifyException {
        memberService.modifyMemberRoleNormal(memberInfo.getMemberEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * 멤버의 식당 즐겨찾기 여부를 반환한다.
     * @param restaurantNo 조회할 식당 번호
     * @param session 요청 유저의 session 정보
     * @return 즐겨찾기 여부
     */
    @GetMapping("bookmark/{restaurantNo}")
    public ResponseEntity<?> isBookmark(@PathVariable String restaurantNo, HttpSession session) {
        boolean isBookmark = memberService.isMemberBookmarkedRestaurant((String) session.getAttribute("memberEmail"), restaurantNo);
        return new ResponseEntity<>(isBookmark, HttpStatus.OK);
    }
}
