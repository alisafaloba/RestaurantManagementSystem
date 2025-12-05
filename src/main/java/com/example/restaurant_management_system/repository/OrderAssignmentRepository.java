package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.OrderAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Extend JpaRepository: Use 'OrderAssignment' as the entity and 'Long' as the ID type
public interface OrderAssignmentRepository extends JpaRepository<OrderAssignment, Long> {
    // JpaRepository provides all necessary CRUD operations
}
// Delete the old OrderAssignmentRepository class that extended InFileRepository