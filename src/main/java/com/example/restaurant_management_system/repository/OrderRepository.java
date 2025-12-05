package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Extend JpaRepository with Long ID
public interface OrderRepository extends JpaRepository<Order, Long> {
}
