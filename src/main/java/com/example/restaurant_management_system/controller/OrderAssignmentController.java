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

    @GetMapping
    public String listAssignments(Model model) {
        model.addAttribute("assignments", assignmentService.getAllAssignments());
        return "orderassignment/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {

        model.addAttribute("assignment", new OrderAssignment());
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("staffList", staffService.getAllStaff());

        return "orderassignment/form";
    }

    @PostMapping
    public String createAssignment(
            @Valid @ModelAttribute("assignment") OrderAssignment assignment,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("staffList", staffService.getAllStaff());
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
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("staffList", staffService.getAllStaff());

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
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("staffList", staffService.getAllStaff());
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
