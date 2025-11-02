package com.urbanpark.park.dto;

import com.urbanpark.park.enums.RecordStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingRecordDTO {
    private Integer parkingId;
    private Integer slotId;          // reference only, not entire ParkingSlot object
    private String vehicleModel;
    private String vehicleNumber;
    private String vehicleColor;
    private String driverEmail;
    private String driverName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDate dateRecord;
    private RecordStatus status;
    private BigDecimal totalAmount;
}
