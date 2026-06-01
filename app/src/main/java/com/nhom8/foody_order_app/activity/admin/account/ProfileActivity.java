package com.nhom8.foody_order_app.activity.admin.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.activity.ActivityImpl.RoleSelectionActivity;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHelper;

public class ProfileActivity extends AppCompatActivity {
    private static final String STORE_PREFS = "store_info";
    private static final String KEY_MANAGER_USERNAME = "manager_username";
    private static final String KEY_MANAGER_PASSWORD = "manager_password";

    private DatabaseHelper dbHelper;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_menu);

        SharedPreferences pref = getSharedPreferences(STORE_PREFS, MODE_PRIVATE);
        currentUsername = pref.getString(KEY_MANAGER_USERNAME, "");
        dbHelper = new DatabaseHelper(this);

        ImageView btnBack = findViewById(R.id.btnBackProfile);
        LinearLayout btnThongTin = findViewById(R.id.btnThongTinCaNhan);
        LinearLayout btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
        LinearLayout btnXoaTaiKhoan = findViewById(R.id.btnXoaTaiKhoan);
        LinearLayout btnLogout = findViewById(R.id.btnDangXuat);

        btnBack.setOnClickListener(v -> finish());
        btnThongTin.setOnClickListener(v -> startActivity(new Intent(this, ThongtinchitietActivity.class)));
        btnDoiMatKhau.setOnClickListener(v -> startActivity(new Intent(this, DoimatkhauActivity.class)));
        btnXoaTaiKhoan.setOnClickListener(v -> confirmDeleteAccount());
        btnLogout.setOnClickListener(v -> confirmLogout());
    }

    private void confirmDeleteAccount() {
        if (currentUsername.isEmpty()) {
            Toast.makeText(this, "Khong tim thay tai khoan quan ly.", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Xac nhan xoa tai khoan")
                .setMessage("Tai khoan quan ly se bi xoa vinh vien. Ban co chac chan khong?")
                .setPositiveButton("Xoa", (dialog, which) -> {
                    int result = dbHelper.deleteUser(currentUsername);
                    if (result > 0) {
                        Toast.makeText(this, "Da xoa tai khoan.", Toast.LENGTH_SHORT).show();
                        performLogout();
                    } else {
                        Toast.makeText(this, "Khong the xoa tai khoan.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Huy", null)
                .show();
    }

    private void confirmLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Xac nhan dang xuat")
                .setMessage("Ban co muon dang xuat tai khoan quan ly khong?")
                .setPositiveButton("Dang xuat", (dialog, which) -> performLogout())
                .setNegativeButton("Huy", null)
                .show();
    }

    private void performLogout() {
        SharedPreferences.Editor editor = getSharedPreferences(STORE_PREFS, MODE_PRIVATE).edit();
        editor.remove(KEY_MANAGER_USERNAME);
        editor.remove(KEY_MANAGER_PASSWORD);
        editor.apply();

        getSharedPreferences("UserPrefs", MODE_PRIVATE)
                .edit()
                .remove("current_user")
                .apply();

        getSharedPreferences(RoleSelectionActivity.ROLE_PREFS, MODE_PRIVATE)
                .edit()
                .remove(RoleSelectionActivity.KEY_USER_ROLE)
                .apply();

        Intent intent = new Intent(this, RoleSelectionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
