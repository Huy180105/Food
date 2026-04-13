package com.nhom8.foody_order_app.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.activity.ActivityImpl.HomeActivity;
import com.nhom8.foody_order_app.model.Notify;
import com.nhom8.foody_order_app.model.NotifyToUser;
import com.nhom8.foody_order_app.model.Order;

public class OrderCard extends LinearLayout implements BaseComponent{
    private Order order;
    private TextView tvDate, tvPrice, tvAddress, tvStatus;
    private Button btnConfirm;

    public OrderCard(Context context){
        super(context);
    }

    public OrderCard(Context context, Order order) {
        super(context);
        this.order = order;
        initControl(context);
    }

    @Override
    public void initUI() {
        tvDate = findViewById(R.id.tvDateMakeOrder);
        tvPrice = findViewById(R.id.tvOrderPrice);
        tvAddress = findViewById(R.id.tvOrderAddress);
        tvStatus = findViewById(R.id.tvOrderStatus);
        btnConfirm = findViewById(R.id.btnConfirmOrder);
    }

    @Override
    public void initControl(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_order_card, this);

        initUI();

        btnConfirm.setOnClickListener(view -> {
            order.setStatus("Delivered");
            HomeActivity.dao.updateOrder(order);
            Toast.makeText(context, "ÄÃ£ cáº­p nháº­t láº¡i tráº¡ng thÃ¡i!", Toast.LENGTH_SHORT).show();

            // System Notify
            int quantityOrder = HomeActivity.dao.quantityOfOrder();
            HomeActivity.dao.addNotify(new Notify(1, "Cáº­p nháº­t thÃ´ng tin á»©ng dá»¥ng!",
                    "á»¨ng dá»¥ng Ä‘Ã£ cÃ³ " + quantityOrder + " Ä‘Æ¡n Ä‘áº·t hÃ ng!", HomeActivity.dao.getDate()));

            // User Notify
            String content = "ÄÆ¡n hÃ ng ngÃ y " + order.getDateOfOrder() + " Ä‘Ã£ Ä‘Æ°á»£c nháº­n!\nCáº£m Æ¡n báº¡n Ä‘Ã£ tin tÆ°á»Ÿng á»©ng dá»¥ng!";
            HomeActivity.dao.addNotify(new Notify(1, "ThÃ´ng bÃ¡o vá» Ä‘Æ¡n hÃ ng!",
                    content, HomeActivity.dao.getDate()));
            HomeActivity.dao.addNotifyToUser(new NotifyToUser(
                    HomeActivity.dao.getNewestNotifyId(), HomeActivity.user.getId()));
        });

        if(order.getStatus().equals("Delivered")){
            btnConfirm.setEnabled(false);
        }

        if(order.getStatus().equals("Canceled")){
            btnConfirm.setText("ÄÃƒ Há»¦Y ÄÆ N");
            btnConfirm.setEnabled(false);
        }

        tvDate.setText(order.getDateOfOrder());
        tvAddress.setText(order.getAddress());
        tvPrice.setText(getRoundPrice(order.getTotalValue()));
        tvStatus.setText(order.getStatus());
    }

    private String getRoundPrice(Double price){
        return Math.round(price) + " VNÄ";
    }
}

