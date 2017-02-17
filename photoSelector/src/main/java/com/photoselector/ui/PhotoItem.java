package com.photoselector.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.photoselector.R;
import com.photoselector.model.PhotoModel;

/**
 * @author Aizaz AZ
 */
public class PhotoItem extends LinearLayout implements OnCheckedChangeListener,
		OnLongClickListener {

	private ImageView ivPhoto;
	private CheckBox cbPhoto;
	private OnPhotoItemCheckedListener checkListener;
	private PhotoModel photo;
	private boolean isCheckAll;
	private onItemClickListener clickListener;
	private int position;

	private PhotoItem(Context context) {
		super(context);
	}

	public PhotoItem(Context context, OnPhotoItemCheckedListener listener) {
		this(context);
		LayoutInflater.from(context).inflate(R.layout.layout_photoitem, this,
				true);
		this.checkListener = listener;
		this.setOnLongClickListener(this);

		ivPhoto = (ImageView) findViewById(R.id.iv_photo_lpsi);
		cbPhoto = (CheckBox) findViewById(R.id.cb_photo_lpsi);

		cbPhoto.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//		if (!isCheckAll) {
//			checkListener.onCheckedChanged(photo, buttonView, isChecked);
//		}
//		if (isChecked) {
//			setDrawingable();
//			ivPhoto.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
//		} else {
//			ivPhoto.clearColorFilter();
//		}
//		photo.setChecked(isChecked);

	
		if (isChecked) {
			if (checkListener instanceof PhotoSelectorActivity) {
				PhotoSelectorActivity ref = (PhotoSelectorActivity) checkListener;
				if (!ref.selectedPhoto.contains(this.photo)) {
					int selected = ref.selectedPhoto.size();
//					System.out.println("count = " + selected);
					
					if (!(selected < PhotoSelectorActivity.MAX_IMAGE)) {
						Toast.makeText(
								ref.getApplicationContext(),
								getResources().getString(
										R.string.max_img_limit_reached,
										selected), Toast.LENGTH_SHORT).show();
						buttonView.setChecked(false);
						photo.setChecked(false);
						return;
					} else {
						checkListener.onCheckedChanged(photo, buttonView,
								isChecked);
						setDrawingable();
						ivPhoto.setColorFilter(Color.GRAY,
								PorterDuff.Mode.MULTIPLY);
						photo.setChecked(true);
					}
				}
//				else{
					//included, photo.setChecked(false)
//				}
			}
		} else {
			checkListener.onCheckedChanged(photo, buttonView, isChecked);
			ivPhoto.clearColorFilter();
			photo.setChecked(false);
		}
		 
	}

	/***/
	public void setImageDrawable(final PhotoModel photo) {
		this.photo = photo;
		// You may need this setting form some custom ROM(s)
		/*
		 * new Handler().postDelayed(new Runnable() {
		 * 
		 * @Override public void run() { ImageLoader.getInstance().displayImage(
		 * "file://" + photo.getOriginalPath(), ivPhoto); } }, new
		 * Random().nextInt(10));
		 */

		ImageLoader.getInstance().displayImage(
				"file://" + photo.getOriginalPath(), ivPhoto);
	}

	private void setDrawingable() {
		ivPhoto.setDrawingCacheEnabled(true);
		ivPhoto.buildDrawingCache();
	}

	// public void setPhotoChecked(boolean checked){
	// cbPhoto.setChecked(checked);
	// }

	@Override
	public void setSelected(boolean selected) {
		if (photo == null) {
			return;
		}
		isCheckAll = true;
		cbPhoto.setChecked(selected);
		isCheckAll = false;
	}

	/*
	 * @Override public void setSelected(boolean selected) { if (photo == null)
	 * { return; } isCheckAll = true; cbPhoto.setChecked(selected); isCheckAll =
	 * false; }
	 */

	public void setOnClickListener(onItemClickListener listener, int position) {
		this.clickListener = listener;
		this.position = position;
	}

	@Override
	public boolean onLongClick(View v) {
		if (clickListener != null)
			clickListener.onItemClick(position);
		return true;
	}

	// @Override
	// public void
	// onClick(View v) {
	// if (l != null)
	// l.onItemClick(position);
	// }

	/***/
	public static interface OnPhotoItemCheckedListener {
		public void onCheckedChanged(PhotoModel photoModel,
				CompoundButton buttonView, boolean isChecked);
	}

	/***/
	public interface onItemClickListener {
		public void onItemClick(int position);
	}
}
