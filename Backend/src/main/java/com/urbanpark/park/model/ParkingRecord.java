package com.urbanpark.park.model;

import com.urbanpark.park.enums.RecordStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "parkingrecord")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer parkingId;

    @Column(length = 100)
    private String vehicleModel;

    @Column(length = 20)
    private String vehicleNumber;

    @Column(length = 50)
    private String vehicleColor;

    @Column(length = 100)
    private String driverEmail;

    @Column(length = 100)
    private String driverName;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private LocalDate dateRecord;

    @Enumerated(EnumType.STRING)
    private RecordStatus status; // ACTIVE, COMPLETED, CANCELLED

    private BigDecimal totalAmount;

    // --- RELATIONSHIP with Bill ---
    @OneToOne(mappedBy = "parkingRecord", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Bill bill;

    // --- RELATIONSHIP with Slot ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id")
    private ParkingSlot slot;
}
