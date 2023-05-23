package com.beginvegan.service;

import com.beginvegan.repository.MemberRepository;
import com.beginvegan.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("myPageService")
public class MyPageService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
}
