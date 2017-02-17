package com.example.drawerlayout_fragment.Fragment;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.Toast;

import com.example.drawerlayout_fragment.Adapter.CommAdatper;
import com.example.drawerlayout_fragment.Adapter.CursorAdatper3;
import com.example.drawerlayout_fragment.DB.MyDBOpenHelper;
import com.example.drawerlayout_fragment.JavaBean.CopyOfJkUser;
import com.example.drawerlayout_fragment.JavaBean.JkUser;
import com.example.drawerlayout_fragment.JavaBean.Post2;
import com.example.drawerlayout_fragment.R;
import com.example.drawerlayout_fragment.config.PullDownListView;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/9/20.
 * 将图片转换为String数组，并用‘，’号隔开，存入数据库，后面取出来时再加以分割还原成List；
 */
public class GameFragment2 extends Fragment implements PullDownListView.OnRefreshListioner{
    private List<Post2> post_down = new ArrayList<Post2>();
    private View view;
    private Context mContext;
    private CommAdatper ca;
    private SQLiteDatabase db;
    private MyDBOpenHelper myDBHelper;
    private StringBuilder sb;
    private Cursor cursor;
    private FloatingActionButton fab_reset;
    private Animation rotate;
    private ListView lv;
    private PullDownListView mPullDownView;
    private Handler handler;
    private CursorAdatper3 cursoradapter3;
    private Handler mHandler = new Handler();
    //    private PullToRefreshListView pulltorefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_game, null);
        mContext = this.getActivity();
        myDBHelper = new MyDBOpenHelper(mContext, "posts", null, 1);
        db = myDBHelper.getWritableDatabase();
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("创建了一次", "onActivityCreated_____________");
        lv = (ListView) view.findViewById(R.id.list);
        querydata();
        fab_reset = (FloatingActionButton) view.findViewById(R.id.fab_reset);
//        fab_reset.setImageResource(R.drawable.ic_reset);
//        fab_reset.getBackground().setAlpha(30);//0~255透明度值
//        final RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
//                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        animation.setDuration(3000);//设置动画持续时间
//        animation.setFillAfter(false);//动画执行完后是否停留在执行完的状态
//        fab_reset.setAnimation(animation);
//        animation.startNow();
        // 加载动画
        rotate = AnimationUtils.loadAnimation(mContext, R.anim.widget_rotate);
// 动画执行完后停留在执行完的状态
        rotate.setFillAfter(false);
// 执行动画
        fab_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println("点击了炫富按钮");
//                Toast.makeText(mContext, "点击了炫富按钮", Toast.LENGTH_SHORT).show();

                /** 开始动画 */
                fab_reset.startAnimation(rotate);
                onRefresh();
            }
        });
        mPullDownView = (PullDownListView) view.findViewById(R.id.sreach_list);
        mPullDownView.setRefreshListioner(this);
//        mPullDownView.setRefreshListioner(new PullDownListView.OnRefreshListioner() {
//            @Override
//            public void onRefresh() {
//
//            }
//
//            @Override
//            public void onLoadMore() {
//
//            }
//        });
        mPullDownView.setMore(true);

//        pulltorefresh=(PullToRefreshListView)view.findViewById(R.id.pulltorefresh);
//        pulltorefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
//                Toast.makeText(mContext, "PullToRefreshBase.OnRefreshListener", Toast.LENGTH_SHORT).show();
//            }
//        });
        /*
        query.findObjects(new FindListener<Post>() {
            int n = 1;

            @Override
            public void done(List<Post> object, BmobException e) {
                if (e == null) {
                    post_down.clear();
                    String str = "";
                    Log.i("bmob", "成功");
                    String DEL = "delete from posts";
                    db.execSQL(DEL);
                    int n = 1, o = 1;
                    for (Post pos : object) {
                        String picst = "123";

                        str += "/作者:" + pos.getAuthor().getUsername();
                        str += "/标题:" + pos.getTitle();
                        str += "/内容:" + pos.getContent() + "\n";

                        System.out.println("获得" + o + "次数据___");
                        post_down.add(new Post(pos.getTitle(), pos.getContent(), pos
                                .getAuthor(), pos.getImage(), null));
                        o++;
                        //得先进行图片是否存在的判断，否则直接执行取图片地址语句，
                        if (pos.getImage() == null) {
                            picst = null;
                        } else {
                            picst = pos.getImage().getFileUrl();
                        }
//                        picst=pos.getImage().getFileUrl();

                        ContentValues values1 = new ContentValues();
                        try {
                            values1.put("name", pos.getAuthor().getUsername());
                            values1.put("title", pos.getTitle());
                            values1.put("content", pos.getContent());
                            values1.put("picture", picst);

                            System.out.println("第" + n + "次放入数据库___");
                            db.insert("posts", null, values1);
                            n++;


                        } catch (Exception e2) {

                        }

                    }

                    cursor = db.query("posts", null, null, null,
                            null, null, null);
                    lv.setAdapter(new CursorAdatper(mContext, cursor));

                } else {
                    Log.i("bmob", "失败：" + e.getMessage());
                    cursor = db.query("posts", null, null, null,
                            null, null, null);
                    lv.setAdapter(new CursorAdatper(mContext, cursor));
                }
            }

        });
        */
    }

    private void querydata() {
        JkUser user = BmobUser.getCurrentUser(JkUser.class);
        BmobQuery<Post2> query = new BmobQuery<Post2>();
        query.addWhereEqualTo("author", user); // 查询当前用户的所有帖子
        query.order("-updatedAt");
        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
//        query.getObject("0e317bd64a", new QueryListener<Post>() {
//            @Override
//            public void done(Post post, BmobException e) {
//                if (e == null) {
//                    for (String s : post.getUrls()) {
//
//                        System.out.println("得到post网址:"+s);
//                    }
//                }
//            }
//        });
        query.findObjects(new FindListener<Post2>() {
            private String urlstring;
            private String[] array;
            int n = 1;

            @Override
            public void done(List<Post2> object, BmobException e) {
                if (e == null) {
                    post_down.clear();

                    Log.i("bmob", "成功");
                    String DEL = "delete from posts";
                    db.execSQL(DEL);
                    int n = 1, o = 1;
                    System.out.println("解得数据长度:" + object.size());
                    for (Post2 pos : object) {
                        String str = "";
                        List<String> picst = new ArrayList<String>();

                        if (pos.getUrls() == null) {

                        }
                        System.out.println("获得" + o + "次数据___");
                        post_down.add(new Post2(pos.getTitle(), pos.getContent(), pos.getTime(), pos
                                .getAuthor(), pos.getUrls(), null));
                        o++;
                        //得先进行图片是否存在的判断，否则直接执行取图片地址语句，

                        if (pos.getUrls() == null) {
                            urlstring = null;
                        } else {
                            picst = pos.getUrls();
//                            array = new String[pos.getUrls().size()];
//                            pos.getUrls().toArray(array);
//                            urlstring = Arrays.toString(array);
                            urlstring = listToString(picst, ',');
                        }
                        str += "*作者:" + pos.getAuthor().getUsername();
                        str += "/标题:" + pos.getTitle();
                        str += "/内容:" + pos.getContent();
                        str += "/时间:" + pos.getTime();
                        str += "/网址:" + urlstring + "\n";

                        System.out.println("解析数据并存入sql:" + str);

//                        picst=pos.getImage().getFileUrl();

                        ContentValues values1 = new ContentValues();
                        try {
                            values1.put("name", pos.getAuthor().getUsername());
                            values1.put("title", pos.getTitle());
                            values1.put("content", pos.getContent());
                            values1.put("time", pos.getTime());
                            values1.put("urls", urlstring);

                            System.out.println("第" + n + "次放入数据库___");
                            db.insert("posts", null, values1);
                            n++;


                        } catch (Exception e2) {

                        }

                    }

                    cursor = db.query("posts", null, null, null,
                            null, null, null);
                    cursoradapter3=new CursorAdatper3(mContext, cursor);
                    lv.setAdapter(cursoradapter3);
                    if (cursor != null && cursor.moveToFirst()) {

                        do {
                            String autor = cursor.getString(cursor.getColumnIndex("name"));
                            String title = cursor.getString(cursor.getColumnIndex("title"));
                            String content = cursor.getString(cursor.getColumnIndex("content"));
                            String time = cursor.getString(cursor.getColumnIndex("time"));
                            String picture = cursor.getString(cursor.getColumnIndex("urls"));
                            List<String> list = new ArrayList();
                            if (picture != null) {

                                list = Arrays.asList(picture.split(","));
                                for (String s : list) {

                                    System.out.println("缴获网址:" + s);
                                }
                            } else {
                                System.out.println(")))))))))))))))))))))))))))))))))))");
                                list = null;
                            }
                            System.out.println("从sql中取出autor:" + autor + "|title" + title + "|content" + content + "|time" + time + "|picture:" + list + "\n");

                        } while (cursor.moveToNext());

                    }
                } else {
                    Log.i("bmob", "失败：" + e.getMessage());
                    cursor = db.query("posts", null, null, null,
                            null, null, null);
                    cursoradapter3=new CursorAdatper3(mContext, cursor);
                    lv.setAdapter(cursoradapter3);
                }
            }

        });

    }

    //获得头像
    private String gethead(String st) {
        BmobQuery<CopyOfJkUser> query = new BmobQuery<CopyOfJkUser>();
        query.addWhereEqualTo("username", st);
        query.addQueryKeys("head");
        query.findObjects(new FindListener<CopyOfJkUser>() {
            @Override
            public void done(List<CopyOfJkUser> list, BmobException e) {
                if (e == null) {
                    for (CopyOfJkUser cp : list) {
                        cp.getHead().getFileUrl();
                    }
                } else {
                }
            }

        });


        return st;
    }

    //把list列表转换为String字符串，之间加上逗号
    public String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    public void onDestroy() {

        super.onDestroy();
        Log.d("yes_______", "onDestroy");
        cursor.close();
    }

    @Override
    public void onRefresh() {
//        Toast.makeText(mContext, "onRefresh", Toast.LENGTH_SHORT).show();
        handler = new Handler();
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                // 在此处添加执行的代码
                mPullDownView.onRefreshComplete();// 这里表示刷新处理完成后把上面的加载刷新界面隐藏
                handler.removeCallbacks(this); //移除定时任务
                cursoradapter3.notifyDataSetChanged();
                mPullDownView.setMore(true);
            }
        };
        handler.postDelayed(runnable, 1000);// 打开定时器，50ms后执行runnable操作
    }

    @Override
    public void onLoadMore() {
        Toast.makeText(mContext, "onLoadMore", Toast.LENGTH_SHORT).show();

        handler = new Handler();
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                // 在此处添加执行的代码
                mPullDownView.onLoadMoreComplete();// 这里表示刷新处理完成后把上面的加载刷新界面隐藏
                mPullDownView.setMore(true);//这里设置true表示还有更多加载，设置为false底部将不显示更多
                handler.removeCallbacks(this); //移除定时任务
            }
        };
        handler.postDelayed(runnable, 1000);// 打开定时器，50ms后执行runnable操作
    }


/*    class MyRunnable implements Runnable {

        boolean isRefresh;

        public MyRunnable(boolean isRefresh) {
            this.isRefresh = isRefresh;
        }

        @Override
        public void run() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isRefresh) {
                        mPullDownView.onRefreshComplete();// 这里表示刷新处理完成后把上面的加载刷新界面隐藏
                        handler.removeCallbacks(this); //移除定时任务
                        cursoradapter3.notifyDataSetChanged();
//                        newData();
//                        refreshComplate();
//                        // 刷新完成后调用，必须在UI线程中
//                        mRecyclerView.refreshComplate();
                    } else {
//                        addData();
//                        loadMoreComplate();
//                        // 加载更多完成后调用，必须在UI线程中
//                        mRecyclerView.loadMoreComplate();
                        mPullDownView.onLoadMoreComplete();// 这里表示刷新处理完成后把上面的加载刷新界面隐藏
                        mPullDownView.setMore(true);//这里设置true表示还有更多加载，设置为false底部将不显示更多
                        handler.removeCallbacks(this); //移除定时任务
                        cursoradapter3.notifyDataSetChanged();
                    }
                }
            }, 2000);
        }
    }*/
}
