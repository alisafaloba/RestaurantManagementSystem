package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Chef;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ChefRepository implements AbstractRepository<Chef> {

    private final List<Chef> chefs = new ArrayList<>(Arrays.asList(
            new Chef("C1", "John Doe", "Italian"),
            new Chef("C2", "Maria Popescu", "Desserts"),
            new Chef("C3", "Luca Ionescu", "Steak")
    ));

    @Override
    public void save(Chef entity) {
        chefs.add(entity);
    }

    @Override
    public void delete(Integer id) {
        chefs.removeIf(c -> c.getId().equals("C" + id));
    }

    @Override
    public void update(Chef entity) {
        chefs.removeIf(c -> c.getId().equals(entity.getId()));
        chefs.add(entity);
    }

    @Override
    public Chef findById(Integer id) {
        return chefs.stream()
                .filter(c -> c.getId().equals("C" + id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Chef> findAll() {
        return chefs;
    }
}
