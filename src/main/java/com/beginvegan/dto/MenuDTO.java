package com.beginvegan.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@ToString
@EqualsAndHashCode
public class MenuDTO {
    private int menuNo; // menu_no
    private int restaurantNo; // restaurant_no
    private String menuName; // menu_name
    private int menuPrice; // menu_price
    private String menuCategory; // menu_category
    private String menuDetail; // menu_detail
    private String menuPhotoDir; // menu_photo_dir
}
