package com.nhom8.foody_order_app.activity.ActivityImpl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.activity.FoodDetailsActivityImpl;
import com.nhom8.foody_order_app.repository.DAO;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHandler;
import com.nhom8.foody_order_app.model.Food;
import com.nhom8.foody_order_app.model.FoodSaved;
import com.nhom8.foody_order_app.model.FoodSize;
import com.nhom8.foody_order_app.model.Order;
import com.nhom8.foody_order_app.model.OrderDetail;
import com.nhom8.foody_order_app.model.Restaurant;

import java.util.ArrayList;

public class FoodDetailsActivity extends AppCompatActivity implements FoodDetailsActivityImpl {
    private ImageView image, btnAddQuantity, btnSubQuantity;
    private LinearLayout layout_sizeS, layout_sizeM, layout_sizeL, btnAddToCart, btnSavedFood;
    private CheckBox checkBoxFavorite, checkBoxCart;
    private TextView tvName, tvDescription, tvPrice,
            tvRestaurantName, tvRestaurantAddress,
            tvPriceSizeS, tvPriceSizeM, tvPriceSizeL,
            tvQuantity;

    //    private Button btnAddToCart, btnSavedFood;
    public static Integer userID; // láº¥y ra userId
    private static int quantity;
    public static FoodSize foodSize;    // láº¥y ra gias cáº£ vÃ  kÃ­ch thÆ°á»›c
    private DAO dao;    // káº¿t ná»‘i vá»›i CSDL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        quantity = 1;
        dao = new DAO(this);

        initializeUI();
        referenceComponent();
        LoadData();
    }

    private String getRoundPrice(Double price) {
        return Math.round(price) + " VNÄ";
    }

    private String getTotalPrice() {
        return Math.round(foodSize.getPrice() * quantity) + " VNÄ";
    }

    @Override
    public void initializeUI() {
        // initUI
        tvName = findViewById(R.id.tvFoodName);
        tvDescription = findViewById(R.id.tvDescription);
        tvPrice = findViewById(R.id.tvPrice);
        image = findViewById(R.id.image);

        layout_sizeS = findViewById(R.id.layout_size_S);
        layout_sizeM = findViewById(R.id.layout_size_M);
        layout_sizeL = findViewById(R.id.layout_size_L);

        tvPriceSizeS = findViewById(R.id.tvPriceSizeS);
        tvPriceSizeM = findViewById(R.id.tvPriceSizeM);
        tvPriceSizeL = findViewById(R.id.tvPriceSizeL);

        tvQuantity = findViewById(R.id.tvFoodQuantity_Food);

        tvRestaurantName = findViewById(R.id.tvRestaurantName);
        tvRestaurantAddress = findViewById(R.id.tvRestaurantAddress);

        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnSavedFood = findViewById(R.id.btnSavedFood);
        checkBoxCart = findViewById(R.id.checkBoxCart);
        checkBoxFavorite = findViewById(R.id.checkBoxFavorite);

        btnAddQuantity = findViewById(R.id.btnAddQuantity_Food);
        btnSubQuantity = findViewById(R.id.btnSubQuantity_Food);
    }

    @Override
    public void referenceComponent() {
        // quay vá» mÃ n hÃ¬nh trÆ°á»›c Ä‘Ã³.
        findViewById(R.id.btnBack).setOnClickListener(view -> this.finish());

        btnAddToCart.setOnClickListener(view -> {
            addCartProduct();
        });

        checkBoxCart.setOnClickListener(view -> {
            addCartProduct();
        });

        btnSavedFood.setOnClickListener(view -> {
            saveFood();
        });

        checkBoxFavorite.setOnClickListener(view -> {
            saveFood();
        });

        // tÄƒng sá»‘ lÆ°á»£ng mÃ³n Äƒn
        btnAddQuantity.setOnClickListener(view -> {
            quantity++;
            tvQuantity.setText(String.format("%s", quantity));
            tvPrice.setText(getTotalPrice());
        });


        // giáº£m sá»‘ lÆ°á»£ng mÃ³n Äƒn
        btnSubQuantity.setOnClickListener(view -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.format("%s", quantity));
                tvPrice.setText(getTotalPrice());
            }
        });
    }


    // Ä‘áº©y thÃ´ng tin vÃ o giá» hÃ ng
    private void addCartProduct() {
        // Make cart if don't have
        Cursor cursor = dao.getCart(userID);

        // (di chuyá»ƒn con trá» Cursor Ä‘áº¿n vá»‹ trÃ­ Ä‘Ãºng trÆ°á»›c khi truy cáº­p dá»¯ liá»‡u.)
        // vÃ  kiá»ƒm tra xem cursor nÃ y Ä‘ang khÃ´ng trá» Ä‘áº¿n vá»‹ trÃ­ Ä‘áº§u tiÃªn khÃ´ng.
        if (!cursor.moveToFirst()) {
            dao.addOrder(new Order(1, userID, "", "", 0d, "Craft")); // ta sáº½ add má»™t order vÃ  (hay thÃªm vÃ o pháº§n giá» hÃ ng)
            cursor = dao.getCart(userID); // láº¥y ra Ä‘á»‘i tÆ°á»£ng cursor Ä‘áº¥y vá»›i userID
//            System.out.println("Ä‘Ã£ Ä‘Æ°á»£c add Ä‘á»‘i tÆ°á»£ng vÃ o Ä‘Ã¢y rá»“i.");
        }

        // add order detail
        cursor.moveToFirst();   // (di chuyá»ƒn con trá» Cursor Ä‘áº¿n vá»‹ trÃ­ Ä‘Ãºng trÆ°á»›c khi truy cáº­p dá»¯ liá»‡u.)

        OrderDetail orderDetail = dao.getExistOrderDetail(cursor.getInt(0), foodSize);
        if (orderDetail != null) {
            orderDetail.setQuantity(orderDetail.getQuantity() + quantity);
            if (dao.updateQuantity(orderDetail)) {
                Toast.makeText(this, getResources().getString(R.string.add_dish_successfully), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.add_error), Toast.LENGTH_SHORT).show();
            }

        } else {
            boolean addOrderDetail = dao.addOrderDetail(new OrderDetail(cursor.getInt(0),
                    foodSize.getFoodId(), foodSize.getSize(), foodSize.getPrice(), quantity));

            if (addOrderDetail) {
                Toast.makeText(this, getResources().getString(R.string.add_dish_successfully), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.add_error), Toast.LENGTH_SHORT).show();
            }
        }
    }


    // lÆ°u láº¡i thÃ´ng tin mÃ³n Äƒn
    private void saveFood() {
        boolean addFoodSaved = dao.addFoodSaved(new FoodSaved(foodSize.getFoodId(), foodSize.getSize(), userID));
        if (addFoodSaved) {
            Toast.makeText(this, getResources().getString(R.string.SAVED_DISHED), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.INFORMATION_EXISTED), Toast.LENGTH_SHORT).show();
        }
    }


    // setting giÃ¡ máº·c Ä‘á»‹nh
    private void SetPriceDefault(Double price) {
        tvPrice.setText(getRoundPrice(price));
        quantity = 1;
        tvQuantity.setText("1");
    }

    // load Ä‘á»ƒ Ä‘áº©y táº¥t cáº£ thÃ´ng tin vá» sáº£n pháº©m lÃªn
    @Override
    public void LoadData() {
        Intent intent = getIntent();
        if (intent != null) {
            Food food = (Food) intent.getSerializableExtra("food");

            assert food != null;
            ArrayList<FoodSize> foodSizeArrayList = dao.getAllFoodSize(food.getId());

            // size S
            if (foodSizeArrayList.get(0) != null) {
                if (foodSize == null) foodSize = foodSizeArrayList.get(0);
                tvPriceSizeS.setText(String.format("+ %s", foodSizeArrayList.get(0).getPrice()));
                layout_sizeS.setOnClickListener(view -> {
                    SetPriceDefault(foodSizeArrayList.get(0).getPrice());
                    foodSize = foodSizeArrayList.get(0);
                });
            } else {
                layout_sizeS.setVisibility(View.INVISIBLE);
            }

            // size M
            if (foodSizeArrayList.get(1) != null) {
                tvPriceSizeM.setText(String.format("+ %s", foodSizeArrayList.get(1).getPrice()));
                layout_sizeM.setOnClickListener(view -> {
                    SetPriceDefault(foodSizeArrayList.get(1).getPrice());
                    foodSize = foodSizeArrayList.get(1);
                });
            } else {
                layout_sizeM.setVisibility(View.INVISIBLE);
            }

            // Size L
            if (foodSizeArrayList.get(2) != null) {
                tvPriceSizeL.setText(String.format("+ %s", foodSizeArrayList.get(2).getPrice()));
                layout_sizeL.setOnClickListener(view -> {
                    SetPriceDefault(foodSizeArrayList.get(2).getPrice());
                    foodSize = foodSizeArrayList.get(2);
                });
            } else {
                layout_sizeL.setVisibility(View.INVISIBLE);
            }

            // Set information: Ä‘áº©y lÃªn cÃ¡c thÃ´ng tin cÆ¡ báº£n (tÃªn, miÃªu táº£, áº£nh)
            tvName.setText(food.getName());
            tvDescription.setText(food.getDescription());
            image.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(food.getImage()));

            Restaurant restaurant = dao.getRestaurantInformation(food.getRestaurantId());
            tvRestaurantName.setText(String.format("+ TÃªn cá»­a hÃ ng \n \t\t %s", restaurant.getName()));
            tvRestaurantAddress.setText(String.format("+ Äá»‹a chá»‰\n \t\t %s", restaurant.getAddress()));

            // set giÃ¡ tá»•ng
            tvPrice.setText(getRoundPrice(foodSize.getPrice()));
        }
    }
}
