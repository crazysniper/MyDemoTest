package com.gftest.myappclient.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;
import com.gftest.myappclient.listener.UpdateVersionMange;

/**
 * 版本更新
 * 
 * @author Gao
 * 
 */
public class UpdateVersion extends BaseActivity {

	private Button btn1;
	private UpdateVersionMange updateManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updateversion);
		initView();
	}

	private void initView() {
		btn1 = (Button) findViewById(R.id.btn1);
		updateManager = new UpdateVersionMange(this);

		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				updateManager.checkUpdateInfo();
			}
		});
	}

}
