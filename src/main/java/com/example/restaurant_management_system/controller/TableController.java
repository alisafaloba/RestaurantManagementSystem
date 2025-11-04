package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Table;
import com.example.restaurant_management_system.service.TableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @PostMapping
    public String processTable(@ModelAttribute("table") Table table) {
        if (table.getId() == null || table.getId().isEmpty()) {
            table.setId(java.util.UUID.randomUUID().toString());
        }
        tableService.addTable(table);
        return "redirect:/table";
    }
    @PostMapping ("{id}/delete")
    public String deleteTable(@PathVariable String id) {
        tableService.deleteTable(id);
        return "redirect:/table";
    }
}
