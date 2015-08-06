/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package com.gftest.myappclient.xlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gftest.myappclient.R;

public class XListViewHeader extends LinearLayout {
	/** 布局容器，也就是根布局 */
	private LinearLayout mContainer;
	private ImageView mArrowImageView;
	private ProgressBar mProgressBar;
	private TextView mHintTextView;

	/** 记录当前的状态 */
	private int mState = STATE_NORMAL;

	/** 用于改变箭头的方向的动画 ，往下拖动时，尖头的变化动画 */
	private Animation mRotateUpAnim;
	/** 用于改变箭头的方向的动画 */
	private Animation mRotateDownAnim;
	/** 动画持续时间 */
	private final int ROTATE_ANIM_DURATION = 180;

	/** 正常状态 */
	public final static int STATE_NORMAL = 0;
	/** 准备刷新状态，也就是箭头方向发生改变之后的状态 */
	public final static int STATE_READY = 1;
	/** 刷新状态，箭头变成了progressBar */
	public final static int STATE_REFRESHING = 2;

	public XListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public XListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		// 初始情况，设置下拉刷新view高度为0,这是为了隐藏header
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.xlistview_header, null);
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);// 这行的代码目的是：
									// 当下拉的距离过大时，headerView的顶部描述部分应该居于底部

		mArrowImageView = (ImageView) findViewById(R.id.xlistview_header_arrow);
		mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);
		mProgressBar = (ProgressBar) findViewById(R.id.xlistview_header_progressbar);

		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
	}

	/**
	 * 设置header的状态,完成控件隐藏、显示、改变文字等操作
	 * 
	 * @param state
	 */
	public void setState(int state) {
		if (state == mState)
			return;

		if (state == STATE_REFRESHING) { // 显示进度
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.INVISIBLE);
			mProgressBar.setVisibility(View.VISIBLE);
		} else { // 显示箭头图片
			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.INVISIBLE);
		}

		switch (state) {
		case STATE_NORMAL:
			if (mState == STATE_READY) {
				mArrowImageView.startAnimation(mRotateDownAnim);
			}
			if (mState == STATE_REFRESHING) {
				mArrowImageView.clearAnimation();
			}
			mHintTextView.setText(R.string.xlistview_header_hint_normal);
			break;
		case STATE_READY:
			if (mState != STATE_READY) {
				mArrowImageView.clearAnimation();
				mArrowImageView.startAnimation(mRotateUpAnim);
				mHintTextView.setText(R.string.xlistview_header_hint_ready);
			}
			break;
		case STATE_REFRESHING:
			mHintTextView.setText(R.string.xlistview_header_hint_loading);
			break;
		default:
		}

		mState = state;
	}

	/**
	 * 设置Header中根布局文件的高度属性,完成拉伸的效果
	 * 
	 * @param height
	 */
	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContainer.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	/**
	 * 获取Header中根布局文件的高度属性,完成收缩的效果
	 * 
	 * @return
	 */
	public int getVisiableHeight() {
		return mContainer.getHeight();
	}

	/**
	 * 完成显示的效果
	 */
	public void show() {
		mContainer.setVisibility(View.VISIBLE);
	}

	/**
	 * 完成隐藏的效果
	 */
	public void hide() {
		mContainer.setVisibility(View.INVISIBLE);
	}

	public void setHeaderBackground(int headerBackground) {
		mContainer.setBackgroundResource(headerBackground);
	}
}
