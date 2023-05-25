package com.beginvegan.controller;

import com.beginvegan.dto.ReviewDTO;
import com.beginvegan.exception.FindException;
import com.beginvegan.service.MyPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("mypage/*")
public class MyPageController {
    @Autowired
    MyPageService myPageService;

    @GetMapping("review/{userEmail}")
    public ResponseEntity<?> reviewList(@PathVariable String userEmail, HttpSession session) throws FindException {
        System.out.println(userEmail);
        List<ReviewDTO> reviewList = myPageService.findAllReviewByMemberEmail(userEmail);
        System.out.println(userEmail);
        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }

}
