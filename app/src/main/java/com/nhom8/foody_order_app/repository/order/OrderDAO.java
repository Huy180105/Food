package com.nhom8.foody_order_app.repository.order;

import android.content.Context;
import android.database.Cursor;

import com.nhom8.foody_order_app.model.Order;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHandler;

import java.util.ArrayList;

public class OrderDAO {

    DatabaseHandler dbHelper;

    public OrderDAO(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    // Äáº¿m sá»‘ Ä‘Æ¡n Ä‘Ã£ giao
    public int countDeliveredOrders() {
        String query = "SELECT COUNT(*) FROM tblOrder WHERE status='Delivered'";
        Cursor cursor = dbHelper.getDataRow(query);

        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return 0;
    }

    // ThÃªm Ä‘Æ¡n hÃ ng
    public void addOrder(Order order) {
        String query = "INSERT INTO tblOrder VALUES(null," +
                order.getUserId() + ",'" +
                order.getAddress() + "','" +
                order.getDateOfOrder() + "'," +
                order.getTotalValue() + ",'" +
                order.getStatus() + "')";
        dbHelper.queryData(query);
    }

    // Cáº­p nháº­t Ä‘Æ¡n hÃ ng
    public void updateOrder(Order order) {
        String query = "UPDATE tblOrder SET " +
                "address='" + order.getAddress() + "'," +
                "date_order='" + order.getDateOfOrder() + "'," +
                "total_value=" + order.getTotalValue() + "," +
                "status='" + order.getStatus() + "' " +
                "WHERE id=" + order.getId() +
                " AND user_id=" + order.getUserId();
        dbHelper.queryData(query);
    }

    // Láº¥y danh sÃ¡ch Ä‘Æ¡n theo user + status
    public ArrayList<Order> getOrdersByUser(int userId, String status) {
        ArrayList<Order> list = new ArrayList<>();

        String query = "SELECT * FROM tblOrder WHERE user_id=" + userId +
                " AND status='" + status + "'";

        // náº¿u lÃ  Delivered thÃ¬ láº¥y thÃªm Canceled
        if (status.equals("Delivered")) {
            query = "SELECT * FROM tblOrder WHERE user_id=" + userId +
                    " AND (status='Delivered' OR status='Canceled')";
        }

        Cursor cursor = dbHelper.getData(query);

        while (cursor.moveToNext()) {
            list.add(new Order(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getDouble(4),
                    cursor.getString(5)
            ));
        }

        return list;
    }
}
