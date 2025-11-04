package com.example.restaurant_management_system.controller;



import com.example.restaurant_management_system.model.OrderAssignment;
import com.example.restaurant_management_system.service.OrderAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping ("/orderassignment")
public class OrderAssignmentController {
    private OrderAssignmentService orderAssignmentService;

    public OrderAssignmentController(OrderAssignmentService orderService) {
        this.orderAssignmentService = orderService;
    }
    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("assignments", orderAssignmentService.getAllAssignments());
        return "orderassignment/index";
    }
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("assignment", new OrderAssignment());
        return "orderassignment/form";
    }
    @PostMapping
    public String createOrderAssignment(@ModelAttribute("assignment") OrderAssignment orderAssignment) {
        if (orderAssignment.getId() == null || orderAssignment.getId().isEmpty()) {
            orderAssignment.setId(java.util.UUID.randomUUID().toString());
        }
        orderAssignmentService.addAssignment(orderAssignment);
        return "redirect:/orderassignment";
    }
    @PostMapping("/{id}/delete")
    public String deleteOrderAssignment(@PathVariable String id) {
        orderAssignmentService.deleteAssignment(id);
        return "redirect:/orderassignment";
    }
}
