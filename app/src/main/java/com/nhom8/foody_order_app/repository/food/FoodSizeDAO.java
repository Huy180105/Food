package com.nhom8.foody_order_app.repository.food;

import android.content.Context;
import android.database.Cursor;

import com.nhom8.foody_order_app.model.FoodSize;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHandler;

import java.util.ArrayList;

public class FoodSizeDAO {

    DatabaseHandler dbHelper;

    public FoodSizeDAO(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    public FoodSize getDefaultSize(int foodId) {
        String query = "SELECT * FROM tblFoodSize WHERE food_id=" + foodId;
        Cursor cursor = dbHelper.getDataRow(query);

        if (cursor != null && cursor.moveToFirst()) {
            return new FoodSize(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getDouble(2)
            );
        }
        return null;
    }

    public ArrayList<FoodSize> getAllSize(int foodId) {
        ArrayList<FoodSize> list = new ArrayList<>();

        String query = "SELECT * FROM tblFoodSize WHERE food_id=" + foodId;
        Cursor cursor = dbHelper.getData(query);

        while (cursor.moveToNext()) {
            list.add(new FoodSize(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getDouble(2)
            ));
        }

        return list;
    }

    public FoodSize getFoodSize(int foodId, int size) {
        String query = "SELECT * FROM tblFoodSize WHERE food_id=" + foodId + " AND size=" + size;
        Cursor cursor = dbHelper.getDataRow(query);

        if (cursor != null && cursor.moveToFirst()) {
            return new FoodSize(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getDouble(2)
            );
        }
        return null;
    }
}

