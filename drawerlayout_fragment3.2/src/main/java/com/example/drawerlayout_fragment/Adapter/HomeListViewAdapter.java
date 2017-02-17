package com.example.drawerlayout_fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drawerlayout_fragment.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeListViewAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<HashMap<String, Object>> mListViewData;
	
	public HomeListViewAdapter(Context context,
			ArrayList<HashMap<String, Object>> mListViewData) {
		this.context = context;
		this.mListViewData = mListViewData;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		HolderView holderView = null;
		if (convertView == null) {
			holderView = new HolderView();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.home_listview_item, null);
			holderView.home_logo = (ImageView) convertView
					.findViewById(R.id.home_listview_item_logo);
			holderView.home_text = (TextView) convertView
					.findViewById(R.id.home_listview_item_text);
			convertView.setTag(holderView);
		} else {
			holderView = (HolderView) convertView.getTag();
		}
//		holderView.home_logo.setBackgroundResource((Integer) mListViewData.get(position).get("home_logo"));
		holderView.home_text.setText(mListViewData.get(position).get("home_text").toString());
		return convertView;
	}

	class HolderView {
		ImageView home_logo;
		TextView home_text;
	}

	@Override
	public int getCount() {
		return mListViewData.size();
	}

	@Override
	public Object getItem(int position) {
		return mListViewData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}