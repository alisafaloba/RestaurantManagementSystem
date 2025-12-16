package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.OrderAssignment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderAssignmentRepository extends JpaRepository<OrderAssignment, Long> {

    /**
     * Filtert Zuordnungen basierend auf der Order ID und der Staff ID.
     */
    @Query("SELECT oa FROM OrderAssignment oa WHERE " +
            // Filter 1: Order ID (optional)
            "(:orderIdFilter IS NULL OR oa.order.id = :orderIdFilter) AND " +
            // Filter 2: Staff ID (optional)
            "(:staffIdFilter IS NULL OR oa.staff.id = :staffIdFilter)")
    List<OrderAssignment> findFilteredAssignments(@Param("orderIdFilter") Long orderIdFilter,
                                                  @Param("staffIdFilter") Long staffIdFilter,
                                                  Sort sort);
}