package com.gftest.myappclient.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class FirstReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String type = intent.getStringExtra("type");

		if ("1".equals(type)) {// 有序广播
			String msg = intent.getStringExtra("msg");
			boolean abort = intent.getBooleanExtra("abort", false);
			System.out.println("有序广播FirstReceiver msg=" + msg + "__type=" + type);
			Bundle bundle = new Bundle();
			bundle.putString("msg", msg + "@FirstReceiver");
			bundle.putString("type", "1");
			setResultExtras(bundle);
			if (abort) {
				abortBroadcast();
			}
		} else {// 普通广播
			String msg = intent.getStringExtra("msg");
			System.out.println("FirstReceiver msg=" + msg + "__type=" + type);
		}

	}

}
