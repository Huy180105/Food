package com.nhom8.foody_order_app.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.activity.admin.ingredient.EditIngredientActivity;
import com.nhom8.foody_order_app.activity.admin.ingredient.IngredientDetailActivity;
import com.nhom8.foody_order_app.model.ManagerIngredient;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHelper;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private final Context context;
    private final List<ManagerIngredient> ingredientList;

    public IngredientAdapter(Context context, List<ManagerIngredient> ingredientList) {
        this.context = context;
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        ManagerIngredient ingredient = ingredientList.get(holder.getAdapterPosition());

        holder.tvIngredientName.setText(ingredient.getName());
        holder.tvIngredientStock.setText("Ton: " + ingredient.getQuantity() + " " + ingredient.getUnit());

        int imageId = context.getResources().getIdentifier(ingredient.getImage(), "drawable", context.getPackageName());
        holder.imgIngredient.setImageResource(imageId != 0 ? imageId : R.drawable.ic_food);

        holder.btnViewIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(context, IngredientDetailActivity.class);
            intent.putExtra("name", ingredient.getName());
            intent.putExtra("quantity", ingredient.getQuantity());
            intent.putExtra("unit", ingredient.getUnit());
            intent.putExtra("image", ingredient.getImage());
            context.startActivity(intent);
        });

        holder.btnEditIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditIngredientActivity.class);
            intent.putExtra("id", ingredient.getId());
            intent.putExtra("name", ingredient.getName());
            intent.putExtra("quantity", ingredient.getQuantity());
            intent.putExtra("unit", ingredient.getUnit());
            intent.putExtra("image", ingredient.getImage());
            context.startActivity(intent);
        });

        holder.btnDeleteIngredient.setOnClickListener(v -> {
            int currentPos = holder.getAdapterPosition();
            if (currentPos == RecyclerView.NO_POSITION) {
                return;
            }

            new AlertDialog.Builder(context)
                    .setTitle("Xac nhan xoa")
                    .setMessage("Ban co chac muon xoa '" + ingredient.getName() + "' khong?")
                    .setPositiveButton("Co", (dialog, which) -> {
                        DatabaseHelper db = new DatabaseHelper(context);
                        if (db.deleteIngredient(ingredient.getId()) > 0) {
                            ingredientList.remove(currentPos);
                            notifyItemRemoved(currentPos);
                            notifyItemRangeChanged(currentPos, ingredientList.size());
                            Toast.makeText(context, "Da xoa thanh cong", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Khong", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return ingredientList == null ? 0 : ingredientList.size();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgIngredient;
        final TextView tvIngredientName;
        final TextView tvIngredientStock;
        final ImageButton btnViewIngredient;
        final ImageButton btnEditIngredient;
        final ImageButton btnDeleteIngredient;

        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIngredient = itemView.findViewById(R.id.imgIngredient);
            tvIngredientName = itemView.findViewById(R.id.tvIngredientName);
            tvIngredientStock = itemView.findViewById(R.id.tvIngredientStock);
            btnViewIngredient = itemView.findViewById(R.id.btnViewIngredient);
            btnEditIngredient = itemView.findViewById(R.id.btnEditIngredient);
            btnDeleteIngredient = itemView.findViewById(R.id.btnDeleteIngredient);
        }
    }
}
