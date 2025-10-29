package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.OrderAssignment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class OrderAssignmentRepository implements AbstractRepository<OrderAssignment> {

    private final List<OrderAssignment> assignments = new ArrayList<>(Arrays.asList(
            new OrderAssignment("A1", "O1", "S1"),
            new OrderAssignment("A2", "O2", "S2"),
            new OrderAssignment("A3", "O3", "S3")
    ));

    @Override
    public void save(OrderAssignment entity) {
        assignments.add(entity);
    }

    @Override
    public void delete(String id) {
        assignments.removeIf(a -> a.getId().equals("A" + id));
    }

    @Override
    public void update(OrderAssignment entity) {
        assignments.removeIf(a -> a.getId().equals(entity.getId()));
        assignments.add(entity);
    }

    @Override
    public OrderAssignment findById(String id) {
        return assignments.stream()
                .filter(a -> a.getId().equals("A" + id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<OrderAssignment> findAll() {
        return assignments;
    }
}
