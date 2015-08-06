package com.gftest.myappclient.ui;

import android.os.Bundle;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;
import com.gftest.myappclient.widget.TopBar;

public class TopBarActivity extends BaseActivity {

	private TopBar topbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topbar);
		initView();
	}

	private void initView() {
		topbar = (TopBar) findViewById(R.id.topbar);
	}

}
