package com.beginvegan.dto;

import lombok.*;

@Getter @Setter @ToString
public class ReservationMenuDTO extends MenuDTO{
    private int reservationNo; // reservation_no
    private int menuNo; // menu_no
    private int reservationMenuCount; // reservation_menu_count

    public ReservationMenuDTO() {
        super();
    }
    public ReservationMenuDTO(int reservationNo, int menuNo, int reservationMenuCount) {
        this.reservationNo = reservationNo;
        this.menuNo = menuNo;
        this.reservationMenuCount = reservationMenuCount;
    }
}
