package com.example.listviewdemo.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

	private static Toast toast = null;

	public static void showToast(Context context, String msg, int duration) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, duration);
		} else {
			toast.setText(msg);
			toast.setDuration(duration);
		}
		toast.show();
	}

}
