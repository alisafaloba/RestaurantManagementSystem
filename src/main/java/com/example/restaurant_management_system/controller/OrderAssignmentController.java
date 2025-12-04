package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.OrderAssignment;
import com.example.restaurant_management_system.service.OrderAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/orderassignment")
public class OrderAssignmentController {
    private final OrderAssignmentService orderAssignmentService;

    public OrderAssignmentController(OrderAssignmentService orderAssignmentService) {
        this.orderAssignmentService = orderAssignmentService;
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("assignments", orderAssignmentService.getAllAssignments());
        return "orderassignment/index";
    }

    //GetMapping("/new")
   // public String showCreateForm(Model model) {
        //model.addAttribute("assignment", new OrderAssignment());
       // return "orderassignment/form";
    //}
    @GetMapping("/new")
    public String showCreateForm(@RequestParam(value = "orderId", required = false) String orderId,
                                 Model model) {

        OrderAssignment assignment = new OrderAssignment();
        assignment.setOrderId(orderId); // pre-fill if coming from Order Details

        model.addAttribute("assignment", assignment);
        return "orderassignment/form";
    }


    @PostMapping
    public String createAssignment(@ModelAttribute("assignment") OrderAssignment assignment) {

        if (assignment.getId() == null || assignment.getId().isEmpty()) {
            assignment.setId(UUID.randomUUID().toString());
        }

        orderAssignmentService.addAssignment(assignment);

        // FIX: return to the order details page
        if (assignment.getOrderId() != null) {
            return "redirect:/order/" + assignment.getOrderId() + "/details";
        }

        return "redirect:/orderassignment";
    }


    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        OrderAssignment orderAssignment = orderAssignmentService.getAssignmentById(id);
        if (orderAssignment == null) {
            redirectAttributes.addFlashAttribute("error", "Order assignment not found");
            return "redirect:/orderassignment";
        }
        model.addAttribute("assignment", orderAssignment); // Consistent attribute name
        return "orderassignment/form";
    }

    @PostMapping("/{id}/update")
    public String updateOrderAssignment(@PathVariable String id, @ModelAttribute("assignment") OrderAssignment orderAssignment) {
        orderAssignment.setId(id);
        orderAssignmentService.updateAssignment(orderAssignment);
        return "redirect:/orderassignment";
    }

    @PostMapping("/{id}/delete")
    public String deleteOrderAssignment(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            orderAssignmentService.deleteAssignment(id);
            redirectAttributes.addFlashAttribute("success", "Order assignment deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete order assignment");
        }
        return "redirect:/orderassignment";
    }
}