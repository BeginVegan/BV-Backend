package com.beginvegan.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewDTO {
    private int reviewNo; // review_no
    private int reservationNo; // reservation_no
    private int restaurantNo; // restaurant_no
    private String memberEmail; // member_email
    private int reviewStar; // review_star
    private String reviewContent; // review_content
    private Date reviewTime; // review_time
    private String reviewPhotoDir; // review_photo_dir

}
