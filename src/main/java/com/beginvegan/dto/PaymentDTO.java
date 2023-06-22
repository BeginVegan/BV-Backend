package com.beginvegan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

@Builder
public class PaymentDTO {
    private String impUid; // imp_uid
    private int reservationNo; // reservation_no
    private String memberEmail; // member_email
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime paymentTime; // payment_time
    private int paymentPrice; // payment_price
    private String paymentStatus; // payment_status
}
