package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Server;
import com.example.restaurant_management_system.service.ServerService;
import jakarta.validation.Valid; // Required for validation
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // Required for validation errors
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/server")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    // GET /server -> list all servers
    @GetMapping
    public String listServers(Model model) {
        model.addAttribute("servers", serverService.getAllServers());
        return "server/index";
    }

    // GET /server/new -> show create form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("server", new Server());
        return "server/form";
    }

    // POST /server → create new server (requires @Valid and error handling)
    @PostMapping
    public String createServer(@Valid @ModelAttribute Server server,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "server/form"; // Return to the form to display errors
        }

        // Manual ID generation is removed (handled by JPA)
        serverService.addServer(server);
        return "redirect:/server";
    }

    // POST /server/{id}/delete → delete server (ID is now Long, passed as PathVariable)
    @PostMapping("/{id}/delete")
    public String deleteServer(@PathVariable Long id) {
        serverService.deleteServer(id);
        return "redirect:/server";
    }

    // GET /server/{id}/edit → show edit form (ID is now Long)
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Server server = serverService.getServerById(id);
        model.addAttribute("server", server);
        return "server/form";
    }


    @PostMapping("/{id}/update")
    public String updateServer(@PathVariable Long id, // ID is now Long
                               @Valid @ModelAttribute Server server, // Requires @Valid
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "server/form"; // Return to the form if errors exist
        }

        // Set the path variable ID back onto the Server object
        server.setId(id);
        serverService.updateServer(server);
        return "redirect:/server";
    }
}