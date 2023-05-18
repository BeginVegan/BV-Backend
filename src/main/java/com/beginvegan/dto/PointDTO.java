package com.beginvegan.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PointDTO {
    private String memberId; // member_id
    private String pointDiv; // point_div
    private Date pointTime; // point_time
    private int pointChange; // point_change
    private int pointResult; // point_result

}
