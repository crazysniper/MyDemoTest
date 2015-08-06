package com.gftest.myappclient.ui;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;

public class Location extends BaseActivity implements OnClickListener, OnTouchListener {

	private Button btn1;
	private RelativeLayout view1, view2, view3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		initView();
	}

	private void initView() {
		view1 = (RelativeLayout) findViewById(R.id.view1);
		view2 = (RelativeLayout) findViewById(R.id.view2);
		view3 = (RelativeLayout) findViewById(R.id.view3);
		btn1 = (Button) findViewById(R.id.btn1);

		btn1.setOnClickListener(this);
		view1.setOnTouchListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			System.out.println("view2宽度=" + view2.getWidth());
			System.out.println("view2高度=" + view2.getHeight());

			System.out.println("view2 getLeft=" + view2.getLeft());
			System.out.println("view2 getTop=" + view2.getTop());
			System.out.println("view2 getRight=" + view2.getRight());
			System.out.println("view2 getBottom=" + view2.getBottom());
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.view1:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:// 获取按下的坐标，只可以用event，而不是view
				System.out.println("event.getRawX()=" + event.getRawX());
				System.out.println("event.getRawY()=" + event.getRawY());
				System.out.println("event.getX()=" + event.getX());
				System.out.println("event.getY()=" + event.getY());

				System.out.println("v.getX()=" + v.getX());
				System.out.println("v.getY()=" + v.getY());
				break;

			default:
				break;
			}
			break;
		default:
			break;
		}
		return true;
	}

}
