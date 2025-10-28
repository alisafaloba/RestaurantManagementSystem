package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Bill;
import com.example.restaurant_management_system.service.BillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping
    public List<Bill> getAllBills() {
        return billService.getAllBills();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable String id) {
        Bill bill = billService.getBillById(id);
        return bill != null ? ResponseEntity.ok(bill) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {
        billService.addBill(bill);
        return ResponseEntity.status(HttpStatus.CREATED).body(bill);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bill> updateBill(@PathVariable String id, @RequestBody Bill bill) {
        Bill existingBill = billService.getBillById(id);
        if (existingBill == null) {
            return ResponseEntity.notFound().build();
        }
        billService.updateBill(bill);
        return ResponseEntity.ok(bill);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable String id) {
        Bill existingBill = billService.getBillById(id);
        if (existingBill == null) {
            return ResponseEntity.notFound().build();
        }
        billService.deleteBill(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/generate/{orderNumber}")
    public ResponseEntity<Bill> generateBillForOrder(@PathVariable String orderNumber) {
        Bill bill = billService.generateBillForOrder(orderNumber);
        return bill != null ? ResponseEntity.status(HttpStatus.CREATED).body(bill) : ResponseEntity.notFound().build();
    }
}