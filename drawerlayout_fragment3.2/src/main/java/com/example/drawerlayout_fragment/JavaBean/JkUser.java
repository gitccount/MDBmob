package com.example.drawerlayout_fragment.JavaBean;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/10/25.
 */

public class JkUser extends BmobUser {
    private String info;
    private String icbc;
    private Integer age;// 为用户表新增一个age字段，注意其必须为`Integer`类型，而不是int
    // private String username;
    // private String password;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getIcbc() {
        return icbc;
    }

    public void setIcbc(String icbc) {
        this.icbc = icbc;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    // public void signUp(Activity regActivity, SaveListener saveListener) {
    // // TODO Auto-generated method stub
    //
    // }
}
