package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.OrderAssignment;
import com.example.restaurant_management_system.repository.OrderAssignmentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderAssignmentService {

    private final OrderAssignmentRepository assignmentRepository;

    public OrderAssignmentService(OrderAssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    /**
     * Ruft alle Zuordnungen ab, wendet Sortierung und Filterung an.
     */
    public List<OrderAssignment> getAllAssignments(String sortField, String sortDir,
                                                   Long orderIdFilter, Long staffIdFilter) {

        Sort sort = Sort.by(sortField);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();

        return assignmentRepository.findFilteredAssignments(orderIdFilter, staffIdFilter, sort);
    }

    // Die parameterlose Methode leitet auf die neue Methode um
    public List<OrderAssignment> getAllAssignments() {
        return getAllAssignments("id", "asc", null, null);
    }

    // --- CRUD-Methoden ---

    public OrderAssignment getAssignmentById(Long id) {
        return assignmentRepository.findById(id).orElse(null);
    }

    public void addAssignment(OrderAssignment assignment) {
        assignmentRepository.save(assignment);
    }

    public void updateAssignment(OrderAssignment assignment) {
        assignmentRepository.save(assignment);
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }
}