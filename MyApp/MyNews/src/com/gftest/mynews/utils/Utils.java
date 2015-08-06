package com.gftest.mynews.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.os.Environment;

public class Utils {

	/**
	 * 判断sd卡存在
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
	 * 获取屏幕信息
	 * 
	 * @param context
	 * @return
	 */
	public static Point getDevice(Context context) {
		Point point = new Point();
		point.x = context.getResources().getDisplayMetrics().widthPixels;
		point.y = context.getResources().getDisplayMetrics().heightPixels;
		return point;
	}

	public static int dp2px(Context context, int dp) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	public static int px2dp(Context context, int px) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	public static int getAppVersionCode(Context context) {
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}

	}
}
