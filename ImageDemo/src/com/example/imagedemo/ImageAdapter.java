package com.example.imagedemo;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.example.imagedemo.image.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private List<String> imageUrls; // 图片地址list
	private Context context;
	private ImageLoader imageLoader;
	private LayoutInflater layoutInflater;

	public ImageAdapter(List<String> imageUrls, Context context) {
		this.imageUrls = imageUrls;
		this.context = context;
		imageLoader = new ImageLoader(this.context);
		layoutInflater = LayoutInflater.from(this.context);
	}

	public int getCount() {
		return imageUrls.size();
	}

	public Object getItem(int position) {
		return imageUrls.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Bitmap image;
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.item, null); // 实例化convertView
			// image =
			// UrlConnectionActivity.imagesCache.get(imageUrls.get(position));
			// // 从缓存中读取图片
			// if (image == null) {
			// // 当缓存中没有要使用的图片时，先显示默认的图片
			// image =
			// UrlConnectionActivity.imagesCache.get("background_non_load");
			// // 异步加载图片
			// LoadImageTask task = new LoadImageTask(convertView);
			// task.execute(imageUrls.get(position));
			// }
			viewHolder.gallery_image = (ImageView) convertView.findViewById(R.id.gallery_image);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		System.out.println("url=" + imageUrls.get(position));
		imageLoader.displayImage(imageUrls.get(position), viewHolder.gallery_image, R.drawable.ic_launcher, false, false);
		// imageView.setImageBitmap(image);
		// imageView.setScaleType(ImageView.ScaleType.FIT_XY);

		return convertView;
	}

	private class ViewHolder {
		private ImageView gallery_image;
	}

	// // 加载图片的异步任务
	// class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
	// private View resultView;
	//
	// LoadImageTask(View resultView) {
	// this.resultView = resultView;
	// }
	//
	// // doInBackground完成后才会被调用
	// @Override
	// protected void onPostExecute(Bitmap bitmap) {
	// // 调用setTag保存图片以便于自动更新图片
	// resultView.setTag(bitmap);
	// }
	//
	// // 从网上下载图片
	// @Override
	// protected Bitmap doInBackground(String... params) {
	// Bitmap image = null;
	// try {
	// // new URL对象 把网址传入
	// URL url = new URL(params[0]);
	// // 取得链接
	// URLConnection conn = url.openConnection();
	// conn.connect();
	// // 取得返回的InputStream
	// InputStream is = conn.getInputStream();
	// // 将InputStream变为Bitmap
	// image = BitmapFactory.decodeStream(is);
	// is.close();
	// UrlConnectionActivity.imagesCache.put(params[0], image); // 把下载好的图片保存到缓存中
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return image;
	// }
	// }
}
