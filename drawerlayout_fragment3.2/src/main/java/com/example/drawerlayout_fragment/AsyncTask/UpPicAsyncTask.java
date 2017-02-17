package com.example.drawerlayout_fragment.AsyncTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.drawerlayout_fragment.Activity.AddComm;
import com.example.drawerlayout_fragment.Custom.AsyncResponse;
import com.example.drawerlayout_fragment.Custom.PicAsyncResPonse;
import com.example.drawerlayout_fragment.JavaBean.JkUser;
import com.example.drawerlayout_fragment.JavaBean.Pics;
import com.example.drawerlayout_fragment.JavaBean.Post;
import com.example.drawerlayout_fragment.config.Statc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2016/11/4.
 */

public class UpPicAsyncTask extends AsyncTask<List<String>, Integer, List<String>> {
    private List<String> geturl, puturl, respurl;
    private Context mContext;
    private BmobFile icon;
    private String title, contnt;
    private PicAsyncResPonse picresp;
    private List<String> returnurls;

    public void setOnAsyncResponse(PicAsyncResPonse asyncResponse) {
        this.picresp = asyncResponse;
    }

    public UpPicAsyncTask(Context mContext, List<String> st, String title, String content) {
        super();

        this.geturl = st;
        this.mContext = mContext;
        this.title = title;
        this.contnt = content;
    }

    @Override
    protected List<String> doInBackground(List<String>... params) {
        System.out.println("调用了上传：____" + geturl.size());
//        geturl = new ArrayList<String>();
        respurl = new ArrayList<String>();
        for (String s : geturl) {
            File file = new File(s);
            icon = new BmobFile(file);

            // final BmobFile icon = new BmobFile(new File(imgpath));
            icon.uploadblock(new UploadFileListener() {
                Pics per;

                public void done(BmobException arg0) {
                    // TODO Auto-generated method stub
                    if (arg0 == null) {
                        respurl.add(icon.getFileUrl());
                        LongToast("上传成功");// 上传完成后保存到Pics表
                        System.out.println("图片保存的url________" + icon.getFileUrl());
                        per = new Pics();
                        per.setIcon(icon);
                        per.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {

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
        System.out.println("上传图片-----" + respurl.size());
        return respurl;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<String> strings) {
//        super.onPostExecute(strings);
//        this.respurl = strings;
        System.out.println("上传图片++++++++++" + strings.size());
//        if (strings != null) {
//            Post post = new Post();
//
//            JkUser user = BmobUser.getCurrentUser(JkUser.class);
//            post.setUrls(strings);
//            post.setTitle(title);
//
//            post.setContent(contnt);
//            post.setAuthor(user);
//            post.save(new SaveListener<String>() {
//
//                @Override
//                public void done(String arg0, BmobException arg1) {
//                    // TODO Auto-generated method stub
//
//                    if (arg1 == null) {
//                        for (String s : respurl) {
//                            System.out.println("保存的网址:" + s);
//                        }
//
//                        LongToast("帖子保存成功" + arg0);
//                        System.out.println("帖子保存成功" + arg0);
//                    } else {
//                        LongToast("帖子保存失败" + arg1);
//                        System.out.println("帖子保存失败" + arg1);
//                    }
//                }
//
//            });
            if (strings != null) {
                picresp.onPicReceivedSuccess(strings);
            } else {
                picresp.onPicReceivedFailed();
            }
//        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    public void LongToast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }

}
