package com.nhom8.foody_order_app.activity.admin.account;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHelper;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);

        dbHelper = new DatabaseHelper(this);

        EditText etUser = findViewById(R.id.etRegUser);
        EditText etPass = findViewById(R.id.etRegPass);
        EditText etName = findViewById(R.id.etRegFullName);
        EditText etBirth = findViewById(R.id.etRegBirthDate);
        EditText etPhone = findViewById(R.id.etRegPhone);
        EditText etAddr = findViewById(R.id.etRegAddress);
        RadioButton rbMale = findViewById(R.id.rbMale);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView tvBack = findViewById(R.id.tvBackToLogin);

        etBirth.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, day) ->
                    etBirth.setText(day + "/" + (month + 1) + "/" + year),
                    c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });

        btnRegister.setOnClickListener(v -> {
            String user = etUser.getText().toString().trim();
            String pass = etPass.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String birth = etBirth.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String addr = etAddr.getText().toString().trim();
            String gender = rbMale.isChecked() ? "Nam" : "Nu";


            if (user.isEmpty() || pass.isEmpty() || name.isEmpty() ||
                    birth.isEmpty() || phone.isEmpty() || addr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin vào các ô!", Toast.LENGTH_SHORT).show();
                return;
            }


            if (pass.length() < 6) {
                Toast.makeText(this, "Mật khẩu phải từ 6 ký tự trở lên!", Toast.LENGTH_SHORT).show();
                return;
            }


            if (phone.length() != 10) {
                Toast.makeText(this, "Số điện thoại phải có đúng 10 số!", Toast.LENGTH_SHORT).show();
                return;
            }


            long check = dbHelper.addUser(user, pass, name, birth, phone, addr, gender, "admin");
            if (check != -1) {
                Toast.makeText(this, "Đăng ký thành công.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Tài khoản đã tồn tại.", Toast.LENGTH_SHORT).show();
            }
        });

        tvBack.setOnClickListener(v -> finish());
    }
}
