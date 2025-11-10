package com.urbanpark.park.mapper;

import com.urbanpark.park.model.Bill;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BillMapper {

    public Map<String, Object> toDTO(Bill bill) {
        Map<String, Object> dto = new HashMap<>();
        dto.put("bill_id", bill.getBillId().toString());
        dto.put("parking_id", bill.getParkingRecord().getParkingId());
        dto.put("amount", bill.getAmount());
        dto.put("duration_hours", bill.getDurationHours());
        dto.put("pdf_link", bill.getPdfLink());
        dto.put("payment_status", bill.getPaymentStatus());
        dto.put("generated_at", bill.getGeneratedAt());
        return dto;
    }
}
