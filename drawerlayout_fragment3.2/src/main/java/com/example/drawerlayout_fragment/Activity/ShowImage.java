package com.example.drawerlayout_fragment.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.drawerlayout_fragment.Adapter.ImagesGet;
import com.example.drawerlayout_fragment.Custom.ImageHelper;
import com.example.drawerlayout_fragment.Custom.ImageSet;
import com.example.drawerlayout_fragment.R;
import com.example.drawerlayout_fragment.config.ExitApplication;
import com.example.drawerlayout_fragment.config.MyApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by Administrator on 2016/10/28.
 */

public class ShowImage extends Activity {
    private ImageView iamgeview;
    private RelativeLayout showimage;
    private static String url;

    public static void toShow(Context cont, String data1) {
//        this.url = url;
        Intent i = new Intent(cont, ShowImage.class);
        i.putExtra("param1", data1);
        cont.startActivity(i);
//        url=data1;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.show_iamge);
        ExitApplication.getInstance().addActivity(this);
        url = getIntent().getStringExtra("param1");
        System.out.println("得到的网址_:" + url);
        iamgeview = (ImageView) findViewById(R.id.imageview);
        showimage = (RelativeLayout) findViewById(R.id.showimage);
        ImageLoader.getInstance().displayImage(
                url,
                iamgeview,
                MyApplication.getInstance().getadaptermap());
//        new ImageHelper(this).display(iamgeview, url);
//        iamgeview.setImageBitmap(ImageSet.getBitmap());
        showimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImage.this.finish();
                moveTaskToBack(true);
                ExitApplication.getInstance().removeActivity(ShowImage.this);
                ImageSet.setBitmap(null);
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        ExitApplication.getInstance().removeActivity(this);
    }
}

