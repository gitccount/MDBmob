package com.example.drawerlayout_fragment.JavaBean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/10/25.
 */

public class CopyOfJkUser extends BmobUser {
//public class CopyOfJkUser extends BmobObject {

    private String info;
    private String icbc;
    private BmobFile head;// 头像

    public BmobFile getHead() {
        return head;
    }

    public void setHead(BmobFile head) {
        this.head = head;
    }

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


    // public void signUp(SaveListener<CopyOfJkUser> saveListener) {
    // // TODO Auto-generated method stub
    //
    // }

}