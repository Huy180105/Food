package com.nhom8.foody_order_app.repository.order;

import android.content.Context;
import android.database.Cursor;

import com.nhom8.foody_order_app.model.FoodSize;
import com.nhom8.foody_order_app.model.OrderDetail;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHandler;

import java.util.ArrayList;

public class OrderDetailDAO {

    DatabaseHandler dbHelper;

    public OrderDetailDAO(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    // Kiá»ƒm tra mÃ³n Ä‘Ã£ tá»“n táº¡i trong order chÆ°a
    public OrderDetail getExistOrderDetail(int orderId, FoodSize foodSize) {
        String query = "SELECT * FROM tblOrderDetail WHERE order_id=" + orderId +
                " AND food_id=" + foodSize.getFoodId() +
                " AND size=" + foodSize.getSize();

        Cursor cursor = dbHelper.getDataRow(query);

        if (cursor != null && cursor.moveToFirst()) {
            return new OrderDetail(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getDouble(3),
                    cursor.getInt(4)
            );
        }
        return null;
    }

    // ThÃªm chi tiáº¿t Ä‘Æ¡n hÃ ng
    public boolean addOrderDetail(OrderDetail od) {
        String query = "INSERT INTO tblOrderDetail VALUES(" +
                od.getOrderId() + ", " +
                od.getFoodId() + ", " +
                od.getSize() + ", " +
                od.getPrice() + ", " +
                od.getQuantity() + ")";
        try {
            dbHelper.queryData(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // XÃ³a mÃ³n khá»i order
    public boolean deleteOrderDetail(int orderId, int foodId) {
        String query = "DELETE FROM tblOrderDetail WHERE food_id=" +
                foodId + " AND order_id=" + orderId;
        try {
            dbHelper.queryData(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Láº¥y order "giá» hÃ ng"
    public Integer getCartOrderId(int userId) {
        String query = "SELECT id FROM tblOrder WHERE status='Craft' AND user_id=" + userId;
        Cursor cursor = dbHelper.getDataRow(query);

        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return null;
    }

    // Láº¥y danh sÃ¡ch mÃ³n trong giá»
    public ArrayList<OrderDetail> getCartDetailList(int orderId) {
        ArrayList<OrderDetail> list = new ArrayList<>();

        String query = "SELECT * FROM tblOrderDetail WHERE order_id=" + orderId;
        Cursor cursor = dbHelper.getData(query);

        while (cursor.moveToNext()) {
            list.add(new OrderDetail(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getDouble(3),
                    cursor.getInt(4)
            ));
        }

        return list;
    }

    // Cáº­p nháº­t sá»‘ lÆ°á»£ng
    public boolean updateQuantity(OrderDetail od) {
        String query = "UPDATE tblOrderDetail SET quantity=" + od.getQuantity() +
                " WHERE order_id=" + od.getOrderId() +
                " AND food_id=" + od.getFoodId() +
                " AND size=" + od.getSize();

        try {
            dbHelper.queryData(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
