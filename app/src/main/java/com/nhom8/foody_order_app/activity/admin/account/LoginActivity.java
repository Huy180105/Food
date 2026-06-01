package com.nhom8.foody_order_app.activity.admin.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.activity.ActivityImpl.ManagerHomeActivity;
import com.nhom8.foody_order_app.activity.ActivityImpl.RoleSelectionActivity;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    private static final String STORE_PREFS = "store_info";
    private static final String KEY_MANAGER_USERNAME = "manager_username";
    private static final String KEY_MANAGER_PASSWORD = "manager_password";

    private EditText etUsername;
    private EditText etPassword;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        dbHelper = new DatabaseHelper(this);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvRegister = findViewById(R.id.tvRegister);

        SharedPreferences prefs = getSharedPreferences(STORE_PREFS, MODE_PRIVATE);
        etUsername.setText(prefs.getString(KEY_MANAGER_USERNAME, ""));
        etPassword.setText(prefs.getString(KEY_MANAGER_PASSWORD, ""));

        btnLogin.setOnClickListener(v -> handleLogin());
        tvRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void handleLogin() {
        String user = etUsername.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Vui long nhap day du thong tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!dbHelper.checkLogin(user, pass, "admin")) {
            Toast.makeText(this, "Sai tai khoan hoac mat khau admin.", Toast.LENGTH_SHORT).show();
            return;
        }

        getSharedPreferences(STORE_PREFS, MODE_PRIVATE)
                .edit()
                .putString(KEY_MANAGER_USERNAME, user)
                .putString(KEY_MANAGER_PASSWORD, pass)
                .apply();

        getSharedPreferences("UserPrefs", MODE_PRIVATE)
                .edit()
                .putString("current_user", user)
                .apply();

        getSharedPreferences(RoleSelectionActivity.ROLE_PREFS, MODE_PRIVATE)
                .edit()
                .putString(RoleSelectionActivity.KEY_USER_ROLE, RoleSelectionActivity.ROLE_MANAGER)
                .apply();

        Toast.makeText(this, "Dang nhap thanh cong.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ManagerHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
