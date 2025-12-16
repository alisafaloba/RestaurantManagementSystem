package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.OrderLine;
import com.example.restaurant_management_system.repository.OrderLineRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;

    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    /**
     * Ruft alle Bestellzeilen ab, wendet Sortierung und Filterung an.
     */
    public List<OrderLine> getAllOrderLines(String sortField, String sortDir,
                                            Long orderIdFilter, Long menuItemIdFilter, Double minQuantity) {

        Sort sort = Sort.by(sortField);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();

        return orderLineRepository.findFilteredOrderLines(orderIdFilter, menuItemIdFilter, minQuantity, sort);
    }

    // Die parameterlose Methode leitet auf die neue Methode um
    public List<OrderLine> getAllOrderLines() {
        return getAllOrderLines("id", "asc", null, null, null);
    }

    // --- CRUD-Methoden ---

    public OrderLine getOrderLineById(Long id) {
        return orderLineRepository.findById(id).orElse(null);
    }

    public void addOrderLine(OrderLine line) {
        orderLineRepository.save(line);
    }

    public void updateOrderLine(OrderLine line) {
        orderLineRepository.save(line);
    }

    public void deleteOrderLine(Long id) {
        orderLineRepository.deleteById(id);
    }
}