package com.gftest.myappclient.wangyi;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gftest.myappclient.R;
import com.gftest.myappclient.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class QuanFragment_Item_Adapter extends BaseAdapter {

	private List<JSONObject> jsonList = new ArrayList<JSONObject>();
	private List<JSONObject> topJsonList = new ArrayList<JSONObject>();
	private LayoutInflater layoutInflater;
	private Context context;

	private final int TOP = 0;
	private final int POST = 1;

	private LinearLayout.LayoutParams layoutParams1, layoutParams2;

	private PopupWindow popupWindow;

	public QuanFragment_Item_Adapter(Context context, List<JSONObject> jsonList, List<JSONObject> topJsonList) {
		this.context = context;
		this.jsonList = jsonList;
		this.topJsonList = topJsonList;
		layoutInflater = LayoutInflater.from(context);
		setLayoutParams(this.context);
		initPop();
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
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		if (position <= topJsonList.size() - 1) {
			return TOP;
		}
		return POST;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TopViewHolder topViewHolder = null;
		PostViewHolder postViewHolder = null;
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
			case TOP:
				convertView = layoutInflater.inflate(R.layout.quan_item_top, null);
				topViewHolder = new TopViewHolder();
				topViewHolder.topLine1 = (TextView) convertView.findViewById(R.id.topLine1);
				topViewHolder.topLine2 = (TextView) convertView.findViewById(R.id.topLine2);
				topViewHolder.title = (TextView) convertView.findViewById(R.id.title);
				convertView.setTag(topViewHolder);
				break;
			case POST:
				convertView = layoutInflater.inflate(R.layout.quan_item2, null);
				postViewHolder = new PostViewHolder();
				postViewHolder.postHead = (ImageView) convertView.findViewById(R.id.postHead);
				postViewHolder.nickname = (TextView) convertView.findViewById(R.id.nickname);
				postViewHolder.ageStr = (TextView) convertView.findViewById(R.id.ageStr);
				postViewHolder.showdated = (TextView) convertView.findViewById(R.id.showdated);
				postViewHolder.title = (TextView) convertView.findViewById(R.id.title);
				postViewHolder.picLayout = (LinearLayout) convertView.findViewById(R.id.picLayout);
				postViewHolder.pic1 = (ImageView) convertView.findViewById(R.id.pic1);
				postViewHolder.pic2 = (ImageView) convertView.findViewById(R.id.pic2);
				postViewHolder.pic3 = (ImageView) convertView.findViewById(R.id.pic3);
				postViewHolder.replyNum = (TextView) convertView.findViewById(R.id.replyNum);
				postViewHolder.pic1.setLayoutParams(layoutParams1);
				postViewHolder.pic2.setLayoutParams(layoutParams2);
				postViewHolder.pic3.setLayoutParams(layoutParams2);
				convertView.setTag(postViewHolder);
				break;
			}
		} else {
			switch (type) {
			case TOP:
				topViewHolder = (TopViewHolder) convertView.getTag();
				break;
			case POST:
				postViewHolder = (PostViewHolder) convertView.getTag();
				break;
			}
		}
		switch (type) {
		case TOP:
			JSONObject topJsonObject = topJsonList.get(position);
			topViewHolder.topLine1.setVisibility(View.GONE);
			if (position == 0) {
				topViewHolder.topLine1.setVisibility(View.VISIBLE);
			} else {
				topViewHolder.topLine2.setVisibility(View.VISIBLE);
			}
			String topId = topJsonObject.optString("id");
			String topTitle = topJsonObject.optString("title");

			topViewHolder.title.setText(topTitle);
			topViewHolder.title.setTag(topId);

			topViewHolder.title.setTextColor(Color.parseColor("#574d43"));
			break;
		case POST:
			JSONObject postJsonObject = jsonList.get(position);
			String postId = postJsonObject.optString("id");
			String avatar = postJsonObject.optString("avatar");
			String nickname = postJsonObject.optString("nickname");
			String age_str = postJsonObject.optString("age_str");
			String showdated = postJsonObject.optString("showdated");
			String title = postJsonObject.optString("title");
			String re_num = postJsonObject.optString("re_num");
			String post_type = postJsonObject.optString("post_type");
			int gold_added = postJsonObject.optInt("gold_added");
			JSONArray pic_small = postJsonObject.optJSONArray("pic_small");

			if (post_type.equals("2")) {
				title = "<img src=\"" + R.drawable.list_jian + "\"> " + title;
			} else if (gold_added == 1) {
				title = "<img src=\"" + R.drawable.list_jing + "\"> " + title;
			}

			int length = pic_small.length();
			switch (length) {
			case 0:
				postViewHolder.picLayout.setVisibility(View.GONE);
				postViewHolder.pic1.setVisibility(View.GONE);
				postViewHolder.pic2.setVisibility(View.GONE);
				postViewHolder.pic3.setVisibility(View.GONE);
				break;
			case 1:
				postViewHolder.picLayout.setVisibility(View.VISIBLE);
				postViewHolder.pic1.setVisibility(View.VISIBLE);
				postViewHolder.pic2.setVisibility(View.GONE);
				postViewHolder.pic3.setVisibility(View.GONE);
				break;
			case 2:
				postViewHolder.picLayout.setVisibility(View.VISIBLE);
				postViewHolder.pic1.setVisibility(View.VISIBLE);
				postViewHolder.pic2.setVisibility(View.VISIBLE);
				postViewHolder.pic3.setVisibility(View.GONE);
				break;
			case 3:
				postViewHolder.picLayout.setVisibility(View.VISIBLE);
				postViewHolder.pic1.setVisibility(View.VISIBLE);
				postViewHolder.pic2.setVisibility(View.VISIBLE);
				postViewHolder.pic3.setVisibility(View.VISIBLE);
				break;

			default:
				break;
			}

			postViewHolder.nickname.setText(nickname);
			postViewHolder.ageStr.setText(age_str);
			postViewHolder.showdated.setText(showdated);

			postViewHolder.title.setText(Html.fromHtml(title, imageGetter, null));
			postViewHolder.title.setTag(postId);
			postViewHolder.replyNum.setText(re_num);

			postViewHolder.title.setTextColor(this.context.getResources().getColor(R.color.wordgray2));

			postViewHolder.replyNum.setOnClickListener(new MyListener(position));
			break;
		}
		return convertView;
	}

	private class TopViewHolder {
		private TextView topLine1, topLine2, title;
	}

	private class PostViewHolder {
		private ImageView postHead, pic1, pic2, pic3;
		private TextView nickname, ageStr, showdated, title, replyNum;
		private LinearLayout picLayout;

	}

	ImageGetter imageGetter = new ImageGetter() {
		@Override
		public Drawable getDrawable(String source) {
			int id = Integer.parseInt(source);
			Drawable drawable = context.getResources().getDrawable(id);
			drawable.setBounds(0, 0, Utils.dip2px(context, 18), Utils.dip2px(context, 18));
			return drawable;
		}
	};

	private void setLayoutParams(Context context) {
		int screenWidth = Utils.getDeviceSize(context).x;
		int marinLeft = Utils.dip2px(context, 11);
		int picWidth = (screenWidth - Utils.dip2px(context, 80) - marinLeft * 2 - Utils.dip2px(context, 18)) / 3;
		layoutParams1 = new LinearLayout.LayoutParams(picWidth, picWidth);
		layoutParams2 = new LinearLayout.LayoutParams(picWidth, picWidth);
		layoutParams2.setMargins(marinLeft, 0, 0, 0);
	}

	private class MyListener implements OnClickListener {
		private int position;

		public MyListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			int[] arrayOfInt = new int[2];
			// 获取点击按钮的坐标
			v.getLocationOnScreen(arrayOfInt);
			int x = arrayOfInt[0];
			int y = arrayOfInt[1];
			showPop(v, x, y, position);
		}
	}

	private void initPop() {
		View view = layoutInflater.inflate(R.layout.fragment_wangyi_choose_pop, null);
		popupWindow = new PopupWindow(context);
		popupWindow.setContentView(view);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new ColorDrawable(0xb0000000));
		popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
		popupWindow.setWidth(context.getResources().getDisplayMetrics().widthPixels);
		popupWindow.setAnimationStyle(R.style.fragment_wangyi_choose_pop);

		TextView read = (TextView) view.findViewById(R.id.read);
		TextView collect = (TextView) view.findViewById(R.id.collect);
		TextView nointerest = (TextView) view.findViewById(R.id.nointerest);
		ImageView close = (ImageView) view.findViewById(R.id.close);

		read.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
		collect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
		nointerest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
	}

	/**
	 * 显示popWindow
	 * */
	public void showPop(View parent, int x, int y, int postion) {
		// 设置popwindow显示位置
		popupWindow.showAtLocation(parent, 0, x, y);
		// 获取popwindow焦点
		popupWindow.setFocusable(true);
		// 设置popwindow如果点击外面区域，便关闭。
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
		if (popupWindow.isShowing()) {

		}
	}
}
