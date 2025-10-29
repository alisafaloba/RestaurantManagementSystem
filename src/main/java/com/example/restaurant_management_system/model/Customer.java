package com.example.restaurant_management_system.model;



import java.util.List;

public class Customer {
    private String id;
    private String name;
    private List<Order> orders;
    private String emailaddress;

    public Customer() {}

    public Customer(String id, String name, List<Order> orders,String emailaddress) {
        this.id = id;
        this.name = name;
        this.orders = orders;
        this.emailaddress = emailaddress;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
}
