package com.nhom8.foody_order_app.activity.ActivityImpl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.activity.HomeActivityImpl;
import com.nhom8.foody_order_app.fragments.ChatFragment;
import com.nhom8.foody_order_app.repository.DAO;
import com.nhom8.foody_order_app.fragments.HomeFragment;
import com.nhom8.foody_order_app.fragments.ProfileFragment;
import com.nhom8.foody_order_app.model.User;

public class HomeActivity extends AppCompatActivity implements HomeActivityImpl {
    public static DAO dao;
    public static User user;
    private Fragment homeFragment, chatFragment, profileFragment;
    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;
    private static int clickToLogout;
    private static int stackLayout = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Integer userID = user.getId(); // dá»¯ liá»‡u user Ä‘Ã£ Ä‘Æ°á»£c truyá»n vÃ o á»Ÿ pháº§n Ä‘Äƒng nháº­p tÃ i khoáº£n rá»“i
        FoodDetailsActivity.userID = userID; // truyá»n userID cá»§a ngÆ°á»i dÃ¹ng qua FoodDetailsActivity
        ViewOrderActivity.userID = userID;   // truyá»n userID cá»§a ngÆ°á»i dÃ¹ng qua ViewOrderActivity

        dao = new DAO(this);
        initializeUI();

        stackLayout++;
        clickToLogout = 0;
        clickButtonNavigation();

        // tá»« Fragment trá»Ÿ Ä‘áº¿n Activity Ä‘á»ƒ láº¥y Ä‘á»‘i tÆ°á»£ng User (XÃ¡c thá»±c)
        Intent intent = getIntent();
        String request = intent.getStringExtra("request");
        if(request != null) {
            switch (request) {
                case "history": case "check": case "payment": case "cart":
                    loadFragment_replace(chatFragment, 2);
                    break;
                default:
                    loadFragment_replace(homeFragment, 0);
                    break;
            }
        } else {
            loadFragment_replace(homeFragment, 0);
        }
    }

    @Override
    public void initializeUI() {
        homeFragment = new HomeFragment();
        chatFragment = new ChatFragment();
        profileFragment = new ProfileFragment();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void clickButtonNavigation() {
        loadFragment(homeFragment); // setting: load máº·c Ä‘á»‹nh - HomeFragment lÃªn trÃªn HomeActivity
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_home) {
                    loadFragment(homeFragment);
                } else if (item.getItemId() == R.id.action_category) {
                    loadFragment(chatFragment);
                } else if (item.getItemId() == R.id.action_profile) {
                    loadFragment(profileFragment);
                } else {
                    return true;
                }
                return true;
            }
        });
    }

    // load Fragment
    private void loadFragment(Fragment fragmentReplace) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, fragmentReplace)
                .commit();
    }

    private void loadFragment_replace(Fragment fragment, int indexItem) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);    // transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        System.out.println(stackLayout);
        if (stackLayout < 2) {
            clickToLogout++;

            if (clickToLogout > 1) {
                finish();
                stackLayout--;
            } else {
                Toast.makeText(this, "Click thÃªm láº§n ná»¯a Ä‘á»ƒ Ä‘Äƒng xuáº¥t!", Toast.LENGTH_SHORT).show();
            }

            new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    clickToLogout = 0;
                }
            }.start();
        } else {
            stackLayout--;
            super.onBackPressed();
        }
    }

}

