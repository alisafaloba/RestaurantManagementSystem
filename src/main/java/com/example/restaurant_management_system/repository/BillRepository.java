package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Bill;
import org.springframework.stereotype.Repository;

@Repository
public class BillRepository extends InFileRepository<Bill> {
    public BillRepository() {
        super(Bill.class, "bills.json");
    }
}
