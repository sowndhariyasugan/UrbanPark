package com.urbanpark.park.service;

import com.urbanpark.park.dto.ParkingSlotDTO;
import com.urbanpark.park.enums.SlotStatus;
import com.urbanpark.park.enums.SlotType;
import com.urbanpark.park.exception.SlotNotFoundException;
import com.urbanpark.park.mapper.ParkingSlotMapper;
import com.urbanpark.park.model.ParkingSlot;
import com.urbanpark.park.repository.ParkingSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor   // Automatically injects final dependencies
@Transactional              // Ensures DB consistency for write operations
public class ParkingSlotService {

    private final ParkingSlotRepository parkingSlotRepository;
    private final ParkingSlotMapper parkingSlotMapper;

    // 1. Create a new parking slot
    public ParkingSlot createSlot(ParkingSlot slot) {
        slot.setLastUpdated(LocalDateTime.now());
        return parkingSlotRepository.save(slot);
    }

    // 2. Fetch all parking slots
    @Transactional(readOnly = true)
    public List<ParkingSlotDTO> getAllSlots() {
        return parkingSlotRepository.findAll()
                .stream()
                .map(ParkingSlotMapper::toDTO)
                .collect(Collectors.toList());
    }

    // 3. Get a slot by its ID
    @Transactional(readOnly = true)
    public ParkingSlotDTO getSlotById(Integer slotId) {
        ParkingSlot slot = parkingSlotRepository.findById(slotId)
                .orElseThrow(() -> new SlotNotFoundException("Slot not found with ID: " + slotId));
        return ParkingSlotMapper.toDTO(slot);
    }


    // 🔹 4. Get slots by type
    @Transactional(readOnly = true)
    public List<ParkingSlot> getSlotsByType(SlotType type) {
        return parkingSlotRepository.findByType(type);
    }

    // 🔹 5. Get slots by status
    @Transactional(readOnly = true)
    public List<ParkingSlot> getSlotsByStatus(SlotStatus status) {
        return parkingSlotRepository.findByStatus(status);
    }

    // 🔹 6. Get slots by both type and status
    @Transactional(readOnly = true)
    public List<ParkingSlot> getSlotsByTypeAndStatus(SlotType type, SlotStatus status) {
        return parkingSlotRepository.findByTypeAndStatus(type, status);
    }

    // 🔹 7. Find the first available slot for a given type
    @Transactional(readOnly = true)
    public Optional<ParkingSlot> getFirstAvailableSlot(SlotType type) {
        return parkingSlotRepository.findFirstByTypeAndStatus(type, SlotStatus.AVAILABLE);
    }

    // 🔹 8. Update slot status (ex: when booking or freeing a slot)
    public ParkingSlot updateSlotStatus(Integer slotId, SlotStatus newStatus) {
        ParkingSlot slot = parkingSlotRepository.findById(slotId)
                .orElseThrow(() -> new IllegalArgumentException("Slot not found with ID: " + slotId));

        slot.setStatus(newStatus);
        slot.setLastUpdated(LocalDateTime.now());
        return parkingSlotRepository.save(slot);
    }

    // 🔹 9. Delete a slot
    public void deleteSlot(Integer slotId) {
        if (!parkingSlotRepository.existsById(slotId)) {
            throw new IllegalArgumentException("Slot not found with ID: " + slotId);
        }
        parkingSlotRepository.deleteById(slotId);
    }

}
