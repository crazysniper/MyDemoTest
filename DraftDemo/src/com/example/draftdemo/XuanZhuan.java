package com.example.draftdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * http://www.2cto.com/kf/201407/321109.html<br>
 * http://blog.csdn.net/sdvch/article/details/10831073<br>
 * 
 */
public class XuanZhuan extends Activity {
	private TextView tv1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TouchImageView img = new TouchImageView(XuanZhuan.this);
		setContentView(img);
		// setContentView(R.layout.activity_xuanzhuan);
		// initView();
	}

	private void initView() {
		tv1 = (TextView) findViewById(R.id.tv1);

	}
}
