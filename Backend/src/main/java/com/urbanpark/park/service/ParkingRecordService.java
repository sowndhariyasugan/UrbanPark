package com.urbanpark.park.service;

import com.urbanpark.park.dto.ParkingRecordDTO;
import com.urbanpark.park.enums.RecordStatus;
import com.urbanpark.park.enums.SlotStatus;
import com.urbanpark.park.exception.RecordNotFoundException;
import com.urbanpark.park.mapper.ParkingRecordMapper;
import com.urbanpark.park.model.Bill;
import com.urbanpark.park.model.ParkingRecord;
import com.urbanpark.park.model.ParkingSlot;
import com.urbanpark.park.repository.ParkingRecordRepository;
import com.urbanpark.park.repository.ParkingSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ParkingRecordService {

    private final ParkingRecordRepository parkingRecordRepository;
    private final ParkingSlotRepository parkingSlotRepository;

    // 🔹 1. Create new parking record (vehicle entry)
    @Transactional
    public ParkingRecord createRecord(ParkingRecordDTO dto) {
        ParkingSlot slot = parkingSlotRepository.findById(dto.getSlotId())
                .orElseThrow(() -> new RecordNotFoundException("Slot not found with ID: " + dto.getSlotId()));

        ParkingRecord record = ParkingRecordMapper.toEntity(dto, slot);
        record.setStartTime(LocalDateTime.now()); // auto-set when created
        record.setStatus(dto.getStatus() != null ? dto.getStatus() : RecordStatus.ACTIVE);

        return parkingRecordRepository.save(record);
    }



    @Transactional(readOnly = true)
    public List<ParkingRecord> getRecordsBySlotAndStatus(Integer slotId, RecordStatus status) {
        return parkingRecordRepository.findAll().stream()
                .filter(r -> r.getSlot() != null &&
                        r.getSlot().getSlotId().equals(slotId) &&
                        r.getStatus() == status)
                .toList();
    }

    // 🔹 2. Get all records
    @Transactional(readOnly = true)
    public List<ParkingRecord> getAllRecords() {
        return parkingRecordRepository.findAll();
    }

    // 🔹 3. Get record by ID
    @Transactional(readOnly = true)
    public ParkingRecord getRecordById(Integer id) {
        return parkingRecordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Parking record not found with ID: " + id));
    }

    @Transactional
    public ParkingRecord updateRecord(Integer id, ParkingRecordDTO dto) {
        ParkingRecord record = parkingRecordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Parking record not found with ID: " + id));

        if (dto.getEndTime() != null) record.setEndTime(dto.getEndTime());
        if (dto.getStatus() != null) record.setStatus(dto.getStatus());
        if (dto.getTotalAmount() != null) record.setTotalAmount(dto.getTotalAmount());

        return parkingRecordRepository.save(record);
    }



    // 🔹 4. Get records by slot
    @Transactional(readOnly = true)
    public List<ParkingRecord> getRecordsBySlot(ParkingSlot slot) {
        return parkingRecordRepository.findBySlot(slot);
    }

    // 🔹 5. Get all active records (vehicles currently parked)
    @Transactional(readOnly = true)
    public List<ParkingRecord> getActiveRecords() {
        return parkingRecordRepository.findByEndTimeIsNull();
    }

    // 🔹 6. Get all completed records (vehicles exited)
    @Transactional(readOnly = true)
    public List<ParkingRecord> getCompletedRecords() {
        return parkingRecordRepository.findByEndTimeIsNotNull();
    }

    // 🔹 7. Get records between specific time range
    @Transactional(readOnly = true)
    public List<ParkingRecord> getRecordsBetween(LocalDateTime start, LocalDateTime end) {
        return parkingRecordRepository.findByStartTimeBetween(start, end);
    }

    // 🔹 8. Get recent records (latest 10)
    @Transactional(readOnly = true)
    public List<ParkingRecord> getRecentRecords() {
        return parkingRecordRepository.findRecentRecords();
    }

    // 🔹 9. Search records by vehicle number
    @Transactional(readOnly = true)
    public List<ParkingRecord> searchByVehicleNumber(String vehicleNumber) {
        return parkingRecordRepository.findByVehicleNumberContainingIgnoreCase(vehicleNumber);
    }

    // 🔹 10. Count records by slot status (useful for analytics)
    @Transactional(readOnly = true)
    public long countRecordsBySlotStatus(SlotStatus status) {
        return parkingRecordRepository.countBySlotStatus(status);
    }

    // 🔹 11. Mark a record as completed (vehicle exit)
    public ParkingRecord completeRecord(Integer recordId, BigDecimal totalAmount) {
        ParkingRecord record = parkingRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("Parking Record not found with ID: " + recordId));

        record.setEndTime(LocalDateTime.now());
        record.setStatus(RecordStatus.COMPLETED);
        record.setTotalAmount(totalAmount);

        // If Bill exists, mark it as linked
        Bill bill = record.getBill();
        if (bill != null) {
            bill.setGeneratedAt(LocalDateTime.now());
        }

        return parkingRecordRepository.save(record);
    }

    // 🔹 12. Cancel a record
    public ParkingRecord cancelRecord(Integer recordId) {
        ParkingRecord record = parkingRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("Parking Record not found with ID: " + recordId));

        record.setStatus(RecordStatus.CANCELLED);
        record.setEndTime(LocalDateTime.now());
        return parkingRecordRepository.save(record);
    }

    // 🔹 13. Delete record
    public void deleteRecord(Integer recordId) {
        if (!parkingRecordRepository.existsById(recordId)) {
            throw new IllegalArgumentException("Record not found with ID: " + recordId);
        }
        parkingRecordRepository.deleteById(recordId);
    }
}
