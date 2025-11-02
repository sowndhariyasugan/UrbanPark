package com.urbanpark.park.service;

import com.urbanpark.park.enums.PaymentStatus;
import com.urbanpark.park.model.Bill;
import com.urbanpark.park.model.ParkingRecord;
import com.urbanpark.park.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BillService {

    private final BillRepository billRepository;

    // 🔹 1. Create a new bill for a parking record
    public Bill createBill(ParkingRecord parkingRecord, BigDecimal amount, BigDecimal durationHours) {
        Bill bill = Bill.builder()
                .parkingRecord(parkingRecord)
                .amount(amount)
                .durationHours(durationHours)
                .paymentStatus(PaymentStatus.PENDING)
                .generatedAt(LocalDateTime.now())
                .dateRecord(LocalDate.now())
                .build();
        return billRepository.save(bill);
    }

    // 🔹 2. Get all bills
    @Transactional(readOnly = true)
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    // 🔹 3. Get bill by ID
    @Transactional(readOnly = true)
    public Optional<Bill> getBillById(Integer billId) {
        return billRepository.findById(billId);
    }

    // 🔹 4. Get bill by parking record
    @Transactional(readOnly = true)
    public Optional<Bill> getBillByParkingRecord(ParkingRecord parkingRecord) {
        return billRepository.findByParkingRecord(parkingRecord);
    }

    // 🔹 5. Get bills by payment status
    @Transactional(readOnly = true)
    public List<Bill> getBillsByPaymentStatus(PaymentStatus status) {
        return billRepository.findByPaymentStatus(status);
    }

    // 🔹 6. Get unpaid bills (PENDING or FAILED)
    @Transactional(readOnly = true)
    public List<Bill> getUnpaidBills() {
        return billRepository.findUnpaidBills();
    }

    // 🔹 7. Get bills generated on a specific date
    @Transactional(readOnly = true)
    public List<Bill> getBillsByDate(LocalDate date) {
        return billRepository.findByDateRecord(date);
    }

    // 🔹 8. Update payment status of a bill
    public Bill updatePaymentStatus(Integer billId, PaymentStatus status) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found with ID: " + billId));

        bill.setPaymentStatus(status);
        return billRepository.save(bill);
    }

    // 🔹 9. Attach PDF link after generation
    public Bill attachPdfLink(Integer billId, String pdfLink) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found with ID: " + billId));

        bill.setPdfLink(pdfLink);
        return billRepository.save(bill);
    }

    // 🔹 10. Calculate total revenue for a specific date
    @Transactional(readOnly = true)
    public BigDecimal getTotalRevenueByDate(LocalDate date) {
        BigDecimal total = billRepository.findTotalRevenueByDate(date);
        return total != null ? total : BigDecimal.ZERO;
    }

    // 🔹 11. Count bills by payment status (dashboard analytics)
    @Transactional(readOnly = true)
    public long countBillsByPaymentStatus(PaymentStatus status) {
        return billRepository.countByPaymentStatus(status);
    }

    // 🔹 12. Delete a bill (for rollback/admin cleanup)
    public void deleteBill(Integer billId) {
        if (!billRepository.existsById(billId)) {
            throw new IllegalArgumentException("Bill not found with ID: " + billId);
        }
        billRepository.deleteById(billId);
    }
}
