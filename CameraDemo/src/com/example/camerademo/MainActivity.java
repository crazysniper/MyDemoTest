package com.example.camerademo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * http://blog.csdn.net/redoffice/article/details/6716462
 * 
 */
public class MainActivity extends Activity implements OnClickListener {

	private Button btn1, btn2, btn3, btn4, btn5;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
	}

	private void initView() {
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		btn5 = (Button) findViewById(R.id.btn5);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn1:
			intent = new Intent(MainActivity.this, SurfaceView_Activity.class);
			break;
		case R.id.btn2:
			intent = new Intent(MainActivity.this, Camera2.class);
			break;
		case R.id.btn3:
			intent = new Intent(MainActivity.this, ChangeCamera.class);
			break;
		case R.id.btn4:
			break;
		case R.id.btn5:
			intent = new Intent(MainActivity.this, MyCamera.class);
		}
		startActivity(intent);
	}
}
