package com.example.drawerlayout_fragment.Custom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;


import com.example.drawerlayout_fragment.Activity.Main_Page;
import com.example.drawerlayout_fragment.JavaBean.GameScore;
import com.example.drawerlayout_fragment.JavaBean.JkUser;
import com.example.drawerlayout_fragment.config.MyApplication;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2016/10/25.
 */

public class Immples {
    private static SharedPreferences pref = PreferenceManager
            .getDefaultSharedPreferences(MyApplication.getContext());

    private static SharedPreferences.Editor editor;
    boolean isRemember = pref.getBoolean("remember_password", false);

    // 登录代码
    public static void immples_login(JkUser user, final Boolean ischeck,
                                     final String account, final String password) {
        BmobUser.loginByAccount(account, password, new LogInListener<JkUser>() {

            @Override
            public void done(JkUser arg0, BmobException arg1) {
                // TODO Auto-generated method stub
                if (arg1 == null) {
                    toast(arg0.getUsername() + arg0.getInfo() + "-登录成功:"
                            + arg0.getIcbc());
                    editor = pref.edit();

                    if (ischeck) { // 检查复选框是否被选中

                        editor.putBoolean("remember_password", true);
                        editor.putString("account", account);
                        editor.putString("password", password);

                    } else {

                        editor.clear();

                    }

                    editor.commit();
                    loginin(MyApplication.getContext(),Main_Page.class);
//					Intent intent = new Intent(MyApplication.getContext(),
//							Main_Page.class);
//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//					MyApplication.getContext().startActivity(intent);
                } else {
                    toast("登陆失败" + arg1);
                    System.out.println("______________________登录失败" + arg1);
                }
            }
        });
    }

    // 一个查询
    public static void immples_query(final BackUpCallBackText back) {
        BmobQuery<GameScore> query = new BmobQuery<GameScore>();
        query.addQueryKeys("message");
        query.findObjects(new FindListener<GameScore>() {

            @Override
            public void done(List<GameScore> arg0, BmobException arg1) {
                // TODO Auto-generated method stub
                if (arg1 == null) {
                    // TODO Auto-generated method stub
                    String c = null;
                    for (int i = 0; i < arg0.size(); i++) {
                        System.out.println("查询出来的数据是_____________:"
                                + arg0.get(i));
                        GameScore a = arg0.get(i);

                        c += a.getCreatedAt() + "/";
                        c += a.getObjectId() + "/";
                        c += a.getPlayerName() + "/";
                        c += a.getMessage() + "/";
                        c += a.getTableName() + "/";
                        c += a.getUpdatedAt() + "/";
                        c += a.getACL() + "/";
                        c += a.getCheatMode() + "/";
                        c += a.getClass() + "/";
                        c += a.getScore() + "/";
                        // }
                        System.out.println("出来的数据是_____________:" + c);
                        // change_text(c);
                        back.settext(c);
                    }
                    toast("查询成功:");
                } else {
                    toast("更新失败" + arg1);
                }
            }
        });

    }

    public static void immples_createData() {
        GameScore gameScore = new GameScore();
        gameScore.setPlayerName("王五");
        gameScore.setMessage("老王老婆偷汉子");
        gameScore.setScore(87);
        gameScore.setCheatMode(true);
        gameScore.save(new SaveListener<String>() {

            @Override
            public void done(String arg0,
                             cn.bmob.v3.exception.BmobException arg1) {
                // TODO Auto-generated method stub
                if (arg1 == null) {
                    toast("创建成功");
                } else {
                    toast("创建失败" + arg1);
                }

            }
        });
    }

    public static void immples_upData() {
        JkUser p2 = new JkUser();
        p2.setIcbc("蛋蛋你敢不");
        p2.update("1b0cb1cac6", new UpdateListener() {

            @Override
            public void done(BmobException arg0) {
                // TODO Auto-generated method stub
                if (arg0 == null) {
                    toast("更新成功");
                } else {
                    toast("更新失败" + arg0);
                }
            }
        });
    }

    public static void toast(String string) {
        // TODO Auto-generated method stub
        Toast.makeText(MyApplication.getContext(), string, Toast.LENGTH_LONG)
                .show();
    }

    public static void loginin(Context context, Class<Main_Page> class2) {
        Intent intent = new Intent(context, class2);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        MyApplication.getContext().startActivity(intent);
    }

    public interface BackUpCallBackText {
        public void settext(String str);
    }

}
