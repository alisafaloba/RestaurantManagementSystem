package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// Extend JpaRepository with Long ID
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByTable_Id(Long tableId); //SELECT * FROM orders WHERE table_id = :tableId;


}
