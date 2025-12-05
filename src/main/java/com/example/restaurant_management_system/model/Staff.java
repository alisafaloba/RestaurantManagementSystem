package com.example.restaurant_management_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity // FIX: Must be an Entity to be a target of @ManyToOne
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // FIX: TPC strategy for polymorphism
public abstract class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE) // FIX: Use GenerationType.TABLE for TPC
    protected Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 100, message = "Name length must be between 2 and 100")
    protected String name;

    public Staff() {}

    public Staff(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}