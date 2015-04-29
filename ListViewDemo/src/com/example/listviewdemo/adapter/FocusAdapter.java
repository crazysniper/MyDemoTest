package com.example.listviewdemo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.listviewdemo.R;

/**
 * 焦点Adapter
 * 
 * @author Gao
 * 
 */
public class FocusAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater layoutInflater;
	private List<String> dataList = new ArrayList<String>();

	public FocusAdapter(Context context, List<String> dataList) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.focus_item, null);
			viewHolder = new ViewHolder();
			viewHolder.btn = (Button) convertView.findViewById(R.id.btn);
			viewHolder.tv = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.btn.setText("按钮" + dataList.get(position));
		viewHolder.tv.setText("文本" + dataList.get(position));
		viewHolder.btn.setOnClickListener(new clickListener(position));
		return convertView;
	}

	private class ViewHolder {
		private Button btn;
		private TextView tv;
	}

	private class clickListener implements OnClickListener {

		private int index;

		public clickListener(int index) {
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			System.out.println("子控件的点击下标=" + index);
		}

	}

}
