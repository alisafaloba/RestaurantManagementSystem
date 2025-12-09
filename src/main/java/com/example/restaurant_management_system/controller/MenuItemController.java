package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.MenuItem;
import com.example.restaurant_management_system.model.Table;
import com.example.restaurant_management_system.service.MenuItemService;
import jakarta.validation.Valid; // <-- ADD VALIDATION IMPORT
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // <-- ADD VALIDATION IMPORT
import org.springframework.web.bind.annotation.*;
// Removed java.util.UUID import

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
        return "menuitem/index";
    }

    // GET /menuitem/new → show form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("menuItem", new MenuItem());
        return "menuitem/form";
    }

    // POST /menuitem → handle form submission (Requires @Valid and error handling)
    @PostMapping
    public String createMenuItem(@Valid @ModelAttribute MenuItem menuItem,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "menuitem/form"; // Return to form to show validation errors
        }

        // Manual ID generation is removed (handled by JPA)
        menuItemService.addMenuItem(menuItem);
        return "redirect:/menuitem";
    }

    // GET /menuitem/{id}/edit → show edit form (ID is now Long)
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) { // Changed type to Long
        MenuItem menuItem = menuItemService.getMenuItemById(id);
        model.addAttribute("menuItem", menuItem);
        return "menuitem/form";
    }

    // POST /menuitem/{id}/update → handle edit submission (ID is now Long, requires @Valid)
    @PostMapping("/{id}/update")
    public String updateMenuItem(@PathVariable Long id, // Changed type to Long
                                 @Valid @ModelAttribute MenuItem menuItem,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "menuitem/form"; // Return to form if validation fails
        }

        menuItem.setId(id);
        menuItemService.updateMenuItem(menuItem);
        return "redirect:/menuitem";
    }

    // POST /menuitem/{id}/delete → delete a menu item (ID is now Long)
    @PostMapping("/{id}/delete")
    public String deleteMenuItem(@PathVariable Long id) { // Changed type to Long
        menuItemService.deleteMenuItem(id);
        return "redirect:/menuitem";
    }

    @GetMapping("/{id}/details")
    public String showEntityDetails(@PathVariable Long id, Model model) {
        MenuItem menuItem = menuItemService.getMenuItemById(id);

        if (menuItem == null) {
            return "redirect:/menuitem";
        }

        model.addAttribute("menuItem", menuItem);
        model.addAttribute("allergens", menuItem.getAllergens());
        return "menuitem/details";
    }

}