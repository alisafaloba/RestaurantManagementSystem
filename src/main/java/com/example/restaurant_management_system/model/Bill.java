package com.example.restaurant_management_system.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // DB verwaltet IDs automatisch!

    @NotBlank(message = "Order ID cannot be empty")
    private String orderId;

    @Positive(message = "Total amount must be positive")
    private double totalAmount;

    public Bill() {}

    public Bill(String orderId, double totalAmount) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
    }

    public Long getId() { return id; }
    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}
