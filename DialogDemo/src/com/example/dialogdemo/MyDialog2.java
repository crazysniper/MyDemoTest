package com.example.dialogdemo;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyDialog2 extends Dialog implements OnClickListener {

	private Context context;
	private LayoutInflater layoutInflater;

	private TextView title, cancel, confirm;
	private LinearLayout content;

	public MyDialog2(Context context, int theme) {
		super(context, theme);
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		setContentView(R.layout.dialog_item);

		initView();
		initWidth();
	}

	private void initView() {
		setCanceledOnTouchOutside(true);// 点击对话框外边可以关闭对话框
		title = (TextView) findViewById(R.id.title);
		cancel = (TextView) findViewById(R.id.cancel);
		confirm = (TextView) findViewById(R.id.confirm);

		cancel.setOnClickListener(this);
		confirm.setOnClickListener(this);
	}

	private void initWidth() {
		WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		int witdth = windowManager.getDefaultDisplay().getWidth();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel:
			dismiss();
			break;
		case R.id.confirm:
			dismiss();
			break;
		}
	}
}
