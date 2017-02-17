package com.example.drawerlayout_fragment.Activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.drawerlayout_fragment.JavaBean.Person;
import com.example.drawerlayout_fragment.R;
import com.example.drawerlayout_fragment.config.ExitApplication;
import com.example.drawerlayout_fragment.config.MyApplication;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2016/10/25.
 */

public class up extends Activity implements View.OnClickListener {
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    private ImageView mImageHeader;
    private String path = "/storage/emulated/0/11.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up);
        ExitApplication.getInstance().addActivity(this);
//		Bmob.initialize(this, Conf.APP_ID);
        // 初始化Bmob
        setupViews();
        upload(path);//打开页面就自动上传
        // uploadfile(path);
    }

    private void setupViews() {
        mImageHeader = (ImageView) findViewById(R.id.image_header);
        final Button selectBtn1 = (Button) findViewById(R.id.btn_selectimage);
        final Button selectBtn2 = (Button) findViewById(R.id.btn_takephoto);
        selectBtn1.setOnClickListener(this);
        selectBtn2.setOnClickListener(this);
        // Bitmap bmp = BitmapFactory.decodeFile(path);
        // mImageHeader.setImageBitmap(bmp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_selectimage:
                // Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                // Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                // Intent galleryIntent = new
                // Intent(Intent.ACTION_PICK);//android4.4以后的方法

                // galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                // galleryIntent.setType("image/*");
                // galleryIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, IMAGE_REQUEST_CODE);

                // 图片
                // startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);
                break;
            case R.id.btn_takephoto:
                if (isSdcardExisting()) {
                    Intent cameraIntent = new Intent(
                            "android.media.action.IMAGE_CAPTURE");
                    // 拍照
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
                    cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                } else {
                    Toast.makeText(v.getContext(), "请插入sd卡", Toast.LENGTH_LONG)
                            .show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    Uri originalUri = data.getData();// 获取图片uri

                    // 选择图片成功，就显示图片
                    Bitmap bmpp = BitmapFactory.decodeFile(getRealFilePath(this,
                            originalUri));
                    mImageHeader.setImageBitmap(bmpp);
                    upload(getRealFilePath(this, originalUri));
                    // showToast(img_url);
                    break;
                case CAMERA_REQUEST_CODE:
                    if (isSdcardExisting()) {
                        resizeImage(getImageUri());
                        String[] imgs = { MediaStore.Images.Media.DATA };
                        // 将图片URI转换成存储路径
                        @SuppressWarnings("deprecation")
//					Cursor cursor1 = this.managedQuery(getImageUri(), imgs,
                                Cursor cursor1 = this.getContentResolver().query(getImageUri(), imgs,
                                null, null, null);
                        int index1 = cursor1
                                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor1.moveToFirst();
                        String img_url1 = cursor1.getString(index1);

                        upload(img_url1);
                        // showToast(img_url1);
                    } else {
                        Toast.makeText(up.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG)
                                .show();
                    }
                    break;
                case RESIZE_REQUEST_CODE:
                    if (data != null) {
                        showResizeImage(data);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isSdcardExisting() {// 判断SD卡是否存在
        final String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public void resizeImage(Uri uri) {// 重塑图片大小
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");// 可以裁剪
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 350);
        intent.putExtra("outputY", 350);
        intent.putExtra("return-data", true);

        intent.putExtra("scale", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection

        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }

    @SuppressWarnings("deprecation")
    private void showResizeImage(Intent data) {// 显示图片
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Uri originalUri = data.getData();// 获取图片uri

            Drawable drawable = new BitmapDrawable(photo);
            mImageHeader.setImageDrawable(drawable);
        }
    }

    private Uri getImageUri() {// 获取路径
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                IMAGE_FILE_NAME));
    }

    /**
     *
     将图片上传
     *
     * @param imgpath
     */
    public static  void upload(String imgpath) {
        System.out.println("_____传过来的路径:" + imgpath);
        File file = new File(imgpath);
        final BmobFile icon = new BmobFile(file);
        // final BmobFile icon = new BmobFile(new File(imgpath));
        icon.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException arg0) {
                // TODO Auto-generated method stub
                if (arg0 == null) {
                    LongToast("上传成功");// 上传完成后保存到Person表
                    Person per = new Person();
                    per.setIcon(icon);
                    per.save(new SaveListener<String>() {

                        @Override
                        public void done(String arg0, BmobException arg1) {
                            // TODO Auto-generated method stub
                            if (arg1 == null) {
                                LongToast("保存的id" + arg0);
                            } else {
                                LongToast("保存失败" + arg1);
                                System.out.println("保存失败" + arg1);
                            }
                        }
                    });
                    // per.update();

                } else {
                    LongToast("上传失败" + arg0);
                    System.out.println("保存失败" + arg0);
                }
            }

        });
    }

    // icon.upload(this, new UploadFileListener() {
    // public void onSuccess() {
    // // TODO Auto-generated method stub
    //
    // }
    //
    //
    // public void onFailure(int arg0, String arg1) {
    // showToast("图片上传失败：" + arg1);
    // }
    //
    // @Override
    // public void done(BmobException arg0) {
    // // TODO Auto-generated method stub
    // // 保存头像文件到自己的Person表中
    // Person person = new Person();
    // person.setIcon(icon);
    // // person.save(up.this);
    // showToast("图片上传成功");
    // }
    // });
    // }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    /**
     *
     时间长一点的toast
     *
     * @param String
     */
    static void LongToast(String msg) {
        Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
    protected void onDestroy() {
        super.onDestroy();
        ExitApplication.getInstance().removeActivity(this);
    }
}
