package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Chef;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Long> {

    /**
     * Filtert Chefs basierend auf Name und Specialization (Teilstring-Suche).
     * Filterung und Sortierung müssen zusammen funktionieren.
     */
    @Query("SELECT c FROM Chef c WHERE " +
            // Filter 1: Name (Teilstring-Suche, case-insensitive, erbt von Staff)
            "(:nameFilter IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :nameFilter, '%'))) AND " +
            // Filter 2: Specialization (Teilstring-Suche, case-insensitive)
            "(:specializationFilter IS NULL OR LOWER(c.specialization) LIKE LOWER(CONCAT('%', :specializationFilter, '%')))")
    List<Chef> findFilteredChefs(@Param("nameFilter") String nameFilter,
                                 @Param("specializationFilter") String specializationFilter,
                                 Sort sort);
}