package com.example.drawerlayout_fragment.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.drawerlayout_fragment.Adapter.GridViewAdapter;
import com.example.drawerlayout_fragment.Custom.AsyncResponse;
import com.example.drawerlayout_fragment.AsyncTask.ImageAsyncTask;
import com.example.drawerlayout_fragment.R;

import java.util.ArrayList;
import java.util.List;

public class gridview extends AppCompatActivity {
    private List<Bitmap> environmentList = null;//listview上显示的数据源
    private GridView gvPic;
    private ArrayList<String> infoList;
    private ImageView iv_show;

    public static void toShow(Context cont, ArrayList<String> data1) {
        Intent i = new Intent(cont, gridview.class);
        i.putExtra("param1", data1);
        cont.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);
        intView();

        sendRequestForListData();
    }


    private void intView() {
        iv_show = (ImageView) findViewById(R.id.iv_show);
        gvPic = (GridView) findViewById(R.id.gv_pic);
        infoList = new ArrayList<String>();

        infoList = getIntent().getStringArrayListExtra("param1");
//        gvPic.setAdapter(new GridViewAdapter(this, infoList));

    }

    private void sendRequestForListData() {
        try {
            ImageAsyncTask socketConn = new ImageAsyncTask(gridview.this, iv_show, infoList);
            socketConn.execute();
            socketConn.setOnAsyncResponse(new AsyncResponse() {
                //通过自定义的接口回调获取AsyncTask中onPostExecute返回的结果变量
                @Override
                public void onDataReceivedSuccess(List<Bitmap> listData, List<String> st) {
                    Log.d("TAG", "onDataReceivedSuccess");
                    environmentList = listData;//如此，我们便把onPostExecute中的变量赋给了成员变量environmentList
                    gvPic.setAdapter(new GridViewAdapter(gridview.this, listData));
                }

                @Override
                public void onDataReceivedFailed() {
//                    ToastUtil.show(gridview.this, "data received failed!");
                    Toast.makeText(gridview.this, "data received failed!", Toast.LENGTH_SHORT).show();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
