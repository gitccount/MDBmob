package com.example.drawerlayout_fragment.config;

import android.content.Context;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Administrator on 2016/10/25.
 */

public class Util {
    private static Toast toast;

    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    /*
    从缓存中获取数据的方法为get(String imageUri)该类是重写BaseDiscDache方法，
    该方法从loadingDates中获取imageUri所代表的图片的最新更新时间loadingDate，
    然后拿当前时间和loadingDate做差，
    如果差值大于maxFileAge也就是说查过了加载的最大时间，
    就删除该imageUri所代表的file,并从loadingDates中的数据，
    当然如果map中没有imageUri就不会涉及到超时的问题，
    此时就把image放入map中去，具体的实现如下

*/
}
