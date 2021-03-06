package com.example.drawerlayout_fragment.config;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */

public class ExitApplication extends Application {
    private List<Activity> activityList = new LinkedList<Activity>();
    private static ExitApplication instance;

    private ExitApplication() {

    }

    // 单例模式中获取唯一的ExitApplication 实例
    public static ExitApplication getInstance() {
        if (null == instance) {
            instance = new ExitApplication();
        }
        return instance;
    }

    // 添加Activity 到容器中
    public void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {

            activityList.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    // 遍历所有Activity 并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}