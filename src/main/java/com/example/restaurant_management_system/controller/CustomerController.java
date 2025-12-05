package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Customer;
import com.example.restaurant_management_system.service.CustomerService;
import jakarta.validation.Valid; // <-- ADD VALIDATION IMPORT
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // <-- ADD VALIDATION IMPORT
import org.springframework.web.bind.annotation.*;
// Removed java.util.UUID import

@Controller
@RequestMapping ("/customer")
public class CustomerController {

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customer/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/form";
    }

    // POST /customer (Requires @Valid and error handling)
    @PostMapping
    public String createCustomer(@Valid @ModelAttribute Customer customer,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "customer/form"; // Return to form to show validation errors
        }

        // Manual ID generation is removed (handled by JPA)
        customerService.addCustomer(customer);
        return "redirect:/customer";
    }

    // POST /customer/{id}/delete (ID is now Long)
    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Long id) { // Changed type to Long
        customerService.deleteCustomer(id);
        return "redirect:/customer";
    }

    // GET /customer/{id}/edit (ID is now Long)
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) { // Changed type to Long
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "customer/form"; // Corrected case
    }

    // POST /customer/{id}/update (ID is now Long, requires @Valid)
    @PostMapping("/{id}/update")
    public String updateCustomer(@PathVariable Long id, // Changed type to Long
                                 @Valid @ModelAttribute Customer customer,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "customer/form"; // Return to form if validation fails
        }

        customer.setId(id);
        customerService.updateCustomer(customer);
        return "redirect:/customer";
    }
}