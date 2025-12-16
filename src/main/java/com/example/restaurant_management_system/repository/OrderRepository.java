package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Order;
import com.example.restaurant_management_system.model.OrderStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE " +
            // Filter 1: Status (optional)
            "(:statusFilter IS NULL OR o.status = :statusFilter) AND " +
            // Filter 2: Datum von (optional)
            "(:startDate IS NULL OR o.date >= :startDate) AND " +
            // Filter 3: Datum bis (optional)
            "(:endDate IS NULL OR o.date <= :endDate)")
    List<Order> findFilteredOrders(@Param("statusFilter") OrderStatus statusFilter,
                                   @Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate,
                                   Sort sort);

    List<Order> findByTable_Id(Long id);
}