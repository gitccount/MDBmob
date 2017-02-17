package com.example.drawerlayout_fragment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.drawerlayout_fragment.Custom.Immples;
import com.example.drawerlayout_fragment.JavaBean.CopyOfJkUser;
import com.example.drawerlayout_fragment.R;
import com.example.drawerlayout_fragment.config.ExitApplication;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/10/25.
 */

public class RegActivity extends Activity implements View.OnClickListener {
    private EditText et_name, et_password;
    private Button btn_login, btn_reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ExitApplication.getInstance().addActivity(this);
        initView();
    }

    private void initView() {
        et_name = (EditText) this.findViewById(R.id.et_name);
        et_password = (EditText) this.findViewById(R.id.et_password);
        btn_login = (Button) this.findViewById(R.id.btn_login);
        btn_reg = (Button) this.findViewById(R.id.btn_reg);
        btn_login.setOnClickListener(this);
        btn_reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                ExitApplication.getInstance().removeActivity(this);
                startActivity(new Intent(this, MainActivity.class));

                break;
            case R.id.btn_reg:
                CopyOfJkUser user = new CopyOfJkUser();
                user.setUsername(et_name.getText().toString().trim());
                user.setPassword(et_password.getText().toString().trim());
                // user.setInfo("霸王登录");
                // user.setEmail("123@456.com");
                user.setInfo("霸王登录");
                user.setIcbc("456");
                // user.setEmail("sendi@163.com");
                user.signUp(new SaveListener<CopyOfJkUser>() {

                    @Override
                    public void done(CopyOfJkUser arg0, BmobException arg1) {
                        // TODO Auto-generated method stub
                        if (arg1 == null) {
                            Immples.toast("注册成功:" + arg0.getUsername());
                        } else {
                            Immples.toast("注册失败" + arg1);
                        }
                    }
                });

                // BmobUser bu = new BmobUser();
                // bu.setUsername("sendi");
                // bu.setPassword("123456");
                // bu.setEmail("sendi@163.com");
                // // 注意：不能用save方法进行注册
                // bu.signUp(new SaveListener<BmobUser>() {
                // @Override
                // public void done(BmobUser s, BmobException e) {
                // if (e == null) {
                // Immples.toast("注册成功:" + s.getUsername());
                // } else {
                // Immples.toast("注册失败:" + e);
                // }
                // }
                // });
                break;
            default:
                break;
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        ExitApplication.getInstance().removeActivity(this);
    }
}

