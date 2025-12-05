package com.example.restaurant_management_system.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Switched from String to Long ID

    // ----------------------------------------------------
    // ENTITY RELATIONSHIPS (ManyToOne)
    // Replaced String customerId with Customer entity object
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "Customer is required")
    private Customer customer; // Assumes a Customer entity exists

    // Replaced String tableId with Table entity object
    @ManyToOne
    @JoinColumn(name = "table_id", nullable = false)
    @NotNull(message = "Table is required")
    private com.example.restaurant_management_system.model.Table table; // Assumes a Table entity exists
    // ----------------------------------------------------

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status cannot be null")
    private OrderStatus status; // "Open" or "Closed"

    @PastOrPresent(message = "Order date cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") // <-- FIX: Add 'T' separator
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Date is required")
    private Date date;


    // ----------------------------------------------------
    // ENTITY RELATIONSHIPS (OneToMany)
    // Handled loading of related lists via JPA annotations

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLine> orderLines; // Assumes OrderLine entity exists

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderAssignment> assignments; // Assumes OrderAssignment entity exists
    // ----------------------------------------------------


    public Order() {}

    // Basic constructor for creating new entities via service
    public Order(Customer customer,
                 com.example.restaurant_management_system.model.Table table, // <-- Use the full path here
                 OrderStatus status,
                 Date date) {

        this.customer = customer;
        this.table = table;
        this.status = status;
        this.date = date;
    }

    // Getters and Setters (Updated for Long ID and Entity Objects)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public com.example.restaurant_management_system.model.Table getTable() {
        return table;
    }

    public void setTable(com.example.restaurant_management_system.model.Table table) {
        this.table = table;
    }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public List<OrderLine> getOrderLines() { return orderLines; }
    public void setOrderLines(List<OrderLine> orderLines) { this.orderLines = orderLines; }

    public List<OrderAssignment> getAssignments() { return assignments; }
    public void setAssignments(List<OrderAssignment> assignments) { this.assignments = assignments; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}