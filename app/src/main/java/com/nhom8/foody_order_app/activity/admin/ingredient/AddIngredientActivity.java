package com.nhom8.foody_order_app.activity.admin.ingredient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHelper;

public class AddIngredientActivity extends AppCompatActivity {
    private ImageView imgIngredient;
    private EditText edtName;
    private EditText edtQuantity;
    private EditText edtUnit;
    private EditText edtImageName;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        databaseHelper = new DatabaseHelper(this);
        imgIngredient = findViewById(R.id.imgIngredient);
        edtName = findViewById(R.id.edtName);
        edtQuantity = findViewById(R.id.edtQuantity);
        edtUnit = findViewById(R.id.edtUnit);
        edtImageName = findViewById(R.id.edtImageName);
        Button btnPickImage = findViewById(R.id.btnPickImage);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnCancel = findViewById(R.id.btnCancel);

        edtImageName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String fileName = s.toString().trim();
                if (fileName.isEmpty()) {
                    imgIngredient.setImageResource(android.R.drawable.ic_menu_camera);
                    return;
                }
                int resId = getResources().getIdentifier(fileName, "drawable", getPackageName());
                imgIngredient.setImageResource(resId != 0 ? resId : android.R.drawable.ic_menu_camera);
            }
        });

        btnCancel.setOnClickListener(v -> finish());
        btnSave.setOnClickListener(v -> saveIngredient());
    }

    private void saveIngredient() {
        String name = edtName.getText().toString().trim();
        String qtyStr = edtQuantity.getText().toString().trim();
        String unit = edtUnit.getText().toString().trim();
        String imageName = edtImageName.getText().toString().trim();

        if (name.isEmpty() || qtyStr.isEmpty() || unit.isEmpty() || imageName.isEmpty()) {
            Toast.makeText(this, "Vui long nhap day du thong tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        if (resId == 0) {
            Toast.makeText(this, "Ten file anh khong ton tai trong drawable.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double quantity = Double.parseDouble(qtyStr);
            long result = databaseHelper.addIngredient(name, quantity, unit, imageName);
            if (result != -1) {
                Toast.makeText(this, "Them nguyen lieu thanh cong.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Khong the them nguyen lieu.", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException exception) {
            Toast.makeText(this, "So luong khong hop le.", Toast.LENGTH_SHORT).show();
        }
    }
}
