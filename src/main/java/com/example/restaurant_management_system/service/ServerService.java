package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.Server;
import com.example.restaurant_management_system.repository.ServerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerService {

    private final ServerRepository serverRepository;

    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public List<Server> getAllServers() {
        return serverRepository.findAll();
    }

    // Change ID type to Long
    public Server getServerById(Long id) {
        return serverRepository.findById(id).orElse(null);
    }

    public void addServer(Server server) {
        serverRepository.save(server);
    }

    public void updateServer(Server server) {
        // JPA's save() handles both add and update
        serverRepository.save(server);
    }

    // Change ID type to Long
    public void deleteServer(Long id) {
        serverRepository.deleteById(id);
    }
}