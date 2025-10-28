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

    public OrderLine getOrderLineById(String id) {
        return orderLineRepository.findById(id);
    }

    public void addOrderLine(OrderLine line) {
        orderLineRepository.save(line);
    }

    public void updateOrderLine(OrderLine line) {
        orderLineRepository.update(line);
    }

    public void deleteOrderLine(String id) {
        orderLineRepository.delete(id);
    }
}
