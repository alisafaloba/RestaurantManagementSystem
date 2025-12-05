package com.example.restaurant_management_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "chefs") // Mapped to its own table
public class Chef extends Staff {

    @NotBlank(message = "Specialization cannot be empty")
    private String specialization;

    public Chef() {}

    public Chef(String name, String specialization) {
        super(name);
        this.specialization = specialization;
    }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
}