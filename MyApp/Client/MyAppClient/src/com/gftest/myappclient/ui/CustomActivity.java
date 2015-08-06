package com.gftest.myappclient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;

public class CustomActivity extends BaseActivity implements OnClickListener {

	private Button custom_topbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom);
		initView();
	}

	private void initView() {
		custom_topbar = (Button) findViewById(R.id.custom_topbar);

		custom_topbar.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.custom_topbar:
			intent = new Intent(CustomActivity.this, TopBarActivity.class);
			break;
		}
		startActivity(intent);
	}
}
