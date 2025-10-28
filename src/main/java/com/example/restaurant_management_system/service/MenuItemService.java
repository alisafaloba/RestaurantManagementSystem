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

    public MenuItem getMenuItemById(String id) {
        return menuItemRepository.findById(id);
    }

    public void addMenuItem(MenuItem item) {
        menuItemRepository.save(item);
    }

    public void updateMenuItem(MenuItem item) {
        menuItemRepository.update(item);
    }

    public void deleteMenuItem(String id) {
        menuItemRepository.delete(id);
    }
}
