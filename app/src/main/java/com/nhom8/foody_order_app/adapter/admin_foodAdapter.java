package com.nhom8.foody_order_app.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter; // Hãy chắc chắn có dòng này
import android.widget.ImageView;
import android.widget.TextView;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.activity.admin.admin_foodActivity;


public class admin_foodAdapter extends CursorAdapter {


    public admin_foodAdapter(Context context, Cursor c) {

        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//Tạo ra một View từ file XML admin_dongmonan.xml để hiển thị 1 dòng dữ liệu
        return LayoutInflater.from(context).inflate(R.layout.admin_dongmonan, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Lấy ID món ăn tại dòng này
        final int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

  // lấy dữ liệu lên theo dòng
        //-ánh xạ
        TextView txtTen = view.findViewById(R.id.txtTenItem);
        TextView txtGia = view.findViewById(R.id.txtGiaItem);
        ImageView img = view.findViewById(R.id.imgItem);
        // Lấy dữ liệu từ cs
        String ten = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        int gia = cursor.getInt(cursor.getColumnIndexOrThrow("price"));
        String hinh = cursor.getString(cursor.getColumnIndexOrThrow("image"));

        txtTen.setText(ten);
        txtGia.setText(String.format("%,d VNĐ", gia));

        // Sự kiện click vào cả dòng để xem chi tiết
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof admin_foodActivity) {
                    ((admin_foodActivity) context).xemChiTiet(id);
                }
            }
        });

        // xử lý ảnh
        int resID = context.getResources().getIdentifier(hinh, "drawable", context.getPackageName());
        if (resID != 0) {
            img.setImageResource(resID);
        } else {
            img.setImageResource(R.mipmap.ic_launcher);
        }
// nút xem chi tiết
        //-lấy nút xem của dòng hiện tại
        android.widget.ImageButton btnXem = view.findViewById(R.id.btnXem);
         // khi người dùng Bấm nút ở item → gọi Activity mở chi tiết của item đó
        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kiểm tra xem context có phải là Activity
                if (context instanceof admin_foodActivity) {
                    // Gọi hàm xemChiTiet bên Activity
                    ((admin_foodActivity) context).xemChiTiet(id);
                }
            }
        });

//Nút sửa
        // 1. Ánh xạ nút Sửa
        android.widget.ImageButton btnSua = view.findViewById(R.id.btnSua);
       //khai báo id ở nut xem rôi nên thôi ko cần nữa
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof admin_foodActivity) {
                    // Gọi hàm hienThiDialog và truyền id vào
                    ((admin_foodActivity) context).hienThiDialog(id);
                }
            }
        });

        // 2. Ánh xạ nút Xóa (Đảm bảo ID trong XML là btnXoa)
        android.widget.ImageButton btnXoa = view.findViewById(R.id.btnXoa);
        // 3. Bắt sự kiện click
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof admin_foodActivity) {
                    // Gọi hàm xoaMonAn
                    ((admin_foodActivity) context).xoaMonAn(id);
                }
            }
        });
    }
}