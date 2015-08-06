package com.example.paintdemo;

import com.example.paintdemo.mywidget.ShuduView;

import android.app.Activity;
import android.os.Bundle;

public class Shudu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new ShuduView(this));
	}
}
