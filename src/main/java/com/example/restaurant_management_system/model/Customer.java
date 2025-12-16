package com.example.restaurant_management_system.model;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity // 1. Must be annotated as a JPA entity
@Table(name = "customers") // 2. Map to a specific database table
public class Customer {

    @Id // 3. Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Switched from String to Long ID

    @NotBlank(message = "Name is required") // 4. Backend Validation
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Pattern(
            regexp = "^[a-zA-Z .]+$",
            message = "Name can only contain: letters, spaces and dots!"
    )
    private String name;

    @Email(message = "Must be a valid email format") // 4. Backend Validation
    @NotBlank(message = "Email address is required")
    @Column(unique = true) // Optional: Ensures email is unique in the database
    private String emailaddress;

    // 5. Bi-directional relationship (One Customer to Many Orders)
    // 'mappedBy' points to the 'customer' field in the Order entity that owns the relationship.
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    public Customer() {}

    // Constructor adjusted for generated Long ID
    public Customer(String name, String emailaddress) {
        this.name = name;
        this.emailaddress = emailaddress;
    }

    // Getters and Setters (Updated for Long ID)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }

    public String getEmailaddress() { return emailaddress; }
    public void setEmailaddress(String emailaddress) { this.emailaddress = emailaddress; }
}