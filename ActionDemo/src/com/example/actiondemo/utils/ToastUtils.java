package com.example.actiondemo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.widget.Toast;

public class ToastUtils {
	private static ToastUtils instance = null;
	private Toast toast = null;

	private ToastUtils() {

	}

	public static synchronized ToastUtils getInstance() {
		if (instance == null) {
			instance = new ToastUtils();
		}
		return instance;
	}

	/**
	 * 显示Toast
	 * 
	 * @param context
	 * @param msg
	 *            文本
	 * @param duration
	 *            时长
	 */
	public void showToast(Context context, String msg, int duration) {
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			System.out.println("versionCode=" + packageInfo.versionCode);// 1
			System.out.println("versionName=" + packageInfo.versionName);// 1.0
			System.out.println("getPackageName=" + context.getPackageName());// com.example.actiondemo
			System.out.println("getPackageCodePath=" + context.getPackageCodePath());// /data/app/com.example.actiondemo-1.apk或者/data/app/com.example.actiondemo-2.apk
			System.out.println("getPackageResourcePath=" + context.getPackageResourcePath());// /data/app/com.example.actiondemo-1.apk或者/data/app/com.example.actiondemo-2.apk
			System.out.println("toString=" + packageInfo.toString());// PackageInfo{413f5138 com.example.actiondemo}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		if (toast == null) {
			toast = Toast.makeText(context, msg, duration);
		} else {
			toast.setText(msg);
			toast.setDuration(duration);
		}
		toast.show();
	}
}
