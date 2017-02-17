package com.example.drawerlayout_fragment.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/12.
 */
public class MyViewHolder extends RecyclerView.ViewHolder {
    LinearLayout show_pinlun, ly;
    TextView auther, title, content, tv_showtime, list_dianzan, getzan,
            pinlun_click;
    ImageView imgiv, imag, list_head;
    //        GridView grv_inner;
    LinearLayout rel_grid;
    SodukuGridView gridview;

    public MyViewHolder(View itemView) {
        super(itemView);
        show_pinlun = (LinearLayout) itemView;
        ly = (LinearLayout) itemView;
        auther = (TextView) itemView;
        title = (TextView) itemView;
        content = (TextView) itemView;
        tv_showtime = (TextView) itemView;
        list_dianzan = (TextView) itemView;
        getzan = (TextView) itemView;
        pinlun_click = (TextView) itemView;
        imgiv = (ImageView) itemView;
        imag = (ImageView) itemView;
        list_head = (ImageView) itemView;
        rel_grid = (LinearLayout) itemView;
        gridview = (SodukuGridView) itemView;

    }
}
