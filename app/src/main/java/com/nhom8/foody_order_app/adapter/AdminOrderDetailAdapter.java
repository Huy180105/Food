package com.nhom8.foody_order_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.model.AdminOrderItem;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class AdminOrderDetailAdapter extends RecyclerView.Adapter<AdminOrderDetailAdapter.OrderItemViewHolder> {
    private final List<AdminOrderItem> items = new ArrayList<>();

    public void submitList(List<AdminOrderItem> newItems) {
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_order_detail, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        AdminOrderItem item = items.get(position);
        
        if (item.getFoodImage() != null) {
            holder.imgFood.setImageBitmap(DatabaseHelper.convertByteArrayToBitmap(item.getFoodImage()));
        }
        
        holder.txtFoodName.setText("Tên món: " + item.getFoodName());
        holder.txtSize.setText("Kích cỡ: Size " + mapSize(item.getSize()));
        holder.txtQuantity.setText("Số lượng: " + item.getQuantity());
        holder.txtUnitPrice.setText(String.format("Đơn giá: %,.0f VND", item.getUnitPrice()));
        holder.txtLineTotal.setText(String.format("Thành tiền: %,.0f VND", item.getLineTotal()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private String mapSize(int size) {
        if (size == 1) {
            return "S";
        }
        if (size == 2) {
            return "M";
        }
        if (size == 3) {
            return "L";
        }
        return String.valueOf(size);
    }

    static class OrderItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgFood;
        private final TextView txtFoodName;
        private final TextView txtSize;
        private final TextView txtQuantity;
        private final TextView txtUnitPrice;
        private final TextView txtLineTotal;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFoodDetail);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtSize = itemView.findViewById(R.id.txtFoodSize);
            txtQuantity = itemView.findViewById(R.id.txtFoodQuantity);
            txtUnitPrice = itemView.findViewById(R.id.txtFoodUnitPrice);
            txtLineTotal = itemView.findViewById(R.id.txtFoodLineTotal);
        }
    }
}
