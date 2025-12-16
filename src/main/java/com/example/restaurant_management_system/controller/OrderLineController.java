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

    private void addFormAttributes(Model model) {
        // Nutzt die parameterlosen Methoden, die alle Daten liefern
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("menuItems", menuItemService.getAllMenuItems());
    }

    // GET /orderline → list all order lines with filtering and sorting (UPDATED)
    @GetMapping
    public String listOrderLines(Model model,
                                 // Sortier-Parameter
                                 @RequestParam(defaultValue = "id") String sortField,
                                 @RequestParam(defaultValue = "asc") String sortDir,
                                 // Filter-Parameter (kombiniert)
                                 @RequestParam(required = false) Long orderIdFilter,
                                 @RequestParam(required = false) Long menuItemIdFilter,
                                 @RequestParam(required = false) Double minQuantity) {

        // Daten aus dem Service abrufen (gefiltert und sortiert)
        model.addAttribute("orderlines",
                orderLineService.getAllOrderLines(sortField, sortDir, orderIdFilter, menuItemIdFilter, minQuantity));

        // Sortier-Infos für das UI
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equalsIgnoreCase("asc") ? "desc" : "asc");

        // Filter-Infos, um die Werte im Filterformular beizubehalten
        model.addAttribute("orderIdFilter", orderIdFilter);
        model.addAttribute("menuItemIdFilter", menuItemIdFilter);
        model.addAttribute("minQuantity", minQuantity);

        return "orderLine/index";
    }

    @GetMapping("/new")
    public String showCreateForm(@RequestParam(value = "orderId", required = false) Long orderId,
                                 Model model) {
        OrderLine line = new OrderLine();
        model.addAttribute("orderline", line);
        addFormAttributes(model); // Load dropdown data

        model.addAttribute("preselectedOrderId", orderId);

        return "orderLine/form";
    }

    @PostMapping
    public String createOrderLine(@Valid @ModelAttribute("orderline") OrderLine orderLine,
                                  BindingResult bindingResult,
                                  Model model) {

        if (bindingResult.hasErrors()) {
            addFormAttributes(model);
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
        addFormAttributes(model); // Load dropdown data

        return "orderLine/form";
    }

    @PostMapping("/{id}/update")
    public String updateOrderLine(@PathVariable Long id,
                                  @Valid @ModelAttribute("orderline") OrderLine orderLine,
                                  BindingResult bindingResult,
                                  Model model) {

        if (bindingResult.hasErrors()) {
            addFormAttributes(model);
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