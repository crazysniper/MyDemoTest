package com.gftest.myappclient.imageloader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import libcore.io.DiskLruCache;
import libcore.io.DiskLruCache.Snapshot;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gftest.myappclient.bgshow.InputStreamWrapper;
import com.gftest.myappclient.bgshow.InputStreamWrapper.InputStreamProgressListener;
import com.gftest.myappclient.constants.Constants;
import com.gftest.myappclient.utils.HttpUtils;
import com.gftest.myappclient.utils.ImageUtils;
import com.gftest.myappclient.utils.ToastUtils;
import com.gftest.myappclient.utils.Utils;

/**
 * 
 * @author Gao
 * 
 */
public class ImageLoaderNew {
	private MyLruCache mMemoryCache;
	private ExecutorService executorService = null;
	private Context context;
	private Handler handler = new Handler();
	private int screenW = 0;
	private DiskLruCache diskLruCache;
	private static ImageLoaderNew instance = null;

	private ImageLoaderNew(Context context) {
		this.context = context;
		mMemoryCache = MyLruCache.get(this.context);
		executorService = getThreadPool();
		screenW = Utils.getDeviceSize(context).x;

		try {
			File cacheDir = Utils.getDiskCacheDir(context, "bitmap");
			diskLruCache = DiskLruCache.open(cacheDir, Utils.getAppVersion(context), 1, Constants.maxSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static synchronized ImageLoaderNew getInstance(Context context) {
		if (instance == null) {
			instance = new ImageLoaderNew(context);
		}
		return instance;
	}

	/**
	 * 获取线程池的方法，因为涉及到并发的问题，我们加上同步锁
	 * 
	 * @return
	 */
	public ExecutorService getThreadPool() {
		if (executorService == null) {
			synchronized (ExecutorService.class) {
				if (executorService == null) {
					// 为了下载图片更加的流畅，我们用了2个线程来下载图片
					executorService = Executors.newFixedThreadPool(5);
				}
			}
		}
		return executorService;
	}

	/**
	 * 显示图片
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
	 * @param marginType
	 *            只有在isBig是true的时候，才起作用 。marginType = 1:首页时光轴显示1张图片 ；2:
	 *            postdetail,时光轴状态详情；0:默认
	 */
	public void displayImage(String url, ImageView imageView, int defaultId, boolean isCorner, boolean isBig, int marginType) {
		checkDirExists();
		Bitmap bitmap = showCacheBitmap(url);
		if (bitmap != null) {
			if (isCorner) {
				bitmap = ImageUtils.toRoundCorner(bitmap, 10, true);
			} else if (isBig) {
				int width = Utils.getDeviceSize(context).x;
				int REQUIRED_SIZE = width / 3 * 2;
				bitmap = ImageUtils.zoomImg(bitmap, REQUIRED_SIZE);
				resizeImageView(imageView, bitmap, getImgType(url), marginType);
			}
			imageView.setImageBitmap(bitmap);
		} else {
			Bitmap defaultBitmap = ImageUtils.readBitmap(context, defaultId);
			if (isCorner) {
				defaultBitmap = ImageUtils.toRoundCorner(defaultBitmap, 10, true);
			} else if (isBig) {
				resizeImageView(imageView, defaultBitmap, getImgType(url), marginType);
			}
			imageView.setImageBitmap(defaultBitmap);
			queuePhoto(url, imageView, isCorner, isBig, marginType);
		}
	}

	/**
	 * 显示大图
	 * 
	 * @param url
	 * @param imageView
	 * @param defaultId
	 * @param mProgressBar
	 */
	public void displayBigImg(String url, ImageView imageView, int defaultId, ProgressBar mProgressBar) {
		checkDirExists();
		Bitmap bitmap = showCacheBitmap(url);
		if (bitmap != null) {
			imageView.setScaleType(ScaleType.MATRIX);
			imageView.setImageBitmap(bitmap);
			imageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.GONE);
			return;
		}
		BigImageEntity bigEntity = new BigImageEntity(url, imageView, defaultId, mProgressBar);
		new BigImageLoadTask(bigEntity).execute();
	}

	/**
	 * 大图Entity
	 * 
	 * @author Gao
	 * 
	 */
	public class BigImageEntity {
		private String url;
		private ImageView imageView;
		private int defaultId;
		private ProgressBar mProgressBar;

		public BigImageEntity(String url, ImageView imageView, int defaultId, ProgressBar mProgressBar) {
			this.url = url;
			this.imageView = imageView;
			this.defaultId = defaultId;
			this.mProgressBar = mProgressBar;
		}
	}

	public class BigImageLoadTask extends AsyncTask<BigImageEntity, Integer, Bitmap> {

		private BigImageEntity bigEntity;

		public BigImageLoadTask(BigImageEntity bigEntity) {
			this.bigEntity = bigEntity;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Bitmap doInBackground(BigImageEntity... params) {
			if (!HttpUtils.isNetworkConnected(context)) {
				handler2.sendEmptyMessage(1);
				return null;
			}
			return getBigBitmapFromUr(bigEntity);
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			bigEntity.imageView.setVisibility(View.VISIBLE);
			bigEntity.mProgressBar.setVisibility(View.GONE);
			if (bitmap == null) {
				bigEntity.imageView.setScaleType(ScaleType.CENTER);
				bitmap = BitmapFactory.decodeResource(context.getResources(), bigEntity.defaultId);
				bigEntity.imageView.setImageBitmap(bitmap);
				try {
					if (bitmap != null && !bitmap.isRecycled()) {
						bitmap = null;
					}
				} catch (Exception e) {
				}
				return;
			}
			bigEntity.imageView.setScaleType(ScaleType.MATRIX);
			bigEntity.imageView.setImageBitmap(bitmap);
			try {
				if (bitmap != null && !bitmap.isRecycled()) {
					bitmap = null;
				}
			} catch (Exception e) {
			}
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			bigEntity.mProgressBar.setProgress(values[0]);
		}

		/**
		 * 获取大图片
		 * 
		 * @param url
		 * @param isBig
		 * @return
		 */
		private Bitmap getBigBitmapFromUr(BigImageEntity bigEntity) {
			return downloadBigImageUrlToStream(bigEntity.url);
		}

		/**
		 * 获取大图片，建立HTTP请求，并获取Bitmap对象。
		 * 
		 * @param imageUrl
		 *            大图片的URL地址
		 * @return 解析后的Bitmap对象
		 */
		private Bitmap downloadBigImageUrlToStream(String urlString) {
			HttpURLConnection con = null;
			BufferedOutputStream out = null;
			BufferedInputStream in = null;
			InputStream inputStream = null;
			InputStream inputStream2 = null;
			InputStreamWrapper bis = null;
			String key = hashKeyForDisk(urlString);
			Bitmap bitmap = null;
			try {
				final URL url = new URL(urlString);
				con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(30000);
				con.setReadTimeout(30000);
				con.setInstanceFollowRedirects(true);

				con.connect();
				inputStream = con.getInputStream();
				int totalLen = con.getContentLength();
				bis = new InputStreamWrapper(inputStream, 8192, totalLen);
				bis.setProgressListener(new InputStreamProgressListener() {
					@Override
					public void onProgress(float progressValue, long bytesLoaded, long bytesTotal) {
						publishProgress((int) (progressValue * 100));
					}
				});

				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inDither = false;
				options.inPreferredConfig = Config.RGB_565;
				options.inPurgeable = true;
				options.inInputShareable = true;
				int scale = 1;
				scale = computeSampleSize(options, -1, 240 * 300);
				options.inSampleSize = scale;
				try {
					byte[] data = ImageUtils.readStream(bis);
					bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// bitmap = BitmapFactory.decodeStream(bis, null, options);
				if (bitmap != null) {
					mMemoryCache.addBitmapToMemoryCache(key, bitmap);
				}

				DiskLruCache.Editor editor = diskLruCache.edit(key);
				if (editor != null) {
					inputStream2 = bitmap2InputStream(bitmap, urlString);
					in = new BufferedInputStream(inputStream2, 8 * 1024);

					OutputStream outputStream = editor.newOutputStream(0);
					out = new BufferedOutputStream(outputStream, 8 * 1024);
					int b;
					while ((b = in.read()) != -1) {
						out.write(b);
					}
					if (bitmap != null) {
						editor.commit();
					} else {
						editor.abort();
					}
				}
				diskLruCache.flush();
				return bitmap;
			} catch (final IOException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					con.disconnect();
				}
				try {
					if (bis != null) {
						bis.close();
					}
					if (inputStream != null) {
						inputStream.close();
					}
					if (inputStream2 != null) {
						inputStream2.close();
					}
					if (in != null) {
						in.close();
					}
					if (out != null) {
						out.close();
					}
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
			return bitmap;
		}
	}

	// public Bitmap getBigBitmap(String urlString) {
	// Bitmap bitmap = null;
	// HttpURLConnection urlConnection = null;
	// InputStream inputStream = null;
	// try {
	// URL url = new URL(urlString);
	// try {
	// urlConnection = (HttpURLConnection) url.openConnection();
	// urlConnection.connect();
	// inputStream = urlConnection.getInputStream();
	// BitmapFactory.Options o2 = new BitmapFactory.Options();
	// o2.inDither = false;
	// o2.inPreferredConfig = Config.RGB_565;
	// o2.inPurgeable = true;
	// o2.inInputShareable = true;
	// int scale = 1;
	// scale = computeSampleSize(o2, 720f, 1280f);
	// o2.inSampleSize = scale;
	// bitmap = BitmapFactory.decodeStream(inputStream, null, o2);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// } finally {
	// if (urlConnection != null) {
	// urlConnection.disconnect();
	// }
	// try {
	// if (inputStream != null) {
	// inputStream.close();
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// return bitmap;
	// }
	/**
	 * 下载网络图片
	 * 
	 * @param url
	 * @param imageView
	 * @param isCorner
	 * @param isBig
	 * @param marginType
	 * @param postion
	 */
	private void queuePhoto(String url, ImageView imageView, boolean isCorner, boolean isBig, int marginType) {
		PhotoEntity photoEntity = new PhotoEntity(url, imageView, isCorner, isBig, marginType);
		executorService.submit(new PhotosLoader(photoEntity));
	}

	/**
	 * 图片entity
	 * 
	 * 
	 */
	private class PhotoEntity {
		private String url;
		private ImageView imageView;
		private boolean isCorner;
		private boolean isBig;
		private int marginType;

		public PhotoEntity(String u, ImageView i, boolean c, boolean b, int marginType) {
			this.url = u;
			this.imageView = i;
			this.isCorner = c;
			this.isBig = b;
			this.marginType = marginType;
		}
	}

	private class PhotosLoader implements Runnable {
		private PhotoEntity photoEntity;

		public PhotosLoader(PhotoEntity photoEntity) {
			this.photoEntity = photoEntity;
		}

		@Override
		public void run() {
			if (!HttpUtils.isNetworkConnected(context)) {
				handler2.sendEmptyMessage(1);
				return;
			}
			Bitmap bitmap = getBitmapFromUrl(photoEntity);
			if (bitmap == null) {
				return;
			}
			BitmapDisplayer bitmapDisplayer = new BitmapDisplayer(bitmap, photoEntity);
			handler.post(bitmapDisplayer);
		}
	}

	/**
	 * 获取图片
	 * 
	 * @param url
	 * @param isBig
	 * @return
	 */
	private Bitmap getBitmapFromUrl(PhotoEntity photoEntity) {
		return downloadUrlToStream(photoEntity.url, photoEntity.isBig);
	}

	/**
	 * 正常的加载图片，并获取Bitmap对象。
	 * 
	 * @param urlString
	 *            图片的URL地址
	 * @param isBig
	 * @return
	 */
	private Bitmap downloadUrlToStream(String urlString, boolean isBig) {
		HttpURLConnection con = null;
		BufferedOutputStream out = null;
		BufferedInputStream in = null;
		InputStream inputStream = null;
		InputStream inputStream2 = null;
		String key = hashKeyForDisk(urlString);
		Bitmap bitmap = null;
		try {
			URL url = new URL(urlString);
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(30000);
			con.setReadTimeout(30000);
			con.setInstanceFollowRedirects(true);

			con.connect();
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inDither = false;
			// options.inPreferredConfig = Config.RGB_565;
			options.inPreferredConfig = null;
			options.inPurgeable = true;
			options.inInputShareable = true;
			int scale = 1;
			// 获取到网络图片后，根据是否大图设置缩放比例
			if (!isBig) {
				// scale = computeSampleSize(options, 70f, 70f);
				scale = computeSampleSize(options, -1, 70 * 70);
			} else {
				// scale = computeSampleSize(options, 720f, 1280f);
				scale = computeSampleSize(options, -1, 240 * 300);
			}
			options.inSampleSize = scale;
			inputStream = con.getInputStream();
			try {
				byte[] data = ImageUtils.readStream(inputStream);
				bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// bitmap = BitmapFactory.decodeStream(inputStream, null, options);
			if (bitmap != null) {
				mMemoryCache.addBitmapToMemoryCache(key, bitmap);
			}
			DiskLruCache.Editor editor = diskLruCache.edit(key);
			if (editor != null) {
				inputStream2 = bitmap2InputStream(bitmap, urlString);
				in = new BufferedInputStream(inputStream2, 8 * 1024);
				OutputStream outputStream = editor.newOutputStream(0);

				out = new BufferedOutputStream(outputStream, 8 * 1024);
				int b;
				while ((b = in.read()) != -1) {
					out.write(b);
				}
				if (bitmap != null) {
					editor.commit();
				} else {
					editor.abort();
				}
			}
			diskLruCache.flush();
			return bitmap;
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (inputStream2 != null) {
					inputStream2.close();
				}
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	private class BitmapDisplayer implements Runnable {
		private Bitmap bitmap;
		private PhotoEntity photoEntity;

		public BitmapDisplayer(Bitmap bitmap, PhotoEntity entity) {
			this.bitmap = bitmap;
			this.photoEntity = entity;
		}

		@Override
		public void run() {
			if (bitmap == null) {
				return;
			}
			if (photoEntity.isCorner) {
				bitmap = ImageUtils.toRoundCorner(bitmap, 10, true);
			} else if (photoEntity.isBig) {
				int width = Utils.getDeviceSize(context).x;
				int REQUIRED_SIZE = width / 3 * 2;
				bitmap = ImageUtils.zoomImg(bitmap, REQUIRED_SIZE);
				resizeImageView(photoEntity.imageView, bitmap, getImgType(photoEntity.url), photoEntity.marginType);
			}
			photoEntity.imageView.setImageBitmap(bitmap);
			// AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
			// alphaAnimation.setDuration(500);
			// photoEntity.imageView.startAnimation(alphaAnimation);

			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap = null;
			}
		}
	}

	/**
	 * 将Bitmap转换成InputStream
	 * 
	 * @param bm
	 * @return
	 */
	public InputStream bitmap2InputStream(Bitmap bm, String url) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (!url.substring(url.lastIndexOf(".") + 1).toUpperCase().contains("PNG")) {
			bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		} else {
			bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		}
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	/**
	 * 获取Bitmap, 内存中没有就去手机或者sd卡中获取，这一步在getView中会调用，比较关键的一步
	 * 
	 * @param url
	 * @return
	 */
	public Bitmap showCacheBitmap(String url) {
		String code = hashKeyForDisk(url);
		Bitmap bitmap1 = mMemoryCache.getBitmapFromMemCache(code);
		if (bitmap1 != null) {
			return bitmap1;
		}
		Bitmap bitmap2 = getBitmapFromDiskCache(code);
		if (bitmap2 != null) {
			return bitmap2;
		}
		return null;
	}

	/**
	 * 从文件中加载
	 * 
	 * @param key
	 * @return
	 */
	private Bitmap getBitmapFromDiskCache(String key) {
		FileDescriptor fileDescriptor = null;
		FileInputStream fileInputStream = null;
		Snapshot snapshot = null;
		try {
			snapshot = diskLruCache.get(key);
			if (snapshot == null) {
				return null;
			}
			fileInputStream = (FileInputStream) snapshot.getInputStream(0);
			fileDescriptor = fileInputStream.getFD();
			Bitmap bitmap = null;
			if (fileDescriptor != null) {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inDither = false;
				options.inPreferredConfig = Config.RGB_565;
				options.inPurgeable = true;
				options.inInputShareable = true;
				options.inSampleSize = 1;
				bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
			}
			if (bitmap != null) {
				mMemoryCache.addBitmapToMemoryCache(key, bitmap);
			}
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileDescriptor == null && fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * 设置图片的宽度、高度<br>
	 * 当isBig是true的时候执行
	 * 
	 * @param iv
	 * @param bm
	 * @param type
	 * @param marginType
	 */
	private void resizeImageView(ImageView iv, Bitmap bm, int type, int marginType) {
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
		} else if (marginType == 1) {// 首页时光轴显示一张图片
			width = screenW - Utils.dip2px(context, 24) * 2;
			height = width * bm.getHeight() / bm.getWidth();
		} else if (marginType == 2) {
			width = screenW - Utils.dip2px(context, 10) * 2;
			height = width * bm.getHeight() / bm.getWidth();
		} else {
			width = bm.getWidth();
			height = bm.getHeight();
		}
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
		if (marginType == 1) {
			lp.setMargins(Utils.dip2px(context, 14), Utils.dip2px(context, 10), Utils.dip2px(context, 10), Utils.dip2px(context, 10));
		} else if (marginType == 2) {
			lp.setMargins(0, 0, 0, Utils.dip2px(context, 10));
		} else {
			lp.setMargins(Utils.dip2px(context, 10), Utils.dip2px(context, 10), Utils.dip2px(context, 10), Utils.dip2px(context, 10));
		}

		if (type == 2 || type == 3) {
			lp.gravity = Gravity.LEFT;
		} else {
			lp.gravity = Gravity.CENTER;
		}
		iv.setLayoutParams(lp);
	}

	/**
	 * 判断快速回复表情的返回类型
	 * 
	 * @param url
	 * @return
	 */
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

	// /**
	// * 计算缩放比例
	// *
	// * @param options
	// * @return
	// */
	// public static int computeSampleSize(BitmapFactory.Options options, float
	// ww, float hh) {
	// int w = options.outWidth;
	// int h = options.outHeight;
	// // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
	// int be = 1;// be=1表示不缩放
	// if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
	// be = (int) (options.outWidth / ww);
	// } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
	// be = (int) (options.outHeight / hh);
	// }
	// if (be <= 0)
	// be = 1;
	// return be;
	// }

	public int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
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

	private int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
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

	/**
	 * 使用MD5算法对传入的key进行加密并返回。
	 */
	public String hashKeyForDisk(String key) {
		String cacheKey;
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}

	private String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	public synchronized boolean checkDirExists() {
		File file = diskLruCache.getDirectory();
		if (file != null) {
			if (!file.exists()) {
				return file.mkdirs();
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	@SuppressLint("HandlerLeak")
	Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				ToastUtils.getInstance().showToast(context, "网络异常，请检查网络~", Toast.LENGTH_LONG);
				break;
			}
		}
	};
}
