package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.OrderLine;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

    /**
     * Filtert OrderLines basierend auf Order ID, Menu Item ID und Minimum Quantity.
     */
    @Query("SELECT ol FROM OrderLine ol WHERE " +
            // Filter 1: Order ID (optional)
            "(:orderIdFilter IS NULL OR ol.order.id = :orderIdFilter) AND " +
            // Filter 2: Menu Item ID (optional)
            "(:menuItemIdFilter IS NULL OR ol.menuItem.id = :menuItemIdFilter) AND " +
            // Filter 3: Minimum Quantity (optional)
            "(:minQuantity IS NULL OR ol.quantity >= :minQuantity)")
    List<OrderLine> findFilteredOrderLines(@Param("orderIdFilter") Long orderIdFilter,
                                           @Param("menuItemIdFilter") Long menuItemIdFilter,
                                           @Param("minQuantity") Double minQuantity,
                                           Sort sort);
}