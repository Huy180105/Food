package com.nhom8.foody_order_app.activity.ActivityImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import database.DatabaseHelper; // Nhớ import đúng file của nhóm trưởng

public class admin_foodDao {
    private SQLiteDatabase db;

    public admin_foodDao(Context context) {

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // 1. Hàm lấy tất cả món ăn (Dùng để hiện lên ListView ban đầu)
    public Cursor layTatCa() {
        // SQLite yêu cầu bảng phải có cột _id để CursorAdapter hoạt động
        // Ở đây ta đổi tên cột 'id' thành '_id' bằng lệnh AS
        return db.rawQuery("SELECT id AS _id, name, price, description, image, categoryId FROM Product", null);
    }

    // 2. Hàm tìm kiếm món ăn theo tên
    public Cursor timKiem(String ten) {
        // Sử dụng hàm LOWER để đồng nhất tìm kiếm không phân biệt hoa thường
        // trim() để loại bỏ khoảng trắng thừa ở đầu/cuối chuỗi
        String tuKhoa = "%" + ten.trim().toLowerCase() + "%";

        String sql = "SELECT id AS _id, name, price, description, image, categoryId " +
                "FROM Product WHERE LOWER(name) LIKE ?";

        return db.rawQuery(sql, new String[]{tuKhoa});
    }

    // 3. Hàm thêm món ăn mới
    public long them(String ten, int gia, String moTa, String hinh, int loai) {
        ContentValues v = new ContentValues();
        v.put("name", ten);
        v.put("price", gia);
        v.put("description", moTa);
        v.put("image", hinh);
        v.put("categoryId", loai);
        return db.insert("Product", null, v);
    }

    // 4. Hàm sửa món ăn
    public int sua(int id, String ten, int gia, String moTa, String hinh, int loai) {
        ContentValues v = new ContentValues();
        v.put("name", ten);
        v.put("price", gia);
        v.put("description", moTa);
        v.put("image", hinh);
        v.put("categoryId", loai);
        return db.update("Product", v, "id = ?", new String[]{String.valueOf(id)});
    }

    // 5. Hàm xóa món ăn
    public int xoa(int id) {
        return db.delete("Product", "id = ?", new String[]{String.valueOf(id)});
    }
    //xem
    public Cursor layTheoId(int id) {
        String sql = "SELECT id AS _id, name, price, description, image, categoryId FROM Product WHERE id = ?";
        return db.rawQuery(sql, new String[]{String.valueOf(id)});
    }
    public Cursor locTheoMien(int categoryId) {
        String sql = "SELECT id AS _id, name, price, description, image, categoryId " +
                "FROM Product WHERE categoryId = ?";
        return db.rawQuery(sql, new String[]{String.valueOf(categoryId)});
    }
}