package com.example.restaurant_management_system.model;



public class Bill {
    private String id;
    private String orderId;
    private double totalAmount;

    public Bill() {}

    public Bill(String id, String orderId, double totalAmount) {
        this.id = id;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}

