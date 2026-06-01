package com.nhom8.foody_order_app.activity.admin.account;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHelper;

public class DoimatkhauActivity extends AppCompatActivity {
    private static final String STORE_PREFS = "store_info";
    private static final String KEY_MANAGER_USERNAME = "manager_username";
    private static final String KEY_MANAGER_PASSWORD = "manager_password";

    private EditText etOldPass;
    private EditText etNewPass;
    private EditText etConfirmPass;
    private DatabaseHelper dbHelper;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        SharedPreferences pref = getSharedPreferences(STORE_PREFS, MODE_PRIVATE);
        currentUsername = pref.getString(KEY_MANAGER_USERNAME, "");

        ImageView btnBack = findViewById(R.id.btnBackChangePass);
        btnBack.setOnClickListener(v -> finish());

        dbHelper = new DatabaseHelper(this);
        etOldPass = findViewById(R.id.etOldPassword);
        etNewPass = findViewById(R.id.etNewPassword);
        etConfirmPass = findViewById(R.id.etConfirmPassword);
        Button btnChange = findViewById(R.id.btnConfirmChange);
        btnChange.setOnClickListener(v -> changePassword());
    }

    private void changePassword() {
        String oldPassword = etOldPass.getText().toString().trim();
        String newPassword = etNewPass.getText().toString().trim();
        String confirmPassword = etConfirmPass.getText().toString().trim();

        if (currentUsername.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy tài khoản quản lý.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Kiểm tra mật khẩu mới phải khác mật khẩu cũ
        if (newPassword.equals(oldPassword)) {
            Toast.makeText(this, "Mật khẩu mới không được giống mật khẩu cũ!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. Kiểm tra mật khẩu mới phải từ 6 ký tự trở lên (giống bên Đăng ký)
        if (newPassword.length() < 6) {
            Toast.makeText(this, "Mật khẩu mới phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu xác nhận không khớp.", Toast.LENGTH_SHORT).show();
            return;
        }

        try (Cursor cursor = dbHelper.getUserData(currentUsername)) {
            if (cursor == null || !cursor.moveToFirst()) {
                Toast.makeText(this, "Không tìm thấy tài khoản quản lý.", Toast.LENGTH_SHORT).show();
                return;
            }
            String dbPassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            if (!oldPassword.equals(dbPassword)) {
                Toast.makeText(this, "Mật khẩu cũ không chính xác.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (dbHelper.updateUserPassword(currentUsername, newPassword) > 0) {
            getSharedPreferences(STORE_PREFS, MODE_PRIVATE)
                    .edit()
                    .putString(KEY_MANAGER_PASSWORD, newPassword)
                    .apply();
            Toast.makeText(this, "Đổi mật khẩu thành công.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Không thể đổi mật khẩu.", Toast.LENGTH_SHORT).show();
        }
    }
}
