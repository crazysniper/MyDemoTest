package com.gftest.myappclient.new_imagepicker;

import java.util.ArrayList;
import java.util.List;

import com.gftest.myappclient.R;
import com.gftest.myappclient.new_imagepicker.ImageLoader.Type;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageDirPopupWindowAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private Context context;
	/** 扫描拿到所有的图片文件夹 */
	private List<ImageFolder> imageFolderList = new ArrayList<ImageFolder>();
	private ImageLoader imageLoader;

	public ImageDirPopupWindowAdapter(Context context, List<ImageFolder> imageFolderList) {
		this.context = context;
		this.imageFolderList = imageFolderList;
		layoutInflater = LayoutInflater.from(this.context);
		imageLoader = ImageLoader.getInstance(3, Type.LIFO);
	}

	@Override
	public int getCount() {
		return imageFolderList.size();
	}

	@Override
	public Object getItem(int position) {
		return imageFolderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.new_imagepicker_pop_list_dir_item, null);
			viewHolder = new ViewHolder();
			viewHolder.id_dir_item_image = (ImageView) convertView.findViewById(R.id.id_dir_item_image);
			viewHolder.id_dir_item_name = (TextView) convertView.findViewById(R.id.id_dir_item_name);
			viewHolder.id_dir_item_count = (TextView) convertView.findViewById(R.id.id_dir_item_count);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ImageFolder imageFolder = imageFolderList.get(position);
		viewHolder.id_dir_item_name.setText(imageFolder.getDirName());
		viewHolder.id_dir_item_count.setText(imageFolder.getCount() + "张");

		String path = imageFolder.getFirstImgPath();
		System.out.println("path=" + path);
		imageLoader.loadImage(path, viewHolder.id_dir_item_image);
		return convertView;
	}

	private class ViewHolder {
		private ImageView id_dir_item_image;
		private TextView id_dir_item_name, id_dir_item_count;
	}

}
