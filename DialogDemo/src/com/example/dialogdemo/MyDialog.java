package com.example.dialogdemo;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyDialog extends Dialog implements OnClickListener {

	private Context context;
	private LayoutInflater layoutInflater;
	private EditText nickname, psd;
	private TextView cancel_btn, diaolog_title_tv, confirm_btn;

	private WindowManager windowManager;
	MyDialogListener myDialogListener = null;

	public interface MyDialogListener {
		public void onCancel();

		public void onComfirm();
	}

	public void setMyDialogListener(MyDialogListener listener) {
		this.myDialogListener = listener;
	}

	public MyDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		layoutInflater = LayoutInflater.from(this.context);
		windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		initView();
	}

	private void initView() {
		View view = layoutInflater.inflate(R.layout.dialog_demo, null);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(displayMetrics);

		int width = displayMetrics.widthPixels;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LayoutParams.WRAP_CONTENT);

		Window window = getWindow();
		window.setGravity(Gravity.BOTTOM);

		setContentView(view, params);
		nickname = (EditText) view.findViewById(R.id.nickname);
		psd = (EditText) view.findViewById(R.id.psd);
		cancel_btn = (TextView) view.findViewById(R.id.cancel_btn);
		diaolog_title_tv = (TextView) view.findViewById(R.id.diaolog_title_tv);
		confirm_btn = (TextView) view.findViewById(R.id.confirm_btn);

		cancel_btn.setOnClickListener(this);
		confirm_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (myDialogListener != null) {
			switch (v.getId()) {
			case R.id.cancel_btn:
				myDialogListener.onCancel();
				break;
			case R.id.confirm_btn:
				myDialogListener.onComfirm();
				break;
			}
		}
	}

	public void setTitle(String title) {
		diaolog_title_tv.setText(title);
	}

	public void setNickname(String name) {
		nickname.setHint(name);
	}

	public void setPsd(String password) {
		psd.setHint(password);
	}
}
