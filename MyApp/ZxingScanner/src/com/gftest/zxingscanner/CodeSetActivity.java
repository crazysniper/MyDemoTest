package com.gftest.zxingscanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//二维码设置界面
public class CodeSetActivity extends Activity implements OnClickListener {
	private Button code_setting_fanhui;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.code_setting);
		code_setting_fanhui = (Button) findViewById(R.id.code_setting_fanhui);
		code_setting_fanhui.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.code_setting_fanhui:
			finish();
			break;

		default:
			break;
		}
	}

}
