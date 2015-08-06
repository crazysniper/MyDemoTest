package com.gftest.myappclient.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;
import com.gftest.myappclient.widget.MyLinearLayout;

public class Jifen_qiandao extends BaseActivity implements OnClickListener {

	private MyLinearLayout mylayout;
	private Button btn1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jifen_qiandao);
		initView();
	}

	private void initView() {
		mylayout = (MyLinearLayout) findViewById(R.id.mylayout);
		btn1 = (Button) findViewById(R.id.btn1);

		btn1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			mylayout.change();
			break;
		default:
			break;
		}
	}

}
