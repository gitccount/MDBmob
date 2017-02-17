package com.photoselector.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Media;

import com.photoselector.model.AlbumModel;
import com.photoselector.model.PhotoModel;

public class AlbumController {

	private ContentResolver resolver;
	String[] projection = new String[] { MediaStore.Images.ImageColumns._ID,
			MediaStore.Images.ImageColumns.DATA,
			MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
			MediaStore.Images.ImageColumns.SIZE,
			MediaStore.Images.ImageColumns.DATE_TAKEN,
			MediaStore.Images.ImageColumns.MIME_TYPE };

	public AlbumController(Context context) {
		resolver = context.getContentResolver();
	}

	public List<PhotoModel> getCurrent() {
		List<PhotoModel> photos = new ArrayList<PhotoModel>();
		Cursor cursor = null;
		try {
			cursor = resolver.query(Media.EXTERNAL_CONTENT_URI, projection,
					null, null, MediaStore.Images.ImageColumns.DATE_TAKEN + " ASC");
			if (cursor != null) {
				/*
				cursor.moveToFirst();
				while (cursor.moveToNext()) {
					if(cursor.getLong(cursor.getColumnIndex(ImageColumns.SIZE)) > 1024 *10) {
						PhotoModel photoModel = new PhotoModel();
						photoModel.setOriginalPath(cursor.getString(cursor
								.getColumnIndex(ImageColumns.DATA)));
						photos.add(photoModel);
					}
				}*/
				
				cursor.moveToLast();
				do {
					if (cursor.getLong(cursor.getColumnIndex(ImageColumns.SIZE)) > 1024 * 10) {
						PhotoModel photoModel = new PhotoModel();
						photoModel.setOriginalPath(cursor.getString(cursor
								.getColumnIndex(ImageColumns.DATA)));
						photos.add(photoModel);
					}
				} while (cursor.moveToPrevious());
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return photos;
	}

	/**  
	public List<PhotoModel> getCurrent() {
		Cursor cursor = resolver.query(Media.EXTERNAL_CONTENT_URI,
				new String[] { ImageColumns.DATA, ImageColumns.DATE_ADDED,
						ImageColumns.SIZE }, null, null,
				ImageColumns.DATE_ADDED + " DESC");

		if (cursor == null || !cursor.moveToNext())
			return new ArrayList<PhotoModel>();
		List<PhotoModel> photos = new ArrayList<PhotoModel>();
		cursor.moveToLast();
		do {
			if (cursor.getLong(cursor.getColumnIndex(ImageColumns.SIZE)) > 1024 * 10) {
				PhotoModel photoModel = new PhotoModel();
				photoModel.setOriginalPath(cursor.getString(cursor
						.getColumnIndex(ImageColumns.DATA)));
				photos.add(photoModel);
			}
		} while (cursor.moveToPrevious());
		return photos;
	}
*/

	/** ��ȡ��������б� */
	public List<AlbumModel> getAlbums() {
		List<AlbumModel> albums = new ArrayList<AlbumModel>();
		Map<String, AlbumModel> map = new HashMap<String, AlbumModel>();
		Cursor cursor = resolver.query(Media.EXTERNAL_CONTENT_URI,
				new String[] { ImageColumns.DATA,
						ImageColumns.BUCKET_DISPLAY_NAME, ImageColumns.SIZE },
				null, null, null);
		if (cursor == null || !cursor.moveToNext())
			return new ArrayList<AlbumModel>();
		cursor.moveToLast();
		AlbumModel current = new AlbumModel("最近照片", 0, cursor.getString(cursor
				.getColumnIndex(ImageColumns.DATA)), true); // "�����Ƭ"���
		albums.add(current);
		do {
			if (cursor.getInt(cursor.getColumnIndex(ImageColumns.SIZE)) < 1024 * 10)
				continue;

			current.increaseCount();
			String name = cursor.getString(cursor
					.getColumnIndex(ImageColumns.BUCKET_DISPLAY_NAME));
			if (map.keySet().contains(name))
				map.get(name).increaseCount();
			else {
				AlbumModel album = new AlbumModel(name, 1,
						cursor.getString(cursor
								.getColumnIndex(ImageColumns.DATA)));
				map.put(name, album);
				albums.add(album);
			}
		} while (cursor.moveToPrevious());
		return albums;
	}

	/***/
	public List<PhotoModel> getAlbum(String name) {
		Cursor cursor = resolver.query(Media.EXTERNAL_CONTENT_URI,
				new String[] { ImageColumns.BUCKET_DISPLAY_NAME,
						ImageColumns.DATA, ImageColumns.DATE_ADDED,
						ImageColumns.SIZE }, "bucket_display_name = ?",
				new String[] { name }, ImageColumns.DATE_ADDED);
		if (cursor == null || !cursor.moveToNext())
			return new ArrayList<PhotoModel>();
		List<PhotoModel> photos = new ArrayList<PhotoModel>();
		cursor.moveToLast();
		do {
			if (cursor.getLong(cursor.getColumnIndex(ImageColumns.SIZE)) > 1024 * 10) {
				PhotoModel photoModel = new PhotoModel();
				photoModel.setOriginalPath(cursor.getString(cursor
						.getColumnIndex(ImageColumns.DATA)));
				photos.add(photoModel);
			}
		} while (cursor.moveToPrevious());
		return photos;
	}
}
