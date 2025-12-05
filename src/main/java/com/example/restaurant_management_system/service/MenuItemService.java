package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.MenuItem;
import com.example.restaurant_management_system.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    // Change ID type from String to Long, and use JpaRepository.findById
    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id).orElse(null);
    }

    public void addMenuItem(MenuItem item) {
        menuItemRepository.save(item);
    }

    public void updateMenuItem(MenuItem item) {
        // JpaRepository.save() handles both insert and update
        menuItemRepository.save(item);
    }

    // Change ID type from String to Long, and use JpaRepository.deleteById
    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }
}