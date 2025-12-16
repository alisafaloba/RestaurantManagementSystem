package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Order;
import com.example.restaurant_management_system.model.Table;
import com.example.restaurant_management_system.model.TableStatus; // WICHTIG
import com.example.restaurant_management_system.repository.OrderRepository;
import com.example.restaurant_management_system.repository.TableRepository;
import com.example.restaurant_management_system.service.TableService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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
    public String showTables(Model model,
                             // Sortier-Parameter
                             @RequestParam(defaultValue = "id") String sortField,
                             @RequestParam(defaultValue = "asc") String sortDir,
                             // Filter-Parameter
                             @RequestParam(required = false) TableStatus statusFilter,
                             @RequestParam(required = false) String numberFilter) {

        // Daten aus dem Service abrufen (gefiltert und sortiert)
        model.addAttribute("tables",
                tableService.getAllTables(sortField, sortDir, statusFilter, numberFilter));

        // Sortier-Infos für das UI
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equalsIgnoreCase("asc") ? "desc" : "asc");

        // Filter-Infos, um die Werte im Filterformular beizubehalten
        model.addAttribute("statusFilter", statusFilter);
        model.addAttribute("numberFilter", numberFilter);

        // Alle Statuswerte für das Filter-Dropdown
        model.addAttribute("allStatuses", Arrays.asList(TableStatus.values()));

        return "table/index";
    }

    // ... (restliche CRUD-Methoden bleiben gleich) ...

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("table", new Table());
        return "table/form";
    }

    @PostMapping
    public String processTable(@Valid @ModelAttribute("table") Table table,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "table/form";
        }
        tableService.addTable(table);
        return "redirect:/table";
    }

    @PostMapping ("/{id}/delete")
    public String deleteTable(@PathVariable Long id) {
        tableService.deleteTable(id);
        return "redirect:/table";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Table table = tableService.getTableById(id);
        model.addAttribute("table", table);
        return "table/form";
    }

    @PostMapping("/{id}/update")
    public String updateTable(@PathVariable Long id,
                              @Valid @ModelAttribute("table") Table table,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "table/form";
        }

        table.setId(id);
        tableService.updateTable(table);
        return "redirect:/table";
    }

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