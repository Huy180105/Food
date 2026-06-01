package com.nhom8.foody_order_app.activity.admin.ingredient;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom8.foody_order_app.R;

public class IngredientDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_detail);

        ImageView imgBack = findViewById(R.id.imgBackDetail);
        ImageView imgDetail = findViewById(R.id.imgDetail);
        TextView tvName = findViewById(R.id.tvDetailName);
        TextView tvStock = findViewById(R.id.tvDetailStock);
        TextView tvUnit = findViewById(R.id.tvDetailUnit);

        String name = getIntent().getStringExtra("name");
        double quantity = getIntent().getDoubleExtra("quantity", 0);
        String unit = getIntent().getStringExtra("unit");
        String imageName = getIntent().getStringExtra("image");

        tvName.setText(name);
        tvStock.setText(String.valueOf(quantity));
        tvUnit.setText(unit);

        int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        imgDetail.setImageResource(resId != 0 ? resId : R.drawable.ic_food);
        imgBack.setOnClickListener(v -> finish());
    }
}
