package com.gftest.myappclient.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gftest.myappclient.R;
import com.gftest.myappclient.ui.home.Home3;

public class MyReceiver extends BroadcastReceiver {

	public static final int ID = 10;

	@Override
	public void onReceive(Context context, Intent intent) {
		String msg = intent.getStringExtra("msg");
		System.out.println("msg=" + msg);
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.ic_launcher, "提示有新消息啦", System.currentTimeMillis());
		Intent intent2 = new Intent(context, Home3.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, 0);
		notification.setLatestEventInfo(context, "下拉后显示的标题", msg, pendingIntent);
		notificationManager.notify(ID, notification);
	}
	/**
	 * 在Android系统中,BroadcastReceiver的设计初衷就是从全局考虑的，可以方便应用程序和系统、应用程序之间、应用程序内的通信，
	 * 所以对单个应用程序而言BroadcastReceiver是存在安全性问题的，相应问题及解决如下：
	 * 
	 * 1、当应用程序发送某个广播时系统会将发送的Intent与系统中所有注册的BroadcastReceiver的IntentFilter进行匹配，
	 * 若匹配成功则执行相应的onReceive函数。可以通过类似sendBroadcast(Intent,
	 * String)的接口在发送广播时指定接收者必须具备的permission。或通过Intent.setPackage设置广播仅对某个程序有效。
	 * 
	 * 2. 当应用程序注册了某个广播时，即便设置了IntentFilter还是会接收到来自其他应用程序的广播进行匹配判断。
	 * 对于动态注册的广播可以通过类似registerReceiver(BroadcastReceiver, IntentFilter, String,
	 * android.os.Handler)的接口指定发送者必须具备的permission，对于静态注册的广播可以通过android:exported=
	 * "false"属性表示接收者对外部应用程序不可用，即不接受来自外部的广播。
	 * 
	 * 上面两个问题其实都可以通过LocalBroadcastManager来解决:
	 * 
	 * 
	 * Android v4 兼容包提供android.support.v4.content.LocalBroadcastManager工具类，
	 * 帮助大家在自己的进程内进行局部广播发送与注册，使用它比直接通过sendBroadcast(Intent)发送系统全局广播有以下几点好处。
	 * 
	 * 1 因广播数据在本应用范围内传播，你不用担心隐私数据泄露的问题。
	 * 
	 * 2 不用担心别的应用伪造广播，造成安全隐患。
	 * 
	 * 3 相比在系统内发送全局广播，它更高效。
	 * 
	 * 其使用方法也和正常注册广播类似:
	 */
	// LocalBroadcastManager.registerReceiver(receiver, filter);

	/**
	 * BroadcastReceiver 和事件处理机制类似，只不过事件的处理机制是应用程序级别的，而广播处理机制是系统级别的。
	 * 
	 * BroadcastReceiver 用于接收并处理广播通知（broadcast
	 * announcements）。多数的广播是系统发起的，如地域变换、电量不足
	 * 、来电来信等，当然，应用程序自己也可以广播一个广播。BroadcastReceiver
	 * 可以通过多种方式通知用户：启动activity、使用NotificationManager
	 * 、开启背景灯、振动设备、播放声音等，最典型的是在状态栏显示一个图标，这样用户就可以点它打开看通知内容。
	 */
}
