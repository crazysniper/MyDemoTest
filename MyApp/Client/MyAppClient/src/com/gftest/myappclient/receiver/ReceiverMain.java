package com.gftest.myappclient.receiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;

public class ReceiverMain extends BaseActivity implements OnClickListener {

	private Button register1, register2;
	private Button receiver1, receiver2, receiver3, receiver4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receiver);
		initView();
	}

	private void initView() {
		register1 = (Button) findViewById(R.id.register1);
		register2 = (Button) findViewById(R.id.register2);
		receiver1 = (Button) findViewById(R.id.receiver1);
		receiver2 = (Button) findViewById(R.id.receiver2);
		receiver3 = (Button) findViewById(R.id.receiver3);
		receiver4 = (Button) findViewById(R.id.receiver4);

		register1.setOnClickListener(this);
		register2.setOnClickListener(this);
		receiver1.setOnClickListener(this);
		receiver2.setOnClickListener(this);
		receiver3.setOnClickListener(this);
		receiver4.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register1:// 静态注册,在AndroidManifest.xml文件中配置的
			Intent intent1 = new Intent("android.intent.action.MY_BROADCAST");
			intent1.putExtra("msg", "静态注册.");
			sendBroadcast(intent1);
			break;
		case R.id.register2:// 动态注册,需要在代码中动态的指定广播地址并注册。动态注册不需要再在AndroidManifest.xml注册
			MyReceiver myReceiver2 = new MyReceiver();
			IntentFilter intentFilter2 = new IntentFilter();
			intentFilter2.addAction("android.intent.action.MY_BROADCAST");
			registerReceiver(myReceiver2, intentFilter2);

			Intent intent2 = new Intent("android.intent.action.MY_BROADCAST");
			intent2.putExtra("msg", "动态注册.");
			sendBroadcast(intent2);
			break;
		case R.id.receiver1:
			// 普通广播，对于多个接收者来说是完全异步的，通常每个接收者都无需等待即可以接收到广播，接收者相互之间不会有影响。对于这种广播，接收者无法终止广播，即无法阻止其他接收者的接收动作。
			Intent intent3 = new Intent("android.intent.action.MY_BROADCAST1");
			intent3.putExtra("msg", "普通广播");
			intent3.putExtra("type", "0");
			sendBroadcast(intent3);
			break;
		case R.id.receiver2:// 有序广播
			/**
			 * 使用sendOrderedBroadcast方法发送有序广播时，需要一个权限参数，如果为null则表示不要求接收者声明指定的权限，
			 * 如果不为null，则表示接收者若要接收此广播，需声明指定权限。
			 * 
			 * 这样做是从安全角度考虑的，例如系统的短信就是有序广播的形式，
			 * 一个应用可能是具有拦截垃圾短信的功能，当短信到来时它可以先接受到短信广播，必要时终止广播传递，这样的软件就必须声明接收短信的权限。
			 */
			Intent intent4 = new Intent("android.intent.action.MY_BROADCAST1");
			intent4.putExtra("msg", "有序广播");
			intent4.putExtra("type", "1");
			sendOrderedBroadcast(intent4, "scott.permission.MY_BROADCAST_PERMISSION");
			break;
		case R.id.receiver3:// 终止有序广播
			Intent intent5 = new Intent("android.intent.action.MY_BROADCAST1");
			intent5.putExtra("msg", "有序广播");
			intent5.putExtra("type", "1");
			intent5.putExtra("abort", true);
			sendOrderedBroadcast(intent5, "scott.permission.MY_BROADCAST_PERMISSION");
			break;
		case R.id.receiver4:
			break;
		default:
			break;
		}
	}
}
