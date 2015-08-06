package com.example.paintdemo;

import com.example.paintdemo.mywidget.CanvasView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class CanvasActivity1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CanvasView canvasView = new CanvasView(this);
		setContentView(R.layout.activity_canvas1);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.BOTTOM;
		addContentView(canvasView, params);
//		setContentView(canvasView);
	}
}
