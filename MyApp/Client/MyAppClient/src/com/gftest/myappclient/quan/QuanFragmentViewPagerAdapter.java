package com.gftest.myappclient.quan;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import android.view.ViewGroup;

public class QuanFragmentViewPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private FragmentManager fragmentManager;

	public QuanFragmentViewPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
		this.fragmentManager = fragmentManager;
	}

	public QuanFragmentViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
		super(fragmentManager);
		this.fragmentManager = fragmentManager;
		this.fragmentList = fragmentList;
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragmentList.get(arg0);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		Object obj = super.instantiateItem(container, position);
		return obj;
	}
}
