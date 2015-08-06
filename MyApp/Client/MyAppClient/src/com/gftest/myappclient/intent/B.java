package com.gftest.myappclient.intent;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;

public class B extends BaseActivity implements OnClickListener {
	private Button btn1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b);
		System.out.println("oncreate B_" + getTaskId());

		ComponentName componentName = getIntent().getComponent();
		if (componentName != null) {
			String packName = componentName.getPackageName();// com.gftest.myappclient
																// 应用的packname,而不是该类所在的包名
			String className = componentName.getClassName();// com.gftest.myappclient.intent.B
			System.out.println("packname=" + packName);
			System.out.println("className=" + className);
		}

		initView();
	}

	private void initView() {
		btn1 = (Button) findViewById(R.id.btn1);

		btn1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(B.this, A.class);
		switch (v.getId()) {
		case R.id.btn1:
			break;

		default:
			break;
		}
		startActivity(intent);
	}

}
