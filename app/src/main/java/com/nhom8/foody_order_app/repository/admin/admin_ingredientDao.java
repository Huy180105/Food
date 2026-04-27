package com.nhom8.foody_order_app.repository.admin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHelper;

public class admin_ingredientDao {
    private DatabaseHelper dbHelper;

    public admin_ingredientDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public Cursor getAll() {
        String sql = "SELECT id AS _id, name, quantity, unit FROM Ingredient";
        return dbHelper.getReadableDatabase().rawQuery(sql, null);
    }

    public Cursor search(String name) {
        String query = "%" + name.trim().toLowerCase() + "%";
        String sql = "SELECT id AS _id, name, quantity, unit FROM Ingredient WHERE LOWER(name) LIKE ?";
        return dbHelper.getReadableDatabase().rawQuery(sql, new String[]{query});
    }

    public long add(String name, double quantity, String unit) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("quantity", quantity);
        values.put("unit", unit);
        return db.insert("Ingredient", null, values);
    }

    public int update(int id, String name, double quantity, String unit) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("quantity", quantity);
        values.put("unit", unit);
        return db.update("Ingredient", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("Ingredient", "id = ?", new String[]{String.valueOf(id)});
    }

    public Cursor getById(int id) {
        String sql = "SELECT id AS _id, name, quantity, unit FROM Ingredient WHERE id = ?";
        return dbHelper.getReadableDatabase().rawQuery(sql, new String[]{String.valueOf(id)});
    }
}
