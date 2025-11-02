package com.urbanpark.park.repository;

import com.urbanpark.park.model.ParkingSlot;
import com.urbanpark.park.enums.SlotStatus;
import com.urbanpark.park.enums.SlotType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Integer> {

    // find all slots by vehicle type
    List<ParkingSlot> findByType(SlotType type);

    // find all slots by current status
    List<ParkingSlot> findByStatus(SlotStatus status);

    // filter by both type and status
    List<ParkingSlot> findByTypeAndStatus(SlotType type, SlotStatus status);

    // find the first available slot of a given type
    Optional<ParkingSlot> findFirstByTypeAndStatus(SlotType type, SlotStatus status);

    // check for validations
    boolean existsBySlotIdAndStatus(Integer slotId, SlotStatus status);
}
