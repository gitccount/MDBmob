package com.example.drawerlayout_fragment.Custom;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/10/28.
 */

public class ImageSet {
    public static Bitmap bitmap = null;
    public static int posdata=70;
    public static int getPosdata() {
        return posdata;
    }

    public static void setPosdata(int posdata) {
        ImageSet.posdata = posdata;
    }



    public static Bitmap getBitmap() {
        return bitmap;
    }

    public static void setBitmap(Bitmap bitmap) {
        ImageSet.bitmap = bitmap;
    }

}
