package com.nhom8.foody_order_app.activity.ActivityImpl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.model.User;
import com.nhom8.foody_order_app.repository.DAO;

public class SignInActivity extends AppCompatActivity {
    private static final String MANAGER_USERNAME = "manager_nhom8";
    private static final String MANAGER_PASSWORD = "nhom8@123";
    private static final String CUSTOMER_USERNAME_KEY = "customer_username";
    private static final String CUSTOMER_PASSWORD_KEY = "customer_password";
    private static final String MANAGER_USERNAME_KEY = "manager_username";
    private static final String MANAGER_PASSWORD_KEY = "manager_password";

    public static final String PREFERENCES = "store_info";
    public static DAO dao;

    private Button btnLogin;
    private EditText txtUsername;
    private EditText txtPassword;
    private TextView validateUsername;
    private TextView validatePassword;
    private TextView textSignUpApp;
    private TextView textRoleSubtitle;
    private TextView textChangeRole;
    private TextView textLoginTitle;
    private SharedPreferences sharedPreferences;
    private boolean isPasswordVisible = false;
    private String selectedRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initializeUI();
        dao = new DAO(this);
        sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        selectedRole = resolveRole();
        setupRoleUi();
        populateRememberedCredentials();
        setupLogin();
        setNextActivityListener();
    }

    private void initializeUI() {
        btnLogin = findViewById(R.id.btnLogin);
        txtUsername = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        validateUsername = findViewById(R.id.validateUsername);
        validatePassword = findViewById(R.id.validatePassword);
        textSignUpApp = findViewById(R.id.textSignUpApp);
        textRoleSubtitle = findViewById(R.id.textRoleSubtitle);
        textChangeRole = findViewById(R.id.textChangeRole);
        textLoginTitle = findViewById(R.id.textView20);
    }

    private void setupLogin() {
        btnLogin.setOnClickListener(v -> {
            validateUsername.setText("");
            validatePassword.setText("");

            String username = txtUsername.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, getResources().getString(R.string.input_full_username_and_password), Toast.LENGTH_SHORT).show();
                return;
            }

            if (RoleSelectionActivity.ROLE_MANAGER.equals(selectedRole)) {
                handleManagerLogin(username, password);
                return;
            }

            User userExist = dao.getUserByUsernameAndPassword(username, password);
            boolean isRightAuthentication = false;
            if (userExist != null) {
                isRightAuthentication = dao.signIn(userExist);
            }

            if (isRightAuthentication) {
                saveCredentials(CUSTOMER_USERNAME_KEY, CUSTOMER_PASSWORD_KEY, userExist.getUsername(), userExist.getPassword());
                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                HomeActivity.user = userExist;
                startActivity(intent);
                finish();
                return;
            }

            if (!dao.checkUsername(username)) {
                validateUsername.setText(getResources().getString(R.string.error_username_login));
            } else if (!dao.checkPasswordToCurrentUsername(username, password)) {
                validatePassword.setText(getResources().getString(R.string.error_password_login));
            }
            Toast.makeText(this, getResources().getString(R.string.error_information_login), Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.checkPassword).setOnClickListener(v -> togglePasswordVisibility());

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isPasswordVisible) {
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    txtPassword.setSelection(txtPassword.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void handleManagerLogin(String username, String password) {
        if (!MANAGER_USERNAME.equals(username)) {
            validateUsername.setText("Sai tài khoản quản lý.");
            Toast.makeText(this, "Tài khoản này không thuộc quản lý.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!MANAGER_PASSWORD.equals(password)) {
            validatePassword.setText("Sai mật khẩu quản lý.");
            Toast.makeText(this, "Mật khẩu quản lý không đúng.", Toast.LENGTH_SHORT).show();
            return;
        }

        saveCredentials(MANAGER_USERNAME_KEY, MANAGER_PASSWORD_KEY, username, password);
        launchManagerHome();
    }

    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;
        int inputType = isPasswordVisible
                ? (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                : (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        txtPassword.setInputType(inputType);
        txtPassword.setSelection(txtPassword.getText().length());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            txtUsername.setText(data.getStringExtra("username"));
            txtPassword.setText(data.getStringExtra("password"));
        }
    }

    private void setNextActivityListener() {
        textSignUpApp.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivityForResult(intent, 0);
        });

        textChangeRole.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this, RoleSelectionActivity.class));
            finish();
        });
    }

    private String resolveRole() {
        String roleFromIntent = getIntent().getStringExtra(RoleSelectionActivity.KEY_USER_ROLE);
        if (roleFromIntent != null) {
            return roleFromIntent;
        }

        SharedPreferences rolePreferences = getSharedPreferences(RoleSelectionActivity.ROLE_PREFS, MODE_PRIVATE);
        return rolePreferences.getString(RoleSelectionActivity.KEY_USER_ROLE, RoleSelectionActivity.ROLE_CUSTOMER);
    }

    private void setupRoleUi() {
        if (RoleSelectionActivity.ROLE_MANAGER.equals(selectedRole)) {
            textLoginTitle.setText("Đăng nhập quản lý");
            textRoleSubtitle.setText("Dùng tài khoản quản lý riêng để vào khu vực quản lý.");
            textSignUpApp.setVisibility(View.GONE);
        } else {
            textLoginTitle.setText("Đăng nhập khách hàng");
            textRoleSubtitle.setText("Dùng tài khoản khách hàng để vào ứng dụng đặt đồ ăn.");
            textSignUpApp.setVisibility(View.VISIBLE);
        }
    }

    private void populateRememberedCredentials() {
        if (RoleSelectionActivity.ROLE_MANAGER.equals(selectedRole)) {
            txtUsername.setText(sharedPreferences.getString(MANAGER_USERNAME_KEY, ""));
            txtPassword.setText(sharedPreferences.getString(MANAGER_PASSWORD_KEY, ""));
            return;
        }

        txtUsername.setText(sharedPreferences.getString(CUSTOMER_USERNAME_KEY, ""));
        txtPassword.setText(sharedPreferences.getString(CUSTOMER_PASSWORD_KEY, ""));
    }

    private void saveCredentials(String usernameKey, String passwordKey, String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(usernameKey, username);
        editor.putString(passwordKey, password);
        editor.apply();
    }

    private void launchManagerHome() {
        Intent intent = new Intent(this, ManagerHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
