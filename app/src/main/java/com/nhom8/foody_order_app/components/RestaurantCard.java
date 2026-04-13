package com.nhom8.foody_order_app.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.activity.ActivityImpl.HomeActivity;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHandler;
import com.nhom8.foody_order_app.fragments.SavedFragment;
import com.nhom8.foody_order_app.model.Restaurant;
import com.nhom8.foody_order_app.model.RestaurantSaved;

public class RestaurantCard extends LinearLayout implements BaseComponent{
    private Restaurant restaurant;
    private boolean isSaved;
    private ImageView image;
    private TextView tvRestaurantName, tvRestaurantAddress;

//    private LinearLayout btnSavedShop;
    private CheckBox btnSavedShop;

    private TextView textViewSaveShop;

    public RestaurantCard(Context context, Restaurant restaurant, boolean isSaved) {
        super(context);
        this.restaurant = restaurant;
        this.isSaved = isSaved;
        initControl(context);
    }

    public RestaurantCard(Context context){
        super(context);
    }

    @Override
    public void initUI() {
        image = findViewById(R.id.imageRestaurant);
        tvRestaurantName = findViewById(R.id.tvRestaurantName_res_cart);
        tvRestaurantAddress = findViewById(R.id.tvRestaurantAddress_res_cart);
//        btnSavedShop = findViewById(R.id.btnSavedShop);
        btnSavedShop = findViewById(R.id.btnSavedShop);

        textViewSaveShop = findViewById(R.id.textViewSaveShop);
    }

    @Override
    public void initControl(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_restaurant_card, this);

        initUI();

        if(isSaved){
            textViewSaveShop.setText("Bá»Ž LÆ¯U");
        }
        btnSavedShop.setOnClickListener(view ->{
            if(isSaved){
                if(HomeActivity.dao.deleteRestaurantSaved(new RestaurantSaved(restaurant.getId(), HomeActivity.user.getId()))){
                    Toast.makeText(context, "ÄÃ£ bá» lÆ°u thÃ´ng tin nhÃ  hÃ ng!", Toast.LENGTH_SHORT).show();
                    SavedFragment.saved_container.removeView(this);
                } else {
                    Toast.makeText(context, "CÃ³ lá»—i khi xÃ³a thÃ´ng tin!", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(HomeActivity.dao.addRestaurantSaved(new RestaurantSaved(restaurant.getId(), HomeActivity.user.getId()))){
                    Toast.makeText(context, "LÆ°u thÃ´ng tin nhÃ  hÃ ng thÃ nh cÃ´ng!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Báº¡n Ä‘Ã£ lÆ°u thÃ´ng tin nhÃ  hÃ ng nÃ y rá»“i!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // set information
        image.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(restaurant.getImage()));
        tvRestaurantName.setText(restaurant.getName());
        tvRestaurantAddress.setText(restaurant.getAddress());
    }
}


