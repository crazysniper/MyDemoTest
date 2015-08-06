package com.gftest.mybaidu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(Splash.this, Home.class));
				Splash.this.finish();
			}
		}, 2000);
	}
}
