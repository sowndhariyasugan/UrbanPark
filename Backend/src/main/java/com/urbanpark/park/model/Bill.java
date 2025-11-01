package com.urbanpark.park.model;

import com.urbanpark.park.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billId;

    // --- RELATIONSHIP with ParkingRecord ---
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_id", nullable = false)
    private ParkingRecord parkingRecord;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(precision = 5, scale = 2)
    private BigDecimal durationHours;

    @Column(length = 255)
    private String pdfLink;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // PENDING, PAID, FAILED

    private LocalDateTime generatedAt = LocalDateTime.now();
    private LocalDate dateRecord;
}
