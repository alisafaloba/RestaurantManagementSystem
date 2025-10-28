package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.Order;
import com.example.restaurant_management_system.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(int id) {
        return orderRepository.findById(id);
    }

    public void createOrder(Order order) {
        order.setStatus("Open");
        orderRepository.save(order);
    }

    public void closeOrder(Order order) {
        order.setStatus("Closed");
        orderRepository.update(order);
    }

    public void deleteOrder(int id) {
        orderRepository.delete(id);
    }

    public List<Order> getOpenOrders() {
        return orderRepository.findAll().stream()
                .filter(o -> "Open".equalsIgnoreCase(o.getStatus()))
                .toList();
    }
}
