package com.photoselector.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.ImageColumns;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;

import com.photoselector.ui.PhotoSelectorActivity;

/**
 * ͨ�ù�����
 * 
 * @author chenww
 * 
 */
public class CommonUtils {

	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";

	/**
	 * ����activity
	 */
	public static void launchActivity(Context context, Class<?> activity) {
		Intent intent = new Intent(context, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivity(intent);
	}

	/**
	 * ����activity(�����)
	 */
	public static void launchActivity(Context context, Class<?> activity,
			Bundle bundle) {
		Intent intent = new Intent(context, activity);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivity(intent);
	}

	/**
	 * ����activity(�����)
	 */
	public static void launchActivity(Context context, Class<?> activity,
			String key, int value) {
		Bundle bundle = new Bundle();
		bundle.putInt(key, value);
		launchActivity(context, activity, bundle);
	}

	public static void launchActivity(Context context, Class<?> activity,
			String key, String value) {
		Bundle bundle = new Bundle();
		bundle.putString(key, value);
		launchActivity(context, activity, bundle);
	}

	public static void launchActivityForResult(Activity context,
			Class<?> activity, int requestCode) {
		Intent intent = new Intent(context, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivityForResult(intent, requestCode);
	}

	public static void launchActivityForResult(Activity context,
			Class<?> activity, int requestCode, int maxImage) {
		Intent intent = new Intent(context, activity);
		intent.putExtra(PhotoSelectorActivity.KEY_MAX, maxImage);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivityForResult(intent, requestCode);
	}

	public static void launchActivityForResult(Activity activity,
			Intent intent, int requestCode) {
		activity.startActivityForResult(intent, requestCode);
	}

	/** ����һ������ */
	public static void launchService(Context context, Class<?> service) {
		Intent intent = new Intent(context, service);
		context.startService(intent);
	}

	public static void stopService(Context context, Class<?> service) {
		Intent intent = new Intent(context, service);
		context.stopService(intent);
	}

	/**
	 * �ж��ַ��Ƿ�Ϊ��
	 * 
	 * @param text
	 * @return true null false !null
	 */
	public static boolean isNull(CharSequence text) {
		if (text == null || "".equals(text.toString().trim())
				|| "null".equals(text))
			return true;
		return false;
	}

	/** ��ȡ��Ļ��� */
	public static int getWidthPixels(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/** ��ȡ��Ļ�߶� */
	public static int getHeightPixels(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/** ͨ��Uri��ȡͼƬ·�� */
	public static String query(Context context, Uri uri) {
		Cursor cursor = context.getContentResolver().query(uri,
				new String[] { ImageColumns.DATA }, null, null, null);
		cursor.moveToNext();
		return cursor.getString(cursor.getColumnIndex(ImageColumns.DATA));
	}

	public static String getImagePath(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(inContext.getContentResolver(),
				inImage, DateFormat.format("yyyy-MM-dd-hh-mm-ss", new Date())
						.toString(), null);
		return path;
	}

	public static String getRealPathFromURI(Context context, Uri uri) {
		Cursor cursor = context.getContentResolver().query(uri, null, null,
				null, null);
		cursor.moveToFirst();
		int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
		return cursor.getString(idx);
	}

	public static Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(inContext.getContentResolver(),
				inImage, DateFormat.format("yyyy-MM-dd-hh-mm-ss", new Date())
						.toString(), null);
		return Uri.parse(path);
	}

	/**
	 * TODO Leon
	 */
	public static Uri getImageUri_New() {
		Uri mImageUri = null;
		try {
			File photo = createImageFile();
			mImageUri = Uri.fromFile(photo);
		} catch (Exception e) {
			Log.v("PhotoSelector", "Can't create file to take picture!");
		}
		return mImageUri;
	}

	public static File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_ijia_";
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			if (storageDir != null) {
				if (!storageDir.mkdirs()) {
					if (!storageDir.exists()) {
						Log.d("CameraSample", "failed to create directory");
						return null;
					}
				}
			}
		} else {
			Log.v("CommonUtils", "External storage is not mounted READ/WRITE.");
		}

		File image = File.createTempFile(imageFileName, // prefix
				JPEG_FILE_SUFFIX, // suffix
				storageDir // directory
				);

		// Save a file: path for use with ACTION_VIEW intents
		// mCurrentPhotoPath = "file:" + image.getAbsolutePath();
		return image;
	}
}
