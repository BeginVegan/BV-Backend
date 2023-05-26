package com.beginvegan.service;

import com.beginvegan.dto.BookmarkDTO;
import com.beginvegan.dto.ReviewDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.repository.MemberRepository;
import com.beginvegan.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("myPageService")
public class MyPageService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public List<ReviewDTO> findAllReviewByMemberEmail(String userEmail) throws FindException {
        return reviewRepository.selectAllReviewByMemberEmail(userEmail);
    }

    public List<ReviewDTO> findAllReviewByRestaurantId(int id) throws FindException {
        return reviewRepository.selectAllReviewByRestaurantId(id);
    }

    public void addReview(ReviewDTO reviewInfo) throws AddException {
        reviewRepository.insertReview(reviewInfo);
    }

    public void removeReview(int reviewNo) throws RemoveException {
        reviewRepository.deleteReview(reviewNo);
    }

    public void modifyReview(int reviewNo, ReviewDTO reviewInfo) throws ModifyException {
        reviewRepository.updateReview(reviewNo, reviewInfo);
    }

    public void addBookmark(String userEmail, int restaurantNo) throws AddException {
        memberRepository.insertBookmark(restaurantNo, userEmail);
    }

    public void removeBookmark(String userEmail, int restaurantNo) throws RemoveException {
        memberRepository.deleteBookmark(restaurantNo, userEmail);
    }

    public List<BookmarkDTO> findAllBookmarkByMemberEmail(String memberEmail) throws FindException {
        return memberRepository.selectAllBookmarkByMemberEmail(memberEmail);
    }
}
