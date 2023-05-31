package com.beginvegan.dto;

import com.beginvegan.util.TimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private int reservationNo; // reservation_no
    private String memberEmail; // member_email
    private int restaurantNo; // restaurant_no
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservationTime; // reservation_time
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservationVisitTime; // reservation_visit_time
    private String reservationType; // reservation_type
    private int reservationPeople; // reservation_people
    private int reservationDiscount; // reservation_discount
    private int reservationTotalPrice; // reservation_total_price
    private String reservationStatus; // reservation_status
    private List<ReservationMenuDTO> reservationMenuList;
}

