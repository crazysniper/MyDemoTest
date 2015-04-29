package com.example.listviewdemo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.listviewdemo.R;
import com.example.listviewdemo.model.StudentEntity;

public class ListViewAdapter5 extends BaseAdapter {

	private Context context;
	private List<StudentEntity> stuList = new ArrayList<StudentEntity>();
	private LayoutInflater layoutInflater;
	private String aa = "";
	private List<StudentEntity> stuList2 = new ArrayList<StudentEntity>();
	private List<String> aaList = new ArrayList<String>();

	public ListViewAdapter5(Context context, List<StudentEntity> stuList, List<String> aaList, List<StudentEntity> stuList2) {
		this.context = context;
		this.stuList = stuList;
		this.stuList2 = stuList2;
		this.aaList = aaList;
		layoutInflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		System.out.println("getCount");
		return stuList.size();
	}

	@Override
	public Object getItem(int position) {
		System.out.println("getItem");
		return stuList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("position=" + position);
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.list5_item, null);
			viewHolder.uid = (TextView) convertView.findViewById(R.id.uid);
			viewHolder.username = (TextView) convertView.findViewById(R.id.username);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (stuList != null) {
			StudentEntity entity = stuList.get(position);
			viewHolder.uid.setText(String.valueOf(entity.getUid()));
			String aaaString = "";
			if (aaList.size() > 0) {
				aaaString = aaList.get(0);
			}
			System.out.println("aa=" + aa);
			System.out.println("aaaString=" + aaaString);
			if ("".equals(aaaString)) {
				System.out.println("1111111");
				viewHolder.username.setText(entity.getUsername());
			} else {
				System.out.println("222222222");
				viewHolder.username.setText(aaaString);
			}
		}
		return convertView;
	}

	private class ViewHolder {
		private TextView uid, username;
	}

}
