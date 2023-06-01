package com.beginvegan.controller;

import com.beginvegan.dto.BookmarkDTO;
import com.beginvegan.dto.PointDTO;
import com.beginvegan.dto.ReviewDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.service.MyPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("mypage/*")
public class MyPageController {
    @Autowired
    MyPageService myPageService;

    @GetMapping("review/userEmail/")
    public ResponseEntity<?> reviewList(HttpSession session) throws FindException {
        String userEmail = session.getAttribute("memberEmail").toString();
        log.info("GET reviewList By userEmail 시작 ");

        List<ReviewDTO> reviewList = myPageService.findAllReviewByMemberEmail(userEmail);

        log.info("GET reviewList By userEmail 종료 ");
        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }

    @GetMapping("review/restaurantId/{id}")
    public ResponseEntity<?> reviewList(@PathVariable int id, HttpSession session) throws FindException {
        log.info("GET reviewList By reataurantId 시작 ");

        List<ReviewDTO> reviewList = myPageService.findAllReviewByRestaurantId(id);
        log.info("GET reviewList By reataurantId 종료 ");
        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }

    @GetMapping("review/{reviewNo}")
    public ResponseEntity<?> reviewList(@PathVariable int reviewNo) throws FindException {
        log.info("GET reviewInfo By reviewNo  시작 ");

        ReviewDTO reviewList = myPageService.findReviewByReviewNo(reviewNo);

        log.info("GET reviewInfo By reviewNo 종료 ");
        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }


    @PostMapping("review")
    public ResponseEntity<?> reviewAdd(@RequestBody ReviewDTO reviewInfo) throws AddException {
        log.info("reviewAdd 시작: " + reviewInfo.getReviewNo() + "/" + reviewInfo.getReservationNo() + "/" + reviewInfo.getRestaurantNo() + "/" + reviewInfo.getMemberEmail() + "/" + reviewInfo.getReviewStar() + "/" + reviewInfo.getReviewContent() + "/" + reviewInfo.getReviewTime() + "/" + reviewInfo.getReviewPhotoDir());

        myPageService.addReview(reviewInfo);

        log.info("reviewAdd 종료");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("review/{reviewNo}")
    public ResponseEntity<?> reviewRemove(@PathVariable int reviewNo, HttpSession session) throws RemoveException {
        log.info("reviewRemove 시작: " + reviewNo);
        myPageService.removeReview(reviewNo);
        log.info("reviewRemove 종료");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("review/{reviewNo}")
    public ResponseEntity<?> reviewModify(@PathVariable int reviewNo, @RequestBody ReviewDTO reviewInfo, HttpSession session) throws ModifyException {
        log.info("reviewModify 시작: " + reviewNo);
        myPageService.modifyReview(reviewNo, reviewInfo);
        log.info("reviewModify 종료");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("bookmark/{restaurantNo}")
    public ResponseEntity<?> bookmarkAdd(@PathVariable int restaurantNo, HttpSession session) throws AddException {
        String userEmail = session.getAttribute("memberEmail").toString();
        log.info("bookmarkAdd 시작: " + userEmail + "/" + restaurantNo);

        myPageService.addBookmark(userEmail, restaurantNo);
        log.info("bookmarkAdd 종료");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("bookmark/{restaurantNo}")
    public ResponseEntity<?> bookmarkRemove(@PathVariable int restaurantNo, HttpSession session) throws RemoveException {
        String userEmail = session.getAttribute("memberEmail").toString();
        log.info("bookmarkRemove 시작: " + userEmail + "/" + restaurantNo);

        myPageService.removeBookmark(userEmail, restaurantNo);
        log.info("bookmarkRemove 종료");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("bookmark/userEmail")
    public ResponseEntity<?> bookmarkList(HttpSession session) throws FindException {
        String userEmail = session.getAttribute("memberEmail").toString();
        log.info("GET bookmarkList By userEmail 시작 : " + userEmail);

        List<BookmarkDTO> bookmarkList = myPageService.findAllBookmarkByMemberEmail(userEmail);

        log.info("GET reviewList By userEmail 종료 ");
        return new ResponseEntity<>(bookmarkList, HttpStatus.OK);
    }

    @GetMapping("point-histroy")
    public ResponseEntity<?> getPointHistory(HttpSession session) throws FindException {
        List<PointDTO> pointList = myPageService.findAllPointByMemberEmail((String) session.getAttribute("memberEmail"));
        return new ResponseEntity<>(pointList, HttpStatus.OK);
    }
}
