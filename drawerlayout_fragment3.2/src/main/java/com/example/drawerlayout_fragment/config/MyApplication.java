package com.example.drawerlayout_fragment.config;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.drawerlayout_fragment.JavaBean.JkUser;
import com.example.drawerlayout_fragment.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/10/25.
 */

public class MyApplication extends Application {
    private static Context context;
    private static MyApplication myApplication = null;

    public static MyApplication getInstance() {
        return myApplication;
    }

    public JkUser getCurrentUser() {
        JkUser user = BmobUser.getCurrentUser(JkUser.class);
        if (user != null) {
            return user;
        }
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        // 创建默认的ImageLoader配置参数

        // Initialize ImageLoader with configuration.
        myApplication = this;
        initImageLoader();

    }

    private void initImageLoader() {
        // TODO Auto-generated method stub
        File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .memoryCache(new LruMemoryCache(5 * 1024 * 1024))
                .memoryCacheSize(10 * 1024 * 1024)
//                .discCache(new UnlimitedDiscCache(cacheDir))
                .discCache(new UnlimitedDiskCache(cacheDir))
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .memoryCacheExtraOptions(480, 800)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public DisplayImageOptions getOptions(int drawableId) {
        return new DisplayImageOptions.Builder().showImageOnLoading(drawableId)
                .showImageForEmptyUri(drawableId).showImageOnFail(drawableId)
                .resetViewBeforeLoading(true).cacheInMemory(true)
                .cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public DisplayImageOptions gettitlemap() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new CircleBitmapDisplayer(Color.WHITE, 5))
                .build();
    }
    public DisplayImageOptions getadaptermap() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
//                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageForEmptyUri(null)
                .showImageOnFail(R.drawable.ic_error)
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
//                .cacheInMemory(new UsingFreqLimitedCache(2000000))
//                .discCache(new UnlimitedDiscCache(cacheDir)) // 你可以传入自己的磁盘缓存
                .cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .memoryCacheExtraOptions(480, 800)
                .build();
    }

    public static Context getContext() {
        return context;
    }
}

