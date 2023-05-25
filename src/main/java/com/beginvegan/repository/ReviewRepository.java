package com.beginvegan.repository;

import com.beginvegan.dto.ReviewDTO;
import com.beginvegan.exception.FindException;

import java.util.List;

public interface ReviewRepository {
    /**
     * 로그인 유저의 전체 리뷰를 조회한다
     *
     * @return 리뷰의 리스트
     * @throws FindException Review테이블에 데이터가 없을때 발생
     */
    public List<ReviewDTO> selectAllReviewByMemberEmail(String userEmail) throws FindException;
}
