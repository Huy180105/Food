package com.nhom8.foody_order_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.activity.ActivityImpl.HomeActivity;
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
        LinearLayout userInformation = mainView.findViewById(R.id.layout_user_information);
        userInformation.setOnClickListener(view ->
                startActivity(new Intent(getActivity(), UserInformationActivity.class)));

        LinearLayout check = mainView.findViewById(R.id.account_btn_layout_check);
        check.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            intent.putExtra("request", "check");
            startActivity(intent);
        });

        TextView txtUserName = mainView.findViewById(R.id.account_user_name);
        txtUserName.setText(HomeActivity.user.getName());
    }
}

