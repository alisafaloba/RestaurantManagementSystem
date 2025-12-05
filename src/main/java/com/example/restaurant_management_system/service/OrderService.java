package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.Order;
import com.example.restaurant_management_system.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    // Remove dependencies on OrderAssignmentService and OrderLineService
    // for simple loading, as JPA handles it. You might keep them for business logic.

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        // JPA loads relationships (if configured with FetchType.EAGER or accessed later)
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) { // Change ID type to Long
        // JPA handles loading assignments/orderLines automatically now
        return orderRepository.findById(id).orElse(null);
    }

    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    public void deleteOrder(Long id) { // Change ID type to Long
        orderRepository.deleteById(id);
    }

    // REMOVE these methods as they are now handled by JPA entity relationships:
    // private void loadAssignments(Order order) { ... }
    // private void loadOrderLines(Order order) { ... }
}