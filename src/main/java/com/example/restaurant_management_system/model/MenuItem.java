package com.example.restaurant_management_system.model;


import java.util.List;

public class MenuItem {
    private String id;
    private String name;
    private double price;
    private List<String> allergens;

    public MenuItem() {}

    public MenuItem(String id, String name, double price, List<String> allergens) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.allergens = allergens;
    }

    public List<String> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<String> allergens) {
        this.allergens = allergens;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
