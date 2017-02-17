package com.example.drawerlayout_fragment.Custom;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


/**
 * Created by Administrator on 2016/10/31.
 */

public class BitmapCompress {
    //压缩文件
    public static Bitmap BitmapCompressToString(String srcPath) {
        Bitmap bmp = compressImageFromFile(srcPath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 150) {
            options -= 10;// 每次都减少10
            // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
//            Log.d("ROTK", "baos"+baos.toByteArray().length);
            // 这里压缩options%，把压缩后的数据存放到baos中
        }
//        byte[] b = baos.toByteArray();
//        return b;
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//        return BitmapFactory.decodeStream( new ByteArrayInputStream(baos.toByteArray()), null, null);
        System.out.println("这个文件大小:" + getBitmapBytes(bitmap));
        return bitmap;
    }

    //设置宽高
    public static Bitmap compressImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;// 只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//
        float ww = 480f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;// 该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);


        return bitmap;
    }

    //压缩图图片
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100)

        {//循环判断如果压缩后图片是否大于100kb,大于继续压缩

            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }

        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;

    }

    //获取文件大小
    public static int getBitmapBytes(Bitmap bitmap) {
        int result;
        if (Build.VERSION.SDK_INT >= 12) {
            result = bitmap.getByteCount();
        } else {
            result = bitmap.getRowBytes() * bitmap.getHeight();
        }

        if (result < 0) {
            throw new IllegalStateException("Negative size: " + bitmap);
        } else {
            return result;
        }
    }

    //获得路径
    public static String getRealPathFromURI(final Context mcontext, final Uri contentUri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = mcontext.getContentResolver().query(contentUri, proj,
                null, null, null);
        int column_index
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    //bitmap转换为路径
    public static String bitmapToPath(Context context, Bitmap bmp) {

        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bmp, null, null));//获得路径
        String st = getRealPathFromURI(context, uri);
        return st;
    }
}
