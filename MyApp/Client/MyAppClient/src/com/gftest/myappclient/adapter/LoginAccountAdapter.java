package com.gftest.myappclient.adapter;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.gftest.myappclient.R;

public class LoginAccountAdapter extends BaseAdapter {
	private Context mContext;
	private List<String> mDtatList;// 显示数据
	private Handler mHandler;// 传递消息

	public LoginAccountAdapter(Context context, List<String> list, Handler handler) {
		this.mContext = context;
		this.mDtatList = list;
		this.mHandler = handler;
	}

	@Override
	public int getCount() {
		return mDtatList.size();
	}

	@Override
	public Object getItem(int position) {
		return mDtatList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.logined_account_item, null);
			holder.textView = (TextView) convertView.findViewById(R.id.tv);
			holder.button = (Button) convertView.findViewById(R.id.del_but);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 给textView和button设置唯一标示
		holder.textView.setTag(position);
		holder.button.setTag(position);
		holder.textView.setText(mDtatList.get(position));
		holder.textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				Bundle bundle = new Bundle();
				int id = Integer.parseInt(v.getTag().toString());
				bundle.putInt("sel_id", id);
				msg.setData(bundle);
				msg.what = 1;
				mHandler.sendMessage(msg);
			}
		});

		holder.button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putInt("del_id", Integer.parseInt(v.getTag().toString()));
				msg.setData(bundle);
				msg.what = 2;
				mHandler.sendMessage(msg);
			}
		});
		return convertView;
	}

	private class ViewHolder {
		TextView textView;
		Button button;
	}
}
