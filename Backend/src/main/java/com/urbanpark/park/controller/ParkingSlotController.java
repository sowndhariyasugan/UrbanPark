package com.urbanpark.park.controller;

import com.urbanpark.park.dto.ParkingSlotDTO;
import com.urbanpark.park.service.ParkingSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")  // Base URL
@RequiredArgsConstructor
public class ParkingSlotController {

    private final ParkingSlotService parkingSlotService;

    // Endpoint 1: GET /api/v1/slots
    @GetMapping("/slots")
    public ResponseEntity<List<ParkingSlotDTO>> getAllSlots() {
        List<ParkingSlotDTO> slots = parkingSlotService.getAllSlots();
        return ResponseEntity.ok(slots);
    }

    @GetMapping("slots/{slotId}")
    public ResponseEntity<ParkingSlotDTO> getSlotById(@PathVariable("slotId") Integer slotId) {
        ParkingSlotDTO slotDTO = parkingSlotService.getSlotById(slotId);
        return ResponseEntity.ok(slotDTO);
    }

}

