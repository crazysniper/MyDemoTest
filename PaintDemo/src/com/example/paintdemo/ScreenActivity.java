package com.example.paintdemo;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.graphics.Rect;

/**
 * 获取屏幕宽度/高度
 * 
 * @author Administrator
 * 
 */
public class ScreenActivity extends Activity implements OnClickListener {

	private Button btn1, btn2, btn3, btn4, btn5;
	private TextView tv1, tv2, tv3, tv4, tv5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screen);

		initView();
	}

	private void initView() {
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		btn5 = (Button) findViewById(R.id.btn5);

		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv5 = (TextView) findViewById(R.id.tv5);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		Rect rect = new Rect();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		// /取得整个视图部分,注意，如果你要设置标题样式，这个必须出现在标题样式之后，否则会出错
		getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		View view = getWindow().findViewById(Window.ID_ANDROID_CONTENT);

		switch (v.getId()) {
		case R.id.btn1:// 获取屏幕高度和宽度
			int width1 = displayMetrics.widthPixels;
			int height1 = displayMetrics.heightPixels;
			tv1.setText("屏幕高度：" + height1 + "   宽度：" + width1);
			break;
		case R.id.btn2:// 获取状态栏的高度和宽度
			int width2 = rect.width();
			int top2 = rect.top;// 状态栏的高度，所以rect.height,rect.width分别是系统的高度的宽度
			int height2 = rect.height();
			tv2.setText("状态栏高度：" + top2 + "   宽度：" + width2 + " height2:" + height2);
			break;
		case R.id.btn3:// 获取标题栏的高度和宽度
			int top = view.getTop();// 状态栏标题栏的总高度
			int width3 = view.getWidth();// 视图的宽度,这个宽度好像总是最大的那个
			int height3 = view.getHeight();// 视图的高度，不包括状态栏和标题栏
			tv3.setText("状态栏标题栏的总高度：" + top + "   标题栏宽度：" + width3 + " 标题栏高度:" + (top - rect.top) + "  height3:" + height3);
			break;
		case R.id.btn4:// 获取内容的高度和宽度
			int width4 = view.getWidth();// 视图的宽度,这个宽度好像总是最大的那个
			int height4 = view.getHeight();// 视图的高度，不包括状态栏和标题栏
			tv4.setText("内容高度：" + height4 + "   宽度：" + width4);
			break;
		case R.id.btn5:
			int width5 = rect.width();// rect.height,rect.width分别是系统的高度的宽度
			int height5 = rect.height();
			tv5.setText("屏幕高度：" + height5 + "   宽度：" + width5);
			break;
		}
	}
}
