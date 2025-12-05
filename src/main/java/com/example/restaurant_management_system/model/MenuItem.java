package com.example.restaurant_management_system.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity // 1. Annotated as a JPA entity
@Table(name = "menu_items")
public class MenuItem {

    @Id // 2. Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Switched from String to Long ID

    @NotBlank(message = "Name is required") // 3. Backend Validation
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Price is required") // 3. Backend Validation
    @Positive(message = "Price must be a positive value")
    private Double price; // Changed 'double' to 'Double' for @NotNull validation robustness

    // 4. Persistence for the list of allergens (using ElementCollection for simple lists)
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "menu_item_allergens", joinColumns = @JoinColumn(name = "menu_item_id"))
    @Column(name = "allergen")
    private List<String> allergens;

    public MenuItem() {}

    // Constructor adjusted for generated Long ID and Double price
    public MenuItem(String name, Double price, List<String> allergens) {
        this.name = name;
        this.price = price;
        this.allergens = allergens;
    }

    // Getters and Setters (Updated for Long ID and Double price)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public List<String> getAllergens() { return allergens; }
    public void setAllergens(List<String> allergens) { this.allergens = allergens; }
}