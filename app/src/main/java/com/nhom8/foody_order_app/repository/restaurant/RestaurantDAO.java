package com.nhom8.foody_order_app.repository.restaurant;

import android.content.Context;
import android.database.Cursor;

import com.nhom8.foody_order_app.model.Restaurant;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHandler;

import java.util.ArrayList;

public class RestaurantDAO {

    DatabaseHandler dbHelper;

    public RestaurantDAO(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    // Láº¥y thÃ´ng tin nhÃ  hÃ ng theo ID
    public Restaurant getRestaurantById(int restaurantId) {
        String query = "SELECT * FROM tblRestaurant WHERE id=" + restaurantId;
        Cursor cursor = dbHelper.getDataRow(query);

        if (cursor != null && cursor.moveToFirst()) {
            return new Restaurant(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getBlob(4)
            );
        }
        return null;
    }

    // Láº¥y nhÃ  hÃ ng theo tÃªn
    public Restaurant getRestaurantByName(String name) {
        String query = "SELECT * FROM tblRestaurant WHERE name='" + name + "'";
        Cursor cursor = dbHelper.getDataRow(query);

        if (cursor != null && cursor.moveToFirst()) {
            return new Restaurant(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getBlob(4)
            );
        }
        return null;
    }

    // Láº¥y danh sÃ¡ch táº¥t cáº£ nhÃ  hÃ ng
    public ArrayList<Restaurant> getAllRestaurant() {
        ArrayList<Restaurant> list = new ArrayList<>();

        String query = "SELECT * FROM tblRestaurant";
        Cursor cursor = dbHelper.getData(query);

        while (cursor.moveToNext()) {
            list.add(new Restaurant(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getBlob(4)
            ));
        }
        return list;
    }
}
