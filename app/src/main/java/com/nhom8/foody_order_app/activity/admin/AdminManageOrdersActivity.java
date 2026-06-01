package com.nhom8.foody_order_app.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.adapter.AdminOrderAdapter;
import com.nhom8.foody_order_app.model.AdminOrderSummary;
import com.nhom8.foody_order_app.repository.admin.AdminInvoiceDAO;

import java.util.ArrayList;

public class AdminManageOrdersActivity extends AppCompatActivity {
    private AdminInvoiceDAO invoiceDAO;
    private AdminOrderAdapter adapter;
    private TextView txtCountOrder;
    private TextView txtSumRevenue;
    private EditText edtSearchOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_orders);

        invoiceDAO = new AdminInvoiceDAO(this);
        txtCountOrder = findViewById(R.id.txtCountOrder);
        txtSumRevenue = findViewById(R.id.txtSumRevenue);
        edtSearchOrder = findViewById(R.id.edtSearchOrder);

        RecyclerView rvOrders = findViewById(R.id.rvOrders);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdminOrderAdapter(this::openOrderDetail);
        rvOrders.setAdapter(adapter);

        edtSearchOrder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadOrders(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        findViewById(R.id.btnBackManageOrders).setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOrders(edtSearchOrder.getText().toString());
    }

    private void loadOrders(String keyword) {
        ArrayList<AdminOrderSummary> orders = invoiceDAO.getOrders(keyword);
        adapter.submitList(orders);
        txtCountOrder.setText(String.valueOf(invoiceDAO.getTotalOrderCount()));
        txtSumRevenue.setText(String.format("%,.0f VND", invoiceDAO.getTotalRevenue()));
    }

    private void openOrderDetail(AdminOrderSummary order) {
        Intent intent = new Intent(this, AdminOrderDetailActivity.class);
        intent.putExtra("order_id", order.getId());
        startActivity(intent);
    }
}
