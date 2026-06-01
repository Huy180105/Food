package com.nhom8.foody_order_app.repositoryInit;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DatabaseHandler {
    private DatabaseHelper dbHelper;

    public DatabaseHandler(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public SQLiteDatabase getReadableDatabase() {
        return dbHelper.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDatabase() {
        return dbHelper.getWritableDatabase();
    }

    public void queryData(String sql) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor getData(String sql) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public Cursor getDataRow(String sql) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            return cursor;
        }
        return cursor;
    }

    public static Bitmap convertByteArrayToBitmap(byte[] bytes) {
        if (bytes == null) return null;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public byte[] convertDrawableToByteArray(android.graphics.drawable.Drawable drawable) {
        if (drawable == null) return null;
        Bitmap bitmap = ((android.graphics.drawable.BitmapDrawable) drawable).getBitmap();
        java.io.ByteArrayOutputStream stream = new java.io.ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
