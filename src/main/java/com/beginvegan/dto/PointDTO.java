package com.beginvegan.dto;

import com.beginvegan.util.TimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Generated
@Builder
public class PointDTO {
    private String memberEmail; // member_email
    private String pointDiv; // point_div
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
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
