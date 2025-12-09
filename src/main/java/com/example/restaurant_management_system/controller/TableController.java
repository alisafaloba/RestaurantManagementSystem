package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Order;
import com.example.restaurant_management_system.model.Table;
import com.example.restaurant_management_system.repository.CrudRepository;
import com.example.restaurant_management_system.repository.OrderRepository;
import com.example.restaurant_management_system.repository.TableRepository;
import com.example.restaurant_management_system.service.TableService;
import jakarta.validation.Valid; // <-- ADD VALIDATION IMPORT
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // <-- ADD VALIDATION IMPORT
import org.springframework.web.bind.annotation.*;

import java.util.List;
// Removed java.util.UUID import

@Controller
@RequestMapping ("/table")
public class TableController {

    private final TableService tableService;
    private final TableRepository tableRepository;
    private final OrderRepository orderRepository;


    public TableController(TableService tableService, TableRepository tableRepository, OrderRepository orderRepository) {
        this.tableService = tableService;
        this.tableRepository = tableRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public String showTables(Model model) {
        model.addAttribute("tables", tableService.getAllTables());
        return "table/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("table", new Table());
        return "table/form";
    }

    // POST /table (Requires @Valid and error handling)
    @PostMapping
    public String processTable(@Valid @ModelAttribute("table") Table table,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "table/form"; // Return to form to show validation errors
        }

        // Manual ID generation is removed (handled by JPA)
        tableService.addTable(table);
        return "redirect:/table";
    }

    // POST /table/{id}/delete (ID is now Long)
    @PostMapping ("/{id}/delete")
    public String deleteTable(@PathVariable Long id) { // Changed type to Long
        tableService.deleteTable(id);
        return "redirect:/table";
    }

    // GET /table/{id}/edit (ID is now Long)
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) { // Changed type to Long
        Table table = tableService.getTableById(id);
        model.addAttribute("table", table);
        return "table/form";
    }

    // POST /table/{id}/update (ID is now Long, requires @Valid)
    @PostMapping("/{id}/update")
    public String updateTable(@PathVariable Long id, // Changed type to Long
                              @Valid @ModelAttribute("table") Table table,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "table/form"; // Return to form if validation fails
        }

        table.setId(id);
        tableService.updateTable(table);
        return "redirect:/table";
    }





//    @GetMapping("/{id}/details")
//    public String showEntityDetails(@PathVariable Long id, Model model) {
//        Table table = tableService.getTableById(id);
//
//        if (table == null) {
//            return "redirect:/table";
//        }
//
//        model.addAttribute("table", table);
//        model.addAttribute("orders", table.getOrders());
//        return "table/details";
//    }

    @GetMapping("/{id}/details")
    public String getTableDetails(@PathVariable Long id, Model model) {

        Table table = tableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        List<Order> orders = orderRepository.findByTable_Id(id);

        model.addAttribute("table", table);
        model.addAttribute("orders", orders);
        model.addAttribute("totalOrders", orders.size());

        return "table/details";
    }

}