package com.beginvegan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data

public class ReviewDTO {
    private int reviewNo; // review_no
    private int reservationNo; // reservation_no
    private int restaurantNo; // restaurant_no
    private String memberEmail; // member_email
    private int reviewStar; // review_star
    private String reviewContent; // review_content
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reviewTime; // review_time
    private String reviewPhotoDir; // review_photo_dir
}
