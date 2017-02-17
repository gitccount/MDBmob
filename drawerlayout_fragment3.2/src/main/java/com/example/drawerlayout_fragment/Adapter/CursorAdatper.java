package com.example.drawerlayout_fragment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drawerlayout_fragment.Activity.ShowImage;
import com.example.drawerlayout_fragment.Custom.ImageSet;
import com.example.drawerlayout_fragment.JavaBean.Post;
import com.example.drawerlayout_fragment.R;
import com.example.drawerlayout_fragment.config.MyApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */

public class CursorAdatper extends BaseAdapter {
    private Context context;
    private Cursor cursor;
    private Bitmap bitm;
    private List<Bitmap> btml = new ArrayList<>();
    ;
    private int n = 0;
    private String picture;


    public CursorAdatper(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return cursor.getCount();

    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // layout = (LinearLayout) inflater.inflate(R.layout.cell, null);

        // View view = null;
        // 使用ViewHolder复用方法，优化
        // ViewHolder viewHolder = new ViewHolder();
        ViewHolder viewHolder = null;
        if (convertView != null) {
            // view = convertView;
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            // view = inflater.inflate(R.layout.cell, parent, false);
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.comm_cursor, parent, false);

            viewHolder.ly = (LinearLayout) convertView.findViewById(R.id.ly);
            // 添加主题，详细
            viewHolder.title = (TextView) convertView
                    .findViewById(R.id.list_title);
            viewHolder.auther = (TextView) convertView
                    .findViewById(R.id.auther);
            viewHolder.content = (TextView) convertView
                    .findViewById(R.id.content);

            viewHolder.list_showtime = (TextView) convertView
                    .findViewById(R.id.list_showtime);

            viewHolder.imgiv = (ImageView) convertView
                    .findViewById(R.id.list_img);
            // networkImageView = (NetworkImageView)convertView
            // .findViewById(R.id.network_image_view);
            // 点赞栏
            viewHolder.getzan = (TextView) convertView
                    .findViewById(R.id.list_getzan);
            viewHolder.list_dianzan = (TextView) convertView
                    .findViewById(R.id.list_dianzan);
            // 评论
            viewHolder.pinlun_click = (TextView) convertView
                    .findViewById(R.id.pinlun_click);

            convertView.setTag(viewHolder);
        }
//        Post ppos = post.get(position);
//        viewHolder.title.setText(ppos.getTitle());
//        viewHolder.content.setText(ppos.getContent());
//        viewHolder.auther.setText(ppos.getAuthor().getUsername());
        cursor.moveToPosition(position);
        String autor = cursor.getString(cursor.getColumnIndex("name"));
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String content = cursor.getString(cursor.getColumnIndex("content"));
        picture = cursor.getString(cursor.getColumnIndex("picture"));

        viewHolder.auther.setText(autor);
        viewHolder.title.setText(title);
        viewHolder.content.setText(content);
        ImageLoader.getInstance().displayImage(
                picture,
                viewHolder.imgiv,
                MyApplication.getInstance().getadaptermap(),
                new SimpleImageLoadingListener() {

                    @Override
                    public void onLoadingComplete(String imageUri,
                                                  View view, Bitmap loadedImage) {
                        // TODO Auto-generated method stub
                        super.onLoadingComplete(imageUri, view, loadedImage);
//                        ImageSet.setBitmap(loadedImage);
//                        ImagesGet.addLbt(loadedImage);
//                        btml.add(loadedImage);
//                        btml.set(position,loadedImage);
//                        bitm = loadedImage;
//                        ImageSet.setBitmap(bitm);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        // Empty implementation
//                        btml.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_stub));
                        n++;
                    }
                });

//        if (viewHolder.imgiv.getDrawable() == null) {
//            bitm = ((BitmapDrawable)viewHolder.imgiv.getDrawable()).getBitmap();
//            bitm = viewHolder.imgiv.getDrawingCache(true);
//            n++;
//        }
//        viewHolder.imgiv.setTag(position);// 不可少的方法
        viewHolder.imgiv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                /*
                 * int pos = Integer.parseInt(view.getTag().toString());// 获取位置
				 * Bitmap bitmap = null; // 处理，获取缩略图 BitmapFactory.Options
				 * options = new BitmapFactory.Options();
				 * options.inJustDecodeBounds = true;
				 */
//                compressImage cp = new compressImage();// 压缩图片
//                cp.getimage(url);
                // bitmap=cp.compressImage(bitmap);
                // BitmapFactory.Options options = new BitmapFactory.Options();
                // ImageSet.setBitmap(BitmapFactory.decodeFile(url));
//                        ImageSet.setBitmap(bitm);
//                System.out.println("点击的位置 :"+position);
//                ImageSet.setBitmap(btml.get(position));
                cursor.moveToPosition(position);
                if (cursor.getString(cursor.getColumnIndex("picture")) != null) {

                    ShowImage.toShow(context, cursor.getString(cursor.getColumnIndex("picture")));
                }


                // getImageThumbnail gl = new getImageThumbnail();
                // ImageSet.setBitmap(gl.getImageThumbnail(url, 800, 800));


            }

            ;
        });


        return convertView;
    }

    private static class ViewHolder {
        LinearLayout show_pinlun, ly;
        TextView auther, title, content, list_showtime, list_dianzan, getzan,
                pinlun_click;
        ImageView imgiv, imag;

    }
}

