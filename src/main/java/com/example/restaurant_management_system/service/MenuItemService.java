package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.MenuItem;
import com.example.restaurant_management_system.repository.MenuItemRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    /**
     * Ruft alle Menüpunkte ab, wendet Sortierung und Filterung an.
     */
    public List<MenuItem> getAllMenuItems(String sortField, String sortDir,
                                          String nameFilter, Double minPrice, Double maxPrice) {

        Sort sort = Sort.by(sortField);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();

        return menuItemRepository.findFilteredMenuItems(nameFilter, minPrice, maxPrice, sort);
    }

    // Die parameterlose Methode leitet auf die neue Methode um
    public List<MenuItem> getAllMenuItems() {
        return getAllMenuItems("id", "asc", null, null, null);
    }

    // --- CRUD-Methoden ---

    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id).orElse(null);
    }

    public void addMenuItem(MenuItem item) {
        menuItemRepository.save(item);
    }

    public void updateMenuItem(MenuItem item) {
        menuItemRepository.save(item);
    }

    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }
}