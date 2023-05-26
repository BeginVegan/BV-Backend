package com.beginvegan.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class PointDTO {
    private String memberEmail; // member_email
    private String pointDiv; // point_div
    private Timestamp pointTime; // point_time
    private int pointChange; // point_change
    private int pointResult; // point_result

}
