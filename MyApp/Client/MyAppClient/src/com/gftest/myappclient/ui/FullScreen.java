package com.gftest.myappclient.ui;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;
import com.gftest.myappclient.utils.ToastUtils;

/**
 * @author Gao
 * 
 */
public class FullScreen extends BaseActivity implements OnClickListener {

	private LinearLayout rootView;
	private Button btn1;

	private static boolean isfull = false; // 全屏设置和退出全屏
	private boolean waitDouble = true;
	private static final int DOUBLE_CLICK_TIME = 350; // 两次单击的时间间隔

	private ToastUtils toastUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_fullscreen);
		System.out.println("刚进来");
		initView();
	}

	private void initView() {
		toastUtils = ToastUtils.getInstance();

		rootView = (LinearLayout) findViewById(R.id.rootView);
		btn1 = (Button) findViewById(R.id.btn1);

		rootView.setOnClickListener(this);
		btn1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rootView:
			chechClick();
			break;
		case R.id.btn1:
			changeHengPing();
			break;
		}
	}

	private void changeHengPing() {
		if (FullScreen.this.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {// 无法进行画面的旋转
			toastUtils.showToast(FullScreen.this, "错误：无法改变屏幕方向", Toast.LENGTH_SHORT);
		} else {
			if (FullScreen.this.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) { // 现在的方向是横屏显示
				FullScreen.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				// 变为竖屏显示
			} else if (FullScreen.this.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) { // 如果为竖屏显示
				FullScreen.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // 变为横屏显示
			}
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

	/**
	 * 全屏
	 */
	private void setFullScreen() {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		isfull = true;
	}

	/**
	 * 非全屏
	 */
	private void quitFullScreen() {
		final WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setAttributes(attrs);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		// //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		isfull = false;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// 表示的是系统设置修改的时候触发
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// 现在的屏幕方向是横屏
			toastUtils.showToast(FullScreen.this, "当前屏幕为横屏", Toast.LENGTH_SHORT);
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			// 现在竖屏
			toastUtils.showToast(FullScreen.this, "当前屏幕为竖屏", Toast.LENGTH_SHORT);
		}
		super.onConfigurationChanged(newConfig);
	}

}
