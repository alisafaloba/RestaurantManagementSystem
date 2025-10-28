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

    public Staff getStaffById(int id) {
        return staffRepository.findById(id);
    }

    public void addStaff(Staff s) {
        staffRepository.save(s);
    }

    public void updateStaff(Staff s) {
        staffRepository.update(s);
    }

    public void deleteStaff(int id) {
        staffRepository.delete(id);
    }
}
