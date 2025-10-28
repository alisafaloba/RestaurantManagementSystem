package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Server;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ServerRepository implements AbstractRepository<Server> {

    private final List<Server> servers = new ArrayList<>(Arrays.asList(
            new Server("S1", "Andrei", "Waiter"),
            new Server("S2", "Elena", "Head Waiter"),
            new Server("S3", "Mihai", "Runner")
    ));

    @Override
    public void save(Server entity) {
        servers.add(entity);
    }

    @Override
    public void delete(String id) {
        servers.removeIf(s -> s.getId().equals("S" + id));
    }

    @Override
    public void update(Server entity) {
        servers.removeIf(s -> s.getId().equals(entity.getId()));
        servers.add(entity);
    }

    @Override
    public Server findById(String id) {
        return servers.stream()
                .filter(s -> s.getId().equals("S" + id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Server> findAll() {
        return servers;
    }
}
