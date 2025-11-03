package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Bill;
import com.example.restaurant_management_system.service.BillService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bill")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    // GET /bill → show all bills
    @GetMapping
    public String listBills(Model model) {
        model.addAttribute("bills", billService.getAllBills());
        return "bill/index"; // corresponds to templates/bill/index.html
    }

    // GET /bill/new → show form for creating a new bill
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("bill", new Bill());
        return "bill/form"; // corresponds to templates/bill/form.html
    }

    // POST /bill → handle form submission
    @PostMapping
    public String createBill(@ModelAttribute Bill bill) {
        // Ensure ID is set manually (since no DB auto-generation)
        if (bill.getId() == null || bill.getId().isEmpty()) {
            bill.setId(java.util.UUID.randomUUID().toString());
        }
        billService.addBill(bill);
        return "redirect:/bill";
    }

    // POST /bill/{id}/delete → delete a bill by ID
    @PostMapping("/{id}/delete")
    public String deleteBill(@PathVariable String id) {
        billService.deleteBill(id);
        return "redirect:/bill";
    }
}
