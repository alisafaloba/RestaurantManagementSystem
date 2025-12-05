package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// NOTE: While Staff is @MappedSuperclass, we use the JpaRepository
// pattern for continuity with the existing repository structure.
// We use 'Staff' as the type and 'Long' as the ID type.
public interface StaffRepository extends JpaRepository<Staff, Long> {
    // JpaRepository provides all necessary CRUD operations based on the Long ID
}
