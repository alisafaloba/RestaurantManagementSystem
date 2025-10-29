package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Server;
import org.springframework.stereotype.Repository;

@Repository
public class ServerRepository extends InMemoryRepository<Server> {
    public ServerRepository() {
        super(Server.class);
    }
}
