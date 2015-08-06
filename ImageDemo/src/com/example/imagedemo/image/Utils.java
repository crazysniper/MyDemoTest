package com.example.imagedemo.image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

public class Utils {
	/**
	 * 获取版本号
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		String version = "0.0";
		try {
			// 获取packagemanager的实例
			PackageManager packageManager = context.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			version = packInfo.versionName;
		} catch (Exception e) {
		}
		return version;
	}

	/**
	 * 取出配置中的数据，并转成整形
	 * 
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int readIntSharedPreferences(Context context, String key, int defaultValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("pregnancytime", Context.MODE_MULTI_PROCESS);
		String value = sharedPreferences.getString(key, "");
		if ("".equals(value)) {
			return defaultValue;
		}
		return Integer.parseInt(value);
	}

	/**
	 * 指定key读配置
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getSharedPreferences(Context context, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("pregnancytime", Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, "");
	}

	/**
	 * 指定默认值读取key
	 * 
	 * @param context
	 * @param key
	 * @param defaultString
	 *            默认字符串
	 * @return
	 */
	public static String getSharedPreferencesDefault(Context context, String key, String defaultString) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("pregnancytime", Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, defaultString);
	}

	/**
	 * 保存value
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveSharedPreferences(Context context, String key, String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("pregnancytime", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value).commit();
	}

	/**
	 * 指定baby，默认值读取key
	 * 
	 * @param context
	 * @param babyId
	 * @param key
	 * @param defaultString
	 *            默认字符串
	 * @return
	 */
	public static String getBabySharedPreferencesDefault(Context context, String babyId, String key, String defaultString) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("pregnancytime_" + babyId, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, defaultString);
	}

	/**
	 * 指定baby，保存value
	 * 
	 * @param context
	 * @param babyId
	 * @param key
	 * @param value
	 */
	public static void saveBabySharedPreferences(Context context, String babyId, String key, String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("pregnancytime_" + babyId, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value).commit();
	}

	/**
	 * 判断sd卡是否存在
	 * 
	 * @return true:存在；false：不存在
	 */
	public static boolean isSdcardExisting() {
		final String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否是平板
	 * 
	 * @param context
	 * @return true:平板；false：手机
	 */
	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	/**
	 * 弹出输入框
	 * 
	 * @param context
	 * @param view
	 * @param show_type
	 *            true:显示输入框；false：隐藏
	 */
	public static void showInputMethod(Context context, View view, boolean show_type) {
		InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (show_type) {
			im.showSoftInput(view, 0);
		} else {
			im.hideSoftInputFromWindow(view.getWindowToken(), 0); // 强制隐藏键盘
		}
	}

	public static void copyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 sp 的单位 转成为 px
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 将String转成int
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}


	/**
	 * 保存选择item
	 * 
	 * @param fileName
	 * @param content
	 * @param context
	 */
	public static void saveSelectIds(Context context, String fileName, String content) {
		try {
			FileOutputStream outputStream = context.openFileOutput(fileName, Activity.MODE_PRIVATE);
			outputStream.write(content.getBytes());
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取保存在缓存中的帖子已读id
	 * 
	 * @param fileName
	 * @param context
	 * @return
	 */
	public static String readSelectIds(Context context, String fileName) {
		try {
			FileInputStream inputStream = context.openFileInput(fileName);
			byte[] bytes = new byte[1024 * 3];
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			while (inputStream.read(bytes) != -1) {
				arrayOutputStream.write(bytes, 0, bytes.length);
			}
			inputStream.close();
			arrayOutputStream.close();
			String content = new String(arrayOutputStream.toByteArray());

			String lists[] = content.split(",");
			String lString = "";
			int start = 0;
			if (lists.length > 100) {
				start = lists.length - 100;// 记录最近100条post_id
				cleanFile(fileName, context);
			}
			for (int j = start; j < lists.length - 1; j++) {
				if (j == 0) {
					lString = lists[j];
				} else {
					lString = lString + ',' + lists[j];
				}
			}
			return lString;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 清空指定文件
	 * 
	 * @param fileName
	 */
	private static void cleanFile(String fileName, Context context) {
		String cleanStr = "";
		try {
			FileOutputStream outStream = context.openFileOutput(fileName, Context.MODE_WORLD_READABLE);
			outStream.write(cleanStr.getBytes());
			outStream.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	/**
	 * 获取context宽高
	 */
	public static Point getDeviceSize(Context ctx) {
		DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
		Point size = new Point();
		size.x = dm.widthPixels;
		size.y = dm.heightPixels;
		return size;
	}

	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 旋转图片
	 * 
	 * @param angle
	 *            >0:顺时针；<0:逆时针
	 * @param bitmap
	 *            原图片
	 * @return
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * @author zhangxuefang
	 * @description 保存已选图片路径
	 * @since 2014/09/19
	 */
	public static void saveSelectedImags(Context context, List<String> imgList) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("pregnancytime_imgs", Context.MODE_PRIVATE);
		// 存储数据
		SharedPreferences.Editor editor = sharedPreferences.edit();
		if (imgList == null) {
			return;
		}
		StringBuffer result = new StringBuffer("");
		for (String item : imgList) {
			result.append(item).append(",");
		}
		editor.putString("selected_images", result.toString());
		editor.commit();
	}

	/**
	 * @author zhangxuefang
	 * @description 读取已选图片路径
	 * @since 2014/09/19
	 */
	public static List<String> getSeletedImages(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("pregnancytime_imgs", Context.MODE_MULTI_PROCESS);
		String images = sharedPreferences.getString("selected_images", "");
		List<String> list = new ArrayList<String>();
		if (TextUtils.isEmpty(images)) {
			return list;
		}
		String[] imgArr = images.split(",");
		for (String item : imgArr) {
			if (TextUtils.isEmpty(item)) {
				continue;
			}
			list.add(item);
		}
		return list;
	}

	/**
	 * 清空保存的选择图片路径
	 * 
	 * @param context
	 */
	public static void clearSelectedImages(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("pregnancytime_imgs", Context.MODE_MULTI_PROCESS);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.clear().commit();
	}

	/**
	 * @author zhangxuefang
	 * @description 保存已选图片拍摄时间
	 * @since 2014/09/19
	 */
	public static void saveSelectedImagsTime(Context context, List<String> imgList) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("pregnancytime_imgs_time", Context.MODE_PRIVATE);
		// 存储数据
		SharedPreferences.Editor editor = sharedPreferences.edit();
		if (imgList == null) {
			return;
		}
		StringBuffer result = new StringBuffer("");
		for (String item : imgList) {
			result.append(item).append(",");
		}
		editor.putString("selected_images_time", result.toString());
		editor.commit();
	}

	/**
	 * @author zhangxuefang
	 * @description 读取已选图片时间
	 * @since 2014/09/19
	 */
	public static List<String> getSeletedImagesTime(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("pregnancytime_imgs_time", Context.MODE_MULTI_PROCESS);
		String images = sharedPreferences.getString("selected_images_time", "");
		List<String> list = new ArrayList<String>();
		if (TextUtils.isEmpty(images)) {
			return list;
		}
		String[] imgArr = images.split(",");
		for (String item : imgArr) {
			if (TextUtils.isEmpty(item)) {
				continue;
			}
			list.add(item);
		}
		return list;
	}

	/**
	 * 清空保存的选择图片时间
	 * 
	 * @param context
	 */
	public static void clearSelectedImagesTime(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("pregnancytime_imgs_time", Context.MODE_MULTI_PROCESS);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.clear().commit();
	}
	
	
	
	
	/**
	 * 获取当前网络连接的类型信息
	 */
	public static int getConnectedType(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
				return mNetworkInfo.getType();
			}
		}
		return -1;
	}

	/**
	 * 保存图片到sd卡
	 * 
	 * @param bitmap
	 * @return 图片名
	 */
	public static String saveImageToSdcard(Bitmap bitmap) {
		String file_name = null;
		try {
			File file = new File("/mnt/sdcard/babytime/pregnancywap");
			if (!file.exists()) {
				file.mkdir();
			}

			String name = "pic" + System.currentTimeMillis() + Math.random();
			file_name = "/mnt/sdcard/babytime/pregnancywap/" + name + ".jpg";

			FileOutputStream out = new FileOutputStream(file_name);
			if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return file_name;
	}
}
