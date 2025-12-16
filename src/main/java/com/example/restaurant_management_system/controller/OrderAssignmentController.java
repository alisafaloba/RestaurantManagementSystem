package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.OrderAssignment;
import com.example.restaurant_management_system.service.OrderAssignmentService;
import com.example.restaurant_management_system.service.OrderService;
import com.example.restaurant_management_system.service.StaffService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/orderassignment")
public class OrderAssignmentController {

    private final OrderAssignmentService assignmentService;
    private final OrderService orderService;
    private final StaffService staffService;

    public OrderAssignmentController(
            OrderAssignmentService assignmentService,
            OrderService orderService,
            StaffService staffService
    ) {
        this.assignmentService = assignmentService;
        this.orderService = orderService;
        this.staffService = staffService;
    }

    // Hilfsmethode, um abhängige Daten zu laden, muss angepasste Service-Methoden aufrufen
    private void addFormAttributes(Model model) {
        // Annahme: orderService und staffService haben jetzt Sortier-/Filter-Signaturen,
        // daher rufen wir die parameterlosen Umleitungen auf, die alle Daten liefern.
        model.addAttribute("orders", orderService.getAllOrders());
        // WICHTIG: staffService.getAllStaff() muss ebenfalls die parameterlose Version
        // haben oder mit null-Filtern aufgerufen werden.
        model.addAttribute("staffList", staffService.getAllStaff());
    }

    // GET /orderassignment → list all assignments with filtering and sorting (ANGEPASST)
    @GetMapping
    public String listAssignments(Model model,
                                  // Sortier-Parameter
                                  @RequestParam(defaultValue = "id") String sortField,
                                  @RequestParam(defaultValue = "asc") String sortDir,
                                  // Filter-Parameter
                                  @RequestParam(required = false) Long orderIdFilter,
                                  @RequestParam(required = false) Long staffIdFilter) {

        // Daten aus dem Service abrufen (gefiltert und sortiert)
        model.addAttribute("assignments",
                assignmentService.getAllAssignments(sortField, sortDir, orderIdFilter, staffIdFilter));

        // Sortier-Infos für das UI
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equalsIgnoreCase("asc") ? "desc" : "asc");

        // Filter-Infos, um die Werte im Filterformular beizubehalten
        model.addAttribute("orderIdFilter", orderIdFilter);
        model.addAttribute("staffIdFilter", staffIdFilter);

        return "orderassignment/index";
    }

    // GET /orderassignment/new → show create form (ANGEPASST)
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("assignment", new OrderAssignment());
        addFormAttributes(model); // Nutzt die korrigierte Hilfsmethode
        return "orderassignment/form";
    }

    @PostMapping
    public String createAssignment(
            @Valid @ModelAttribute("assignment") OrderAssignment assignment,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            addFormAttributes(model);
            return "orderassignment/form";
        }

        assignmentService.addAssignment(assignment);
        return "redirect:/orderassignment";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {

        OrderAssignment assignment = assignmentService.getAssignmentById(id);
        if (assignment == null) {
            redirectAttributes.addFlashAttribute("error", "Order assignment not found");
            return "redirect:/orderassignment";
        }

        model.addAttribute("assignment", assignment);
        addFormAttributes(model); // Nutzt die korrigierte Hilfsmethode
        return "orderassignment/form";
    }

    @PostMapping("/{id}/update")
    public String updateAssignment(
            @PathVariable Long id,
            @Valid @ModelAttribute("assignment") OrderAssignment assignment,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            addFormAttributes(model);
            return "orderassignment/form";
        }

        assignment.setId(id);
        assignmentService.updateAssignment(assignment);

        return "redirect:/orderassignment";
    }

    @PostMapping("/{id}/delete")
    public String deleteAssignment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            assignmentService.deleteAssignment(id);
            redirectAttributes.addFlashAttribute("success", "Order assignment deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete order assignment");
        }
        return "redirect:/orderassignment";
    }
}