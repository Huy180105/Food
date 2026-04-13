package com.nhom8.foody_order_app.components;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.activity.ActivityImpl.HomeActivity;
import com.nhom8.foody_order_app.fragments.ChatFragment;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHandler;
import com.nhom8.foody_order_app.model.Food;
import com.nhom8.foody_order_app.model.OrderDetail;

public class CartCard extends LinearLayout implements BaseComponent {
    private Food food;
    private String restaurantName;
    private OrderDetail card;
    private boolean activateControl;
    private int quantity;
    private ImageView image;
    private TextView tvName, tvSize, tvRestaurantName, tvPrice, tvQuantity;
    private ImageView btnSub, btnAdd, btnDelete;
    private LinearLayout layout;


    public CartCard(Context context) {
        super(context);
        initControl(context);
    }

    public CartCard(Context context, Food food, String restaurantName, OrderDetail card) {
        super(context);
        this.food = food;
        this.restaurantName = restaurantName;
        this.card = card;
        this.activateControl = true;
        initControl(context);
    }

    public CartCard(Context context, Food food, String restaurantName, OrderDetail card, boolean activateControl) {
        super(context);
        this.food = food;
        this.restaurantName = restaurantName;
        this.card = card;
        this.activateControl = activateControl;
        initControl(context);
    }

    @Override
    public void initUI() {
        image = findViewById(R.id.imageCartFood);
        tvName = findViewById(R.id.tvFoodNameCart);
        tvSize = findViewById(R.id.tvFoodSizeCart);
        tvRestaurantName = findViewById(R.id.tvRestaurantNameCart);
        tvPrice = findViewById(R.id.tvFoodPriceCart);
        tvQuantity = findViewById(R.id.tvFoodQuantity_Cart); // Sá»‘ lÆ°á»£ng mÃ³n Äƒn
        btnSub = findViewById(R.id.btnSubQuantity_Cart);
        btnAdd = findViewById(R.id.btnAddQuantity_Cart);
        btnDelete = findViewById(R.id.btnDeleteCartItem);
        layout = findViewById(R.id.layout_btn_delete);
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_cart_card, this);

        initUI(); // init UI
        quantity = card.getQuantity();

        // giáº£m sá»‘ lÆ°á»£ng mÃ³n Äƒn xuá»‘ng
        btnSub.setOnClickListener(view -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText("" + quantity);
                card.setQuantity(quantity);
                HomeActivity.dao.updateQuantity(card);
            }
        });

        // tÄƒng sá»‘ lÆ°á»£ng mÃ³n Äƒn lÃªn
        btnAdd.setOnClickListener(view -> {
            quantity++;
            tvQuantity.setText("" + quantity);
            card.setQuantity(quantity);
            HomeActivity.dao.updateQuantity(card);
        });


        // xÃ³a mÃ³n Äƒn Ä‘Ã³ khá»i giá» hÃ ng
        btnDelete.setOnClickListener(view -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext()); // khai bÃ¡o 1 dialog
            dialog.setMessage("Báº¡n cÃ³ muá»‘n xÃ³a mÃ³n " + food.getName() + " khÃ´ng ?");
            dialog.setPositiveButton("CÃ³", (dialogInterface, i) -> {
                if (HomeActivity.dao.deleteOrderDetailByOrderIdAndFoodId(card.getOrderId(), food.getId())) {
//                    ChatFragment.cartContainer.removeView(this);
                    ChatFragment.cartContainer.removeView(this); // xÃ³a view Ä‘Ã³ (hay lÃ  xÃ³a mÃ³n Äƒn Ä‘Ã³ khá»i giá» hÃ ng)

                    Toast.makeText(context, "ÄÃ£ xÃ³a thÃ´ng tin thÃ nh cÃ´ng.", Toast.LENGTH_SHORT).show(); // Ä‘áº©y ra thÃ´ng bÃ¡o
                } else {
                    Toast.makeText(context, "Gáº·p lá»—i khi xÃ³a thÃ´ng tin!!!!", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.setNegativeButton("KhÃ´ng", (dialogInterface, i) -> {
            });
            dialog.show();
        });


        if (!activateControl) {
            btnDelete.setVisibility(GONE);
            layout.setBackgroundColor(Color.rgb(255, 70, 70));
            btnAdd.setEnabled(activateControl);
            btnSub.setEnabled(activateControl);
        }

        // Set information for cart card
        image.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(food.getImage()));
        tvName.setText(food.getName());
        switch (card.getSize()) {
            case 1:
                tvSize.setText("Size S");
                break;
            case 2:
                tvSize.setText("Size M");
                break;
            case 3:
                tvSize.setText("Size L");
                break;
        }
        tvRestaurantName.setText(restaurantName);
        tvPrice.setText(getRoundPrice(card.getPrice()));
        tvQuantity.setText("" + quantity);
    }

    private String getRoundPrice(Double price) {
        return Math.round(price) + " VNÄ";
    }
}

