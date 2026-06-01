package com.nhom8.foody_order_app.activity.admin;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.adapter.AdminOrderDetailAdapter;
import com.nhom8.foody_order_app.model.AdminOrderSummary;
import com.nhom8.foody_order_app.repository.admin.AdminInvoiceDAO;

public class AdminOrderDetailActivity extends AppCompatActivity {
    private AdminInvoiceDAO invoiceDAO;
    private AdminOrderDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_detail);

        invoiceDAO = new AdminInvoiceDAO(this);
        adapter = new AdminOrderDetailAdapter();

        RecyclerView rvOrderDetail = findViewById(R.id.rvOrderDetail);
        rvOrderDetail.setLayoutManager(new LinearLayoutManager(this));
        rvOrderDetail.setAdapter(adapter);

        findViewById(R.id.btnBackOrderDetail).setOnClickListener(v -> finish());

        int orderId = getIntent().getIntExtra("order_id", -1);
        bindOrder(orderId);
    }

    private void bindOrder(int orderId) {
        AdminOrderSummary order = invoiceDAO.getOrderById(orderId);
        if (order == null) {
            finish();
            return;
        }

        ((TextView) findViewById(R.id.txtDetailTitle)).setText("Chi tiết đơn #" + order.getId());
        ((TextView) findViewById(R.id.txtCustomerInfo)).setText(order.getCustomerName() + " | " + order.getPhone());
        ((TextView) findViewById(R.id.txtAddressInfo)).setText(order.getAddress());
        ((TextView) findViewById(R.id.txtDateInfo)).setText(order.getDateOfOrder());
        ((TextView) findViewById(R.id.txtStatusInfo)).setText(order.getStatus());
        ((TextView) findViewById(R.id.txtTotalFinal)).setText(String.format("%,.0f VND", order.getTotalValue()));

        adapter.submitList(invoiceDAO.getOrderItems(orderId));
    }
}
