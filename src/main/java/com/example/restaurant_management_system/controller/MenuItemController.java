package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.MenuItem;
import com.example.restaurant_management_system.service.MenuItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/menuitem")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    // GET /menuitem → list all menu items
    @GetMapping
    public String listMenuItems(Model model) {
        model.addAttribute("menuItems", menuItemService.getAllMenuItems());
        return "menuitem/index"; // templates/menuitem/index.html
    }

    // GET /menuitem/new → show form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("menuItem", new MenuItem());
        return "menuitem/form"; // templates/menuitem/form.html
    }

    // POST /menuitem → handle form submission
    @PostMapping
    public String createMenuItem(@ModelAttribute MenuItem menuItem) {
        if (menuItem.getId() == null || menuItem.getId().isEmpty()) {
            menuItem.setId(java.util.UUID.randomUUID().toString());
        }
        menuItemService.addMenuItem(menuItem);
        return "redirect:/menuitem";
    }

    // POST /menuitem/{id}/delete → delete a menu item
    @PostMapping("/{id}/delete")
    public String deleteMenuItem(@PathVariable String id) {
        menuItemService.deleteMenuItem(id);
        return "redirect:/menuitem";
    }
}
