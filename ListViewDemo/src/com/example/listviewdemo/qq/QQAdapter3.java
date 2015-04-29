package com.example.listviewdemo.qq;

import java.util.ArrayList;
import java.util.List;

import com.example.listviewdemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class QQAdapter3 extends BaseAdapter {

	private Context context;
	private LayoutInflater layoutInflater;
	private List<String> dataList = new ArrayList<String>();
	private final int TYPE1 = 0;
	private final int TYPE2 = 1;

	public QQAdapter3(Context context, List<String> dataList) {
		this.context = context;
		layoutInflater = LayoutInflater.from(this.context);
		this.dataList = dataList;
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	/**
	 * 返回的值不能是自定义的，必须从0开始增长。否则就会发生数组越界ArrayIndexOutOfBoundsException
	 */
	@Override
	public int getItemViewType(int position) {
		if (position % 2 == 0) {
			return TYPE1;
		}
		return TYPE2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Q3ViewHolder1 qHolder1 = null;
		Q3ViewHolder2 qHolder2 = null;
		int type = getItemViewType(position);
		System.out.println("dataList.size=" + dataList.size());
		System.out.println("position=" + position);
		System.out.println("type=" + type);
		if (convertView == null) {
			switch (type) {
			case 0:
				qHolder1 = new Q3ViewHolder1();
				convertView = layoutInflater.inflate(R.layout.qq3_item1, null);
				qHolder1.left_msg = (TextView) convertView.findViewById(R.id.left_msg);
				convertView.setTag(qHolder1);
				break;
			case 1:
				qHolder2 = new Q3ViewHolder2();
				convertView = layoutInflater.inflate(R.layout.qq3_item2, null);
				qHolder2.right_msg = (TextView) convertView.findViewById(R.id.right_msg);
				convertView.setTag(qHolder2);
				break;
			}
		} else {
			switch (type) {
			case 0:
				qHolder1 = (Q3ViewHolder1) convertView.getTag();
				break;
			case 1:
				qHolder2 = (Q3ViewHolder2) convertView.getTag();
				break;
			}
		}
		switch (type) {
		case 0:
			qHolder1.left_msg.setText(dataList.get(position));
			break;
		case 1:
			qHolder2.right_msg.setText(dataList.get(position));
			break;
		}
		return convertView;
	}

	private class Q3ViewHolder1 {
		private TextView left_msg;
	}

	private class Q3ViewHolder2 {
		private TextView right_msg;
	}
}
