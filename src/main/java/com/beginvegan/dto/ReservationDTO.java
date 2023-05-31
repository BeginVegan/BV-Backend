package com.beginvegan.dto;

import com.beginvegan.util.TimeUtil;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
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
    private List<ReservationMenuDTO> reservationMenuList;

    public ReservationDTO() {
        super();
    }

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

    public ReservationDTO(int reservationNo, String memberEmail, int restaurantNo, Timestamp reservationTime, Timestamp reservationVisitTime, String reservationType, int reservationPeople, int reservationDiscount, int reservationTotalPrice, String reservationStatus, List<ReservationMenuDTO> reservationMenuList) {
        this.reservationNo = reservationNo;
        this.memberEmail = memberEmail;
        this.restaurantNo = restaurantNo;
        this.reservationTime = TimeUtil.toDateTime(reservationTime);
        this.reservationVisitTime = TimeUtil.toDateTime(reservationVisitTime);
        this.reservationType = reservationType;
        this.reservationPeople = reservationPeople;
        this.reservationDiscount = reservationDiscount;
        this.reservationTotalPrice = reservationTotalPrice;
        this.reservationStatus = reservationStatus;
        this.reservationMenuList = reservationMenuList;
    }
}

