package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Order;
import com.example.restaurant_management_system.model.OrderStatus;
import com.example.restaurant_management_system.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // GET /order → show all orders
    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "order/index"; // templates/order/index.html
    }

    // GET /order/new → show form to create a new order
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Order order = new Order();
        order.setStatus(OrderStatus.OPEN);
        order.setDate(new Date());

        model.addAttribute("order", order);
        model.addAttribute("statuses", OrderStatus.values());
        return "order/form"; // templates/order/form.html
    }

    // POST /order → create new order
    @PostMapping
    public String createOrder(@ModelAttribute Order order) {
        if (order.getId() == null || order.getId().isEmpty()) {
            order.setId(UUID.randomUUID().toString());
        }
        if (order.getDate() == null) {
            order.setDate(new Date());
        }
        orderService.addOrder(order);

        return "redirect:/order";
    }

    // POST /order/{id}/delete → delete order
    @PostMapping("/{id}/delete")
    public String deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return "redirect:/order";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "order/form";
    }
    @PostMapping("/{id}/update")
    public String updateOrder(@PathVariable String id, @ModelAttribute Order order) {
        order.setId(id);
        orderService.updateOrder(order);
        return "redirect:/order";
    }
}
