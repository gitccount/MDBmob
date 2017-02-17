package com.example.drawerlayout_fragment.config;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */

public class Utils {
    // 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
    @SuppressLint("NewApi")
    public static String getPathByUri4kitkat(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {// ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {// DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {// MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore
            // (and
            // general)
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context
     *            The context.
     * @param uri
     *            The Uri to query.
     * @param selection
     *            (Optional) Filter used in the query.
     * @param selectionArgs
     *            (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    /**
     * 判断App是否运行
     */
    public static boolean isAppRunning(Context context, String str) {
        boolean isAppRunning = false;
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT < 21) {
            // 如果没有就用老版本
            List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager
                    .getRunningTasks(100);
            for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTaskInfos) {
                if (runningTaskInfo.topActivity.getPackageName().equals(str)
                        && runningTaskInfo.baseActivity.getPackageName()
                        .equals(str)) {

                    isAppRunning = true;
                    break;
                }
            }
        } else {
            List<ActivityManager.RunningAppProcessInfo> runningApp = manager
                    .getRunningAppProcesses();
            if (runningApp != null) {
                for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningApp) {
                    if (runningAppProcessInfo.processName.equals(str)) {
                        int status = runningAppProcessInfo.importance;
                        /*
                         * 可以根据importance的不同来判断前台或后台 RunningAppProcessInfo
						 * 里面的常量IMOPORTANCE就是上面所说的前台后台
						 * ，其实IMOPORTANCE是表示这个app进程的重要性
						 * ，因为系统回收时候，会根据IMOPORTANCE来回收进程的。具体可以去看文档。。 public
						 * static final int IMPORTANCE_BACKGROUND = 400//后台
						 * public static final int IMPORTANCE_EMPTY = 500//空进程
						 * public static final int IMPORTANCE_FOREGROUND =
						 * 100//在屏幕最前端、可获取到焦点 可理解为Activity生命周期的OnResume();
						 * public static final int IMPORTANCE_SERVICE =
						 * 300//在服务中 public static final int IMPORTANCE_VISIBLE
						 * = 200//在屏幕前端、获取不到焦点可理解为Activity生命周期的OnStart();
						 */
                        if (status == runningAppProcessInfo.IMPORTANCE_VISIBLE
                                || status == runningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                            Toast.makeText(context,
                                    "APP is foreground", Toast.LENGTH_SHORT)
                                    .show();
                            isAppRunning = true;
                            break;
                        } else if (status == runningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                            Toast.makeText(context,
                                    "APP is runningbackgound",
                                    Toast.LENGTH_SHORT).show();
                            isAppRunning = true;
                            break;
                        }
                    }

                }
            }
        }
        return isAppRunning;
    }
}
