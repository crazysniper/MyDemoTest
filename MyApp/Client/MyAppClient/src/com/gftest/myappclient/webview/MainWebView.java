package com.gftest.myappclient.webview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;

public class MainWebView extends BaseActivity implements OnClickListener {

	private Button button1, button2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainwebview);
		initView();
	}

	private void initView() {
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);

		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.button1:
			intent = new Intent(MainWebView.this, InternetWebView.class);
			break;
		case R.id.button2:
			intent = new Intent(MainWebView.this, LocalWebView.class);
			break;
		default:
			break;
		}
		startActivity(intent);
	}

}
