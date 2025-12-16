package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.Order;
import com.example.restaurant_management_system.model.OrderStatus;
import com.example.restaurant_management_system.repository.OrderRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Ruft alle Bestellungen ab, wendet Sortierung und Filterung an.
     */
    public List<Order> getAllOrders(String sortField, String sortDir,
                                    OrderStatus statusFilter, LocalDateTime startDate, LocalDateTime endDate) {

        Sort sort = Sort.by(sortField);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();

        return orderRepository.findFilteredOrders(statusFilter, startDate, endDate, sort);
    }

    // --- CRUD-Methoden ---

    // Die ursprüngliche getAllOrders() muss umbenannt oder entfernt werden.
    // Wir ersetzen sie durch die gefilterte/sortierte Version.
    public List<Order> getAllOrders() {
        // Leitet an die neue Methode weiter, ohne Filter, sortiert nach ID
        return getAllOrders("id", "asc", null, null, null);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}