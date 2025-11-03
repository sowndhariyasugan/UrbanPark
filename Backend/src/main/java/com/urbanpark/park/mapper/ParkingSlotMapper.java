package com.urbanpark.park.mapper;

import com.urbanpark.park.dto.ParkingSlotDTO;
import com.urbanpark.park.enums.SlotStatus;
import com.urbanpark.park.enums.SlotType;
import com.urbanpark.park.model.ParkingSlot;
import org.springframework.stereotype.Component;

@Component
public class ParkingSlotMapper {

    // Entity → DTO
    public static ParkingSlotDTO toDTO(ParkingSlot slot) {
        ParkingSlotDTO dto = new ParkingSlotDTO();
        dto.setSlotId(slot.getSlotId());
        dto.setType(SlotType.valueOf(slot.getType().name()));
        dto.setStatus(SlotStatus.valueOf(slot.getStatus().name()));
        dto.setLastUpdated(slot.getLastUpdated());
        return dto;
    }

    // DTO → Entity (optional, for POST/PUT)
    public static ParkingSlot toEntity(ParkingSlotDTO dto) {
        ParkingSlot slot = new ParkingSlot();
        slot.setSlotId(dto.getSlotId());
        slot.setType(dto.getType());
        slot.setStatus(dto.getStatus());
        slot.setLastUpdated(dto.getLastUpdated());
        return slot;
    }
}
