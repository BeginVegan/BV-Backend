package com.beginvegan.repository;

import com.beginvegan.dto.BookmarkDTO;
import com.beginvegan.dto.MemberDTO;
import com.beginvegan.dto.PointDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;

import java.util.List;

public interface MemberRepository {

    /**
     * 회원가입 정보를 추가한다.
     *
     * @param memberInfo 회원 가입 정보
     * @throws AddException DB에 회원 정보 추가에 실패할 경우 발생
     * @Description 이메일 중복 체크 : 동일한 이메일로 가입을 시도하는 경우 예외를 발생시킵니다.
     */
    public void insertMember(MemberDTO memberInfo) throws AddException;

    /**
     * 회원 정보 변경 사항을 반영한다.
     *
     * @param memeberInfo 회원 수정 정보
     * @throws ModifyException DB의 회원 정보 수정에 실패할 경우 발생
     * @Description 1. 회원 정보 수정 사항 반영
     * 2. 회원의 포인트 변화(적립, 사용) 반영
     */
    public void updateMember(MemberDTO memeberInfo) throws ModifyException;

    /**
     * 회원을 탈퇴한다.
     *
     * @param memberEmail 삭제할 회원의 이메일
     * @throws RemoveException DB에서 회원 정보 삭제에 실패할 겨우 발생
     */
    public void deleteMember(String memberEmail) throws RemoveException;

    /**
     * 회원의 정보를 반환한다.
     *
     * @param memberEmail 조회할 회원의 이메일
     * @return 회원 정보
     * @throws FindException DB에서 회원 정보 조회에 실패할 경우 발생 또는 데이터가 없을 경우 발생
     */
    public MemberDTO selectMemberByMemberEmail(String memberEmail) throws FindException;

    /**
     * 포인트 변경 내역을 기록합니다.
     *
     * @param pointInfo 포인트 변경 내용
     * @throws AddException DB에 포인트 변경 내역 추가에 실패할 경우 발생
     */
    public void insertPoint(PointDTO pointInfo) throws AddException;

    /**
     * 회원의 모든 포인트 내역(적립, 사용)을 가져옵니다.
     *
     * @param memberEmail 조회할 회원의 이메일
     * @return 포인트 내역
     * @throws FindException DB의 포인트 내역 조회에 실패할 경우 발생
     */
    public List<PointDTO> selectAllPointsByMemberEmail(String memberEmail) throws FindException;

    /**
     * 식당 즐겨찾기를 추가한다.
     *
     * @param restaurantNo 즐겨찾기할 식당 번호
     * @param memberEmail  즐겨찾기를 변경할 회원의 이메일
     * @throws AddException DB에 즐겨찾기 정보 추가에 실패할 경우 발생
     */
    public void insertBookmark(int restaurantNo, String memberEmail) throws AddException;

    /**
     * 식당 즐겨찾기를 취소한다.
     *
     * @param restaurantNo 즐겨찾기 취소할 식당 번호
     * @param memberEmail  즐겨찾기를 변경할 회원의 이메일
     * @throws RemoveException
     */
    public void deleteBookmark(int restaurantNo, String memberEmail) throws RemoveException;

    /**
     * 회원의 즐겨찾기 목록을 반환한다.
     *
     * @param memberEmail 즐겨찾기를 조회할 회원의 이메일
     * @return 회원의 즐겨찾기 목록
     * @throws FindException DB에서 회원의 즐겨찾기 목록 조회에 실패할 경우 발생
     */
    public List<BookmarkDTO> selectAllBookmarkByMemberEmail(String memberEmail) throws FindException;

    /**
     * 회원의 포인트 정보를 업데이트한다.
     * @param memberInfo
     * @throws ModifyException
     */
    public void updateMemberPoint(MemberDTO memberInfo) throws ModifyException;

    /**
     * 회원이 특정 식당을 즐겨찾기한 정보를 반환한다.
     * @param memberEmail
     * @param restaurantNo
     * @return
     */
    public boolean selectBookmarkByMemberEmailAndRestaurntNo(String memberEmail, String restaurantNo);
}
