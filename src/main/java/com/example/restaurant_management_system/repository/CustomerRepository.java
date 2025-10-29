package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository extends InMemoryRepository<Customer> {
    public CustomerRepository() {
        super(Customer.class);
    }
}
