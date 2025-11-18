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

    public Table getTableById(String number) {
        return tableRepository.findById(number);
    }

    public void addTable(Table table) {
        tableRepository.save(table);
    }

    public void updateTable(Table table) {
        tableRepository.update(table);
    }

    public void deleteTable(String number) {
        tableRepository.delete(number);
    }

}
