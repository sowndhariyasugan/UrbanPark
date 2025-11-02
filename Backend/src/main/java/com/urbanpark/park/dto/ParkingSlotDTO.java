package com.urbanpark.park.dto;

import com.urbanpark.park.enums.SlotStatus;
import com.urbanpark.park.enums.SlotType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingSlotDTO {
    private Integer slotId;
    private SlotType type;
    private SlotStatus status;
    private LocalDateTime lastUpdated;
}
