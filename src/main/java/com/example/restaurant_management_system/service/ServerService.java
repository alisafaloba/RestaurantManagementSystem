package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.Server;
import com.example.restaurant_management_system.repository.ServerRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerService {

    private final ServerRepository serverRepository;

    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    /**
     * Ruft alle Server ab, wendet Sortierung und Filterung an.
     */
    public List<Server> getAllServers(String sortField, String sortDir,
                                      String nameFilter, String designationFilter) {

        Sort sort = Sort.by(sortField);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();

        return serverRepository.findFilteredServers(nameFilter, designationFilter, sort);
    }

    // Die parameterlose Methode leitet auf die neue Methode um
    public List<Server> getAllServers() {
        return getAllServers("id", "asc", null, null);
    }

    // --- CRUD-Methoden ---

    public Server getServerById(Long id) {
        return serverRepository.findById(id).orElse(null);
    }

    public void addServer(Server server) {
        serverRepository.save(server);
    }

    public void updateServer(Server server) {
        serverRepository.save(server);
    }

    public void deleteServer(Long id) {
        serverRepository.deleteById(id);
    }
}