package com.nhom8.foody_order_app.activity.admin.ingredient;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.adapter.IngredientAdapter;
import com.nhom8.foody_order_app.model.ManagerIngredient;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ManageIngredientsActivity extends AppCompatActivity {
    private RecyclerView rvIngredients;
    private IngredientAdapter adapter;
    private DatabaseHelper databaseHelper;
    private final List<ManagerIngredient> ingredientList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_ingredients);

        ImageView imgBackIngredient = findViewById(R.id.imgBackIngredient);
        rvIngredients = findViewById(R.id.rvIngredients);
        EditText edtSearchIngredient = findViewById(R.id.edtSearchIngredient);
        Button btnAddNewIngredient = findViewById(R.id.btnAddNewIngredient);

        databaseHelper = new DatabaseHelper(this);
        adapter = new IngredientAdapter(this, ingredientList);
        rvIngredients.setLayoutManager(new LinearLayoutManager(this));
        rvIngredients.setAdapter(adapter);

        loadIngredients();

        imgBackIngredient.setOnClickListener(v -> finish());
        edtSearchIngredient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().trim();
                if (keyword.isEmpty()) {
                    loadIngredients();
                } else {
                    searchIngredients(keyword);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btnAddNewIngredient.setOnClickListener(v -> startActivity(new Intent(this, AddIngredientActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadIngredients();
    }

    private void loadIngredients() {
        bindCursor(databaseHelper.getAllIngredients());
    }

    private void searchIngredients(String query) {
        bindCursor(databaseHelper.searchIngredient(query));
    }

    private void bindCursor(Cursor cursor) {
        ingredientList.clear();
        if (cursor != null) {
            try {
                int idIndex = cursor.getColumnIndexOrThrow("id");
                int nameIndex = cursor.getColumnIndexOrThrow("name");
                int quantityIndex = cursor.getColumnIndexOrThrow("quantity");
                int unitIndex = cursor.getColumnIndexOrThrow("unit");
                int imageIndex = cursor.getColumnIndexOrThrow("image");
                while (cursor.moveToNext()) {
                    ingredientList.add(new ManagerIngredient(
                            cursor.getInt(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getDouble(quantityIndex),
                            cursor.getString(unitIndex),
                            cursor.getString(imageIndex)
                    ));
                }
            } finally {
                cursor.close();
            }
        }
        adapter.notifyDataSetChanged();
    }
}
