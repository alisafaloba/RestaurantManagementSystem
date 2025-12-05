package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.OrderLine;
import com.example.restaurant_management_system.service.MenuItemService;
import com.example.restaurant_management_system.service.OrderLineService;
import com.example.restaurant_management_system.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/orderline")
public class OrderLineController {

    private final OrderLineService orderLineService;
    private final OrderService orderService;
    private final MenuItemService menuItemService;

    public OrderLineController(
            OrderLineService orderLineService,
            OrderService orderService,
            MenuItemService menuItemService
    ) {
        this.orderLineService = orderLineService;
        this.orderService = orderService;
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public String listOrderLines(Model model) {
        model.addAttribute("orderlines", orderLineService.getAllOrderLines());
        return "orderLine/index";
    }

    @GetMapping("/new")
    public String showCreateForm(@RequestParam(value = "orderId", required = false) Long orderId,
                                 Model model) {
        OrderLine line = new OrderLine();
        model.addAttribute("orderline", line);

        // dropdown data
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("menuItems", menuItemService.getAllMenuItems());

        // optional: preselect an order if passed as request param
        model.addAttribute("preselectedOrderId", orderId);

        return "orderLine/form";
    }

    @PostMapping
    public String createOrderLine(@Valid @ModelAttribute("orderline") OrderLine orderLine,
                                  BindingResult bindingResult,
                                  Model model) {

        if (bindingResult.hasErrors()) {
            // re-add dropdowns before returning form
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("menuItems", menuItemService.getAllMenuItems());
            return "orderLine/form";
        }

        orderLineService.addOrderLine(orderLine);

        if (orderLine.getOrder() != null && orderLine.getOrder().getId() != null) {
            return "redirect:/order/" + orderLine.getOrder().getId() + "/details";
        }

        return "redirect:/orderline";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        OrderLine line = orderLineService.getOrderLineById(id);
        if (line == null) {
            redirectAttributes.addFlashAttribute("error", "Order line not found");
            return "redirect:/orderline";
        }

        model.addAttribute("orderline", line);
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("menuItems", menuItemService.getAllMenuItems());

        return "orderLine/form";
    }

    @PostMapping("/{id}/update")
    public String updateOrderLine(@PathVariable Long id,
                                  @Valid @ModelAttribute("orderline") OrderLine orderLine,
                                  BindingResult bindingResult,
                                  Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("menuItems", menuItemService.getAllMenuItems());
            return "orderLine/form";
        }

        orderLine.setId(id);
        orderLineService.updateOrderLine(orderLine);

        return "redirect:/orderline";
    }

    @PostMapping("/{id}/delete")
    public String deleteOrderLine(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        var line = orderLineService.getOrderLineById(id);
        Long orderId = (line != null && line.getOrder() != null) ? line.getOrder().getId() : null;

        try {
            orderLineService.deleteOrderLine(id);
            redirectAttributes.addFlashAttribute("success", "Order line deleted");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete order line");
        }

        if (orderId != null) {
            return "redirect:/order/" + orderId + "/details";
        }
        return "redirect:/orderline";
    }
}
