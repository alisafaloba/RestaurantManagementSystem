package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Bill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BillRepository implements AbstractRepository <Bill> {
    private final List<Bill> bills = new ArrayList<>(Arrays.asList(
            new Bill("B1", "O1", 120.50),
            new Bill("B2", "O2", 89.99),
            new Bill("B3", "O3", 45.00)
    ));

    @Override
    public void save(Bill entity) {
        bills.add(entity);
    }

    @Override
    public void delete(Integer id) {
        bills.removeIf(b -> b.getId().equals("B" + id));
    }

    @Override
    public void update(Bill entity) {
        bills.removeIf(b -> b.getId().equals(entity.getId()));
        bills.add(entity);
    }

    @Override
    public Bill findById(Integer id) {
        return bills.stream()
                .filter(b -> b.getId().equals("B" + id))
                .findFirst()
                .orElse(null);
    }
    @Override
    public List<Bill> findAll() {
        return bills;
    }
}
