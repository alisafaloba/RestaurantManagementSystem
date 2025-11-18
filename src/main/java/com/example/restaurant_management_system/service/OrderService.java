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

    public Order getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    public void updateOrder(Order order) {orderRepository.save(order);}

    public void deleteOrder(String id) {
        orderRepository.delete(id);
    }
}
