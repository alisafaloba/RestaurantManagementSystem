package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Customer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Filtert Kunden basierend auf Name und E-Mail-Adresse (Teilstring-Suche).
     * Name und E-Mail-Adresse sind die zwei kombinierten Filterkriterien.
     */
    @Query("SELECT c FROM Customer c WHERE " +
            // Filter 1: Name (Teilstring-Suche, case-insensitive)
            "(:nameFilter IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :nameFilter, '%'))) AND " +
            // Filter 2: Email Address (Teilstring-Suche, case-insensitive)
            "(:emailFilter IS NULL OR LOWER(c.emailaddress) LIKE LOWER(CONCAT('%', :emailFilter, '%')))")
    List<Customer> findFilteredCustomers(@Param("nameFilter") String nameFilter,
                                         @Param("emailFilter") String emailFilter,
                                         Sort sort);
}