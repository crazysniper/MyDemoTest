package com.gftest.myappclient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;
import com.gftest.myappclient.ui.home.Home;
import com.gftest.myappclient.ui.home.Home3;
import com.gftest.myappclient.utils.Utils;

public class Splash extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent intent = null;
				String no = Utils.getInstance().getSharedPreferences(Splash.this, "isFirst", "");
				if ("no".equals(no)) {
					// intent = new Intent(Splash.this, Login.class);
					// intent = new Intent(Splash.this, Home.class);//
					// 设置两侧菜单Menu的
					// intent = new Intent(Splash.this, Home2.class);
					intent = new Intent(Splash.this, Home3.class);
				} else {
					intent = new Intent(Splash.this, Guide.class);
				}
				startActivity(intent);
				Splash.this.finish();
			}
		}, 2000);
	}

}
