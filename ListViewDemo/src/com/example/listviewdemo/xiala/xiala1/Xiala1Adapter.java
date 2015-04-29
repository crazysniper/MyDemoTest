package com.example.listviewdemo.xiala.xiala1;

import java.util.ArrayList;
import java.util.List;

import com.example.listviewdemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Xiala1Adapter extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private Context context;
	private List<String> letterList = new ArrayList<String>();

	public Xiala1Adapter(Context context, List<String> letterList) {
		this.context = context;
		layoutInflater = LayoutInflater.from(this.context);
		this.letterList = letterList;
	}

	@Override
	public int getCount() {
		return letterList.size();
	}

	@Override
	public Object getItem(int position) {
		return letterList.get(position);
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
			convertView = layoutInflater.inflate(R.layout.xiala1_item, null);
			viewHolder.tv = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv.setText(letterList.get(position));
		return convertView;
	}

	private class ViewHolder {
		private TextView tv;
	}

}
