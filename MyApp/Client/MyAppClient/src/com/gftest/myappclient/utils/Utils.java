package com.gftest.myappclient.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.os.Environment;
import android.util.DisplayMetrics;

public class Utils {

	private static Utils utils;
	private static SharedPreferences.Editor editor;

	private Utils() {

	}

	public synchronized static Utils getInstance() {
		if (utils == null) {
			utils = new Utils();
		}
		return utils;
	}

	public static String getSharedPreferences(Context context, String key, String defaultValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("myappclient", Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, defaultValue);
	}

	public static void saveSharedPreferences(Context context, String key, String value) {
		if (editor == null) {
			setEditor(context);
		}
		editor.putString(key, value).commit();
	}

	public static void setEditor(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("myappclient", Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
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

	/**
	 * 获取标题栏高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	public synchronized static File getDiskCacheDir(Context context, String uniqueName) {
		String cachePath = "";
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return new File(cachePath + File.separator + uniqueName);
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
	 * 判断sd卡是否存在
	 * 
	 * @return
	 */
	public static boolean isSdExists() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取当前应用程序的版本号。
	 */
	public static int getAppVersion(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * 保存json
	 * 
	 * @param context
	 * @param fileName
	 * @param content
	 */
	public static void saveJsonData(Context context, String fileName, JSONObject content) {
		if (content != null) {
			try {
				FileOutputStream outputStream = context.openFileOutput(fileName, Activity.MODE_PRIVATE);
				outputStream.write(content.toString().getBytes());
				outputStream.flush();
				outputStream.close();
				// System.out.println("to sdCard:"+content.toString().getBytes());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取json
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static JSONObject readJsonData(Context context, String fileName) {
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

			JSONObject obj;
			try {
				obj = new JSONObject(content);
				return obj;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
