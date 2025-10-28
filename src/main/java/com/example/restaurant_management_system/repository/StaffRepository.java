package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Chef;
import com.example.restaurant_management_system.model.Server;
import com.example.restaurant_management_system.model.Staff;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class StaffRepository implements AbstractRepository<Staff> {

    private final List<Staff> staff = new ArrayList<>(Arrays.asList(
            new Server("S1", "Andrei", "Waiter"),
            new Chef("C1", "John Doe", "Italian"),
            new Server("S2", "Elena", "Head Waiter")
    ));

    @Override
    public void save(Staff entity) {
        staff.add(entity);
    }

    @Override
    public void delete(Integer id) {
        staff.removeIf(s -> s.getId().equals("S" + id) || s.getId().equals("C" + id));
    }

    @Override
    public void update(Staff entity) {
        staff.removeIf(s -> s.getId().equals(entity.getId()));
        staff.add(entity);
    }

    @Override
    public Staff findById(Integer id) {
        return staff.stream()
                .filter(s -> s.getId().endsWith(String.valueOf(id)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Staff> findAll() {
        return staff;
    }
}
