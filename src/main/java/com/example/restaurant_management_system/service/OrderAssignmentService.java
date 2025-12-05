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

    // Change ID type from String to Long, and use JpaRepository.findById
    public OrderAssignment getAssignmentById(Long id) {
        return assignmentRepository.findById(id).orElse(null);
    }

    public void addAssignment(OrderAssignment assignment) {
        assignmentRepository.save(assignment);
    }

    public void updateAssignment(OrderAssignment assignment) {
        // JpaRepository.save() handles both insert and update
        assignmentRepository.save(assignment);
    }

    // Change ID type from String to Long, and use JpaRepository.deleteById
    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }
}