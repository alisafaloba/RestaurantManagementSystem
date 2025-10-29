package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.OrderAssignment;
import org.springframework.stereotype.Repository;

@Repository
public class OrderAssignmentRepository extends InMemoryRepository<OrderAssignment> {
    public OrderAssignmentRepository() {
        super(OrderAssignment.class);
    }
}
