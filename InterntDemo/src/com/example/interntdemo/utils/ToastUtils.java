package com.example.interntdemo.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

	private static ToastUtils toastUtils;
	private Toast toast;

	private ToastUtils() {

	}

	public static synchronized ToastUtils getInstance() {
		if (toastUtils == null) {
			return new ToastUtils();
		}
		return toastUtils;
	}

	public void showToast(Context context, String msg, int duration) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, duration);
		} else {
			toast.setText(msg);
			toast.setDuration(duration);
		}
		toast.show();
	}

}
