package com.example.interntdemo.test2;

import android.app.Application;

public class DemoApp extends Application {
	private static DemoApp instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;

	}

	public static DemoApp getInstance() {
		return instance;
	}

}
