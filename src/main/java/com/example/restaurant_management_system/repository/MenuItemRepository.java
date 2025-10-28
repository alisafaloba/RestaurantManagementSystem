package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.MenuItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class MenuItemRepository implements AbstractRepository<MenuItem> {

    private final List<MenuItem> items = new ArrayList<>(Arrays.asList(
            new MenuItem("M1", "Pizza", 35.5),
            new MenuItem("M2", "Pasta", 27.0),
            new MenuItem("M3", "Salad", 18.5)
    ));

    @Override
    public void save(MenuItem entity) {
        items.add(entity);
    }

    @Override
    public void delete(String id) {
        items.removeIf(i -> i.getId().equals("M" + id));
    }

    @Override
    public void update(MenuItem entity) {
        items.removeIf(i -> i.getId().equals(entity.getId()));
        items.add(entity);
    }

    @Override
    public MenuItem findById(String id) {
        return items.stream()
                .filter(i -> i.getId().equals("M" + id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<MenuItem> findAll() {
        return items;
    }
}
