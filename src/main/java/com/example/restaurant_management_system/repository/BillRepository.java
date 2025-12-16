package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Bill;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    /**
     * Filtert Rechnungen basierend auf Order ID (enthält) und Total Amount (Wertebereich).
     * Filterung und Sortierung müssen zusammen funktionieren[cite: 71].
     */
    @Query("SELECT b FROM Bill b WHERE " +
            // Filter 1: Order ID (Teilstring-Suche, case-insensitive)
            "(:orderIdFilter IS NULL OR LOWER(b.orderId) LIKE LOWER(CONCAT('%', :orderIdFilter, '%'))) AND " +
            // Filter 2: Total Amount Minimum
            "(:minAmount IS NULL OR b.totalAmount >= :minAmount) AND " +
            // Filter 3: Total Amount Maximum
            "(:maxAmount IS NULL OR b.totalAmount <= :maxAmount)")
    List<Bill> findFilteredBills(@Param("orderIdFilter") String orderIdFilter,
                                 @Param("minAmount") Double minAmount,
                                 @Param("maxAmount") Double maxAmount,
                                 Sort sort);
}