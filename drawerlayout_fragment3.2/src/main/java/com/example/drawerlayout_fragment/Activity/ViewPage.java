package com.example.drawerlayout_fragment.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.drawerlayout_fragment.Custom.Urls;
import com.example.drawerlayout_fragment.DB.MyDBOpenHelper;
import com.example.drawerlayout_fragment.JavaBean.Post2;
import com.example.drawerlayout_fragment.R;
import com.example.drawerlayout_fragment.config.MyApplication;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.jude.rollviewpager.hintview.IconHintView;
import com.jude.rollviewpager.hintview.TextHintView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewPage extends AppCompatActivity {
    private List<String> data2;

    private RollPagerView mRollViewPager;

    private static int position;
//    private SQLiteDatabase db;
//    private MyDBOpenHelper myDBHelper;
//    private StringBuilder sb;
    private Cursor cursor;
    private String picture;
    private static List<String> urls;
    private static Post2 pos2,pos2t;
    private static List<Post2> post2s;

    public static void toViewPage(Context context, Integer postion1, Post2 pos2,List<Post2> post2s) {
        Intent i = new Intent(context, ViewPage.class);
        context.startActivity(i);
        position = postion1;
        urls = pos2.getUrls();
        pos2t=post2s.get(position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpage);
/*        myDBHelper = new MyDBOpenHelper(this, "posts", null, 1);
        db = myDBHelper.getWritableDatabase();
        cursor = db.query("posts", null, null, null,
                null, null, null);
        cursor.moveToPosition(position);
        picture = cursor.getString(cursor.getColumnIndex("urls"));
        urls = new ArrayList();
        urls = Arrays.asList(picture.split(","));*/
        mRollViewPager = (RollPagerView) findViewById(R.id.roll_view_pager);

        //设置播放时间间隔
        mRollViewPager.setPlayDelay(1000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new TestNormalAdapter());

        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
//        mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
//        mRollViewPager.setHintView(new ColorPointHintView(this, Color.YELLOW, Color.WHITE));
        mRollViewPager.setHintView(new TextHintView(this));
        //mRollViewPager.setHintView(null);

    }

    private class TestNormalAdapter extends StaticPagerAdapter {
        //        private int[] imgs = {
//                R.drawable.img1,
//                R.drawable.img2,
//                R.drawable.img3,
//                R.drawable.img4,
//        };

        @Override
        public View getView(ViewGroup container, int position2) {


            ImageView view = new ImageView(container.getContext());
//            view.setImageResource(imgs[position]);
//            view.setScaleType(ImageView.ScaleType.CENTER_CROP);//按图片的原来size居中显示，当图片长/宽超过View的长/宽，则截 取图片的居中部分显示
            view.setScaleType(ImageView.ScaleType.CENTER_INSIDE );//改变图片显示大小的控制参数就在这里
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            if (urls != null) {

                ImageLoader.getInstance().displayImage(
                        urls.get(position2),
                        view,
                        MyApplication.getInstance().getadaptermap(), null);
            }
            return view;
        }


        @Override
        public int getCount() {
            return urls.size();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void onDestroy() {

        super.onDestroy();
//        cursor.close();
    }
}
