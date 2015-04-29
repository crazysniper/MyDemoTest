package com.example.listviewdemo.qq;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.listviewdemo.R;

public class MyCustomAdapter extends BaseAdapter {

	private static final int TYPE_ITEM = 0;
	private static final int TYPE_SEPARATOR = 1;
	private static final int TYPE_MAX_COUNT = 2;

	private List<String> mData = new ArrayList<String>();
	private LayoutInflater layoutInflater;
	private TreeSet<Integer> mSeparatorsSet = new TreeSet<Integer>();
	private Context context;

	public MyCustomAdapter(Context context, List<String> mData) {
		this.context = context;
		this.layoutInflater = LayoutInflater.from(context);
		this.mData = mData;

	}

	public void addSeparatorItem(final String item) {
		mData.add(item);
		mSeparatorsSet.add(mData.size() - 1);
	}

	@Override
	public int getItemViewType(int position) {
		return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_MAX_COUNT;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public String getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		int type = getItemViewType(position);
		System.out.println("getView " + position + " " + convertView + " type = " + type);
		if (convertView == null) {
			holder = new ViewHolder();
			switch (type) {
			case TYPE_ITEM:
				convertView = layoutInflater.inflate(R.layout.qq2_item1, null);
				holder.textView = (TextView) convertView.findViewById(R.id.text);
				break;
			case TYPE_SEPARATOR:
				convertView = layoutInflater.inflate(R.layout.qq2_item2, null);
				holder.textView = (TextView) convertView.findViewById(R.id.textSeparator);
				break;
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(mData.get(position));
		return convertView;
	}

	public static class ViewHolder {
		public TextView textView;
	}
}
