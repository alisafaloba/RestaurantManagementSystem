package com.example.restaurant_management_system.model;


import java.util.List;

public class Order {
    private String id;
    private String customerId;
    private String tableId;
    private String status; // "Open" or "Closed"
    private List<OrderLine> orderLines;
    private List<OrderAssignment> assignments;

    public Order() {}

    public Order(String id, String customerId, String tableId, String status,
                 List<OrderLine> orderLines, List<OrderAssignment> assignments) {
        this.id = id;
        this.customerId = customerId;
        this.tableId = tableId;
        this.status = status;
        this.orderLines = orderLines;
        this.assignments = assignments;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getTableId() { return tableId; }
    public void setTableId(String tableId) { this.tableId = tableId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<OrderLine> getOrderLines() { return orderLines; }
    public void setOrderLines(List<OrderLine> orderLines) { this.orderLines = orderLines; }

    public List<OrderAssignment> getAssignments() { return assignments; }
    public void setAssignments(List<OrderAssignment> assignments) { this.assignments = assignments; }
}

