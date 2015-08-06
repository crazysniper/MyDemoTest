package com.gftest.myappclient.adapter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gftest.myappclient.R;
import com.gftest.myappclient.imageloader.ImageLoaderNew;

public class Fragment5_item_Adapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater layoutInflater;
	private List<JSONObject> jsonList;
	private ImageLoaderNew imageLoaderNew;

	public Fragment5_item_Adapter(Activity activity, List<JSONObject> jsonList) {
		this.activity = activity;
		this.jsonList = jsonList;
		layoutInflater = LayoutInflater.from(this.activity);
		imageLoaderNew = ImageLoaderNew.getInstance(activity);
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
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.act_item, null);
			viewHolder.actImg = (ImageView) convertView.findViewById(R.id.act_img);// 图片
			viewHolder.actOverBg = (ImageView) convertView.findViewById(R.id.act_over_bg);// 已结束的覆盖层
			viewHolder.actNum = (TextView) convertView.findViewById(R.id.act_num);// 奖品数
			viewHolder.actTime = (TextView) convertView.findViewById(R.id.act_time);// 距离结束时间
			viewHolder.actCity = (TextView) convertView.findViewById(R.id.act_city);// 城市圈名
			viewHolder.actLocal = (LinearLayout) convertView.findViewById(R.id.act_local);// 城市圈LinearLayout
			viewHolder.url = (TextView) convertView.findViewById(R.id.url);
			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (jsonList != null) {
			JSONObject jsonObject = jsonList.get(position);
			try {
				String url = jsonObject.getString("url");
				viewHolder.actImg.setVisibility(View.GONE);
				// imageLoaderNew.displayImage(jsonObject.getString("pic"),
				// viewHolder.actImg, R.drawable.ic_launcher, false, false, 0);
				String num = jsonObject.getString("prize_num");// 奖品数
				String city = jsonObject.getString("city");// 2:本城市圈
				String timeType = jsonObject.getString("time_type");// 0：即将开始；1：未结束；2：已结束
				String timeStr = jsonObject.getString("time_str");// 距离开始时间
				String title = jsonObject.getString("m_title");// 标题

				if ("2".equals(city)) {// 2:本城市圈
					viewHolder.actLocal.setVisibility(View.VISIBLE);
				} else {
					viewHolder.actLocal.setVisibility(View.INVISIBLE);
				}
				viewHolder.actNum.setText(Html.fromHtml("奖品数：<font color=#ff537b>" + num + "</font>份"));
				if ("1".equals(timeType)) {// 1：未结束
					viewHolder.actTime.setText(Html.fromHtml("距离结束：<font color=#ff537b>" + timeStr + "</font>"));
					viewHolder.actOverBg.setVisibility(View.GONE);
				} else if ("0".equals(timeType)) {// 0：即将开始；
					viewHolder.actTime.setText(Html.fromHtml("即将开始：<font color=#005aff>" + timeStr + "</font>"));
					viewHolder.actOverBg.setVisibility(View.GONE);
				} else {// 2：已结束
					viewHolder.actTime.setText("已结束");
					viewHolder.actOverBg.setVisibility(View.GONE);
				}
				viewHolder.url.setText(url);
				viewHolder.title.setText(title);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return convertView;
	}

	public class ViewHolder {
		LinearLayout actLocal;
		ImageView actImg, actOverBg;
		TextView actNum, actTime, actCity, url, title;
	}
}
