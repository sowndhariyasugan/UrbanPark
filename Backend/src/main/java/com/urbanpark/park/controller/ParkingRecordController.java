package com.urbanpark.park.controller;

import com.urbanpark.park.dto.ParkingRecordDTO;
import com.urbanpark.park.enums.RecordStatus;
import com.urbanpark.park.mapper.ParkingRecordMapper;
import com.urbanpark.park.model.ParkingRecord;
import com.urbanpark.park.service.ParkingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/v1/parking-records")
@RequiredArgsConstructor
public class ParkingRecordController {

    private final ParkingRecordService parkingRecordService;

    // Get all parking records
    @GetMapping
    public ResponseEntity<List<ParkingRecordDTO>> getAllRecords() {
        List<ParkingRecordDTO> records = parkingRecordService.getAllRecords()
                .stream()
                .map(ParkingRecordMapper::toDTO)
                .collect(toList());

        return ResponseEntity.ok(records);
    }

    // Get parking record by ID
    @GetMapping("/{id}")
    public ResponseEntity<ParkingRecordDTO> getRecordById(@PathVariable Integer id) {
        ParkingRecord record = parkingRecordService.getRecordById(id);
        return ResponseEntity.ok(ParkingRecordMapper.toDTO(record));
    }


    // Get parking record by Slot ID and Status
    @GetMapping("/filter")
    public ResponseEntity<List<ParkingRecordDTO>> getRecordsBySlotAndStatus(
            @RequestParam Integer slotId,
            @RequestParam RecordStatus status
    ) {
        System.out.println(slotId+" "+status);
        List<ParkingRecordDTO> records = parkingRecordService.getRecordsBySlotAndStatus(slotId, status)
                .stream()
                .map(ParkingRecordMapper::toDTO)
                .collect(toList());

        return ResponseEntity.ok(records);
    }

    @PostMapping
    public ResponseEntity<ParkingRecordDTO> createRecord(@RequestBody ParkingRecordDTO dto) {
        ParkingRecord savedRecord = parkingRecordService.createRecord(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ParkingRecordMapper.toDTO(savedRecord));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingRecordDTO> updateRecord(
            @PathVariable Integer id,
            @RequestBody ParkingRecordDTO dto) {

        ParkingRecord updatedRecord = parkingRecordService.updateRecord(id, dto);
        return ResponseEntity.ok(ParkingRecordMapper.toDTO(updatedRecord));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteRecord(@PathVariable Integer id) {
        parkingRecordService.deleteRecord(id);
        return ResponseEntity.ok(Map.of("message", "Record deleted successfully"));
    }


}
