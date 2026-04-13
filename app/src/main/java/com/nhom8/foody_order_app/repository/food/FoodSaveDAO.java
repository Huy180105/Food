package com.nhom8.foody_order_app.repository.food;

import android.content.Context;
import android.database.Cursor;

import com.nhom8.foody_order_app.model.FoodSaved;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHandler;

import java.util.ArrayList;

public class FoodSaveDAO {

    DatabaseHandler dbHelper;

    public FoodSaveDAO(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    public boolean addFoodSaved(FoodSaved fs) {
        String query = "INSERT INTO tblFoodSaved VALUES(" +
                fs.getFoodId() + ", " +
                fs.getSize() + ", " +
                fs.getUserId() + ")";
        try {
            dbHelper.queryData(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteFoodSaved(int foodId, int size) {
        String query = "DELETE FROM tblFoodSaved WHERE food_id=" +
                foodId + " AND size=" + size;
        dbHelper.queryData(query);
    }

    public ArrayList<FoodSaved> getFoodSavedList(int userId) {
        ArrayList<FoodSaved> list = new ArrayList<>();

        String query = "SELECT * FROM tblFoodSaved WHERE user_id=" + userId;
        Cursor cursor = dbHelper.getData(query);

        while (cursor.moveToNext()) {
            list.add(new FoodSaved(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2)
            ));
        }

        return list;
    }
}
