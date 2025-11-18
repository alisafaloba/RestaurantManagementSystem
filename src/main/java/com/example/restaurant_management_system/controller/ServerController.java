package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Server;
import com.example.restaurant_management_system.service.ServerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/server")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping
    public String listServers(Model model) {
        model.addAttribute("servers", serverService.getAllServers());
        return "server/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("server", new Server());
        return "server/form";
    }

    @PostMapping
    public String createServer(@ModelAttribute("server") Server server) {
        if (server.getId() == null || server.getId().isEmpty()) {
            server.setId(java.util.UUID.randomUUID().toString());
        }
        serverService.addServer(server);
        return "redirect:/server";
    }

    @PostMapping("/{id}/delete")
    public String deleteServer(@PathVariable String id) {
        serverService.deleteServer(id);
        return "redirect:/server";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Server server = serverService.getServerById(id);
        model.addAttribute("server", server);
        return "server/form";
    }

    @PostMapping("/{id}/update")
    public String updateServer(@PathVariable String id, @ModelAttribute("server") Server server) {
        server.setId(id);
        serverService.updateServer(server);
        return "redirect:/server";
    }
}
