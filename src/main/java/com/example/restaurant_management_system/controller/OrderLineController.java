package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.OrderLine;
import com.example.restaurant_management_system.service.OrderLineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orderline")
public class OrderLineController {

    private final OrderLineService orderLineService;

    public OrderLineController(OrderLineService orderLineService) {
        this.orderLineService = orderLineService;
    }

    @GetMapping
    public String listOrderLines(Model model) {
        model.addAttribute("orderlines", orderLineService.getAllOrderLines());
        return "orderLine/index";
    }

    //@GetMapping("/new")
    //public String showCreateForm(Model model) {
       // model.addAttribute("orderline", new OrderLine());
       // return "orderline/form";
    //}

    @PostMapping
    public String createOrderLine(@ModelAttribute("orderline") OrderLine orderLine) {

        if (orderLine.getId() == null || orderLine.getId().isEmpty()) {
            orderLine.setId(java.util.UUID.randomUUID().toString());
        }

        orderLineService.addOrderLine(orderLine);

        // if created *from an order*, go back to that order’s details
        if (orderLine.getOrderId() != null) {
            return "redirect:/order/" + orderLine.getOrderId() + "/details";
        }

        return "redirect:/orderline"; // fallback
    }




    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        OrderLine orderLine = orderLineService.getOrderLineById(id);
        model.addAttribute("orderline", orderLine);
        return "orderLine/form";
    }

    @PostMapping("/{id}/update")
    public String updateOrderLine(@PathVariable String id, @ModelAttribute("orderline") OrderLine orderLine) {
        orderLine.setId(id);
        orderLineService.updateOrderLine(orderLine);
        return "redirect:/orderline";
    }

    @GetMapping("/new")
    public String showCreateForm(@RequestParam(value = "orderId", required = false) String orderId,
                                 Model model) {

        OrderLine line = new OrderLine();
        line.setOrderId(orderId); // prefill like assignment controller

        model.addAttribute("orderline", line);
        return "orderLine/form";
    }

    @PostMapping("/{id}/delete")
    public String deleteOrderLine(@PathVariable String id) {

        OrderLine line = orderLineService.getOrderLineById(id);
        String orderId = (line != null) ? line.getOrderId() : null;

        orderLineService.deleteOrderLine(id);

        if (orderId != null) {
            return "redirect:/order/" + orderId + "/details";
        }

        return "redirect:/orderline";
    }


}
