package com.example.restaurant_management_system.model;

public class OrderLine {
    private String id;
    private String menuItemId;
    private double quantity;
    private String orderId;

    public OrderLine() {}

    public OrderLine(String id, String menuItemId, double quantity, String orderId) {
        this.id = id;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.orderId = orderId;
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMenuItemId() { return menuItemId; }
    public void setMenuItemId(String menuItemId) { this.menuItemId = menuItemId; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

}

