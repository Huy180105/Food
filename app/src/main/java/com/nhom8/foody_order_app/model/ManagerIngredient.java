package com.nhom8.foody_order_app.model;

public class ManagerIngredient {
    private final int id;
    private final String name;
    private final double quantity;
    private final String unit;
    private final String image;

    public ManagerIngredient(int id, String name, double quantity, String unit, String image) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getImage() {
        return image;
    }
}
