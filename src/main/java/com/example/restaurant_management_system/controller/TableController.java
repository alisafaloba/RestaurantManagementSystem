package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Table;
import com.example.restaurant_management_system.service.TableService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping("/test-tables")
    @ResponseBody
    public String testTables() {
        // Create some sample tables (orders = empty for now)
//        Table t1 = new Table("T1", 1, "Free", new ArrayList<>());
//        Table t2 = new Table("T2", 2, "Occupied", new ArrayList<>());
//        Table t3 = new Table("T3", 3, "Free", new ArrayList<>());

        // Add tables
        tableService.addTable(t1);
        tableService.addTable(t2);
        tableService.addTable(t3);

        // Fetch all tables
        List<Table> allTables = tableService.getAllTables();

        StringBuilder sb = new StringBuilder("All tables in repository:<br>");
        allTables.forEach(t -> sb.append(t).append("<br>"));

        // Fetch one by table ID
        Table fetched = tableService.getTableByNumber("T2");
        sb.append("<br>Fetched table with ID T2: ").append(fetched).append("<br>");

        // Show all free tables (filter done HERE)
//        List<Table> freeTables = allTables.stream()
//                .filter(t -> "Free".equalsIgnoreCase(t.getStatus()))
//                .collect(Collectors.toList());

//
        // Delete one table
        tableService.deleteTable("T3");
        sb.append("<br>After deleting table with ID T3:<br>");
        tableService.getAllTables().forEach(t -> sb.append(t).append("<br>"));

        sb.append("<br>TableService test completed.");

        return sb.toString();
    }
}
