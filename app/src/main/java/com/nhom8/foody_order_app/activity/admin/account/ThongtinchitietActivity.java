package com.nhom8.foody_order_app.activity.admin.account;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHelper;

import java.util.Calendar;

public class ThongtinchitietActivity extends AppCompatActivity {
    private static final String STORE_PREFS = "store_info";
    private static final String KEY_MANAGER_USERNAME = "manager_username";

    private EditText etUser;
    private EditText etName;
    private EditText etBirth;
    private EditText etPhone;
    private EditText etAddress;
    private RadioButton rbMale, rbFemale;
    private DatabaseHelper dbHelper;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_chi_tiet);

        SharedPreferences pref = getSharedPreferences(STORE_PREFS, MODE_PRIVATE);
        currentUsername = pref.getString(KEY_MANAGER_USERNAME, "");
        dbHelper = new DatabaseHelper(this);

        ImageView btnBack = findViewById(R.id.btnBackThongtin);
        btnBack.setOnClickListener(v -> finish());

        etUser = findViewById(R.id.etlUser);
        etName = findViewById(R.id.etFullName);
        etBirth = findViewById(R.id.etBirth);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        Button btnUpdate = findViewById(R.id.btnUpdateProfile);

        etUser.setEnabled(false);
        etBirth.setFocusable(false);
        etBirth.setClickable(true);
        etBirth.setOnClickListener(v -> showDatePicker());

        loadUserData();
        btnUpdate.setOnClickListener(v -> updateData());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) ->
                etBirth.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void loadUserData() {
        if (currentUsername.isEmpty()) {
            return;
        }

        try (Cursor cursor = dbHelper.getUserData(currentUsername)) {
            if (cursor == null || !cursor.moveToFirst()) {
                return;
            }
            etUser.setText(cursor.getString(cursor.getColumnIndexOrThrow("username")));
            etName.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            etBirth.setText(cursor.getString(cursor.getColumnIndexOrThrow("date_of_birth")));
            etPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
            int addressIndex = cursor.getColumnIndex("address");
            if (addressIndex >= 0) {
                etAddress.setText(cursor.getString(addressIndex));
            }
            String gender = cursor.getString(cursor.getColumnIndexOrThrow("gender"));

            if ("Nam".equalsIgnoreCase(gender) || "Male".equalsIgnoreCase(gender)) {
                rbMale.setChecked(true);
            } else {
                rbFemale.setChecked(true);
            }
        }
    }

    private void updateData() {
        String name = etName.getText().toString().trim();
        String birth = etBirth.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String gender = rbMale.isChecked() ? "Nam" : "Nu";

        // 1. Kiểm tra không được để trống bất kỳ ô nào (giống đăng ký)
        if (name.isEmpty() || birth.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin, không được để trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. Kiểm tra số điện thoại phải có đúng 10 chữ số
        if (phone.length() != 10) {
            Toast.makeText(this, "Số điện thoại phải có đúng 10 số!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Nếu các điều kiện trên đều thỏa mãn thì mới gọi Database để update
        if (dbHelper.updateUserProfile(currentUsername, name, birth, phone, address, gender) > 0) {
            Toast.makeText(this, "Cập nhật thành công.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Không thể cập nhật thông tin.", Toast.LENGTH_SHORT).show();
        }
    }
}
