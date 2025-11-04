package com.example.restaurant_management_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.restaurant_management_system.model.Customer;
import com.example.restaurant_management_system.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping ("/customer")
public class CustomerController {

    private CustomerService customerService;
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

    @PostMapping
    public String createCustomer(@ModelAttribute Customer customer) {
        if (customer.getId() == null || customer.getId().isEmpty()) {
            customer.setId(java.util.UUID.randomUUID().toString());
        }
        customerService.addCustomer(customer);
        return "redirect:/customer";
    }

    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return "redirect:/bill";
    }
}
