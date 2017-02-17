package com.example.drawerlayout_fragment.Fragment;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.drawerlayout_fragment.Activity.AddComm;
import com.example.drawerlayout_fragment.Adapter.CommAdatper;
import com.example.drawerlayout_fragment.Adapter.CursorAdatper;
import com.example.drawerlayout_fragment.DB.MyDBOpenHelper;
import com.example.drawerlayout_fragment.JavaBean.CopyOfJkUser;
import com.example.drawerlayout_fragment.JavaBean.JkUser;
import com.example.drawerlayout_fragment.JavaBean.Post;
import com.example.drawerlayout_fragment.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.example.drawerlayout_fragment.Custom.Immples.toast;

/**
 * Created by Administrator on 2016/9/20.
 */
public class GameFragment extends Fragment {
    private List<Post> post_down = new ArrayList<Post>();
    private View view;
    private Context mContext;
    private CommAdatper ca;
    private SQLiteDatabase db;
    private MyDBOpenHelper myDBHelper;
    private StringBuilder sb;
    private Cursor cursor;

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
        final ListView lv = (ListView) view.findViewById(R.id.list);
        JkUser user = BmobUser.getCurrentUser(JkUser.class);
        BmobQuery<Post> query = new BmobQuery<Post>();
        query.addWhereEqualTo("author", user); // 查询当前用户的所有帖子
        query.order("-updatedAt");
        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
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
    }




    public void onDestroy() {

        super.onDestroy();
        Log.d("yes_______", "onDestroy");
        cursor.close();
    }
}
