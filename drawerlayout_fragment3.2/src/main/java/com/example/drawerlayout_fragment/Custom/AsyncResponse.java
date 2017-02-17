package com.example.drawerlayout_fragment.Custom;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */

public interface AsyncResponse {
    void onDataReceivedSuccess(List<Bitmap> listData,List<String> url);

    void onDataReceivedFailed();
}
