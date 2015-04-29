package com.example.draftdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Second extends Activity implements OnTouchListener, OnClickListener {

	String TAG = "Second";
	private ImageView take_pic, back, album, change_camera, guide_slide_1, guide_slide_2;
	private FrameLayout frame;
	private TextView tv1, dialog;
	private int screenWidth, screenHeight;
	private int lastX, lastY;
	private List<View> viewList = new ArrayList<View>();
	private View view1, view2, view3;
	private LayoutInflater layoutInflater;
	private LinearLayout item1, item2, item3;
	private int marginLeft, marginTop, marginRight, marginBottom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		initView();
	}

	private void initView() {
		back = (ImageView) findViewById(R.id.close);// 返回
		take_pic = (ImageView) findViewById(R.id.take_pic);// 拍照
		album = (ImageView) findViewById(R.id.album);// 左下方的相册预览
		frame = (FrameLayout) findViewById(R.id.frame);// 水印viewpager的父控件
		change_camera = (ImageView) findViewById(R.id.change_camera);// 前后摄像头切换
		tv1 = (TextView) findViewById(R.id.tv1);
		dialog = (TextView) findViewById(R.id.dialog);

		layoutInflater = LayoutInflater.from(this);
		view1 = layoutInflater.inflate(R.layout.item1, null);
		view2 = layoutInflater.inflate(R.layout.item2, null);
		view3 = layoutInflater.inflate(R.layout.item3, null);

		item1 = (LinearLayout) view1.findViewById(R.id.item1);
		item2 = (LinearLayout) view2.findViewById(R.id.item2);
		item3 = (LinearLayout) view3.findViewById(R.id.item3);

		viewList.add(view1);
		viewList.add(view2);
		viewList.add(view3);

		tv1.setOnTouchListener(this);
		dialog.setOnClickListener(this);
		item1.setOnTouchListener(this);
		item2.setOnTouchListener(this);
		item3.setOnTouchListener(this);

		DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		screenWidth = displayMetrics.widthPixels;
		screenHeight = displayMetrics.heightPixels;
	}

	/**
	 * 添加文案View值主ui
	 * 
	 * @param position
	 */
	public void showWenAn(int position) {
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER);

		System.out.println("------------------");
		System.out.println("出现时距离上边：" + marginTop);
		System.out.println("出现时距离下边：" + marginBottom);
		System.out.println("出现时距离左边：" + marginLeft);
		System.out.println("出现时距离右边：" + marginBottom);
		// System.out.println("子控件个数=" + frame.getChildCount());
		frame.removeAllViews();
		// if (frame.getChildCount() == 2) {
		// frame.removeViewAt(1);
		// }
		// if (frame.getChildCount() > 1) {
		// frame.removeViews(1, frame.getChildCount() - 1);
		// }
		// frame.addView(viewList.get(position), layoutParams);
		frame.addView(viewList.get(position), layoutParams);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog:
			AlertDialog.Builder dialog = new AlertDialog.Builder(Second.this);
			dialog.setTitle("选择文案");
			String[] strArr = { "文案1", "文案2", "文案3" };
			dialog.setItems(strArr, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					showWenAn(which);
				}
			});
			dialog.show();
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.item1:
			switch (event.getAction()) {
			/**
			 * getX()是表示Widget相对于自身左上角的x坐标<br>
			 * ,而getRawX()是表示相对于屏幕左上角的x坐标值<br>
			 * (注意:
			 * 这个屏幕左上角是手机屏幕左上角,不管activity是否有titleBar或是否全屏幕),getY(),getRawY()
			 * 一样的道理
			 */

			case MotionEvent.ACTION_DOWN:
				lastX = (int) event.getRawX();
				lastY = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				int dx = (int) event.getRawX() - lastX;
				int dy = (int) event.getRawY() - lastY;

				int left1 = v.getLeft() + dx;// getLeft()方法得到的是控件坐标距离父控件原点(左上角，坐标（0，0）)的x轴距离
				int top1 = v.getTop() + dy;// getTop和getButtom是距离的y轴距离。
				int right1 = v.getRight() + dx;// getReght()是控件右边距离父控件原点的x轴距离
				int bottom1 = v.getBottom() + dy;
				// System.out.println("顶部距离父控件的距离=" + top1);
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
				if (bottom1 > frame.getHeight()) {
					bottom1 = frame.getHeight();
					top1 = bottom1 - v.getHeight();
				}
				v.layout(left1, top1, right1, bottom1);
				lastX = (int) event.getRawX();
				lastY = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_UP:
				break;
			}
		}
		return false;
	}

}
