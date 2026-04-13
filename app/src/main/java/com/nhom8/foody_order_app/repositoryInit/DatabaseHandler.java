package com.nhom8.foody_order_app.repositoryInit;

import static java.lang.String.format;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.model.Food;
import com.nhom8.foody_order_app.model.FoodSaved;
import com.nhom8.foody_order_app.model.FoodSize;
import com.nhom8.foody_order_app.model.Notify;
import com.nhom8.foody_order_app.model.NotifyToUser;
import com.nhom8.foody_order_app.model.Order;
import com.nhom8.foody_order_app.model.OrderDetail;
import com.nhom8.foody_order_app.model.Restaurant;
import com.nhom8.foody_order_app.model.RestaurantSaved;
import com.nhom8.foody_order_app.model.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "foody_order_app_version_6.sqlite";
    public static final Integer DATABASE_VERSION = 2;
    public static final CursorFactory DATABASE_FACTORY = null;
    private final Context context;

    private List<User> userList;
    private List<Restaurant> restaurantList;
    private List<RestaurantSaved> restaurantSavedList;
    private List<Food> foodList;
    private List<FoodSize> foodSizeList;
    private List<Notify> notifyList;
    private List<NotifyToUser> notifyToUsers;
    private List<Order> orderList;
    private List<OrderDetail> orderDetailList;
    private List<FoodSaved> foodSavedList;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, DATABASE_FACTORY, DATABASE_VERSION);
        this.context = context;
    }

    public void queryData(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public Cursor getData(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    public Cursor getDataRow(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        return cursor;
    }

    public byte[] convertDrawableToByteArray(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                bitmap = bitmapDrawable.getBitmap();
            } else {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            }
        } else {
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }

        // Resize bitmap if it's too large
        int maxWidth = 500;
        int maxHeight = 500;
        if (bitmap.getWidth() > maxWidth || bitmap.getHeight() > maxHeight) {
            float aspectRatio = (float) bitmap.getWidth() / (float) bitmap.getHeight();
            int width = maxWidth;
            int height = Math.round(maxWidth / aspectRatio);
            if (height > maxHeight) {
                height = maxHeight;
                width = Math.round(maxHeight * aspectRatio);
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out);
        return out.toByteArray();
    }

    public static Bitmap convertByteArrayToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private void SampleData() {
        // region User
        userList = new ArrayList<>();
        userList.add(new User(1, "Hoàng Anh Tiến", "Male", "12-04-2003", "0828007853", "hoangtien2k3", "123456"));
        userList.add(new User(2, "Nguyễn Chí Hải Anh", "Male", "17-04-2003", "0947679750", "nguyenchihaianh", "123456"));
        userList.add(new User(3, "Vũ Mạnh Chiến", "Male", "25-06-2003", "0388891635", "vumanhchien", "123456"));

        // region Restaurant
        restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant(1, "Đặc sản Miền Bắc", "Hà Nội", "0828007853", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.img_mien_bac, null))));
        restaurantList.add(new Restaurant(2, "Đặc sản Miền Trung", "Huế - Đà Nẵng", "0885438847", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.img_mien_trung, null))));
        restaurantList.add(new Restaurant(3, "Đặc sản Miền Nam", "TP. Hồ Chí Minh", "0559996574", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.img_mien_nam, null))));

        // region Restaurant saved
        restaurantSavedList = new ArrayList<>();
        restaurantSavedList.add(new RestaurantSaved(1, 1));
        restaurantSavedList.add(new RestaurantSaved(2, 1));
        restaurantSavedList.add(new RestaurantSaved(3, 1));

        // region Food
        foodList = new ArrayList<>();
        // region Miền Bắc
        foodList.add(new Food(1, "Phở bò", "Miền Bắc",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mb_pho_bo, null)),
                "Phở bò truyền thống Hà Nội với nước dùng trong, bánh phở dẻo và thịt bò mềm.", 1));
        foodList.add(new Food(2, "Bún chả", "Miền Bắc",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mb_bun_cha, null)),
                "Bún chả Hà Nội - sự kết hợp tinh tế giữa thịt nướng và nước mắm chua ngọt.", 1));
        foodList.add(new Food(3, "Bánh cuốn", "Miền Bắc",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mb_banh_cuon, null)),
                "Bánh cuốn Thanh Trì mỏng, mướt, ăn kèm với chả quế và hành phi thơm nức.", 1));
        foodList.add(new Food(4, "Chả cá", "Miền Bắc",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mb_cha_ca, null)),
                "Chả cá Lã Vọng - món ăn sang trọng của người Hà Nội với cá lăng nướng vàng.", 1));
//        foodList.add(new Food(5, "Chả mực", "Miền Bắc",
//                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mb_cha_muc, null)),
//                "Chả mực Hạ Long giòn sần sật, thơm mùi mực tươi nguyên chất.", 1));
//        foodList.add(new Food(6, "Bánh đa cua", "Miền Bắc",
//                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mb_banh_da_cua, null)),
//                "Bánh đa cua Hải Phòng đậm đà vị gạch cua đồng và màu đỏ của bánh đa.", 1));

        // region Miền Trung
        foodList.add(new Food(7, "Bún bò Huế", "Miền Trung",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mt_bun_bo, null)),
                "Bún bò Huế cay nồng, đậm đà vị mắm ruốc đặc trưng của cố đô.", 2));
        foodList.add(new Food(8, "Mì Quảng", "Miền Trung",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mt_mi_quang, null)),
                "Mì Quảng tôm thịt với nước dùng cô đặc, ăn kèm bánh tráng nướng.", 2));
        foodList.add(new Food(9, "Bánh xèo", "Miền Trung",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mt_banh_xeo, null)),
                "Bánh xèo miền Trung giòn tan, cuốn cùng rau sống và nước chấm chua ngọt.", 2));
        foodList.add(new Food(10, "Nem lụi", "Miền Trung",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mt_nem_lui, null)),
                "Nem lụi nướng thơm nức mũi, ăn kèm với nước lèo gan đậu phộng béo ngậy.", 2));
//        foodList.add(new Food(11, "Nem chua", "Miền Trung",
//                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mt_nem_chua, null)),
//                "Nem chua Thanh Hóa vị chua thanh, giòn sần sật của bì heo.", 2));

        // region Miền Nam
        foodList.add(new Food(12, "Cơm tấm", "Miền Nam",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mn_com_tam, null)),
                "Cơm tấm Sài Gòn sườn bì chả, món ăn sáng quen thuộc và đầy đủ năng lượng.", 3));
        foodList.add(new Food(13, "Hủ tiếu Nam Vang", "Miền Nam",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mn_hu_tieu, null)),
                "Hủ tiếu Nam Vang đặc trưng với nước dùng thanh ngọt từ xương và tôm mực.", 3));
        foodList.add(new Food(14, "Bánh mì", "Miền Nam",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mn_banh_mi, null)),
                "Bánh mì Sài Gòn giòn rụm với nhân thịt nguội, pate và đồ chua.", 3));
        foodList.add(new Food(15, "Gỏi cuốn", "Miền Nam",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mn_goi_cuon, null)),
                "Gỏi cuốn tôm thịt thanh mát, món khai vị tuyệt vời cho ngày hè.", 3));

        // region foodSize
        foodSizeList = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= 15; i++) {
            foodSizeList.add(new FoodSize(i, 1, (random.nextInt(10) + 20) * 1000d));
            foodSizeList.add(new FoodSize(i, 2, (random.nextInt(10) + 30) * 1000d));
            foodSizeList.add(new FoodSize(i, 3, (random.nextInt(10) + 40) * 1000d));
        }

        // region foodSaved
        foodSavedList = new ArrayList<>();
        foodSavedList.add(new FoodSaved(1, 1, 1));
        foodSavedList.add(new FoodSaved(7, 1, 1));
        foodSavedList.add(new FoodSaved(12, 1, 1));

        // region notify
        notifyList = new ArrayList<>();
        notifyList.add(new Notify(1, "Chào bạn mới!",
                "Chào mừng bạn đến với ứng dụng Đặc Sản 3 Miền!", "02/09/2023"));

        // region notify to user
        notifyToUsers = new ArrayList<>();
        notifyToUsers.add(new NotifyToUser(1, 1));

        // region Order
        orderList = new ArrayList<>();
        orderList.add(new Order(1, 1, "Hà Nội", "04/09/2023", 0d, "Delivered"));

        // region Order detail
        orderDetailList = new ArrayList<>();
        orderDetailList.add(new OrderDetail(1, 1, 1, 30000d, 2));
        orderDetailList.add(new OrderDetail(1, 7, 1, 40000d, 1));
    }

    private void addSampleData(SQLiteDatabase db) {
        SampleData();

        // Add user
        for (User user : userList) {
            db.execSQL(format("INSERT INTO tblUser VALUES(null, '%s','%s', '%s', '%s', '%s', '%s')",
                    user.getName(), user.getGender(), user.getDateOfBirth(), user.getPhone(), user.getUsername(), user.getPassword()));
        }

        // Add restaurant
        for (Restaurant restaurant : restaurantList) {
            String sql = "INSERT INTO tblRestaurant VALUES(null, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, restaurant.getName());
            statement.bindString(2, restaurant.getAddress());
            statement.bindString(3, restaurant.getPhone());
            statement.bindBlob(4, restaurant.getImage());
            statement.executeInsert();
        }

        // Add restaurant saved
        for (RestaurantSaved restaurantSaved : restaurantSavedList) {
            db.execSQL("INSERT INTO tblRestaurantSaved VALUES(" + restaurantSaved.getRestaurantId() + ", " + restaurantSaved.getUserId() + ")");
        }

        // Add food
        for (Food food : foodList) {
            String sql = "INSERT INTO tblFood VALUES (null, ?, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, food.getName());
            statement.bindString(2, food.getType());
            statement.bindBlob(3, food.getImage());
            statement.bindString(4, food.getDescription());
            statement.bindLong(5, food.getRestaurantId());
            statement.executeInsert();
        }

        // Add food size
        for (FoodSize foodSize : foodSizeList) {
            String sql = "INSERT INTO tblFoodSize VALUES(?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindLong(1, foodSize.getFoodId());
            statement.bindLong(2, foodSize.getSize());
            statement.bindDouble(3, foodSize.getPrice());
            statement.executeInsert();
        }

        // Add food saved
        for (FoodSaved foodSaved : foodSavedList) {
            String sql = "INSERT INTO tblFoodSaved VALUES(?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindLong(1, foodSaved.getFoodId());
            statement.bindLong(2, foodSaved.getSize());
            statement.bindLong(3, foodSaved.getUserId());
            statement.executeInsert();
        }

        // Add notify
        for (Notify notify : notifyList) {
            db.execSQL(format("INSERT INTO tblNotify VALUES(null, '%s', '%s', '%s')",
                    notify.getTitle(), notify.getContent(), notify.getDateMake()));
        }

        // Add notify to user
        for (NotifyToUser notifyToUser : notifyToUsers) {
            db.execSQL("INSERT INTO tblNotifyToUser VALUES('"
                    + notifyToUser.getNotifyId() + "', '"
                    + notifyToUser.getUserId() + "')");
        }

        // Add order
        for (Order order : orderList) {
            String sql = "INSERT INTO tblOrder VALUES(null, ?, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindLong(1, order.getUserId());
            statement.bindString(2, order.getAddress());
            statement.bindString(3, order.getDateOfOrder());
            statement.bindDouble(4, order.getTotalValue());
            statement.bindString(5, order.getStatus());
            statement.executeInsert();
        }

        // Add order detail
        for (OrderDetail orderDetail : orderDetailList) {
            db.execSQL("INSERT INTO tblOrderDetail VALUES(" + orderDetail.getOrderId() + ", " +
                    orderDetail.getFoodId() + ", " +
                    orderDetail.getSize() + ", " +
                    orderDetail.getPrice() + ", " +
                    orderDetail.getQuantity() + ")");
        }

        // Update order price
        String queryGetTotal;
        for (Order order : orderList) {
            queryGetTotal = "SELECT SUM(price * quantity) FROM tblOrderDetail WHERE order_id=" + order.getId();
            @SuppressLint("Recycle")
            Cursor cursor = db.rawQuery(queryGetTotal, null);
            cursor.moveToFirst();

            String sql = "UPDATE tblOrder SET total_value=" + cursor.getDouble(0) + " WHERE id=" + order.getId();
            db.execSQL(sql);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryUser = "CREATE TABLE IF NOT EXISTS tblUser(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name nvarchar(200), " +
                "gender nvarchar(20), " +
                "date_of_birth nvarchar(20), " +
                "phone nvarchar(20), " +
                "username nvarchar(200), " +
                "password nvarchar(200))";
        sqLiteDatabase.execSQL(queryUser);

        String queryRestaurant = "CREATE TABLE IF NOT EXISTS tblRestaurant(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name nvarchar(200), " +
                "address nvarchar(200), " +
                "phone nvarchar(20), " +
                "image blob)";
        sqLiteDatabase.execSQL(queryRestaurant);

        String queryRestaurantSaved = "CREATE TABLE IF NOT EXISTS tblRestaurantSaved(" +
                "restaurant_id INTEGER, " +
                "user_id INTEGER, " +
                "PRIMARY KEY(restaurant_id, user_id), " +
                "FOREIGN KEY(restaurant_id) REFERENCES tblRestaurant(id), " +
                "FOREIGN KEY(user_id) REFERENCES tblUser(id))";
        sqLiteDatabase.execSQL(queryRestaurantSaved);

        String queryFood = "CREATE TABLE IF NOT EXISTS tblFood(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name nvarchar(200), " +
                "type nvarchar(200), " +
                "image blob, " +
                "description nvarchar(200), " +
                "restaurant_id INTEGER, " +
                "FOREIGN KEY(restaurant_id) REFERENCES tblRestaurant(id))";
        sqLiteDatabase.execSQL(queryFood);

        String queryFoodSize = "CREATE TABLE IF NOT EXISTS tblFoodSize(" +
                "food_id INTEGER, " +
                "size INTEGER, " +
                "price Double, " +
                "PRIMARY KEY(food_id, size), " +
                "FOREIGN KEY(food_id) REFERENCES tblFood(id))";
        sqLiteDatabase.execSQL(queryFoodSize);

        String queryFoodSaved = "CREATE TABLE IF NOT EXISTS tblFoodSaved(" +
                "food_id INTEGER, " +
                "size INTEGER, " +
                "user_id INTEGER, " +
                "PRIMARY KEY(food_id, size, user_id), " +
                "FOREIGN KEY(food_id) REFERENCES tblFood(id), " +
                "FOREIGN KEY(user_id) REFERENCES tblUser(id))";
        sqLiteDatabase.execSQL(queryFoodSaved);

        String queryNotify = "CREATE TABLE IF NOT EXISTS tblNotify(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title nvarchar(200), " +
                "content nvarchar(200), " +
                "date_make nvarchar(20))";
        sqLiteDatabase.execSQL(queryNotify);

        String queryNotifyToUser = "CREATE TABLE IF NOT EXISTS tblNotifyToUser(" +
                "notify_id INTEGER, " +
                "user_id INTEGER, " +
                "PRIMARY KEY(notify_id, user_id), " +
                "FOREIGN KEY(notify_id) REFERENCES tblNotify(id), " +
                "FOREIGN KEY(user_id) REFERENCES tblUser(id))";
        sqLiteDatabase.execSQL(queryNotifyToUser);

        String queryOrder = "CREATE TABLE IF NOT EXISTS tblOrder(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "address nvarchar(200), " +
                "date_of_order nvarchar(20), " +
                "total_value Double, " +
                "status nvarchar(20), " +
                "FOREIGN KEY(user_id) REFERENCES tblUser(id))";
        sqLiteDatabase.execSQL(queryOrder);

        String queryOrderDetail = "CREATE TABLE IF NOT EXISTS tblOrderDetail(" +
                "order_id INTEGER, " +
                "food_id INTEGER, " +
                "size INTEGER, " +
                "price Double, " +
                "quantity INTEGER, " +
                "PRIMARY KEY(order_id, food_id, size), " +
                "FOREIGN KEY(order_id) REFERENCES tblOrder(id), " +
                "FOREIGN KEY(food_id) REFERENCES tblFood(id))";
        sqLiteDatabase.execSQL(queryOrderDetail);

        addSampleData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblOrderDetail");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblOrder");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblNotifyToUser");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblNotify");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblFoodSaved");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblFoodSize");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblFood");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblRestaurantSaved");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblRestaurant");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblUser");
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
