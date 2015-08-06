package com.gftest.myappclient;

import com.gftest.myappclient.utils.ActivityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 每当我们进入到一个活动的界面，该活动的类名就会被打印出来，这样我们就可以
		// 时时刻刻知晓当前界面对应的是哪一个活动了。
		Log.i("BaseActivity", getClass().getSimpleName());
		ActivityUtils.addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityUtils.removeActivity(this);
	}
}
