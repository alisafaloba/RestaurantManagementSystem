package com.example.restaurant_management_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "servers") // Mapped to its own table
public class Server extends Staff {

    @NotBlank(message = "Designation cannot be empty")
    private String designation;

    public Server() {}

    public Server(String name, String designation) {
        super(name);
        this.designation = designation;
    }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
}