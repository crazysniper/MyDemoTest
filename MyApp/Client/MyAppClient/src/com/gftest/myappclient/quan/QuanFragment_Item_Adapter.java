package com.gftest.myappclient.quan;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gftest.myappclient.R;
import com.gftest.myappclient.imageloader.ImageLoaderNew;
import com.gftest.myappclient.utils.Utils;

public class QuanFragment_Item_Adapter extends BaseAdapter {

	private List<JSONObject> jsonList = new ArrayList<JSONObject>();
	private LayoutInflater layoutInflater;
	private Context context;
	public ImageLoaderNew imageLoader;
	private int topNum = 0;
	private String hasSelectedIds;

	public QuanFragment_Item_Adapter(Context context, List<JSONObject> jsonList) {
		this.context = context;
		this.jsonList = jsonList;
		layoutInflater = LayoutInflater.from(context);
		imageLoader = ImageLoaderNew.getInstance(context);
	}

	public void setSelectedIds(String hasSelectedIds) {
		this.hasSelectedIds = hasSelectedIds;
	}

	public void setTopNum(int top_num) {
		topNum = top_num;
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
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.bbs_item, null);
			holder = new ViewHolder();
			holder.head_box = (FrameLayout) convertView.findViewById(R.id.head_box);
			holder.image = (ImageView) convertView.findViewById(R.id.ItemImage);
			holder.title = (TextView) convertView.findViewById(R.id.ItemTitle);
			holder.totalnum = (TextView) convertView.findViewById(R.id.ItemNum);
			holder.nickname = (TextView) convertView.findViewById(R.id.ItemText);
			holder.age_str = (TextView) convertView.findViewById(R.id.ItemSign);
			holder.dated = (TextView) convertView.findViewById(R.id.ItemDated);
			holder.postid = (TextView) convertView.findViewById(R.id.ItemPostId);
			holder.item_bottom = (LinearLayout) convertView.findViewById(R.id.ItemBottom);
			holder.item_top = (TextView) convertView.findViewById(R.id.ItemTop);
			holder.top_line = (TextView) convertView.findViewById(R.id.top_line);
			holder.top_space = (TextView) convertView.findViewById(R.id.top_space);
			holder.bottom_line = (TextView) convertView.findViewById(R.id.bottom_line);
			holder.bottom_space = (TextView) convertView.findViewById(R.id.bottom_space);
			holder.in_line = (TextView) convertView.findViewById(R.id.in_line);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		JSONObject jsonObject = (JSONObject) jsonList.get(position);
		holder.nickname.setText(jsonObject.optString("nickname"));
		holder.totalnum.setText(jsonObject.optString("re_num"));
		holder.age_str.setText(jsonObject.optString("age_str"));
		holder.dated.setText(jsonObject.optString("showdated"));
		holder.postid.setText(jsonObject.optString("id"));
		imageLoader.displayImage(jsonObject.optString("avatar"), holder.image, R.drawable.ic_launcher, false, false, 0);
		String postSeletedId = jsonObject.optString("id");

		String post_type = jsonObject.optString("post_type");

		if (post_type.equals("1")) {
			holder.item_bottom.setVisibility(View.GONE);
			holder.item_top.setVisibility(View.VISIBLE);
			holder.image.setVisibility(View.GONE);
			holder.head_box.setVisibility(View.GONE);
			holder.title.setText(jsonObject.optString("title"));
			holder.title.setSingleLine(true);
			holder.title.setEllipsize(TruncateAt.END);
			if (position == 0 && position == topNum - 1) {
				holder.in_line.setVisibility(View.VISIBLE);
				holder.top_line.setVisibility(View.VISIBLE);
				holder.top_space.setVisibility(View.VISIBLE);
				holder.bottom_line.setVisibility(View.VISIBLE);
				holder.bottom_space.setVisibility(View.VISIBLE);
			} else if (position == 0) {
				holder.in_line.setVisibility(View.VISIBLE);
				holder.top_line.setVisibility(View.VISIBLE);
				holder.top_space.setVisibility(View.VISIBLE);
				holder.bottom_line.setVisibility(View.GONE);
				holder.bottom_space.setVisibility(View.GONE);
			} else if (position == topNum - 1) {
				holder.in_line.setVisibility(View.GONE);
				holder.top_line.setVisibility(View.GONE);
				holder.top_space.setVisibility(View.GONE);
				holder.bottom_line.setVisibility(View.VISIBLE);
				holder.bottom_space.setVisibility(View.VISIBLE);
			} else {
				holder.in_line.setVisibility(View.VISIBLE);
				holder.top_line.setVisibility(View.GONE);
				holder.top_space.setVisibility(View.GONE);
				holder.bottom_line.setVisibility(View.GONE);
				holder.bottom_space.setVisibility(View.GONE);
			}
		} else {
			holder.item_bottom.setVisibility(View.VISIBLE);
			holder.item_top.setVisibility(View.GONE);
			holder.image.setVisibility(View.VISIBLE);
			holder.head_box.setVisibility(View.VISIBLE);
			int gold_added = jsonObject.optInt("gold_added");

			String content = jsonObject.optString("title");
			if (!jsonObject.optString("pic").equals("0")) {
				content = content + "<img src=\"" + R.drawable.has_pic + "\">";
			}

			if (post_type.equals("1")) {
				content = "<img src=\"" + R.drawable.list_ding + "\"> " + content;
			} else if (post_type.equals("2")) {
				content = "<img src=\"" + R.drawable.list_jian + "\"> " + content;
			} else if (gold_added == 1) {
				content = "<img src=\"" + R.drawable.list_jing + "\"> " + content;
			}

			holder.title.setText(Html.fromHtml(content, imageGetter, null));
			holder.title.setSingleLine(false);
			holder.title.setEllipsize(null);
			holder.in_line.setVisibility(View.GONE);
			holder.top_line.setVisibility(View.VISIBLE);
			holder.top_space.setVisibility(View.VISIBLE);
			holder.bottom_line.setVisibility(View.VISIBLE);
			holder.bottom_space.setVisibility(View.VISIBLE);
		}
		if (hasSelectedIds.indexOf(postSeletedId) != -1) {
			holder.title.setTextColor(context.getResources().getColor(R.color.wordgray2));
		} else {
			holder.title.setTextColor(0xff000000);
		}
		return convertView;
	}

	private class ViewHolder {
		private TextView title, totalnum, nickname, age_str, dated, postid, item_top, top_line, bottom_line, top_space, bottom_space, in_line;
		private ImageView image;
		private LinearLayout item_bottom;
		private FrameLayout head_box;
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
}
