package com.example.imagedemo.image3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.imagedemo.R;
import com.example.imagedemo.image3.ImageDownLoader.onImageLoaderListener;

/**
 * 主要是GridView滑动的时候取消下载任务，静止的时候去下载当前显示的item的图片，其他也没什么不同了
 * 
 * @author Administrator
 * 
 */
public class ImageAdapter3 extends BaseAdapter implements OnScrollListener {
	/**
	 * 上下文对象的引用
	 */
	private Activity context;

	/**
	 * Image Url的数组
	 */
	private String[] imageThumbUrls;

	/**
	 * GridView对象的应用
	 */
	private GridView mGridView;

	/**
	 * Image 下载器
	 */
	private ImageDownLoader mImageDownLoader;

	/**
	 * 记录是否刚打开程序，用于解决进入程序不滚动屏幕，不会下载图片的问题。
	 * 参考http://blog.csdn.net/guolin_blog/article/details/9526203#comments
	 */
	private boolean isFirstEnter = true;

	/**
	 * 一屏中第一个item的位置
	 */
	private int mFirstVisibleItem;

	/**
	 * 一屏中所有item的个数
	 */
	private int mVisibleItemCount;

	private LayoutInflater layoutInflater;

	private RelativeLayout.LayoutParams layoutParams;

	public ImageAdapter3(Activity context, GridView mGridView, String[] imageThumbUrls) {
		this.context = context;
		this.mGridView = mGridView;
		this.imageThumbUrls = imageThumbUrls;
		mImageDownLoader = new ImageDownLoader(context);
		mGridView.setOnScrollListener(this);
		layoutInflater = LayoutInflater.from(this.context);
		layoutParams = getLayoutParams();
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// 仅当GridView静止时才去下载图片，GridView滑动时取消所有正在下载的任务
		System.out.println("cccccccccccc");
		if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
			System.out.println("eeeeeeeeeeee");
			showImage(mFirstVisibleItem, mVisibleItemCount);
		} else {
			System.out.println("ddddddddddd");
			cancelTask();
		}

	}

	/**
	 * GridView滚动的时候调用的方法，刚开始显示GridView也会调用此方法
	 */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		mFirstVisibleItem = firstVisibleItem;
		mVisibleItemCount = visibleItemCount;
		// 因此在这里为首次进入程序开启下载任务。
		System.out.println("aaaaaaaaaaaaa");
		if (isFirstEnter && visibleItemCount > 0) {
			System.out.println("bbbbbbbbbbb");
			showImage(mFirstVisibleItem, mVisibleItemCount);
			isFirstEnter = false;
		}
	}

	@Override
	public int getCount() {
		System.out.println("总个数是" + imageThumbUrls.length);
		return imageThumbUrls.length;
	}

	@Override
	public Object getItem(int position) {
		return imageThumbUrls[position];
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
			convertView = layoutInflater.inflate(R.layout.image3_item, null);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
			viewHolder.image.setLayoutParams(layoutParams);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Bitmap bitmap = mImageDownLoader.showCacheBitmap(imageThumbUrls[position].replaceAll("[^\\w]", ""));
		if (bitmap != null) {
			System.out.println("position=" + position + "不为空");
			viewHolder.image.setImageBitmap(bitmap);
		} else {
			System.out.println("position=" + position + "为空");
			viewHolder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher));
		}
		return convertView;
	}

	private class ViewHolder {
		private ImageView image;
	}

	public RelativeLayout.LayoutParams getLayoutParams() {
		RelativeLayout.LayoutParams layoutParams = null;
		DisplayMetrics displayMetrics = new DisplayMetrics();
		this.context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int screenW = displayMetrics.widthPixels;
		layoutParams = new RelativeLayout.LayoutParams(screenW / 4, screenW / 4);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		return layoutParams;
	}

	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// ImageView mImageView;
	// final String mImageUrl = imageThumbUrls[position];
	// if (convertView == null) {
	// mImageView = new ImageView(context);
	// } else {
	// mImageView = (ImageView) convertView;
	// }
	//
	// mImageView.setLayoutParams(new GridView.LayoutParams(150, 150));
	// mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
	//
	// // 给ImageView设置Tag,这里已经是司空见惯了
	// mImageView.setTag(mImageUrl);
	//
	// /******************************* 去掉下面这几行试试是什么效果
	// ****************************/
	// Bitmap bitmap =
	// mImageDownLoader.showCacheBitmap(mImageUrl.replaceAll("[^\\w]", ""));
	// // Bitmap bitmap = mImageDownLoader.showCacheBitmap(mImageUrl);
	// if (bitmap != null) {
	// System.out.println("position=" + position + "不为空");
	// mImageView.setImageBitmap(bitmap);
	// } else {
	// System.out.println("position=" + position + "为空");
	// mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher));
	// }
	// /**********************************************************************************/
	// return mImageView;
	// }

	/**
	 * 显示当前屏幕的图片，先会去查找LruCache，LruCache没有就去sd卡或者手机目录查找，在没有就开启线程去下载
	 * 
	 * @param firstVisibleItem
	 * @param visibleItemCount
	 */
	private void showImage(int firstVisibleItem, int visibleItemCount) {
		Bitmap bitmap = null;
		for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++) {
			String mImageUrl = imageThumbUrls[i];
			final ImageView mImageView = (ImageView) mGridView.findViewWithTag(mImageUrl);
			bitmap = mImageDownLoader.downloadImage(mImageUrl, new onImageLoaderListener() {

				@Override
				public void onImageLoader(Bitmap bitmap, String url) {
					if (mImageView != null && bitmap != null) {
						mImageView.setImageBitmap(bitmap);
					}

				}
			});

			// TODO?
			// if (bitmap != null) {
			// mImageView.setImageBitmap(bitmap);
			// } else {
			// mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher));
			// }
		}
	}

	/**
	 * 取消下载任务
	 */
	public void cancelTask() {
		mImageDownLoader.cancelTask();
	}

}
