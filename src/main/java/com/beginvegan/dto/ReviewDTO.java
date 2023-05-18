package com.beginvegan.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private int reviewNo; // review_no
    private int reservationNo; // reservation_no
    private int restaurantNo; // restaurant_no
    private String memberId; // member_id
    private int reviewStar; // review_star
    private String reviewContent; // review_content
    private LocalDateTime reviewTime; // review_time
    private String reviewPhotoDir; // review_photo_dir

}
