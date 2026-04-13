package com.nhom8.foody_order_app.activity;

public interface CategoryActivityImpl {
    void initializeUI();
    void setupSearchBar();
    void loadRestaurantInformation();
    void loadFoodData(String nameFoodOfThisRestaurant);
}
