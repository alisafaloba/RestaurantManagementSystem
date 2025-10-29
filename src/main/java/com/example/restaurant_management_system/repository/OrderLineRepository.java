package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.OrderLine;
import org.springframework.stereotype.Repository;

@Repository
public class OrderLineRepository extends InMemoryRepository<OrderLine> {
    public OrderLineRepository() {
        super(OrderLine.class);
    }
}
