package com.example.drawerlayout_fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drawerlayout_fragment.Activity.ViewPage;
import com.example.drawerlayout_fragment.Custom.ItemClickListener;
import com.example.drawerlayout_fragment.JavaBean.Post2;
import com.example.drawerlayout_fragment.R;
import com.example.drawerlayout_fragment.config.CommonSharedPreferences;
import com.example.drawerlayout_fragment.config.GetTime;
import com.example.drawerlayout_fragment.config.MyApplication;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */

public class GameAdapter extends BaseAdapter {
    private Context context;
    private List<Post2> list;
    private List<String> piclist;
    private List<String> picture;
    private ItemClickListener mItemClickListener;

    public void setItemClickListener(ItemClickListener mItemClickListener) {

        this.mItemClickListener = mItemClickListener;
    }
    public GameAdapter(Context context, List<Post2> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder = null;
        if (convertView != null) {

            viewHolder = (ViewHolder) convertView.getTag();
        } else {
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
//        Post2 pos2 = (Post2) getItem(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mItemClickListener) {
                    mItemClickListener.onItemClick(v, position);
                }
            }
        });

        Post2 pos2=list.get(position);
        viewHolder.auther.setText(pos2.getAuthor().getUsername());
        viewHolder.title.setText(pos2.getTitle());
        viewHolder.content.setText(pos2.getContent());
//        String time=pos2.getTime();
        String time=pos2.getCreatedAt();
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
        picture=pos2.getUrls();
        if (picture != null) {
            viewHolder.gridview.setVisibility(View.VISIBLE);
//            piclist = new ArrayList();
//            piclist = Arrays.asList(picture.split(","));
            viewHolder.gridview.setAdapter(new CursorAdPic(context, picture));
            for (String s : picture) {
                System.out.println("位置:" + position + "|图片:" + s);
            }

//            viewHolder.gridview.setOnClickListener(this);
            viewHolder.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position2, long id) {
//                    System.out.println("点击的项目是:" + position);
                    System.out.println("点击的项目是:" + position + "|传过ViewPage去的图片:");

//                    for (String s : picture) {
//                        System.out.println("|传过ViewPage去的图片:" + s);
//                    }

                    ViewPage.toViewPage(context, position,list.get(position),list);
//                        new ViewPage(context,Urls.getList());
//                        Intent i = new Intent(context, ViewPage.class);
//                        context.startActivity(i);

                }
            });


        } else {
            System.out.println("空空");


        }

        return convertView;
    }
    class ViewHolder {
        LinearLayout show_pinlun, ly;
        TextView auther, title, content, tv_showtime, list_dianzan, getzan,
                pinlun_click;
        ImageView imgiv, imag, list_head;
        //        GridView grv_inner;
        LinearLayout rel_grid;
        SodukuGridView gridview;
    }
}
