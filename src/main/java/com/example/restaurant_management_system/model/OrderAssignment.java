package com.example.restaurant_management_system.model;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "order_assignments")
public class OrderAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Switched to Long ID

    // ----------------------------------------------------
    // ENTITY RELATIONSHIPS (ManyToOne)

    // 1. Link back to the Order entity (replaces orderId)
    // This completes the bi-directional mapping from Order (@OneToMany)
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @NotNull(message = "Order must be assigned")
    private Order order;

    // 2. Link to the Staff entity (replaces staffId)
    // Note: Staff is an abstract MappedSuperclass, so we link to the specific type (e.g., Server)
    // or to a concrete entity that represents all assignable staff if you have one.
    // For simplicity, assuming you link to the Staff root entity if possible, or a specific Server/Chef entity.
    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    @NotNull(message = "Staff member must be assigned")
    private Staff staff; // Assumes Staff is a concrete entity or linked correctly

    // ----------------------------------------------------

    public OrderAssignment() {}

    public OrderAssignment(Order order, Staff staff) {
        this.order = order;
        this.staff = staff;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Staff getStaff() { return staff; }
    public void setStaff(Staff staff) { this.staff = staff; }

    @Override
    public String toString() {
        return "Assignment{id=" + id + ", staffId=" + (staff != null ? staff.getId() : "null") + "}";
    }
}