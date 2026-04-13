package com.nhom8.foody_order_app.repositoryInit;

import com.nhom8.foody_order_app.R;
import com.nhom8.foody_order_app.imageBanner.Photo;

import java.util.ArrayList;
import java.util.List;

public class DataInitFragmentHome {
    public static final List<Photo> listPhoto = new ArrayList<>();

    static {
        listPhoto.add(new Photo(R.drawable.img_mien_bac));
        listPhoto.add(new Photo(R.drawable.mb_pho_bo));
        listPhoto.add(new Photo(R.drawable.img_mien_trung));
        listPhoto.add(new Photo(R.drawable.mt_bun_bo));
        listPhoto.add(new Photo(R.drawable.img_mien_nam));
        listPhoto.add(new Photo(R.drawable.mn_com_tam));
    }
}
