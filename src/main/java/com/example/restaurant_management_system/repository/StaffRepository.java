package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Staff;
import org.springframework.stereotype.Repository;

@Repository
public class StaffRepository extends InMemoryRepository<Staff> {
    public StaffRepository() {
        super(Staff.class);
    }
}
