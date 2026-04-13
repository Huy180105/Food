package com.nhom8.foody_order_app.repository.restaurant;

import android.content.Context;
import android.database.Cursor;

import com.nhom8.foody_order_app.model.RestaurantSaved;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHandler;

import java.util.ArrayList;

public class RestaurantSavedDAO {

    DatabaseHandler dbHelper;

    public RestaurantSavedDAO(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    // ThÃªm nhÃ  hÃ ng yÃªu thÃ­ch
    public boolean addRestaurantSaved(RestaurantSaved restaurantSaved) {
        String query = "INSERT INTO tblRestaurantSaved VALUES(" +
                restaurantSaved.getRestaurantId() + ", " +
                restaurantSaved.getUserId() + ")";
        try {
            dbHelper.queryData(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // XÃ³a nhÃ  hÃ ng yÃªu thÃ­ch
    public boolean deleteRestaurantSaved(int restaurantId, int userId) {
        String query = "DELETE FROM tblRestaurantSaved WHERE restaurant_id=" +
                restaurantId + " AND user_id=" + userId;
        try {
            dbHelper.queryData(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Láº¥y danh sÃ¡ch nhÃ  hÃ ng Ä‘Ã£ lÆ°u theo user
    public ArrayList<RestaurantSaved> getRestaurantSavedList(int userId) {
        ArrayList<RestaurantSaved> list = new ArrayList<>();

        String query = "SELECT * FROM tblRestaurantSaved WHERE user_id=" + userId;
        Cursor cursor = dbHelper.getData(query);

        while (cursor.moveToNext()) {
            list.add(new RestaurantSaved(
                    cursor.getInt(0),
                    cursor.getInt(1)
            ));
        }

        return list;
    }

    // Kiá»ƒm tra Ä‘Ã£ lÆ°u chÆ°a (ráº¥t há»¯u Ã­ch cho UI icon tim â¤ï¸)
    public boolean isRestaurantSaved(int restaurantId, int userId) {
        String query = "SELECT * FROM tblRestaurantSaved WHERE restaurant_id=" +
                restaurantId + " AND user_id=" + userId;

        Cursor cursor = dbHelper.getData(query);
        return cursor.getCount() > 0;
    }
}
