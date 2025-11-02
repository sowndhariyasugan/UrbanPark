package com.urbanpark.park.dto;

import com.urbanpark.park.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillDTO {
    private Integer billId;
    private Integer parkingRecordId;  // reference to ParkingRecord only
    private BigDecimal amount;
    private BigDecimal durationHours;
    private String pdfLink;
    private PaymentStatus paymentStatus;
    private LocalDateTime generatedAt;
    private LocalDate dateRecord;
}
