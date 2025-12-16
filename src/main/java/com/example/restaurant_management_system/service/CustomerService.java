package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.Customer;
import com.example.restaurant_management_system.repository.CustomerRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Ruft alle Kunden ab, wendet Sortierung und Filterung an.
     */
    public List<Customer> getAllCustomers(String sortField, String sortDir,
                                          String nameFilter, String emailFilter) {

        // Erstellung des Sortierobjekts
        Sort sort = Sort.by(sortField);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();

        // Aufruf der Repository-Methode für kombiniertes Filtern und Sortieren
        return customerRepository.findFilteredCustomers(nameFilter, emailFilter, sort);
    }

    // --- CRUD-Methoden ---

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}