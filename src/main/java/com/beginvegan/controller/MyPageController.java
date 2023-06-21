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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("mypage")
public class MyPageController {
    @Autowired
    MyPageService myPageService;

    @GetMapping("review/userEmail")
    public ResponseEntity<?> reviewList(HttpSession session) throws FindException {
        log.info("GET reviewList By userEmail 시작 ");

        Object memberEmail = session.getAttribute("memberEmail");
        if (memberEmail == null) {
            // not logged in or session expired
            log.info("User not logged in or session expired");
            return new ResponseEntity<>("User not logged in or session expired", HttpStatus.UNAUTHORIZED);
        }

        String userEmail = memberEmail.toString();

        List<ReviewDTO> reviewList = myPageService.findAllReviewByMemberEmail(userEmail);
        if (reviewList.isEmpty()) {
            // review list is empty
            log.info("No reviews found for user");
            return new ResponseEntity<>("No reviews found for user", HttpStatus.NO_CONTENT);
        }

        log.info("GET reviewList By userEmail 종료 ");
        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }

    @GetMapping("review/restaurantId/{id}")
    public ResponseEntity<?> reviewList(@PathVariable int id, HttpSession session) throws FindException {
        log.info("GET reviewList By restaurantId 시작 ");

        List<ReviewDTO> reviewList = myPageService.findAllReviewByRestaurantId(id);
        if (reviewList.isEmpty()) {
            log.info("No reviews found for restaurantId");
            return new ResponseEntity<>("No reviews found for restaurantId", HttpStatus.NO_CONTENT);
        }

        log.info("GET reviewList By restaurantId 종료 ");
        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }

    @GetMapping("review/{reviewNo}")
    public ResponseEntity<?> reviewInfo(@PathVariable int reviewNo) throws FindException {
        log.info("GET reviewInfo By reviewNo 시작 ");

        ReviewDTO review = myPageService.findReviewByReviewNo(reviewNo);
        if (review == null) {
            log.info("No review found for reviewNo");
            return new ResponseEntity<>("No review found for reviewNo", HttpStatus.NOT_FOUND);
        }

        log.info("GET reviewInfo By reviewNo 종료 ");
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @PostMapping("review")
    public ResponseEntity<?> reviewAdd(HttpSession session, @RequestPart(value = "reviewDTO") ReviewDTO reviewInfo, @RequestPart(value = "reviewImage", required = false) Optional<MultipartFile> reviewImage) throws AddException, IOException, ParseException {
        log.info("reviewAdd 시작: " + reviewInfo.getReviewNo() + "/" + reviewInfo.getReservationNo() + "/" + reviewInfo.getRestaurantNo() + "/" + reviewInfo.getMemberEmail() + "/" + reviewInfo.getReviewStar() + "/" + reviewInfo.getReviewContent() + "/" + reviewInfo.getReviewTime() + "/" + reviewInfo.getReviewPhotoDir());
        log.info(session.toString() + "/" + session.getAttribute("memberEmail")+ "!!!!");
        String userEmail = session.getAttribute("memberEmail").toString();

        log.info((userEmail) + "!!!!");
        myPageService.addReview(reviewInfo, userEmail, reviewImage);
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
        log.info("GET bookmarkList By userEmail 시작");

        Object memberEmail = session.getAttribute("memberEmail");
        if (memberEmail == null) {
            // not logged in or session expired
            log.info("User not logged in or session expired");
            return new ResponseEntity<>("User not logged in or session expired", HttpStatus.UNAUTHORIZED);
        }

        String userEmail = memberEmail.toString();

        List<BookmarkDTO> bookmarkList = myPageService.findAllBookmarkByMemberEmail(userEmail);
        if (bookmarkList.isEmpty()) {
            // review list is empty
            log.info("No bookmarks found for user");
            return new ResponseEntity<>("No bookmarks found for user", HttpStatus.NO_CONTENT);
        }

        log.info("GET bookmarkList By userEmail 종료 ");
        return new ResponseEntity<>(bookmarkList, HttpStatus.OK);
    }

    @GetMapping("point-history")
    public ResponseEntity<?> getPointHistory(HttpSession session) throws FindException {
        log.info("GET pointHistory By userEmail 시작");

        Object memberEmail = session.getAttribute("memberEmail");
        if (memberEmail == null) {
            // not logged in or session expired
            log.info("User not logged in or session expired");
            return new ResponseEntity<>("User not logged in or session expired", HttpStatus.UNAUTHORIZED);
        }

        String userEmail = memberEmail.toString();

        List<PointDTO> pointList = myPageService.findAllPointByMemberEmail(userEmail);
        if (pointList.isEmpty()) {
            // point history is empty
            log.info("No point history found for user");
            return new ResponseEntity<>("No point history found for user", HttpStatus.NO_CONTENT);
        }

        log.info("GET pointHistory By userEmail 종료");
        return new ResponseEntity<>(pointList, HttpStatus.OK);
    }

    @PostMapping("point-history")
    public ResponseEntity<?> getPointHistory(@RequestBody PointDTO pointInfo) throws FindException {
        List<PointDTO> pointList = myPageService.findAllPointByMemberEmail(pointInfo.getMemberEmail());
        return new ResponseEntity<>(pointList, HttpStatus.OK);
    }
}
