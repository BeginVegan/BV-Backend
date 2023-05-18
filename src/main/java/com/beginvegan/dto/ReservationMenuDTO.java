package com.beginvegan.dto;

import lombok.*;

@Getter @Setter @ToString @RequiredArgsConstructor
public class ReservationMenuDTO extends MenuDTO{
    private int reservationNo; // reservation_no
    private int menuNo; // menu_no
    private int reservationMenuCount; // reservation_menu_count

}
