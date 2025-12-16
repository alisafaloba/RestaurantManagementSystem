package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.MenuItem;
import com.example.restaurant_management_system.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors; // Neu importiert

@Controller
@RequestMapping("/menuitem")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    // Hilfsmethode, um den kommagetrennten String in eine Liste umzuwandeln
    private List<String> parseAllergens(String allergenString) {
        if (allergenString == null || allergenString.trim().isEmpty()) {
            return List.of(); // Gibt leere, nicht null-Liste zurück
        }
        return Arrays.stream(allergenString.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    // GET /menuitem → list all menu items with filtering and sorting
    @GetMapping
    public String listMenuItems(Model model,
                                // Sortier-Parameter
                                @RequestParam(defaultValue = "id") String sortField,
                                @RequestParam(defaultValue = "asc") String sortDir,
                                // Filter-Parameter
                                @RequestParam(required = false) String nameFilter,
                                @RequestParam(required = false) Double minPrice,
                                @RequestParam(required = false) Double maxPrice) {

        // Daten aus dem Service abrufen (gefiltert und sortiert)
        model.addAttribute("menuItems",
                menuItemService.getAllMenuItems(sortField, sortDir, nameFilter, minPrice, maxPrice));

        // Sortier-Infos für das UI
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equalsIgnoreCase("asc") ? "desc" : "asc");

        // Filter-Infos, um die Werte im Filterformular beizubehalten
        model.addAttribute("nameFilter", nameFilter);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "menuitem/index";
    }

    // GET /menuitem/new → show form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("menuItem", new MenuItem());
        return "menuitem/form";
    }

    // POST /menuitem → handle form submission (ANGEPASST FÜR PARSING)
    @PostMapping
    public String createMenuItem(@Valid @ModelAttribute MenuItem menuItem,
                                 BindingResult bindingResult,
                                 // Fängt den Wert aus dem Input-Feld ab
                                 @RequestParam("allergens") String allergenString) {

        // 1. Allergene parsen und setzen, bevor Validierung oder Speicherung erfolgt
        menuItem.setAllergens(parseAllergens(allergenString));

        if (bindingResult.hasErrors()) {
            return "menuitem/form";
        }

        menuItemService.addMenuItem(menuItem);
        return "redirect:/menuitem";
    }

    // GET /menuitem/{id}/edit → show edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        MenuItem menuItem = menuItemService.getMenuItemById(id);
        model.addAttribute("menuItem", menuItem);
        return "menuitem/form";
    }

    // POST /menuitem/{id}/update → handle edit submission (ANGEPASST FÜR PARSING)
    @PostMapping("/{id}/update")
    public String updateMenuItem(@PathVariable Long id,
                                 @Valid @ModelAttribute MenuItem menuItem,
                                 BindingResult bindingResult,
                                 // Fängt den Wert aus dem Input-Feld ab
                                 @RequestParam("allergens") String allergenString) {

        // 1. Allergene parsen und setzen
        menuItem.setAllergens(parseAllergens(allergenString));

        if (bindingResult.hasErrors()) {
            return "menuitem/form";
        }

        menuItem.setId(id);
        menuItemService.updateMenuItem(menuItem);
        return "redirect:/menuitem";
    }

    // POST /menuitem/{id}/delete → delete a menu item
    @PostMapping("/{id}/delete")
    public String deleteMenuItem(@PathVariable Long id) {
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