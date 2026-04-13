package com.nhom8.foody_order_app.activity.ActivityImpl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.activity.admin.admin_foodActivity;

public class ManagerHomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        LinearLayout btnQlyMonAn = findViewById(R.id.btnQlyMonAn);
        btnQlyMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerHomeActivity.this, admin_foodActivity.class);
                startActivity(intent);
            }
        });
    }
}
