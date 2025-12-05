package com.example.restaurant_management_system.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


import java.util.List;

@Entity // 1. Annotated as a JPA entity
@jakarta.persistence.Table(name = "restaurant_tables")
public class Table {

    @Id // 3. Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Switched from String to Long ID

    @NotNull(message = "Table number is required") // 4. Backend Validation
    @Positive(message = "Table number must be positive")
    @Column(unique = true) // Ensures the table number is unique
    private Integer number; // Changed 'int' to 'Integer' for @NotNull validation

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Occupancy status is required") // 4. Backend Validation
    private TableStatus occupiedStatus; // Assumes TableStatus is an Enum

    // 5. Bi-directional relationship (One Table to Many Orders)
    // 'mappedBy' points to the 'table' field in the Order entity that owns the relationship.
    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    public Table() {}

    // Constructor adjusted for generated Long ID and Integer number
    public Table(Integer number, TableStatus occupiedStatus) {
        this.number = number;
        this.occupiedStatus = occupiedStatus;
    }

    // Getters and Setters (Updated for Long ID and Integer number)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }

    public TableStatus getOccupiedStatus() { return occupiedStatus; }
    public void setOccupiedStatus(TableStatus occupiedStatus) { this.occupiedStatus = occupiedStatus; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
}