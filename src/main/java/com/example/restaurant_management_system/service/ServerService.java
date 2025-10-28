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

    public Server getServerById(int id) {
        return serverRepository.findById(id);
    }

    public void addServer(Server server) {
        serverRepository.save(server);
    }

    public void updateServer(Server server) {
        serverRepository.update(server);
    }

    public void deleteServer(int id) {
        serverRepository.delete(id);
    }
}
