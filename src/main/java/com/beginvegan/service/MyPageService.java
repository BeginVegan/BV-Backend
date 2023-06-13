package com.beginvegan.service;

import com.beginvegan.dto.BookmarkDTO;
import com.beginvegan.dto.PointDTO;
import com.beginvegan.dto.ReviewDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.repository.MemberRepository;
import com.beginvegan.repository.ReviewRepository;
import com.beginvegan.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("myPageService")
public class MyPageService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private S3Service S3Service;


    public List<ReviewDTO> findAllReviewByMemberEmail(String userEmail) throws FindException {
        return reviewRepository.selectAllReviewByMemberEmail(userEmail);
    }

    public List<ReviewDTO> findAllReviewByRestaurantId(int id) throws FindException {
        return reviewRepository.selectAllReviewByRestaurantId(id);
    }

    public ReviewDTO findReviewByReviewNo(int reviewNo) throws FindException {
        return reviewRepository.selectReviewByReviewNo(reviewNo);
    }


    public void addReview(ReviewDTO reviewInfo, String userEmail, Optional<MultipartFile> reviewImage) throws AddException, IOException, ParseException {
        reviewInfo.setMemberEmail(userEmail);
        reviewInfo.setReviewTime(TimeUtil.toDateTime(new Date()));
        if (reviewImage.isPresent()) {
            String uploadUrl = S3Service.upload(reviewImage.get(), "review/" + reviewInfo.getReservationNo());
            reviewInfo.setReviewPhotoDir(uploadUrl);
        }
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

    /**
     * 포인트 내역을 리스트로 반환한다.
     *
     * @param memberEmail 조회할 회원의 이메일
     * @return 포인트 내역 목록
     * @throws FindException 회원 Email로 DB 조회 실패시 발생
     */
    public List<PointDTO> findAllPointByMemberEmail(String memberEmail) throws FindException {
        return memberRepository.selectAllPointsByMemberEmail(memberEmail);
    }
}
