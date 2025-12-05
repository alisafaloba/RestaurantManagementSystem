package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Extend JpaRepository: Use 'Customer' as the entity and 'Long' as the ID type
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // JpaRepository provides all necessary CRUD operations
}
// Delete the old CustomerRepository class that extended InFileRepository