package com.gftest.mynews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.gftest.mynews.base.BaseActivity;

public class Splash extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		initView();
	}

	private void initView() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(Splash.this, Home.class));
				Splash.this.finish();
			}
		}, 3000);
	}
}
