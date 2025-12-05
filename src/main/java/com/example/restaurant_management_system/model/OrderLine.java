package com.example.restaurant_management_system.model;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "order_lines")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Switched to Long ID

    // ----------------------------------------------------
    // ENTITY RELATIONSHIPS (ManyToOne)

    // 1. Link to the MenuItem entity (replaces menuItemId)
    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false)
    @NotNull(message = "Menu item is required")
    private MenuItem menuItem; // Assumes a MenuItem entity exists

    // 2. Link back to the Order entity (replaces orderId)
    // This completes the bi-directional mapping from Order (@OneToMany)
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @NotNull(message = "Order is required")
    private Order order;

    // ----------------------------------------------------

    @Positive(message = "Quantity must be a positive number")
    @NotNull(message = "Quantity is required")
    private Double quantity; // Use Double wrapper for @NotNull validation robustness

    public OrderLine() {}

    public OrderLine(MenuItem menuItem, Double quantity, Order order) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.order = order;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public MenuItem getMenuItem() { return menuItem; }
    public void setMenuItem(MenuItem menuItem) { this.menuItem = menuItem; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}