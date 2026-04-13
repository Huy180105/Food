package com.nhom8.foody_order_app.repositoryInit;

import static java.lang.String.format;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.model.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "foody_order_app_version_5.sqlite";
    private static final Integer DATABASE_VERSION = 1;
    private static final SQLiteDatabase.CursorFactory DATABASE_FACTORY = null;
    private final Context context;

    // List Sample DataSS
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
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        return c;
    }

    // Convert Image
    public byte[] convertDrawableToByteArray(Drawable drawable) {
        // Convert khi Ä‘Ãºng cáº¥u trÃºc bitmap
        if (drawable instanceof BitmapDrawable) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }

        // KhÃ´ng cÃ¹ng cáº¥u trÃºc bitmap
        final int width = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().width() : drawable.getIntrinsicWidth();

        final int height = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().height() : drawable.getIntrinsicHeight();

        final Bitmap bitmap = Bitmap.createBitmap(width <= 0 ? 1 : width,
                height <= 0 ? 1 : height, Bitmap.Config.ARGB_8888);

        // Váº½ hÃ¬nh
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        // Chuyá»ƒn kiá»ƒu
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap convertByteArrayToBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    private void SampleData() {
        // region User
        userList = new ArrayList<>();
        userList.add(new User(1, "HoÃ ng Anh Tiáº¿n", "Male", "12-04-2003", "0828007853", "hoangtien2k3", "123456"));
        userList.add(new User(2, "Nguyá»…n ChÃ­ Háº£i Anh", "Male", "17-04-2003", "0947679750", "nguyenchihaianh", "123456"));
        userList.add(new User(3, "VÅ© Máº¡nh Chiáº¿n", "Male", "25-06-2003", "0388891635", "vumanhchien", "123456"));

        // region Restaurant
        restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant(1, "QuÃ¡n bÃ¡nh má»³", "Sá»‘ 24/63 PhÃ¹ng Khoang, Trung VÄƒn, Nam Tá»« LiÃªm",
                "0828007853", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.quan_banh_my_cho_phung_khoang, null))));
        restaurantList.add(new Restaurant(2, "QuÃ¡n trÃ  sá»¯a", "Sá»‘ 16/84 Há»“ TÃ¹ng Máº­u, Cáº§u Giáº¥y, HÃ  Ná»™i",
                "0885438847", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.quan_cafe_duong_tau, null))));
        restaurantList.add(new Restaurant(3, "QuÃ¡n cÆ¡m táº¥m", "Sá»‘ 44/132 Q.Cáº§y Giáº¥y, P.Quang Hoa, HÃ  Ná»™i",
                "0559996574", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.quan_com_tam_phuc_map, null))));
        restaurantList.add(new Restaurant(4, "QuÃ¡n bÃ¡nh Thanh HÃ ", "Sá»‘ 184 PhÃ¹ng Khoang, Trung VÄƒn, Nam Tá»« LiÃªm",
                "0141670738", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.quan_banh_ngot_le_van_viet, null))));
        restaurantList.add(new Restaurant(5, "QuÃ¡n kem Moly", "105 Thanh XuÃ¢n Báº¯c, Q.Thanh XuÃ¢n, HÃ  NÃ´i",
                "0627724695", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.quan_kem_co_hai, null))));
        restaurantList.add(new Restaurant(6, "Phá»Ÿ bÃ² HoÃ ng Tiáº¿n", "Royal City 72A Nguyá»…n TrÃ£i, Nam Tá»« LiÃªm",
                "0828007853", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.quan_pho_gia_truyen, null))));

        // region Restaurant saved
        restaurantSavedList = new ArrayList<>();
        restaurantSavedList.add(new RestaurantSaved(1, 3));
        restaurantSavedList.add(new RestaurantSaved(4, 3));
        restaurantSavedList.add(new RestaurantSaved(1, 1));
        restaurantSavedList.add(new RestaurantSaved(1, 2));
        restaurantSavedList.add(new RestaurantSaved(2, 2));
        restaurantSavedList.add(new RestaurantSaved(6, 3));

        // region Food
        foodList = new ArrayList<>();
        // region Kem
        foodList.add(new Food(1, "Kem há»™p Ä‘áº­u Ä‘á»", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemhop_daudo, null)),
                "Kem há»™p Ä‘áº­u Ä‘á» lÃ  má»™t mÃ³n trÃ¡ng miá»‡ng ngon vÃ  mÃ¡t láº¡nh, ráº¥t thÃ­ch há»£p cho nhá»¯ng ngÃ y hÃ¨ nÃ³ng bá»©c. DÆ°á»›i Ä‘Ã¢y lÃ  cÃ¡ch lÃ m kem há»™p Ä‘áº­u Ä‘á» táº¡i nhÃ :\n" +
                        "\n" +
                        "NguyÃªn liá»‡u:\n" +
                        "\n" +
                        "\t\t+ 250g Ä‘áº­u Ä‘á»1\n" +
                        "\t\t+ 35ml sá»¯a tÆ°Æ¡i1\n" +
                        "\t\t+ 150g Ä‘Æ°á»ng nÃ¢u1\n" +
                        "\t\t+ 1/4 muá»—ng cÃ  phÃª muá»‘i1\n" +
                        "\t\t+ 1/2 muá»—ng cÃ  phÃª máº­t ong1\n" +
                        "\t\t+ 40g sá»¯a Ä‘áº·c1\n" +
                        "\nCÃ¡ch lÃ m:\n" +
                        "\n" +
                        "\t\t+ Äáº­u Ä‘á» vo sáº¡ch, sau Ä‘Ã³ ngÃ¢m nÆ°á»›c 5-6 tiáº¿ng cho Ä‘á»— ná»Ÿ má»m1.\n" +
                        "\t\t+ Cho Ä‘á»— vÃ o ná»“i, sau Ä‘Ã³ cho nÆ°á»›c ngáº­p máº·t Ä‘á»— (cá»¡ 1 Ä‘á»‘t ngÃ³n tay). Báº­t báº¿p vÃ  khÃ´ng cáº§n Ä‘áº­y náº¯p ná»“i, náº¥u cho tá»›i khi Ä‘á»— chÃ­n má»m vÃ  nÆ°á»›c cÅ©ng rÃºt cáº¡n thÃ¬ táº¯t báº¿p1.\n" +
                        "\t\t+ Khi tháº¥y Ä‘áº­u chÃ­n má»m thÃ¬ cho thÃªm 100g Ä‘Æ°á»ng vÃ o, khuáº¥y Ä‘á»u. Khi Ä‘áº­u Ä‘Ã£ chÃ­n, báº¡n vá»›t ra Ä‘á»ƒ nguá»™i1.\n" +
                        "\t\t+ Láº¥y 2/3 pháº§n Ä‘áº­u vá»«a náº¥u vÃ o mÃ¡y sinh tá»‘, Ä‘á»ƒ nguá»™i bá»›t thÃ¬ thÃªm sá»¯a tÆ°Æ¡i, sá»¯a Ä‘áº·c vÃ o vÃ  xay nhuyá»…n2.\n" +
                        "\t\t+ Cho há»—n há»£p vá»«a xay vÃ o khuÃ´n kem Ä‘Ã£ chuáº©n bá»‹ sáºµn2.\n" +
                        "\t\t+ Giá» thÃ¬ cÃ¹ng cho vÃ o ngÄƒn Ä‘Ã´ng cá»§a tá»§ láº¡nh, báº¡n Ä‘á»ƒ khoáº£ng 7-8 giá» lÃ  cÃ³ thá»ƒ thÆ°á»Ÿng thá»©c mÃ³n kem thÆ¡m ngon, mÃ¡t láº¡nh nÃ y rá»“i2.", 5));
        foodList.add(new Food(1, "Kem há»™p sá»¯a dá»«a", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemhop_suadua, null)),
                "Kem há»™p sá»¯a dá»«a lÃ  má»™t loáº¡i kem thÆ¡m ngon, má»m má»‹n, cÃ³ hÆ°Æ¡ng vá»‹ Ä‘áº·c trÆ°ng cá»§a sá»¯a dá»«a. \n\n- DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ thÃ´ng tin vá» cÃ¡c loáº¡i kem há»™p sá»¯a dá»«a phá»• biáº¿n:\n" +
                        "\t\t+ Kem há»™p sá»¯a dá»«a Merino 900ml: Sáº£n pháº©m nÃ y káº¿t há»£p Ä‘á»™c Ä‘Ã¡o giá»¯a sáº§u riÃªng tá»± nhiÃªn vÃ  sá»¯a dá»«a má»m bÃ©o thÆ¡m bÃ¹i khÃ³ cÆ°á»¡ng, vá»‹ ngon hoÃ n háº£o1. GiÃ¡ bÃ¡n táº¡i BÃ¡ch hÃ³a XANH khoáº£ng 84.000 VND.\n" +
                        "\t\t+ Kem ly sá»¯a dá»«a Merino 53g: GiÃ¡ bÃ¡n khoáº£ng 13.000 VND, kem váº«n giá»¯ nguyÃªn nÃ©t thÆ¡m ngon Ä‘áº·c trÆ°ng truyá»n thá»‘ng, cá»±c há»£p kháº©u vá»‹ cá»§a ngÆ°á»i Viá»‡t, mang Ä‘áº¿n hÆ°Æ¡ng vá»‹ Ä‘á»™c Ä‘Ã¡o cá»±c ngon miá»‡ng.\n" +
                        "\t\t+ Há»™p sá»¯a dá»«a 450ml - Kem Merino: Kem há»™p Merino thÆ¡m ngon vá»›i cÃ¡c hÆ°Æ¡ng vá»‹ truyá»n thá»‘ng quen thuá»™c luÃ´n lÃ  mÃ³n trÃ¡ng miá»‡ng tuyá»‡t vá»i cho cáº£ gia Ä‘Ã¬nh.", 5));
        foodList.add(new Food(1, "Kem á»‘c quáº¿ vani", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_vani, null)),
                "Kem á»‘c quáº¿ vani lÃ  má»™t loáº¡i kem thÆ¡m ngon, má»m má»‹n, cÃ³ hÆ°Æ¡ng vá»‹ Ä‘áº·c trÆ°ng cá»§a vani. \n\n- DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ thÃ´ng tin vá» cÃ¡c loáº¡i kem á»‘c quáº¿ vani phá»• biáº¿n:\n" +
                        "\t\t+ Kem á»‘c quáº¿ vani socola Celano 110ml: Sáº£n pháº©m nÃ y mang Ä‘áº¿n sá»± Ä‘á»™c Ä‘Ã¡o, má»›i láº¡ vá»›i kem bÃ¡nh giÃ²n cÃ¹ng vá»‹ sÃ´ cÃ´ la ngá»t ngÃ o, vá»«a Ä‘áº­m Ä‘Ã  Ä‘áº§y kÃ­ch thÃ­ch1. GiÃ¡ bÃ¡n táº¡i BÃ¡ch hÃ³a XANH khoáº£ng 20.500 VND1.\n" +
                        "\t\t+ Kem á»c Quáº¿ Crunchy Classic Vani 70g: Sáº£n pháº©m nÃ y cÃ³ vá»‹ vani háº¥p dáº«n cÃ¹ng pháº§n á»‘c quáº¿ lÃ m tá»« bÃ¡nh quy giÃ²n thÆ¡m hÃ²a quyá»‡n vá»›i cÃ¡i láº¡nh mÃ¡t cá»§a kem mang láº¡i báº¡n sá»± ngá»t ngÃ o khÃ³ táº£ khÃ´ng thá»ƒ bá» qua2. GiÃ¡ bÃ¡n khoáº£ng 19.000 VND2.\n" +
                        "\t\t+ Kem á»‘c quáº¿ Merino Superteen vá»‹ vani socola 60g: Sáº£n pháº©m nÃ y Ä‘Æ°á»£c sáº£n xuáº¥t tá»« nguá»“n nguyÃªn liá»‡u tÆ°Æ¡i ngon, Ä‘áº£m báº£o cháº¥t lÆ°á»£ng. Vá»‹ kem mÃ¡t láº¡nh, káº¿t há»£p vá»›i vá»‹ ngá»t dá»‹u cá»§a dÃ¢u vÃ  hÆ°Æ¡ng vani thÆ¡m mÃ¡t, mang láº¡i sá»± ngon miá»‡ng, sáº£ng khoÃ¡i cho ngÆ°á»i thÆ°á»Ÿng thá»©c3.\n" +
                        "\t\t+ Kem vani socola Merino Super Teen cÃ¢y 60g: Sáº£n pháº©m nÃ y lÃ  dáº¡ng kem cÃ¢y á»‘c quáº¿ vÃ´ cÃ¹ng thÆ¡m ngon vÃ  tiá»‡n dá»¥ng vá»›i hÆ°Æ¡ng vá»‹ vani socola hÃ²a quyá»‡n Ä‘á»™c Ä‘Ã¡o4.", 5));
        foodList.add(new Food(1, "Kem Ã´c quáº¿ dÃ¢u", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_dau, null)),
                "Kem á»‘c quáº¿ dÃ¢u lÃ  má»™t loáº¡i kem thÆ¡m ngon, má»m má»‹n, cÃ³ hÆ°Æ¡ng vá»‹ Ä‘áº·c trÆ°ng cá»§a dÃ¢u. \n\n-  DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ thÃ´ng tin vá» cÃ¡c loáº¡i kem á»‘c quáº¿ dÃ¢u phá»• biáº¿n:\n" +
                        "\t\t+ Kem á»‘c quáº¿ dÃ¢u Celano Extra cÃ¢y 125ml (73g): Sáº£n pháº©m nÃ y mang Ä‘áº¿n sá»± Ä‘á»™c Ä‘Ã¡o, má»›i láº¡ vá»›i kem bÃ¡nh giÃ²n cÃ¹ng vá»‹ sá»¯a kem bÃ©o nháº¹ & má»©t dÃ¢u1. GiÃ¡ bÃ¡n táº¡i BÃ¡ch hÃ³a XANH khoáº£ng 24.000 VND1.\n" +
                        "\t\t+ Kem á»‘c quáº¿ vani dÃ¢u Celano cÃ¢y 66g: Sáº£n pháº©m nÃ y cÃ³ vá»‹ thÆ¡m ngon khÃ³ cÆ°á»¡ng, giÃºp háº¡ nhiá»‡t, giáº£i khÃ¡t vÃ´ cÃ¹ng hiá»‡u quáº£ cho cÃ¡c ngÃ y náº¯ng nÃ³ng2. GiÃ¡ bÃ¡n khoáº£ng 20.000 VND2.\n" +
                        "\t\t+ Kem á»c Quáº¿ Delight DÃ¢u - Nam Viá»‡t Quáº¥t 110ml: LÃ  sá»± hÃ²a quyá»‡n Ä‘á»™c Ä‘Ã¡o giá»¯a vá»‹ bÃ©o kem sá»¯a cÃ¹ng nhá»¯ng nguyÃªn liá»‡u thÆ¡m ngon, phá»§ bÃªn trÃªn chiáº¿c bÃ¡nh á»‘c quáº¿ giÃ²n giÃ²n, thÆ¡m lá»«ng mang Ä‘áº¿n cho báº¡n cáº£m giÃ¡c tháº­t mÃ¡t láº¡nh & ngon khÃ³ cÆ°á»¡ng ngay tá»« miáº¿ng cáº¯n Ä‘áº§u tiÃªn3", 5));
        foodList.add(new Food(1, "Kem á»‘c quáº¿ socola", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_socola, null)),
                "Kem á»‘c quáº¿ socola lÃ  má»™t loáº¡i kem thÆ¡m ngon, má»m má»‹n, cÃ³ hÆ°Æ¡ng vá»‹ Ä‘áº·c trÆ°ng cá»§a socola. \n\n- DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ thÃ´ng tin vá» cÃ¡c loáº¡i kem á»‘c quáº¿ socola phá»• biáº¿n:\n" +
                        "\t\t+ Kem á»‘c quáº¿ socola Kingâ€™s cÃ¢y 80g: Sáº£n pháº©m nÃ y vá»›i cÃ¡c nguyÃªn liá»‡u tá»± nhiÃªn cao cáº¥p, khÃ´ng sá»­ dá»¥ng cÃ¡c hÃ³a cháº¥t Ä‘á»™c háº¡i. Kem á»‘c quáº¿ socola Kingâ€™s cÃ¢y 80g vá»‹ socola Ä‘áº­m Ä‘Ã  vá»›i thiáº¿t káº¿ áº¥n tÆ°á»£ng, báº¯t máº¯t, hÆ°Æ¡ng vá»‹ thÆ¡m bÃ©o cÃ ng lÃ m cho sáº£n pháº©m thÃªm pháº§n háº¥p dáº«n1.\n" +
                        "\t\t+ Kem á»‘c quáº¿ socola extra Celano cÃ¢y 75g: Sáº£n pháº©m nÃ y mang Ä‘áº¿n sá»± Ä‘á»™c Ä‘Ã¡o, má»›i láº¡ vá»›i kem bÃ¡nh giÃ²n cÃ¹ng vá»‹ sÃ´ cÃ´ la extra Ä‘áº§y Ä‘á»™c Ä‘Ã¡o, bÃ©o thÆ¡m, vá»«a Ä‘áº­m Ä‘Ã  Ä‘áº§y kÃ­ch thÃ­ch2.\n" +
                        "\t\t+ Kem á»‘c quáº¿ Merino Superteen vá»‹ vani socola 60g: Sáº£n pháº©m nÃ y Ä‘Æ°á»£c sáº£n xuáº¥t tá»« nguá»“n nguyÃªn liá»‡u tÆ°Æ¡i ngon, Ä‘áº£m báº£o cháº¥t lÆ°á»£ng. Vá»‹ kem mÃ¡t láº¡nh, káº¿t há»£p vá»›i vá»‹ ngá»t dá»‹u cá»§a dÃ¢u vÃ  hÆ°Æ¡ng vani thÆ¡m mÃ¡t, mang láº¡i sá»± ngon miá»‡ng, sáº£ng khoÃ¡i cho ngÆ°á»i thÆ°á»Ÿng thá»©c3.\n" +
                        "\t\t+ Kem á»c Quáº¿ TH true ICE CREAM SÃ´ CÃ´ La NguyÃªn Cháº¥t: Sáº£n pháº©m Ä‘Æ°á»£c lÃ m tá»« sá»¯a tÆ°Æ¡i sáº¡ch nguyÃªn cháº¥t cá»§a Trang tráº¡i TH cÃ¹ng cÃ¡c nguyÃªn liá»‡u hoÃ n toÃ n tá»± nhiÃªn. Sáº£n pháº©m cÃ³ lá»›p kem sÃ´ cÃ´ la nguyÃªn cháº¥t má»m má»‹n, Ä‘Æ°á»£c phá»§ sá»‘t sÃ´ cÃ´ la cÃ¹ng háº¡t Ä‘áº­u phá»™ng thÆ¡m ngáº­y trÃªn bá» máº·t, cuá»™n trong vá» bÃ¡nh á»‘c quáº¿ giÃ²n tan bÃªn ngoÃ i4.\n" +
                        "\t\t+ Kem á»c Quáº¿ Delight SÃ´cÃ´la - Äáº­u Phá»™ng 110ml: LÃ  sá»± hÃ²a quyá»‡n Ä‘á»™c Ä‘Ã¡o giá»¯a vá»‹ bÃ©o kem sá»¯a cÃ¹ng nhá»¯ng nguyÃªn liá»‡u thÆ¡m ngon, phá»§ bÃªn trÃªn chiáº¿c bÃ¡nh á»‘c quáº¿ giÃ²n giÃ²n", 5));
        foodList.add(new Food(1, "Kem Ã´c quáº¿ dÃ¢u", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_dau, null)),
                "Kem á»‘c quáº¿ dÃ¢u lÃ  má»™t loáº¡i kem thÆ¡m ngon, má»m má»‹n, cÃ³ hÆ°Æ¡ng vá»‹ Ä‘áº·c trÆ°ng cá»§a dÃ¢u. \\n\\n-  DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ thÃ´ng tin vá» cÃ¡c loáº¡i kem á»‘c quáº¿ dÃ¢u phá»• biáº¿n:\\n\" +\n" +
                        "\"\\t\\t+ Kem á»‘c quáº¿ dÃ¢u Celano Extra cÃ¢y 125ml (73g): Sáº£n pháº©m nÃ y mang Ä‘áº¿n sá»± Ä‘á»™c Ä‘Ã¡o, má»›i láº¡ vá»›i kem bÃ¡nh giÃ²n cÃ¹ng vá»‹ sá»¯a kem bÃ©o nháº¹ & má»©t dÃ¢u1. GiÃ¡ bÃ¡n táº¡i BÃ¡ch hÃ³a XANH khoáº£ng 24.000 VND1.\\n\" +\n" +
                        "\"\\t\\t+ Kem á»‘c quáº¿ vani dÃ¢u Celano cÃ¢y 66g: Sáº£n pháº©m nÃ y cÃ³ vá»‹ thÆ¡m ngon khÃ³ cÆ°á»¡ng, giÃºp háº¡ nhiá»‡t, giáº£i khÃ¡t vÃ´ cÃ¹ng hiá»‡u quáº£ cho cÃ¡c ngÃ y náº¯ng nÃ³ng2. GiÃ¡ bÃ¡n khoáº£ng 20.000 VND2.\\n\" +\n" +
                        "\"\\t\\t+ Kem á»c Quáº¿ Delight DÃ¢u - Nam Viá»‡t Quáº¥t 110ml: LÃ  sá»± hÃ²a quyá»‡n Ä‘á»™c Ä‘Ã¡o giá»¯a vá»‹ bÃ©o kem sá»¯a cÃ¹ng nhá»¯ng nguyÃªn liá»‡u thÆ¡m ngon, phá»§ bÃªn trÃªn chiáº¿c bÃ¡nh á»‘c quáº¿ giÃ²n giÃ²n, thÆ¡m lá»«ng mang Ä‘áº¿n cho báº¡n cáº£m giÃ¡c tháº­t mÃ¡t láº¡nh & ngon khÃ³ cÆ°á»¡ng ngay tá»« miáº¿ng cáº¯n Ä‘áº§u tiÃªn3", 5));
        foodList.add(new Food(1, "Kem á»‘c quáº¿ socola", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_socola, null)),
                "Kem á»‘c quáº¿ socola lÃ  má»™t loáº¡i kem thÆ¡m ngon, má»m má»‹n, cÃ³ hÆ°Æ¡ng vá»‹ Ä‘áº·c trÆ°ng cá»§a socola. \n\n- DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ thÃ´ng tin vá» cÃ¡c loáº¡i kem á»‘c quáº¿ socola phá»• biáº¿n:\n" +
                        "\t\t+ Kem á»‘c quáº¿ socola Kingâ€™s cÃ¢y 80g: Sáº£n pháº©m nÃ y vá»›i cÃ¡c nguyÃªn liá»‡u tá»± nhiÃªn cao cáº¥p, khÃ´ng sá»­ dá»¥ng cÃ¡c hÃ³a cháº¥t Ä‘á»™c háº¡i. Kem á»‘c quáº¿ socola Kingâ€™s cÃ¢y 80g vá»‹ socola Ä‘áº­m Ä‘Ã  vá»›i thiáº¿t káº¿ áº¥n tÆ°á»£ng, báº¯t máº¯t, hÆ°Æ¡ng vá»‹ thÆ¡m bÃ©o cÃ ng lÃ m cho sáº£n pháº©m thÃªm pháº§n háº¥p dáº«n.\n" +
                        "\t\t+ Kem á»‘c quáº¿ socola extra Celano cÃ¢y 75g: Sáº£n pháº©m nÃ y mang Ä‘áº¿n sá»± Ä‘á»™c Ä‘Ã¡o, má»›i láº¡ vá»›i kem bÃ¡nh giÃ²n cÃ¹ng vá»‹ sÃ´ cÃ´ la extra Ä‘áº§y Ä‘á»™c Ä‘Ã¡o, bÃ©o thÆ¡m, vá»«a Ä‘áº­m Ä‘Ã  Ä‘áº§y kÃ­ch thÃ­ch.\n" +
                        "\t\t+ Kem á»‘c quáº¿ Merino Superteen vá»‹ vani socola 60g: Sáº£n pháº©m nÃ y Ä‘Æ°á»£c sáº£n xuáº¥t tá»« nguá»“n nguyÃªn liá»‡u tÆ°Æ¡i ngon, Ä‘áº£m báº£o cháº¥t lÆ°á»£ng. Vá»‹ kem mÃ¡t láº¡nh, káº¿t há»£p vá»›i vá»‹ ngá»t dá»‹u cá»§a dÃ¢u vÃ  hÆ°Æ¡ng vani thÆ¡m mÃ¡t, mang láº¡i sá»± ngon miá»‡ng, sáº£ng khoÃ¡i cho ngÆ°á»i thÆ°á»Ÿng thá»©c.\n" +
                        "\t\t+ Kem á»c Quáº¿ TH true ICE CREAM SÃ´ CÃ´ La NguyÃªn Cháº¥t: Sáº£n pháº©m Ä‘Æ°á»£c lÃ m tá»« sá»¯a tÆ°Æ¡i sáº¡ch nguyÃªn cháº¥t cá»§a Trang tráº¡i TH cÃ¹ng cÃ¡c nguyÃªn liá»‡u hoÃ n toÃ n tá»± nhiÃªn. Sáº£n pháº©m cÃ³ lá»›p kem sÃ´ cÃ´ la nguyÃªn cháº¥t má»m má»‹n, Ä‘Æ°á»£c phá»§ sá»‘t sÃ´ cÃ´ la cÃ¹ng háº¡t Ä‘áº­u phá»™ng thÆ¡m ngáº­y trÃªn bá» máº·t, cuá»™n trong vá» bÃ¡nh á»‘c quáº¿ giÃ²n tan bÃªn ngoÃ i.\n" +
                        "\t\t+ Kem á»c Quáº¿ Delight SÃ´cÃ´la - Äáº­u Phá»™ng 110ml: LÃ  sá»± hÃ²a quyá»‡n Ä‘á»™c Ä‘Ã¡o giá»¯a vá»‹ bÃ©o kem sá»¯a cÃ¹ng nhá»¯ng nguyÃªn liá»‡u thÆ¡m ngon, phá»§ bÃªn trÃªn chiáº¿c bÃ¡nh á»‘c quáº¿ giÃ²n giÃ²n.", 5));

        // region Banh mi
        foodList.add(new Food(1, "BÃ¡nh mÃ¬ bÃ² kho", "BÃ¡nh mÃ¬",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_bokho, null)),
                "BÃ¡nh mÃ¬ bÃ² kho lÃ  má»™t mÃ³n Äƒn ngon vÃ  bá»• dÆ°á»¡ng, thÆ°á»ng Ä‘Æ°á»£c cháº¿ biáº¿n cho bá»¯a Äƒn sÃ¡ng. DÆ°á»›i Ä‘Ã¢y lÃ  cÃ¡ch náº¥u bÃ¡nh mÃ¬ bÃ² kho1:\n" +
                        "\n" +
                        "NguyÃªn liá»‡u:\n" +
                        "\n" +
                        "\t\t+ 1 kg thá»‹t báº¯p bÃ² hoáº·c thá»‹t mÃ´ng bÃ²\n" +
                        "\t\t+ 2 cá»§ hÃ nh khÃ´ hoáº·c Â½ cá»§ hÃ nh tÃ¢y\n" +
                        "\t\t+ 1 trÃ¡i cÃ  chua\n" +
                        "\t\t+ 1 máº©u gá»«ng nhá»\n" +
                        "\t\t+ 5 tÃ©p tá»i\n" +
                        "\t\t+ 3 cÃ¢y sáº£\n" +
                        "\t\t+ 2 hoa há»“i\n" +
                        "\t\t+ 1 thanh quáº¿\n" +
                        "\t\t+ 2 cá»§ cÃ  rá»‘t\n" +
                        "\t\t+ 1 thÃ¬a dáº§u mÃ u Ä‘iá»u\n" +
                        "\t\t+ 1 thÃ¬a bá»™t nÄƒng\n" +
                        "\t\t+ NÆ°á»›c dá»«a tÆ°Æ¡i (náº¿u cÃ³)\n" +
                        "\t\t+ BÃ¡nh mÃ¬: 2 cÃ¡i1\n" +
                        "\nCÃ¡ch lÃ m:\n" +
                        "\n" +
                        "\t\t+ SÆ¡ cháº¿ thá»‹t bÃ²: Thá»‹t bÃ² vo sáº¡ch, sau Ä‘Ã³ ngÃ¢m nÆ°á»›c khoáº£ng 5-6 tiáº¿ng cho Ä‘á»— ná»Ÿ má»m1.\n" +
                        "\t\t+ Náº¥u thá»‹t bÃ²: Cho thá»‹t vÃ o ná»“i, sau Ä‘Ã³ cho nÆ°á»›c ngáº­p máº·t thá»‹t (cá»¡ 1 Ä‘á»‘t ngÃ³n tay). Báº­t báº¿p vÃ  khÃ´ng cáº§n Ä‘áº­y náº¯p ná»“i, náº¥u cho tá»›i khi thá»‹t chÃ­n má»m vÃ  nÆ°á»›c cÅ©ng rÃºt cáº¡n thÃ¬ táº¯t báº¿p1.\n" +
                        "\t\t+ SÆ¡ cháº¿ cÃ¡c nguyÃªn liá»‡u khÃ¡c: HÃ nh tÃ¢y thÃ¡i má»ng. Gá»«ng, tá»i, sáº£ Ä‘áº­p dáº­p. CÃ  chua cáº¯t háº¡t lá»±u. CÃ  rá»‘t tá»‰a hoa, thÃ¡i miáº¿ng vá»«a Äƒn. Hoa há»“i, quáº¿ rang thÆ¡m. HÃ²a tan bá»™t nÄƒng cÃ¹ng má»™t chÃºt nÆ°á»›c1.\n" +
                        "\t\t+ Náº¥u bÃ² kho: Phi thÆ¡m gá»«ng, tá»i, sáº£ vá»›i 1 thÃ¬a dáº§u mÃ u Ä‘iá»u. Cho thá»‹t bÃ² vÃ o xÃ o Ä‘áº¿n khi sÄƒn láº¡i thÃ¬ cho cÃ  chua vÃ o Ä‘áº£o Ä‘á»u tay. Cho nÆ°á»›c dá»«a, nÆ°á»›c lá»c xÃ¢m xáº¥p máº·t thá»‹t. Cho tiáº¿p quáº¿, hoa há»“i vÃ o ná»“i vÃ  Ä‘un sÃ´i1.\n" +
                        "\t\t+ Sau khoáº£ng 30 phÃºt thÃ¬ vá»›t xÃ¡c quáº¿, hoa há»“i, sáº£ ra ngoÃ i1.\n" +
                        "\t\t+ Cho bá»™t nÄƒng Ä‘Ã£ hÃ²a tan vÃ o vÃ  khuáº¥y Ä‘á»u1.\n" +
                        "\t\t+ Cuá»‘i cÃ¹ng cho cÃ  rá»‘t, nÃªm náº¿m gia vá»‹ vá»«a Äƒn1.\n" +
                        "\t\t+ Äun sÃ´i thÃªm má»™t lÃºc Ä‘áº¿n khi cÃ  rá»‘t chÃ­n thÃ¬ táº¯t báº¿p1.", 1));
        foodList.add(new Food(1, "BÃ¡nh mÃ¬ bÆ¡ tá»i", "BÃ¡nh mÃ¬",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_botoi, null)),
                "BÃ¡nh mÃ¬ bÆ¡ tá»i lÃ  má»™t mÃ³n Äƒn ráº¥t nhiá»u ngÆ°á»i yÃªu thÃ­ch, khÃ´ng chá»‰ tiá»‡n lá»£i cho bá»¯a sÃ¡ng dinh dÆ°á»¡ng mÃ  cÃ²n cá»±c ká»³ thÃ­ch há»£p cho nhá»¯ng buá»•i tiá»‡c nhá»1. \n\n-  DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u bÃ¡nh mÃ¬ bÆ¡ tá»i:\n" +
                        "\t\t+ HÆ°á»›ng Dáº«n CÃ¡ch LÃ m BÃ¡nh MÃ¬ BÆ¡ Tá»i SiÃªu Ngon - Huongnghiepaau1: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m bÃ¡nh mÃ¬ bÆ¡ tá»i kiá»ƒu má»›i lÃ m mÃ³n Äƒn â€œÄ‘á»•i giÃ³â€ cho cáº£ gia Ä‘Ã¬nh. CÃ¡ch lÃ m khÃ¡ Ä‘Æ¡n giáº£n vÃ  dá»… dÃ ng, báº¥t cá»© ai cÅ©ng cÃ³ thá»ƒ thÃ nh cÃ´ng ngay láº§n Ä‘áº§u1.\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh mÃ¬ bÆ¡ tá»i giÃ²n thÆ¡m ná»©c mÅ©i - BÃ¡ch hÃ³a XANH2: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh mÃ¬ bÆ¡ tá»i thÆ¡m ngon, giÃ²n rá»¥m Ä‘Æ¡n giáº£n táº¡i nhÃ 2.\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh mÃ¬ bÆ¡ tá»i báº±ng ná»“i chiÃªn khÃ´ng dáº§u háº¥p dáº«n3: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh mÃ¬ bÆ¡ tá»i vá»›i ná»“i chiÃªn khÃ´ng dáº§u, giÃºp báº¡n cÃ³ Ä‘Æ°á»£c chiáº¿c bÃ¡nh mÃ¬ giÃ²n rá»¥m, thÆ¡m phá»©c3.", 1));
        foodList.add(new Food(1, "BÃ¡nh mÃ¬ cháº£o", "BÃ¡nh mÃ¬",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_chao, null)),
                "BÃ¡nh mÃ¬ cháº£o lÃ  má»™t mÃ³n Äƒn ngon vÃ  nhanh gá»n, thÃ­ch há»£p cho bá»¯a sÃ¡ng. DÆ°á»›i Ä‘Ã¢y lÃ  cÃ¡ch lÃ m bÃ¡nh mÃ¬ cháº£o pate, xÃºc xÃ­ch, trá»©ng1:\n" +
                        "\n" +
                        "NguyÃªn liá»‡u:\n" +
                        "\n" +
                        "\t\t+ 2 muá»—ng canh dáº§u Äƒn\n" +
                        "\t\t+ 1 muá»—ng canh tá»i bÄƒm\n" +
                        "\t\t+ 2 trÃ¡i cÃ  chua cáº¯t háº¡t lá»±u\n" +
                        "\t\t+ 3 muá»—ng canh sá»‘t cÃ  chua\n" +
                        "\t\t+ 1 muá»—ng cÃ  phÃª muá»‘i\n" +
                        "\t\t+ 1 muá»—ng cÃ  phÃª Ä‘Æ°á»ng\n" +
                        "\t\t+ 1 muá»—ng cÃ  phÃª tiÃªu\n" +
                        "\t\t+ BÃ¡nh mÃ¬, pa tÃª, xÃºc xÃ­ch, trá»©ng gÃ  (sá»‘ lÆ°á»£ng tÃ¹y thÃ­ch)1\n" +
                        "\nCÃ¡ch lÃ m:\n" +
                        "\n" +
                        "\t\t+ LÃ m sá»‘t cÃ  chua: Cho 1 muá»—ng canh dáº§u Äƒn vÃ o ná»“i Ä‘á»ƒ phi tá»i cho thÆ¡m. Khi tá»i Ä‘Ã£ vÃ ng thÃ¬ cho cÃ  chua cáº¯t nhá» vÃ  3 muá»—ng canh sá»‘t cÃ  chua vÃ o trá»™n Ä‘á»u, thÃªm chÃºt nÆ°á»›c vÃ  nÃªm vá»›i 1 muá»—ng cÃ  phÃª muá»‘i, 1 muá»—ng cÃ  phÃª Ä‘Æ°á»ng vÃ  1 muá»—ng cÃ  phÃª tiÃªu rá»“i náº¥u cho Ä‘áº¿n khi cÃ  chua nhá»« lÃ  Ä‘Æ°á»£c1.\n" +
                        "\t\t+ LÃ m bÃ¡nh mÃ¬ cháº£o: LÃ m nÃ³ng cháº£o rá»“i cho 1 muá»—ng canh dáº§u Äƒn vÃ o, dáº§u Äƒn nÃ³ng thÃ¬ cho xÃºc xÃ­ch Ä‘Ã£ cáº¯t Ä‘Ã´i vÃ  1 viÃªn pa tÃª vÃ o chiÃªn cho xÃºc xÃ­ch vÃ ng. Khi xÃºc xÃ­ch Ä‘Ã£ vÃ ng thÃ¬ Ä‘áº­p trá»©ng gÃ  vÃ  hÃ nh tÃ¢y cáº¯t sá»£i vÃ o. Cuá»‘i cÃ¹ng mÃºc nÆ°á»›c sá»‘t Ä‘Ã£ lÃ m chan vÃ o rá»“i cho 1 viÃªn bÆ¡ láº¡t lÃªn", 1));
        foodList.add(new Food(1, "BÃ¡nh mÃ¬ hoa cÃºc", "BÃ¡nh mÃ¬",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_hoacuc, null)),
                "BÃ¡nh mÃ¬ cháº£o lÃ  má»™t mÃ³n Äƒn ngon Ä‘Æ°á»£c giá»›i tráº» yÃªu thÃ­ch vÃ  ngÃ y cÃ ng trá»Ÿ nÃªn phá»• biáº¿n1. HÆ°Æ¡ng vá»‹ hÃ²a quyá»‡n giá»¯a xÃºc xÃ­ch, trá»©ng, pate, thá»‹t bÃ² má»mâ€¦ Ä‘áº¯m chÃ¬m trong nÆ°á»›c xá»‘t Ä‘áº­m Ä‘Ã , sÃ¡nh má»‹n tháº­t khÃ³ quÃªn khi Ä‘Ã£ thÆ°á»Ÿng thá»©c qua 1 láº§n1. \n\n- DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u bÃ¡nh mÃ¬ cháº£o:\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh mÃ¬ cháº£o pate, xÃºc xÃ­ch1: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m bÃ¡nh mÃ¬ cháº£o kiá»ƒu má»›i lÃ m mÃ³n Äƒn â€œÄ‘á»•i giÃ³â€ cho cáº£ gia Ä‘Ã¬nh. CÃ¡ch lÃ m khÃ¡ Ä‘Æ¡n giáº£n vÃ  dá»… dÃ ng, báº¥t cá»© ai cÅ©ng cÃ³ thá»ƒ thÃ nh cÃ´ng ngay láº§n Ä‘áº§u1.\n" +
                        "\t\t+ Tá»± Tay LÃ m BÃ¡nh MÃ¬ Cháº£o Cho SiÃªu Ngon Bá»¯a SÃ¡ng Äáº§y NÄƒng LÆ°á»£ng2: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh mÃ¬ cháº£o thÆ¡m ngon, giÃ²n rá»¥m Ä‘Æ¡n giáº£n táº¡i nhÃ 2.\n" +
                        "\t\t+ BÃ¡nh mÃ¬ cháº£o cÃ¡ há»™p3: Báº¡n tÃ¬m 1 mÃ³n Äƒn sÃ¡ng cháº¿ biáº¿n nhanh nhÆ°ng cÅ©ng pháº£i ngon miá»‡ng thÃ¬ bÃ¡nh mÃ¬ cháº£o lÃ  mÃ³n báº¡n Ä‘ang tÃ¬m kiáº¿m. NguyÃªn liá»‡u dá»… mua, thá»i gian thá»±c hiá»‡n nhanh chÃ³ng vÃ  cÃ²n ráº¥t Ä‘Æ°á»£c lÃ²ng cá»§a má»i ngÆ°á»i3.\n" +
                        "\t\t- 4 cÃ¡ch lÃ m bÃ¡nh mÃ¬ cháº£o táº¡i nhÃ  thÆ¡m ngon cho bá»¯a sÃ¡ng4: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh mÃ¬ cháº£o theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t. VÃ¬ tháº¿, hÃ´m nay Chef.vn sáº½ hÆ°á»›ng dáº«n cÃ¡c báº¡n cÃ¡ch náº¥u bÃ² kho bÃ¡nh mÃ¬ ráº¥t Ä‘Æ¡n giáº£n mÃ  láº¡i dá»… lÃ m, quan trá»ng nháº¥t lÃ  báº¡n pháº£i biáº¿t máº¹o Ä‘á»ƒ Æ°á»›p thá»‹t bÃ² Ä‘á»ƒ mÃ³n bÃ² kho Ä‘áº­m Ä‘Ã  thÆ¡m ngon mÃ  khÃ´ng bá»‹ dai", 1));
        foodList.add(new Food(1, "BÃ¡nh mÃ¬ hoa cÃºc", "BÃ¡nh mÃ¬",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_hoacuc, null)),
                "BÃ¡nh mÃ¬ hoa cÃºc, cÃ²n Ä‘Æ°á»£c gá»i lÃ  Brioche, lÃ  má»™t loáº¡i bÃ¡nh mÃ¬ cÃ³ nguá»“n gá»‘c tá»« PhÃ¡p. Vá»›i thÃ nh pháº§n giÃ u bÆ¡ vÃ  trá»©ng, bÃ¡nh mÃ¬ hoa cÃºc cÃ³ má»™t lá»›p vá» má»m, áº©m vÃ  vÃ ng sáº«m. Khi nÆ°á»›ng lÃªn, bÃ¡nh luÃ´n cÃ³ thá»› má»m vÃ  xá»‘p, hÆ°Æ¡ng vá»‹ vÃ´ cÃ¹ng tuyá»‡t vá»i1. \n\n- DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u bÃ¡nh mÃ¬ hoa cÃºc:\n" +
                        "\t\t+ CÃ¡ch LÃ m BÃ¡nh MÃ¬ Hoa CÃºc ÄÆ¡n Giáº£n & Ngon Nháº¥t1: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m bÃ¡nh mÃ¬ hoa cÃºc kiá»ƒu má»›i lÃ m mÃ³n Äƒn â€œÄ‘á»•i giÃ³â€ cho cáº£ gia Ä‘Ã¬nh. CÃ¡ch lÃ m khÃ¡ Ä‘Æ¡n giáº£n vÃ  dá»… dÃ ng, báº¥t cá»© ai cÅ©ng cÃ³ thá»ƒ thÃ nh cÃ´ng ngay láº§n Ä‘áº§u1.\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh mÃ¬ hoa cÃºc Harrys (Ä‘Æ¡n giáº£n, nhá»“i bá»™t dá»…) [VIDEO]2: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh mÃ¬ hoa cÃºc theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t2.\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh mÃ¬ hoa cÃºc Harrys (Ä‘Æ¡n giáº£n, nhá»“i bá»™t dá»…) [VIDEO] - Savoury Days2: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh mÃ¬ hoa cÃºc theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t2", 1));
        foodList.add(new Food(1, "BÃ¡nh mÃ¬ káº¹p thá»‹t", "BÃ¡nh mÃ¬",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_kepthit, null)),
                "BÃ¡nh mÃ¬ káº¹p thá»‹t lÃ  má»™t mÃ³n Äƒn truyá»n thá»‘ng cá»§a ngÆ°á»i Viá»‡t, Ä‘Æ°á»£c nhiá»u ngÆ°á»i yÃªu thÃ­ch vÃ  trá»Ÿ thÃ nh â€œsiÃªu saoâ€ áº©m thá»±c khÃ´ng chá»‰ á»Ÿ Viá»‡t Nam mÃ  cÃ²n trÃªn kháº¯p tháº¿ giá»›i1. \n\n- DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u bÃ¡nh mÃ¬ káº¹p thá»‹t:\n" +
                        "\t\t+ CÃ¡ch LÃ m BÃ¡nh MÃ¬ Káº¹p Thá»‹t Viá»‡t Nam LÃ m MÃª Máº©n Thá»±c KhÃ¡ch Tháº¿ Giá»›i - Huongnghiepaau1: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m bÃ¡nh mÃ¬ káº¹p thá»‹t kiá»ƒu má»›i lÃ m mÃ³n Äƒn â€œÄ‘á»•i giÃ³â€ cho cáº£ gia Ä‘Ã¬nh. CÃ¡ch lÃ m khÃ¡ Ä‘Æ¡n giáº£n vÃ  dá»… dÃ ng, báº¥t cá»© ai cÅ©ng cÃ³ thá»ƒ thÃ nh cÃ´ng ngay láº§n Ä‘áº§u1.\n" +
                        "\t\t+ CÃ¡ch lÃ m 4 mÃ³n bÃ¡nh mÃ¬ káº¹p siÃªu ngon vÃ  Ä‘Æ¡n giáº£n - YummyDay2: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh mÃ¬ káº¹p thÆ¡m ngon, giÃ²n rá»¥m Ä‘Æ¡n giáº£n táº¡i nhÃ 2.\n" +
                        "\t\t+ BÃ¡nh mÃ¬ sandwich káº¹p thá»‹t nguá»™i cho bá»¯a sÃ¡ng ngon lÃ nh3: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh mÃ¬ sandwich káº¹p thá»‹t nguá»™i cho bá»¯a sÃ¡ng ngon lÃ nh3.\n" +
                        "\t\t+ Top 10 loáº¡i nhÃ¢n káº¹p bÃ¡nh mÃ¬ thÆ¡m ngon, dinh dÆ°á»¡ng cho bá»¯a sÃ¡ng4: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh mÃ¬ káº¹p theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t4.\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh mÃ¬ káº¹p thá»‹t ngon khÃ´ng kÃ©m ngoÃ i hÃ ng5: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh mÃ¬ káº¹p theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t5.", 1));
        foodList.add(new Food(1, "BÃ¡nh mÃ¬ káº¹p xÃºc xÃ­ch", "BÃ¡nh mÃ¬",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_kepxucxich, null)),
                "BÃ¡nh mÃ¬ káº¹p xÃºc xÃ­ch lÃ  má»™t mÃ³n Äƒn ngon vÃ  phá»• biáº¿n, thÆ°á»ng Ä‘Æ°á»£c dÃ¹ng lÃ m bá»¯a sÃ¡ng hoáº·c bá»¯a Äƒn nháº¹. \n\n- DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u bÃ¡nh mÃ¬ káº¹p xÃºc xÃ­ch:\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh mÃ¬ káº¹p trá»©ng xÃºc xÃ­ch cho bá»¯a sÃ¡ng ngon miá»‡ng!1: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m bÃ¡nh mÃ¬ káº¹p trá»©ng xÃºc xÃ­ch kiá»ƒu má»›i lÃ m mÃ³n Äƒn â€œÄ‘á»•i giÃ³â€ cho cáº£ gia Ä‘Ã¬nh. CÃ¡ch lÃ m khÃ¡ Ä‘Æ¡n giáº£n vÃ  dá»… dÃ ng, báº¥t cá»© ai cÅ©ng cÃ³ thá»ƒ thÃ nh cÃ´ng ngay láº§n Ä‘áº§u1.\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh mÃ¬ káº¹p trá»©ng xÃºc xÃ­ch Cá»°C NGON2: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh mÃ¬ káº¹p trá»©ng xÃºc xÃ­ch thÆ¡m ngon háº¥p dáº«n chi tiáº¿t2.\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh mÃ¬ káº¹p xÃºc xÃ­ch NGON, Ráºº, Cá»°C Dá»„ LÃ€M táº¡i nhÃ 3: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh mÃ¬ káº¹p theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t3.\n" +
                        "\t\t+ Báº­t mÃ­ cÃ¡ch lÃ m bÃ¡nh mÃ¬ káº¹p xÃºc xÃ­ch Ä‘Æ¡n giáº£n, chuáº©n vá»‹4: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh mÃ¬ káº¹p theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t4.", 1));
        foodList.add(new Food(1, "Hamburger bÃ²", "BÃ¡nh mÃ¬",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.burger_bo, null)),
                "Hamburger bÃ² lÃ  má»™t mÃ³n Äƒn nhanh phá»• biáº¿n cá»§a ngÆ°á»i phÆ°Æ¡ng TÃ¢y1. \n\n- DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u Hamburger bÃ²:\n" +
                        "\t\t+ CÃ¡ch lÃ m Hamburger bÃ² cá»±c Ä‘Æ¡n giáº£n chá»‰ trong 3 bÆ°á»›c - PasGo2: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m Hamburger bÃ² kiá»ƒu má»›i lÃ m mÃ³n Äƒn â€œÄ‘á»•i giÃ³â€ cho cáº£ gia Ä‘Ã¬nh. CÃ¡ch lÃ m khÃ¡ Ä‘Æ¡n giáº£n vÃ  dá»… dÃ ng, báº¥t cá»© ai cÅ©ng cÃ³ thá»ƒ thÃ nh cÃ´ng ngay láº§n Ä‘áº§u2.\n" +
                        "\t\t+ 3 cÃ¡ch lÃ m hamburger bÃ² kiá»ƒu Má»¹ ngon nhÆ° ngoÃ i hÃ ng1: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m hamburger bÃ² theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t1.\n" +
                        "\t\t+ 2 CÃ¡ch LÃ m Hamburger BÃ² Cá»±c Ngon KhÃ´ng Thá»ƒ Bá» Qua3: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m hamburger bÃ² theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t", 1));
        foodList.add(new Food(1, "Hamburger heo", "BÃ¡nh mÃ¬",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.burger_heo, null)),
                "DÆ°á»›i Ä‘Ã¢y lÃ  cÃ¡ch lÃ m Hamburger Heo BÃ² NhÃ  LÃ m1:\n" +
                        "\n" +
                        "NguyÃªn liá»‡u:\n" +
                        "\n" +
                        "\t\t+ 100g thá»‹t heo xay\n" +
                        "\t\t+ 100g thá»‹t bÃ² xay\n" +
                        "\t\t+ 2 cÃ¡i vá» bÃ¡nh hamburger\n" +
                        "\t\t+ PhÃ´ mai con bÃ² cÆ°á»i: 1 viÃªn\n" +
                        "\t\t+ PhÃ´ mai lÃ¡t: 2 lÃ¡ (cÃ³ thá»ƒ dÃ¹ng báº¥t kÃ¬ phomai lÃ¡t nÃ o cÅ©ng Ä‘Æ°á»£c)\n" +
                        "\t\t+ HÃ nh tÃ¢y, hÃ nh cá»§, giáº¥m, xÃ  lÃ¡ch, muá»‘i, Ä‘Æ°á»ng, bá»™t nÃªm\n" +
                        "\t\t+ Sá»‘t kewpie, sá»‘t tÆ°Æ¡ng á»›t\n" +
                        "\t\t+ 1 trÃ¡i cÃ  chua1\n" +
                        "\nCÃ¡ch lÃ m:\n" +
                        "\n" +
                        "\t\t+ Rá»­a sáº¡ch xÃ  lÃ¡ch, cÃ  chua, hÃ nh tÃ¢y.\n" +
                        "\t\t+ Trá»™n thá»‹t heo vÃ  bÃ², cáº¯t nhá» hÃ nh cá»§, cáº¯t 1 khoanh hÃ nh tÃ¢y báº±m nhá». Cho 2 muá»—ng cafe Ä‘Æ°á»ng, 1 muá»—ng cafe muá»‘i, 1 muá»—ng cafe bá»™t nÃªm. Trá»™n Ä‘á»u. PhÃ´mai con bÃ² cÆ°á»i cáº¯t nhá» trá»™n chung vá»›i thá»‹t. Chia lÃ m 2 pháº§n thá»‹t báº±ng nhau vo viÃªn, Ã©p dáº¹p vá»«a Ä‘á»§.\n" +
                        "\t\t+ Cáº¯t 2 khoanh hÃ nh tÃ¢y.\n" +
                        "\t\t+ Bá» 2 pháº§n thá»‹t vÃ o cháº£o trÃ¡ng dáº§u chiÃªn vÃ ng Ä‘á»u 2 máº·t khoáº£ng 10p-15p. Vá»›t thá»‹t ra cho hÃ nh tÃ¢y vÃ o xÃ o sÆ¡ thÃªm Ã­t Ä‘Æ°á»ng vÃ  muá»‘i vÃ o khi xÃ o hÃ nh.\n" +
                        "\t\t+ HÃ nh tÃ¢y Ä‘Ã£ xÃ o sÆ¡ cho vÃ o chÃ©n cho 1 muá»—ng cafe giáº¥m Äƒn, 2 muá»—ng cafe Ä‘Æ°á»ng. Trá»™n Ä‘á»u.\n" +
                        "\t\t+ TrÃ¬nh bÃ y: cáº¯t vá» bÃ¡nh lÃ m Ä‘Ã´i. Cho sá»‘t kewpie vÃ o 1 bÃªn vá» bÃ¡nh, sau Ä‘Ã³ cho lÃªn Ä‘Ã³ 1 lá»›p rau cá»§, tiáº¿p Ä‘áº¿n thÃªm 1 lá»›p thá»‹t vÃ  1 muá»—ng sá»‘t hoisin lÃªn trÃªn, tiáº¿p tá»¥c cho 1 lá»›p náº¥m lÃªn trÃªn1", 1));
        foodList.add(new Food(1, "Hamburger bÃ²", "BÃ¡nh mÃ¬",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.burger_bo, null)),
                "Hamburger bÃ² lÃ  má»™t mÃ³n Äƒn nhanh phá»• biáº¿n cá»§a ngÆ°á»i phÆ°Æ¡ng TÃ¢y1. \n\n- DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u Hamburger bÃ²:\n" +
                        "\t\t+ CÃ¡ch lÃ m Hamburger bÃ² cá»±c Ä‘Æ¡n giáº£n chá»‰ trong 3 bÆ°á»›c - PasGo2: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m Hamburger bÃ² kiá»ƒu má»›i lÃ m mÃ³n Äƒn â€œÄ‘á»•i giÃ³â€ cho cáº£ gia Ä‘Ã¬nh. CÃ¡ch lÃ m khÃ¡ Ä‘Æ¡n giáº£n vÃ  dá»… dÃ ng, báº¥t cá»© ai cÅ©ng cÃ³ thá»ƒ thÃ nh cÃ´ng ngay láº§n Ä‘áº§u2.\n" +
                        "\t\t+ 3 cÃ¡ch lÃ m hamburger bÃ² kiá»ƒu Má»¹ ngon nhÆ° ngoÃ i hÃ ng1: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m hamburger bÃ² theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t1.\n" +
                        "\t\t+ 2 CÃ¡ch LÃ m Hamburger BÃ² Cá»±c Ngon KhÃ´ng Thá»ƒ Bá» Qua3: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m hamburger bÃ² theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t", 1));
        foodList.add(new Food(1, "Hamburger heo", "BÃ¡nh mÃ¬",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.burger_heo, null)),
                "Hamburger heo lÃ  má»™t mÃ³n Äƒn ngon vÃ  phá»• biáº¿n, thÆ°á»ng Ä‘Æ°á»£c dÃ¹ng lÃ m bá»¯a sÃ¡ng hoáº·c bá»¯a Äƒn nháº¹. \n\n- DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u Hamburger heo:\n" +
                        "\t\t+ CÃ¡ch lÃ m Hamburger heo cá»±c Ä‘Æ¡n giáº£n chá»‰ trong 3 bÆ°á»›c - PasGo1: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m Hamburger heo kiá»ƒu má»›i lÃ m mÃ³n Äƒn â€œÄ‘á»•i giÃ³â€ cho cáº£ gia Ä‘Ã¬nh. CÃ¡ch lÃ m khÃ¡ Ä‘Æ¡n giáº£n vÃ  dá»… dÃ ng, báº¥t cá»© ai cÅ©ng cÃ³ thá»ƒ thÃ nh cÃ´ng ngay láº§n Ä‘áº§u1.\n" +
                        "\t\t+ 3 cÃ¡ch lÃ m hamburger bÃ² kiá»ƒu Má»¹ ngon nhÆ° ngoÃ i hÃ ng2: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m hamburger bÃ² theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t2.\n" +
                        "\t\t+ 2 CÃ¡ch LÃ m Hamburger BÃ² Cá»±c Ngon KhÃ´ng Thá»ƒ Bá» Qua3: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m hamburger bÃ² theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t", 1));
        foodList.add(new Food(1, "Hamburger gÃ ", "BÃ¡nh mÃ¬",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.burger_ga, null)),
                "Hamburger gÃ  lÃ  má»™t mÃ³n Äƒn ngon vÃ  phá»• biáº¿n, thÆ°á»ng Ä‘Æ°á»£c dÃ¹ng lÃ m bá»¯a sÃ¡ng hoáº·c bá»¯a Äƒn nháº¹. \n\n- DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u Hamburger gÃ :\n" +
                        "\t\t+ CÃ¡ch lÃ m Hamburger gÃ  cá»±c Ä‘Æ¡n giáº£n chá»‰ trong 3 bÆ°á»›c - PasGo1: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m Hamburger gÃ  kiá»ƒu má»›i lÃ m mÃ³n Äƒn â€œÄ‘á»•i giÃ³â€ cho cáº£ gia Ä‘Ã¬nh. CÃ¡ch lÃ m khÃ¡ Ä‘Æ¡n giáº£n vÃ  dá»… dÃ ng, báº¥t cá»© ai cÅ©ng cÃ³ thá»ƒ thÃ nh cÃ´ng ngay láº§n Ä‘áº§u1.\n" +
                        "\t\t+ 3 cÃ¡ch lÃ m hamburger bÃ² kiá»ƒu Má»¹ ngon nhÆ° ngoÃ i hÃ ng2: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m hamburger bÃ² theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t2.\n" +
                        "\t\t+ 2 CÃ¡ch LÃ m Hamburger BÃ² Cá»±c Ngon KhÃ´ng Thá»ƒ Bá» Qua3: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m hamburger bÃ² theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t", 1));

        // region Banh ngot
        foodList.add(new Food(1, "BÃ¡nh Ä‘áº­u xanh cá»‘t dá»«a", "BÃ¡nh ngá»t",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhdauxanh_cotdua, null)),
                "BÃ¡nh Ä‘áº­u xanh cá»‘t dá»«a lÃ  má»™t mÃ³n Äƒn ngon vÃ  phá»• biáº¿n, thÆ°á»ng Ä‘Æ°á»£c dÃ¹ng lÃ m bá»¯a sÃ¡ng hoáº·c bá»¯a Äƒn nháº¹. DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u BÃ¡nh Ä‘áº­u xanh cá»‘t dá»«a:\n" +
                        "\n" +
                        "NguyÃªn liá»‡u:\n" +
                        "\n" +
                        "\t\t+ 200g Ä‘áº­u xanh khÃ´ng vá»\n" +
                        "\t\t+ 120g Ä‘Æ°á»ng\n" +
                        "\t\t+ Má»™t chÃºt muá»‘i\n" +
                        "\t\t+ 1 thÃ¬a dáº§u Äƒn\n" +
                        "\t\t+ 60ml nÆ°á»›c cá»‘t dá»«a1\n" +
                        "\nCÃ¡ch lÃ m:\n" +
                        "\n" +
                        "\t\t+ Äáº­u xanh vo sáº¡ch, sau Ä‘Ã³ ngÃ¢m nÆ°á»›c 2-3 tiáº¿ng cho Ä‘á»— ná»Ÿ má»m1.\n" +
                        "\t\t+ Cho Ä‘á»—, má»™t chÃºt muá»‘i vÃ o ná»“i, sau Ä‘Ã³ cho nÆ°á»›c ngáº­p máº·t Ä‘á»— (cá»¡ 1 Ä‘á»‘t ngÃ³n tay). Báº­t báº¿p vÃ  khÃ´ng cáº§n Ä‘áº­y náº¯p ná»“i, náº¥u cho tá»›i khi Ä‘á»— chÃ­n má»m vÃ  nÆ°á»›c cÅ©ng rÃºt cáº¡n thÃ¬ táº¯t báº¿p1.\n" +
                        "\t\t+ Cho Ä‘á»— vÃ o mÃ¡y xay sinh tá»‘ cÃ¹ng vá»›i nÆ°á»›c cá»‘t dá»«a, xay cho há»—n há»£p nhuyá»…n má»‹n1.\n" +
                        "\t\t+ Äá»• há»—n há»£p vá»«a xay ra cháº£o chá»‘ng dÃ­nh vÃ  báº­t báº¿p nhá» lá»­a, cho thÃªm Ä‘Æ°á»ng, dáº§u Äƒn vÃ  trá»™n Ä‘á»u, xÃ o nhÆ° váº­y cho tá»›i khi bá»™t Ä‘áº­u xanh khÃ´ rÃ¡o lÃ  táº¯t báº¿p1.\n" +
                        "\t\t+ Chuáº©n bá»‹ 1 chiáº¿c khuÃ´n vuÃ´ng, sau Ä‘Ã³ lÃ³t mÃ ng bá»c thá»±c pháº©m vÃ o rá»“i láº¥y 1 lÆ°á»£ng Ä‘áº­u xanh vá»«a Ä‘á»§, trÃºt vÃ o khuÃ´n vÃ  áº¥n Ä‘á»u 4 gÃ³c sao cho pháº³ng Ä‘á»u lÃ  Ä‘Æ°á»£c1.\n" +
                        "\t\t+ Láº¥y bÃ¡nh ra khá»i khuÃ´n, bÃ´i 1 lá»›p má»ng dáº§u Äƒn lÃªn dao rá»“i cáº¯t bÃ¡nh thÃ nh cÃ¡c miáº¿ng vuÃ´ng vá»«a nhá» lÃ  xong rá»“i nhÃ©! Nhá»¯ng chiáº¿c bÃ¡nh nhá» xinh láº¡i bÃ¹i thÆ¡m vá»‹ Ä‘á»— xanh, bÃ©o ngáº­y nÆ°á»›c cá»‘t dá»«a, ngá»t vá»«a pháº£i, Äƒn khÃ´ng bá»‹ ngáº¥y1.", 4));
        foodList.add(new Food(1, "BÃ¡nh matcha", "BÃ¡nh ngá»t",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banh_matcha, null)),
                "BÃ¡nh matcha lÃ  má»™t loáº¡i bÃ¡nh ngá»t vá»›i hÆ°Æ¡ng vá»‹ Ä‘áº·c trÆ°ng tá»« bá»™t trÃ  xanh matcha. DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ¡ch lÃ m bÃ¡nh matcha phá»• biáº¿n:\n" +
                        "\n" +
                        "BÃ¡nh bÃ´ng lan matcha1:\n" +
                        "\n" +
                        "\nNguyÃªn liá»‡u: 100g bá»™t mÃ¬, 50g bá»™t báº¯p, 5g bá»™t ná»Ÿ, 20g bá»™t matcha, 3 quáº£ trá»©ng gÃ , 80g Ä‘Æ°á»ng, 40g bÆ¡ nháº¡t, muá»‘i, vani1.\n" +
                        "\t\t+ CÃ¡ch cháº¿ biáº¿n: Äáº§u tiÃªn, báº¡n cáº§n tÃ¡ch lÃ²ng Ä‘á» vÃ  lÃ²ng tráº¯ng trá»©ng, sau Ä‘Ã³ Ä‘Ã¡nh lÃ²ng Ä‘á» vá»›i 40g Ä‘Æ°á»ng vÃ  vani. Tiáº¿p theo, hÃ²a tan bÆ¡ vÃ  dáº§u Äƒn trong nÆ°á»›c sÃ´i. Khi há»—n há»£p bÆ¡ cÃ²n áº¥m, báº¡n cho bá»™t trÃ  xanh vÃ o vÃ  khuáº¥y Ä‘á»u. Sau cÃ¹ng, báº¡n trá»™n há»—n há»£p bá»™t mÃ¬, bá»™t báº¯p vÃ  bá»™t ná»Ÿ vá»›i nhau vÃ  cho vÃ o há»—n há»£p trÃ  xanh. Tiáº¿p tá»¥c thÃªm há»—n há»£p lÃ²ng Ä‘á» trá»©ng ban Ä‘áº§u vÃ  trá»™n tháº­t Ä‘á»u1.\n" +
                        "\t\t+ BÃ¡nh mousse matcha1:\n" +
                        "\n" +
                        "\nNguyÃªn liá»‡u: VÃ i lÃ¡t bÃ¡nh bÃ´ng lan matcha, 250g whipping cream, 5g bá»™t trÃ  xanh, 60g Ä‘Æ°á»ng, 2 lÃ¡ gelatine1.\n" +
                        "\t\t+ CÃ¡ch cháº¿ biáº¿n: Báº¡n cáº§n pháº£i káº¿t há»£p cÃ¡c nguyÃªn liá»‡u nÃ y Ä‘á»ƒ táº¡o ra má»™t lá»›p mousse má»m máº¡i vÃ  thÆ¡m ngon1.\n" +
                        "\t\t+ BÃ¡nh tiramisu matcha1:\n" +
                        "\n" +
                        "\nNguyÃªn liá»‡u: CÃ¡c nguyÃªn liá»‡u cáº§n thiáº¿t cho loáº¡i bÃ¡nh nÃ y gá»“m cÃ³ matcha, kem mascarpone, vÃ  cÃ¡c loáº¡i nguyÃªn liá»‡u khÃ¡c tÃ¹y thuá»™c vÃ o cÃ¡ch báº¡n muá»‘n lÃ m1.\n" +
                        "\t\t+ CÃ¡ch cháº¿ biáº¿n: Báº¡n sáº½ cáº§n pháº£i káº¿t há»£p cÃ¡c nguyÃªn liá»‡u nÃ y Ä‘á»ƒ táº¡o ra má»™t lá»›p tiramisu má»m máº¡i vÃ  thÆ¡m ngon1.\n" +
                        "\t\t+ BÃ¡nh quy matcha1:\n" +
                        "\n" +
                        "\nNguyÃªn liá»‡u: Báº¡n sáº½ cáº§n cÃ³ bá»™t matcha, bá»™t mÃ¬, vÃ  cÃ¡c nguyÃªn liá»‡u khÃ¡c tÃ¹y thuá»™c vÃ o cÃ¡ch báº¡n muá»‘n lÃ m1.\n" +
                        "\t\t+ CÃ¡ch cháº¿ biáº¿n: Báº¡n sáº½ cáº§n pháº£i káº¿t há»£p cÃ¡c nguyÃªn liá»‡u nÃ y Ä‘á»ƒ táº¡o ra nhá»¯ng chiáº¿c bÃ¡nh quy giÃ²n vÃ  thÆ¡m ngon1.", 4));
        foodList.add(new Food(1, "BÃ¡nh sáº§u riÃªng", "BÃ¡nh ngá»t",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banh_saurieng, null)),
                "BÃ¡nh matcha lÃ  má»™t loáº¡i bÃ¡nh ngá»t cÃ³ hÆ°Æ¡ng vá»‹ Ä‘áº·c trÆ°ng cá»§a bá»™t trÃ  xanh matcha. DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c lÃ m bÃ¡nh matcha:\n" +
                        "\n" +
                        "\t\t+ BÃ¡nh bÃ´ng lan matcha: BÃ¡nh bÃ´ng lan matcha cÃ³ vá»‹ bÃ©o ngá»t, Ä‘áº¯ng nháº¹ cá»§a bá»™t trÃ  xanh vÃ  hÆ°Æ¡ng vá»‹ thÆ¡m ngon cá»§a trá»©ng. BÃ¡nh bÃ´ng lan matcha má»m má»‹n, thÆ¡m ngon cháº¯c cháº¯n sáº½ lÃ m cÃ¡c bÃ© thÃ­ch mÃª1.\n" +
                        "\t\t+ BÃ¡nh mousse matcha: BÃ¡nh mousse matcha lÃ  sá»± káº¿t há»£p cá»±c ká»³ sÃ¡ng táº¡o vÃ  má»›i láº¡ tá»« nhá»¯ng nguyÃªn liá»‡u Ä‘Æ¡n giáº£n. Bá»™t trÃ  xanh Ä‘áº¯ng nháº¹, thÆ¡m dá»‹u káº¿t há»£p cÃ¹ng whipping cream bÃ©o ngáº­y cháº¯c cháº¯n sáº½ khiáº¿n cho nhá»¯ng tÃ¢m há»“n yÃªu Ä‘á»“ ngá»t thÃ­ch thÃº1.\n" +
                        "\t\t+ BÃ¡nh Chocopie Orion Matcha TrÃ  Xanh: Lá»›p bÃ¡nh xá»‘p má»‹n vá»›i sÃ´ cÃ´ la cháº£y ngá»t ngÃ o bÃªn ngoÃ i Ä‘áº¿n lá»›p nhÃ¢n marshmallow vá»‹ matcha thanh háº¥p dáº«n á»Ÿ bÃªn trong2.", 4));
        foodList.add(new Food(1, "BÃ¡nh bÃ´ng lan cuá»™n", "BÃ¡nh ngá»t",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_cuon, null)),
                "BÃ¡nh bÃ´ng lan cuá»™n lÃ  má»™t mÃ³n Äƒn ngon vÃ  phá»• biáº¿n, thÆ°á»ng Ä‘Æ°á»£c dÃ¹ng lÃ m bá»¯a sÃ¡ng hoáº·c bá»¯a Äƒn nháº¹. DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u BÃ¡nh bÃ´ng lan cuá»™n:\n" +
                        "\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh bÃ´ng lan cuá»™n Ä‘Æ¡n giáº£n1: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m bÃ¡nh bÃ´ng lan cuá»™n kiá»ƒu má»›i lÃ m mÃ³n Äƒn â€œÄ‘á»•i giÃ³â€ cho cáº£ gia Ä‘Ã¬nh. CÃ¡ch lÃ m khÃ¡ Ä‘Æ¡n giáº£n vÃ  dá»… dÃ ng, báº¥t cá»© ai cÅ©ng cÃ³ thá»ƒ thÃ nh cÃ´ng ngay láº§n Ä‘áº§u1.\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh bÃ´ng lan cuá»™n Vanilla Cake Roll Recipe1: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh bÃ´ng lan cuá»™n theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t1.\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh bÃ´ng lan cuá»™n kem tÆ°Æ¡i hoa quáº£2: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh bÃ´ng lan cuá»™n theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t2.\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh bÃ´ng lan cuá»™n hoa cÃºc1: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh bÃ´ng lan cuá»™n theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t1", 4));
        foodList.add(new Food(1, "BÃ¡nh sáº§u riÃªng", "BÃ¡nh ngá»t",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banh_saurieng, null)),
                "BÃ¡nh sáº§u riÃªng lÃ  má»™t loáº¡i bÃ¡nh ngá»t cÃ³ hÆ°Æ¡ng vá»‹ Ä‘áº·c trÆ°ng cá»§a sáº§u riÃªng. DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ thÃ´ng tin vá» cÃ¡c loáº¡i bÃ¡nh sáº§u riÃªng phá»• biáº¿n:\n" +
                        "\n" +
                        "\t\t+ BÃ¡nh Sáº§u RiÃªng TBT: BÃ¡nh Ä‘Æ°á»£c lÃ m báº±ng sáº§u riÃªng Ri6 tÆ°Æ¡i nguyÃªn cháº¥t, khÃ´ng sá»­ dá»¥ng sáº§u kem sáº§u pha trá»™n. Vá» bÃ¡nh dai má»m má»‹n, khÃ´ng bá»‹ bá»™t bá»Ÿ khi Äƒn. Lá»›p kem láº¡nh thÆ¡m bÃ©o, xen láº«n nhÃ¢n sáº§u ngá»t dá»‹u1.\n" +
                        "\t\t+ BÃ¡nh Sáº§u RiÃªng: ChuyÃªn sá»‰ & láº» bÃ¡nh sáº§u riÃªng ngÃ n lá»›p, bÃ¡nh crepe. Vá»›i phÆ°Æ¡ng chÃ¢m luÃ´n Ä‘áº·t cháº¥t lÆ°á»£ng lÃªn hÃ ng Ä‘áº§u, chÃºng tÃ´i tá»± hÃ o mang Ä‘áº¿n nhá»¯ng sáº£n pháº©m tá»‘t nháº¥t cho khÃ¡ch hÃ ng2.\n" +
                        "\t\t+ BÃ¡nh Sáº§u RiÃªng ChiÃªn: BÃ¡nh Sáº§u RiÃªng ChiÃªn giÃ²n rá»¥m thÆ¡m ngon tuyá»‡t cÃº mÃ¨o3.\n" +
                        "\t\t+ BÃ¡nh Trung Thu NhÃ¢n Sáº§u RiÃªng: BÃ¡nh Trung Thu NhÃ¢n Sáº§u RiÃªng thÆ¡m ná»©c mÅ©i4", 4));
        foodList.add(new Food(1, "BÃ¡nh bÃ´ng lan cuá»™n", "BÃ¡nh ngá»t",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_cuon, null)),
                "BÃ¡nh bÃ´ng lan cuá»™n lÃ  má»™t mÃ³n Äƒn ngon vÃ  phá»• biáº¿n, thÆ°á»ng Ä‘Æ°á»£c dÃ¹ng lÃ m bá»¯a sÃ¡ng hoáº·c bá»¯a Äƒn nháº¹. DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u BÃ¡nh bÃ´ng lan cuá»™n:\n" +
                        "\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh bÃ´ng lan cuá»™n Ä‘Æ¡n giáº£n1: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m bÃ¡nh bÃ´ng lan cuá»™n kiá»ƒu má»›i lÃ m mÃ³n Äƒn â€œÄ‘á»•i giÃ³â€ cho cáº£ gia Ä‘Ã¬nh. CÃ¡ch lÃ m khÃ¡ Ä‘Æ¡n giáº£n vÃ  dá»… dÃ ng, báº¥t cá»© ai cÅ©ng cÃ³ thá»ƒ thÃ nh cÃ´ng ngay láº§n Ä‘áº§u1.\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh bÃ´ng lan cuá»™n Vanilla Cake Roll Recipe1: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh bÃ´ng lan cuá»™n theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t1.\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh bÃ´ng lan cuá»™n kem tÆ°Æ¡i hoa quáº£2: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh bÃ´ng lan cuá»™n theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t2.\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh bÃ´ng lan cuá»™n hoa cÃºc1: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh bÃ´ng lan cuá»™n theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t1", 4));
        foodList.add(new Food(1, "BÃ¡nh bÃ´ng lan socola", "BÃ¡nh ngá»t",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_socola, null)),
                "BÃ¡nh bÃ´ng lan socola lÃ  má»™t mÃ³n Äƒn ngon vÃ  phá»• biáº¿n, thÆ°á»ng Ä‘Æ°á»£c dÃ¹ng lÃ m bá»¯a sÃ¡ng hoáº·c bá»¯a Äƒn nháº¹. DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u BÃ¡nh bÃ´ng lan socola:\n" +
                        "\n" +
                        "\t\t+ CÃ¡ch lÃ m BÃ¡nh BÃ´ng Lan Socola cá»±c Ä‘Æ¡n giáº£n chá»‰ trong 3 bÆ°á»›c - PasGo1: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m BÃ¡nh BÃ´ng Lan Socola kiá»ƒu má»›i lÃ m mÃ³n Äƒn â€œÄ‘á»•i giÃ³â€ cho cáº£ gia Ä‘Ã¬nh. CÃ¡ch lÃ m khÃ¡ Ä‘Æ¡n giáº£n vÃ  dá»… dÃ ng, báº¥t cá»© ai cÅ©ng cÃ³ thá»ƒ thÃ nh cÃ´ng ngay láº§n Ä‘áº§u1.\n" +
                        "\t\t+ CÃ¡ch lÃ m BÃ¡nh BÃ´ng Lan Socola TÆ°Æ¡i | Chocolate Cake | Chá»‹ MÃ­a2: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m BÃ¡nh BÃ´ng Lan Socola TÆ°Æ¡i theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t2.\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh bÃ´ng lan socola cá»±c Ä‘Æ¡n giáº£n (chocolate sponge cake â€¦3: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh bÃ´ng lan socola theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t3.\n" +
                        "\t\t+ BÃNH BÃ”NG LAN SOCOLA â€“ CÃ´ng ty TNHH Äáº¡i Hiá»n TÃ¢m4: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh bÃ´ng lan socola theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t4", 4));
        foodList.add(new Food(1, "BÃ¡nh bÃ´ng lan trá»©ng muá»‘i", "BÃ¡nh ngá»t",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_trungmuoi, null)),
                "BÃ¡nh bÃ´ng lan trá»©ng muá»‘i lÃ  má»™t mÃ³n Äƒn ngon vÃ  phá»• biáº¿n, thÆ°á»ng Ä‘Æ°á»£c dÃ¹ng lÃ m bá»¯a sÃ¡ng hoáº·c bá»¯a Äƒn nháº¹. DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u BÃ¡nh bÃ´ng lan trá»©ng muá»‘i:\n" +
                        "\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh bÃ´ng lan trá»©ng muá»‘i (báº±ng ná»“i cÆ¡m Ä‘iá»‡n hoáº·c lÃ² nÆ°á»›ng)1: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m bÃ¡nh bÃ´ng lan trá»©ng muá»‘i kiá»ƒu má»›i lÃ m mÃ³n Äƒn â€œÄ‘á»•i giÃ³â€ cho cáº£ gia Ä‘Ã¬nh. CÃ¡ch lÃ m khÃ¡ Ä‘Æ¡n giáº£n vÃ  dá»… dÃ ng, báº¥t cá»© ai cÅ©ng cÃ³ thá»ƒ thÃ nh cÃ´ng ngay láº§n Ä‘áº§u1.\n" +
                        "\t\t+ CÃ¡ch LÃ m BÃ¡nh BÃ´ng Lan Trá»©ng Muá»‘i ÄÆ¡n Giáº£n Táº¡i NhÃ 2: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh bÃ´ng lan trá»©ng muá»‘i theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t", 4));
        foodList.add(new Food(1, "BÃ¡nh bÃ´ng lan trá»©ng muá»‘i", "BÃ¡nh ngá»t",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_trungmuoi, null)),
                "BÃ¡nh bÃ´ng lan trá»©ng muá»‘i lÃ  má»™t mÃ³n Äƒn ngon vÃ  phá»• biáº¿n, thÆ°á»ng Ä‘Æ°á»£c dÃ¹ng lÃ m bá»¯a sÃ¡ng hoáº·c bá»¯a Äƒn nháº¹. DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u BÃ¡nh bÃ´ng lan trá»©ng muá»‘i:\n" +
                        "\n" +
                        "\t\t+ CÃ¡ch lÃ m bÃ¡nh bÃ´ng lan trá»©ng muá»‘i (báº±ng ná»“i cÆ¡m Ä‘iá»‡n hoáº·c lÃ² nÆ°á»›ng)1: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m bÃ¡nh bÃ´ng lan trá»©ng muá»‘i kiá»ƒu má»›i lÃ m mÃ³n Äƒn â€œÄ‘á»•i giÃ³â€ cho cáº£ gia Ä‘Ã¬nh. CÃ¡ch lÃ m khÃ¡ Ä‘Æ¡n giáº£n vÃ  dá»… dÃ ng, báº¥t cá»© ai cÅ©ng cÃ³ thá»ƒ thÃ nh cÃ´ng ngay láº§n Ä‘áº§u1.\n" +
                        "\t\t+ CÃ¡ch LÃ m BÃ¡nh BÃ´ng Lan Trá»©ng Muá»‘i ÄÆ¡n Giáº£n Táº¡i NhÃ 2: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh bÃ´ng lan trá»©ng muá»‘i theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t", 4));
        foodList.add(new Food(1, "BÃ¡nh su kem", "BÃ¡nh ngá»t",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhsukem, null)),
                "BÃ¡nh su kem, cÃ²n Ä‘Æ°á»£c gá»i lÃ  Choux Ã  la crÃ¨me, lÃ  má»™t loáº¡i bÃ¡nh ngá»t cÃ³ nguá»“n gá»‘c tá»« nÆ°á»›c PhÃ¡p. BÃ¡nh su kem cÃ³ Ä‘á»™ má»m dáº»o, thÆ¡m ngáº­y Ä‘áº§y lÃ´i cuá»‘n1. DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u bÃ¡nh su kem:\n" +
                        "\n" +
                        "\t\t+ CÃ¡ch lÃ m BÃ¡nh Su Kem (Choux Ã  la crÃ¨me) Ngon BÃ©o Ngáº­y â€“ bTaskee1: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m bÃ¡nh su kem kiá»ƒu má»›i lÃ m mÃ³n Äƒn â€œÄ‘á»•i giÃ³â€ cho cáº£ gia Ä‘Ã¬nh. CÃ¡ch lÃ m khÃ¡ Ä‘Æ¡n giáº£n vÃ  dá»… dÃ ng, báº¥t cá»© ai cÅ©ng cÃ³ thá»ƒ thÃ nh cÃ´ng ngay láº§n Ä‘áº§u1.\n" +
                        "\t\t+ LÃ m BÃ¡nh Su Kem Vá»›i CÃ´ng Thá»©c Dá»… ThÃ nh CÃ´ng Ngay Láº§n Äáº§u | Choux Pastry Recipe | Chá»‹ MÃ­a2: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh su kem theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t2.\n" +
                        "\t\t+ BÃ¡nh Su kem (Choux Ã  la CrÃ¨me) â€“ Demo & Má»™t sá»‘ lÆ°u Ã½ khi lÃ m Choux3: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m bÃ¡nh su kem theo cÃ´ng thá»©c nhÆ° tháº¿ nÃ o ngon nháº¥t", 4));

        // region Com suon
        foodList.add(new Food(1, "CÆ¡m sÆ°á»n trá»©ng", "CÆ¡m sÆ°á»n",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_trung, null)),
                "CÆ¡m sÆ°á»n trá»©ng lÃ  má»™t mÃ³n Äƒn truyá»n thá»‘ng cá»§a Viá»‡t Nam, thÆ°á»ng Ä‘Æ°á»£c phá»¥c vá»¥ trong bá»¯a Äƒn hÃ ng ngÃ y. DÆ°á»›i Ä‘Ã¢y lÃ  cÃ¡ch lÃ m cÆ¡m sÆ°á»n trá»©ng táº¡i nhÃ 1:\n" +
                        "\n" +
                        "NguyÃªn liá»‡u:\n" +
                        "\n" +
                        "\t\t+ 1kg sÆ°á»n cá»‘t láº¿t\n" +
                        "\t\t+ 400g thá»‹t bÄƒm\n" +
                        "\t\t+ 3 quáº£ trá»©ng gÃ \n" +
                        "\t\t+ HÃ nh tÃ­m, hÃ nh lÃ¡, hÃ nh tÃ¢y, tá»i\n" +
                        "\t\t+ Gia vá»‹: Máº­t ong, sá»¯a tÆ°Æ¡i khÃ´ng Ä‘Æ°á»ng, sá»¯a Ä‘áº·c, dáº§u hÃ o, giáº¥m Äƒn, dáº§u Äƒn, nÆ°á»›c tÆ°Æ¡ng, nÆ°á»›c máº¯m, Ä‘Æ°á»ng, muá»‘i, háº¡t nÃªm, tiÃªu xay1\n" +
                        "\nCÃ¡ch lÃ m:\n" +
                        "\n" +
                        "\t\t+ SÆ¡ cháº¿ sÆ°á»n heo vÃ  Æ°á»›p sÆ°á»n: Rá»­a sáº¡ch sÆ°á»n heo, sau Ä‘Ã³ dÃ¹ng bÃºa Ä‘áº­p nháº¹ nhÃ ng lÃªn 2 máº·t thá»‹t Ä‘á»ƒ thá»‹t má»m vÃ  dá»… ngáº¥m gia vá»‹ hÆ¡n. Vá»›i sÆ°á»n, báº¡n Æ°á»›p sÆ°á»n nÆ°á»›ng nhÆ° sau: 1 muá»—ng canh Ä‘Æ°á»ng, 2 muá»—ng canh nÆ°á»›c tÆ°Æ¡ng, 2 muá»—ng canh dáº§u hÃ o, 2 muá»—ng canh nÆ°á»›c máº¯m, 1 muá»—ng canh máº­t ong. Sau Ä‘Ã³ thÃªm vÃ o 100ml sá»¯a tÆ°Æ¡i khÃ´ng Ä‘Æ°á»ng, 2 muá»—ng canh sá»¯a Ä‘áº·c, 1 muá»—ng canh hÃ nh bÄƒm, 1 muá»—ng canh tá»i bÄƒm, 1 muá»—ng cÃ  phÃª tiÃªu xay, Â½ muá»—ng canh dáº§u Äƒn, â…“ muá»—ng cÃ  phÃª muá»‘i. Báº¡n trá»™n Ä‘á»u táº¥t cáº£ lÃªn cÃ¹ng thá»‹t sÆ°á»n. Sau Ä‘Ã³ cho pháº§n thá»‹t Ä‘Ã£ Æ°á»›p vÃ o há»™p nhá»±a Ä‘áº­y náº¯p kÃ­n1.\n" +
                        "\t\t+ SÆ¡ cháº¿ cÃ¡c nguyÃªn liá»‡u khÃ¡c: HÃ nh tÃ¢y thÃ¡i má»ng. Gá»«ng, tá»i, sáº£ Ä‘áº­p dáº­p. CÃ  chua cáº¯t háº¡t lá»±u. CÃ  rá»‘t tá»‰a hoa1.\n" +
                        "\t\t+ Pha nÆ°á»›c máº¯m: Trong má»™t cÃ¡i chÃ©n nhá» báº¡n cho vÃ o: 3 muá»—ng canh nÆ°á»›c máº¯m ngon (hoáº·c nÆ°á»›c máº¯m cháº¥m), sau Ä‘Ã³ cho tiáº¿p vÃ o chÃ©n: 2 muá»—ng canh dáº§u hÃ o (hoáº·c dáº§u cÃ¡), tiáº¿p theo lÃ : 3 muá»—ng canh nÆ°á»›c lá»c (hoáº·c nÆ°á»›c cá»‘t dá»«a), cuá»‘i cÃ¹ng báº¡n cho vÃ o chÃ©n: 3 muá»—ng canh Ä‘Æ°á»ng vÃ  khuáº¥y Ä‘á»u1.\n" +
                        "\t\t+ NÆ°á»›ng thá»‹t: Báº¡n cho tá»« tá»« pháº§n thá»‹t Ä‘Ã£ Ä‘Æ°á»£c Æ°á»›p vÃ o cháº£o vÃ  Ä‘á»ƒ lá»­a vá»«a pháº£i1.\n" +
                        "\t\t+ LÃ m má»¡ hÃ nh vÃ  tÃ³p má»¡: Báº¡n cho tá»« tá»« pháº§n thá»‹t Ä‘Ã£ Ä‘Æ°á»£c Æ°á»›p vÃ o cháº£o vÃ  Ä‘á»ƒ lá»­a vá»«a pháº£i1.\n" +
                        "\t\t+ ChiÃªn trá»©ng: Báº¡n cho tá»« tá»« pháº§n thá»‹t Ä‘Ã£ Ä‘Æ°á»£c Æ°á»›p vÃ o cháº£o vÃ  Ä‘á»ƒ lá»­a vá»«a pháº£i1.", 3));
        foodList.add(new Food(1, "CÆ¡m sÆ°á»n bÃ¬ cháº£", "CÆ¡m sÆ°á»n",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_bi_cha, null)),
                "CÆ¡m sÆ°á»n bÃ¬ cháº£ lÃ  má»™t mÃ³n Äƒn truyá»n thá»‘ng cá»§a ngÆ°á»i Viá»‡t, Ä‘áº·c biá»‡t lÃ  á»Ÿ miá»n Nam. MÃ³n nÃ y bao gá»“m cÆ¡m táº¥m (cÆ¡m gáº¡o táº¥m), sÆ°á»n heo nÆ°á»›ng, bÃ¬ (da heo giÃ²n) vÃ  cháº£ (thÆ°á»ng lÃ  cháº£ trá»©ng hoáº·c cháº£ háº¥p). " +
                        "\n+ CÆ¡m táº¥m Ä‘Æ°á»£c náº¥u tá»« gáº¡o táº¥m, loáº¡i gáº¡o vá»¥n vá»¡ nhá», mang láº¡i hÆ°Æ¡ng vá»‹ Ä‘áº·c trÆ°ng. SÆ°á»n heo Ä‘Æ°á»£c Æ°á»›p vá»›i cÃ¡c loáº¡i gia vá»‹ nhÆ° nÆ°á»›c máº¯m, Ä‘Æ°á»ng, tá»iâ€¦ sau Ä‘Ã³ Ä‘Æ°á»£c nÆ°á»›ng cho tá»›i khi chÃ­n tá»›i. \n+ " +
                        "\t\t+ BÃ¬ Ä‘Æ°á»£c lÃ m tá»« da heo, thÆ°á»ng Ä‘Æ°á»£c luá»™c vÃ  xáº¯t nhá». Cháº£ cÃ³ thá»ƒ lÃ  cháº£ trá»©ng hoáº·c cháº£ háº¥p, tÃ¹y vÃ o sá»Ÿ thÃ­ch cá»§a tá»«ng ngÆ°á»i. \n+ " +
                        "\t\t+ MÃ³n Äƒn thÆ°á»ng Ä‘Æ°á»£c dÃ¹ng kÃ¨m vá»›i nÆ°á»›c máº¯m pha chua ngá»t vÃ  rau sá»‘ng. CÆ¡m sÆ°á»n bÃ¬ cháº£ lÃ  má»™t mÃ³n Äƒn ngon vÃ  bá»• dÆ°á»¡ng, ráº¥t phÃ¹ há»£p cho bá»¯a trÆ°a hoáº·c bá»¯a tá»‘i. \n+ " +
                        "\t\t+ ChÃºc báº¡n thÆ°á»Ÿng thá»©c ngon miá»‡ng! \uD83D\uDE0A", 3));
        foodList.add(new Food(1, "CÆ¡m sÆ°á»n nÆ°á»›ng", "CÆ¡m sÆ°á»n",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_nuong, null)),
                "CÆ¡m sÆ°á»n nÆ°á»›ng áº£ lÃ  má»™t mÃ³n Äƒn truyá»n thá»‘ng cá»§a ngÆ°á»i Viá»‡t, Ä‘áº·c biá»‡t lÃ  á»Ÿ miá»n Nam. MÃ³n nÃ y bao gá»“m:\n" +
                        "\n" +
                        "\t\t+ CÆ¡m táº¥m: CÆ¡m Ä‘Æ°á»£c náº¥u tá»« gáº¡o táº¥m, loáº¡i gáº¡o vá»¥n vá»¡ nhá», mang láº¡i hÆ°Æ¡ng vá»‹ Ä‘áº·c trÆ°ng.\n" +
                        "\t\t+ SÆ°á»n heo nÆ°á»›ng: SÆ°á»n heo Ä‘Æ°á»£c Æ°á»›p vá»›i cÃ¡c loáº¡i gia vá»‹ nhÆ° nÆ°á»›c máº¯m, Ä‘Æ°á»ng, tá»iâ€¦ sau Ä‘Ã³ Ä‘Æ°á»£c nÆ°á»›ng cho tá»›i khi chÃ­n tá»›i.\n" +
                        "\t\t+ BÃ¬ (da heo giÃ²n): BÃ¬ Ä‘Æ°á»£c lÃ m tá»« da heo, thÆ°á»ng Ä‘Æ°á»£c luá»™c vÃ  xáº¯t nhá».\n" +
                        "\t\t+ Cháº£: Cháº£ cÃ³ thá»ƒ lÃ  cháº£ trá»©ng hoáº·c cháº£ háº¥p, tÃ¹y vÃ o sá»Ÿ thÃ­ch cá»§a tá»«ng ngÆ°á»i.", 3));
        foodList.add(new Food(1, "CÆ¡m sÆ°á»n ram", "CÆ¡m sÆ°á»n",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_ram, null)),
                "CÆ¡m sÆ°á»n ram lÃ  má»™t mÃ³n Äƒn truyá»n thá»‘ng cá»§a Viá»‡t Nam, Ä‘áº·c biá»‡t phá»• biáº¿n á»Ÿ miá»n Nam. MÃ³n nÃ y bao gá»“m cÆ¡m tráº¯ng Ä‘Æ°á»£c phá»¥c vá»¥ cÃ¹ng vá»›i sÆ°á»n heo Ä‘Ã£ Ä‘Æ°á»£c Æ°á»›p vá»›i cÃ¡c loáº¡i gia vá»‹ vÃ  sau Ä‘Ã³ Ä‘Æ°á»£c ram cho tá»›i khi chÃ­n tá»›i. DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u CÆ¡m sÆ°á»n ram:\n" +
                        "\n" +
                        "\t\t+ CÃ¡ch lÃ m mÃ³n sÆ°á»n ram: 6 cÃ´ng thá»©c ram sÆ°á»n ngon khá»i chÃª1: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m sÆ°á»n ram máº·n ngá»t. SÆ°á»n non Ä‘Æ°á»£c Æ°á»›p vá»›i hÃ nh tÃ­m, á»›t, Ä‘Æ°á»ng, bá»™t nÃªm, bá»™t ngá»t, nÆ°á»›c máº¯m, nÆ°á»›c mÃ u trong khoáº£ng 20 phÃºt. Sau Ä‘Ã³, sÆ°á»n Ä‘Æ°á»£c chiÃªn trong cháº£o dáº§u nÃ³ng. Khi sÆ°á»n chÃ­n, lá»­a Ä‘Æ°á»£c tÄƒng lá»›n Ä‘á»ƒ sÆ°á»n lÃªn mÃ u1.\n" +
                        "\t\t+ CÃ¡ch LÃ m SÆ°á»n Ram Máº·n Ngá»t Ngon Má»m, Äáº­m ÄÃ  ÄÆ°a CÆ¡m2: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m sÆ°á»n ram máº·n ngá»t. SÆ°á»n non Ä‘Æ°á»£c Æ°á»›p vá»›i hÃ nh tÃ­m, tá»i, nÆ°á»›c máº¯m, dáº§u hÃ o, bá»™t nÃªm, tiÃªu vÃ  nÆ°á»›c hÃ ng. Sau Ä‘Ã³, sÆ°á»n Ä‘Æ°á»£c chiÃªn trong cháº£o dáº§u nÃ³ng. Khi miáº¿ng sÆ°á»n Ä‘Ã£ ráº¥t má»m vÃ  tháº¥m gia vá»‹ máº·n ngá»t Ä‘áº­m Ä‘Ã , thÃªm hÃ nh lÃ¡ vÃ o cháº£o vÃ  táº¯t báº¿p2.", 3));
        foodList.add(new Food(1, "CÆ¡m sÆ°á»n bÃ¬ cháº£", "CÆ¡m sÆ°á»n",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_bi_cha, null)),
                "CÆ¡m sÆ°á»n bÃ¬ cháº£ lÃ  má»™t mÃ³n Äƒn truyá»n thá»‘ng cá»§a ngÆ°á»i Viá»‡t, Ä‘áº·c biá»‡t lÃ  á»Ÿ miá»n Nam. MÃ³n nÃ y bao gá»“m cÆ¡m táº¥m (cÆ¡m gáº¡o táº¥m), sÆ°á»n heo nÆ°á»›ng, bÃ¬ (da heo giÃ²n) vÃ  cháº£ (thÆ°á»ng lÃ  cháº£ trá»©ng hoáº·c cháº£ háº¥p). \\n+ CÆ¡m táº¥m Ä‘Æ°á»£c náº¥u tá»« gáº¡o táº¥m, loáº¡i gáº¡o vá»¥n vá»¡ nhá», mang láº¡i hÆ°Æ¡ng vá»‹ Ä‘áº·c trÆ°ng. SÆ°á»n heo Ä‘Æ°á»£c Æ°á»›p vá»›i cÃ¡c loáº¡i gia vá»‹ nhÆ° nÆ°á»›c máº¯m, Ä‘Æ°á»ng, tá»iâ€¦ sau Ä‘Ã³ Ä‘Æ°á»£c nÆ°á»›ng cho tá»›i khi chÃ­n tá»›i. \\n+ \" +\n" +
                        "\"BÃ¬ Ä‘Æ°á»£c lÃ m tá»« da heo, thÆ°á»ng Ä‘Æ°á»£c luá»™c vÃ  xáº¯t nhá». Cháº£ cÃ³ thá»ƒ lÃ  cháº£ trá»©ng hoáº·c cháº£ háº¥p, tÃ¹y vÃ o sá»Ÿ thÃ­ch cá»§a tá»«ng ngÆ°á»i. \\n+ \" +\n" +
                        "\"MÃ³n Äƒn thÆ°á»ng Ä‘Æ°á»£c dÃ¹ng kÃ¨m vá»›i nÆ°á»›c máº¯m pha chua ngá»t vÃ  rau sá»‘ng. CÆ¡m sÆ°á»n bÃ¬ cháº£ lÃ  má»™t mÃ³n Äƒn ngon vÃ  bá»• dÆ°á»¡ng, ráº¥t phÃ¹ há»£p cho bá»¯a trÆ°a hoáº·c bá»¯a tá»‘i. \\n+ \" +\n" +
                        "\"ChÃºc báº¡n thÆ°á»Ÿng thá»©c ngon miá»‡ng! \\uD83D\\uDE0A", 3));
        foodList.add(new Food(1, "CÆ¡m sÆ°á»n nÆ°á»›ng", "CÆ¡m sÆ°á»n",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_nuong, null)),
                "CÆ¡m sÆ°á»n nÆ°á»›ng áº£ lÃ  má»™t mÃ³n Äƒn truyá»n thá»‘ng cá»§a ngÆ°á»i Viá»‡t, Ä‘áº·c biá»‡t lÃ  á»Ÿ miá»n Nam. MÃ³n nÃ y bao gá»“m:\\n\" +\n" +
                        "\"\\n\" +\n" +
                        "\"\\t\\t+ CÆ¡m táº¥m: CÆ¡m Ä‘Æ°á»£c náº¥u tá»« gáº¡o táº¥m, loáº¡i gáº¡o vá»¥n vá»¡ nhá», mang láº¡i hÆ°Æ¡ng vá»‹ Ä‘áº·c trÆ°ng.\\n\" +\n" +
                        "\"\\t\\t+ SÆ°á»n heo nÆ°á»›ng: SÆ°á»n heo Ä‘Æ°á»£c Æ°á»›p vá»›i cÃ¡c loáº¡i gia vá»‹ nhÆ° nÆ°á»›c máº¯m, Ä‘Æ°á»ng, tá»iâ€¦ sau Ä‘Ã³ Ä‘Æ°á»£c nÆ°á»›ng cho tá»›i khi chÃ­n tá»›i.\\n\" +\n" +
                        "\"\\t\\t+ BÃ¬ (da heo giÃ²n): BÃ¬ Ä‘Æ°á»£c lÃ m tá»« da heo, thÆ°á»ng Ä‘Æ°á»£c luá»™c vÃ  xáº¯t nhá».\\n\" +\n" +
                        "\"\\t\\t+ Cháº£: Cháº£ cÃ³ thá»ƒ lÃ  cháº£ trá»©ng hoáº·c cháº£ háº¥p, tÃ¹y vÃ o sá»Ÿ thÃ­ch cá»§a tá»«ng ngÆ°á»i.", 3));
        foodList.add(new Food(1, "CÆ¡m sÆ°á»n ram", "CÆ¡m sÆ°á»n",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_ram, null)),
                "CÆ¡m sÆ°á»n ram lÃ  má»™t mÃ³n Äƒn truyá»n thá»‘ng cá»§a Viá»‡t Nam, Ä‘áº·c biá»‡t phá»• biáº¿n á»Ÿ miá»n Nam. MÃ³n nÃ y bao gá»“m cÆ¡m tráº¯ng Ä‘Æ°á»£c phá»¥c vá»¥ cÃ¹ng vá»›i sÆ°á»n heo Ä‘Ã£ Ä‘Æ°á»£c Æ°á»›p vá»›i cÃ¡c loáº¡i gia vá»‹ vÃ  sau Ä‘Ã³ Ä‘Æ°á»£c ram cho tá»›i khi chÃ­n tá»›i. DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u CÆ¡m sÆ°á»n ram:\\n\" +\n" +
                        "\"\\n\" +\n" +
                        "\"\\t\\t+ CÃ¡ch lÃ m mÃ³n sÆ°á»n ram: 6 cÃ´ng thá»©c ram sÆ°á»n ngon khá»i chÃª1: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m sÆ°á»n ram máº·n ngá»t. SÆ°á»n non Ä‘Æ°á»£c Æ°á»›p vá»›i hÃ nh tÃ­m, á»›t, Ä‘Æ°á»ng, bá»™t nÃªm, bá»™t ngá»t, nÆ°á»›c máº¯m, nÆ°á»›c mÃ u trong khoáº£ng 20 phÃºt. Sau Ä‘Ã³, sÆ°á»n Ä‘Æ°á»£c chiÃªn trong cháº£o dáº§u nÃ³ng. Khi sÆ°á»n chÃ­n, lá»­a Ä‘Æ°á»£c tÄƒng lá»›n Ä‘á»ƒ sÆ°á»n lÃªn mÃ u1.\\n\" +\n" +
                        "\"\\t\\t+ CÃ¡ch LÃ m SÆ°á»n Ram Máº·n Ngá»t Ngon Má»m, Äáº­m ÄÃ  ÄÆ°a CÆ¡m2: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m sÆ°á»n ram máº·n ngá»t. SÆ°á»n non Ä‘Æ°á»£c Æ°á»›p vá»›i hÃ nh tÃ­m, tá»i, nÆ°á»›c máº¯m, dáº§u hÃ o, bá»™t nÃªm, tiÃªu vÃ  nÆ°á»›c hÃ ng. Sau Ä‘Ã³, sÆ°á»n Ä‘Æ°á»£c chiÃªn trong cháº£o dáº§u nÃ³ng. Khi miáº¿ng sÆ°á»n Ä‘Ã£ ráº¥t má»m vÃ  tháº¥m gia vá»‹ máº·n ngá»t Ä‘áº­m Ä‘Ã , thÃªm hÃ nh lÃ¡ vÃ o cháº£o vÃ  táº¯t báº¿p2.", 3));
        foodList.add(new Food(1, "CÆ¡m sÆ°á»n xÃ o chua ngá»t", "CÆ¡m sÆ°á»n",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_chuangot, null)),
                "CÆ¡m sÆ°á»n xÃ o chua ngá»t lÃ  má»™t mÃ³n Äƒn truyá»n thá»‘ng cá»§a Viá»‡t Nam, Ä‘áº·c biá»‡t phá»• biáº¿n á»Ÿ miá»n Nam. MÃ³n nÃ y bao gá»“m cÆ¡m tráº¯ng Ä‘Æ°á»£c phá»¥c vá»¥ cÃ¹ng vá»›i sÆ°á»n heo Ä‘Ã£ Ä‘Æ°á»£c Æ°á»›p vá»›i cÃ¡c loáº¡i gia vá»‹ vÃ  sau Ä‘Ã³ Ä‘Æ°á»£c xÃ o chung vá»›i nÆ°á»›c sá»‘t chua ngá»t. DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ´ng thá»©c náº¥u CÆ¡m sÆ°á»n xÃ o chua ngá»t:\n" +
                        "\n" +
                        "\t\t+ 6 CÃ¡ch LÃ m SÆ°á»n XÃ o Chua Ngá»t Äáº­m ÄÃ , Háº¥p Dáº«n, Ngon ÄÃºng Äiá»‡u1: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m sÆ°á»n xÃ o chua ngá»t máº·n ngá»t. SÆ°á»n non Ä‘Æ°á»£c Æ°á»›p vá»›i hÃ nh tÃ­m, á»›t, Ä‘Æ°á»ng, bá»™t nÃªm, bá»™t ngá»t, nÆ°á»›c máº¯m, nÆ°á»›c mÃ u trong khoáº£ng 20 phÃºt. Sau Ä‘Ã³, sÆ°á»n Ä‘Æ°á»£c chiÃªn trong cháº£o dáº§u nÃ³ng. Khi sÆ°á»n chÃ­n, lá»­a Ä‘Æ°á»£c tÄƒng lá»›n Ä‘á»ƒ sÆ°á»n lÃªn mÃ u1.\n" +
                        "\t\t+ CÃ¡ch lÃ m sÆ°á»n xÃ o chua ngá»t ngon báº¥t báº¡i, vÃ´ cÃ¹ng Ä‘Æ¡n giáº£n2: CÃ´ng thá»©c nÃ y giá»›i thiá»‡u cÃ¡ch lÃ m sÆ°á»n xÃ o chua ngá»t. SÆ°á»n non Ä‘Æ°á»£c Æ°á»›p vá»›i hÃ nh tÃ­m, tá»i, nÆ°á»›c máº¯m, dáº§u hÃ o, bá»™t nÃªm, tiÃªu vÃ  nÆ°á»›c hÃ ng. Sau Ä‘Ã³, sÆ°á»n Ä‘Æ°á»£c chiÃªn trong cháº£o dáº§u nÃ³ng. Khi miáº¿ng sÆ°á»n Ä‘Ã£ ráº¥t má»m vÃ  tháº¥m gia vá»‹ máº·n ngá»t thÃ¬ thÃªm hÃ nh lÃ¡ vÃ o cháº£o vÃ  táº¯t báº¿p2.\n" +
                        "\t\t+ CÃ¡ch lÃ m sÆ°á»n xÃ o chua ngá»t kiá»ƒu miá»n Nam vÃ©t sáº¡ch ná»“i cÆ¡m3: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m sÆ°á»n xÃ o chua ngá»t theo kiá»ƒu miá»n Nam3.\n" +
                        "\t\t+ SÆ°á»n xÃ o chua ngá»t cho bá»¯a cÆ¡m ngon háº¥p dáº«n má»—i ngÃ y4: CÃ´ng thá»©c nÃ y hÆ°á»›ng dáº«n cÃ¡ch lÃ m sÆ°á»n xÃ o chua ngá»t4.", 3));

        // region Mon nuoc
        foodList.add(new Food(1, "BÃ¡nh canh", "MÃ³n nÆ°á»›c",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhcanh, null)),
                "BÃ¡nh canh lÃ  má»™t mÃ³n Äƒn truyá»n thá»‘ng cá»§a Viá»‡t Nam, Ä‘áº·c biá»‡t phá»• biáº¿n á»Ÿ miá»n Nam vÃ  miá»n Trung. BÃ¡nh canh bao gá»“m nÆ°á»›c dÃ¹ng Ä‘Æ°á»£c náº¥u tá»« tÃ´m, cÃ¡ vÃ  giÃ² heo thÃªm gia vá»‹ tÃ¹y theo tá»«ng loáº¡i bÃ¡nh canh1. Sá»£i bÃ¡nh canh cÃ³ thá»ƒ Ä‘Æ°á»£c lÃ m tá»« bá»™t gáº¡o, bá»™t mÃ¬, bá»™t nÄƒng hoáº·c bá»™t sáº¯n hoáº·c bá»™t gáº¡o pha bá»™t sáº¯n12. BÃ¡nh Ä‘Æ°á»£c lÃ m tá»« bá»™t Ä‘Æ°á»£c cÃ¡n thÃ nh táº¥m vÃ  cáº¯t ra thÃ nh sá»£i to vÃ  ngáº¯n1. Gia vá»‹ cho bÃ¡nh canh thay Ä‘á»•i tÃ¹y theo mÃ³n bÃ¡nh canh vÃ  tÃ¹y theo kháº©u vá»‹ má»—i vÃ¹ng1.\n" +
                        "\n" +
                        "CÃ³ nhiá»u loáº¡i bÃ¡nh canh khÃ¡c nhau nhÆ°:\n" +
                        "\n" +
                        "\t\t+ BÃ¡nh canh cua: Vá»›i nhá»¯ng miáº¿ng thá»‹t cua Ä‘á» tÆ°Æ¡i Ä‘Æ°á»£c xáº¿p á»Ÿ bÃªn trÃªn, khi Äƒn báº¡n sáº½ cáº£m nháº­n Ä‘Æ°á»£c sá»± dai ngá»t cá»§a thá»‹t cua3.\n" +
                        "\t\t+ BÃ¡nh canh gháº¹: ThÆ¡m ngá»t cá»§a thá»‹t gháº¹, lÃ m tÄƒng thÃªm hÆ°Æ¡ng vá»‹ cho nÆ°á»›c dÃ¹ng3.\n" +
                        "\t\t+ BÃ¡nh canh chay: Pháº§n nÆ°á»›c dÃ¹ng tá»« rau cá»§ Ä‘áº­m Ä‘Ã  ngá»t thanh vÃ  cá»±c kÃ¬ bá»• dÆ°á»¡ng3.\n" +
                        "\t\t+ BÃ¡nh canh thá»‹t báº±m: MÃ³n Äƒn sÃ¡ng phá»• biáº¿n cá»§a nhiá»u gia Ä‘Ã¬nh3.\n" +
                        "\t\t+ BÃ¡nh canh cÃ¡ lÃ³c: NÆ°á»›c dÃ¹ng sÃ³ng sÃ¡nh, thá»‹t cÃ¡ lÃ³c thÆ¡m ngá»t, dai dai mÃ  láº¡i khÃ´ng há» bá»‹ tanh", 6));
        foodList.add(new Food(1, "BÃºn máº¯m", "MÃ³n nÆ°á»›c",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.bunmam, null)),
                "BÃºn máº¯m lÃ  má»™t mÃ³n Äƒn Ä‘áº·c trÆ°ng cá»§a Viá»‡t Nam, Ä‘áº·c biá»‡t lÃ  miá»n TÃ¢y, vá»›i hÆ°Æ¡ng vá»‹ Ä‘áº­m Ä‘Ã  vÃ  phong cÃ¡ch riÃªng biá»‡t. DÆ°á»›i Ä‘Ã¢y lÃ  mÃ´ táº£ chi tiáº¿t vá» mÃ³n Äƒn nÃ y:\n" +
                        "\n" +
                        "\t\t+ NguyÃªn liá»‡u chÃ­nh: BÃºn máº¯m chá»§ yáº¿u Ä‘Æ°á»£c lÃ m tá»« cÃ¡ lÃ³c phi lÃª, thá»‹t heo quay, máº¯m linh, sáº·c (máº¯m cÃ¡), tÃ´m sÃº, dÆ°a, cÃ  tÃ­m, sáº£ cay, sáº£ báº±m vÃ  nÆ°á»›c thÆ¡m1.\n" +
                        "\t\t+ HÆ°Æ¡ng vá»‹: BÃºn máº¯m cÃ³ hÆ°Æ¡ng vá»‹ Ä‘áº­m Ä‘Ã  tá»« nÆ°á»›c lÃ¨o thÆ¡m ná»“ng káº¿t há»£p vá»›i mÃ¹i náº¯m cÃ¡ linh vÃ  cÃ¡ sáº·c2. MÃ³n Äƒn nÃ y cÃ²n Ä‘Æ°á»£c bá»• sung thÃªm hÆ°Æ¡ng vá»‹ tá»« cÃ¡c loáº¡i rau Ä‘áº·c trÆ°ng cá»§a miá»n TÃ¢y2.\n" +
                        "\t\t+ CÃ¡ch cháº¿ biáº¿n: BÃºn máº¯m Ä‘Æ°á»£c cháº¿ biáº¿n báº±ng cÃ¡ch háº¥p cÃ¡c nguyÃªn liá»‡u chÃ­nh nhÆ° cÃ¡ lÃ³c phi lÃª, thá»‹t heo quay vÃ  tÃ´m sÃº. Sau Ä‘Ã³, cÃ¡c nguyÃªn liá»‡u nÃ y Ä‘Æ°á»£c káº¿t há»£p vá»›i nÆ°á»›c lÃ¨o thÆ¡m ná»“ng Ä‘á»ƒ táº¡o ra hÆ°Æ¡ng vá»‹ Ä‘áº·c trÆ°ng cho mÃ³n Äƒn1.\n" +
                        "\t\t+ Phá»¥c vá»¥: BÃºn máº¯m thÆ°á»ng Ä‘Æ°á»£c phá»¥c vá»¥ nÃ³ng há»•i cÃ¹ng vá»›i cÃ¡c loáº¡i rau sá»‘ng vÃ  gia vá»‹1.\n" +
                        "\t\t+ ÄÃ¡nh giÃ¡: MÃ³n Äƒn nÃ y Ä‘Æ°á»£c Ä‘Ã¡nh giÃ¡ ráº¥t cao bá»Ÿi ngÆ°á»i dÃ¹ng, vá»›i Ä‘iá»ƒm sá»‘ trung bÃ¬nh lÃ  5/5 dá»±a trÃªn 9986 Ä‘Ã¡nh giÃ¡1.", 6));
        foodList.add(new Food(1, "BÃºn thÃ¡i", "MÃ³n nÆ°á»›c",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.bun_thai, null)),
                "BÃºn ThÃ¡i lÃ  má»™t mÃ³n Äƒn Ä‘áº·c trÆ°ng cá»§a ThÃ¡i Lan, vá»›i hÆ°Æ¡ng vá»‹ chua cay Ä‘áº·c trÆ°ng. DÆ°á»›i Ä‘Ã¢y lÃ  cÃ¡ch cháº¿ biáº¿n mÃ³n BÃºn ThÃ¡i:\n" +
                        "\n" +
                        "\t\t+ NguyÃªn liá»‡u: BÃºn ThÃ¡i chá»§ yáº¿u Ä‘Æ°á»£c lÃ m tá»« thá»‹t bÃ² fillet má»m, rÃ¢u má»±c, tÃ´m sÃº tÆ°Æ¡i, nghÃªu to, rau muá»‘ng, cÃ  chua, náº¥m Ä‘Ã´ng cÃ´ tÆ°Æ¡i, gá»«ng cáº¯t sá»£i, riá»ng cáº¯t sá»£i vÃ  hÃ nh tÃ­m1.\n" +
                        "\t\t+ HÆ°Æ¡ng vá»‹: BÃºn ThÃ¡i cÃ³ hÆ°Æ¡ng vá»‹ chua chua, cay cay Ä‘áº·c trÆ°ng cá»§a xá»© ThÃ¡i2. HÆ°Æ¡ng vá»‹ nÃ y Ä‘Æ°á»£c táº¡o ra tá»« viá»‡c káº¿t há»£p cÃ¡c loáº¡i gia vá»‹ Ä‘áº·c trÆ°ng trong áº©m thá»±c ThÃ¡i2.\n" +
                        "\t\t+ CÃ¡ch cháº¿ biáº¿n: BÃºn ThÃ¡i Ä‘Æ°á»£c cháº¿ biáº¿n báº±ng cÃ¡ch xÃ o thÆ¡m cÃ¡c nguyÃªn liá»‡u nhÆ° thá»‹t bÃ², rÃ¢u má»±c vÃ  tÃ´m sÃº. Sau Ä‘Ã³, cÃ¡c nguyÃªn liá»‡u nÃ y Ä‘Æ°á»£c káº¿t há»£p vá»›i nÆ°á»›c dÃ¹ng thÆ¡m ná»“ng Ä‘á»ƒ táº¡o ra hÆ°Æ¡ng vá»‹ Ä‘áº·c trÆ°ng cho mÃ³n Äƒn1.\n" +
                        "\t\t+ Phá»¥c vá»¥: BÃºn ThÃ¡i thÆ°á»ng Ä‘Æ°á»£c phá»¥c vá»¥ nÃ³ng há»•i cÃ¹ng vá»›i cÃ¡c loáº¡i rau sá»‘ng vÃ  gia vá»‹1.\n" +
                        "\t\t+ ÄÃ¡nh giÃ¡: MÃ³n Äƒn nÃ y Ä‘Æ°á»£c Ä‘Ã¡nh giÃ¡ ráº¥t cao bá»Ÿi ngÆ°á»i dÃ¹ng, vá»›i Ä‘iá»ƒm sá»‘ trung bÃ¬nh lÃ  5/5 dá»±a trÃªn 21339 Ä‘Ã¡nh giÃ¡1.", 6));
        foodList.add(new Food(1, "HoÃ nh thÃ¡nh", "MÃ³n nÆ°á»›c",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hoanhthanh, null)),
                "HoÃ nh thÃ¡nh lÃ  má»™t mÃ³n Äƒn Ä‘áº·c trÆ°ng cá»§a Trung Quá»‘c vÃ  Ä‘Ã£ trá»Ÿ nÃªn phá»• biáº¿n á»Ÿ Viá»‡t Nam. DÆ°á»›i Ä‘Ã¢y lÃ  cÃ¡ch cháº¿ biáº¿n mÃ³n hoÃ nh thÃ¡nh:\n" +
                        "\n" +
                        "\t\t+ NguyÃªn liá»‡u: HoÃ nh thÃ¡nh chá»§ yáº¿u Ä‘Æ°á»£c lÃ m tá»« thá»‹t náº¡c vai xay, thá»‹t tÃ´m xay, cÃ  rá»‘t, náº¥m hÆ°Æ¡ng, hÃ nh tÃ¢y, tá»i, hÃ nh tÃ­m vÃ  hÃ nh lÃ¡1.\n" +
                        "\t\t+ HÆ°Æ¡ng vá»‹: HoÃ nh thÃ¡nh cÃ³ hÆ°Æ¡ng vá»‹ thÆ¡m ngon tá»« nhÃ¢n thá»‹t bÃ¹i ngá»t bÃªn trong vÃ  lá»›p vá» má»m máº¡i bÃªn ngoÃ i1.\n" +
                        "\t\t+ CÃ¡ch cháº¿ biáº¿n: HoÃ nh thÃ¡nh Ä‘Æ°á»£c cháº¿ biáº¿n báº±ng cÃ¡ch náº·n tá»«ng viÃªn hoÃ nh thÃ¡nh rá»“i náº¥u trong nÆ°á»›c dÃ¹ng xÆ°Æ¡ng thÆ¡m vá»‹ Ä‘áº·c trÆ°ng cá»§a lÃ¡ háº¹1.\n" +
                        "\t\t+ Phá»¥c vá»¥: HoÃ nh thÃ¡nh thÆ°á»ng Ä‘Æ°á»£c phá»¥c vá»¥ nÃ³ng há»•i cÃ¹ng vá»›i nÆ°á»›c dÃ¹ng thanh mÃ¡t1.\n" +
                        "\t\t+ ÄÃ¡nh giÃ¡: MÃ³n Äƒn nÃ y Ä‘Æ°á»£c Ä‘Ã¡nh giÃ¡ ráº¥t cao bá»Ÿi ngÆ°á»i dÃ¹ng, vá»›i Ä‘iá»ƒm sá»‘ trung bÃ¬nh lÃ  5/5 dá»±a trÃªn 1 Ä‘Ã¡nh giÃ¡1.", 6));
        foodList.add(new Food(1, "Há»§ tiáº¿u bÃ² kho", "MÃ³n nÆ°á»›c",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hutieu_bokho, null)),
                "Há»§ tiáº¿u bÃ² kho lÃ  má»™t mÃ³n Äƒn ngon, Ä‘áº­m Ä‘Ã  Ä‘Æ°á»£c nhiá»u ngÆ°á»i yÃªu thÃ­ch. DÆ°á»›i Ä‘Ã¢y lÃ  cÃ¡ch cháº¿ biáº¿n mÃ³n há»§ tiáº¿u bÃ² kho:\n" +
                        "\n" +
                        "\t\t+ NguyÃªn liá»‡u: Há»§ tiáº¿u bÃ² kho chá»§ yáº¿u Ä‘Æ°á»£c lÃ m tá»« thá»‹t bÃ² náº¡m, há»§ tiáº¿u, giÃ¡, mÃ u dáº§u Ä‘iá»u, háº¡t nÃªm, Ä‘Æ°á»ng, muá»‘i, á»›t bá»™t, tiÃªu, bá»™t gia vá»‹ bÃ² kho, sáº£, ngÃ² gai, hÃ nh lÃ¡, hÃ nh tÃ­m, hÃ nh tÃ¢y, á»›t, tá»i, quáº¿, chanh1.\n" +
                        "\t\t+ HÆ°Æ¡ng vá»‹: Há»§ tiáº¿u bÃ² kho cÃ³ hÆ°Æ¡ng vá»‹ thÆ¡m ngon tá»« nhÃ¢n thá»‹t bÃ¹i ngá»t bÃªn trong vÃ  lá»›p vá» má»m máº¡i bÃªn ngoÃ i1.\n" +
                        "\t\t+ CÃ¡ch cháº¿ biáº¿n: Há»§ tiáº¿u bÃ² kho Ä‘Æ°á»£c cháº¿ biáº¿n báº±ng cÃ¡ch Æ°á»›p thá»‹t bÃ² trong 30 phÃºt vá»›i gia vá»‹ bÃ² kho, mÃ u dáº§u Ä‘iá»u, Ä‘Æ°á»ng, háº¡t nÃªm, tiÃªu, á»›t bá»™t vÃ  muá»‘i. Sau Ä‘Ã³ thá»‹t Ä‘Æ°á»£c xÃ o sÆ¡ trong nÆ°á»›c dÃ¹ng xÆ°Æ¡ng thÆ¡m vá»‹ Ä‘áº·c trÆ°ng cá»§a lÃ¡ háº¹1.\n" +
                        "\t\t+ Phá»¥c vá»¥: Há»§ tiáº¿u bÃ² kho thÆ°á»ng Ä‘Æ°á»£c phá»¥c vá»¥ nÃ³ng há»•i cÃ¹ng vá»›i cÃ¡c loáº¡i rau sá»‘ng vÃ  gia vá»‹1.\n" +
                        "\t\t+ ÄÃ¡nh giÃ¡: MÃ³n Äƒn nÃ y Ä‘Æ°á»£c Ä‘Ã¡nh giÃ¡ ráº¥t cao bá»Ÿi ngÆ°á»i dÃ¹ng1", 6));
        foodList.add(new Food(1, "Há»§ tiáº¿u nam vang", "MÃ³n nÆ°á»›c",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hutieu_namvang, null)),
                "Há»§ tiáº¿u Nam Vang lÃ  má»™t mÃ³n Äƒn Ä‘áº·c trÆ°ng cá»§a Viá»‡t Nam, vá»›i hÆ°Æ¡ng vá»‹ Ä‘áº­m Ä‘Ã  vÃ  phong cÃ¡ch riÃªng biá»‡t. DÆ°á»›i Ä‘Ã¢y lÃ  mÃ´ táº£ chi tiáº¿t vá» mÃ³n Äƒn nÃ y:\n" +
                        "\n" +
                        "\t\t+ NguyÃªn liá»‡u chÃ­nh: Há»§ tiáº¿u Nam Vang chá»§ yáº¿u Ä‘Æ°á»£c lÃ m tá»« thá»‹t náº¡c vai, tÃ´m tÆ°Æ¡i, gan heo, trá»©ng cÃºt, tá»i, hÃ nh lÃ¡, cáº§n tÃ u, xÃ  lÃ¡ch, giÃ¡ vÃ  há»§ tiáº¿u1.\n" +
                        "\t\t+ HÆ°Æ¡ng vá»‹: Há»§ tiáº¿u Nam Vang cÃ³ hÆ°Æ¡ng vá»‹ thÆ¡m ngon tá»« nhÃ¢n thá»‹t bÃ¹i ngá»t bÃªn trong vÃ  lá»›p vá» má»m máº¡i bÃªn ngoÃ i1.\n" +
                        "\t\t+ CÃ¡ch cháº¿ biáº¿n: Há»§ tiáº¿u Nam Vang Ä‘Æ°á»£c cháº¿ biáº¿n báº±ng cÃ¡ch náº¥u sÃ´i cÃ¡c nguyÃªn liá»‡u nhÆ° thá»‹t náº¡c vai, gan heo vÃ  tÃ´m trong nÆ°á»›c dÃ¹ng xÆ°Æ¡ng thÆ¡m vá»‹ Ä‘áº·c trÆ°ng cá»§a lÃ¡ háº¹1.\n" +
                        "\t\t+ Phá»¥c vá»¥: Há»§ tiáº¿u Nam Vang thÆ°á»ng Ä‘Æ°á»£c phá»¥c vá»¥ nÃ³ng há»•i cÃ¹ng vá»›i cÃ¡c loáº¡i rau sá»‘ng vÃ  gia vá»‹1.\n" +
                        "\t\t+ ÄÃ¡nh giÃ¡: MÃ³n Äƒn nÃ y Ä‘Æ°á»£c Ä‘Ã¡nh giÃ¡ ráº¥t cao bá»Ÿi ngÆ°á»i dÃ¹ng1.", 6));
        foodList.add(new Food(1, "BÃºn thÃ¡i", "MÃ³n nÆ°á»›c",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.bun_thai, null)),
                "BÃºn ThÃ¡i lÃ  má»™t mÃ³n Äƒn Ä‘áº·c trÆ°ng cá»§a ThÃ¡i Lan, vá»›i hÆ°Æ¡ng vá»‹ chua cay Ä‘áº·c trÆ°ng. DÆ°á»›i Ä‘Ã¢y lÃ  cÃ¡ch cháº¿ biáº¿n mÃ³n BÃºn ThÃ¡i:\n" +
                        "\n" +
                        "\t\t+ NguyÃªn liá»‡u: BÃºn ThÃ¡i chá»§ yáº¿u Ä‘Æ°á»£c lÃ m tá»« thá»‹t bÃ² fillet má»m, rÃ¢u má»±c, tÃ´m sÃº tÆ°Æ¡i, nghÃªu to, rau muá»‘ng, cÃ  chua, náº¥m Ä‘Ã´ng cÃ´ tÆ°Æ¡i, gá»«ng cáº¯t sá»£i, riá»ng cáº¯t sá»£i vÃ  hÃ nh tÃ­m1.\n" +
                        "\t\t+ HÆ°Æ¡ng vá»‹: BÃºn ThÃ¡i cÃ³ hÆ°Æ¡ng vá»‹ chua chua, cay cay Ä‘áº·c trÆ°ng cá»§a xá»© ThÃ¡i2. HÆ°Æ¡ng vá»‹ nÃ y Ä‘Æ°á»£c táº¡o ra tá»« viá»‡c káº¿t há»£p cÃ¡c loáº¡i gia vá»‹ Ä‘áº·c trÆ°ng trong áº©m thá»±c ThÃ¡i2.\n" +
                        "\t\t+ CÃ¡ch cháº¿ biáº¿n: BÃºn ThÃ¡i Ä‘Æ°á»£c cháº¿ biáº¿n báº±ng cÃ¡ch xÃ o thÆ¡m cÃ¡c nguyÃªn liá»‡u nhÆ° thá»‹t bÃ², rÃ¢u má»±c vÃ  tÃ´m sÃº. Sau Ä‘Ã³, cÃ¡c nguyÃªn liá»‡u nÃ y Ä‘Æ°á»£c káº¿t há»£p vá»›i nÆ°á»›c dÃ¹ng thÆ¡m ná»“ng Ä‘á»ƒ táº¡o ra hÆ°Æ¡ng vá»‹ Ä‘áº·c trÆ°ng cho mÃ³n Äƒn1.\n" +
                        "\t\t+ Phá»¥c vá»¥: BÃºn ThÃ¡i thÆ°á»ng Ä‘Æ°á»£c phá»¥c vá»¥ nÃ³ng há»•i cÃ¹ng vá»›i cÃ¡c loáº¡i rau sá»‘ng vÃ  gia vá»‹1.\n" +
                        "\t\t+ ÄÃ¡nh giÃ¡: MÃ³n Äƒn nÃ y Ä‘Æ°á»£c Ä‘Ã¡nh giÃ¡ ráº¥t cao bá»Ÿi ngÆ°á»i dÃ¹ng, vá»›i Ä‘iá»ƒm sá»‘ trung bÃ¬nh lÃ  5/5 dá»±a trÃªn 21339 Ä‘Ã¡nh giÃ¡1", 6));
        foodList.add(new Food(1, "HoÃ nh thÃ¡nh", "MÃ³n nÆ°á»›c",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hoanhthanh, null)),
                "HoÃ nh thÃ¡nh lÃ  má»™t mÃ³n Äƒn Ä‘áº·c trÆ°ng cá»§a Trung Quá»‘c vÃ  Ä‘Ã£ trá»Ÿ nÃªn phá»• biáº¿n á»Ÿ Viá»‡t Nam. DÆ°á»›i Ä‘Ã¢y lÃ  cÃ¡ch cháº¿ biáº¿n mÃ³n hoÃ nh thÃ¡nh:\n" +
                        "\n" +
                        "\t\t+ NguyÃªn liá»‡u: HoÃ nh thÃ¡nh chá»§ yáº¿u Ä‘Æ°á»£c lÃ m tá»« thá»‹t náº¡c vai xay, thá»‹t tÃ´m xay, cÃ  rá»‘t, náº¥m hÆ°Æ¡ng, hÃ nh tÃ¢y, tá»i, hÃ nh tÃ­m vÃ  hÃ nh lÃ¡1.\n" +
                        "\t\t+ HÆ°Æ¡ng vá»‹: HoÃ nh thÃ¡nh cÃ³ hÆ°Æ¡ng vá»‹ thÆ¡m ngon tá»« nhÃ¢n thá»‹t bÃ¹i ngá»t bÃªn trong vÃ  lá»›p vá» má»m máº¡i bÃªn ngoÃ i1.\n" +
                        "\t\t+  CÃ¡ch cháº¿ biáº¿n: HoÃ nh thÃ¡nh Ä‘Æ°á»£c cháº¿ biáº¿n báº±ng cÃ¡ch náº·n tá»«ng viÃªn hoÃ nh thÃ¡nh rá»“i náº¥u trong nÆ°á»›c dÃ¹ng xÆ°Æ¡ng thÆ¡m vá»‹ Ä‘áº·c trÆ°ng cá»§a lÃ¡ háº¹1.\n" +
                        "\t\t+ Phá»¥c vá»¥: HoÃ nh thÃ¡nh thÆ°á»ng Ä‘Æ°á»£c phá»¥c vá»¥ nÃ³ng há»•i cÃ¹ng vá»›i nÆ°á»›c dÃ¹ng thanh mÃ¡t1.\n" +
                        "\t\t+ ÄÃ¡nh giÃ¡: MÃ³n Äƒn nÃ y Ä‘Æ°á»£c Ä‘Ã¡nh giÃ¡ ráº¥t cao bá»Ÿi ngÆ°á»i dÃ¹ng, vá»›i Ä‘iá»ƒm sá»‘ trung bÃ¬nh lÃ  5/5 dá»±a trÃªn 1 Ä‘Ã¡nh giÃ¡1.", 6));
        foodList.add(new Food(1, "Há»§ tiáº¿u bÃ² kho", "MÃ³n nÆ°á»›c",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hutieu_bokho, null)),
                "Há»§ tiáº¿u bÃ² kho lÃ  má»™t mÃ³n Äƒn ngon, Ä‘áº­m Ä‘Ã  Ä‘Æ°á»£c nhiá»u ngÆ°á»i yÃªu thÃ­ch. DÆ°á»›i Ä‘Ã¢y lÃ  cÃ¡ch cháº¿ biáº¿n mÃ³n há»§ tiáº¿u bÃ² kho:\\n\" +\n" +
                        "\"\\n\" +\n" +
                        "\"\\t\\t+ NguyÃªn liá»‡u: Há»§ tiáº¿u bÃ² kho chá»§ yáº¿u Ä‘Æ°á»£c lÃ m tá»« thá»‹t bÃ² náº¡m, há»§ tiáº¿u, giÃ¡, mÃ u dáº§u Ä‘iá»u, háº¡t nÃªm, Ä‘Æ°á»ng, muá»‘i, á»›t bá»™t, tiÃªu, bá»™t gia vá»‹ bÃ² kho, sáº£, ngÃ² gai, hÃ nh lÃ¡, hÃ nh tÃ­m, hÃ nh tÃ¢y, á»›t, tá»i, quáº¿, chanh1.\\n\" +\n" +
                        "\"\\t\\t+ HÆ°Æ¡ng vá»‹: Há»§ tiáº¿u bÃ² kho cÃ³ hÆ°Æ¡ng vá»‹ thÆ¡m ngon tá»« nhÃ¢n thá»‹t bÃ¹i ngá»t bÃªn trong vÃ  lá»›p vá» má»m máº¡i bÃªn ngoÃ i1.\\n\" +\n" +
                        "\"\\t\\t+ CÃ¡ch cháº¿ biáº¿n: Há»§ tiáº¿u bÃ² kho Ä‘Æ°á»£c cháº¿ biáº¿n báº±ng cÃ¡ch Æ°á»›p thá»‹t bÃ² trong 30 phÃºt vá»›i gia vá»‹ bÃ² kho, mÃ u dáº§u Ä‘iá»u, Ä‘Æ°á»ng, háº¡t nÃªm, tiÃªu, á»›t bá»™t vÃ  muá»‘i. Sau Ä‘Ã³ thá»‹t Ä‘Æ°á»£c xÃ o sÆ¡ trong nÆ°á»›c dÃ¹ng xÆ°Æ¡ng thÆ¡m vá»‹ Ä‘áº·c trÆ°ng cá»§a lÃ¡ háº¹1.\\n\" +\n" +
                        "\"\\t\\t+ Phá»¥c vá»¥: Há»§ tiáº¿u bÃ² kho thÆ°á»ng Ä‘Æ°á»£c phá»¥c vá»¥ nÃ³ng há»•i cÃ¹ng vá»›i cÃ¡c loáº¡i rau sá»‘ng vÃ  gia vá»‹1.\\n\" +\n" +
                        "\"\\t\\t+ ÄÃ¡nh giÃ¡: MÃ³n Äƒn nÃ y Ä‘Æ°á»£c Ä‘Ã¡nh giÃ¡ ráº¥t cao bá»Ÿi ngÆ°á»i dÃ¹ng1", 6));
        foodList.add(new Food(1, "Há»§ tiáº¿u nam vang", "MÃ³n nÆ°á»›c",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hutieu_namvang, null)),
                "Há»§ tiáº¿u Nam Vang lÃ  má»™t mÃ³n Äƒn Ä‘áº·c trÆ°ng cá»§a Viá»‡t Nam, vá»›i hÆ°Æ¡ng vá»‹ Ä‘áº­m Ä‘Ã  vÃ  phong cÃ¡ch riÃªng biá»‡t. DÆ°á»›i Ä‘Ã¢y lÃ  mÃ´ táº£ chi tiáº¿t vá» mÃ³n Äƒn nÃ y:\\n\" +\n" +
                        "\"\\n\" +\n" +
                        "\"\\t\\t+ NguyÃªn liá»‡u chÃ­nh: Há»§ tiáº¿u Nam Vang chá»§ yáº¿u Ä‘Æ°á»£c lÃ m tá»« thá»‹t náº¡c vai, tÃ´m tÆ°Æ¡i, gan heo, trá»©ng cÃºt, tá»i, hÃ nh lÃ¡, cáº§n tÃ u, xÃ  lÃ¡ch, giÃ¡ vÃ  há»§ tiáº¿u1.\\n\" +\n" +
                        "\"\\t\\t+ HÆ°Æ¡ng vá»‹: Há»§ tiáº¿u Nam Vang cÃ³ hÆ°Æ¡ng vá»‹ thÆ¡m ngon tá»« nhÃ¢n thá»‹t bÃ¹i ngá»t bÃªn trong vÃ  lá»›p vá» má»m máº¡i bÃªn ngoÃ i1.\\n\" +\n" +
                        "\"\\t\\t+ CÃ¡ch cháº¿ biáº¿n: Há»§ tiáº¿u Nam Vang Ä‘Æ°á»£c cháº¿ biáº¿n báº±ng cÃ¡ch náº¥u sÃ´i cÃ¡c nguyÃªn liá»‡u nhÆ° thá»‹t náº¡c vai, gan heo vÃ  tÃ´m trong nÆ°á»›c dÃ¹ng xÆ°Æ¡ng thÆ¡m vá»‹ Ä‘áº·c trÆ°ng cá»§a lÃ¡ háº¹1.\\n\" +\n" +
                        "\"\\t\\t+ Phá»¥c vá»¥: Há»§ tiáº¿u Nam Vang thÆ°á»ng Ä‘Æ°á»£c phá»¥c vá»¥ nÃ³ng há»•i cÃ¹ng vá»›i cÃ¡c loáº¡i rau sá»‘ng vÃ  gia vá»‹1.\\n\" +\n" +
                        "\"\\t\\t+ ÄÃ¡nh giÃ¡: MÃ³n Äƒn nÃ y Ä‘Æ°á»£c Ä‘Ã¡nh giÃ¡ ráº¥t cao bá»Ÿi ngÆ°á»i dÃ¹ng1.", 6));
        foodList.add(new Food(1, "MÃ¬ váº±n tháº¯n", "MÃ³n nÆ°á»›c",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mi_vanthan, null)),
                "MÃ¬ váº±n tháº¯n lÃ  má»™t mÃ³n Äƒn thÆ¡m ngon, bá»• dÆ°á»¡ng vÃ  cá»±c ká»³ háº¥p dáº«n. DÆ°á»›i Ä‘Ã¢y lÃ  cÃ¡ch cháº¿ biáº¿n mÃ³n mÃ¬ váº±n tháº¯n:\n" +
                        "\n" +
                        "\t\t+ NguyÃªn liá»‡u: MÃ¬ váº±n tháº¯n chá»§ yáº¿u Ä‘Æ°á»£c lÃ m tá»« thá»‹t náº¡c vai, tÃ´m tÆ°Æ¡i, gan heo, tÃ´m khÃ´, mÃ¬ tÆ°Æ¡i, vá» gÃ³i sá»§i cáº£o, bÃ³ng bÃ¬ lá»£n, trá»©ng gÃ , rau táº§n Ã´ hoáº·c rau cáº£i xanh123.\n" +
                        "\t\t+ HÆ°Æ¡ng vá»‹: MÃ¬ váº±n tháº¯n cÃ³ hÆ°Æ¡ng vá»‹ thÆ¡m ngon tá»« nhÃ¢n thá»‹t bÃ¹i ngá»t bÃªn trong vÃ  lá»›p vá» má»m máº¡i bÃªn ngoÃ i2.\n" +
                        "\t\t+ CÃ¡ch cháº¿ biáº¿n: MÃ¬ váº±n tháº¯n Ä‘Æ°á»£c cháº¿ biáº¿n báº±ng cÃ¡ch náº¥u sÃ´i cÃ¡c nguyÃªn liá»‡u nhÆ° thá»‹t náº¡c vai, gan heo vÃ  tÃ´m trong nÆ°á»›c dÃ¹ng xÆ°Æ¡ng thÆ¡m vá»‹ Ä‘áº·c trÆ°ng cá»§a lÃ¡ háº¹1.\n" +
                        "\t\t+ Phá»¥c vá»¥: MÃ¬ váº±n tháº¯n thÆ°á»ng Ä‘Æ°á»£c phá»¥c vá»¥ nÃ³ng há»•i cÃ¹ng vá»›i cÃ¡c loáº¡i rau sá»‘ng vÃ  gia vá»‹1.\n" +
                        "\t\t+ ÄÃ¡nh giÃ¡: MÃ³n Äƒn nÃ y Ä‘Æ°á»£c Ä‘Ã¡nh giÃ¡ ráº¥t cao bá»Ÿi ngÆ°á»i dÃ¹ng", 6));
        foodList.add(new Food(1, "MÃ¬ xÃ¡ xÃ­u", "MÃ³n nÆ°á»›c",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mi_xaxiu, null)),
                "MÃ¬ XÃ¡ XÃ­u lÃ  má»™t mÃ³n Äƒn ngon vÃ  thÆ°á»ng Ä‘Æ°á»£c Äƒn vÃ o bá»¯a sÃ¡ng. DÆ°á»›i Ä‘Ã¢y lÃ  cÃ¡ch cháº¿ biáº¿n mÃ³n MÃ¬ XÃ¡ XÃ­u:\n" +
                        "\n" +
                        "\t\t+ NguyÃªn liá»‡u: MÃ¬ XÃ¡ XÃ­u chá»§ yáº¿u Ä‘Æ°á»£c lÃ m tá»« thá»‹t náº¡c vai, tÃ´m tÆ°Æ¡i, gan heo, tÃ´m khÃ´, mÃ¬ tÆ°Æ¡i, vá» gÃ³i sá»§i cáº£o, bÃ³ng bÃ¬ lá»£n, trá»©ng gÃ , rau táº§n Ã´ hoáº·c rau cáº£i xanh12.\n" +
                        "\t\t+ HÆ°Æ¡ng vá»‹: MÃ¬ XÃ¡ XÃ­u cÃ³ hÆ°Æ¡ng vá»‹ thÆ¡m ngon tá»« nhÃ¢n thá»‹t bÃ¹i ngá»t bÃªn trong vÃ  lá»›p vá» má»m máº¡i bÃªn ngoÃ i3.\n" +
                        "\t\t+ CÃ¡ch cháº¿ biáº¿n: MÃ¬ XÃ¡ XÃ­u Ä‘Æ°á»£c cháº¿ biáº¿n báº±ng cÃ¡ch náº¥u sÃ´i cÃ¡c nguyÃªn liá»‡u nhÆ° thá»‹t náº¡c vai, gan heo vÃ  tÃ´m trong nÆ°á»›c dÃ¹ng xÆ°Æ¡ng thÆ¡m vá»‹ Ä‘áº·c trÆ°ng cá»§a lÃ¡ háº¹3.\n" +
                        "\t\t+ Phá»¥c vá»¥: MÃ¬ XÃ¡ XÃ­u thÆ°á»ng Ä‘Æ°á»£c phá»¥c vá»¥ nÃ³ng há»•i cÃ¹ng vá»›i cÃ¡c loáº¡i rau sá»‘ng vÃ  gia vá»‹3.\n" +
                        "\t\t+ ÄÃ¡nh giÃ¡: MÃ³n Äƒn nÃ y Ä‘Æ°á»£c Ä‘Ã¡nh giÃ¡ ráº¥t cao bá»Ÿi ngÆ°á»i dÃ¹ng3", 6));
        foodList.add(new Food(1, "Nui", "MÃ³n nÆ°á»›c",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.nui, null)),
                "Nui lÃ  má»™t loáº¡i mÃ¬ Ã dáº¡ng ngáº¯n, thÆ°á»ng Ä‘Æ°á»£c sá»­ dá»¥ng trong nhiá»u mÃ³n Äƒn khÃ¡c nhau. DÆ°á»›i Ä‘Ã¢y lÃ  má»™t sá»‘ cÃ¡ch cháº¿ biáº¿n nui:\n" +
                        "\n" +
                        "\t\t+ Nui xÃ o háº£i sáº£n: MÃ³n Äƒn nÃ y Ä‘Æ°á»£c lÃ m tá»« rau má»±c cáº¯t nhá», nui, tÃ´m sÃº, sÃ² Ä‘iá»‡p, thá»‹t ngao, cÃ  chua báº±m, báº¯p háº¡t (HÃ  Lan), muá»‘i (tiÃªu, Ä‘Æ°á»ng, Äƒn), cÃ  chua há»™p (báº±m, tá»i báº±m), vÃ  háº¡t Aji-ngon Heo1.\n" +
                        "\t\t+ Nui XÃ o LÃ²ng GÃ : MÃ³n Äƒn nÃ y Ä‘Æ°á»£c lÃ m tá»« lÃ²ng gÃ  (má», gan, tim), nui, hÃ nh lÃ¡ (gia vá»‹ nem nem), vÃ  cáº£i ngá»t2.\n" +
                        "\t\t+ Nui sá»‘t cay kiá»ƒu HÃ n: MÃ³n Äƒn nÃ y Ä‘Æ°á»£c lÃ m tá»« nui khÃ´, quáº£ trá»©ng cÃºt, lÃ¡ rong biá»ƒn, cÃ¡ cÆ¡m (khÃ´), tÃ´m khÃ´, lÃ¡ (háº¹), vÃ  gia vá»‹ (Siro báº¯p, Ä‘Æ°á»ng, nÆ°á»›c tÆ°Æ¡ng, dáº§u Äƒn, á»›t bá»™t HÃ n Quá»‘c, tÆ°Æ¡ng á»›t HÃ n Quá»‘c)3", 6));
        foodList.add(new Food(1, "Phá»Ÿ bÃ²", "MÃ³n nÆ°á»›c",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.phobo, null)),
                "Phá»Ÿ BÃ² lÃ  má»™t mÃ³n Äƒn quá»‘c gia cá»§a Viá»‡t Nam, má»™t mÃ³n Äƒn mÃ  nhiá»u ngÆ°á»i Viá»‡t yÃªu thÃ­ch. DÆ°á»›i Ä‘Ã¢y lÃ  cÃ¡ch cháº¿ biáº¿n Phá»Ÿ BÃ²:\n" +
                        "\n" +
                        "\t\t+ NguyÃªn liá»‡u: Phá»Ÿ BÃ² chá»§ yáº¿u Ä‘Æ°á»£c lÃ m tá»« xÆ°Æ¡ng cá»¥c (xÆ°Æ¡ng á»‘ng), thá»‹t bÃ², hÃ nh khÃ´, gá»«ng, quáº¿, hoa há»“i, trÃ¡i tháº£o (quáº£), muá»‘i, háº¡t nÃªm, nÆ°á»›c máº¯m, chÃ­n (mÃ¬, bá»™t ngá»t), hÃ nh lÃ¡ (rau mÃ¹i, ngÃ² ri), bÃ¡nh phá»Ÿ1.\n" +
                        "\t\t+ HÆ°Æ¡ng vá»‹: Phá»Ÿ BÃ² cÃ³ hÆ°Æ¡ng vá»‹ thÆ¡m ngon tá»« nhÃ¢n thá»‹t bÃ¹i ngá»t bÃªn trong vÃ  lá»›p vá» má»m máº¡i bÃªn ngoÃ i1.\n" +
                        "\t\t+ CÃ¡ch cháº¿ biáº¿n: Phá»Ÿ BÃ² Ä‘Æ°á»£c cháº¿ biáº¿n báº±ng cÃ¡ch náº¥u sÃ´i cÃ¡c nguyÃªn liá»‡u nhÆ° xÆ°Æ¡ng cá»¥c (xÆ°Æ¡ng á»‘ng), thá»‹t bÃ² trong nÆ°á»›c dÃ¹ng xÆ°Æ¡ng thÆ¡m vá»‹ Ä‘áº·c trÆ°ng cá»§a lÃ¡ háº¹1.\n" +
                        "\t\t+ Phá»¥c vá»¥: Phá»Ÿ BÃ² thÆ°á»ng Ä‘Æ°á»£c phá»¥c vá»¥ nÃ³ng há»•i cÃ¹ng vá»›i cÃ¡c loáº¡i rau sá»‘ng vÃ  gia vá»‹1.\n" +
                        "\t\t+ ÄÃ¡nh giÃ¡: MÃ³n Äƒn nÃ y Ä‘Æ°á»£c Ä‘Ã¡nh giÃ¡ ráº¥t cao bá»Ÿi ngÆ°á»i dÃ¹ng", 6));

        // region Tra sua
        foodList.add(new Food(1, "TrÃ  sá»¯a dÃ¢u", "TrÃ  sá»¯a",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.trasua_dau, null)),
                "TrÃ  sá»¯a dÃ¢u lÃ  má»™t mÃ³n Ä‘á»“ uá»‘ng ngá»t ngÃ o, thÆ¡m mÃ¡t ráº¥t Ä‘Æ°á»£c Æ°a chuá»™ng. DÆ°á»›i Ä‘Ã¢y lÃ  cÃ¡ch lÃ m trÃ  sá»¯a dÃ¢u táº¡i nhÃ :\n" +
                        "\n" +
                        "NguyÃªn liá»‡u: TrÃ  Ä‘en (trÃ  cÃ¡nh hoáº·c trÃ  tÃºi lá»c), dÃ¢u tÆ°Æ¡i hoáº·c siro dÃ¢u Ä‘á»ƒ táº¡o hÆ°Æ¡ng vá»‹ vÃ  trang trÃ­, sá»¯a Ä‘áº·c hoáº·c bá»™t pha trÃ  sá»¯a chuyÃªn dá»¥ng Ä‘á»ƒ táº¡o Ä‘á»™ thÆ¡m bÃ©o, sá»¯a tÆ°Æ¡i (tÃ¹y chá»n Ä‘á»ƒ thay tháº¿ cho sá»¯a Ä‘áº·c vÃ  bá»™t sá»¯a), Ä‘Æ°á»ng kÃ­nh hoáº·c siro Ä‘Æ°á»ng Ä‘á»ƒ táº¡o Ä‘á»™ ngá»t, vÃ  Ä‘Ã¡ viÃªn12.\n" +
                        "\n" +
                        "CÃ¡ch lÃ m:\n" +
                        "\n" +
                        "\t\t+ CÃ¡ch 1: Sá»­ dá»¥ng trÃ  tÃºi lá»c vá»‹ dÃ¢u1. á»¦ trÃ  vá»›i nÆ°á»›c nÃ³ng trong khoáº£ng 15 phÃºt, sau Ä‘Ã³ lá»c láº¥y nÆ°á»›c cá»‘t. Cho sá»¯a Ä‘áº·c, siro dÃ¢u, Ä‘Æ°á»ng kÃ­nh vÃ o trÃ  nÃ³ng vÃ  hÃ²a tan. Cho há»—n há»£p vÃ o bÃ¬nh láº¯c, thÃªm Ä‘Ã¡ viÃªn, Ä‘Ã³ng náº¯p bÃ¬nh vÃ  láº¯c khoáº£ng 15 láº§n. Äá»• trÃ  sá»¯a dÃ¢u ra ly, thÃªm topping vÃ  thÆ°á»Ÿng thá»©c1.\n" +
                        "\t\t+  CÃ¡ch 2: Sá»­ dá»¥ng nÆ°á»›c Ã©p dÃ¢u tÆ°Æ¡i1. Cho dÃ¢u tÃ¢y vÃ o mÃ¡y xay, xay nhuyá»…n, dÃ¹ng rÃ¢y lá»c láº¥y nÆ°á»›c cá»‘t dÃ¢u. HÃ²a tan nÆ°á»›c cá»‘t trÃ , nÆ°á»›c cá»‘t dÃ¢u, bá»™t pha trÃ  sá»¯a chuyÃªn dá»¥ng, siro Ä‘Æ°á»ng. Cho há»—n há»£p vÃ o bÃ¬nh láº¯c, thÃªm Ä‘Ã¡ viÃªn, Ä‘Ã³ng náº¯p bÃ¬nh vÃ  láº¯c khoáº£ng 15 láº§n. Äá»• trÃ  sá»¯a dÃ¢u ra ly, thÃªm topping, trang trÃ­ thÃªm lÃ¡t dÃ¢u tÆ°Æ¡i vÃ  báº¡c hÃ  tÃ¹y thÃ­ch1.\n" +
                        "\t\t+ CÃ¡ch 3: Sá»­ dá»¥ng siro dÃ¢u vÃ  sá»¯a tÆ°Æ¡i1. Trá»™n Ä‘Æ°á»ng vÃ  siro dÃ¢u vá»›i trÃ  Ä‘Ã£ á»§. Cho sá»¯a tÆ°Æ¡i vÃ o ly rá»“i rÃ³t trÃ  vÃ o khuáº¥y Ä‘á»u. ThÃªm Ä‘Ã¡ vÃ  trÃ¢n chÃ¢u (náº¿u muá»‘n) vÃ o trÃ  sá»¯a3.", 2));
        foodList.add(new Food(1, "TrÃ  sá»¯a matcha", "TrÃ  sá»¯a",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.trasua_matcha, null)),
                "TrÃ  sá»¯a Matcha lÃ  má»™t mÃ³n Ä‘á»“ uá»‘ng thÆ¡m ngon, bÃ©o ngáº­y tá»« sá»¯a vÃ  cÃ³ vá»‹ Ä‘áº¯ng nháº¹ cÃ¹ng hÆ°Æ¡ng thÆ¡m tá»« matcha1. DÆ°á»›i Ä‘Ã¢y lÃ  cÃ¡ch lÃ m trÃ  sá»¯a Matcha táº¡i nhÃ :\n" +
                        "\n" +
                        "NguyÃªn liá»‡u:\n" +
                        "\n" +
                        "\t\t+ 3 gram bá»™t Matcha (cÃ³ thá»ƒ thay tháº¿ báº±ng bá»™t trÃ  xanh)\n" +
                        "\t\t+ 100ml sá»¯a tÆ°Æ¡i khÃ´ng Ä‘Æ°á»ng (hay 70 gram bá»™t sá»¯a)\n" +
                        "\t\t+ 30ml sá»¯a Ä‘áº·c\n" +
                        "\t\t+ 1 gÃ³i trÃ  xanh tÃºi lá»c\n" +
                        "\t\t+ ÄÃ¡ viÃªn2\n" +
                        "CÃ¡ch pha trÃ  sá»¯a Matcha:\n" +
                        "\n" +
                        "\t\t+ Cho tÃºi trÃ  xanh vÃ o 50ml nÆ°á»›c nÃ³ng, nÃªn Ä‘á»ƒ nhiá»‡t Ä‘á»™ khoáº£ng 80 Ä‘á»™ C. KhÃ´ng Ä‘áº­y náº¯p vÃ  á»§ trÃ  trong 2 â€“ 3 phÃºt rá»“i láº¥y tÃºi trÃ  ra.\n" +
                        "\t\t+ Cho vÃ o ly vÃ  khuáº¥y tan há»—n há»£p gá»“m: 3 gram bá»™t Matcha, 15 gram bá»™t sá»¯a, 50ml nÆ°á»›c cá»‘t trÃ  xanh tÃºi lá»c.\n" +
                        "\t\t+ Chuáº©n bá»‹ ly khÃ¡c, cho 30ml sá»¯a Ä‘áº·c vÃ  50ml nÆ°á»›c cá»‘t trÃ  xanh Ä‘un nÃ³ng vÃ o vÃ  khuáº¥y Ä‘áº¿n khi hÃ²a quyá»‡n.\n" +
                        "\t\t+ Cho há»—n há»£p sá»¯a Ä‘Ã£ khuáº¥y á»Ÿ trÃªn cÃ¹ng Ä‘Ã¡ viÃªn vÃ o bÃ¬nh láº¯c. Äáº­y náº¯p bÃ¬nh vÃ  láº¯c khoáº£ng 3 giÃ¢y cho há»—n há»£p hÃ²a quyá»‡n.\n" +
                        "\t\t+ RÃ³t sá»¯a láº¯c ra ly thá»§y tinh, rÃ³t pháº§n há»—n há»£p Matcha Ä‘Ã£ pha lÃªn trÃªn táº¡o thÃ nh lá»›p táº§ng ráº¥t Ä‘áº¹p máº¯t2.", 2));
        foodList.add(new Food(1, "TrÃ  sá»¯a truyá»n thá»‘ng", "TrÃ  sá»¯a",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.trasua_truyenthong, null)),
                "TrÃ  sá»¯a truyá»n thá»‘ng lÃ  má»™t mÃ³n Ä‘á»“ uá»‘ng ngon vÃ  thÆ¡m mÃ¡t, ráº¥t Ä‘Æ°á»£c Æ°a chuá»™ng. DÆ°á»›i Ä‘Ã¢y lÃ  cÃ¡ch lÃ m trÃ  sá»¯a truyá»n thá»‘ng táº¡i nhÃ :\n" +
                        "\n" +
                        "NguyÃªn liá»‡u:\n" +
                        "\n" +
                        "\t\t+ 70g trÃ  Ä‘en\n" +
                        "\t\t+ 200g Ä‘Æ°á»ng tráº¯ng\n" +
                        "\t\t+ 250g bá»™t sá»¯a\n" +
                        "\t\t+ 100ml sá»¯a Ä‘áº·c1\n" +
                        "CÃ¡ch lÃ m:\n" +
                        "\n" +
                        "\t\t+ Náº¥u sÃ´i 1 lÃ­t nÆ°á»›c, cho 70g trÃ  vÃ o khuáº¥y Ä‘á»u rá»“i á»§ 15 phÃºt sau Ä‘Ã³ lá»c láº¥y nÆ°á»›c trÃ 1.\n" +
                        "\t\t+ Cho 200g Ä‘Æ°á»ng tráº¯ng vÃ  250g bá»™t sá»¯a vÃ o nÆ°á»›c trÃ  vÃ  khuáº¥y Ä‘á»u1.\n" +
                        "\t\t+ Cuá»‘i cÃ¹ng cho 100ml sá»¯a Ä‘áº·c vÃ o. Khuáº¥y cho cÃ¡c nguyÃªn liá»‡u tan hoÃ n toÃ n thÃ¬ thÃªm 300g Ä‘Ã¡ viÃªn vÃ o khuáº¥y cho tan1.\n" +
                        "\t\t+ LÃºc nÃ y cÃ³ thá»ƒ lá»c qua rÃ¢y Ä‘á»ƒ Ä‘áº£m báº£o trÃ  khÃ´ng cÃ²n cáº·n1.", 2));
        foodList.add(new Food(1, "TrÃ  sá»¯a xoÃ i", "TrÃ  sá»¯a",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.trasua_xoai, null)),
                "TrÃ  sá»¯a xoÃ i lÃ  má»™t mÃ³n Ä‘á»“ uá»‘ng thÆ¡m ngon, mÃ¡t láº¡nh vÃ  vÃ´ cÃ¹ng lÃ½ tÆ°á»Ÿng cho nhá»¯ng ngÃ y hÃ¨ nÃ³ng ná»±c. DÆ°á»›i Ä‘Ã¢y lÃ  cÃ¡ch lÃ m trÃ  sá»¯a xoÃ i táº¡i nhÃ :\n" +
                        "\n" +
                        "NguyÃªn liá»‡u:\n" +
                        "\n" +
                        "\t\t+ 50 gr trÃ  Ä‘en hoáº·c trÃ  lÃ i\n" +
                        "\t\t+ 200 gr bá»™t sá»¯a\n" +
                        "\t\t+ 300 gr Ä‘Æ°á»ng tráº¯ng hoáº·c Ä‘Æ°á»ng nÃ¢u\n" +
                        "\t\t+ 1 gr muá»‘i\n" +
                        "\t\t+ 1.3l nÆ°á»›c sÃ´i\n" +
                        "\t\t+ ÄÃ¡ viÃªn\n" +
                        "\t\t+ 1l nÆ°á»›c sÃ´i Ä‘á»ƒ nguá»™i1\n" +
                        "\t\t+ XoÃ i chÃ­n bá» vá» vÃ  háº¡t, cáº¯t nhá»2\n" +
                        "\t\t+ Siro Ä‘Ã o, siro chanh dÃ¢y2\n" +
                        "CÃ¡ch lÃ m:\n" +
                        "\n" +
                        "\t\t+ Náº¥u sÃ´i 1 lÃ­t nÆ°á»›c, cho 50g trÃ  vÃ o khuáº¥y Ä‘á»u rá»“i á»§ 15 phÃºt sau Ä‘Ã³ lá»c láº¥y nÆ°á»›c trÃ 1.\n" +
                        "\t\t+ Cho vÃ o ly vÃ  khuáº¥y tan há»—n há»£p gá»“m: 200g bá»™t sá»¯a, 300g Ä‘Æ°á»ng tráº¯ng hoáº·c Ä‘Æ°á»ng nÃ¢u, vÃ  1.3l nÆ°á»›c sÃ´i1.\n" +
                        "\t\t+ Cho xoÃ i Ä‘Ã£ cáº¯t nhá» vÃ o mÃ¡y xay sinh tá»‘, thÃªm siro Ä‘Ã o, siro chanh dÃ¢y, má»™t chÃºt Ä‘Ã¡ viÃªn vÃ o rá»“i xay nhuyá»…n2.\n" +
                        "\t\t+ NgÃ¢m trÃ  vá»›i 150ml nÆ°á»›c nÃ³ng, sau 10 phÃºt lá»c bÃ£ láº¥y nÆ°á»›c cá»‘t, hÃ²a tan cÃ¹ng Ä‘Æ°á»ng vÃ  Ä‘á»ƒ nguá»™i bá»›t2.\n" +
                        "\t\t+ Cho Ä‘Ã¡ viÃªn vÃ o ly, Ä‘á»• nÆ°á»›c trÃ  vÃ o. XÃºc há»—n há»£p xoÃ i lÃªn trÃªn cÃ¹ng lÃ  hoÃ n thÃ nh2.", 2));

        // region foodSize
        foodSizeList = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= 55; i++) {
            foodSizeList.add(new FoodSize(i, 1, (random.nextInt(20) + 1) * 1000d));
            foodSizeList.add(new FoodSize(i, 2, (random.nextInt(20) + 21) * 1000d));
            foodSizeList.add(new FoodSize(i, 3, (random.nextInt(20) + 41) * 1000d));
        }

        // region foodSaved
        foodSavedList = new ArrayList<>();
        foodSavedList.add(new FoodSaved(1, 3, 1));
        foodSavedList.add(new FoodSaved(36, 3, 2));
        foodSavedList.add(new FoodSaved(3, 3, 2));
        foodSavedList.add(new FoodSaved(42, 3, 2));
        foodSavedList.add(new FoodSaved(11, 3, 1));
        foodSavedList.add(new FoodSaved(28, 1, 4));
        foodSavedList.add(new FoodSaved(40, 3, 3));
        foodSavedList.add(new FoodSaved(3, 3, 3));
        foodSavedList.add(new FoodSaved(42, 3, 3));
        foodSavedList.add(new FoodSaved(31, 3, 3));
        foodSavedList.add(new FoodSaved(20, 1, 4));

        // region notify
        notifyList = new ArrayList<>();
        notifyList.add(new Notify(1, "ChÃ o báº¡n má»›i!",
                "ChÃ o má»«ng báº¡n Ä‘áº¿n vá»›i á»©ng dá»¥ng á»¨ng dá»¥ng Äáº·t Äá»“ Ä‚n!", "02/09/2023"));
        notifyList.add(new Notify(2, "ThÃ´ng bÃ¡o chung!",
                "á»¨ng dá»¥ng Äáº·t Äá»“ Ä‚n dÃ¹ng cho viá»‡c lá»±a chá»n thÆ°á»Ÿng thá»©c mÃ³n Äƒn táº¡i nhÃ¡nh chÃ³ng.", "02/09/2023"));
        notifyList.add(new Notify(3, "Báº¡n Ä‘i Ä‘Ã¢u Ä‘áº¥y!",
                "á»¨ng dá»¥ng Äáº·t Äá»“ Ä‚n luÃ´n luÃ´n chÃ o Ä‘Ã³n báº¡n tráº£i nghiá»‡m.", "02/09/2023"));
        notifyList.add(new Notify(4, "NgÆ°á»i quáº£n lÃ½ app!",
                "HoÃ ng Anh Tiáº¿n(2k3) - VÅ© Máº¡nh Chiáº¿n(2k3) - Nguyá»…n ChÃ­ Háº£i Anh(2k3).", "02/09/2023"));

        // region notify to user
        notifyToUsers = new ArrayList<>();
        notifyToUsers.add(new NotifyToUser(3, 1));
        notifyToUsers.add(new NotifyToUser(3, 2));
        notifyToUsers.add(new NotifyToUser(3, 3));
        notifyToUsers.add(new NotifyToUser(3, 4));

        // region Order
        orderList = new ArrayList<>();
        orderList.add(new Order(1, 1, "Nam Tá»« LiÃªm", "4/3/2023", 0d, "Delivered"));
        orderList.add(new Order(2, 1, "Nam Tá»« LiÃªm", "5/3/2023", 0d, "Craft"));
        orderList.add(new Order(3, 3, "Triá»u KhÃºc", "4/3/2023", 0d, "Coming"));
        orderList.add(new Order(4, 4, "Nam Tá»« LiÃªm", "5/4/2023", 0d, "Craft"));
        orderList.add(new Order(5, 1, "HÃ  ÄÃ´ng", "4/5/2023", 0d, "Coming"));

        // region Order detail
        orderDetailList = new ArrayList<>();
        orderDetailList.add(new OrderDetail(1, 1, 2, 12000d, 1));
        orderDetailList.add(new OrderDetail(1, 2, 3, 15000d, 2));
        orderDetailList.add(new OrderDetail(2, 31, 2, 18000d, 1));
        orderDetailList.add(new OrderDetail(3, 3, 3, 25000d, 3));
        orderDetailList.add(new OrderDetail(3, 4, 3, 25000d, 1));
        orderDetailList.add(new OrderDetail(3, 25, 2, 18000d, 1));
        orderDetailList.add(new OrderDetail(4, 23, 2, 18000d, 2));
        orderDetailList.add(new OrderDetail(4, 32, 3, 25000d, 3));
        orderDetailList.add(new OrderDetail(5, 11, 2, 12000d, 1));
        orderDetailList.add(new OrderDetail(5, 17, 3, 15000d, 2));
        orderDetailList.add(new OrderDetail(5, 31, 2, 18000d, 1));
        orderDetailList.add(new OrderDetail(5, 33, 3, 25000d, 3));
        orderDetailList.add(new OrderDetail(5, 41, 3, 25000d, 1));
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
        //Create table "User"
        String queryCreateUser = "CREATE TABLE IF NOT EXISTS tblUser(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name NVARCHAR(200)," +
                "gender VARCHAR(10)," +
                "date_of_birth VARCHAR(20)," +
                "phone VARCHAR(15)," +
                "username VARCHAR(30)," +
                "password VARCHAR(100))";
        sqLiteDatabase.execSQL(queryCreateUser);

        //Create table "Restaurant" => Ä‘á»ƒ lÆ°u áº£nh trong SQLite ta dÃ¹ng BLOG (Binary Longer Object)
        String queryCreateRestaurant = "CREATE TABLE IF NOT EXISTS tblRestaurant(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name NVARCHAR(200), " +
                "address NVARCHAR(200)," +
                "phone CHAR(10)," +
                "image BLOB)";
        sqLiteDatabase.execSQL(queryCreateRestaurant);

        //Create table "RestaurantSaved"
        String queryCreateRestaurantSaved = "CREATE TABLE IF NOT EXISTS tblRestaurantSaved(" +
                "restaurant_id INTEGER, user_id INTEGER," +
                "PRIMARY KEY(restaurant_id, user_id))";
        sqLiteDatabase.execSQL(queryCreateRestaurantSaved);

        //Create table "Food"
        String queryCreateFood = "CREATE TABLE IF NOT EXISTS tblFood(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name NVARCHAR(200)," +
                "type NVARCHAR(200)," +
                "image BLOB," +
                "description NVARCHAR(200)," +
                "restaurant_id INTEGER)";
        sqLiteDatabase.execSQL(queryCreateFood);

        //Create table "FoodSize"
        String queryCreateFoodSize = "CREATE TABLE IF NOT EXISTS tblFoodSize(" +
                "food_id INTEGER," +
                "size INTEGER ," +
                "price DOUBLE," +
                "PRIMARY KEY (food_id, size))";
        sqLiteDatabase.execSQL(queryCreateFoodSize);

        //Create table "FoodSaved"
        String queryCreateFoodSaved = "CREATE TABLE IF NOT EXISTS tblFoodSaved(" +
                "food_id INTEGER," +
                "size INTEGER ," +
                "user_id INTEGER," +
                "PRIMARY KEY (food_id, size, user_id))";
        sqLiteDatabase.execSQL(queryCreateFoodSaved);

        //Create table "Order"
        String queryCreateOrder = "CREATE TABLE IF NOT EXISTS tblOrder(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "address NVARCHAR(200)," +
                "date_order VARCHAR(20)," +
                "total_value DOUBLE," +
                "status VARCHAR(200))";
        sqLiteDatabase.execSQL(queryCreateOrder);

        //Create table "OrderDetail"
        String queryCreateOrderDetail = "CREATE TABLE IF NOT EXISTS tblOrderDetail(" +
                "order_id INTEGER," +
                "food_id INTEGER," +
                "size INTEGER," +
                "price DOUBLE," +
                "quantity INTEGER," +
                "PRIMARY KEY (order_id, food_id, size))";
        sqLiteDatabase.execSQL(queryCreateOrderDetail);

        //Create table "Notify"
        String queryCreateNotify = "CREATE TABLE IF NOT EXISTS tblNotify(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title NVARCHAR(200)," +
                "content NVARCHAR(200)," +
                "date_make VARCHAR(20))";
        sqLiteDatabase.execSQL(queryCreateNotify);

        //Create table "NotifyToUser"
        String queryCreateNotifyToUser = "CREATE TABLE IF NOT EXISTS tblNotifyToUser(" +
                "notify_id INTEGER," +
                "user_id INTEGER," +
                "PRIMARY KEY (notify_id, user_id))";
        sqLiteDatabase.execSQL(queryCreateNotifyToUser);

        Log.i("SQLite", "DATABASE CREATED");
        addSampleData(sqLiteDatabase);
        Log.i("SQLite", "ADDED DATA");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("SQLite", "Upgrade SQLite");

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblNotifyToUser");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblNotify");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblFoodSaved");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblFoodSize");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblFood");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblOrderDetail");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblOrder");
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

