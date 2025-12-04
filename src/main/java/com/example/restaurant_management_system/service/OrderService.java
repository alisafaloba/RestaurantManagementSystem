package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.Order;
import com.example.restaurant_management_system.model.OrderAssignment;
import com.example.restaurant_management_system.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderAssignmentService assignmentService;
    private final OrderLineService orderLineService;

    public OrderService(OrderRepository orderRepository, OrderAssignmentService assignmentService, OrderLineService orderLineService) {
        this.orderRepository = orderRepository;
        this.assignmentService = assignmentService;
        this.orderLineService = orderLineService;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            loadAssignments(order); // existing
            loadOrderLines(order); // new
        }
        return orders;
    }

    public Order getOrderById(String id) {
        Order order = orderRepository.findById(id);
        loadAssignments(order);
        loadOrderLines(order);
        return order;
    }

    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    public void updateOrder(Order order) {orderRepository.save(order);}

    public void deleteOrder(String id) {
        orderRepository.delete(id);
    }

    private void loadAssignments(Order order) {
        List<OrderAssignment> assignments = assignmentService.getAllAssignments()
                .stream()
                .filter(a -> a.getOrderId().equals(order.getId()))
                .toList();

        order.setAssignments(assignments);

    }


    private void loadOrderLines(Order order) {
        order.setOrderLines(orderLineService.getAllOrderLines().stream()
                .filter(line -> order.getId().equals(line.getOrderId()))
                .toList());
    }


}
