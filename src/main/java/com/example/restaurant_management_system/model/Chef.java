package com.example.restaurant_management_system.model;

public class Chef extends Staff {
    private String specialization;

    public Chef() {}

    public Chef(String id, String name, String specialization) {
        super(id, name);
        this.specialization = specialization;
    }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
}
