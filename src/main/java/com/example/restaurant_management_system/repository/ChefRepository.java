package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Extend JpaRepository: Use 'Chef' as the entity and 'Long' as the ID type
// (since Staff.id is now a Long). This provides all CRUD operations automatically.
public interface ChefRepository extends JpaRepository<Chef, Long> {
}