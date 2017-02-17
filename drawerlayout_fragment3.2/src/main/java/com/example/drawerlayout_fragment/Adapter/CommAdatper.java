package com.example.drawerlayout_fragment.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drawerlayout_fragment.JavaBean.Post;
import com.example.drawerlayout_fragment.R;
import com.example.drawerlayout_fragment.config.MyApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */

public class CommAdatper extends BaseAdapter {
    private Context context;
    private List<Post> post;
    private Cursor cursor;

    public CommAdatper(Context context, List<Post> post) {
        this.context = context;
        this.post = post;

    }

    public CommAdatper(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (cursor.getCount() > 0) {
            return cursor.getCount();

        }
        return post.size();

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
    public View getView(int position, View convertView, ViewGroup parent) {
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
            convertView = inflater.inflate(R.layout.comm, parent, false);

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
        Post ppos = post.get(position);
        viewHolder.title.setText(ppos.getTitle());
        viewHolder.content.setText(ppos.getContent());
        viewHolder.auther.setText(ppos.getAuthor().getUsername());
        try {
            // viewHolder.imgiv.setBackground(post.get(position)
            // .getImage().getFileUrl());
            // viewHolder.imgiv.setImageURI(Uri.fromFile(new File(Environment
            // .getExternalStorageDirectory(), post.get(position)
            // .getImage().getFilename())));
            // viewHolder.imgiv.setImageURI("/storage/emulated/0"
            // + ppos.getImage().getFileUrl(context));

//            System.out.println("图片Filename():" + ppos.getImage().getFilename()
//                    + "\n");
//            System.out.println("图片FileUrl():" + ppos.getImage().getFileUrl()
//                    + "\n");
//            System.out.println("图片Url():" + ppos.getImage().getUrl() + "\n");
//            System.out.println("图片LocalFile():"
//                    + ppos.getImage().getLocalFile() + "\n");
            // ImageLoader.getInstance().loadImage(null, null, null, null,
            // null);
            ImageLoader.getInstance().displayImage(
                    ppos.getImage().getFileUrl(),
                    viewHolder.imgiv,
                    MyApplication.getInstance().getadaptermap(),
                    new SimpleImageLoadingListener() {

                        @Override
                        public void onLoadingComplete(String imageUri,
                                                      View view, Bitmap loadedImage) {
                            // TODO Auto-generated method stub
                            super.onLoadingComplete(imageUri, view, loadedImage);
                        }
                    });
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("设置图片失败" + "次:" + e);
        }
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(position);
            String autor = cursor.getString(cursor.getColumnIndex("name"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String picture = cursor.getString(cursor.getColumnIndex("picture"));

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
                        }
                    });
            cursor.close();
        }

        return convertView;
    }

    private static class ViewHolder {
        LinearLayout show_pinlun, ly;
        TextView auther, title, content, list_showtime, list_dianzan, getzan,
                pinlun_click;
        ImageView imgiv, imag;

    }
}

