package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Table;
import com.example.restaurant_management_system.model.TableStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {

    /**
     * Filtert Tische basierend auf Occupied Status und Tischnummer (optional).
     */
    @Query("SELECT t FROM com.example.restaurant_management_system.model.Table t WHERE " +
            // Filter 1: Occupied Status (Enum/Dropdown filter)
            "(:statusFilter IS NULL OR t.occupiedStatus = :statusFilter) AND " +
            // Filter 2: Tischnummer (optional, kann als Minimum/Teilstring interpretiert werden,
            // hier verwenden wir Contains für Flexibilität oder einfaches number-matching)
            "(:numberFilter IS NULL OR CAST(t.number AS string) LIKE CONCAT('%', :numberFilter, '%'))")
    List<Table> findFilteredTables(@Param("statusFilter") TableStatus statusFilter,
                                   @Param("numberFilter") String numberFilter,
                                   Sort sort);
}