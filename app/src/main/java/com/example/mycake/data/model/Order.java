package com.example.mycake.data.model;

public class Order {
    private int orderId;
    private int userId;
    private double totalPrice;
    private String date;
    private String status;

    public Order(int orderId, int userId, double totalPrice, String date, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.date = date;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
