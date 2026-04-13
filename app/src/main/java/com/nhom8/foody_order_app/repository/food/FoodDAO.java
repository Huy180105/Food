package com.nhom8.foody_order_app.repository.food;

import android.content.Context;
import android.database.Cursor;

import com.nhom8.foody_order_app.model.Food;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHandler;

import java.util.ArrayList;

public class FoodDAO {

    DatabaseHandler dbHelper;

    public FoodDAO(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    public Food getFoodById(int id) {
        String query = "SELECT * FROM tblFood WHERE id=" + id;
        Cursor cursor = dbHelper.getDataRow(query);

        if (cursor != null && !cursor.isAfterLast()) {
            return new Food(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3),
                    cursor.getString(4),
                    cursor.getInt(5)
            );
        }
        return null;
    }

    public ArrayList<Food> getAllFood() {
        ArrayList<Food> list = new ArrayList<>();
        String query = "SELECT * FROM tblFood";
        Cursor cursor = dbHelper.getData(query);

        while (cursor.moveToNext()) {
            list.add(new Food(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3),
                    cursor.getString(4),
                    cursor.getInt(5)
            ));
        }
        return list;
    }

    public ArrayList<Food> searchFood(String keyword, String type) {
        ArrayList<Food> list = new ArrayList<>();

        String query = "SELECT * FROM tblFood WHERE name LIKE '%" + keyword + "%'";
        if (type != null && !type.isEmpty()) {
            query += " AND type='" + type + "'";
        }
        Cursor cursor = dbHelper.getData(query);

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    list.add(new Food(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getBlob(3),
                            cursor.getString(4),
                            cursor.getInt(5)
                    ));
                }
            } catch (android.database.sqlite.SQLiteBlobTooBigException e) {
                // If we still hit this, try to query without the blob and load it lazily or just skip
                // For now, we'll log it and return what we have.
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }

        return list;
    }

    public ArrayList<Food> getFoodByType(String type) {
        ArrayList<Food> list = new ArrayList<>();

        String query = "SELECT * FROM tblFood WHERE type='" + type + "'";
        Cursor cursor = dbHelper.getData(query);

        while (cursor.moveToNext()) {
            list.add(new Food(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3),
                    cursor.getString(4),
                    cursor.getInt(5)
            ));
        }

        return list;
    }

    public ArrayList<Food> getFoodByRestaurant(int restaurantId) {
        ArrayList<Food> list = new ArrayList<>();

        String query = "SELECT * FROM tblFood WHERE restaurant_id=" + restaurantId;
        Cursor cursor = dbHelper.getData(query);

        while (cursor.moveToNext()) {
            list.add(new Food(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3),
                    cursor.getString(4),
                    cursor.getInt(5)
            ));
        }

        return list;
    }
}
