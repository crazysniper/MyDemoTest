package com.gftest.myappclient.wangyi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gftest.myappclient.R;
import com.gftest.myappclient.utils.Utils;
import com.gftest.myappclient.wangyi.DragGridView.DragGridBaseAdapter;

/**
 * @blog http://blog.csdn.net/xiaanming
 * 
 * @author xiaanming
 * 
 */
public class DragAdapter extends BaseAdapter implements DragGridBaseAdapter {
	private List<QuanEntity> quanList = new ArrayList<QuanEntity>();
	private LayoutInflater layoutInflater;
	private int mHidePosition = -1;
	private Context context;
	private int width;
	private List<String> selectedNameList = new ArrayList<String>();

	public DragAdapter(Context context, List<QuanEntity> quanList, List<String> selectedNameList) {
		this.context = context;
		this.quanList = quanList;
		this.selectedNameList = selectedNameList;
		layoutInflater = LayoutInflater.from(context);
		int screentWidth = Utils.getDeviceSize(context).x;
		width = (screentWidth - Utils.dip2px(context, 10) * 4) / 3;
	}

	@Override
	public int getCount() {
		return quanList.size();
	}

	@Override
	public Object getItem(int position) {
		return quanList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 由于复用convertView导致某些item消失了，所以这里不复用item，
	 */
	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = layoutInflater.inflate(R.layout.quan_gridview_item, null);
		TextView text_item = (TextView) convertView.findViewById(R.id.text_item);

		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) text_item.getLayoutParams();
		layoutParams.width = width;
		text_item.setLayoutParams(layoutParams);

		QuanEntity quanEntity = quanList.get(position);
		text_item.setText(quanEntity.getName());

		text_item.setBackgroundDrawable(this.context.getResources().getDrawable(R.drawable.quan_item_tv_bg_selector));

		if (selectedNameList != null && selectedNameList.size() > 0 && quanEntity.getName().equals(selectedNameList.get(0))) {
			text_item.setBackgroundDrawable(this.context.getResources().getDrawable(R.drawable.quan_item_tv_hasselected_bg_selector));
			text_item.setTextColor(Color.WHITE);
		}

		if (position == mHidePosition) {
			convertView.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	public void reorderItems(int oldPosition, int newPosition) {
		QuanEntity quanEntity = quanList.get(oldPosition);
		if (oldPosition < newPosition) {
			for (int i = oldPosition; i < newPosition; i++) {
				Collections.swap(quanList, i, i + 1);
			}
		} else if (oldPosition > newPosition) {
			for (int i = oldPosition; i > newPosition; i--) {
				Collections.swap(quanList, i, i - 1);
			}
		}
		quanList.set(newPosition, quanEntity);
	}

	public void setHideItem(int hidePosition) {
		this.mHidePosition = hidePosition;
		notifyDataSetChanged();
	}

}
