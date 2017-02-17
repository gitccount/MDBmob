package com.example.drawerlayout_fragment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drawerlayout_fragment.JavaBean.GameScore;
import com.example.drawerlayout_fragment.JavaBean.JkUser;
import com.example.drawerlayout_fragment.R;
import com.example.drawerlayout_fragment.config.CommonSharedPreferences;
import com.example.drawerlayout_fragment.config.Conf;
import com.example.drawerlayout_fragment.config.ExitApplication;
import com.example.drawerlayout_fragment.config.Util;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.LogInListener;

//import com.jkxy.bmobdemo2.constants.CommonSharedPreferences;
//import com.jkxy.bmobdemo2.constants.Conf;
//import com.jkxy.bmobdemo2.entity.GameScore;
//import com.jkxy.bmobdemo2.entity.JkUser;
//import com.jkxy.bmobdemo2.immples.ExitApplication;
//import com.jkxy.bmobdemo2.immples.MyApplication;
//import com.jkxy.bmobdemo2.immples.Util;

public class Login<BmobException> extends Activity implements
        OnClickListener {
    private EditText et_name, et_password;
    private Button btn_login, btn_reg, btn_change, btn_upload;
    // private SharedPreferences pref;

    // private SharedPreferences.Editor editor;
    private CheckBox rememberPass, auto_login;
    private GameScore gameScore;
    private TextView change_text;
    private ImageView show_image;
    private BmobFile bmobfile;
    private String account;
    private String password;
    private BmobUser bu2;
    private boolean islogin, isRemember;
    private String path = "/storage/emulated/0/11.png";
    private String data3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data3 = getIntent().getStringExtra("fragid");
        if((data3!=null)&&data3.equals("跳过来")){
//            Conf.intentthis="跳过来";
            Conf.setIntentThis("跳过来");
            Conf.setFindTitle(getIntent().getStringExtra("findtitle"));

            toast("启动存放"+data3);
        }

        try {
        } catch (Exception e) {

        }
        setContentView(R.layout.activity_login);
        ExitApplication.getInstance().addActivity(this);
        initView();




        CommonSharedPreferences.init(this);
        isRemember = CommonSharedPreferences.getRemenberPass();
        islogin = CommonSharedPreferences.getAutologin();

        if (isRemember) {

            // 将账号和密码都设置到文本框中
            String account = CommonSharedPreferences.getAccount();
            String password = CommonSharedPreferences.getPassword();
            et_name.setText(account);
            et_password.setText(password);
            rememberPass.setChecked(true);
            // if (islogin) {
            if (CommonSharedPreferences.getAutologin()) {
                auto_login.setChecked(true);
                // if (CommonSharedPreferences.getAutologin()) {
                // 允许用户使用应用
                System.out.println("免登录");
                logintonext();
                // } else {
                //
                // }
            } else {
                // 缓存用户对象为空时， 可打开用户注册界面…
                System.out.println("要登录");
                // toast("要登录");
            }
        }

        Bmob.initialize(this, Conf.APP_ID);
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation();
        // BmobInstallation.getCurrentInstallation(this).save();
        // 启动推送服务
        BmobPush.startWork(this);

    }

    private void initView() {
        et_name = (EditText) this.findViewById(R.id.et_name);
        et_password = (EditText) this.findViewById(R.id.et_password);
        btn_login = (Button) this.findViewById(R.id.btn_login);
        btn_reg = (Button) this.findViewById(R.id.btn_reg);
        rememberPass = (CheckBox) this.findViewById(R.id.remember_pass);
        auto_login = (CheckBox) this.findViewById(R.id.auto_login);
        btn_login.setOnClickListener(this);
        btn_reg.setOnClickListener(this);
        // BmobUser bmobUser = bu2.getCurrentUser();

    }

    @SuppressWarnings("static-access")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                final JkUser user = new JkUser();
                account = et_name.getText().toString().trim();
                password = et_password.getText().toString().trim();

                user.setUsername(account);
                user.setPassword(password);

                BmobUser.loginByAccount(account, password,
                        new LogInListener<JkUser>() {

                            public void done(JkUser arg0,
                                             cn.bmob.v3.exception.BmobException arg1) {
                                // toast("解析出来的信息:"+BmobUser.getCurrentUser().toString());
                                // System.out.println(
                                // "解析出来的信息:"+BmobUser.getCurrentUser().toString());
                                // TODO Auto-generated method stub
                                if (arg1 == null) {
                                    // Util.showToast(MainActivity.this,
                                    // arg0.getUsername() + arg0.getInfo()
                                    // + "-登录成功:" + arg0.getIcbc());

                                    if (rememberPass.isChecked()) { // 检查复选框是否被选中

                                        CommonSharedPreferences.setAccount(account);
                                        CommonSharedPreferences
                                                .setPassword(password);
                                        CommonSharedPreferences
                                                .setRemenberPass(true);
                                    } else {

                                        CommonSharedPreferences.setAccount("");
                                        CommonSharedPreferences.setPassword("");
                                        CommonSharedPreferences
                                                .setRemenberPass(false);
                                    }
                                    if (auto_login.isChecked()) { // 检查复选框是否被选中
                                        //
                                        CommonSharedPreferences.setAutologin(true);
                                        CommonSharedPreferences
                                                .setRemenberPass(true);

                                    } else {
                                        CommonSharedPreferences.setAutologin(false);
                                    }
                                    logintonext();
                                } else {
                                    Util.showToast(Login.this, "登陆失败" + arg1);
                                    // toast("登陆失败" + arg1);
                                    System.out.println("______________________登录失败"
                                            + arg1);
                                }
                            }

                        });

                break;
            case R.id.btn_reg:
                Intent intent = new Intent(this, RegActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    protected void toast(String string) {
        // TODO Auto-generated method stub
        Toast.makeText(Login.this, string, Toast.LENGTH_SHORT).show();
    }

    private void logintonext() {

        // BmobUser bmobUser = BmobUser.getCurrentUser();
//        Intent intent = new Intent(Login.this, Main_Page.class);
        Intent intent = new Intent(Login.this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // intent.putExtra("bmobU", bmobUser);
        // intent.putExtra("bmobU", bmobUser);
//        MyApplication.getContext().startActivity(intent);
        System.out.println("接到跳转参数启动:" + data3);
        intent.putExtra("fd", data3);
        startActivity(intent);
        data3=null;
    }

    // private void change_text(TextView tex, String str) {
    // // TODO Auto-generated method stub
    // tex.setText(str);
    // }
    protected void onDestroy() {
        super.onDestroy();
        ExitApplication.getInstance().removeActivity(this);
    }
}
