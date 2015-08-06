package com.gftest.myappclient.ui.event;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;

public class EventMain extends BaseActivity implements OnClickListener, OnTouchListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		initView();
	}

	private void initView() {

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}

	@Override
	public void onClick(View v) {

	}

	/**
	 * 事件分发
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		System.out.println("TouchEventActivity | dispatchTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 事件响应
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.out.println("TouchEventActivity | onTouchEvent --> " + TouchEventUtil.getTouchAction(event.getAction()));
		return super.onTouchEvent(event);
	}
}
