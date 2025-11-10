package com.urbanpark.park.controller;

import com.urbanpark.park.mapper.BillMapper;
import com.urbanpark.park.model.Bill;
import com.urbanpark.park.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;
    private final BillMapper billMapper;

    // 1️⃣ GET /bills
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllBills() {
        List<Bill> bills = billService.getAllBills();
        return ResponseEntity.ok(bills.stream().map(billMapper::toDTO).toList());
    }

    // 2️⃣ GET /bills/{bill_id}
    @GetMapping("/{bill_id}")
    public ResponseEntity<Map<String, Object>> getBillById(@PathVariable Integer bill_id) {
        Bill bill = billService.getBillById(bill_id);
        return ResponseEntity.ok(billMapper.toDTO(bill));
    }

    // 3️⃣ POST /bills/generate
    @PostMapping("/generate")
    public ResponseEntity<Map<String, Object>> generateBill(@RequestBody Map<String, String> request) {
        Bill bill = billService.generateBill(Integer.valueOf(request.get("parking_id")));
        return ResponseEntity.ok(billMapper.toDTO(bill));
    }

    // 4️⃣ PUT /bills/{bill_id}/pay
    @PutMapping("/{bill_id}/pay")
    public ResponseEntity<Map<String, Object>> updatePaymentStatus(
            @PathVariable Integer    bill_id,
            @RequestBody Map<String, String> body) {
        Bill updated = billService.updatePaymentStatus(bill_id, body.get("payment_status"));
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Payment confirmed");
        response.put("bill_id", updated.getBillId());
        return ResponseEntity.ok(response);
    }
}
