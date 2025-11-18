package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository extends InFileRepository<Order> {
    public OrderRepository() {
        super(Order.class, "order.json");
    }
}
