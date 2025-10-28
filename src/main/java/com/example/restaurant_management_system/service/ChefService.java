package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.Chef;
import com.example.restaurant_management_system.repository.ChefRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChefService {

    private final ChefRepository chefRepository;

    public ChefService(ChefRepository chefRepository) {
        this.chefRepository = chefRepository;
    }

    public List<Chef> getAllChefs() {
        return chefRepository.findAll();
    }

    public Chef getChefById(String id) {
        return chefRepository.findById(id);
    }

    public void addChef(Chef chef) {
        chefRepository.save(chef);
    }

    public void updateChef(Chef chef) {
        chefRepository.update(chef);
    }

    public void deleteChef(String id) {
        chefRepository.delete(id);
    }
}
