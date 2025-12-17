package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Order;
import com.example.restaurant_management_system.model.OrderStatus;
import com.example.restaurant_management_system.service.CustomerService;
import com.example.restaurant_management_system.service.TableService;
import com.example.restaurant_management_system.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;
import org.springframework.format.annotation.DateTimeFormat; // WICHTIG für LocalDateTime

import java.time.LocalDateTime;
import java.util.Arrays;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final TableService tableService;

    public OrderController(OrderService orderService,
                           CustomerService customerService,
                           TableService tableService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.tableService = tableService;
    }

    /**
     * Helper method to load data required by the order/form.html template.
     * Muss customerService.getAllCustomers() mit den neuen Argumenten aufrufen.
     */
    private void addFormAttributes(Model model) {
        // 1. Pass lists of entities required for the dropdowns
        // Rufe die gefilterte/sortierte Methode mit NULL-Filtern auf, um ALLE Kunden zu erhalten.
        model.addAttribute("customers", customerService.getAllCustomers("name", "asc", null, null));

        // Annahme: TableService hat noch die alte Signatur ohne Filter/Sortierung.
        model.addAttribute("tables", tableService.getAllTables());

        // 2. Pass the list of OrderStatus enum values
        model.addAttribute("statuses", Arrays.asList(OrderStatus.values()));
    }


    // ERSATZ der ursprünglichen listOrders durch die Filter-/Sortier-Version
    @GetMapping
    public String listOrders(Model model,
                             // Sortier-Parameter
                             @RequestParam(defaultValue = "id") String sortField,
                             @RequestParam(defaultValue = "asc") String sortDir,
                             // Filter-Parameter
                             @RequestParam(required = false) OrderStatus statusFilter,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        // Ruft den Service mit allen Parametern auf
        model.addAttribute("orders",
                orderService.getAllOrders(sortField, sortDir, statusFilter, startDate, endDate));

        // Sortier-Infos für das UI
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equalsIgnoreCase("asc") ? "desc" : "asc");

        // Filter-Infos, um die Werte im Filterformular beizubehalten
        model.addAttribute("statusFilter", statusFilter);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        // Füge alle OrderStatus-Werte hinzu (für Filter-Dropdown)
        model.addAttribute("allStatuses", Arrays.asList(OrderStatus.values()));

        return "order/index";
    }

    // --- CRUD-Methoden bleiben unverändert, aber addFormAttributes wurde korrigiert ---

    // GET /order/new → show form to create a new order
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Order order = new Order();
        order.setStatus(OrderStatus.OPEN);
        order.setDate(LocalDateTime.now()); // Pre-fill date/time

        model.addAttribute("order", order);
        addFormAttributes(model); // Load customers, tables, statuses
        return "order/form";
    }

    // POST /order → create new order
    @PostMapping
    public String createOrder(@Valid @ModelAttribute Order order,
                              BindingResult bindingResult,
                              @RequestParam(required = false) Long customerId,
                              @RequestParam(required = false) Long tableId,
                              Model model) {

        // Manuelle Validierung (wie zuvor)
        if (bindingResult.hasErrors()) {
            addFormAttributes(model);
            return "order/form";
        }

        // Manuell Customer setzen (da @Valid nicht direkt auf ManyToOne funktioniert)
        if (customerId == null || customerId <= 0) {
            bindingResult.addError(new FieldError("order", "customer", order.getCustomer(), false, null, null, "Customer is required"));
        } else {
            // Korrigierter Aufruf von getAllCustomers (sollte getCustomerById sein, was hier korrekt ist)
            order.setCustomer(customerService.getCustomerById(customerId));
            if (order.getCustomer() == null) {
                bindingResult.addError(new FieldError("order", "customer", "Invalid customer ID provided."));
            }
        }

        // Manuell Table setzen
        if (tableId == null || tableId <= 0) {
            bindingResult.addError(new FieldError("order", "table", order.getTable(), false, null, null, "Table is required"));
        } else {
            order.setTable(tableService.getTableById(tableId));
            if (order.getTable() == null) {
                bindingResult.addError(new FieldError("order", "table", "Invalid table ID provided."));
            }
        }

        if (bindingResult.hasErrors()) {
            addFormAttributes(model);
            return "order/form";
        }

        orderService.addOrder(order);
        return "redirect:/order";
    }

    // POST /order/{id}/delete → delete order
    @PostMapping("/{id}/delete")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/order";
    }

    // GET /order/{id}/edit → show edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        addFormAttributes(model); // Load customers, tables, statuses
        return "order/form";
    }

    // POST /order/{id}/update
    @PostMapping("/{id}/update")
    public String updateOrder(@PathVariable Long id,
                              @ModelAttribute Order updatedOrder,
                              BindingResult bindingResult,
                              @RequestParam(required = false) Long customerId,
                              @RequestParam(required = false) Long tableId,
                              Model model) {

        if (bindingResult.hasErrors()) {
            addFormAttributes(model);
            return "order/form";
        }

        Order existingOrder = orderService.getOrderById(id);
        if (existingOrder == null) { return "redirect:/order"; }

        // Manuelle Validierung (Customer/Table)
        // Check and set Customer
        if (customerId == null || customerId <= 0) {
            bindingResult.addError(new FieldError("updatedOrder", "customer", updatedOrder.getCustomer(), false, null, null, "Customer is required"));
        } else {
            updatedOrder.setCustomer(customerService.getCustomerById(customerId));
            if (updatedOrder.getCustomer() == null) {
                bindingResult.addError(new FieldError("updatedOrder", "customer", "Invalid customer ID provided."));
            }
        }

        // Check and set Table
        if (tableId == null || tableId <= 0) {
            bindingResult.addError(new FieldError("updatedOrder", "table", updatedOrder.getTable(), false, null, null, "Table is required"));
        } else {
            updatedOrder.setTable(tableService.getTableById(tableId));
            if (updatedOrder.getTable() == null) {
                bindingResult.addError(new FieldError("updatedOrder", "table", "Invalid table ID provided."));
            }
        }

        if (bindingResult.hasErrors()) {
            addFormAttributes(model);
            model.addAttribute("order", updatedOrder);
            return "order/form";
        }

        // Kopieren der Felder
        existingOrder.setStatus(updatedOrder.getStatus());
        existingOrder.setDate(updatedOrder.getDate());
        existingOrder.setCustomer(updatedOrder.getCustomer());
        existingOrder.setTable(updatedOrder.getTable());

        orderService.updateOrder(existingOrder);
        return "redirect:/order";
    }

    // GET /order/{id}/details (Bleibt unverändert)
    @GetMapping("/{id}/details")
    public String showOrderDetails(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("order lines", order.getOrderLines());
        model.addAttribute("assignments", order.getAssignments());
        return "order/details";
    }
}