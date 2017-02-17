package com.example.drawerlayout_fragment.AsyncTask;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.example.drawerlayout_fragment.Custom.PicAsyncResPonse;
import com.example.drawerlayout_fragment.JavaBean.JkUser;
import com.example.drawerlayout_fragment.JavaBean.Person;
import com.example.drawerlayout_fragment.JavaBean.Pics;
import com.example.drawerlayout_fragment.JavaBean.Post;
import com.example.drawerlayout_fragment.JavaBean.Post2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2016/11/4.
 */

public class UploadPostandPic {
    private Context mContext;
    private List<String> geturl, puturl = null, respurl, st;
    private BmobFile icon;
    private String title, content,time;
    private PicAsyncResPonse resp;
    private int i;
    private Pics per;
    private File file;
    private String[] filePaths;

    public void setResposur(PicAsyncResPonse resp) {
        this.resp = resp;
    }

//    public void uppic(Context mContext, List<String> st, String title, String content,String time) {
    public void uppic(Context mContext, List<String> st, String title, String content) {
        respurl = new ArrayList<String>();
        this.mContext = mContext;
        this.puturl = st;
        this.title = title;
        this.content = content;
//        this.time = time;
//        try {
//            if (!puturl.isEmpty()) {
//
//                uploadmany(puturl);
//            }
//        } catch (Exception e) {
//
//        }
        if (puturl == null) {
            uppost(null);

            //1秒倒计时
            new CountDownTimer(1000, 1000) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {

                    resp.onNoPic(true);
                }
            }.start();
        } else {
            uploadmany(puturl);
        }

      /*
        for (String s : puturl) {
            per = new Pics();
            System.out.println("图片保存执行________" + s);
            icon = new BmobFile(new File(s));

            // final BmobFile icon = new BmobFile(new File(imgpath));
            icon.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException arg0) {
                    // TODO Auto-generated method stub
                    if (arg0 == null) {
                        respurl.add(icon.getFileUrl());
                        LongToast("上传成功");// 上传完成后保存到Pics表
                        System.out.println("图片保存的url________" + icon.getFileUrl());
//                        resp.onPicReceivedSuccess(respurl);
                        per.setIcon(icon);
                        uppost(respurl);
                        per.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
//                                    System.out.println("这两个长度为" + respurl.size() + "和" + puturl.size());
//                                    i++;
//                                    if (puturl.size() == i) {
//                                    }
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
        }
*/
    }

    public void uppost(List<String> str) {
        Post2 post = new Post2();

        JkUser user = BmobUser.getCurrentUser(JkUser.class);
        post.setUrls(str);
        post.setTitle(title);
//        post.setTime(time);
        post.setContent(content);
        post.setAuthor(user);
        post.save(new SaveListener<String>() {

            @Override
            public void done(String arg0, BmobException arg1) {
                // TODO Auto-generated method stub

                if (arg1 == null) {
                    resp.onPicReceivedSuccess(respurl);
                    for (String s : respurl) {
                        System.out.println("保存的网址:" + s);
                    }

                    LongToast("帖子保存成功" + arg0);
                    System.out.println("帖子保存成功" + arg0);
                } else {
                    resp.onPicReceivedFailed();
                    LongToast("帖子保存失败" + arg1);
                    System.out.println("帖子保存失败" + arg1);
                }
            }

        });
    }

    public void LongToast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }

    public void uploadmany(List<String> list) {
        //详细示例可查看BmobExample工程中BmobFileActivity类
        String filePath_mp3 = "/mnt/sdcard/testbmob/test1.png";
        String filePath_lrc = "/mnt/sdcard/testbmob/test2.png";
//        final String[] filePaths = new String[2];
        filePaths = new String[list.size()];
        list.toArray(filePaths);
//        filePaths[0] = filePath_mp3;
//        filePaths[1] = filePath_lrc;
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {

            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                //2、urls-上传文件的完整url地址
                if (urls.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成
                    //do something
                    respurl = urls;
                    uppost(urls);
                    for (BmobFile bf : files) {
                        Pics per = new Pics();

                        per.setIcon(bf);
                        per.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                            }
                        });
                    }
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                LongToast("错误码" + statuscode + ",错误描述：" + errormsg);
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
                resp.onReReceivedload(totalPercent);
            }
        });
    }
}
