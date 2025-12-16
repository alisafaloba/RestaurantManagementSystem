package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.MenuItem;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    /**
     * Filtert Menüpunkte basierend auf Name (Teilstring) und Preis (Wertebereich).
     */
    @Query("SELECT m FROM MenuItem m WHERE " +
            // Filter 1: Name (Teilstring-Suche, case-insensitive)
            "(:nameFilter IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :nameFilter, '%'))) AND " +
            // Filter 2: Preis Minimum
            "(:minPrice IS NULL OR m.price >= :minPrice) AND " +
            // Filter 3: Preis Maximum
            "(:maxPrice IS NULL OR m.price <= :maxPrice)")
    List<MenuItem> findFilteredMenuItems(@Param("nameFilter") String nameFilter,
                                         @Param("minPrice") Double minPrice,
                                         @Param("maxPrice") Double maxPrice,
                                         Sort sort);
}