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

    // GET /chef -> list all chefs
    @GetMapping
    public String listChefs(Model model) {
        model.addAttribute("chefs", chefService.getAllChefs());
        return "chef/index";
    }

    // GET /chef/new -> show create form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("chef", new Chef());
        return "chef/form";
    }

    // POST /chef → create new chef (requires @Valid and error handling)
    @PostMapping
    public String createChef(@Valid @ModelAttribute Chef chef,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "chef/form"; // Return to the form to display errors
        }

        // Manual ID generation is removed (handled by JPA)
        chefService.addChef(chef);
        return "redirect:/chef";
    }

    // POST /chef/{id}/delete → delete chef (ID is now Long, passed as PathVariable)
    @PostMapping("/{id}/delete")
    public String deleteChef(@PathVariable Long id) {
        chefService.deleteChef(id);
        return "redirect:/chef";
    }

    // GET /chef/{id}/edit → show edit form (ID is now Long)
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Chef chef = chefService.getChefById(id);
        model.addAttribute("chef", chef);
        return "chef/form";
    }


    @PostMapping("/{id}/update")
    public String updateChef(@PathVariable Long id, // ID is now Long
                             @Valid @ModelAttribute Chef chef, // Requires @Valid
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "chef/form"; // Return to the form if errors exist
        }

        // Set the path variable ID back onto the Chef object
        chef.setId(id);
        chefService.updateChef(chef);
        return "redirect:/chef";
    }
}