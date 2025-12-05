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

    // Change ID type to Long and use JPA's findById
    public Chef getChefById(Long id) {
        return chefRepository.findById(id).orElse(null);
    }

    public void addChef(Chef chef) {
        chefRepository.save(chef);
    }

    public void updateChef(Chef chef) {
        // JPA's save() handles both add and update
        chefRepository.save(chef);
    }

    // Change ID type to Long and use JPA's deleteById
    public void deleteChef(Long id) {
        chefRepository.deleteById(id);
    }
}