package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.Table;
import com.example.restaurant_management_system.repository.TableRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TableService {

    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    // Change ID type from String to Long, and use JpaRepository.findById
    public Table getTableById(Long id) {
        return tableRepository.findById(id).orElse(null);
    }

    public void addTable(Table table) {
        tableRepository.save(table);
    }

    public void updateTable(Table table) {
        // JpaRepository.save() handles both insert and update
        tableRepository.save(table);
    }

    // Change ID type from String to Long, and use JpaRepository.deleteById
    public void deleteTable(Long id) {
        tableRepository.deleteById(id);
    }
}