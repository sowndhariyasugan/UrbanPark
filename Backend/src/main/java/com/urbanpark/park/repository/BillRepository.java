package com.urbanpark.park.repository;

import com.urbanpark.park.model.Bill;
import com.urbanpark.park.enums.PaymentStatus;
import com.urbanpark.park.model.ParkingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

    // 🔹 Find bill by associated parking record
    Optional<Bill> findByParkingRecord(ParkingRecord parkingRecord);

    // 🔹 Find all bills with a given payment status
    List<Bill> findByPaymentStatus(PaymentStatus paymentStatus);

    // 🔹 Find all bills generated on a specific date
    List<Bill> findByDateRecord(LocalDate dateRecord);

    // 🔹 Find unpaid bills (PENDING or FAILED)
    @Query("SELECT b FROM Bill b WHERE b.paymentStatus <> 'PAID'")
    List<Bill> findUnpaidBills();

    // 🔹 Total revenue on a specific date
    @Query("SELECT SUM(b.amount) FROM Bill b WHERE b.paymentStatus = 'PAID' AND b.dateRecord = :date")
    BigDecimal findTotalRevenueByDate(LocalDate date);

    // 🔹 Count bills by payment status (for dashboard analytics)
    @Query("SELECT COUNT(b) FROM Bill b WHERE b.paymentStatus = :status")
    long countByPaymentStatus(PaymentStatus status);
}
