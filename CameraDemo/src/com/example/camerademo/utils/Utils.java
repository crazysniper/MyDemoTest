package com.example.camerademo.utils;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

public class Utils {

	public static void saveSharedPreferences(Context context, String name, String data) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
		// 存储数据
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(name, data);
		editor.commit();
	}

	public static void saveSelectedImags(Context context, List<String> imgList) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
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

	public static String readSharedPreferences(Context context, String name) {
		String ret = "";
		SharedPreferences sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_MULTI_PROCESS);
		return sharedPreferences.getString(name, ret);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
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

}
