package com.gftest.myappclient.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gftest.myappclient.BaseFragment;
import com.gftest.myappclient.R;
import com.gftest.myappclient.new_imagepicker.NewImagePicker;
import com.gftest.myappclient.new_imagepicker.NewImagePicker2;
import com.gftest.myappclient.ui.Jifen_qiandao;
import com.gftest.myappclient.ui.Register;
import com.gftest.myappclient.ui.event.EventMain;
import com.gftest.myappclient.ui.viewpager.ViewPager_Fragment;
import com.gftest.myappclient.webview.MainWebView;

public class Fragment4 extends BaseFragment implements OnClickListener {
	private LayoutInflater layoutInflater;
	private Activity activity;
	private View view;

	private TextView title;
	private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		System.out.println("oncreateview");
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
		view = layoutInflater.inflate(R.layout.fragment_4, null);

		title = (TextView) view.findViewById(R.id.title);
		btn1 = (Button) view.findViewById(R.id.btn1);
		btn2 = (Button) view.findViewById(R.id.btn2);
		btn3 = (Button) view.findViewById(R.id.btn3);
		btn4 = (Button) view.findViewById(R.id.btn4);
		btn5 = (Button) view.findViewById(R.id.btn5);
		btn6 = (Button) view.findViewById(R.id.btn6);
		btn7 = (Button) view.findViewById(R.id.btn7);
		btn8 = (Button) view.findViewById(R.id.btn8);

		title.setText("页面4");
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn1:
			intent = new Intent(activity, Register.class);
			break;
		case R.id.btn2:
			intent = new Intent(activity, NewImagePicker.class);
			break;
		case R.id.btn3:
			intent = new Intent(activity, NewImagePicker2.class);
			break;
		case R.id.btn4:
			intent = new Intent(activity, ViewPager_Fragment.class);
			break;
		case R.id.btn5:
			intent = new Intent(activity, MainWebView.class);
			break;
		case R.id.btn6:
			intent = new Intent(activity, EventMain.class);
			break;
		case R.id.btn7:
			intent = new Intent(activity, EventMain.class);
			break;
		case R.id.btn8:
			intent = new Intent(activity, Jifen_qiandao.class);
			break;
		}
		startActivity(intent);
	}
}
