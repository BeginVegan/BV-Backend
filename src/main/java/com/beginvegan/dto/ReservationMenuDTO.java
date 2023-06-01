package com.beginvegan.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ReservationMenuDTO extends MenuDTO{
    private int reservationNo; // reservation_no
    private int menuNo; // menu_no
    private int reservationMenuCount; // reservation_menu_count

    public ReservationMenuDTO(int menuNo, int restaurantNo, String menuName, int menuPrice, String menuCategory, String menuDetail, String menuPhotoDir, int reservationNo, int reservationMenuCount) {
        super(menuNo, restaurantNo, menuName, menuPrice, menuCategory, menuDetail, menuPhotoDir);
        this.reservationNo = reservationNo;
        this.menuNo = menuNo;
        this.reservationMenuCount = reservationMenuCount;
    }
}
