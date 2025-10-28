package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class OrderRepository implements AbstractRepository<Order> {

    private final List<Order> orders = new ArrayList<>(Arrays.asList(
            new Order("O1", "1", "T1", "Open", new ArrayList<>(), new ArrayList<>()),
            new Order("O2", "2", "T2", "Closed", new ArrayList<>(), new ArrayList<>()),
            new Order("O3", "3", "T3", "Open", new ArrayList<>(), new ArrayList<>())
    ));

    @Override
    public void save(Order entity) {
        orders.add(entity);
    }

    @Override
    public void delete(String id) {
        orders.removeIf(o -> o.getId().equals("O" + id));
    }

    @Override
    public void update(Order entity) {
        orders.removeIf(o -> o.getId().equals(entity.getId()));
        orders.add(entity);
    }

    @Override
    public Order findById(String id) {
        return orders.stream()
                .filter(o -> o.getId().equals("O" + id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Order> findAll() {
        return orders;
    }
}
