package com.photoselector.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;

import com.photoselector.R;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoItem.OnPhotoItemCheckedListener;
import com.photoselector.ui.PhotoItem.onItemClickListener;

/**
 * 
 * @author Aizaz AZ
 * 
 */
public class PhotoSelectorAdapter extends MBaseAdapter<PhotoModel> {

	private int itemWidth;
	private int horizentalNum = 3;
	private OnPhotoItemCheckedListener listener;
	private LayoutParams itemLayoutParams;
	private onItemClickListener mCallback;
	private OnClickListener cameraListener;

	private PhotoSelectorAdapter(Context context, ArrayList<PhotoModel> models) {
		super(context, models);
	}

	public PhotoSelectorAdapter(Context context, ArrayList<PhotoModel> models,
			int screenWidth, OnPhotoItemCheckedListener listener,
			onItemClickListener mCallback, OnClickListener cameraListener) {
		this(context, models);
		setItemWidth(screenWidth);
		this.listener = listener;
		this.mCallback = mCallback;
		this.cameraListener = cameraListener;
	}

	/** 设置每一个Item的宽高 */
	public void setItemWidth(int screenWidth) {
		int horizentalSpace = context.getResources().getDimensionPixelSize(
				R.dimen.sticky_item_horizontalSpacing);
		this.itemWidth = (screenWidth - (horizentalSpace * (horizentalNum - 1)))
				/ horizentalNum;
		this.itemLayoutParams = new LayoutParams(itemWidth, itemWidth);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/*
		 * PhotoItem item = null; if (convertView == null || !(convertView
		 * instanceof PhotoItem)) { item = new PhotoItem(context, listener);
		 * item.setLayoutParams(itemLayoutParams); convertView = item; } else {
		 * item = (PhotoItem) convertView; }
		 * item.setImageDrawable(models.get(position));
		 * item.setSelected(models.get(position).isChecked());
		 * item.setOnClickListener(mCallback, position);
		 */

		if (position == 0) {
			View view = LayoutInflater.from(context).inflate(
					R.layout.view_camera, null);
			view.setOnClickListener(cameraListener);
			return view;
		} else {
			PhotoItem item = null;
			if (convertView == null || !(convertView instanceof PhotoItem)) {
				item = new PhotoItem(context, listener);
				item.setLayoutParams(itemLayoutParams);
				convertView = item;
			} else {
				item = (PhotoItem) convertView;
			}
			
			item.setImageDrawable(models.get(position));
			item.setSelected(models.get(position).isChecked());
//			item.setOnClickListener(mCallback, position);
			
//			item.setPhotoChecked(models.get(position).isChecked());

			/*
			if (context instanceof PhotoSelectorActivity
					&& ((PhotoSelectorActivity) context).selectedPhoto
							.contains(models.get(position))) {
				Log.e("====", position + "had selected");
				item.setPhotoChecked(true);
			} else {
				item.setPhotoChecked(false);
			}
			*/

			/**
			if (context instanceof PhotoSelectorActivity
					&& ((PhotoSelectorActivity) context).selectedPhoto
							.contains(objects.get(position))) {
				Log.e("---------",
						((PhotoSelectorActivity) context).selectedPhoto
								.contains(objects.get(position)) ? "contain"
								: "not contain");
				Log.e("=====", position
						+ " state: "
						+ (objects.get(position).isChecked() ? "selected"
								: "unselect"));
			}*/
			 
			return convertView;
		}
	}

	/** ������� */
	@Override
	public void update(List<PhotoModel> list) {
		if (list == null)
			return;
		this.models.clear();
		this.models.addAll(list);
		this.models.add(0, null);
		notifyDataSetChanged();
	}
}
