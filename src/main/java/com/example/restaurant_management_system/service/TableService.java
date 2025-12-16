package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.Table;
import com.example.restaurant_management_system.model.TableStatus; // Wichtig
import com.example.restaurant_management_system.repository.TableRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TableService {

    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    /**
     * Ruft alle Tische ab, wendet Sortierung und Filterung an.
     */
    public List<Table> getAllTables(String sortField, String sortDir,
                                    TableStatus statusFilter, String numberFilter) {

        Sort sort = Sort.by(sortField);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();

        return tableRepository.findFilteredTables(statusFilter, numberFilter, sort);
    }

    // Die ursprüngliche getAllTables() wird beibehalten, aber auf die neue Methode umgeleitet
    public List<Table> getAllTables() {
        return getAllTables("id", "asc", null, null);
    }

    // --- CRUD-Methoden ---

    public Table getTableById(Long id) {
        return tableRepository.findById(id).orElse(null);
    }

    public void addTable(Table table) {
        tableRepository.save(table);
    }

    public void updateTable(Table table) {
        tableRepository.save(table);
    }

    public void deleteTable(Long id) {
        tableRepository.deleteById(id);
    }
}