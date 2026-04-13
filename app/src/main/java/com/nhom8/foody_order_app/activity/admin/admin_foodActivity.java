package com.nhom8.foody_order_app.activity.ActivityImpl;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nhom8.foody_order_app.adapter.admin_foodAdapter;
import com.nhom8.foody_order_app.repository.food.admin_foodDao;

public class admin_foodActivity extends AppCompatActivity {

    ListView lvMonAn;
    EditText edtTimKiem;
    admin_foodDao dao;
    admin_foodAdapter adapter;
    ImageButton btnLocMien; // Khai báo nút lọc ở đây

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_listmonan);

        // 1. Ánh xạ các thành phần
        lvMonAn = findViewById(R.id.lvMonAn);
        edtTimKiem = findViewById(R.id.edtTimKiem);
        btnLocMien = findViewById(R.id.btnLocMien); // Phải ánh xạ trong onCreate
        dao = new admin_foodDao(this);

        loadData();

        // 2. Xử lý Tìm kiếm
        edtTimKiem.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tuKhoa = edtTimKiem.getText().toString().trim();
                hienThiKetQuaTimKiem(tuKhoa);
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        // 3. Xử lý nút Thêm
        Button btnThemMon = findViewById(R.id.btnThemMon);
        btnThemMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThiDialog(null);
            }
        });

        // 4. Xử lý nút Lọc Miền (Phải nằm TRONG onCreate)
        btnLocMien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.widget.PopupMenu popup = new android.widget.PopupMenu(admin_foodActivity.this, v);
                popup.getMenu().add(1, 1, 1, "1. Miền Bắc");
                popup.getMenu().add(1, 2, 2, "2. Miền Trung");
                popup.getMenu().add(1, 3, 3, "3. Miền Nam");
                popup.getMenu().add(1, 0, 4, "Tất cả");

                popup.setOnMenuItemClickListener(item -> {
                    int idMien = item.getItemId();
                    Cursor cursorMoi;
                    if (idMien == 0) {
                        cursorMoi = dao.layTatCa();
                    } else {
                        cursorMoi = dao.locTheoMien(idMien);
                    }
                    adapter.changeCursor(cursorMoi);
                    return true;
                });
                popup.show();
            }
        });

    } // Kết thúc hàm onCreate

    private void loadData() {
        Cursor cursor = dao.layTatCa();
        adapter = new admin_foodAdapter(this, cursor);
        lvMonAn.setAdapter(adapter);
    }

    public void hienThiDialog(final Integer idCanSua) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.admin_dialogmonan, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        final EditText edtTen = view.findViewById(R.id.edtTen);
        final EditText edtGia = view.findViewById(R.id.edtGia);
        final EditText edtMoTa = view.findViewById(R.id.edtMoTa);
        final EditText edtHinh = view.findViewById(R.id.edtHinh);
        final EditText edtLoai = view.findViewById(R.id.edtLoai);
        final android.widget.ImageView imgPreview = view.findViewById(R.id.imgPreview);
        Button btnLuu = view.findViewById(R.id.btnLuu);
        android.widget.TextView txtTieuDe = view.findViewById(R.id.txtTieuDeDialog);

        if (idCanSua != null) {
            txtTieuDe.setText("CHỈNH SỬA MÓN ĂN");
            Cursor c = dao.layTheoId(idCanSua);
            if (c != null && c.moveToFirst()) {
                edtTen.setText(c.getString(c.getColumnIndexOrThrow("name")));
                edtGia.setText(String.valueOf(c.getInt(c.getColumnIndexOrThrow("price"))));
                edtMoTa.setText(c.getString(c.getColumnIndexOrThrow("description")));
                edtHinh.setText(c.getString(c.getColumnIndexOrThrow("image")));
                edtLoai.setText(String.valueOf(c.getInt(c.getColumnIndexOrThrow("categoryId"))));

                String tenHinh = c.getString(c.getColumnIndexOrThrow("image"));
                int resID = getResources().getIdentifier(tenHinh, "drawable", getPackageName());
                if (resID != 0) imgPreview.setImageResource(resID);
            }
        } else {
            txtTieuDe.setText("THÊM MÓN ĂN MỚI");
        }

        btnLuu.setOnClickListener(v -> {
            String ten = edtTen.getText().toString().trim();
            String giaStr = edtGia.getText().toString().trim();
            String moTa = edtMoTa.getText().toString().trim();
            String hinh = edtHinh.getText().toString().trim();
            String loaiStr = edtLoai.getText().toString().trim();

            if (ten.isEmpty() || giaStr.isEmpty()) {
                Toast.makeText(this, "Nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int gia = Integer.parseInt(giaStr);
                int loai = Integer.parseInt(loaiStr);
                if (idCanSua == null) {
                    dao.them(ten, gia, moTa, hinh, loai);
                } else {
                    dao.sua(idCanSua, ten, gia, moTa, hinh, loai);
                }
                loadData();
                dialog.dismiss();
            } catch (Exception e) {
                Toast.makeText(this, "Lỗi dữ liệu số!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void hienThiKetQuaTimKiem(String ten) {
        Cursor cursorMoi = dao.timKiem(ten);
        if (adapter != null) adapter.changeCursor(cursorMoi);
    }

    public void xemChiTiet(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.admin_dialogmonan, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        EditText edtTen = view.findViewById(R.id.edtTen);
        EditText edtGia = view.findViewById(R.id.edtGia);
        EditText edtMoTa = view.findViewById(R.id.edtMoTa);
        EditText edtHinh = view.findViewById(R.id.edtHinh);
        EditText edtLoai = view.findViewById(R.id.edtLoai);
        android.widget.ImageView imgPreview = view.findViewById(R.id.imgPreview);
        Button btnLuu = view.findViewById(R.id.btnLuu);
        android.widget.TextView txtTieuDe = view.findViewById(R.id.txtTieuDeDialog);

        Cursor c = dao.layTheoId(id);
        if (c != null && c.moveToFirst()) {
            txtTieuDe.setText("CHI TIẾT MÓN ĂN");
            edtTen.setText(c.getString(c.getColumnIndexOrThrow("name")));
            edtGia.setText(String.valueOf(c.getInt(c.getColumnIndexOrThrow("price"))));
            edtMoTa.setText(c.getString(c.getColumnIndexOrThrow("description")));
            edtHinh.setText(c.getString(c.getColumnIndexOrThrow("image")));
            edtLoai.setText(String.valueOf(c.getInt(c.getColumnIndexOrThrow("categoryId"))));

            int resID = getResources().getIdentifier(c.getString(c.getColumnIndexOrThrow("image")), "drawable", getPackageName());
            if (resID != 0) imgPreview.setImageResource(resID);
        }

        edtTen.setEnabled(false);
        edtGia.setEnabled(false);
        edtMoTa.setEnabled(false);
        edtHinh.setEnabled(false);
        edtLoai.setEnabled(false);
        btnLuu.setText("ĐÓNG");
        btnLuu.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public void xoaMonAn(final int id) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Xóa món này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    if (dao.xoa(id) > 0) {
                        Toast.makeText(this, "Đã xóa!", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}