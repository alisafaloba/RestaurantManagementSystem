package com.example.restaurant_management_system.model;



import java.util.List;

public class Table {
    private String id;
    private int number;
    private String occupiedStatus; // "Free" or "Occupied"
    private List<Order> orders;

    public Table() {}

    public Table(String id, int number, String occupiedStatus, List<Order> orders) {
        this.id = id;
        this.number = number;
        this.occupiedStatus = occupiedStatus;
        this.orders = orders;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public String getOccupiedStatus() { return occupiedStatus; }
    public void setOccupiedStatus(String occupiedStatus) { this.occupiedStatus = occupiedStatus; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
}
