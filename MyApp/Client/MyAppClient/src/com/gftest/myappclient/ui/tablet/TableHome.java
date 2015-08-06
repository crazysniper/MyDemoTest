package com.gftest.myappclient.ui.tablet;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gftest.myappclient.R;
import com.gftest.myappclient.ui.tablet.home.AnotherRightFragment;

public class TableHome extends FragmentActivity implements OnClickListener {

	private Button button;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.tablet_home);
		initView();
	}

	private void initView() {
		button = (Button) findViewById(R.id.button);

		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button:
			AnotherRightFragment fragment = new AnotherRightFragment();
			// FragmentManager fragmentManager = getFragmentManager();
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.replace(R.id.right_layout, fragment);
			transaction.commit();
			break;

		default:
			break;
		}
	}

}
