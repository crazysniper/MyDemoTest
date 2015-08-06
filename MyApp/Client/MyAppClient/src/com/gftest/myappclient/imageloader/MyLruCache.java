package com.gftest.myappclient.imageloader;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 
 * @author Gao
 * 
 */
public class MyLruCache extends LruCache<String, Bitmap> {
	private static MyLruCache instance;

	private MyLruCache(int maxSize) {
		super(maxSize);
	}

	public static MyLruCache get(Context context) {
		if (instance == null) {
			int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
			memClass = memClass > 32 ? 32 : memClass;
			// 使用可用内存的1/8作为图片缓存
			final int cacheSize = 1024 * 1024 * memClass / 8;
			instance = new MyLruCache(cacheSize);
			// final int maxMemory = (int) (Runtime.getRuntime().maxMemory()) /
			// 1024;
			// System.out.println("内存大小=" + maxMemory);
			// instance = new Example(maxMemory);
		}
		return instance;
	}

	/**
	 * 计算存储bitmap所占用的字节数
	 */
	@Override
	protected int sizeOf(final String key, final Bitmap value) {
		return value.getRowBytes() * value.getHeight();
	}

	/**
	 * 添加Bitmap到内存缓存
	 * 
	 * @param key
	 *            hasCode
	 * @param bitmap
	 */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		// if (getBitmapFromMemCache(key) == null && bitmap != null) {
		// Bitmap bitmap2 = put(key, bitmap);
		// if (bitmap2 != null) {
		// bitmap2.recycle();
		// }
		// }
		synchronized (instance) {
			put(key, bitmap);
		}
	}

	/**
	 * 从内存缓存中获取一个Bitmap
	 * 
	 * @param key
	 *            hasCode
	 * @return
	 */
	public Bitmap getBitmapFromMemCache(String key) {
		// Bitmap bitmap;
		// synchronized (instance) {
		// bitmap = get(key);
		// if (bitmap != null) {
		// // 如果找到的话，把元素移到LinkedHashMap的最前面，从而保证在LRU算法中是最后被删除
		// remove(key);
		// put(key, bitmap);
		// return bitmap;
		// }
		// }
		// return null;
		return get(key);
	}

	/**
	 * 清除
	 */
	public void clear() {
		if (instance != null) {
			if (instance.size() > 0) {
				instance.evictAll();
			}
		}
	}

	/**
	 * 移除指定缓存
	 * 
	 * @param key
	 */
	public void removeBitmapFromMemCache(String key) {
		if (key != null) {
			if (instance != null) {
				Bitmap bm = instance.remove(key);
				if (bm != null)
					bm.recycle();
			}
		}
	}

}
