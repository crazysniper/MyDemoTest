package com.gftest.myappclient.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ThirdReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String type = intent.getStringExtra("type");

		if ("1".equals(type)) {// 有序广播
			String msg = getResultExtras(true).getString("msg");
			System.out.println("有序广播ThirdReceiver msg=" + msg + "__type=" + type);
			Bundle bundle = new Bundle();
			bundle.putString("msg", msg + "@ThirdReceiver");
			bundle.putString("type", "1");
		} else {// 普通广播
			String msg = intent.getStringExtra("msg");
			System.out.println("ThirdReceiver msg=" + msg + "__type=" + type);
		}
	}
}
