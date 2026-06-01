package com.nhom8.foody_order_app.model;

public class AdminOrderItem {
    private final String foodName;
    private final int size;
    private final double unitPrice;
    private final int quantity;

    private final byte[] foodImage;

    public AdminOrderItem(String foodName, int size, double unitPrice, int quantity, byte[] foodImage) {
        this.foodName = foodName;
        this.size = size;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.foodImage = foodImage;
    }

    public byte[] getFoodImage() {
        return foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getSize() {
        return size;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getLineTotal() {
        return unitPrice * quantity;
    }
}
