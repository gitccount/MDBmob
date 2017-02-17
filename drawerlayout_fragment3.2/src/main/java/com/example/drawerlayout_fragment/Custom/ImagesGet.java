package com.example.drawerlayout_fragment.Custom;

import android.graphics.Bitmap;

import java.util.List;

import static android.R.id.list;

/**
 * Created by Administrator on 2016/10/28.
 */

public class ImagesGet {
    private List<Bitmap> bitm;
    private Bitmap bt;

    public List<Bitmap> getBitm() {
        return bitm;
    }

    public void setBitm(List<Bitmap> bitm) {
        this.bitm = bitm;
    }

    public Bitmap getBt() {
        return bt;
    }

    public void setBt(Bitmap bt) {
        this.bt = bt;
    }

    private void addBit(Bitmap bt) {
        this.bt = bt;
        bitm.add(bt);
    }
}
