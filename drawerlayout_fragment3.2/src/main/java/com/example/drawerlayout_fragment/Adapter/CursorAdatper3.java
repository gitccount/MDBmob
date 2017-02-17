package com.example.drawerlayout_fragment.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drawerlayout_fragment.R;
import com.example.drawerlayout_fragment.config.CommonSharedPreferences;
import com.example.drawerlayout_fragment.config.GetTime;
import com.example.drawerlayout_fragment.config.MyApplication;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */

public class CursorAdatper3 extends BaseAdapter {
    private Context context;
    private Cursor cursor;
    private Bitmap bitm;
    private List<Bitmap> btml = new ArrayList<>();
    ;
    private int n = 0, post;
    private String picture;
    private List<String> list;


    public CursorAdatper3(Context context, Cursor cursor) {
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
        post = position;
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
            viewHolder.rel_grid = (LinearLayout) convertView.findViewById(R.id.rel_grid);
            // 添加主题，详细
            viewHolder.title = (TextView) convertView
                    .findViewById(R.id.list_title);
            viewHolder.auther = (TextView) convertView
                    .findViewById(R.id.auther);
            viewHolder.content = (TextView) convertView
                    .findViewById(R.id.content);

            viewHolder.tv_showtime = (TextView) convertView
                    .findViewById(R.id.tv_showtime);

//            viewHolder.imgiv = (ImageView) convertView
//                    .findViewById(R.id.list_img);
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
            viewHolder.list_head = (ImageView) convertView
                    .findViewById(R.id.list_head);
//            viewHolder.grv_inner = (GridView) convertView
//                    .findViewById(R.id.grv_inner);
            viewHolder.gridview = (SodukuGridView) convertView
                    .findViewById(R.id.gridview);

            convertView.setTag(viewHolder);
        }
//        Post ppos = post.get(position);
//        viewHolder.title.setText(ppos.getTitle());
//        viewHolder.content.setText(ppos.getContent());
//        viewHolder.auther.setText(ppos.getAuthor().getUsername());

        cursor.moveToPosition(position);
        String autor = cursor.getString(cursor.getColumnIndex("name"));
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String time = cursor.getString(cursor.getColumnIndex("time"));
        final String content = cursor.getString(cursor.getColumnIndex("content"));
        picture = cursor.getString(cursor.getColumnIndex("urls"));

        viewHolder.auther.setText(autor);
        viewHolder.title.setText(title);
        viewHolder.content.setText(content);
//        viewHolder.tv_showtime.setText(time);
        viewHolder.tv_showtime.setText(new GetTime().howLongAgo(time));
        ImageLoader.getInstance().displayImage(CommonSharedPreferences.getHead(), viewHolder.list_head,
                MyApplication.getInstance().gettitlemap());
        viewHolder.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
//                System.out.println("你点击了" + position + "或者" + post);
            }
        });
        viewHolder.gridview.setVisibility(View.GONE);//需要知道getview什么时候回调，不设置就会出现图片不正确显示

        if (picture != null) {
            viewHolder.gridview.setVisibility(View.VISIBLE);
            list = new ArrayList();
            list = Arrays.asList(picture.split(","));
            viewHolder.gridview.setAdapter(new CursorAdPic(context, list));
            for (String s : list) {
                System.out.println("位置:" + position + "|图片:" + s);
            }

//            viewHolder.gridview.setOnClickListener(this);
            viewHolder.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position2, long id) {
//                    System.out.println("点击的项目是:" + position);
                    System.out.println("点击的项目是:" + position + "|传过ViewPage去的图片:");

                    for (String s : list) {
                        System.out.println("|传过ViewPage去的图片:" + s);
                    }

//                    ViewPage.toViewPage(context, position,null,list);
//                        new ViewPage(context,Urls.getList());
//                        Intent i = new Intent(context, ViewPage.class);
//                        context.startActivity(i);

                }
            });
        /*



            int length = 150;  //定义一个长度
            int size = list.size();  //得到集合长度
            //获得屏幕分辨路
//            DisplayMetrics dm = new DisplayMetrics();

            WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);

//            context.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            float density = dm.density;
            //                    Log.d(TAG, "handleMessage: "+density);
            int gridviewWidth = (int) (size * (length + 10) * density);
            int itemWidth = (int) (length * density);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
            viewHolder.grv_inner.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
            viewHolder.grv_inner.setColumnWidth(itemWidth); // 设置列表项宽
            viewHolder.grv_inner.setHorizontalSpacing(15); // 设置列表项水平间距
            viewHolder.grv_inner.setStretchMode(GridView.NO_STRETCH);
            viewHolder.grv_inner.setNumColumns(size); // 设置列数量=列表集合数



            viewHolder.grv_inner.setAdapter(new CursorAdPic(context, list));

            */

//            viewHolder.gridview.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    System.out.println("点击了列表");
//                }
//            });


        } else {
            System.out.println("空空");
//            viewHolder.rel_grid.setVisibility(View.GONE);


        }
        /*
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
*/
//        if (viewHolder.imgiv.getDrawable() == null) {
//            bitm = ((BitmapDrawable)viewHolder.imgiv.getDrawable()).getBitmap();
//            bitm = viewHolder.imgiv.getDrawingCache(true);
//            n++;
//        }
//        viewHolder.imgiv.setTag(position);// 不可少的方法

//        viewHolder.imgiv.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//
//            }
//
//        });
//                cursor.moveToPosition(position);
//                if (cursor.getString(cursor.getColumnIndex("picture")) != null) {
//
//                    ShowImage.toShow(context, cursor.getString(cursor.getColumnIndex("picture")));
//                }
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


        // getImageThumbnail gl = new getImageThumbnail();
        // ImageSet.setBitmap(gl.getImageThumbnail(url, 800, 800));


        return convertView;
    }


    private static class ViewHolder {
        LinearLayout show_pinlun, ly;
        TextView auther, title, content, tv_showtime, list_dianzan, getzan,
                pinlun_click;
        ImageView imgiv, imag, list_head;
        //        GridView grv_inner;
        LinearLayout rel_grid;
        SodukuGridView gridview;

    }
}

