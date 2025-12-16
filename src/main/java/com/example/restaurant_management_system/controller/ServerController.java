package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Server;
import com.example.restaurant_management_system.service.ServerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/server")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    // GET /server -> list all servers with filtering and sorting
    @GetMapping
    public String listServers(Model model,
                              // Sortier-Parameter
                              @RequestParam(defaultValue = "id") String sortField,
                              @RequestParam(defaultValue = "asc") String sortDir,
                              // Filter-Parameter
                              @RequestParam(required = false) String nameFilter,
                              @RequestParam(required = false) String designationFilter) {

        // Daten aus dem Service abrufen (gefiltert und sortiert)
        model.addAttribute("servers",
                serverService.getAllServers(sortField, sortDir, nameFilter, designationFilter));

        // Sortier-Infos für das UI
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equalsIgnoreCase("asc") ? "desc" : "asc");

        // Filter-Infos, um die Werte im Filterformular beizubehalten
        model.addAttribute("nameFilter", nameFilter);
        model.addAttribute("designationFilter", designationFilter);

        return "server/index";
    }

    // --- CRUD-Methoden bleiben unverändert, aber @Valid wird genutzt ---

    // GET /server/new -> show create form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("server", new Server());
        return "server/form";
    }

    // POST /server → create new server
    @PostMapping
    public String createServer(@Valid @ModelAttribute Server server,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "server/form";
        }

        serverService.addServer(server);
        return "redirect:/server";
    }

    // POST /server/{id}/delete → delete server
    @PostMapping("/{id}/delete")
    public String deleteServer(@PathVariable Long id) {
        serverService.deleteServer(id);
        return "redirect:/server";
    }

    // GET /server/{id}/edit → show edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Server server = serverService.getServerById(id);
        model.addAttribute("server", server);
        return "server/form";
    }

    @PostMapping("/{id}/update")
    public String updateServer(@PathVariable Long id,
                               @Valid @ModelAttribute Server server,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "server/form";
        }

        server.setId(id);
        serverService.updateServer(server);
        return "redirect:/server";
    }

}