package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Bill;
import com.example.restaurant_management_system.model.Chef;
import com.example.restaurant_management_system.service.ChefService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/chef")
public class ChefController {

    private final ChefService chefService;

    public ChefController(ChefService chefService) {
        this.chefService = chefService;
    }

    // GET /chef → list all chefs
    @GetMapping
    public String listChefs(Model model) {
        model.addAttribute("chefs", chefService.getAllChefs());
        return "chef/index";
    }

    // GET /chef/new → show create form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("chef", new Chef());
        return "chef/form";
    }

    // POST /chef → create new chef
    @PostMapping
    public String createChef(@ModelAttribute Chef chef) {
        if (chef.getId() == null || chef.getId().isEmpty()) {
            chef.setId(UUID.randomUUID().toString());
        }
        chefService.addChef(chef);
        return "redirect:/chef";
    }

    // POST /chef/{id}/delete → delete chef
    @PostMapping("/{id}/delete")
    public String deleteChef(@PathVariable String id) {
        chefService.deleteChef(id);
        return "redirect:/chef";
    }

    // GET /chef/{id}/edit → show edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Chef chef = chefService.getChefById(id);
        model.addAttribute("chef", chef);
        return "chef/form";
    }


    @PostMapping("/{id}/update")
    public String updateChef(@PathVariable String id, @ModelAttribute Chef chef) {
        chef.setId(id);
        chefService.updateChef(chef);
        return "redirect:/chef";
    }
}
