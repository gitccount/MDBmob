package com.example.drawerlayout_fragment.Custom;

import android.graphics.Bitmap;

import java.util.List;

public interface PicAsyncResPonse {
    void onPicReceivedSuccess(List<String> url);

    void onPicReceivedFailed();

    void onReReceivedload(int totalPercent);

    void onNoPic(boolean bl);

}
