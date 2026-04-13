package com.nhom8.foody_order_app.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.activity.ActivityImpl.FoodDetailsActivity;
import com.nhom8.foody_order_app.activity.ActivityImpl.HomeActivity;
import com.nhom8.foody_order_app.model.Food;
import com.nhom8.foody_order_app.model.FoodSize;
import com.nhom8.foody_order_app.model.Restaurant;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHandler;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private final List<Food> foodList;

    public FoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageFood;
        private final TextView tvNameFood;
        private final TextView tvPriceFood;
        private final TextView tvFoodRestaurantName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageFood = itemView.findViewById(R.id.imageFood);
            tvNameFood = itemView.findViewById(R.id.tvNameFood);
            tvPriceFood = itemView.findViewById(R.id.tvPriceFood);
            tvFoodRestaurantName = itemView.findViewById(R.id.tvFoodRestaurantName);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_food_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = foodList.get(position);
        FoodSize defaultSize = HomeActivity.dao.getFoodDefaultSize(food.getId());
        Restaurant restaurant = HomeActivity.dao.getRestaurantInformation(food.getRestaurantId());

        holder.imageFood.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(food.getImage()));
        holder.tvNameFood.setText(food.getName());
        holder.tvPriceFood.setText(String.format("%.0f VNĐ", defaultSize.getPrice()));
        holder.tvFoodRestaurantName.setText(restaurant.getName());

        holder.itemView.setOnClickListener(v -> {
            FoodDetailsActivity.foodSize = defaultSize;
            Intent intent = new Intent(v.getContext(), FoodDetailsActivity.class);
            intent.putExtra("food", food);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
}
