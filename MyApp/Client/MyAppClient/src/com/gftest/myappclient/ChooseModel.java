package com.gftest.myappclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gftest.myappclient.ui.Splash;
import com.gftest.myappclient.ui.tablet.TableHome;

public class ChooseModel extends BaseActivity implements OnClickListener {

	private Button phone, tablet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_model);
		initView();
	}

	private void initView() {
		phone = (Button) findViewById(R.id.phone);
		tablet = (Button) findViewById(R.id.tablet);

		phone.setOnClickListener(this);
		tablet.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.phone:
			startActivity(new Intent(ChooseModel.this, Splash.class));
			break;
		case R.id.tablet:
			startActivity(new Intent(ChooseModel.this, TableHome.class));
			break;
		default:
			break;
		}
		ChooseModel.this.finish();
	}

}
