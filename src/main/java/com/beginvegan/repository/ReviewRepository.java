package com.beginvegan.repository;

import com.beginvegan.dto.ReviewDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;

import java.util.List;

public interface ReviewRepository {
    /**
     * 로그인 유저의 전체 리뷰를 조회한다
     *
     * @return 리뷰의 리스트
     * @throws FindException Review테이블에 데이터가 없을때 발생
     */
    public List<ReviewDTO> selectAllReviewByMemberEmail(String userEmail) throws FindException;


    /**
     * 해당 식당의 전체 리뷰를 조회한다
     *
     * @return 리뷰의 리스트
     * @throws FindException Review테이블에 데이터가 없을때 발생
     */
    public List<ReviewDTO> selectAllReviewByRestaurantId(int id) throws FindException;

    /**
     * ReviewNo를 이용해 리뷰를 조회한다
     *
     * @param reviewNo
     * @return 하나의 리뷰
     * @throws FindException Review테이블에 데이터가 없을때 발생
     */
    public ReviewDTO selectReviewByReviewNo(int reviewNo) throws FindException;

    /**
     * 리뷰를 등록한다
     *
     * @param reviewInfo
     * @throws AddException 데이터 추가가 실패하면 발생
     */
    public void insertReview(ReviewDTO reviewInfo) throws AddException;


    /**
     * 리뷰를 삭제한다
     *
     * @param reviewNo
     * @throws AddException 데이터 추가가 실패하면 발생
     */
    public void deleteReview(int reviewNo) throws RemoveException;

    /**
     * 리뷰를 수정한다
     *
     * @param reviewNo
     * @param reviewInfo
     * @throws ModifyException 데이터 수정이 실패하면 발생
     */
    public void updateReview(int reviewNo, ReviewDTO reviewInfo) throws ModifyException;
}
