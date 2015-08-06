package com.gftest.myappclient.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gftest.myappclient.BaseFragment;
import com.gftest.myappclient.R;
import com.gftest.myappclient.ui.ChangeTheme;
import com.gftest.myappclient.ui.ChangeTheme2;
import com.gftest.myappclient.ui.CustomActivity;
import com.gftest.myappclient.ui.ImageDown;
import com.gftest.myappclient.utils.ToastUtils;

public class Fragment1 extends BaseFragment implements OnClickListener {
	private LayoutInflater layoutInflater;
	private Activity activity;
	private View view;

	private TextView title, showGps, text1;
	private ImageView leftMenu, rightMenu;
	private Button achieveGps, btn1, btn2, btn3, btn4, btn5, custom, check, life;
	private EditText ed;

	/** alphaAnimation1:不透明->透明；alphaAnimation2:透明->不透明 */
	private AlphaAnimation alphaAnimation1, alphaAnimation2;
	/** 默认不透明，false:不透明；true:透明 */
	private boolean isAlpha = false;

	private OpenMenuListener openMenuListener;

	public interface OpenMenuListener {
		public void openLeftMenu();

		public void openRightMenu();
	}

	@Override
	public void onAttach(Activity activity) {
		try {
			openMenuListener = (OpenMenuListener) activity;
		} catch (Exception e) {
		}
		super.onAttach(activity);
		System.out.println("Fragment1 onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("Fragment1 onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		System.out.println("Fragment1 onCreateView");
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
		System.out.println("Fragment1 onActivityCreated");
	}

	@Override
	public void onStart() {
		super.onStart();
		System.out.println("Fragment1 onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		System.out.println("Fragment1 onResume");
	}

	@Override
	public void onPause() {
		super.onPause();
		System.out.println("Fragment1 onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		System.out.println("Fragment1 onStop");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		System.out.println("Fragment1 onDestroyView");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println("Fragment1 onDestroy");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		System.out.println("Fragment1 onDetach");
	}

	private void initView() {
		activity = getActivity();
		layoutInflater = activity.getLayoutInflater();
		view = layoutInflater.inflate(R.layout.fragment_1, null);

		title = (TextView) view.findViewById(R.id.title);
		showGps = (TextView) view.findViewById(R.id.showGps);
		text1 = (TextView) view.findViewById(R.id.text1);
		leftMenu = (ImageView) view.findViewById(R.id.leftMenu);
		rightMenu = (ImageView) view.findViewById(R.id.rightMenu);

		achieveGps = (Button) view.findViewById(R.id.achieveGps);
		btn1 = (Button) view.findViewById(R.id.btn1);
		btn2 = (Button) view.findViewById(R.id.btn2);
		btn3 = (Button) view.findViewById(R.id.btn3);
		btn4 = (Button) view.findViewById(R.id.btn4);
		btn5 = (Button) view.findViewById(R.id.btn5);
		custom = (Button) view.findViewById(R.id.custom);
		check = (Button) view.findViewById(R.id.check);
		ed = (EditText) view.findViewById(R.id.ed);
		life = (Button) view.findViewById(R.id.life);

		title.setText("页面1");
		alphaAnimation1 = new AlphaAnimation(1.0f, 0.0f);// 不透明->透明
		alphaAnimation2 = new AlphaAnimation(0.0f, 1.0f);// 透明->不透明
		alphaAnimation1.setFillAfter(true);
		alphaAnimation2.setFillAfter(true);
		alphaAnimation1.setDuration(1000);
		alphaAnimation2.setDuration(1000);

		achieveGps.setOnClickListener(this);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		custom.setOnClickListener(this);
		check.setOnClickListener(this);
		life.setOnClickListener(this);

		leftMenu.setOnClickListener(this);
		rightMenu.setOnClickListener(this);
	}

	private int count = 0;

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.leftMenu:
			openMenuListener.openLeftMenu();
			return;
		case R.id.rightMenu:
			openMenuListener.openRightMenu();
			return;
		case R.id.achieveGps:
			count++;
			System.out.println("count=" + count);
			if (count % 2 == 0) {
				ToastUtils.getInstance().showToastLong(activity, "网络检查~");
			} else {
				ToastUtils.getInstance().showToastShort(activity, "你好啊aaa~");
			}
			return;
		case R.id.btn1:
			changeVisible();
			return;
		case R.id.btn2:
			changeAlpha();
			return;
		case R.id.btn3:
			intent = new Intent(activity, ImageDown.class);
			break;
		case R.id.btn4:
			intent = new Intent(activity, ChangeTheme.class);
			break;
		case R.id.btn5:
			intent = new Intent(activity, ChangeTheme2.class);
			break;
		case R.id.custom:
			intent = new Intent(activity, CustomActivity.class);
			break;
		case R.id.check:
			String content = ed.getText().toString().trim();
			if (TextUtils.isEmpty(content)) {
				Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
				drawable.setBounds(0, 0, 10, 10);
				ed.setError("请输入内容", drawable);
			}
			return;
		case R.id.life:
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setTitle("是否有影响");
			builder.setCancelable(true);
			builder.setPositiveButton("实验证明没有影响", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			AlertDialog dialog = builder.create();
			dialog.setCanceledOnTouchOutside(true);
			int[] location = new int[2];// location[0]表示view的x坐标值,location[1]表示view的y坐标值
			Window window = dialog.getWindow();
			window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
			window.setWindowAnimations(R.anim.animset0);// 添加动画

			WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
			// layoutParams.gravity = Gravity.BOTTOM |
			// Gravity.CENTER_HORIZONTAL;
			// layoutParams.y = Utils.getDeviceSize(activity).y - 200;
			// dialog.getWindow().setAttributes(layoutParams);
			dialog.show();
			return;
		}
		startActivity(intent);
	}

	private void changeVisible() {
		int visible = text1.getVisibility();
		if (visible == View.VISIBLE) {
			text1.setVisibility(View.GONE);
			btn1.setText("不可见");
		} else if (visible == View.GONE) {
			text1.setVisibility(View.VISIBLE);
			btn1.setText("可见");
		}
	}

	private void changeAlpha() {
		if (isAlpha) {// 不透明->透明
			text1.startAnimation(alphaAnimation1);
			isAlpha = false;
			btn2.setText("透明");
		} else {// 透明->不透明
			text1.startAnimation(alphaAnimation2);
			isAlpha = true;
			btn2.setText("不透明");
		}
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				break;
			}
		}
	};
}
