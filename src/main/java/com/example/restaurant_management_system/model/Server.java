package com.example.restaurant_management_system.model;


public class Server extends Staff {
    private String designation;

    public Server() {}

    public Server(String id, String name, String designation) {
        super(id, name);
        this.designation = designation;
    }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
}

