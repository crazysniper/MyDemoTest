package com.example.dialogdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NextActivity extends Activity implements OnClickListener {

	private Button btn1, btn2, btn3, btn4;
	private MyDialog2 dialog2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two);
		initView();
	}

	private void initView() {
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			btn1();
			break;
		case R.id.btn2:
			btn2();
			break;
		case R.id.btn3:
			btn3();
			break;
		case R.id.btn4:
			btn4();
			break;
		}
	}

	public void btn1() {
		dialog2 = new MyDialog2(NextActivity.this, R.style.mydialog2);

		dialog2.show();
	}

	public void btn2() {
		dialog2 = new MyDialog2(NextActivity.this, R.style.mydialog2);

		dialog2.show();
	}

	public void btn3() {
		dialog2 = new MyDialog2(NextActivity.this, R.style.mydialog2);

		dialog2.show();
	}

	public void btn4() {
		dialog2 = new MyDialog2(NextActivity.this, R.style.mydialog2);

		dialog2.show();
	}
}
