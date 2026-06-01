package com.nhom8.foody_order_app.model;

public class AdminOrderSummary {
    private final int id;
    private final int userId;
    private final String customerName;
    private final String phone;
    private final String address;
    private final String dateOfOrder;
    private final double totalValue;
    private final String status;

    public AdminOrderSummary(int id, int userId, String customerName, String phone,
                             String address, String dateOfOrder, double totalValue, String status) {
        this.id = id;
        this.userId = userId;
        this.customerName = customerName;
        this.phone = phone;
        this.address = address;
        this.dateOfOrder = dateOfOrder;
        this.totalValue = totalValue;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getDateOfOrder() {
        return dateOfOrder;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public String getStatus() {
        return status;
    }
}
