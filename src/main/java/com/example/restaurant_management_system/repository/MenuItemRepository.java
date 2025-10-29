package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.MenuItem;
import org.springframework.stereotype.Repository;

@Repository
public class MenuItemRepository extends InMemoryRepository<MenuItem> {
    public MenuItemRepository() {
        super(MenuItem.class);
    }
}
