package com.example.drawerlayout_fragment.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.drawerlayout_fragment.Custom.BitmapCompress;
import com.example.drawerlayout_fragment.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */

public class GridViewAdapter2 extends BaseAdapter implements View.OnClickListener {
    private List<Bitmap> nameList;
    private Context mContext;
    private LayoutInflater mInflater;
    private ImageView iv_showadd, iv_deladd;
    private MyClickListener mListener;

    //自定义接口，用于回调按钮点击事件到Activity
    public interface MyClickListener {
        public void clickListener(View v);
    }

    //响应按钮点击事件,调用子定义接口，并传入View
    @Override
    public void onClick(View v) {
        mListener.clickListener(v);
    }

    public GridViewAdapter2(Context mContext, List<Bitmap> nameList1, MyClickListener listener) {

        this.mContext = mContext;
        this.nameList = nameList1;
        mListener = listener;
//        if (nameList1.size() >= 4) {
//
//            this.nameList = nameList1.subList(0, 4);
//        } else {
//
//        }
    }

    @Override
    public int getCount() {

        return nameList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mInflater = LayoutInflater.from(mContext);
        convertView = mInflater.inflate(R.layout.addcommpic_adapter, null);
        iv_showadd = (ImageView) convertView.findViewById(R.id.iv_showadd);
//        iv_showadd.setClickable(false);
        iv_deladd = (ImageView) convertView.findViewById(R.id.iv_deladd);
        iv_deladd.setOnClickListener(this);
        iv_deladd.setTag(position);
//        st = nameList.get(position);
//        Bitmap bt = BitmapFactory.decodeFile(st).copy(Bitmap.Config.ARGB_8888, true);
//        bt.setHeight(200);
//        bt.setWidth(200);
        ;
//        Bitmap bt = BitmapFactory.decodeFile(nameList.get(position)).copy(Bitmap.Config.ARGB_8888, true);
//        Bitmap bt = BitmapFactory.decodeFile(nameList.get(position));
//        Bitmap bt =
//        compressImage(BitmapFactory.decodeFile(nameList.get(position)));
//        iv_show.setImageBitmap(bt);//不会变形
//        ImageAsyncTask asyncTask = new ImageAsyncTask(mContext,iv_show, nameList.get(position));
//        asyncTask.execute();

//        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(mContext.getContentResolver(), nameList.get(position), null, null));//获得路径
//        Bitmap bt = BitmapFactory.decodeFile(BitmapCompress.getRealPathFromURI(mContext, uri)).copy(Bitmap.Config.ARGB_8888, true);
//        bt.setWidth(200);
//        bt.setHeight(200);
//        iv_showadd.setMaxHeight(500);
//        iv_showadd.setMaxWidth(500);
        iv_showadd.setImageBitmap(nameList.get(position));
        return convertView;
    }

    //压缩文件
    public static Bitmap getImage(String imgPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;//直接设置它的压缩率，表示1/2
        Bitmap b = null;
        try {
            b = BitmapFactory.decodeFile(imgPath, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }


    /**
     * 多线程压缩图片的质量
     *
     * @param bitmap  内存中的图片
     * @param imgPath 图片的保存路径
     * @author JPH
     * @date 2014-12-5下午11:30:43
     */
/*
    public static void compressImageByQuality(final Bitmap bitmap, final String imgPath) {
        new Thread(new Runnable() {//开启多线程进行压缩处理
            @Override
            public void run() {
                // TODO Auto-generated method stub
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int options = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//质量压缩方法，把压缩后的数据存放到baos中 (100表示不压缩，0表示压缩到最小)
                while (baos.toByteArray().length / 1024 > 100) {//循环判断如果压缩后图片是否大于100kb,大于继续压缩
                    baos.reset();//重置baos即让下一次的写入覆盖之前的内容
                    options -= 10;//图片质量每次减少10
                    if (options < 0) options = 0;//如果图片质量小于10，则将图片的质量压缩到最小值
                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//将压缩后的图片保存到baos中
                    if (options == 0) break;//如果图片的质量已降到最低则，不再进行压缩
                }
                try {
                    FileOutputStream fos = new FileOutputStream(new File(imgPath));//将压缩后的图片保存的本地上指定路径中
                    fos.write(baos.toByteArray());
                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
   */
}
