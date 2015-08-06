package com.gftest.myappclient.new_imagepicker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.gftest.myappclient.R;
import com.gftest.myappclient.new_imagepicker.ImageLoader.Type;
import com.gftest.myappclient.utils.Utils;

public class ImageAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private Context context;
	private List<String> pathList = new ArrayList<String>();
	/** 用户选择的图片，存储为图片的完整路径 */
	public static List<String> selectedImagePath = new LinkedList<String>();
	/** 文件夹路径 */
	private String dirPath;

	private FrameLayout.LayoutParams layoutParams;

	public ImageAdapter(Context context, List<String> pathList, String dirPath) {
		this.context = context;
		this.pathList = pathList;
		layoutInflater = LayoutInflater.from(this.context);
		this.dirPath = dirPath;
		setLayoutParams();
	}

	@Override
	public int getCount() {
		return pathList.size();
	}

	@Override
	public Object getItem(int position) {
		return pathList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.new_image_item, null);
			viewHolder = new ViewHolder();
			viewHolder.imageItem = (ImageView) convertView.findViewById(R.id.imageItem);
			viewHolder.cover = (ImageView) convertView.findViewById(R.id.cover);
			viewHolder.isSelected = (ImageView) convertView.findViewById(R.id.isSelected);
			viewHolder.name = (TextView) convertView.findViewById(R.id.name);
			viewHolder.imageItem.setLayoutParams(layoutParams);
			viewHolder.cover.setLayoutParams(layoutParams);
			viewHolder.imageItem.setScaleType(ScaleType.CENTER_CROP);
			viewHolder.cover.setScaleType(ScaleType.FIT_XY);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.imageItem.setImageResource(R.drawable.pictures_no);
		viewHolder.cover.setVisibility(View.GONE);
		viewHolder.isSelected.setVisibility(View.GONE);

		String path = pathList.get(position);
		System.out.println("myappclient adapter position=" + position + "==>>" + (dirPath + "/" + path));
//		System.out.println("position=" + position + "====>>" + (dirPath + "/" + path));
		// viewHolder.name.setText(path);
		// imageLoader.loadImage(dirPath + "/" + path, viewHolder.imageItem);
		ImageLoader.getInstance(3, Type.LIFO).loadImage(dirPath + "/" + path, viewHolder.imageItem);
//System.out.println(path);
		if (selectedImagePath.contains(dirPath + "/" + path)) {// 已经选择过的
//			System.out.println("包括position=" + position + "====>>" + (dirPath + "/" + path));
			viewHolder.cover.setVisibility(View.VISIBLE);
			viewHolder.isSelected.setVisibility(View.VISIBLE);
		}

		viewHolder.imageItem.setOnClickListener(new MyOnclickListener(path, viewHolder.cover, viewHolder.isSelected));
		return convertView;
	}

	private class MyOnclickListener implements OnClickListener {
		private String path;
		private ImageView cover, isSelected;

		public MyOnclickListener(String path, ImageView cover, ImageView isSelected) {
			this.path = path;
			this.cover = cover;
			this.isSelected = isSelected;
		}

		@Override
		public void onClick(View v) {
			if (selectedImagePath.contains(dirPath + "/" + path)) {
				selectedImagePath.remove(dirPath + "/" + path);
				isSelected.setVisibility(View.GONE);
				cover.setVisibility(View.GONE);
			} else {
				selectedImagePath.add(dirPath + "/" + path);
				isSelected.setVisibility(View.VISIBLE);
				cover.setVisibility(View.VISIBLE);
			}
		}
	}

	private class ViewHolder {
		private ImageView imageItem, cover, isSelected;
		private TextView name;
	}

	private void setLayoutParams() {
		int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
		int width = (screenWidth - Utils.dip2px(context, 5) * 2) / 3;
		layoutParams = new FrameLayout.LayoutParams(width, width);
	}
}
