package com.gftest.myappclient.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SecondReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String type = intent.getStringExtra("type");

		if ("1".equals(type)) {// 有序广播
			String msg = getResultExtras(true).getString("msg");
			System.out.println("有序广播SecondReceiver msg=" + msg + "__type=" + type);
			Bundle bundle = new Bundle();
			bundle.putString("msg", msg + "@SecondReceiver");
			bundle.putString("type", "1");
			setResultExtras(bundle);
		} else {// 普通广播
			String msg = intent.getStringExtra("msg");
			System.out.println("SecondReceiver msg=" + msg + "__type=" + type);
		}

	}
}
