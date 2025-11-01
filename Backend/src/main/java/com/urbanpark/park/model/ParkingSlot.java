package com.urbanpark.park.model;

import com.urbanpark.park.enums.SlotStatus;
import com.urbanpark.park.enums.SlotType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "parkingslot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer slotId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotType type; // TWO_WHEELER, FOUR_WHEELER

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotStatus status; // AVAILABLE, OCCUPIED, MAINTENANCE

    @Column(nullable = false)
    private LocalDateTime lastUpdated = LocalDateTime.now();

    // --- RELATIONSHIP ---
    @OneToMany(mappedBy = "slot", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ParkingRecord> parkingRecords;
}
