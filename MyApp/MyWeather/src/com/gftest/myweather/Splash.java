package com.gftest.myweather;

import com.gftest.myweather.base.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 欢迎
 * 
 * @author Gao
 * 
 */
public class Splash extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		SharedPreferences sharedPreferences = Splash.this.getSharedPreferences("myweather", Context.MODE_PRIVATE);
		if (sharedPreferences.getBoolean("city_selected", false)) {
			handler.sendEmptyMessageDelayed(0, 3000);
			return;
		}
		handler.sendEmptyMessageDelayed(1, 3000);
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Intent intent = null;
			switch (msg.what) {
			case 0:
				intent = new Intent(Splash.this, ChooseAreaActivity.class);
				break;
			case 1:
				intent = new Intent(Splash.this, WeatherActivity.class);
				break;
			default:
				break;
			}
			startActivity(intent);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			Splash.this.finish();
		}
	};

}
