package com.example.viewpagerdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.example.viewpagerdemo.adapter.MyPagerAdapter;

public class YiDongActivity extends Activity implements OnTouchListener {
	private ViewPager viewpager;
	private LayoutInflater layoutInflater;
	private TextView tv, textView1, textView2, textView3, textView4;
	private View view1, view2, view3, view4;
	private RelativeLayout layout1, layout2, layout3, layout4;
	private MyPagerAdapter pagerAdapter;
	private List<View> viewList = new ArrayList<View>(); // 把需要滑动的页卡添加到这个list中
	private List<String> titleList = new ArrayList<String>(); // viewpager的标题

	private int screenWidth, screenHeight;
	private int lastX1, lastY1;
	private int lastX2, lastY2;
	private int lastX3, lastY3;
	private int lastX4, lastY4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yidong);
		initView();
	}

	private void initView() {
		tv = (TextView) findViewById(R.id.tv);
		tv.setText("第1个");
		viewpager = (ViewPager) findViewById(R.id.viewpager);

		layoutInflater = LayoutInflater.from(this);

		DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		screenWidth = displayMetrics.widthPixels;
		screenHeight = displayMetrics.heightPixels;

		setView();
		viewList.add(layout1);
		viewList.add(layout2);
		viewList.add(layout3);
		viewList.add(layout4);

		pagerAdapter = new MyPagerAdapter(viewList);

		viewpager.setAdapter(pagerAdapter);

		viewpager.setOnPageChangeListener(listener);
		viewpager.setCurrentItem(0);
		viewpager.setOffscreenPageLimit(3);

		// layout1.setOnTouchListener(this);
		// layout2.setOnTouchListener(this);
		// layout3.setOnTouchListener(this);
		// layout4.setOnTouchListener(this);

		textView1.setOnTouchListener(this);
		textView2.setOnTouchListener(this);
		textView3.setOnTouchListener(this);
		textView4.setOnTouchListener(this);

		// tv.setOnLongClickListener(this);
		// layout1.setOnLongClickListener(this);
		// layout2.setOnLongClickListener(this);
		// layout3.setOnLongClickListener(this);
		// layout4.setOnLongClickListener(this);
	}

	private void setView() {
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layout1 = (RelativeLayout) layoutInflater.inflate(R.layout.add_img_item, null);
		textView1 = new TextView(this);
		textView1.setText("1");
		textView1.setTextSize(20);
		textView1.setTextColor(Color.WHITE);
		textView1.setBackgroundColor(Color.BLACK);
		textView1.setLines(2);
		textView1.setGravity(Gravity.CENTER);
		textView1.setLayoutParams(layoutParams);
		textView1.setId(1);
		layout1.setId(1);
		layout1.addView(textView1);

		layout2 = (RelativeLayout) layoutInflater.inflate(R.layout.add_img_item, null);
		textView2 = new TextView(this);
		textView2.setText("2");
		textView2.setTextSize(20);
		textView2.setTextColor(Color.WHITE);
		textView2.setBackgroundColor(Color.BLACK);
		textView2.setLines(2);
		textView2.setGravity(Gravity.CENTER);
		textView2.setLayoutParams(layoutParams);
		textView2.setId(2);
		layout2.setId(2);
		layout2.addView(textView2);

		layout3 = (RelativeLayout) layoutInflater.inflate(R.layout.add_img_item, null);
		textView3 = new TextView(this);
		textView3.setText("3");
		textView3.setTextSize(20);
		textView3.setTextColor(Color.WHITE);
		textView3.setBackgroundColor(Color.BLACK);
		textView3.setLines(2);
		textView3.setGravity(Gravity.CENTER);
		textView3.setLayoutParams(layoutParams);
		textView3.setId(3);
		layout3.setId(3);
		layout3.addView(textView3);

		layout4 = (RelativeLayout) layoutInflater.inflate(R.layout.add_img_item, null);
		textView4 = new TextView(this);
		textView4.setText("4");
		textView4.setTextSize(20);
		textView4.setTextColor(Color.WHITE);
		textView4.setBackgroundColor(Color.BLACK);
		textView4.setLines(2);
		textView4.setGravity(Gravity.CENTER);
		textView4.setLayoutParams(layoutParams);
		textView4.setId(4);
		layout4.setId(4);
		layout4.addView(textView4);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		System.out.println("v.getId()=" + v.getId());
		switch (v.getId()) {
		case 1:
			switch (event.getAction()) {
			/**
			 * getX()是表示Widget相对于自身左上角的x坐标<br>
			 * ,而getRawX()是表示相对于屏幕左上角的x坐标值<br>
			 * (注意:
			 * 这个屏幕左上角是手机屏幕左上角,不管activity是否有titleBar或是否全屏幕),getY(),getRawY()
			 * 一样的道理
			 */

			case MotionEvent.ACTION_DOWN:
				System.out.println("按下");
				lastX1 = (int) event.getRawX();
				lastY1 = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				System.out.println("移动");
				int dx = (int) event.getRawX() - lastX1;
				int dy = (int) event.getRawY() - lastY1;

				System.out.println("-------------");
				System.out.println("dx=" + dx);
				System.out.println("dy=" + dy);

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
				if (bottom1 > layout1.getHeight()) {
					bottom1 = layout1.getHeight();
					top1 = bottom1 - v.getHeight();
				}
				v.layout(left1, top1, right1, bottom1);
				lastX1 = (int) event.getRawX();
				lastY1 = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_UP:
				System.out.println("抬起");
				break;
			}
			break;

		case 2:
			switch (event.getAction()) {
			/**
			 * getX()是表示Widget相对于自身左上角的x坐标<br>
			 * ,而getRawX()是表示相对于屏幕左上角的x坐标值<br>
			 * (注意:
			 * 这个屏幕左上角是手机屏幕左上角,不管activity是否有titleBar或是否全屏幕),getY(),getRawY()
			 * 一样的道理
			 */

			case MotionEvent.ACTION_DOWN:
				lastX2 = (int) event.getRawX();
				lastY2 = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				int dx = (int) event.getRawX() - lastX2;
				int dy = (int) event.getRawY() - lastY2;

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
				if (bottom1 > layout2.getHeight()) {
					bottom1 = layout2.getHeight();
					top1 = bottom1 - v.getHeight();
				}
				v.layout(left1, top1, right1, bottom1);
				lastX2 = (int) event.getRawX();
				lastY2 = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_UP:
				break;
			}
			break;

		case 3:
			switch (event.getAction()) {
			/**
			 * getX()是表示Widget相对于自身左上角的x坐标<br>
			 * ,而getRawX()是表示相对于屏幕左上角的x坐标值<br>
			 * (注意:
			 * 这个屏幕左上角是手机屏幕左上角,不管activity是否有titleBar或是否全屏幕),getY(),getRawY()
			 * 一样的道理
			 */

			case MotionEvent.ACTION_DOWN:
				lastX3 = (int) event.getRawX();
				lastY3 = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				int dx = (int) event.getRawX() - lastX3;
				int dy = (int) event.getRawY() - lastY3;

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
				if (bottom1 > layout3.getHeight()) {
					bottom1 = layout3.getHeight();
					top1 = bottom1 - v.getHeight();
				}
				v.layout(left1, top1, right1, bottom1);
				lastX3 = (int) event.getRawX();
				lastY3 = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_UP:
				break;
			}
			break;

		case 4:
			switch (event.getAction()) {
			/**
			 * getX()是表示Widget相对于自身左上角的x坐标<br>
			 * ,而getRawX()是表示相对于屏幕左上角的x坐标值<br>
			 * (注意:
			 * 这个屏幕左上角是手机屏幕左上角,不管activity是否有titleBar或是否全屏幕),getY(),getRawY()
			 * 一样的道理
			 */

			case MotionEvent.ACTION_DOWN:
				lastX4 = (int) event.getRawX();
				lastY4 = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				int dx = (int) event.getRawX() - lastX4;
				int dy = (int) event.getRawY() - lastY4;

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
				if (bottom1 > layout4.getHeight()) {
					bottom1 = layout4.getHeight();
					top1 = bottom1 - v.getHeight();
				}
				v.layout(left1, top1, right1, bottom1);
				lastX4 = (int) event.getRawX();
				lastY4 = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_UP:
				break;
			}
			break;
		}
		return true;
	}

	OnPageChangeListener listener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			tv.setText("第" + (arg0 + 1) + "个");
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};

	// @Override
	// public boolean onLongClick(View v) {
	// System.out.println("长按的id：" + v.getId());
	// switch (v.getId()) {
	// case R.id.tv:
	// AlertDialog.Builder builder0 = new
	// AlertDialog.Builder(YiDongActivity.this);
	// builder0.setTitle("已长按顶部textview");
	// builder0.setCancelable(true);
	// builder0.show();
	// break;
	// case 1:
	// AlertDialog.Builder builder = new
	// AlertDialog.Builder(YiDongActivity.this);
	// builder.setTitle("已长按第一个textview");
	// builder.setCancelable(true);
	// builder.show();
	// break;
	// case 2:
	// AlertDialog.Builder builder2 = new
	// AlertDialog.Builder(YiDongActivity.this);
	// builder2.setTitle("已长按第二个textview");
	// builder2.setCancelable(true);
	// builder2.show();
	// break;
	// case 3:
	// AlertDialog.Builder builder3 = new
	// AlertDialog.Builder(YiDongActivity.this);
	// builder3.setTitle("已长按第三个textview");
	// builder3.setCancelable(true);
	// builder3.show();
	// break;
	// case 4:
	// AlertDialog.Builder builder4 = new
	// AlertDialog.Builder(YiDongActivity.this);
	// builder4.setTitle("已长按第四个textview");
	// builder4.setCancelable(true);
	// builder4.show();
	// break;
	// }
	// return true;
	// }

}
