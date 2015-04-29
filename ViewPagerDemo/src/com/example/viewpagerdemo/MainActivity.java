package com.example.viewpagerdemo;

import com.example.viewpagerdemo.fragment.MyDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initView();
	}

	private void initView() {
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		btn5 = (Button) findViewById(R.id.btn5);
		btn6 = (Button) findViewById(R.id.btn6);
		btn7 = (Button) findViewById(R.id.btn7);
		btn8 = (Button) findViewById(R.id.btn8);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn1:
			intent = new Intent(MainActivity.this, ViewPager1_YiDong_ChangAnTanChuang.class);
			break;
		case R.id.btn2:
			intent = new Intent(MainActivity.this, MainActivity2.class);
			break;
		case R.id.btn3:
			intent = new Intent(MainActivity.this, MainActivity3.class);
			break;
		case R.id.btn4:
			intent = new Intent(MainActivity.this, YiDongActivity.class);
			break;
		case R.id.btn5:
			intent = new Intent(MainActivity.this, CopyOfYiDongActivity.class);
			break;
		case R.id.btn6:
			intent = new Intent(MainActivity.this, YiDongActivity2.class);
			break;
		case R.id.btn7:
			intent = new Intent(MainActivity.this, JavaRelativeLayout.class);
			break;
		case R.id.btn8:
			intent = new Intent(MainActivity.this, MyDemo.class);
			break;

		}
		startActivity(intent);
	}

}
