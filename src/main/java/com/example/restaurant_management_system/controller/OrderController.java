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

import java.util.Date;
import java.util.Arrays;
import org.springframework.validation.FieldError; // ADDED Import for manual validation

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
     */
    private void addFormAttributes(Model model) {
        // 1. Pass lists of entities required for the dropdowns
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("tables", tableService.getAllTables());

        // 2. Pass the list of OrderStatus enum values
        model.addAttribute("statuses", Arrays.asList(OrderStatus.values()));
    }


    // GET /order → show all orders
    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "order/index";
    }

    // GET /order/new → show form to create a new order
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Order order = new Order();
        order.setStatus(OrderStatus.OPEN);
        order.setDate(new Date()); // Pre-fill date/time

        model.addAttribute("order", order);
        addFormAttributes(model); // Load customers, tables, statuses
        return "order/form";
    }

    // POST /order → create new order
    @PostMapping
    public String createOrder(@ModelAttribute Order order, // REMOVED @Valid here
                              BindingResult bindingResult,
                              @RequestParam(required = false) Long customerId, // ADDED required=false
                              @RequestParam(required = false) Long tableId,   // ADDED required=false
                              Model model) {

        // --- Step 1: Handle binding errors for fields that are part of Order object (like date/status) ---
        if (bindingResult.hasErrors()) {
            // Note: If customer/table were not set via dropdown, they might cause
            // validation errors later, but here we handle basic field binding errors.
            addFormAttributes(model);
            return "order/form";
        }

        // --- Step 2: Manually check and set the Customer and Table entities ---

        // Check if customerId was provided
        if (customerId == null || customerId <= 0) {
            // Manually add an error to BindingResult for the 'customer' property
            bindingResult.addError(new FieldError("order", "customer", order.getCustomer(), false, null, null, "Customer is required"));
        } else {
            order.setCustomer(customerService.getCustomerById(customerId));
            if (order.getCustomer() == null) {
                bindingResult.addError(new FieldError("order", "customer", "Invalid customer ID provided."));
            }
        }

        // Check if tableId was provided
        if (tableId == null || tableId <= 0) {
            // Manually add an error to BindingResult for the 'table' property
            bindingResult.addError(new FieldError("order", "table", order.getTable(), false, null, null, "Table is required"));
        } else {
            order.setTable(tableService.getTableById(tableId));
            if (order.getTable() == null) {
                bindingResult.addError(new FieldError("order", "table", "Invalid table ID provided."));
            }
        }

        // --- Step 3: Re-check BindingResult for newly added entity errors ---
        if (bindingResult.hasErrors()) {
            addFormAttributes(model);
            return "order/form";
        }

        // --- Step 4: Save the order if everything is valid ---
        orderService.addOrder(order);
        return "redirect:/order";
    }

    // POST /order/{id}/delete → delete order (ID is now Long)
    @PostMapping("/{id}/delete")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/order";
    }

    // GET /order/{id}/edit → show edit form (ID is now Long)
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        addFormAttributes(model); // Load customers, tables, statuses
        return "order/form";
    }

    // POST /order/{id}/update (ID is now Long, requires @Valid)
    // ... (imports and class definition remain the same)

// ... (methods up to showEditForm remain the same)

    // POST /order/{id}/update (ID is now Long, requires @Valid)
    // ... (imports and class definition remain the same)

// ... (methods up to showEditForm remain the same)

    // POST /order/{id}/update (ID is now Long, requires @Valid)
    @PostMapping("/{id}/update")
    public String updateOrder(@PathVariable Long id,
                              @ModelAttribute Order updatedOrder, // Renamed to clearly separate from DB entity
                              BindingResult bindingResult,
                              @RequestParam(required = false) Long customerId,
                              @RequestParam(required = false) Long tableId,
                              Model model) {

        // 1. Handle binding errors for fields that are part of Order object (like date/status)
        if (bindingResult.hasErrors()) {
            addFormAttributes(model);
            return "order/form";
        }

        // --- Step 2: Retrieve the EXISTING Order entity from the database ---
        Order existingOrder = orderService.getOrderById(id);

        if (existingOrder == null) {
            // Should not happen if ID comes from an existing object, but good practice to check
            return "redirect:/order";
        }

        // 3. Manually check and set the Customer and Table entities on the updatedOrder object
        //    (This logic from the previous answer is still needed for validation)

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

        // 4. Re-check BindingResult for newly added entity errors
        if (bindingResult.hasErrors()) {
            addFormAttributes(model);
            // Re-add the updatedOrder to the model if validation fails
            model.addAttribute("order", updatedOrder);
            return "order/form";
        }

        // --- Step 5: Copy the updated fields to the existing (managed) entity ---

        // Simple fields
        existingOrder.setStatus(updatedOrder.getStatus());
        existingOrder.setDate(updatedOrder.getDate());

        // ManyToOne relationships (which were properly set on updatedOrder in step 3)
        existingOrder.setCustomer(updatedOrder.getCustomer());
        existingOrder.setTable(updatedOrder.getTable());

        // NOTE: The assignments and orderLines lists on existingOrder remain untouched.

        // 6. Save the existing (managed) order
        // The save method will now correctly update the managed entity without touching collections
        orderService.updateOrder(existingOrder);
        return "redirect:/order";
    }

// ... (rest of the controller remains the same)

    // GET /order/{id}/details (ID is now Long)
    @GetMapping("/{id}/details")
    public String showOrderDetails(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "order/details";
    }
}