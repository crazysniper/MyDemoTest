package com.gftest.myappclient.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;
import com.gftest.myappclient.screenshot.GlobalScreenshot;

/**
 * 截屏<br>
 * 
 * http://blog.csdn.net/xu_fu/article/details/39268771<br>
 * 
 * @author Gao
 * 
 */
public class ScreenShot extends BaseActivity implements OnClickListener {

	private Button shot;
	private GlobalScreenshot globalScreenshot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screenshot);
		initView();
	}

	private void initView() {
		shot = (Button) findViewById(R.id.shot);

		globalScreenshot = new GlobalScreenshot(this);

		shot.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shot:
			globalScreenshot.takeScreenshot(getWindow().getDecorView(), new Runnable() {
				@Override
				public void run() {

				}
			}, true, true);// 后面的两个boolean参数是表示是否有状态栏，用于显示不同的淡出动画，如果有一个为false，就会直接淡出，而不会向上偏移到状态栏上。
			break;

		default:
			break;
		}
	}

}
