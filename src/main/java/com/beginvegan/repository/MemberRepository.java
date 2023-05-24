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
     * @param memberInfo 회원 가입 정보
     * @throws
     */
    public void insertMember(MemberDTO memberInfo) throws AddException;

    /**
     * 회원 정보를 수정한다.
     * @param memeberInfo
     * @throws ModifyException
     */
    public void updateMember(MemberDTO memeberInfo) throws ModifyException;

    public void deleteMember(String memberEmail) throws RemoveException;

    public MemberDTO selectMemberByMemberEmail(String memberEmail) throws FindException;

    public void insertPoint(PointDTO pointInfo) throws AddException;

    public List<PointDTO> selectAllPointsByMemberEmail(String memberEmail) throws FindException;

    public void insertBookmark(int restaurantNo) throws AddException;

    public void deleteBookmark(int restaurantNo) throws RemoveException;

    public List<BookmarkDTO> selectAllBookmarkByMemberEmail(String memberEmail) throws FindException;
}
