package com.example.drawerlayout_fragment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.drawerlayout_fragment.Adapter.CommAdatper;
import com.example.drawerlayout_fragment.Custom.ImageViewPlus;
import com.example.drawerlayout_fragment.JavaBean.JkUser;
import com.example.drawerlayout_fragment.JavaBean.Post;
import com.example.drawerlayout_fragment.R;
import com.example.drawerlayout_fragment.config.ExitApplication;
import com.example.drawerlayout_fragment.config.MyApplication;
import com.example.drawerlayout_fragment.Custom.Immples;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;


/**
 * Created by Administrator on 2016/10/25.
 */

public class Main_Page extends Activity implements View.OnClickListener {
    private List<Post> post_down = new ArrayList<Post>();
    private Button btn_change, btn_upload, btn_pointer, btn_querycomm;
    private TextView change_text;
    private ListView lv;
    private ImageView show_image;
    private BmobFile bmobfile;
    //	private String path = "/storage/emulated/0/11.png";
    private String path = "/storage/emulated/0/电子书/网页资料/SimpleListView/bin/res/crunch/drawable-ldpi/listview_image.png";
    private BmobFile icon;
    private ImageViewPlus Ivplu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        ExitApplication.getInstance().addActivity(this);
        initView();
//		Util.showToast(this,
//				"第二页面解析出来的用户名:"
//						+ BmobUser.getCurrentUser(JkUser.class).getUsername());
        // up.LongToast("第二页面解析出来的用户名:"
        // + BmobUser.getCurrentUser(JkUser.class).getUsername());
        System.out.println("第二页面解析出来的用户名:"
                + BmobUser.getCurrentUser(JkUser.class).getUsername());
    }

    private void initView() {
        // TODO Auto-generated method stub
        btn_change = (Button) findViewById(R.id.btn_changedata);
        btn_upload = (Button) findViewById(R.id.btn_uploadpic);
        btn_pointer = (Button) findViewById(R.id.btn_pointer);
        btn_querycomm = (Button) findViewById(R.id.btn_querycomm);
        change_text = (TextView) findViewById(R.id.change_textcontent);
        show_image = (ImageView) findViewById(R.id.show_imagepic);
        lv = (ListView) findViewById(R.id.list);
        Ivplu=(ImageViewPlus)findViewById(R.id.ImageViewPlus);
        btn_change.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
        btn_pointer.setOnClickListener(this);
        btn_querycomm.setOnClickListener(this);
        ImageLoader.getInstance().displayImage("https://www.baidu.com/img/baidu_jgylogo3.gif", Ivplu, MyApplication.getInstance().gettitlemap());
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_changedata:
                Immples.immples_query(new Immples.BackUpCallBackText() {

                    @Override
                    public void settext(String str) {
                        // TODO Auto-generated method stub
                        change_text.setText(str);
                    }

                });
                break;
            case R.id.btn_uploadpic:
//			Intent intent_up = new Intent(this, up.class);
                Intent intent_up = new Intent(this, AddComm.class);
                startActivity(intent_up);
                break;
            case R.id.btn_pointer:
                // 涉及到文件，都是先上传，成功后在移到表里，把其他事件也写在文件上传成功里，文上传失败则其他上传事件也失败
                File file = new File(path);
                icon = new BmobFile(file);
                icon.uploadblock(new UploadFileListener() {

                    @Override
                    public void done(BmobException arg0) {
                        // TODO Auto-generated method stub
                        if (arg0 == null) {
                            Log.i("bmob", "上传成功");
                            up.LongToast("bmob上传成功");
                            // 创建帖子信息
                            JkUser user = BmobUser.getCurrentUser(JkUser.class);
                            Post post = new Post();
                            post.setTitle("这是一个标题4");
                            post.setContent("这是一个帖子4");
                            post.setImage(icon);
                            // 添加一对一关联
                            post.setAuthor(user);
                            post.save(new SaveListener<String>() {

                                @Override
                                public void done(String objectId, BmobException e) {
                                    if (e == null) {
                                        Log.i("bmob", "保存成功");
                                        up.LongToast("bmob保存成功");
                                    } else {
                                        Log.i("bmob", "保存失败：" + e.getMessage());
                                        up.LongToast("bmob" + "保存失败："
                                                + e.getMessage());
                                        System.out.println("bmob" + "保存失败："
                                                + e.getMessage());
                                    }
                                }
                            });
                        } else {
                            Log.i("bmob", "上传失败");
                            up.LongToast("bmob上传失败");
                            System.out.println("bmob上传失败" + arg0);
                        }
                    }
                });

                break;
            case R.id.btn_querycomm:
                JkUser user = BmobUser.getCurrentUser(JkUser.class);
                BmobQuery<Post> query = new BmobQuery<Post>();
                query.addWhereEqualTo("author", user); // 查询当前用户的所有帖子
                query.order("-updatedAt");
                query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
                query.findObjects(new FindListener<Post>() {
                    int n = 1;
                    private CommAdatper ca;

                    @Override
                    public void done(List<Post> object, BmobException e) {
                        if (e == null) {
                            post_down.clear();
                            String str = "";
                            Log.i("bmob", "成功");
                            for (Post pos : object) {
                                str += "/作者:" + pos.getAuthor().getUsername();
                                str += "/标题:" + pos.getTitle();
                                str += "/内容:" + pos.getContent() + "\n";

                                try {// 可能帖子会没有图片
                                    show_image.setImageURI(Uri.fromFile(new File(
                                            Environment
                                                    .getExternalStorageDirectory(),
                                            pos.getImage().getFilename())));
                                } catch (Exception e2) {
                                    // TODO: handle exception
                                    System.out.println("设置图片失败" + n + "次:" + e2);
                                    n++;
                                }

                                post_down.add(new Post(pos.getTitle(), pos.getContent(), pos
                                        .getAuthor(), pos.getImage(), null));
                            }
                            ca = new CommAdatper(Main_Page.this,post_down);
                            lv.setAdapter(ca);
                            change_text.setText(str);
                            System.out.println(str);
                            // File saveFile = new File(Environment
                            // .getExternalStorageDirectory(), object.get(0)
                            // .getImage().getFilename());

                            // Uri ui=Uri.fromFile(new
                            // File(object.get(0).getImage().getFileUrl()));
                            // System.out.println("图片路径"+object.get(2).getImage().getFileUrl());
                            // if(object.get(0).getImage().getFileUrl() != null){

                            // }else{
                            // show_image.setImageURI(Uri.fromFile(new
                            // File(object.get(1).getImage().getFileUrl())));
                            // }

                        } else {
                            Log.i("bmob", "失败：" + e.getMessage());
                        }
                    }

                });
                break;
            default:
                break;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        ExitApplication.getInstance().removeActivity(this);
    }

}