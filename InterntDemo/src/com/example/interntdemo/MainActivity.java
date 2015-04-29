package com.example.interntdemo;

import com.example.interntdemo.test.TestActivity;
import com.example.interntdemo.test2.MainUI;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends Activity implements OnClickListener {

	private Button btn1, btn2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			getI();
			break;
		case R.id.btn2:
			startActivity(new Intent(MainActivity.this, MainUI.class));
			break;
		}
	}

	private void getI() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager.getActiveNetworkInfo() != null) {// 能否获取到网络
			boolean flag = connectivityManager.getActiveNetworkInfo().isAvailable();// 判断获取到的网络是否可以用
			System.out.println("flag=" + flag);
		} else {// 不能获取到网络
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setTitle("没有可用的网络");
			builder.setMessage("是否进行网络设置");
			builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					String version = android.os.Build.VERSION.SDK;
					Intent intent = null;
					if (Integer.parseInt(version) > 10) {
						// intent = new
						// Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
						intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
					} else {
						intent = new Intent();
//						ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
						ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.WIFI_SETTINGS");
						intent.setComponent(componentName);
						intent.setAction("android.intent.action.VIEW");
					}
					startActivity(intent);
				}
			});
			builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.show();

			System.out.println("null");
		}
	}
}
