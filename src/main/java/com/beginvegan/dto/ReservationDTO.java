package com.beginvegan.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class ReservationDTO {
    private int reservationNo; // reservation_no
    private String memberEmail; // member_email
    private int restaurantNo; // restaurant_no
    private Date reservationTime; // reservation_time
    private Date reservationVisitTime; // reservation_visit_time
    private String reservationType; // reservation_type
    private int reservationPeople; // reservation_people
    private int reservationDiscount; // reservation_discount
    private int reservationTotalPrice; // reservation_total_price
    private String reservationStatus; // reservation_status
    private ArrayList<ReservationMenuDTO> reservationMenuList;

}
