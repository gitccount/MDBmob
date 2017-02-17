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
import com.example.drawerlayout_fragment.Custom.ImageHelper;
import com.example.drawerlayout_fragment.R;
import com.example.drawerlayout_fragment.config.MyApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

/**
 * Created by Administrator on 2016/11/5.
 */

public class CursorAdPic extends BaseAdapter {
    private DisplayImageOptions options, options2;
    private Context context;
    private List<String> piclist;

    public CursorAdPic(Context context, List<String> piclist) {
        this.context = context;
        this.piclist = piclist;
    }

    @Override
    public int getCount() {
        return piclist.size();
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
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder = null;
        if (convertView != null) {
            // view = convertView;
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.inner, parent, false);
            viewHolder.imgiv = (ImageView) convertView
                    .findViewById(R.id.curlist_img);
//            viewHolder.imgiv.setMaxWidth(350);
//            viewHolder.imgiv.setMaxHeight(350);
            convertView.setTag(viewHolder);
        }
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
//                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(20))
                .build();

        options2 = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
//                .showImageForEmptyUri(null)
                .showImageOnFail(R.drawable.ic_error)
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)

                .cacheOnDisc(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .memoryCacheExtraOptions(480, 800)
                .build();


        ImageLoader.getInstance().displayImage(
                piclist.get(position),
                viewHolder.imgiv,
                MyApplication.getInstance().getadaptermap(), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), loadedImage, null, null));//获得路径
//                        BitmapCompress.getRealPathFromURI(context, uri);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });

//                options, null);
//        new ImageHelper(context).display(viewHolder.imgiv, piclist.get(position));
        return convertView;
    }

    private static class ViewHolder {
        ImageView imgiv;

    }
}
