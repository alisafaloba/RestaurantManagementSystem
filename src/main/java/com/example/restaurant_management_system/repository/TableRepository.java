package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Table;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class TableRepository implements AbstractRepository<Table> {

    private final List<Table> tables = new ArrayList<>(Arrays.asList(
            new Table("T1", 1, "Free", new ArrayList<>()),
            new Table("T2", 2, "Occupied", new ArrayList<>()),
            new Table("T3", 3, "Free", new ArrayList<>())
    ));
    //public TableRepository(List<Table> tables) {
    //    this.tables = tables;
    //}

    @Override
    public List<Table> findAll() {

        return tables;
    }

    @Override
    public Table findById(String id) {
        return tables.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
    }


    @Override
    public void save(Table table) {
        tables.add(table);
    }

    @Override
    public void delete(String number) {
        tables.removeIf(t -> t.getId().equals(number));

    }

    @Override
    public void update(Table entity) {
        tables.removeIf(t -> t.getId().equals(entity.getId()));
    }
}
