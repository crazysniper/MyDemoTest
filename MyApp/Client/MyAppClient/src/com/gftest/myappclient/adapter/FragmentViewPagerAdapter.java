package com.gftest.myappclient.adapter;

import java.util.List;

import com.gftest.myappclient.widget.MyViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * http://www.tuicool.com/articles/qqmmmu
 * 
 * @author Gao
 * 
 */
public class FragmentViewPagerAdapter extends PagerAdapter implements MyViewPager.OnPageChangeListener {
	private List<Fragment> fragments; // 每个Fragment对应一个Page
	private FragmentManager fragmentManager;
	private MyViewPager viewPager; // viewPager对象
	private int currentPageIndex = 0; // 当前page索引（切换之前）
	private OnExtraPageChangeListener onExtraPageChangeListener; // ViewPager切换页面时的额外功能添加接口

	public FragmentViewPagerAdapter(FragmentManager fragmentManager, MyViewPager viewPager, List<Fragment> fragments) {
		this.fragments = fragments;
		this.fragmentManager = fragmentManager;
		this.viewPager = viewPager;
		this.viewPager.setAdapter(this);
		this.viewPager.setOnPageChangeListener(this);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(fragments.get(position).getView()); // 移出viewpager两边之外的page布局
	}

	/**
	 * 在给定的位置创建页面, PageAdapter负责向指定的position位置添加View页面;
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Fragment fragment = fragments.get(position);
		if (!fragment.isAdded()) { // 如果fragment还没有added
			FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.add(fragment, fragment.getClass().getSimpleName());
			ft.commit();
			/**
			 * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
			 * 会在进程的主线程中，用异步的方式来执行。 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
			 * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
			 */
			fragmentManager.executePendingTransactions();
		}
		if (fragment.getView().getParent() == null) {
			container.addView(fragment.getView()); // 为viewpager增加布局
		}
		return fragment.getView();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		if (null != onExtraPageChangeListener) { // 如果设置了额外功能接口
			onExtraPageChangeListener.onExtraPageScrollStateChanged(arg0);
		}
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		if (null != onExtraPageChangeListener) { // 如果设置了额外功能接口
			onExtraPageChangeListener.onExtraPageScrolled(arg0, arg1, arg2);
		}
	}

	@Override
	public void onPageSelected(int arg0) {
		fragments.get(currentPageIndex).setUserVisibleHint(false);
		fragments.get(currentPageIndex).onPause(); // 调用切换前Fargment的onPause()
		// fragments.get(currentPageIndex).onStop(); // 调用切换前Fargment的onStop()
		if (fragments.get(arg0).isAdded()) {
			// fragments.get(i).onStart(); // 调用切换后Fargment的onStart()
			fragments.get(arg0).onResume(); // 调用切换后Fargment的onResume()
			fragments.get(arg0).setUserVisibleHint(true);
		}
		currentPageIndex = arg0;
		if (null != onExtraPageChangeListener) { // 如果设置了额外功能接口
			onExtraPageChangeListener.onExtraPageSelected(arg0);
		}
	}

	/**
	 * 当前page索引（切换之前）
	 * 
	 * @return
	 */
	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public OnExtraPageChangeListener getOnExtraPageChangeListener() {
		return onExtraPageChangeListener;
	}

	/**
	 * 设置页面切换额外功能监听器
	 * 
	 * @param onExtraPageChangeListener
	 */
	public void setOnExtraPageChangeListener(OnExtraPageChangeListener onExtraPageChangeListener) {
		this.onExtraPageChangeListener = onExtraPageChangeListener;
	}

	/**
	 * page切换额外功能接口
	 */
	public static class OnExtraPageChangeListener {
		public void onExtraPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onExtraPageSelected(int arg0) {
		}

		public void onExtraPageScrollStateChanged(int arg0) {
		}
	}
}
