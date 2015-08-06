package com.example.imagedemo.image4;

import java.util.ArrayList;
import java.util.List;

import com.example.imagedemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageAdapter4 extends MyAdapter {

	private Context context;
	private LayoutInflater layoutInflater;
	private List<String> urlList = new ArrayList<String>();

	public ImageAdapter4(Context context, List<String> urlList) {
		this.context = context;
		this.urlList = urlList;
		layoutInflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		return urlList.size();
	}

	@Override
	public Object getItem(int position) {
		return urlList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.image4_item, null);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

	private class ViewHolder {
		private ImageView image;
	}

}
