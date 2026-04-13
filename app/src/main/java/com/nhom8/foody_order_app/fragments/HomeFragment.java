package com.nhom8.foody_order_app.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.activity.ActivityImpl.HomeActivity;
import com.nhom8.foody_order_app.activity.ActivityImpl.SignInActivity;
import com.nhom8.foody_order_app.adapter.FoodAdapter;
import com.nhom8.foody_order_app.imageBanner.Photo;
import com.nhom8.foody_order_app.imageBanner.PhotoAdapter;
import com.nhom8.foody_order_app.model.Food;
import com.nhom8.foody_order_app.repositoryInit.DataInitFragmentHome;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Intent intent;
    private RecyclerView recyclerView;
    private EditText searchBar;
    private ViewPager viewPager;
    private List<Photo> listPhoto;
    private Timer timer;

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView_Restaurant);
        searchBar = rootView.findViewById(R.id.search_bar);

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        loadFoodList("");
        setupSearchBar();

        viewPager = rootView.findViewById(R.id.viewpager);
        CircleIndicator circleIndicator = rootView.findViewById(R.id.circle_indicator);

        listPhoto = DataInitFragmentHome.listPhoto;
        PhotoAdapter photoAdapter = new PhotoAdapter(requireContext(), listPhoto);
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSildeImage();

        rootView.findViewById(R.id.imageCart).setOnClickListener(v -> {
            intent = new Intent(getActivity(), HomeActivity.class);
            intent.putExtra("request", "cart");
            startActivity(intent);
        });

        rootView.findViewById(R.id.imageNotify).setOnClickListener(v -> {
            intent = new Intent(getActivity(), HomeActivity.class);
            intent.putExtra("request", "bill");
            startActivity(intent);
        });

        rootView.findViewById(R.id.imageLogout).setOnClickListener(view -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setMessage("Bạn có muốn đăng xuất tài khoản?");
            dialog.setPositiveButton("Có", (dialogInterface, i) -> {
                Toast.makeText(this.getActivity(), "Đã đăng xuất khỏi hệ thống!", Toast.LENGTH_SHORT).show();
                requireActivity().finish();
                startActivity(new Intent(getActivity(), SignInActivity.class));
            });
            dialog.setNegativeButton("Không", (dialogInterface, i) -> {
            });
            dialog.show();
        });

        return rootView;
    }

    private void setupSearchBar() {
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            loadFoodList(searchBar.getText().toString().trim());
            return false;
        });
    }

    private void loadFoodList(String keyword) {
        ArrayList<Food> foodList = HomeActivity.dao.getFoodByKeyWord(keyword == null ? "" : keyword, null);
        recyclerView.setAdapter(new FoodAdapter(foodList));
    }

    private void autoSildeImage() {
        if (listPhoto == null || listPhoto.isEmpty() || viewPager == null) {
            return;
        }

        if (timer == null) {
            timer = new Timer();
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(() -> {
                    int currentItem = viewPager.getCurrentItem();
                    int totalItem = listPhoto.size() - 1;
                    if (currentItem < totalItem) {
                        viewPager.setCurrentItem(currentItem + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                });
            }
        }, 500, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
