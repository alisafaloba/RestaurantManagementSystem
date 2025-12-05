package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.OrderLine;
import com.example.restaurant_management_system.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;

    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    public List<OrderLine> getAllOrderLines() {
        return orderLineRepository.findAll();
    }

    public OrderLine getOrderLineById(Long id) { // Changed type to Long
        return orderLineRepository.findById(id).orElse(null); // Use JpaRepository method
    }

    public void addOrderLine(OrderLine line) {
        orderLineRepository.save(line);
    }

    public void updateOrderLine(OrderLine line) {
        // JpaRepository.save() handles both insert and update
        orderLineRepository.save(line);
    }

    public void deleteOrderLine(Long id) { // Changed type to Long
        orderLineRepository.deleteById(id);
    }

    // REMOVED: getOrderLinesByOrderId is now obsolete as JPA handles the relationship
    // via the Order entity (Order.getOrderLines())
}