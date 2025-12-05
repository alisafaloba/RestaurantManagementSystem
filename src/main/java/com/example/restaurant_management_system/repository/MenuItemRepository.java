package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository; // <-- IMPORTANT IMPORT
import org.springframework.stereotype.Repository;

@Repository
// The JpaRepository interface provides deleteById(Long id) automatically
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

}