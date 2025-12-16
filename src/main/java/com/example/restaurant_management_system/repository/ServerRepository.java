package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Server;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {

    /**
     * Filtert Server basierend auf Name und Designation (Teilstring-Suche).
     */
    @Query("SELECT s FROM Server s WHERE " +
            // Filter 1: Name (Teilstring-Suche, case-insensitive, geerbt von Staff)
            "(:nameFilter IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :nameFilter, '%'))) AND " +
            // Filter 2: Designation (Teilstring-Suche, case-insensitive)
            "(:designationFilter IS NULL OR LOWER(s.designation) LIKE LOWER(CONCAT('%', :designationFilter, '%')))")
    List<Server> findFilteredServers(@Param("nameFilter") String nameFilter,
                                     @Param("designationFilter") String designationFilter,
                                     Sort sort);
}