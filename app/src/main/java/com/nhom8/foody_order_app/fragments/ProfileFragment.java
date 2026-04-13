package com.nhom8.foody_order_app.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.activity.ActivityImpl.HomeActivity;
import com.nhom8.foody_order_app.activity.ActivityImpl.RoleSelectionActivity;
import com.nhom8.foody_order_app.activity.ActivityImpl.UserInformationActivity;

public class ProfileFragment extends Fragment {
    private View mainView;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_profile, container, false);
        referenceComponent();
        return mainView;
    }

    private void referenceComponent() {
        LinearLayout userInformation = mainView.findViewById(R.id.account_btn_layout_user_information);
        userInformation.setOnClickListener(view ->
                startActivity(new Intent(getActivity(), UserInformationActivity.class)));

        LinearLayout check = mainView.findViewById(R.id.account_btn_layout_check);
        check.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            intent.putExtra("request", "check");
            startActivity(intent);
        });

        LinearLayout logout = mainView.findViewById(R.id.account_btn_layout_logout);
        logout.setOnClickListener(view -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setMessage("Bạn có muốn đăng xuất tài khoản?");
            dialog.setPositiveButton("Có", (dialogInterface, i) -> {
                Toast.makeText(getActivity(), "Đã đăng xuất khỏi hệ thống!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), RoleSelectionActivity.class);
                startActivity(intent);
                requireActivity().finish();
            });
            dialog.setNegativeButton("Không", null);
            dialog.show();
        });

        TextView txtUserName = mainView.findViewById(R.id.account_user_name);
        txtUserName.setText(HomeActivity.user.getName());
    }
}
