package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.Chef;
import com.example.restaurant_management_system.repository.ChefRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChefService {

    private final ChefRepository chefRepository;

    public ChefService(ChefRepository chefRepository) {
        this.chefRepository = chefRepository;
    }

    /**
     * Ruft alle Chefs ab, wendet Sortierung und Filterung an.
     *
     * @param sortField Das Attribut, nach dem sortiert werden soll (z.B. "name")
     * @param sortDir Die Sortierrichtung ("asc" oder "desc")
     * @param nameFilter Filter für den Namen (Teilstring)
     * @param specializationFilter Filter für die Spezialisierung (Teilstring)
     * @return Liste der gefilterten und sortierten Chefs
     */
    public List<Chef> getAllChefs(String sortField, String sortDir,
                                  String nameFilter, String specializationFilter) {

        // Erstellung des Sortierobjekts
        Sort sort = Sort.by(sortField);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();

        // Aufruf der Repository-Methode für kombiniertes Filtern und Sortieren
        return chefRepository.findFilteredChefs(nameFilter, specializationFilter, sort);
    }

    // --- CRUD-Methoden ---

    // Original-Methoden beibehalten
    public Chef getChefById(Long id) {
        return chefRepository.findById(id).orElse(null);
    }

    public void addChef(Chef chef) {
        chefRepository.save(chef);
    }

    public void updateChef(Chef chef) {
        chefRepository.save(chef);
    }

    public void deleteChef(Long id) {
        chefRepository.deleteById(id);
    }
}