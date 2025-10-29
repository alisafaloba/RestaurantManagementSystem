package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Bill;
import org.springframework.stereotype.Repository;

/**
 * Repository pentru entitățile Bill.
 * Moștenește funcționalitatea CRUD din InMemoryRepository.
 * Nu conține date inițiale — lista este goală la pornire.
 */
@Repository
public class BillRepository extends InMemoryRepository<Bill> {

    public BillRepository() {
        super(Bill.class);
    }
}
