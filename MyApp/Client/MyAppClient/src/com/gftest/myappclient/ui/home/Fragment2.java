package com.gftest.myappclient.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gftest.myappclient.BaseFragment;
import com.gftest.myappclient.R;
import com.gftest.myappclient.intent.A;
import com.gftest.myappclient.ui.ActionBarActivity;
import com.gftest.myappclient.ui.Location;
import com.gftest.myappclient.ui.MyAnim;
import com.gftest.myappclient.ui.MyFloatViewActivity;
import com.gftest.myappclient.ui.Progress;
import com.gftest.myappclient.ui.SaveInstanceStateActivity;
import com.gftest.myappclient.ui.Screen;
import com.gftest.myappclient.ui.ScreenShot;
import com.gftest.myappclient.ui.SharedPreferencesActivitiy;
import com.gftest.myappclient.ui.TimerActivity;
import com.gftest.myappclient.ui.text.TextActivity;

public class Fragment2 extends BaseFragment implements OnClickListener {
	private LayoutInflater layoutInflater;
	private Activity activity;
	private View view;

	private TextView title;
	private Button text, btn1, btn2, btn3, screen_shot, myfloat, myanim, btn4, btn5, btn6;
	private Button actionBar, timer;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		System.out.println("Fragment2 onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("Fragment2 onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		System.out.println("Fragment2 onCreateView");
		if (view == null) {
			initView();
			return view;
		} else {
			ViewGroup viewGroup = (ViewGroup) view.getParent();
			if (viewGroup != null) {
				viewGroup.removeAllViews();
			}
			return view;
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		System.out.println("Fragment2 onActivityCreated");
	}

	@Override
	public void onStart() {
		super.onStart();
		System.out.println("Fragment2 onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		System.out.println("Fragment2 onResume");
	}

	@Override
	public void onPause() {
		super.onPause();
		System.out.println("Fragment2 onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		System.out.println("Fragment2 onStop");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		System.out.println("Fragment2 onDestroyView");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println("Fragment2 onDestroy");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		System.out.println("Fragment2 onDetach");
	}

	private void initView() {
		activity = getActivity();
		layoutInflater = activity.getLayoutInflater();
		view = layoutInflater.inflate(R.layout.fragment_2, null);

		title = (TextView) view.findViewById(R.id.title);
		text = (Button) view.findViewById(R.id.text);
		btn1 = (Button) view.findViewById(R.id.btn1);
		btn2 = (Button) view.findViewById(R.id.btn2);
		btn3 = (Button) view.findViewById(R.id.btn3);
		btn4 = (Button) view.findViewById(R.id.btn4);
		btn5 = (Button) view.findViewById(R.id.btn5);
		screen_shot = (Button) view.findViewById(R.id.screen_shot);
		myfloat = (Button) view.findViewById(R.id.myfloat);
		myanim = (Button) view.findViewById(R.id.myanim);
		actionBar = (Button) view.findViewById(R.id.actionBar);
		btn6 = (Button) view.findViewById(R.id.btn6);
		timer = (Button) view.findViewById(R.id.timer);

		title.setText("页面2");
		text.setOnClickListener(this);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		screen_shot.setOnClickListener(this);
		myfloat.setOnClickListener(this);
		myanim.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		actionBar.setOnClickListener(this);
		btn6.setOnClickListener(this);
		timer.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.text:
			intent = new Intent(activity, TextActivity.class);
			break;
		case R.id.btn1:
			intent = new Intent(activity, SaveInstanceStateActivity.class);
			break;
		case R.id.btn2:
			intent = new Intent(activity, Progress.class);
			break;
		case R.id.btn3:
			intent = new Intent(activity, Location.class);
			break;
		case R.id.screen_shot:
			intent = new Intent(activity, ScreenShot.class);
			break;
		case R.id.myfloat:
			intent = new Intent(activity, MyFloatViewActivity.class);
			break;
		case R.id.myanim:
			intent = new Intent(activity, MyAnim.class);
			break;
		case R.id.btn4:
			intent = new Intent(activity, SharedPreferencesActivitiy.class);
			break;
		case R.id.btn5:
			intent = new Intent(activity, A.class);
			break;
		case R.id.actionBar:
			intent = new Intent(activity, ActionBarActivity.class);
			break;
		case R.id.btn6:
			intent = new Intent(activity, Screen.class);
			break;
		case R.id.timer:
			intent = new Intent(activity, TimerActivity.class);
			break;
		}
		startActivity(intent);
	}
}
