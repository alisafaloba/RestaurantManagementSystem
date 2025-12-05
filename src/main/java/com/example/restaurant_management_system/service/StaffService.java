package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.Staff;
import com.example.restaurant_management_system.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    // Change ID type from String to Long, and use JpaRepository.findById
    public Staff getStaffById(Long id) {
        return staffRepository.findById(id).orElse(null);
    }

    public void addStaff(Staff s) {
        staffRepository.save(s);
    }

    public void updateStaff(Staff s) {
        // JpaRepository.save() handles both insert and update
        staffRepository.save(s);
    }

    // Change ID type from String to Long, and use JpaRepository.deleteById
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }
}