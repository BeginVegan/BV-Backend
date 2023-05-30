package com.beginvegan.dto;

import com.beginvegan.util.TimeUtil;
import lombok.Data;
import lombok.NonNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class ReservationDTO {
    @NonNull
    private int reservationNo; // reservation_no
    @NonNull
    private String memberEmail; // member_email
    @NonNull
    private int restaurantNo; // restaurant_no
    @NonNull
    private LocalDateTime reservationTime; // reservation_time
    @NonNull
    private LocalDateTime reservationVisitTime; // reservation_visit_time
    @NonNull
    private String reservationType; // reservation_type
    @NonNull
    private int reservationPeople; // reservation_people
    @NonNull
    private int reservationDiscount; // reservation_discount
    @NonNull
    private int reservationTotalPrice; // reservation_total_price
    @NonNull
    private String reservationStatus; // reservation_status
    private ArrayList<ReservationMenuDTO> reservationMenuList;

    public ReservationDTO(int reservationNo, String memberEmail, int restaurantNo, LocalDateTime reservationTime, LocalDateTime reservationVisitTime, String reservationType, int reservationPeople, int reservationDiscount, int reservationTotalPrice, String reservationStatus) {
        this.reservationNo = reservationNo;
        this.memberEmail = memberEmail;
        this.restaurantNo = restaurantNo;
        this.reservationTime = reservationTime;
        this.reservationVisitTime = reservationVisitTime;
        this.reservationType = reservationType;
        this.reservationPeople = reservationPeople;
        this.reservationDiscount = reservationDiscount;
        this.reservationTotalPrice = reservationTotalPrice;
        this.reservationStatus = reservationStatus;
    }

    public ReservationDTO(String memberEmail, int restaurantNo, LocalDateTime reservationTime, LocalDateTime reservationVisitTime, String reservationType, int reservationPeople, int reservationDiscount, int reservationTotalPrice, String reservationStatus) {
        this.memberEmail = memberEmail;
        this.restaurantNo = restaurantNo;
        this.reservationTime = reservationTime;
        this.reservationVisitTime = reservationVisitTime;
        this.reservationType = reservationType;
        this.reservationPeople = reservationPeople;
        this.reservationDiscount = reservationDiscount;
        this.reservationTotalPrice = reservationTotalPrice;
        this.reservationStatus = reservationStatus;
    }
}
