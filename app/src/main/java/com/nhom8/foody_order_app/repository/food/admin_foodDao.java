package com.nhom8.foody_order_app.repository.food;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import androidx.core.content.res.ResourcesCompat;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHandler;

public class admin_foodDao {
    private DatabaseHandler dbHelper;
    private Context context;

    public admin_foodDao(Context context) {
        this.context = context;
        dbHelper = new DatabaseHandler(context);
    }

    public Cursor layTatCa() {
        String sql = "SELECT f.id AS _id, f.name, fs.price, f.description, f.image, f.restaurant_id AS categoryId " +
                "FROM tblFood f LEFT JOIN tblFoodSize fs ON f.id = fs.food_id AND fs.size = 1";
        return dbHelper.getReadableDatabase().rawQuery(sql, null);
    }

    public Cursor timKiem(String ten) {
        String tuKhoa = "%" + ten.trim().toLowerCase() + "%";
        String sql = "SELECT f.id AS _id, f.name, fs.price, f.description, f.image, f.restaurant_id AS categoryId " +
                "FROM tblFood f LEFT JOIN tblFoodSize fs ON f.id = fs.food_id AND fs.size = 1 " +
                "WHERE LOWER(f.name) LIKE ?";
        return dbHelper.getReadableDatabase().rawQuery(sql, new String[]{tuKhoa});
    }

    public long them(String ten, int gia, String moTa, String hinh, int loai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("name", ten);
        v.put("description", moTa);
        v.put("restaurant_id", loai);
        v.put("type", "General");

        byte[] imageBlob = null;
        int resID = context.getResources().getIdentifier(hinh, "drawable", context.getPackageName());
        if (resID != 0) {
            Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), resID, null);
            imageBlob = dbHelper.convertDrawableToByteArray(drawable);
        }
        v.put("image", imageBlob);

        long foodId = db.insert("tblFood", null, v);
        if (foodId != -1) {
            ContentValues vs = new ContentValues();
            vs.put("food_id", foodId);
            vs.put("size", 1);
            vs.put("price", gia);
            db.insert("tblFoodSize", null, vs);
        }
        return foodId;
    }

    public int sua(int id, String ten, int gia, String moTa, String hinh, int loai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("name", ten);
        v.put("description", moTa);
        v.put("restaurant_id", loai);

        int resID = context.getResources().getIdentifier(hinh, "drawable", context.getPackageName());
        if (resID != 0) {
            Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), resID, null);
            byte[] imageBlob = dbHelper.convertDrawableToByteArray(drawable);
            v.put("image", imageBlob);
        }

        int res = db.update("tblFood", v, "id = ?", new String[]{String.valueOf(id)});
        
        ContentValues vs = new ContentValues();
        vs.put("price", gia);
        int sizeUpdate = db.update("tblFoodSize", vs, "food_id = ? AND size = 1", new String[]{String.valueOf(id)});
        if (sizeUpdate == 0) {
            vs.put("food_id", id);
            vs.put("size", 1);
            db.insert("tblFoodSize", null, vs);
        }
        
        return res;
    }

    public int xoa(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("tblFoodSize", "food_id = ?", new String[]{String.valueOf(id)});
        return db.delete("tblFood", "id = ?", new String[]{String.valueOf(id)});
    }

    public Cursor layTheoId(int id) {
        String sql = "SELECT f.id AS _id, f.name, fs.price, f.description, f.image, f.restaurant_id AS categoryId " +
                "FROM tblFood f LEFT JOIN tblFoodSize fs ON f.id = fs.food_id AND fs.size = 1 " +
                "WHERE f.id = ?";
        return dbHelper.getReadableDatabase().rawQuery(sql, new String[]{String.valueOf(id)});
    }

    public Cursor locTheoMien(int categoryId) {
        String sql = "SELECT f.id AS _id, f.name, fs.price, f.description, f.image, f.restaurant_id AS categoryId " +
                "FROM tblFood f LEFT JOIN tblFoodSize fs ON f.id = fs.food_id AND fs.size = 1 " +
                "WHERE f.restaurant_id = ?";
        return dbHelper.getReadableDatabase().rawQuery(sql, new String[]{String.valueOf(categoryId)});
    }
}