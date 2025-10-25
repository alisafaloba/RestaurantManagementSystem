package com.example.restaurant_management_system.repository;


import com.example.restaurant_management_system.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements Repository<Customer> {

    private List<Customer> customers = new ArrayList<>();

    @Override
    public void add(Customer customer) {
        customers.add(customer);
    }

    @Override
    public void update(Customer customer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId() == customer.getId()) {
                customers.set(i, customer);
                return;
            }
        }
    }

    @Override
    public void delete(Customer customer) {
        customers.removeIf(c -> c.getId() == customer.getId());
    }

    @Override
    public Customer findById(int id) {
        return customers.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers);
    }
}