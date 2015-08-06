package com.gftest.myappclient.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.gftest.myappclient.BaseFragment;
import com.gftest.myappclient.R;
import com.gftest.myappclient.calendar.CalendarActivity;
import com.gftest.myappclient.receiver.ReceiverMain;
import com.gftest.myappclient.ui.FullScreen;
import com.gftest.myappclient.ui.NotificationActivity;
import com.gftest.myappclient.ui.Password;
import com.gftest.myappclient.ui.Rating;
import com.gftest.myappclient.ui.SpannableStringActivity;
import com.gftest.myappclient.ui.UpdateVersion;

public class Fragment3 extends BaseFragment implements OnClickListener {
	private LayoutInflater layoutInflater;
	private Activity activity;
	private View view;

	private Button btn1, btn2, btn3, btn4, btn5, btn6, spannablestring, receiver, rating;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

	private void initView() {
		activity = getActivity();
		layoutInflater = activity.getLayoutInflater();
		view = layoutInflater.inflate(R.layout.fragment_3, null);

		btn1 = (Button) view.findViewById(R.id.btn1);
		btn2 = (Button) view.findViewById(R.id.btn2);
		btn3 = (Button) view.findViewById(R.id.btn3);
		btn4 = (Button) view.findViewById(R.id.btn4);
		btn5 = (Button) view.findViewById(R.id.btn5);
		spannablestring = (Button) view.findViewById(R.id.spannablestring);
		receiver = (Button) view.findViewById(R.id.receiver);
		rating = (Button) view.findViewById(R.id.rating);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		spannablestring.setOnClickListener(this);
		receiver.setOnClickListener(this);
		rating.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn1:// 版本更新
			intent = new Intent(activity, UpdateVersion.class);
			break;
		case R.id.btn2:
			intent = new Intent(activity, NotificationActivity.class);
			break;
		case R.id.btn3:
			intent = new Intent(activity, CalendarActivity.class);
			break;
		case R.id.btn4:
			intent = new Intent(activity, FullScreen.class);
			break;
		case R.id.btn5:
			intent = new Intent(activity, Password.class);
			break;
		case R.id.spannablestring:
			intent = new Intent(activity, SpannableStringActivity.class);
			break;
		case R.id.receiver:
			intent = new Intent(activity, ReceiverMain.class);
			break;
		case R.id.rating:
			intent = new Intent(activity, Rating.class);
			break;
		}
		startActivity(intent);
	}
}
