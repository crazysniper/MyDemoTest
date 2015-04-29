package com.example.listviewdemo.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.listviewdemo.R;

public class DelAdapter extends BaseAdapter {

	private Context context;
	private List<String> list;
	private LayoutInflater layoutInflater;
	private float x, ux;
	private Button curDel_btn;

	public DelAdapter(Context context, List<String> list) {
		this.context = context;
		this.list = list;
		layoutInflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
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
			convertView = layoutInflater.inflate(R.layout.activity_list_slide_del_item, null);
			viewHolder.listView_item = (TextView) convertView.findViewById(R.id.listView_item);
			viewHolder.del_btn = (Button) convertView.findViewById(R.id.del_btn);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (list != null) {
			viewHolder.listView_item.setText(list.get(position));

			convertView.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					final ViewHolder holder = (ViewHolder) v.getTag();
					// 按下
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						// 获取按下时的x轴坐标
						x = event.getX();
						if (curDel_btn != null) {
							if (curDel_btn.getVisibility() == View.VISIBLE) {
								curDel_btn.setVisibility(View.GONE);
								return true;
							}
						}
					} else if (event.getAction() == MotionEvent.ACTION_UP) {// 抬起
						ux = event.getX();
						if (holder.del_btn != null) {
							if (Math.abs(x - ux) > 20) {
								holder.del_btn.setVisibility(View.VISIBLE);
								curDel_btn = holder.del_btn;
								return true;
							}
						}
					} else if (event.getAction() == MotionEvent.ACTION_MOVE) {// 移动
						return true;
					} else {// 其他

					}
					return false;
				}
			});

		}
		return convertView;
	}

	private class ViewHolder {
		private TextView listView_item;
		private Button del_btn;
	}

}
