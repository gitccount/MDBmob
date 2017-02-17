package com.example.drawerlayout_fragment.config;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/8.
 */

public class GetTime {

    private long days;

    public String howLongAgo(String timey) {
        String ti = "";
        String hours1 = "";
        String minutes1 = "";
        String dayS = "";
        String toTime="";


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
//			System.out.println(timey+"----------------------");
            Date d1 = df.parse(getNowTIme());
            Date d2 = df.parse(timey);
//			Date d2 = df.parse(cursor1.getString(cursor1.getColumnIndex("time")));
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别

            //计算出时间
            days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            //各项判断
            if(days >30){
                toTime = df.format(d2);
            }
            if (days >= 1) {
                dayS = days + "天";
            }
            if (hours >= 1) {
                hours1 = hours + "小时";
            }
            if (minutes >= 1) {
                minutes1 = minutes + "分钟前";
            }

            if (days == 0 && hours == 0 && minutes <= 1) {
                ti = "刚刚";
            } else {
                ti = dayS + hours1 + minutes1;
            }


        } catch (Exception e) {
        }
//		System.out.println(ti);
//        return ti;
        return days>30?toTime:ti;

    }

    // 获取时间
    public String getNowTIme() {
        // 指定时间格式
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = new Date();
        // 按照指定格式获取
        String str1 = format1.format(date1);
        return str1;
    }
}
