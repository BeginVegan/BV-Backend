package com.beginvegan.service;

import com.beginvegan.dto.ReviewDTO;
import com.beginvegan.exception.FindException;
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

}
