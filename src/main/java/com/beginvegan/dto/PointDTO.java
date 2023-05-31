package com.beginvegan.dto;

import com.beginvegan.util.TimeUtil;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter @Setter @ToString @EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor
public class PointDTO {
    private String memberEmail; // member_email
    private String pointDiv; // point_div
    private LocalDateTime pointTime; // point_time
    private int pointChange; // point_change
    private int pointResult; // point_result

    public PointDTO(String memberEmail, String pointDiv, Timestamp pointTime, int pointChange, int pointResult) {
        super();
        this.memberEmail = memberEmail;
        this.pointDiv = pointDiv;
        this.pointTime = TimeUtil.toDateTime(pointTime);
        this.pointChange = pointChange;
        this.pointResult = pointResult;
    }
}
