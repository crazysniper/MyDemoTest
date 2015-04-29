package com.example.viewpagerdemo;

import java.util.ArrayList;
import java.util.List;

import com.example.viewpagerdemo.adapter.MyPagerAdapter;

import android.location.GpsStatus.Listener;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;

public class ViewPager1_YiDong_ChangAnTanChuang extends Activity implements OnTouchListener, OnLongClickListener {

	private TextView tv, tv1, tv2, tv3, tv4;
	private ViewPager viewpager;
	private View view1, view2, view3, view4;
	private LinearLayout layout1, layout2, layout3, layout4, pager_item1_parent, pager_item2_parent, pager_item3_parent, pager_item4_parent;
	private Button btn1, btn2, btn3, btn4;
	private EditText et1, et2, et3, et4;

	private LayoutInflater layoutInflater;
	private MyPagerAdapter pagerAdapter;

	private List<View> viewList = new ArrayList<View>(); // 把需要滑动的页卡添加到这个list中
	private List<String> titleList = new ArrayList<String>(); // viewpager的标题
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度

	private int currendtIndex = 0;// 当前页卡编号
	private int screenWidth, screenHeight;
	private int lastX1, lastY1;
	private int lastX2, lastY2;
	private int lastX3, lastY3;
	private int lastX4, lastY4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		tv = (TextView) findViewById(R.id.tv);
		tv.setText("第1个");
		viewpager = (ViewPager) findViewById(R.id.viewpager);

		layoutInflater = LayoutInflater.from(this);
		view1 = layoutInflater.inflate(R.layout.viewpager1, null);
		view2 = layoutInflater.inflate(R.layout.viewpager2, null);
		view3 = layoutInflater.inflate(R.layout.viewpager3, null);
		view4 = layoutInflater.inflate(R.layout.viewpager4, null);

		tv1 = (TextView) view1.findViewById(R.id.tv1);
		tv2 = (TextView) view2.findViewById(R.id.tv2);
		tv3 = (TextView) view3.findViewById(R.id.tv3);
		tv4 = (TextView) view4.findViewById(R.id.tv4);

		pager_item1_parent = (LinearLayout) view1.findViewById(R.id.pager_item1_parent);
		pager_item2_parent = (LinearLayout) view2.findViewById(R.id.pager_item2_parent);
		pager_item3_parent = (LinearLayout) view3.findViewById(R.id.pager_item3_parent);
		pager_item4_parent = (LinearLayout) view4.findViewById(R.id.pager_item4_parent);

		viewList.add(view1);
		viewList.add(view2);
		viewList.add(view3);
		viewList.add(view4);

		pagerAdapter = new MyPagerAdapter(viewList);
		viewpager.setOnPageChangeListener(listener);

		DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		screenWidth = displayMetrics.widthPixels;
		screenHeight = displayMetrics.heightPixels;

		viewpager.setAdapter(pagerAdapter);

		viewpager.setCurrentItem(0);
		viewpager.setOffscreenPageLimit(3);

		// TODO
		tv1.setOnTouchListener(this);
		tv2.setOnTouchListener(this);
		tv3.setOnTouchListener(this);
		tv4.setOnTouchListener(this);

		tv.setOnLongClickListener(this);
		tv1.setOnLongClickListener(this);
		tv2.setOnLongClickListener(this);
		tv3.setOnLongClickListener(this);
		tv4.setOnLongClickListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.tv1:
			switch (event.getAction()) {
			/**
			 * getX()是表示Widget相对于自身左上角的x坐标<br>
			 * ,而getRawX()是表示相对于屏幕左上角的x坐标值<br>
			 * (注意:
			 * 这个屏幕左上角是手机屏幕左上角,不管activity是否有titleBar或是否全屏幕),getY(),getRawY()
			 * 一样的道理
			 */

			case MotionEvent.ACTION_DOWN:
				lastX1 = (int) event.getRawX();
				lastY1 = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				int dx = (int) event.getRawX() - lastX1;
				int dy = (int) event.getRawY() - lastY1;

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
				if (bottom1 > pager_item1_parent.getHeight()) {
					bottom1 = pager_item1_parent.getHeight();
					top1 = bottom1 - v.getHeight();
				}
				v.layout(left1, top1, right1, bottom1);
				lastX1 = (int) event.getRawX();
				lastY1 = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_UP:
				break;
			}
			break;

		case R.id.tv2:
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
				if (bottom1 > pager_item2_parent.getHeight()) {
					bottom1 = pager_item2_parent.getHeight();
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

		case R.id.tv3:
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
				if (bottom1 > pager_item3_parent.getHeight()) {
					bottom1 = pager_item3_parent.getHeight();
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

		case R.id.tv4:
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
				if (bottom1 > pager_item4_parent.getHeight()) {
					bottom1 = pager_item4_parent.getHeight();
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
		return false;
	}

	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()) {
		case R.id.tv:
			AlertDialog.Builder builder0 = new AlertDialog.Builder(ViewPager1_YiDong_ChangAnTanChuang.this);
			builder0.setTitle("已长按顶部textview");
			builder0.setCancelable(true);
			builder0.show();
			break;
		case R.id.tv1:
			AlertDialog.Builder builder = new AlertDialog.Builder(ViewPager1_YiDong_ChangAnTanChuang.this);
			builder.setTitle("已长按第一个textview");
			builder.setCancelable(true);
			builder.show();
			break;
		case R.id.tv2:
			AlertDialog.Builder builder2 = new AlertDialog.Builder(ViewPager1_YiDong_ChangAnTanChuang.this);
			builder2.setTitle("已长按第二个textview");
			builder2.setCancelable(true);
			builder2.show();
			break;
		case R.id.tv3:
			AlertDialog.Builder builder3 = new AlertDialog.Builder(ViewPager1_YiDong_ChangAnTanChuang.this);
			builder3.setTitle("已长按第三个textview");
			builder3.setCancelable(true);
			builder3.show();
			break;
		case R.id.tv4:
			AlertDialog.Builder builder4 = new AlertDialog.Builder(ViewPager1_YiDong_ChangAnTanChuang.this);
			builder4.setTitle("已长按第四个textview");
			builder4.setCancelable(true);
			builder4.show();
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

}
