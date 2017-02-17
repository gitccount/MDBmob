package com.example.drawerlayout_fragment.AsyncTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.example.drawerlayout_fragment.Activity.up;
import com.example.drawerlayout_fragment.Adapter.GridViewAdapter;
import com.example.drawerlayout_fragment.Custom.AsyncResponse;
import com.example.drawerlayout_fragment.Custom.BitmapCompress;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */

public class ImageAsyncTask extends AsyncTask<List<String>, Integer, List<Bitmap>> {
    public AsyncResponse asyncResponse;
    private ImageView imaview;
    private List<String> str;
    private Context mContext;
    private List<Bitmap> listbitmap;
    private List<String> test;

    public void setOnAsyncResponse(AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    public ImageAsyncTask(Context mContext, ImageView imaview, List<String> str) {
        super();

        this.imaview = imaview;
        this.str = str;
        this.mContext = mContext;
    }

    @Override
    protected List<Bitmap> doInBackground(List<String>... params) {
        listbitmap = new ArrayList<Bitmap>();//不新建对象会报错
        for (String s : str) {
//            Bitmap bt = BitmapCompress.BitmapCompressToString(s);
            listbitmap.add(new BitmapCompress().BitmapCompressToString(s));
        }

//            System.out.println("线程中的图片地址:"+params[0]);
//            System.out.println("线程转换中的图片:"+Uri.parse(MediaStore.Images.Media.insertImage(mContext.getContentResolver(), bt, null, null)));
//            listbitmap.add(bt);
//            test.add("u123");
        return listbitmap;
    }

    //该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
    @Override
    protected void onPostExecute(List<Bitmap> result) {
//        imaview.setImageBitmap(result);
//        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(mContext.getContentResolver(), BitmapCompress.BitmapCompressToString(str), null,null));//获得路径
        test = new ArrayList<String>();
        for (Bitmap bp : result) {
            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(mContext.getContentResolver(), bp, null, null));//获得路径
            test.add(BitmapCompress.getRealPathFromURI(mContext, uri));
//            up.upload(BitmapCompress.getRealPathFromURI(mContext, uri));//路径转换
        }


        if (result != null) {
//            List<String> listData = new ArrayList<String>();
//            listData = parseJsonResponse(result);//解析msg的一个函数
            asyncResponse.onDataReceivedSuccess(result, test);//将结果传给回调接口中的函数
//            imaview.setAdapter(new GridViewAdapter(mContext, result));
        } else {
            asyncResponse.onDataReceivedFailed();
        }
    }

    protected void onProgressUpdate(Integer... progress) {
//        mProgressBar.setProgress(progress[0]);
    }

}
