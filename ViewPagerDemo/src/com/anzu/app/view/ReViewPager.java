package com.anzu.app.view;

import java.util.List;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.anzu.app.adapter.ReViewPagerAdapter;

public class ReViewPager extends LinearLayout {

	private static final int FLING_MIN_DISTANCE = 120;// 移动最小距离
	private static final int FLING_MIN_VELOCITY = 200;// 移动最大速度

	private Context mContext;
	public static boolean isCenter;
	private ViewPager mViewPager;
	private List<View> mViews;
	private boolean mIsActionEndPage;
	private GestureDetector mGDetector; // 用户滑动

	/**
	 * 只需要调用这个监听即可
	 * @param listener 
	 * @param isActionEndPage 当滑倒最后一页，再滑动是否需要触发监听
	 */
	public void setOnChangeListener(ViewListener listener,boolean isActionEndPage) { 
		mPageListener = listener;
		mIsActionEndPage = isActionEndPage;
	}

	public ViewListener mPageListener;

	public interface ViewListener { // 声明接口
		public void onPageSelected(int arg0);

		public void onPageScrolled(int arg0, float arg1, int arg2);

		public void onPageScrollStateChanged(int arg0);

		public void onEndPageSelected();
	}

	public ReViewPager(Context context) { // 初始化时调用的构造函数
		super(context);
		mContext = context;
	}

	public ReViewPager(Context context, AttributeSet attrs) { // 在布局中定义时调用的构造函数
		super(context, attrs);
		mContext = context;
		mGDetector = new GestureDetector(new GuideViewTouch());
		mViewPager = new ViewPager(mContext);
		mViewPager.setOnPageChangeListener(mListener);
		addView(mViewPager, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	}

	public int getCurrItem() {
		return mViewPager.getCurrentItem();
	}

	public void setCurrItem(int item) {
		mViewPager.setCurrentItem(item);
	}
	
	public static boolean isEmpty(List<?> list) {
		if (list == null || list.size() == 0)
			return true;
		return false;
	}

	public void setAdapter(List<View> views) {
		if (!isEmpty(views)) {
			mViews = views;
			ReViewPagerAdapter vAdapter = new ReViewPagerAdapter(mViews, mContext);
			mViewPager.setAdapter(vAdapter);
			mViewPager.setCurrentItem(0);
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (mGDetector.onTouchEvent(ev)) {
			ev.setAction(MotionEvent.ACTION_CANCEL);
		}
		return super.dispatchTouchEvent(ev);
	}

	private OnPageChangeListener mListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			if (arg0 == mViews.size() - 1) {
				isCenter = true;
			}
			if (mPageListener != null)
				mPageListener.onPageSelected(arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			if (mPageListener != null)
				mPageListener.onPageScrolled(arg0, arg1, arg2);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			if (mPageListener != null)
				mPageListener.onPageScrollStateChanged(arg0);
		}
	};

	private class GuideViewTouch extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if (isCenter) {
				if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) { // 手势向右滑动
					if (mPageListener != null && mIsActionEndPage)
						mPageListener.onEndPageSelected();
				}
			}
			return false;
		}
	}

}
