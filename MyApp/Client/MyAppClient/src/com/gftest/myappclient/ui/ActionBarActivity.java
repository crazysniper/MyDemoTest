package com.gftest.myappclient.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;

public class ActionBarActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actionbar);
		initView();
	}

	private void initView() {

	}

	@Override
	public void onClick(View v) {

	}

}
