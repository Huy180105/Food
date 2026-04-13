package com.nhom8.foody_order_app.repositoryInit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "DacSan3Mien.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE User (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "password TEXT," +
                "fullName TEXT," +
                "birthDate TEXT," +
                "phone TEXT," +
                "address TEXT," +
                "gender TEXT," +
                "role TEXT)");

        db.execSQL("CREATE TABLE Category (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT)");

        db.execSQL("CREATE TABLE Product (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "price INTEGER," +
                "description TEXT," +
                "image TEXT," +
                "categoryId INTEGER)");

        db.execSQL("CREATE TABLE Orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER," +
                "customerName TEXT," +
                "phone TEXT," +
                "address TEXT," +
                "totalPrice INTEGER," +
                "date TEXT)");

        db.execSQL("CREATE TABLE OrderDetail (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "orderId INTEGER," +
                "productId INTEGER," +
                "quantity INTEGER," +
                "price INTEGER)");

        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Category");
        db.execSQL("DROP TABLE IF EXISTS Product");
        db.execSQL("DROP TABLE IF EXISTS Orders");
        db.execSQL("DROP TABLE IF EXISTS OrderDetail");
        onCreate(db);
    }

    // ================== DATA MẪU ==================
    private void insertSampleData(SQLiteDatabase db) {

        // CATEGORY
        db.execSQL("INSERT INTO Category VALUES (null,'Miền Bắc')");
        db.execSQL("INSERT INTO Category VALUES (null,'Miền Trung')");
        db.execSQL("INSERT INTO Category VALUES (null,'Miền Nam')");

        // ================== MIỀN BẮC ==================
        db.execSQL("INSERT INTO Product VALUES (null,'Phở bò',30000,'Phở bò truyền thống Hà Nội.','mb_pho_bo',1)");
        db.execSQL("INSERT INTO Product VALUES (null,'Bún chả',35000,'Bún chả Hà Nội.','mb_bun_cha',1)");
        db.execSQL("INSERT INTO Product VALUES (null,'Bánh cuốn',25000,'Bánh cuốn mềm mịn.','mb_banh_cuon',1)");
        db.execSQL("INSERT INTO Product VALUES (null,'Chả cá',50000,'Chả cá thơm ngon.','mb_cha_ca',1)");

        // ================== MIỀN TRUNG ==================
        db.execSQL("INSERT INTO Product VALUES (null,'Bún bò Huế',40000,'Bún bò đậm đà.','mt_bun_bo',2)");
        db.execSQL("INSERT INTO Product VALUES (null,'Mì Quảng',35000,'Mì Quảng đặc sản.','mt_mi_quang',2)");
        db.execSQL("INSERT INTO Product VALUES (null,'Bánh xèo',30000,'Bánh xèo giòn.','mt_banh_xeo',2)");
        db.execSQL("INSERT INTO Product VALUES (null,'Nem lụi',30000,'Nem lụi nướng.','mt_nem_lui',2)");

        // ================== MIỀN NAM ==================
        db.execSQL("INSERT INTO Product VALUES (null,'Cơm tấm',35000,'Cơm tấm Sài Gòn.','mn_com_tam',3)");
        db.execSQL("INSERT INTO Product VALUES (null,'Hủ tiếu',30000,'Hủ tiếu thanh ngọt.','mn_hu_tieu',3)");
        db.execSQL("INSERT INTO Product VALUES (null,'Bánh mì',20000,'Bánh mì Việt Nam.','mn_banh_mi',3)");
        db.execSQL("INSERT INTO Product VALUES (null,'Gỏi cuốn',25000,'Gỏi cuốn thanh mát.','mn_goi_cuon',3)");
    }

    // ================== USER ==================
    public void addUser(String username, String password, String fullName,
                        String birthDate, String phone, String address,
                        String gender, String role) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);
        values.put("fullName", fullName);
        values.put("birthDate", birthDate);
        values.put("phone", phone);
        values.put("address", address);
        values.put("gender", gender);
        values.put("role", role);

        db.insert("User", null, values);
    }

    public boolean checkLogin(String username, String password, String role) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM User WHERE username=? AND password=? AND role=?",
                new String[]{username, password, role});
        return cursor.getCount() > 0;
    }

    // ================== PRODUCT ==================
    public Cursor getAllProduct() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Product", null);
    }

    public Cursor searchProduct(String keyword) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM Product WHERE name LIKE ?",
                new String[]{"%" + keyword + "%"});
    }

    // ================== ORDER ==================
    public long addOrder(int userId, String name, String phone, String address, int total, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("userId", userId);
        values.put("customerName", name);
        values.put("phone", phone);
        values.put("address", address);
        values.put("totalPrice", total);
        values.put("date", date);

        return db.insert("Orders", null, values);
    }

    public Cursor getAllOrder() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Orders ORDER BY id DESC", null);
    }

    public Cursor searchOrder(String keyword) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM Orders WHERE customerName LIKE ?",
                new String[]{"%" + keyword + "%"});
    }

    // ================== ORDER DETAIL ==================
    public void addOrderDetail(long orderId, int productId, int quantity, int price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("orderId", orderId);
        values.put("productId", productId);
        values.put("quantity", quantity);
        values.put("price", price);

        db.insert("OrderDetail", null, values);
    }

    public Cursor getOrderDetailWithProduct(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT p.name, od.quantity, od.price " +
                "FROM OrderDetail od " +
                "JOIN Product p ON od.productId = p.id " +
                "WHERE od.orderId=?";
        return db.rawQuery(query, new String[]{String.valueOf(orderId)});
    }

    public Cursor getOrderDetail(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM OrderDetail WHERE orderId=?",
                new String[]{String.valueOf(orderId)});
    }

    // ================== REPORT ==================
    public int getTotalRevenue() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(totalPrice) FROM Orders", null);
        if (cursor.moveToFirst()) return cursor.getInt(0);
        return 0;
    }

    public int getTotalOrder() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Orders", null);
        if (cursor.moveToFirst()) return cursor.getInt(0);
        return 0;
    }

    public Cursor getAllCustomer() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM User WHERE role='user'", null);
    }
}