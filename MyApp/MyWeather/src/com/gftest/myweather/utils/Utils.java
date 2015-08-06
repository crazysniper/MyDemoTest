package com.gftest.myweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {

	/**
	 * 保存数据
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveSharedPreferences(Context context, String key, String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("myweather", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getSharedPreferences(Context context, String key, String defaultValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("myweather", Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, defaultValue);
	}

}
