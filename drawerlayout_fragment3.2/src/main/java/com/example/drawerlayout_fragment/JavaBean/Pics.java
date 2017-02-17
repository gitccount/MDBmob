package com.example.drawerlayout_fragment.JavaBean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/10/25.
 */

public class Pics extends BmobObject {
    private BmobFile icon;
    private List<BmobFile> listbof;

    public List<BmobFile> getListbof() {
        return listbof;
    }

    public void setListbof(List<BmobFile> listbof) {
        this.listbof = listbof;
    }


    public BmobFile getIcon() {
        return icon;
    }

    public void setIcon(BmobFile icon) {
        this.icon = icon;
    }
}
