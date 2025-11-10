package com.urbanpark.park.service;

import com.urbanpark.park.enums.PaymentStatus;
import com.urbanpark.park.exception.BillNotFoundException;
import com.urbanpark.park.mapper.BillMapper;
import com.urbanpark.park.model.Bill;
import com.urbanpark.park.model.ParkingRecord;
import com.urbanpark.park.repository.BillRepository;
import com.urbanpark.park.repository.ParkingRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;
    private final ParkingRecordRepository parkingRecordRepository;
    private final BillMapper billMapper;

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Bill getBillById(Integer billId) {
        return billRepository.findById(billId)
                .orElseThrow(() -> new BillNotFoundException("Bill not found with ID: " + billId));
    }

    public Bill generateBill(Integer parkingId) {
        ParkingRecord record = parkingRecordRepository.findById(parkingId)
                .orElseThrow(() -> new RuntimeException("Parking record not found"));

        // Simple example: duration in hours and basic amount calc
        double durationHours = Duration.between(
                record.getStartTime(),
                record.getEndTime()
        ).toMinutes() / 60.0;
        BigDecimal amount = BigDecimal.valueOf(durationHours * 60); // Rs.60/hr for example

        PaymentStatus status = PaymentStatus.PENDING;

        Bill bill = Bill.builder()
                .parkingRecord(record)
                .amount(amount)
                .durationHours(BigDecimal.valueOf(durationHours))
                .pdfLink(null)
                .paymentStatus(status)
                .generatedAt(LocalDateTime.now())
                .build();

        return billRepository.save(bill);
    }

    public Bill updatePaymentStatus(Integer billId, String status) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new BillNotFoundException("Bill not found with ID: " + billId));

        bill.setPaymentStatus(PaymentStatus.valueOf(status));
        return billRepository.save(bill);
    }
}
