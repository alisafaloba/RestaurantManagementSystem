package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Table;
import org.springframework.stereotype.Repository;

@Repository
public class TableRepository extends InMemoryRepository<Table> {
    public TableRepository() {
        super(Table.class);
    }
}
