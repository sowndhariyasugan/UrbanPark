package com.urbanpark.park.repository;

import com.urbanpark.park.model.ParkingRecord;
import com.urbanpark.park.model.ParkingSlot;
import com.urbanpark.park.enums.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingRecordRepository extends JpaRepository<ParkingRecord, Integer> {

    // 🔹 Find records by slot
    List<ParkingRecord> findBySlot(ParkingSlot parkingSlot);

    // 🔹 Find all active records (vehicles currently parked)
    List<ParkingRecord> findByEndTimeIsNull();

    // 🔹 Find all completed records (vehicles that have exited)
    List<ParkingRecord> findByEndTimeIsNotNull();

    // 🔹 Find records between a given time range
    List<ParkingRecord> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    // 🔹 Custom Query Example — recent records
    @Query("SELECT r FROM ParkingRecord r ORDER BY r.startTime DESC LIMIT 10")
    List<ParkingRecord> findRecentRecords();

    // 🔹 Find records by vehicle number
    List<ParkingRecord> findByVehicleNumberContainingIgnoreCase(String vehicleNumber);

    // 🔹 Count records for a given slot status (useful for analytics)
    @Query("SELECT COUNT(r) FROM ParkingRecord r WHERE r.slot.status = :status")
    long countBySlotStatus(SlotStatus status);
}
