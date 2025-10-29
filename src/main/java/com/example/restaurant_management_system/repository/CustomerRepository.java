package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class CustomerRepository implements AbstractRepository<Customer> {

    private final List<Customer> customers = new ArrayList<>(Arrays.asList(
            new Customer("1", "Alice", new ArrayList<>()),
            new Customer("2", "Bob", new ArrayList<>()),
            new Customer("3", "Charlie", new ArrayList<>())
    ));

    @Override
    public void save(Customer entity) {
        customers.add(entity);
    }



    @Override
    public void delete(String id) {
        customers.removeIf(c -> c.getId().equals(id));
    }


    @Override
    public void update(Customer entity) {
        delete(entity.getId());
        save(entity);
    }

    @Override
    public Customer findById(String id) {
        customers.stream()
                .filter(c -> {
                    c.getId();
                    return false;
                })
                .findFirst();
        return null;
    }


    @Override
    public List<Customer> findAll() {
        return customers;
    }
}
