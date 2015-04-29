package com.example.listviewdemo.adapter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.listviewdemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adapter2 extends BaseAdapter {

	private Context context;
	private LayoutInflater layoutInflater;
	private List<JSONObject> jsonList;

	public Adapter2(Context context, List<JSONObject> jsonList) {
		this.context = context;
		this.jsonList = jsonList;
		this.layoutInflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		return jsonList.size();
	}

	@Override
	public Object getItem(int position) {
		return jsonList.get(position);
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
			convertView = layoutInflater.inflate(R.layout.async_item, null);
			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (jsonList != null) {
			JSONObject jsonObject = jsonList.get(position);
			String title = "";
			try {
				title = jsonObject.getString("title");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			viewHolder.title.setText(title);
		}
		return convertView;
	}

	private class ViewHolder {
		private TextView title;
	}

}
