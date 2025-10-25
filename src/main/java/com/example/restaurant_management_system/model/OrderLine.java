package com.example.restaurant_management_system.model;

public class OrderLine {
    private String id;
    private String menuItemId;
    private double quantity;

    public OrderLine() {}

    public OrderLine(String id, String menuItemId, double quantity) {
        this.id = id;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMenuItemId() { return menuItemId; }
    public void setMenuItemId(String menuItemId) { this.menuItemId = menuItemId; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
}

