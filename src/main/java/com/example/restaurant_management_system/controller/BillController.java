package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Bill;
import com.example.restaurant_management_system.service.BillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    // GET all bills
    @GetMapping
    public List<Bill> getAllBills() {
        return billService.getAllBills();
    }

    // GET a bill by ID
    @GetMapping("/get/{id}")  // <--- explicitly add /get/{id} to avoid conflicts
    public ResponseEntity<Bill> getBillById(@PathVariable String id) {
        Bill bill = billService.getBillById(id);
        if (bill == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bill);
    }

    // POST a new bill
    @PostMapping
    public ResponseEntity<Bill> addBill(@RequestBody Bill bill) {
        billService.addBill(bill);
        return ResponseEntity.ok(bill);
    }

    // PUT update a bill
    @PutMapping("/{id}")
    public ResponseEntity<Bill> updateBill(@PathVariable String id, @RequestBody Bill bill) {
        bill.setId(id);
        billService.updateBill(bill);
        return ResponseEntity.ok(bill);
    }

    // DELETE a bill
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable String id) {
        billService.deleteBill(id);
        return ResponseEntity.noContent().build();
    }

    // POST generate a bill for an order
    @PostMapping("/generate/{orderId}")
    public ResponseEntity<Bill> generateBill(@PathVariable String orderId) {
        Bill bill = billService.generateBillForOrder(orderId);
        if (bill == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bill);
    }
}
