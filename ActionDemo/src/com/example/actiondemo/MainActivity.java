package com.example.actiondemo;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.actiondemo.utils.ToastUtils;

public class MainActivity extends Activity implements OnClickListener {

	private Button action_call, action_dial, action_sendto, action_sendto2, action_send, open_broswer;
	private Button open, open1, open2, openmarket, open_self_market;
	private Button action_edit;
	private ToastUtils toastUtils;

	private Button action_setting, action_wifisetting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		toastUtils = ToastUtils.getInstance();
		action_call = (Button) findViewById(R.id.action_call);
		action_dial = (Button) findViewById(R.id.action_dial);
		action_sendto = (Button) findViewById(R.id.action_sendto);
		action_sendto2 = (Button) findViewById(R.id.action_sendto2);
		action_send = (Button) findViewById(R.id.action_send);
		open_broswer = (Button) findViewById(R.id.open_broswer);
		open = (Button) findViewById(R.id.open);
		open1 = (Button) findViewById(R.id.open1);
		open2 = (Button) findViewById(R.id.open2);
		openmarket = (Button) findViewById(R.id.openmarket);
		open_self_market = (Button) findViewById(R.id.open_self_market);
		action_edit = (Button) findViewById(R.id.action_edit);
		action_setting = (Button) findViewById(R.id.action_setting);
		action_wifisetting = (Button) findViewById(R.id.action_wifisetting);

		action_call.setOnClickListener(this);
		action_dial.setOnClickListener(this);
		action_sendto.setOnClickListener(this);
		action_sendto2.setOnClickListener(this);
		action_send.setOnClickListener(this);
		open_broswer.setOnClickListener(this);
		open.setOnClickListener(this);
		open1.setOnClickListener(this);
		open2.setOnClickListener(this);
		openmarket.setOnClickListener(this);
		open_self_market.setOnClickListener(this);
		action_edit.setOnClickListener(this);
		action_setting.setOnClickListener(this);
		action_wifisetting.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.action_call:// 呼叫指定的电话号码
			Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intentFromCapture, 2);
			return;
			
//			toastUtils.showToast(MainActivity.this, "调用ACTION_CALL，需要添加权限<uses-permission android:name=\"android.permission.CALL_PHONE\" />", Toast.LENGTH_LONG);
//			intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:123456"));
//			break;
		case R.id.action_dial:// 调用拨号面板
			toastUtils.showToast(MainActivity.this, "调用ACTION_DIAL，需要添加权限<uses-permission android:name=\"android.permission.CALL_PHONE\" />", Toast.LENGTH_LONG);
			intent = new Intent();
			intent.setAction(Intent.ACTION_DIAL);
			intent.setData(Uri.parse("tel:123456"));
			break;
		case R.id.action_sendto:// 调用发送短信面板，带联系人
			toastUtils.showToast(MainActivity.this, "调用ACTION_SENDTO", Toast.LENGTH_LONG);
			Uri smsToUri = Uri.parse("smsto:123456");
			intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
			intent.putExtra("sms_body", "短信内容，带联系人");
			break;
		case R.id.action_sendto2:// 调用发送短信面板，不带联系人
			toastUtils.showToast(MainActivity.this, "调用ACTION_SENDTO", Toast.LENGTH_LONG);
			intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
			intent.putExtra("sms_body", "短信内容，不带联系人");
			break;
		case R.id.action_send:// 调用发送彩信面板
			toastUtils.showToast(MainActivity.this, "调用ACTION_SEND", Toast.LENGTH_LONG);
			Uri uri = Uri.parse("content://media/external/images/media/23");
			// 设备中的资源（图像或其他资源）
			intent = new Intent(Intent.ACTION_SEND);
			intent.putExtra("sms_body", "内容");
			intent.putExtra(Intent.EXTRA_STREAM, uri);
			intent.setType("image/png");
			break;
		case R.id.open_broswer:// 打开浏览器
			toastUtils.showToast(MainActivity.this, "调用ACTION_VIEW", Toast.LENGTH_LONG);
			intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("http://www.baidu.com"));
			break;
		case R.id.open:
			toastUtils.showToast(MainActivity.this, "<intent-filter><action android:name=\"android.intent.action.VIEW\" /><category android:name=\"android.intent.category.DEFAULT\" /></intent-filter>", Toast.LENGTH_LONG);
			return;
		case R.id.open1:
			toastUtils.showToast(MainActivity.this, "调用ACTION_VIEW", Toast.LENGTH_LONG);
			intent = getPackageManager().getLaunchIntentForPackage("packagename");
			if (intent == null) {
				makeScore1();
				return;
			}
			break;
		case R.id.open2:
			toastUtils.showToast(MainActivity.this, "调用ACTION_VIEW", Toast.LENGTH_LONG);
			intent = getPackageManager().getLaunchIntentForPackage("packagename");
			if (intent == null) {
				makeScore1();
				return;
			}
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			break;
		case R.id.openmarket:
			toastUtils.showToast(MainActivity.this, "调用ACTION_VIEW", Toast.LENGTH_LONG);
			makeScore1();
			return;
		case R.id.open_self_market:
			toastUtils.showToast(MainActivity.this, "调用ACTION_VIEW", Toast.LENGTH_LONG);
			makeScore2();
			return;
		case R.id.action_edit:
			intent = new Intent(Intent.ACTION_EDIT, null);
			break;
		case R.id.action_setting:// 打开设置页面
			intent = new Intent();
			intent.setAction(Settings.ACTION_SETTINGS);
			break;
		case R.id.action_wifisetting:// 打开wifi设置页面
			intent = new Intent();
			intent.setAction(Settings.ACTION_WIFI_SETTINGS);
			break;
		}
		startActivity(intent);
	}

	private void makeScore1() {
		Uri uri = Uri.parse("market://details?id=packagename");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		try {
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(MainActivity.this, "市场无法打开!", Toast.LENGTH_SHORT).show();
		}
	}

	private void makeScore2() {
		Uri uri = Uri.parse("market://details?id=" + getApplication().getPackageName());
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		try {
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(MainActivity.this, "市场无法打开!", Toast.LENGTH_SHORT).show();
		}
	}

	private void setWifi() {
		// 对WIFI网卡进行操作需要通过WifiManager对象来进行，获取该对象的方法如下：
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		// 打开WIFI网卡：
		wifiManager.setWifiEnabled(true);
		// 关闭WIFI网卡：
		wifiManager.setWifiEnabled(false);
		// 获取网卡当前的状态：
		wifiManager.getWifiState();
	}
}
