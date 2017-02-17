package com.example.drawerlayout_fragment.Adapter;

import android.graphics.Bitmap;

import com.example.drawerlayout_fragment.Custom.ImageSet;

import java.util.List;

/**
 * Created by Administrator on 2016/10/28.
 */

public class ImagesGet {
    public static List<Bitmap> lbt;
    public static Bitmap lbc;

    public static List<Bitmap> getLbt() {
        return lbt;
    }

    public static void setLbt(List<Bitmap> lbt) {
        ImagesGet.lbt = lbt;
    }

    public static void addLbt(Bitmap lbc) {
//        ImagesGet.lbc = lbc;
        lbt.add(lbc);
    }


}
