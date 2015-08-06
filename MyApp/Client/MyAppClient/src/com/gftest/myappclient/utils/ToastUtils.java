package com.gftest.myappclient.utils;

import org.w3c.dom.Text;

import com.gftest.myappclient.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Toast提示
 * 
 * @author Gao
 * 
 */
public class ToastUtils {
	private static ToastUtils instance = null;
	private Toast toast = null;
	private TextView tv;
	private TextView toastMsg;

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
		if (toast == null || toast.getView() == null) {
			toast = new Toast(context);
			View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
			tv = (TextView) view.findViewById(R.id.tv);
			tv.setText(msg);
			toast.setDuration(duration);
			toast.setView(view);
		} else {
			tv.setText(msg);
			toast.setDuration(duration);
		}
		toast.show();
	}

	public void showToastLong(Context context, String msg) {
		if (toast == null || toast.getView() == null) {
			toast = new Toast(context);
			View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
			toastMsg = (TextView) view.findViewById(R.id.toastMsg);
			toastMsg.setText(msg);
			toast.setView(view);
			toast.setDuration(Toast.LENGTH_LONG);
		} else {
			toastMsg.setText(msg);
			toast.setDuration(Toast.LENGTH_LONG);
		}
		toast.show();
	}

	public void showToastShort(Context context, String msg) {
		if (toast == null) {
			toast = new Toast(context);
			View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
			toastMsg = (TextView) view.findViewById(R.id.toastMsg);
			toastMsg.setText(msg);
			toast.setView(view);
			toast.setDuration(Toast.LENGTH_SHORT);
		} else {
			toastMsg.setText(msg);
			toast.setDuration(Toast.LENGTH_SHORT);
		}
		toast.show();
	}
}
