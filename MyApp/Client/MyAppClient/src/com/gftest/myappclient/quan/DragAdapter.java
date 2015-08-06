package com.gftest.myappclient.quan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gftest.myappclient.R;
import com.gftest.myappclient.quan.DragGridView.DragGridBaseAdapter;
import com.gftest.myappclient.utils.Utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
	private RelativeLayout.LayoutParams layoutParams;

	public DragAdapter(Context context, List<QuanEntity> quanList) {
		this.context = context;
		this.quanList = quanList;
		layoutInflater = LayoutInflater.from(context);
		int screentWidth = Utils.getDeviceSize(context).x;
		int width = (screentWidth - Utils.dip2px(context, 10) * 4) / 3;
		layoutParams = new RelativeLayout.LayoutParams(width, LayoutParams.WRAP_CONTENT);
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
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = layoutInflater.inflate(R.layout.quan_item, null);
		TextView text_item = (TextView) convertView.findViewById(R.id.text_item);
		text_item.setLayoutParams(layoutParams);
		text_item.setPadding(Utils.dip2px(context, 7), Utils.dip2px(context, 5), Utils.dip2px(context, 7), Utils.dip2px(context, 5));
		text_item.setGravity(Gravity.CENTER);

		QuanEntity quanEntity = quanList.get(position);
		text_item.setText(quanEntity.getName());

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
