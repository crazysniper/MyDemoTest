package com.gftest.myweather;

import com.gftest.myweather.base.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class Home extends BaseActivity implements OnClickListener {

	private Button button1;
	private LinearLayout rootView;

	private static boolean isfull = false; // 全屏设置和退出全屏
	private boolean waitDouble = true;
	private static final int DOUBLE_CLICK_TIME = 350; // 两次单击的时间间隔

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_home);
		initView();
	}

	private void initView() {
		button1 = (Button) findViewById(R.id.button1);
		rootView = (LinearLayout) findViewById(R.id.rootView);

		rootView.setOnClickListener(this);
		button1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rootView:
			chechClick();
			break;
		case R.id.button1:
			System.out.println("1111111111");
			break;
		}
	}

	private void chechClick() {
		if (waitDouble) {
			waitDouble = false;
			new Thread() {
				@Override
				public void run() {
					try {
						System.out.println("时间1=" + System.currentTimeMillis());
						sleep(DOUBLE_CLICK_TIME); // 等待双击时间，否则执行单击事件
						System.out.println("时间2=" + System.currentTimeMillis());
						if (!waitDouble) {// 如果过了等待事件还是预执行双击状态，则视为单击
							System.out.println("时间3=" + System.currentTimeMillis());
							waitDouble = true;
							singleClick();
						}
						System.out.println("时间4=" + System.currentTimeMillis());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
		} else {
			waitDouble = true;
			doubleClick();// 执行双击
		}
	}

	// 单击响应事件
	private void singleClick() {
		System.out.println("单击");
	}

	// 双击响应事件
	private void doubleClick() {
		System.out.println("双击");
		changescreen();
	}

	public void changescreen() {
		if (isfull == true) {
			quitFullScreen();
		} else {
			setFullScreen();
		}
	}

	private void setFullScreen() {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		isfull = true;
	}

	private void quitFullScreen() {
		final WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setAttributes(attrs);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		// //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		isfull = false;
	}

}
