package com.example.drawerlayout_fragment.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.drawerlayout_fragment.Adapter.GridViewAdapter2;
import com.example.drawerlayout_fragment.AsyncTask.UpPicAsyncTask;
import com.example.drawerlayout_fragment.AsyncTask.UploadPostandPic;
import com.example.drawerlayout_fragment.Custom.AsyncResponse;
import com.example.drawerlayout_fragment.AsyncTask.ImageAsyncTask;
import com.example.drawerlayout_fragment.Custom.ImageHelper;
import com.example.drawerlayout_fragment.Custom.PicAsyncResPonse;
import com.example.drawerlayout_fragment.JavaBean.CopyOfJkUser;
import com.example.drawerlayout_fragment.JavaBean.JkUser;
import com.example.drawerlayout_fragment.JavaBean.Pics;
import com.example.drawerlayout_fragment.JavaBean.Post;
import com.example.drawerlayout_fragment.R;
import com.example.drawerlayout_fragment.config.CommonSharedPreferences;
import com.example.drawerlayout_fragment.config.ExitApplication;
import com.example.drawerlayout_fragment.config.GetTime;
import com.example.drawerlayout_fragment.config.MyApplication;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoSelectorActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2016/10/25.
 */

public class AddComm extends Activity implements View.OnClickListener, GridViewAdapter2.MyClickListener {
    private List<Bitmap> environmentList;
    private static final int SELECT_IMAGE_CODE = 2;
    private Button savebtn, c_show;
    private static EditText title, addcontent;
    private ImageView c_showimg, c_img, c_del, iv_showadd, iv_showaddpic, iv_deladd, inner_del, inner_showa,c_head;
    private TextView username;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    private File phoneFile;
    private static final int IMAGE_REQUEST_CODE = 0, CAMERA_REQUEST_CODE = 1,
            RESIZE_REQUEST_CODE = 2;
    private String FinaUrl = "/storage/emulated/0/11.png";
    private static BmobFile icon;
    private GridView gdv_addpic;
    private GridViewAdapter2 grdadapt;
    private GridViewAdapter2.MyClickListener mListener;
    private List<String> urls, getresurl;
    private ProgressDialog dialog;
//    private Pics per;
//    private Post post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcomm2);
        ExitApplication.getInstance().addActivity(this);
        environmentList = new ArrayList<Bitmap>();
        urls = new ArrayList<String>();
        // TextView addpicbtn = (TextView) findViewById(R.id.addpic);
        intview();

        CopyOfJkUser newUser2 = BmobUser.getCurrentUser(CopyOfJkUser.class);
        if (newUser2.getHead() != null) {

            String im = newUser2.getHead().getFileUrl();
            new ImageHelper(this).display(c_head, CommonSharedPreferences.getHead());
        }
        username.setText(BmobUser.getCurrentUser(JkUser.class).getUsername());
    }

    private void intview() {
        // 加入主题、报修详情
        title = (EditText) findViewById(R.id.title);
        gdv_addpic = (GridView) findViewById(R.id.grv_setpic);
        addcontent = (EditText) findViewById(R.id.addcontent);
        username = (TextView) findViewById(R.id.username);
        savebtn = (Button) findViewById(R.id.save);
        c_show = (Button) findViewById(R.id.c_show);
        c_showimg = (ImageView) findViewById(R.id.c_showimg);
        c_img = (ImageView) findViewById(R.id.c_img);
        iv_deladd = (ImageView) findViewById(R.id.iv_deladd);
        iv_showaddpic = (ImageView) findViewById(R.id.iv_showaddpic);
        iv_showadd = (ImageView) findViewById(R.id.iv_showadd);
        c_head = (ImageView) findViewById(R.id.c_head);
//        iv_showadd.setClickable(false);
        savebtn.setOnClickListener(this);
        iv_showaddpic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.iv_showaddpic:
                Intent intent = new Intent(AddComm.this, PhotoSelectorActivity.class);
                intent.putExtra(PhotoSelectorActivity.KEY_MAX, 6);//选择多少张图片，默认最大值为10
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent, SELECT_IMAGE_CODE);
                gdv_addpic.setVisibility(View.VISIBLE);
//                iv_deladd.setVisibility(View.VISIBLE);
                break;
            case R.id.save:

                if ((title.getText().toString().trim().isEmpty()) && (addcontent.getText().toString().trim().isEmpty()) && (environmentList.size() == 0)) {
                    Toast.makeText(AddComm.this, "图片和文字为空，请重新编辑", Toast.LENGTH_SHORT).show();
                } else {

//                    new UploadPostandPic().uppic(AddComm.this, getresurl,
//                            title.getText().toString().trim(), addcontent.getText().toString().trim());
                    UploadPostandPic uppc = new UploadPostandPic();
                    uppc.uppic(AddComm.this, getresurl,
//                            title.getText().toString().trim(), addcontent.getText().toString().trim(), new GetTime().getNowTIme());
                            title.getText().toString().trim(), addcontent.getText().toString().trim());
                    uppc.setResposur(new PicAsyncResPonse() {
                        @Override
                        public void onPicReceivedSuccess(List<String> url) {
                            for (String s : url) {
                                System.out.println("嘻嘻，得到保存网址:" + s);
                            }
//                            finish();
                        }

                        @Override
                        public void onPicReceivedFailed() {

                        }

                        @Override
                        public void onReReceivedload(int totalPercent) {
                            dialog = new ProgressDialog(AddComm.this);
                            //设置进度条风格，风格为圆形，旋转的
                            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            //设置ProgressDialog 标题
                            dialog.setTitle("正在上传");
                            //设置ProgressDialog 提示信息
                            dialog.setMessage("请稍等");
                            //设置ProgressDialog 标题图标
                            dialog.setIcon(android.R.drawable.ic_dialog_alert);
                            //设置ProgressDialog的最大进度
                            dialog.setMax(100);
                            //设置ProgressDialog 的一个Button
                            dialog.setButton("确定", new ProgressDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            //设置ProgressDialog 是否可以按退回按键取消
                            dialog.setCancelable(true);
                            //显示
                            dialog.show();
                            //设置ProgressDialog的当前进度
                            dialog.setProgress(totalPercent);
                            if (totalPercent == 100) {
//                                Timer timer = new Timer(true);
//                                timer.schedule(
//                                        new java.util.TimerTask() {
//                                            public void run() { //server.checkNewMail(); 要操作的方法
//
//                                            }
//                                        }, 0, 20 * 60 * 1000);
                                //3秒倒计时
                                new CountDownTimer(2000, 1000) {
                                    public void onTick(long millisUntilFinished) {
//                                        mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                                    }

                                    public void onFinish() {
//                                        mTextField.setText("done!");
                                        dialog.dismiss();
                                        Intent intent1 = new Intent(AddComm.this, MainActivity.class);
                                        AddComm.this.finish();
                                    }
                                }.start();

                            }
                        }

                        @Override
                        public void onNoPic(boolean bl) {


                            Intent intent1 = new Intent(AddComm.this, MainActivity.class);
                            AddComm.this.finish();
                        }

                    });

//                    UpPicAsyncTask uptask = new UpPicAsyncTask(AddComm.this, getresurl,
//                            title.getText().toString().trim(), addcontent.getText().toString().trim());
//                    uptask.execute();
//                    uptask.setOnAsyncResponse(new PicAsyncResPonse() {
//                        @Override
//                        public void onPicReceivedSuccess(List<String> url) {
//                            for (String s : url) {
//                                System.out.println("嘻嘻，得到保存网址:" + s);
//                            }
////                            finish();
//                        }
//
//                        @Override
//                        public void onPicReceivedFailed() {
//
//                        }
//                    });
                }
                // TODO Auto-generated method stub

                break;
            default:
                break;

        }

    }

    private void UploadPost() {
        Post post;
        post = new Post();
//                                post.setPics(per);
//                                relation.add(per);
//                                post.setPics(relation);
        JkUser user = BmobUser.getCurrentUser(JkUser.class);
        post.setUrls(urls);
        post.setTitle(title.getText().toString().trim());
        post.setContent(addcontent.getText().toString().trim());
        post.setAuthor(user);
        post.save(new SaveListener<String>() {

            @Override
            public void done(String arg0, BmobException arg1) {
                // TODO Auto-generated method stub

                if (arg1 == null) {
                    for (String s : urls) {
                        System.out.println("保存的网址:" + s);
                    }

                    LongToast("帖子保存成功" + arg0);
                    System.out.println("帖子保存成功" + arg0);
                    finishthis();
                } else {
                    LongToast("帖子保存失败" + arg1);
                    System.out.println("帖子保存失败" + arg1);
                }
            }

        });
    }

    public void UploadPic(String imgpath) {


//        System.out.println("_____传过来的路径:" + imgpath);
        File file = new File(imgpath);
        icon = new BmobFile(file);

        // final BmobFile icon = new BmobFile(new File(imgpath));
        icon.uploadblock(new UploadFileListener() {
            Pics per;

            public void done(BmobException arg0) {
                // TODO Auto-generated method stub
                if (arg0 == null) {

                    LongToast("上传成功");// 上传完成后保存到Pics表

                    per = new Pics();
                    per.setIcon(icon);
                    per.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                icon.getFileUrl();
                                icon.getUrl();
                                icon.getFilename();
                                icon.getLocalFile();
                                urls.add(icon.getFileUrl());
//                                System.out.println("getFileUrl:"+icon.getFileUrl()+"/icon.getUrl:"+icon.getUrl()+
//                                        "/icon.getFilename:"+icon.getFilename()+"/icon.getLocalFile:"+icon.getLocalFile());
                                LongToast("图片保存的id" + s);
//                                per.setObjectId(s);


                            } else {
                                LongToast("图片保存失败" + e);
                                System.out.println("图片保存失败" + e);
                            }
                        }
                    });


                } else {
                    LongToast("上传失败" + arg0);
                    System.out.println("保存失败" + arg0);
                }
            }

        });
//        file.delete();
    }

    private void finishthis() {
        ExitApplication.getInstance().removeActivity(AddComm.this);
        startActivity(new Intent(AddComm.this, MainActivity.class));
    }


    static void LongToast(String msg) {
        Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT)
                .show();
    }

    protected void onDestroy() {
        super.onDestroy();
        ExitApplication.getInstance().removeActivity(this);
    }

    private void sendRequestForListData(ArrayList<String> strlist) {
        try {
            ImageAsyncTask socketConn = new ImageAsyncTask(AddComm.this, iv_showadd, strlist);
            socketConn.execute();
            socketConn.setOnAsyncResponse(new AsyncResponse() {
                //通过自定义的接口回调获取AsyncTask中onPostExecute返回的结果变量
                @Override
                public void onDataReceivedSuccess(List<Bitmap> listData, List<String> getrelurl) {
                    Log.d("TAG", "onDataReceivedSuccess");
                    getresurl = getrelurl;
                    if (listData.size() >= 4) {
                        environmentList = listData;
//                        environmentList = listData.subList(0, 4);//如此，我们便把onPostExecute中的变量赋给了成员变量environmentList
                    } else {
                        environmentList = listData;
                    }
                    iv_showaddpic.setVisibility(View.GONE);
                    grdadapt = new GridViewAdapter2(AddComm.this, environmentList, AddComm.this);
                    gdv_addpic.setAdapter(grdadapt);
                }

                @Override
                public void onDataReceivedFailed() {
//                    ToastUtil.show(gridview.this, "data received failed!");
                    Toast.makeText(AddComm.this, "data received failed!", Toast.LENGTH_SHORT).show();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void clickListener(View v) {
        // 点击之后的操作在这里写
//        Toast.makeText(
//                AddComm.this,
//                "listview的内部的按钮被点击了！，位置是-->" + (Integer) v.getTag() + ",内容是-->"
//                        + environmentList.get((Integer) v.getTag()),
//                Toast.LENGTH_SHORT).show();
        environmentList.remove(Integer.parseInt(v.getTag().toString()));
        grdadapt.notifyDataSetChanged();
        if (environmentList.size() == 0) {
            gdv_addpic.setVisibility(View.GONE);
            iv_showaddpic.setVisibility(View.VISIBLE);
        }
//        System.out.println("111111111111111");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == SELECT_IMAGE_CODE) {// selected image
            if (data != null && data.getExtras() != null) {
                @SuppressWarnings("unchecked")
                List<PhotoModel> photos = (List<PhotoModel>) data.getExtras().getSerializable("photos");
                ArrayList<String> infoList = new ArrayList<String>();
                if (photos == null || photos.isEmpty()) {
                    Toast.makeText(this, "没有选择图片", Toast.LENGTH_SHORT).show();
                } else {
//                    Intent intent = new Intent(this, YourOwnLogic.class);
//                    Bundle b = new Bundle();
//                    b.putSerializable("album_pojo", albumPojo);
//                    b.putSerializable("photos", (Serializable) photos);
//                    intent.putExtras(b);
//                    startActivity(intent);
//                    finish();
                    for (PhotoModel pm : photos) {
                        infoList.add(pm.getOriginalPath());
                    }
//                    gdv_addpic.toShow(this,infoList);
                    sendRequestForListData(infoList);
                }
            }
        }
    }
}