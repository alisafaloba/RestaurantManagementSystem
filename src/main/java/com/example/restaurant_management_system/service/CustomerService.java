package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.Customer;
import com.example.restaurant_management_system.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Change ID type from String to Long, and use JpaRepository.findById
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void updateCustomer(Customer customer) {
        // JpaRepository.save() handles both insert and update
        customerRepository.save(customer);
    }

    // Change ID type from String to Long, and use JpaRepository.deleteById
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}