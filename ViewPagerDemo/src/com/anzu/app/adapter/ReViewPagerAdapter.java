package com.anzu.app.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ReViewPagerAdapter extends PagerAdapter{

	private List<View> views;

	public ReViewPagerAdapter(List<View> views, Context context) {
		super();
		this.views = views;
	}

	@Override
	public int getCount() { // ��ȡ��ǰ���������
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) { // �ж��Ƿ��ɶ������ɽ���
		return view == (object);
	}

	@Override
	public void destroyItem(View container, int position, Object object) { // ����positionλ�õĽ���
		try {
			((ViewPager) container).removeView(views.get(position));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object instantiateItem(View container, int position) { // ��ʼ��positionλ�õĽ���
		try {
			((ViewPager) container).addView(views.get(position), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return views.get(position);
	}


}
