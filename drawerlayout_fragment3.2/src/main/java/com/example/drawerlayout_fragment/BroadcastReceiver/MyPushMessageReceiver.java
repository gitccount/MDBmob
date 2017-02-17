package com.example.drawerlayout_fragment.BroadcastReceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drawerlayout_fragment.R;
import com.example.drawerlayout_fragment.config.Conf;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.push.PushConstants;

/**
 * Created by Administrator on 2016/11/21.
 * 接收广播有时候会没有反应，需要关闭后重新开启应用
 * 接收广播，在通知栏弹出Notification,PendingIntent点击后跳转到NotificationReceiver，
 * 再从那边跳转到相关页面
 *
 */

public class MyPushMessageReceiver extends BroadcastReceiver {
    private String ST = "cc",
    //            TOActivity = "com.example.administrator.bmobpush";
    TOActivity = "com.example.drawerlayout_fragment.Activity";
    private TextView btnSet;
    private LinearLayout layout;
    private String[] as;
    private NotificationManager manager;
    private Boolean HAVESP = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // TODO Auto-generated method stub
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            ST = intent.getStringExtra("msg");
            Log.d("bmob", "客户端收到推送内容：" + ST);
            Toast.makeText(context, ST, Toast.LENGTH_SHORT).show();
//            MainActivity.maketext(intent.getStringExtra("msg").toString());

            HAVESP = isincludesp(ST.toString());
            try {
                JSONObject dataJson = new JSONObject(ST);
                ST = dataJson.getString("alert");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (HAVESP) {
                // 根据#分割标题和内容，没有内容的话就把内容设为空，后面再判断
//                String s = ST;
                as = ST.split("#");
//                MainActivity.maketext(as[1]);
//                MainActivity.maketext(ST);


            } else {
                System.out.println("消息不规范");
            }

//新建一个Notification管理器;
//API level 11
  /*          PackageManager packageManager = context.getPackageManager();
            Intent startintent = new Intent();
            startintent = packageManager
                    .getLaunchIntentForPackage(TOActivity);
            if (startintent == null) {
                System.out.println("APP not found!");
                Toast.makeText(context, "APP not found!",
                        Toast.LENGTH_SHORT).show();
            } else {
                if (!isAppRunning(context, TOActivity)) {
                }
                startintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

                startintent.putExtra("fragid", "跳过来");
            }
*/
            Notification.Builder builder = new Notification.Builder(context);//新建Notification.Builder对象
//            PendingIntent pdintent = PendingIntent.getActivity(context, 0, new Intent(context, NotificationReceiver.class), 0);
            PendingIntent pdintent = PendingIntent.getBroadcast(context, 0, new Intent(context, NotificationReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
//            PendingIntent pdintent = PendingIntent.getActivity(context, 0, startintent, 0);
//PendingIntent点击通知后所跳转的页面,有#就用分割后的字符串
            builder.setContentTitle(HAVESP ? as[0] : ST);
            builder.setContentText(HAVESP ? as[1] : " ");
            Conf.setToWhichPage(HAVESP ? Integer.parseInt(as[2]) :0);
            Conf.setFindTitle(HAVESP ? as[3] :"");
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentIntent(pdintent);//执行intent
            Notification notification = builder.getNotification();//将builder对象转换为普通的notification
            notification.flags |= Notification.FLAG_AUTO_CANCEL;//点击通知后通知消失
            manager.notify(1, notification);//运行notification

        }

    }

    //判断是否带#
    private boolean isincludesp(String str) {
        boolean in = false;
        if (str.indexOf("#") != -1) {
            in = true;
        }

        return in;
    }

}
