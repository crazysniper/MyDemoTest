package com.gftest.myappclient.new_imagepicker;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.gftest.myappclient.R;
import com.gftest.myappclient.new_imagepicker.ImageLoader.Type;

public class MyAdapter2 extends BaseAdapter {

	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	public static List<String> mSelectedImage = new LinkedList<String>();

	/**
	 * 文件夹路径
	 */
	private String mDirPath;
	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<String> mDatas;

	public MyAdapter2(Context context, List<String> mDatas, String dirPath) {
		this.mContext = context;
		this.mDatas = mDatas;
		this.mDirPath = dirPath;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.grid_item, null);
			viewHolder = new ViewHolder();
			viewHolder.id_item_image = (ImageView) convertView.findViewById(R.id.id_item_image);
			viewHolder.id_item_select = (ImageView) convertView.findViewById(R.id.id_item_select);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.id_item_image.setColorFilter(null);
		String path = mDatas.get(position);
		// viewHolder.name.setText(path);
		// imageLoader.loadImage(dirPath + "/" + path, viewHolder.imageItem);
		ImageLoader.getInstance(3, Type.LIFO).loadImage(mDirPath + "/" + path, viewHolder.id_item_image);

		System.out.println("mSelectedImage=" + mSelectedImage);

		if (mSelectedImage.contains(path)) {// 已经选择过的
			System.out.println("包括position=" + position + "====>>" + (mDirPath + "/" + path));
			viewHolder.id_item_select.setImageResource(R.drawable.pictures_selected);
			viewHolder.id_item_image.setColorFilter(Color.parseColor("#77000000"));
		} else {
			System.out.println("不包括position=" + position + "====>>" + (mDirPath + "/" + path));
			viewHolder.id_item_select.setImageResource(R.drawable.picture_unselected);
		}

		viewHolder.id_item_image.setOnClickListener(new MyOnclickListener(path, viewHolder.id_item_image, viewHolder.id_item_select));
		return convertView;
	}

	private class MyOnclickListener implements OnClickListener {
		private String path;
		private ImageView id_item_image, id_item_select;

		public MyOnclickListener(String path, ImageView id_item_image, ImageView id_item_select) {
			this.path = path;
			this.id_item_image = id_item_image;
			this.id_item_select = id_item_select;
		}

		@Override
		public void onClick(View v) {
			if (mSelectedImage.contains(mDirPath + "/" + path)) {
				mSelectedImage.remove(mDirPath + "/" + path);
				id_item_select.setImageResource(R.drawable.picture_unselected);
				id_item_image.setColorFilter(null);
			} else {
				mSelectedImage.add(mDirPath + "/" + path);
				id_item_select.setImageResource(R.drawable.pictures_selected);
				id_item_image.setColorFilter(Color.parseColor("#77000000"));
			}
		}
	}

	private class ViewHolder {
		private ImageView id_item_image, id_item_select;
	}

}
