package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Extend JpaRepository: Use 'OrderLine' as the entity and 'Long' as the ID type
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    // JpaRepository provides findAll(), findById(Long), save(OrderLine), deleteById(Long)
}
// Delete the old OrderLineRepository class that extended InFileRepository
