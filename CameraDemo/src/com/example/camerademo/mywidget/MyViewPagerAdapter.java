package com.example.camerademo.mywidget;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 水印横屏切换ViewPagerAdapter
 * 
 */
public class MyViewPagerAdapter extends PagerAdapter {

	private List<View> viewList;

	public MyViewPagerAdapter(List<View> viewList) {
		this.viewList = viewList;
	}

	@Override
	public int getCount() {
		return viewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(viewList.get(position));
	}

	/**
	 * 实例化页卡
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(viewList.get(position));
		return viewList.get(position);
	}

}
