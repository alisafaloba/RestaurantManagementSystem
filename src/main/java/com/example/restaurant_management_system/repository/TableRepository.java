package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Extend JpaRepository: Use 'Table' as the entity and 'Long' as the ID type
public interface TableRepository extends JpaRepository<Table, Long> {
    // JpaRepository provides all necessary CRUD operations
}
// Delete the old TableRepository class that extended InFileRepository