package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Bill;
import com.example.restaurant_management_system.service.BillService;
import jakarta.validation.Valid; // Correct import for validation
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String createBill(@Valid @ModelAttribute Bill bill,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "bill/form";
        }

        billService.addBill(bill);
        return "redirect:/bill";
    }


    // POST /bill/{id}/delete → delete a bill by ID
    @PostMapping("/{id}/delete")
    public String deleteBill(@PathVariable String id) {
        billService.deleteBill(Long.valueOf(id));
        return "redirect:/bill";
    }

    // GET /bill/{id}/edit → show edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Bill bill = billService.getBillById(Long.valueOf(id));
        model.addAttribute("bill", bill);
        return "bill/form";
    }

    // POST /bill/{id}/update → handle edit submission
    @PostMapping("/{id}/update")
    public String updateBill(@PathVariable Long id,
                             @Valid @ModelAttribute Bill bill,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "bill/form";
        }

        bill.setId(id);
        billService.updateBill(bill);
        return "redirect:/bill";
    }


}
