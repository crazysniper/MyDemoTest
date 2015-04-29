package com.example.listviewdemo.qq;

import java.util.ArrayList;
import java.util.List;

import com.example.listviewdemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QQAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private Context context;
	private List<Msg> contentList = new ArrayList<Msg>();

	public QQAdapter(Context context, List<Msg> contentList) {
		this.contentList = contentList;
		this.context = context;
		layoutInflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		return contentList.size();
	}

	@Override
	public Object getItem(int position) {
		return contentList.get(position);
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
			convertView = layoutInflater.inflate(R.layout.msg_item, null);
			viewHolder.leftLayout = (LinearLayout) convertView.findViewById(R.id.left_layout);
			viewHolder.rightLayout = (LinearLayout) convertView.findViewById(R.id.right_layout);
			viewHolder.leftmsg = (TextView) convertView.findViewById(R.id.left_msg);
			viewHolder.rightmsg = (TextView) convertView.findViewById(R.id.right_msg);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (contentList != null) {
			Msg msg = contentList.get(position);
			if (msg.getType() == Msg.TYPE_RECEIVED) {
				viewHolder.leftLayout.setVisibility(View.VISIBLE);
				viewHolder.rightLayout.setVisibility(View.GONE);
				viewHolder.leftmsg.setText(msg.getContent());
			} else if (msg.getType() == Msg.TYPE_SEND) {
				viewHolder.leftLayout.setVisibility(View.GONE);
				viewHolder.rightLayout.setVisibility(View.VISIBLE);
				viewHolder.rightmsg.setText(msg.getContent());
			}

		}
		return convertView;
	}

	private class ViewHolder {
		private LinearLayout leftLayout, rightLayout;
		private TextView leftmsg, rightmsg;
	}

}
