package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.OrderAssignment;
import com.example.restaurant_management_system.repository.OrderAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderAssignmentService {

    private final OrderAssignmentRepository assignmentRepository;

    public OrderAssignmentService(OrderAssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<OrderAssignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public OrderAssignment getAssignmentById(int id) {
        return assignmentRepository.findById(id);
    }

    public void addAssignment(OrderAssignment assignment) {
        assignmentRepository.save(assignment);
    }

    public void updateAssignment(OrderAssignment assignment) {
        assignmentRepository.update(assignment);
    }

    public void deleteAssignment(int id) {
        assignmentRepository.delete(id);
    }

    // găsește assignment-urile pentru o comandă
    public List<OrderAssignment> findAssignmentsForOrder(String orderId) {
        return assignmentRepository.findAll().stream()
                .filter(a -> a.getOrderId().equals(orderId))
                .toList();
    }
}
