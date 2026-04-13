package com.nhom8.foody_order_app.activity.ActivityImpl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom8.foody_order_app.R;

public class RoleSelectionActivity extends AppCompatActivity {
    public static final String ROLE_PREFS = "role_preferences";
    public static final String KEY_USER_ROLE = "user_role";
    public static final String ROLE_CUSTOMER = "customer";
    public static final String ROLE_MANAGER = "manager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        Button customerButton = findViewById(R.id.btnCustomer);
        Button managerButton = findViewById(R.id.btnManager);

        customerButton.setOnClickListener(view -> openSignIn(ROLE_CUSTOMER));
        managerButton.setOnClickListener(view -> openSignIn(ROLE_MANAGER));
    }

    private void openSignIn(String role) {
        SharedPreferences preferences = getSharedPreferences(ROLE_PREFS, MODE_PRIVATE);
        preferences.edit().putString(KEY_USER_ROLE, role).apply();

        Intent intent = new Intent(this, SignInActivity.class);
        intent.putExtra(KEY_USER_ROLE, role);
        startActivity(intent);
    }
}

