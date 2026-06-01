package com.nhom8.foody_order_app.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.model.AdminOrderSummary;

import java.util.ArrayList;
import java.util.List;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.OrderViewHolder> {
    public interface OnOrderClickListener {
        void onOrderClick(AdminOrderSummary order);
    }

    private final List<AdminOrderSummary> orders = new ArrayList<>();
    private final OnOrderClickListener listener;

    public AdminOrderAdapter(OnOrderClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<AdminOrderSummary> newOrders) {
        orders.clear();
        if (newOrders != null) {
            orders.addAll(newOrders);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        AdminOrderSummary order = orders.get(position);
        holder.txtOrderId.setText("Don #" + order.getId());
        holder.txtCustomerName.setText(order.getCustomerName());
        holder.txtOrderDate.setText(order.getDateOfOrder());
        holder.txtTotalMoney.setText(String.format("%,.0f VND", order.getTotalValue()));
        holder.txtStatus.setText(order.getStatus());
        holder.txtStatus.setTextColor(getStatusColor(order.getStatus()));
        holder.itemView.setOnClickListener(v -> listener.onOrderClick(order));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    private int getStatusColor(String status) {
        if ("Delivered".equalsIgnoreCase(status)) {
            return Color.parseColor("#2E7D32");
        }
        if ("Canceled".equalsIgnoreCase(status)) {
            return Color.parseColor("#C62828");
        }
        if ("Coming".equalsIgnoreCase(status)) {
            return Color.parseColor("#EF6C00");
        }
        return Color.parseColor("#1565C0");
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtOrderId;
        private final TextView txtOrderDate;
        private final TextView txtCustomerName;
        private final TextView txtTotalMoney;
        private final TextView txtStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtOrderDate = itemView.findViewById(R.id.txtOrderDate);
            txtCustomerName = itemView.findViewById(R.id.txtCustomerName);
            txtTotalMoney = itemView.findViewById(R.id.txtTotalMoney);
            txtStatus = itemView.findViewById(R.id.txtOrderStatus);
        }
    }
}
