package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Extend JpaRepository: Use 'Server' as the entity and 'Long' as the ID type.
public interface ServerRepository extends JpaRepository<Server, Long> {
}
