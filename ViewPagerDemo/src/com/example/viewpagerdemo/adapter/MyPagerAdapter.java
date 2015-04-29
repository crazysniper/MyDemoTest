package com.example.viewpagerdemo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class MyPagerAdapter extends PagerAdapter {

	private List<View> viewList = new ArrayList<View>(); // 把需要滑动的页卡添加到这个list中

	public MyPagerAdapter(List<View> viewList) {
		this.viewList = viewList;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public int getCount() {
		return viewList.size();
	}

	/**
	 * 删除页卡
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(viewList.get(position));
	}

	/**
	 * 实例化页卡
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager) container).addView(viewList.get(position));
		return viewList.get(position);
	}

}
