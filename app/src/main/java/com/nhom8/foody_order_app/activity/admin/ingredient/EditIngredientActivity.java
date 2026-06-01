package com.nhom8.foody_order_app.activity.admin.ingredient;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHelper;

public class EditIngredientActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtQuantity;
    private EditText edtUnit;
    private DatabaseHelper db;
    private int ingredientId;
    private String currentImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredient);

        db = new DatabaseHelper(this);
        edtName = findViewById(R.id.edtEditName);
        edtQuantity = findViewById(R.id.edtEditQuantity);
        edtUnit = findViewById(R.id.edtEditUnit);
        Button btnUpdate = findViewById(R.id.btnConfirmUpdate);
        Button btnCancel = findViewById(R.id.btnCancelUpdate);

        ingredientId = getIntent().getIntExtra("id", -1);
        edtName.setText(getIntent().getStringExtra("name"));
        edtQuantity.setText(String.valueOf(getIntent().getDoubleExtra("quantity", 0)));
        edtUnit.setText(getIntent().getStringExtra("unit"));
        currentImage = getIntent().getStringExtra("image");

        btnCancel.setOnClickListener(v -> finish());
        btnUpdate.setOnClickListener(v -> updateIngredient());
    }

    private void updateIngredient() {
        String newName = edtName.getText().toString().trim();
        String newQuantityStr = edtQuantity.getText().toString().trim();
        String newUnit = edtUnit.getText().toString().trim();

        if (newName.isEmpty() || newQuantityStr.isEmpty() || newUnit.isEmpty()) {
            Toast.makeText(this, "Vui long nhap day du thong tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double newQuantity = Double.parseDouble(newQuantityStr);
            int result = db.updateIngredient(ingredientId, newName, newQuantity, newUnit, currentImage);
            if (result > 0) {
                Toast.makeText(this, "Cap nhat thanh cong.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Cap nhat that bai.", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException exception) {
            Toast.makeText(this, "So luong khong hop le.", Toast.LENGTH_SHORT).show();
        }
    }
}
