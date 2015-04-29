package com.example.draftdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnTouchListener {

	private TextView tv1;
	private Button btn1, btn2, btn3, btn4;
	private FrameLayout frame;
	private int screenWidth, screenHeight;
	private int lastX, lastY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		tv1 = (TextView) findViewById(R.id.tv1);
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		frame = (FrameLayout) findViewById(R.id.frame);

		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, XuanZhuan.class));
			}
		});
		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, Second.class));
			}
		});
		btn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, Third.class));
			}
		});
		btn4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, Four.class));
			}
		});

		tv1.setOnTouchListener(this);

		DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		screenWidth = displayMetrics.widthPixels;
		screenHeight = displayMetrics.heightPixels;

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		/**
		 * getX()是表示Widget相对于自身左上角的x坐标<br>
		 * ,而getRawX()是表示相对于屏幕左上角的x坐标值<br>
		 * (注意:
		 * 这个屏幕左上角是手机屏幕左上角,不管activity是否有titleBar或是否全屏幕),getY(),getRawY()一样的道理
		 */

		case MotionEvent.ACTION_DOWN:
			lastX = (int) event.getRawX();
			lastY = (int) event.getRawY();
			System.out.println("lastX=" + lastX);
			System.out.println("lastY=" + lastY);
			break;
		case MotionEvent.ACTION_MOVE:
			int dx = (int) event.getRawX() - lastX;
			int dy = (int) event.getRawY() - lastY;

			int left1 = v.getLeft() + dx;// getLeft()方法得到的是控件坐标距离父控件原点(左上角，坐标（0，0）)的x轴距离
			int top1 = v.getTop() + dy;// getTop和getButtom是距离的y轴距离。
			int right1 = v.getRight() + dx;// getReght()是控件右边距离父控件原点的x轴距离
			int bottom1 = v.getBottom() + dy;
			int dibu = v.getTop() + v.getHeight();// 控件底部距离父控件顶部的距离
			System.out.println("顶部距离父控件的距离=" + top1);
			if (left1 <= 0) {
				left1 = 0;
				right1 = left1 + v.getWidth();
			}
			if (right1 >= screenWidth) {
				right1 = screenWidth;
				left1 = right1 - v.getWidth();
			}
			if (top1 <= 0) {
				top1 = 0;
				bottom1 = top1 + v.getHeight();
			}
			// if (bottom1 > screenHeight) {
			// bottom1 = screenHeight;
			// top1 = bottom1 - v.getHeight();
			// }
			if (bottom1 > frame.getHeight()) {
				bottom1 = frame.getHeight();
				top1 = bottom1 - v.getHeight();
			}
			// if (dibu >= frame.getHeight()) {// 底部超出父控件的时候
			// // bottom1 = v.getHeight();
			// // top1 = frame.getHeight() - v.getHeight();
			// bottom1 = frame.getHeight();
			// top1 = bottom1 - v.getHeight();
			// }

			v.layout(left1, top1, right1, bottom1);
			lastX = (int) event.getRawX();
			lastY = (int) event.getRawY();
			// v.layout(left, top, left + tv1.getWidth(), top +
			// tv1.getHeight());
			// lastX = (int) event.getRawX();
			// lastY = (int) event.getRawY();
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return false;
	}
}
