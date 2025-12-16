package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Customer;
import com.example.restaurant_management_system.model.Order;
import com.example.restaurant_management_system.repository.CustomerRepository;
import com.example.restaurant_management_system.service.CustomerService;
import jakarta.validation.Valid; // <-- ADD VALIDATION IMPORT
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // <-- ADD VALIDATION IMPORT
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
// Removed java.util.UUID import


@Controller
@RequestMapping ("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    public CustomerController(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }


    @GetMapping
    public String listCustomers(Model model,
                                // Sortier-Parameter
                                @RequestParam(defaultValue = "id") String sortField,
                                @RequestParam(defaultValue = "asc") String sortDir,
                                // Filter-Parameter
                                @RequestParam(required = false) String nameFilter,
                                @RequestParam(required = false) String emailFilter) {

        // Daten aus dem Service abrufen (gefiltert und sortiert)
        model.addAttribute("customers",
                customerService.getAllCustomers(sortField, sortDir, nameFilter, emailFilter));

        // Sortier-Infos für das UI
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equalsIgnoreCase("asc") ? "desc" : "asc");

        // Filter-Infos, um die Werte im Filterformular beizubehalten
        model.addAttribute("nameFilter", nameFilter);
        model.addAttribute("emailFilter", emailFilter);

        return "Customer/index";
    }



    // --- CRUD-Methoden (Bleiben unverändert, aber hier zur Vollständigkeit) ---

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

    @GetMapping("/{id}/details")
    public String showCustomerDetails(@PathVariable Long id, Model model) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
        model.addAttribute("customer", customer);
        List<Order> orders = customer.getOrders();
        model.addAttribute("orders", orders);

        return "customer/details";
    }
}