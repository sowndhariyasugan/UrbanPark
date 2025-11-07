package com.urbanpark.park.mapper;

import com.urbanpark.park.dto.ParkingRecordDTO;
import com.urbanpark.park.model.ParkingRecord;
import com.urbanpark.park.model.ParkingSlot;

public class ParkingRecordMapper {

    public static ParkingRecordDTO toDTO(ParkingRecord record) {
        if (record == null) return null;

        return ParkingRecordDTO.builder()
                .parkingId(record.getParkingId())
                .slotId(record.getSlot() != null ? record.getSlot().getSlotId() : null)
                .vehicleModel(record.getVehicleModel())
                .vehicleNumber(record.getVehicleNumber())
                .vehicleColor(record.getVehicleColor())
                .driverEmail(record.getDriverEmail())
                .driverName(record.getDriverName())
                .startTime(record.getStartTime())
                .endTime(record.getEndTime())
                .dateRecord(record.getDateRecord())
                .status(record.getStatus())
                .totalAmount(record.getTotalAmount())
                .build();
    }

    public static ParkingRecord toEntity(ParkingRecordDTO dto, ParkingSlot slot) {
        if (dto == null) return null;

        return ParkingRecord.builder()
                .parkingId(dto.getParkingId())
                .vehicleModel(dto.getVehicleModel())
                .vehicleNumber(dto.getVehicleNumber())
                .vehicleColor(dto.getVehicleColor())
                .driverEmail(dto.getDriverEmail())
                .driverName(dto.getDriverName())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .dateRecord(dto.getDateRecord())
                .status(dto.getStatus())
                .totalAmount(dto.getTotalAmount())
                .slot(slot)
                .build();
    }
}
