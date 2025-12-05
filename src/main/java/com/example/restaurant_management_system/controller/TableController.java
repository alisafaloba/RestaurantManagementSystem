package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Table;
import com.example.restaurant_management_system.service.TableService;
import jakarta.validation.Valid; // <-- ADD VALIDATION IMPORT
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // <-- ADD VALIDATION IMPORT
import org.springframework.web.bind.annotation.*;
// Removed java.util.UUID import

@Controller
@RequestMapping ("/table")
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
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
}