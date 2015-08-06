package com.gftest.myappclient.intent;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;

public class A extends BaseActivity implements OnClickListener {

	private Button btn1, btn2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a);
		System.out.println("oncreate A_" + getTaskId());
		initView();
	}

	private void initView() {
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			Intent intent = new Intent(A.this, B.class);
			startActivity(intent);
			break;
		case R.id.btn2:
			Intent intent2 = new Intent();
			ComponentName component = new ComponentName(A.this, B.class);
			intent2.setComponent(component);
			startActivity(intent2);
			break;
		default:
			break;
		}
	}

}