package com.example.imagedemo.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 用于加载详情页图片
 * 
 * @author Administrator
 * 
 */
public class ImageLoader {

	MemoryCache memoryCache = new MemoryCache();
	FileCache fileCache;
	private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
	ExecutorService executorService;
	Handler handler = new Handler();// handler to display images in UI thread
	Context context;

	public ImageLoader(Context context) {
		this.context = context;
		fileCache = new FileCache(context);
		executorService = Executors.newFixedThreadPool(5);
	}

	/**
	 * 显示头像
	 * 
	 * @param url
	 *            头像url
	 * @param imageView
	 * @param defaultId
	 *            默认图片id
	 * @param isCorner
	 *            true:圆形，不执行isBig
	 * @param isBig
	 *            true：显示大图，当isCorner为false时，才起作用
	 */
	public void displayImage(String url, ImageView imageView, int defaultId, boolean isCorner, boolean isBig) {
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);

		/**
		 * 1.先从内存缓存中获取图片显示（内存缓冲） <br>
		 * 2.获取不到的话从SD卡里获取（SD卡缓冲） <br>
		 * 3.都获取不到的话从网络下载图片并保存到SD卡同时加入内存并显示（视情况看是否要显示）
		 */
		if (bitmap != null) {
			if (isCorner) {
				bitmap = ImageUtils.toRoundCorner(bitmap, 10, true);
			} else if (isBig) {
				resizeImageView(imageView, bitmap, getImgType(url));
			}
			imageView.setImageBitmap(bitmap);
		} else {
			queuePhoto(url, imageView, isCorner, isBig);

			Bitmap defaultBitmap = readBitMap(context, defaultId);
			if (isCorner) {
				defaultBitmap = ImageUtils.toRoundCorner(defaultBitmap, 10, true);
			} else if (isBig) {
				resizeImageView(imageView, defaultBitmap, getImgType(url));
			}
			imageView.setImageBitmap(defaultBitmap);
		}
	}

	private void queuePhoto(String url, ImageView imageView, boolean isCorner, boolean isBig) {
		PhotoToLoad p = new PhotoToLoad(url, imageView, isCorner, isBig);
		executorService.submit(new PhotosLoader(p));
	}

	private Bitmap getBitmap(String url, boolean isBig) {
		File f = fileCache.getFile(url);

		// from SD cache
		Bitmap b = decodeFile(f, isBig);
		if (b != null)
			return b;

		// from web
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			Utils.copyStream(is, os);
			os.close();
			conn.disconnect();
			bitmap = decodeFile(f, isBig);
			return bitmap;
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError)
				memoryCache.clear();
			return null;
		}
	}

	// decodes image and scales it to reduce memory consumption
	private Bitmap decodeFile(File f, boolean isBig) {
		try {

			int scale = 1;

			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inDither = false;
			o2.inPreferredConfig = null;
			o2.inPurgeable = true;
			o2.inInputShareable = true;

			if (!isBig) {
				scale = computeSampleSize(o2, -1, 70 * 70);
			} else {
				scale = computeSampleSize(o2, -1, 240 * 300);
			}

			o2.inSampleSize = scale;
			FileInputStream stream2 = new FileInputStream(f);
			Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
			stream2.close();

			if (isBig) {
				DisplayMetrics metric = new DisplayMetrics();
				((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
				int width = metric.widthPixels;// 屏幕宽度（像素）
				final int REQUIRED_SIZE = width / 3 * 2;
				Bitmap bm = ImageUtils.zoomImg(bitmap, REQUIRED_SIZE);
				try {
					if (bitmap != null && !bitmap.isRecycled()) {
						bitmap = null;
					}
				} catch (Exception e) {
				}
				return bm;
			} else {
				return bitmap;
			}

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;
		public boolean isCorner;
		public boolean isBig;

		public PhotoToLoad(String u, ImageView i, boolean c, boolean b) {
			url = u;
			imageView = i;
			isCorner = c;
			isBig = b;
		}
	}

	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run() {
			try {
				if (imageViewReused(photoToLoad)) {
					return;
				}
				Bitmap bmp = getBitmap(photoToLoad.url, photoToLoad.isBig);
				memoryCache.put(photoToLoad.url, bmp);
				if (imageViewReused(photoToLoad)) {
					return;
				}
				BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
				handler.post(bd);
			} catch (Throwable th) {
				th.printStackTrace();
			}
		}
	}

	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url)) {
			return true;
		}
		return false;
	}

	// Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		public void run() {
			if (imageViewReused(photoToLoad)) {
				return;
			}
			if (bitmap != null) {
				if (photoToLoad.isCorner) {
					bitmap = ImageUtils.toRoundCorner(bitmap, 10, true);
				} else if (photoToLoad.isBig) {
					resizeImageView(photoToLoad.imageView, bitmap, getImgType(photoToLoad.url));
				}
				photoToLoad.imageView.setImageBitmap(bitmap);

				Animation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
				alphaAnimation.setDuration(500);
				photoToLoad.imageView.startAnimation(alphaAnimation);

				if (bitmap != null && !bitmap.isRecycled()) {
					bitmap = null;
				}
			} else {
			}
		}
	}

	/**
	 * 当isBig是true的时候执行
	 * 
	 * @param iv
	 * @param bm
	 * @param type
	 */
	private void resizeImageView(ImageView iv, Bitmap bm, int type) {
		int width = 0, height = 0;
		if (type == 1) {
			width = Utils.dip2px(context, 12);
			height = Utils.dip2px(context, 12);
		} else if (type == 2) {
			width = Utils.dip2px(context, 58);
			height = Utils.dip2px(context, 20);
		} else if (type == 3) {
			width = Utils.dip2px(context, 76);
			height = Utils.dip2px(context, 42);
		} else {
			width = bm.getWidth();
			height = bm.getHeight();
		}
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
		lp.setMargins(Utils.dip2px(context, 10), Utils.dip2px(context, 10), Utils.dip2px(context, 10), Utils.dip2px(context, 10));
		if (type == 2 || type == 3) {
			lp.gravity = Gravity.LEFT;
		} else {
			lp.gravity = Gravity.CENTER;
		}
		iv.setLayoutParams(lp);
	}

	public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {

		int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	private Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	private int getImgType(String url) {
		int type = 0;

		if (url.indexOf("/emotion/") != -1) {
			type = 1;
		} else if (url.indexOf("/face/") != -1 && url.indexOf(".png") != -1) {
			type = 3;
		} else if (url.indexOf("/face/") != -1) {
			type = 2;
		} else {
			type = 0;
		}

		return type;
	}
}
