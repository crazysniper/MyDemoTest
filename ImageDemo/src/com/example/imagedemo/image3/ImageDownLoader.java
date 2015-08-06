package com.example.imagedemo.image3;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;

public class ImageDownLoader {
	/**
	 * 缓存Image的类，当存储Image的大小大于LruCache设定的值，系统自动释放内存
	 */
	private LruCache<String, Bitmap> mMemoryCache;
	/**
	 * 操作文件相关类对象的引用
	 */
	private FileUtils fileUtils;
	/**
	 * 下载Image的线程池
	 */
	private ExecutorService mImageThreadPool = null;

	public ImageDownLoader(Context context) {
		// 获取系统分配给每个应用程序的最大内存，每个应用系统分配32M
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int mCacheSize = maxMemory / 8;
		// 给LruCache分配1/8 4M
		mMemoryCache = new LruCache<String, Bitmap>(mCacheSize) {

			// 必须重写此方法，来测量Bitmap的大小,默认返回图片的数量
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}

		};

		fileUtils = new FileUtils(context);
	}

	/**
	 * 获取线程池的方法，因为涉及到并发的问题，我们加上同步锁
	 * 
	 * @return
	 */
	public ExecutorService getThreadPool() {
		if (mImageThreadPool == null) {
			synchronized (ExecutorService.class) {
				if (mImageThreadPool == null) {
					// 为了下载图片更加的流畅，我们用了2个线程来下载图片
					mImageThreadPool = Executors.newFixedThreadPool(2);
				}
			}
		}

		return mImageThreadPool;

	}

	/**
	 * 添加Bitmap到内存缓存
	 * 
	 * @param key
	 * @param bitmap
	 */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null && bitmap != null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	/**
	 * 从内存缓存中获取一个Bitmap
	 * 
	 * @param key
	 * @return
	 */
	public Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}

	/**
	 * 先从内存缓存中获取Bitmap,如果没有就从SD卡或者手机缓存中获取，SD卡或者手机缓存 没有就去下载<br>
	 * 先去LruCache查看Image,没有再去手机缓存中查看，在没有则开启线程下载，这里我们提供了一个回调接口，
	 * 回调方法中我们将Bitmap和图片Url作为参数，String subUrl = url.replaceAll("[^\\w]", "")
	 * 我在代码中注释写的比较清楚
	 * 
	 * @param url
	 * @param listener
	 * @return
	 */
	public Bitmap downloadImage(final String url, final onImageLoaderListener listener) {
		// 替换Url中非字母和非数字的字符，这里比较重要，因为我们用Url作为文件名，比如我们的Url
		// 是Http://xiaanming/abc.jpg;用这个作为图片名称，系统会认为xiaanming为一个目录，
		// 我们没有创建此目录保存文件就会报错
		// TODO
		 final String subUrl = url.replaceAll("[^\\w]", "");
//		final String subUrl = url;
		Bitmap bitmap = showCacheBitmap(subUrl);
		if (bitmap != null) {
			return bitmap;
		} else {

			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					listener.onImageLoader((Bitmap) msg.obj, url);
				}
			};

			getThreadPool().execute(new Runnable() {

				@Override
				public void run() {
					Bitmap bitmap = getBitmapFormUrl(url);
					Message msg = handler.obtainMessage();
					msg.obj = bitmap;
					handler.sendMessage(msg);

					try {
						System.out.println("保存");
						// 保存在SD卡或者手机目录
						fileUtils.savaBitmap(subUrl, bitmap);
					} catch (IOException e) {
						e.printStackTrace();
					}

					// 将Bitmap 加入内存缓存
					addBitmapToMemoryCache(subUrl, bitmap);
				}
			});
		}

		return null;
	}

	/**
	 * 获取Bitmap, 内存中没有就去手机或者sd卡中获取，这一步在getView中会调用，比较关键的一步
	 * 
	 * @param url
	 * @return
	 */
	public Bitmap showCacheBitmap(String url) {
		if (getBitmapFromMemCache(url) != null) {
			System.out.println("111111111111");
			return getBitmapFromMemCache(url);
		} else if (fileUtils.isFileExists(url) && fileUtils.getFileSize(url) != 0) {
			// 从SD卡获取手机里面获取Bitmap
			Bitmap bitmap = fileUtils.getBitmap(url);
			System.out.println("222222222222");
			// 将Bitmap 加入内存缓存
			addBitmapToMemoryCache(url, bitmap);
			return bitmap;
		}
		System.out.println("333333333333");
		return null;
	}

	/**
	 * 从Url中获取Bitmap
	 * 
	 * @param url
	 * @return
	 */
	private Bitmap getBitmapFormUrl(String url) {
		Bitmap bitmap = null;
		HttpURLConnection con = null;
		try {
			URL mImageUrl = new URL(url);
			con = (HttpURLConnection) mImageUrl.openConnection();
			// con.setConnectTimeout(10 * 1000);
			// con.setReadTimeout(10 * 1000);
			con.setConnectTimeout(30000);
			con.setReadTimeout(30000);
			// con.setDoInput(true);
			// con.setDoOutput(true);
			bitmap = BitmapFactory.decodeStream(con.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
		System.out.println("url=" + url);
		if (bitmap == null) {
			System.out.println("bitmap为空");
		} else {
			System.out.println("bitmap不为空");
		}
		return bitmap;
	}

	/**
	 * 取消正在下载的任务
	 */
	public synchronized void cancelTask() {
		if (mImageThreadPool != null) {
			mImageThreadPool.shutdownNow();
			mImageThreadPool = null;
		}
	}

	/**
	 * 异步下载图片的回调接口
	 * 
	 * @author len
	 * 
	 */
	public interface onImageLoaderListener {
		void onImageLoader(Bitmap bitmap, String url);
	}

}
