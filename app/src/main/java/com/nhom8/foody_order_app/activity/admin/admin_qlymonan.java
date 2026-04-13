package com.nhom8.foody_order_app.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.nhom8.foody_order_app.R;

public class admin_qlymonan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Nạp giao diện chứa nút bấm
        setContentView(R.layout.activity_main_admin);

        // 1. Ánh xạ nút từ XML
        LinearLayout btnMonAn = findViewById(R.id.btnQlyMonAn);

        // 2. Bắt sự kiện click
        btnMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SỬA TẠI ĐÂY: Dùng tên Class admin_qlymonan
                Intent intent = new Intent(admin_qlymonan.this, admin_foodActivity.class);
                startActivity(intent);
            }
        });
    }
}