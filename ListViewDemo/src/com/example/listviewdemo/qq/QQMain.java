package com.example.listviewdemo.qq;

import com.example.listviewdemo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class QQMain extends Activity implements OnClickListener {
	private Button btn1, btn2, btn3, btn4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qqmain);
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
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn1:
			intent = new Intent(QQMain.this, QQActivity.class);
			break;
		case R.id.btn2:
			intent = new Intent(QQMain.this, QQActivity2.class);
			break;
		case R.id.btn3:
			intent = new Intent(QQMain.this, QQActivity3.class);
			break;
		case R.id.btn4:
			intent = new Intent(QQMain.this, QQActivity4.class);
			break;
		}
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
}
