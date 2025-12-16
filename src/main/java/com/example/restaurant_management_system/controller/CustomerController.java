package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Customer;
import com.example.restaurant_management_system.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping ("/customer")
public class CustomerController {

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
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
        return "Customer/form";
    }

    @PostMapping
    public String createCustomer(@Valid @ModelAttribute Customer customer,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Customer/form";
        }
        customerService.addCustomer(customer);
        return "redirect:/customer";
    }

    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customer";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "Customer/form";
    }

    @PostMapping("/{id}/update")
    public String updateCustomer(@PathVariable Long id,
                                 @Valid @ModelAttribute Customer customer,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Customer/form";
        }
        customer.setId(id);
        customerService.updateCustomer(customer);
        return "redirect:/customer";
    }
}