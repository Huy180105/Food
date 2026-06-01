package com.nhom8.foody_order_app.repository.admin;

import android.content.Context;
import android.database.Cursor;

import com.nhom8.foody_order_app.model.AdminOrderItem;
import com.nhom8.foody_order_app.model.AdminOrderSummary;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHelper;

import java.util.ArrayList;

public class AdminInvoiceDAO {
    private final DatabaseHelper dbHelper;
    private final Context context;
    public AdminInvoiceDAO(Context context) {

        dbHelper = new DatabaseHelper(context);
        this.context = context;
    }

    public ArrayList<AdminOrderSummary> getOrders(String keyword) {
        ArrayList<AdminOrderSummary> list = new ArrayList<>();
        String safeKeyword = keyword == null ? "" : keyword.trim().replace("'", "''");

        String query = "SELECT o.id, o.userId, IFNULL(u.name, 'Khach hang'), " +
                "IFNULL(u.phone, ''), o.address, o.date, o.totalPrice, o.status " +
                "FROM Orders o " +
                "LEFT JOIN User u ON u.id = o.userId " +
                "WHERE o.status <> 'Craft' " +
                "AND (" +
                "CAST(o.id AS TEXT) LIKE '%" + safeKeyword + "%' OR " +
                "u.name LIKE '%" + safeKeyword + "%' OR " +
                "u.phone LIKE '%" + safeKeyword + "%' OR " +
                "o.address LIKE '%" + safeKeyword + "%'" +
                ") " +
                "ORDER BY o.id DESC";

        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
        while (cursor.moveToNext()) {
            list.add(new AdminOrderSummary(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getDouble(6),
                    cursor.getString(7)
            ));
        }
        cursor.close();
        return list;
    }

    public AdminOrderSummary getOrderById(int orderId) {
        String query = "SELECT o.id, o.userId, IFNULL(u.name, 'Khach hang'), " +
                "IFNULL(u.phone, ''), o.address, o.date, o.totalPrice, o.status " +
                "FROM Orders o " +
                "LEFT JOIN User u ON u.id = o.userId " +
                "WHERE o.id = " + orderId;

        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            AdminOrderSummary order = new AdminOrderSummary(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getDouble(6),
                    cursor.getString(7)
            );
            cursor.close();
            return order;
        }
        return null;
    }

    public ArrayList<AdminOrderItem> getOrderItems(int orderId) {
        ArrayList<AdminOrderItem> list = new ArrayList<>();
        String query = "SELECT f.name, od.size, od.price, od.quantity, f.image " +
                "FROM OrderDetail od " +
                "INNER JOIN Product f ON f.id = od.productId " +
                "WHERE od.orderId = " + orderId +
                " ORDER BY f.name";

        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
        while (cursor.moveToNext()) {
            String imageName = cursor.getString(4);

            android.graphics.Bitmap bitmap = DatabaseHelper.getBitmapFromDrawable(context, imageName);

            byte[] imageBytes = null;
            if (bitmap != null) {
                java.io.ByteArrayOutputStream stream = new java.io.ByteArrayOutputStream();
                bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, stream);
                imageBytes = stream.toByteArray();
            }

            list.add(new AdminOrderItem(
                    cursor.getString(0),
                    cursor.getInt(1),
                    cursor.getDouble(2),
                    cursor.getInt(3),
                    imageBytes
            ));
        }
        cursor.close();
        return list;
    }

    public int getTotalOrderCount() {
        String query = "SELECT COUNT(*) FROM Orders WHERE status <> 'Craft'";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
        int count = (cursor != null && cursor.moveToFirst()) ? cursor.getInt(0) : 0;
        if (cursor != null) {
            cursor.close();
        }
        return count;
    }

    public double getTotalRevenue() {
        String query = "SELECT IFNULL(SUM(totalPrice), 0) " +
                "FROM Orders WHERE status <> 'Craft' AND status <> 'Canceled'";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
        double total = (cursor != null && cursor.moveToFirst()) ? cursor.getDouble(0) : 0d;
        if (cursor != null) {
            cursor.close();
        }
        return total;
    }
}
