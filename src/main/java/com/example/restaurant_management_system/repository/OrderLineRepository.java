package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.OrderLine;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class OrderLineRepository implements AbstractRepository<OrderLine> {

    private final List<OrderLine> lines = new ArrayList<>(Arrays.asList(
            new OrderLine("L1", "M1", 2),
            new OrderLine("L2", "M2", 1),
            new OrderLine("L3", "M3", 3)
    ));

    @Override
    public void save(OrderLine entity) {
        lines.add(entity);
    }

    @Override
    public void delete(String id) {
        lines.removeIf(l -> l.getId().equals("L" + id));
    }


    @Override
    public void update(OrderLine entity) {
        lines.removeIf(l -> l.getId().equals(entity.getId()));
        lines.add(entity);
    }

    @Override
    public OrderLine findById(String id) {
        return lines.stream()
                .filter(l -> l.getId().equals("L" + id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<OrderLine> findAll() {
        return lines;
    }
}
