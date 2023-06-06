package com.beginvegan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Generated
public class RestaurantDTO {
    private int restaurantNo; // restaurant_no
    private String restaurantName; // restaurant_name
    private String restaurantAddress; // restaurant_address
    private String restaurantAddressGu; // restaurant_address_gu
    private double restaurantX; // restaurant_x
    private double restaurantY; // restaurant_y
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    private LocalTime restaurantOpen; // restaurant_open
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    private LocalTime restaurantClose; // restaurant_close
    private String restaurantDetail; // restaurant_detail
    private int restaurantAvgPrice; // restaurant_avg_price
    private int restaurantTable; // restaurant_table
    private int restaurantTableMember; // restaurant_table_member
    private int restaurantVeganLevel; // restaurant_vegan_level
    private String restaurantPhotoDir; // restaurant_photo_dir
    private double restaurantStar; // restaurant_star
    private ArrayList<MenuDTO> menuList;

}
