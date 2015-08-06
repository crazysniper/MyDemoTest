package com.gftest.myappclient.ui;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;

public class Screen extends BaseActivity implements OnClickListener {

	private Button btn1, btn2, btn3;
	private TextView result;

	private DisplayMetrics displayMetrics;// DisplayMetrics的对象还不能在onclick中声明呢，不知道为什么

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screen);
		initView();
	}

	/**
	 * DisplayMetrics和Display的关系。
	 * Display指代显示区域这个对象，它可能是真实的物理屏幕，也可能仅指应用程序的显示区域，比如在非全屏Activity里
	 * ，系统因为有状态栏，因此显示区域要比物理屏幕要小 。DisplayMetrics里封装了显示区域的各种属性值。查看源码发现，
	 * 在DisplayMetrics对各个属性值的注释都说明为真实的物理尺寸
	 * 。而且也发现display.getMetrics(dm)这一函数基本在应用在获取真实屏幕尺寸的时候。记住这一点即好。
	 */

	private void initView() {
		result = (TextView) findViewById(R.id.result);
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);

		displayMetrics = new DisplayMetrics();// 构造函数DisplayMetrics 不需要传递任何参数
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);// 方法将取得的宽高维度存放于DisplayMetrics
																			// 对象中

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			int width1 = displayMetrics.widthPixels;// 540 屏幕宽度（像素）
			int height1 = displayMetrics.heightPixels;// 960 屏幕高度（像素）
			float density1 = displayMetrics.density;// 1.5 屏幕密度（0.75 / 1.0 /
													// 1.5）
													// ，计算方法：屏幕密度dpi是240，通过densityDpi/160=
													// 1.5
			float xdpi1 = displayMetrics.xdpi;// 258.79245
												// 得到屏幕上X/Y方向上每英寸有多少个物理像素
			float ydpi1 = displayMetrics.ydpi;// 256.67368
												// 得到屏幕上X/Y方向上每英寸有多少个物理像素
			int densityDpi1 = displayMetrics.densityDpi;// 240 屏幕密度DPI（120 / 160
														// / 240）
			float scaledDensity1 = displayMetrics.scaledDensity;// 1.5
			result.setText("方法一：" + "\n" + "width1=" + width1 + "\n" + "" + "height1=" + height1 + "\n" + "" + "density1=" + density1 + "\n" + "" + "xdpi1=" + xdpi1 + "\n" + "" + "ydpi1=" + ydpi1 + "\n" + "" + "densityDpi1=" + densityDpi1 + "\n" + "" + "scaledDensity1=" + scaledDensity1 + "\n" + "");
			break;
		case R.id.btn2:
			DisplayMetrics displayMetrics2 = getResources().getDisplayMetrics();
			int width2 = displayMetrics2.widthPixels;// 540
			int height2 = displayMetrics2.heightPixels;// 960
			float density2 = displayMetrics2.density;// 1.5
			float xdpi2 = displayMetrics2.xdpi;// 258.79245
			float ydpi2 = displayMetrics2.ydpi;// 256.67368
			int densityDpi2 = displayMetrics2.densityDpi;// 240
			float scaledDensity2 = displayMetrics2.scaledDensity;// 1.7249999
			result.setText("方法二：" + "\n" + "width2=" + width2 + "\n" + "" + "height2=" + height2 + "\n" + "" + "density2=" + density2 + "\n" + "" + "xdpi2=" + xdpi2 + "\n" + "" + "ydpi2=" + ydpi2 + "\n" + "" + "densityDpi2=" + densityDpi2 + "\n" + "" + "scaledDensity2=" + scaledDensity2 + "\n" + "");
			break;
		case R.id.btn3:// 已经弃用
			Display display = getWindowManager().getDefaultDisplay();
			int width3 = display.getWidth();// 540
			int height3 = display.getHeight();// 960
			result.setText("方法三：" + "width3=" + width3 + "\n" + "height3=" + height3);
			break;
		default:
			break;
		}
	}
}
