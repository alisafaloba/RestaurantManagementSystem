package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Chef;
import com.example.restaurant_management_system.service.ChefService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chef")
public class ChefController {

    private final ChefService chefService;

    public ChefController(ChefService chefService) {
        this.chefService = chefService;
    }

    // GET /chef -> list all chefs, now with filtering and sorting
    @GetMapping
    public String listChefs(Model model,
                            // Sortier-Parameter
                            @RequestParam(defaultValue = "id") String sortField,
                            @RequestParam(defaultValue = "asc") String sortDir,
                            // Filter-Parameter
                            @RequestParam(required = false) String nameFilter,
                            @RequestParam(required = false) String specializationFilter) {

        // Daten aus dem Service abrufen (gefiltert und sortiert)
        model.addAttribute("chefs",
                chefService.getAllChefs(sortField, sortDir, nameFilter, specializationFilter));

        // Sortier-Infos für das UI
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equalsIgnoreCase("asc") ? "desc" : "asc");

        // Filter-Infos, um die Werte im Filterformular beizubehalten
        model.addAttribute("nameFilter", nameFilter);
        model.addAttribute("specializationFilter", specializationFilter);

        return "chef/index";
    }

    // --- CRUD-Methoden ---

    // GET /chef/new -> show create form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("chef", new Chef());
        return "chef/form";
    }

    // POST /chef → create new chef
    @PostMapping
    public String createChef(@Valid @ModelAttribute Chef chef,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "chef/form";
        }

        chefService.addChef(chef);
        return "redirect:/chef";
    }

    // POST /chef/{id}/delete → delete chef
    @PostMapping("/{id}/delete")
    public String deleteChef(@PathVariable Long id) {
        chefService.deleteChef(id);
        return "redirect:/chef";
    }

    // GET /chef/{id}/edit → show edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Chef chef = chefService.getChefById(id);
        model.addAttribute("chef", chef);
        return "chef/form";
    }

    // POST /chef/{id}/update
    @PostMapping("/{id}/update")
    public String updateChef(@PathVariable Long id,
                             @Valid @ModelAttribute Chef chef,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "chef/form";
        }

        chef.setId(id);
        chefService.updateChef(chef);
        return "redirect:/chef";
    }
}