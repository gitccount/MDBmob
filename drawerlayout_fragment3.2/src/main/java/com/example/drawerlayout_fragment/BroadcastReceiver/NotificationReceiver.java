package com.example.drawerlayout_fragment.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.example.drawerlayout_fragment.config.Utils;

/**
 * Created by Administrator on 2016/11/21.
 * 从这里跳转到相关页面，根据app的packge判断app是否存在，存在的话还能判断app是否在运行，
 * 然后在新的栈中打开app
 */

public class NotificationReceiver extends BroadcastReceiver {
    private String ToActivity = "com.example.drawerlayout_fragment";
    private PackageManager packageManager;
    private Intent startintent;
    @Override
    public void onReceive(Context context, Intent intent) {
        packageManager = context.getPackageManager();
        startintent = new Intent();
        startintent = packageManager
                .getLaunchIntentForPackage(ToActivity);
        if (startintent == null) {
            System.out.println("APP not found!");
            Toast.makeText(context, "APP not found!",
                    Toast.LENGTH_SHORT).show();

        } else {
            if (!Utils.isAppRunning(context, ToActivity)) {
                System.out.println("没有运行");

            }
            startintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            startintent.putExtra("fragid", "跳过来");
            startintent.putExtra("findtitle", "这次是多图");
            context.startActivity(startintent);
        }
    }
}
