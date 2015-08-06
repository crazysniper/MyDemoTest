package com.gftest.myappclient.ui.fragment3;

import java.util.ArrayList;
import java.util.List;

import com.gftest.myappclient.widget.MyViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

public class Fragment5_3_ViewPagerAdapter extends FragmentPagerAdapter implements MyViewPager.OnPageChangeListener {
	private List<Fragment> fragments = new ArrayList<Fragment>();
	private FragmentManager fm;
	private MyViewPager viewPager; // viewPager对象
	private OnExtraPageChangeListener onExtraPageChangeListener; // ViewPager切换页面时的额外功能添加接口

	public Fragment5_3_ViewPagerAdapter(FragmentManager fm) {
		super(fm);
		this.fm = fm;
	}

	public Fragment5_3_ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fm = fm;
		this.fragments = fragments;
	}

	public Fragment5_3_ViewPagerAdapter(FragmentManager fm, MyViewPager viewPager, List<Fragment> fragments) {
		super(fm);
		this.fm = fm;
		this.viewPager = viewPager;
		this.fragments = fragments;
		this.viewPager.setOnPageChangeListener(this);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	public void setFragments(ArrayList<Fragment> fragments) {
		if (this.fragments != null) {
			FragmentTransaction ft = fm.beginTransaction();
			for (Fragment f : this.fragments) {
				ft.remove(f);
			}
			ft.commit();
			ft = null;
			fm.executePendingTransactions();
		}
		this.fragments = fragments;
		notifyDataSetChanged();
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		Object obj = super.instantiateItem(container, position);
		return obj;
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		if (null != onExtraPageChangeListener) { // 如果设置了额外功能接口
			onExtraPageChangeListener.onExtraPageScrolled(arg0, arg1, arg2);
		}
	}

	@Override
	public void onPageSelected(int position) {
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		if (null != onExtraPageChangeListener) { // 如果设置了额外功能接口
			onExtraPageChangeListener.onExtraPageScrollStateChanged(arg0);
		}
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
