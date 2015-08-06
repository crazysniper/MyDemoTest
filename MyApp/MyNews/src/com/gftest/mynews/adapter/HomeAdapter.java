package com.gftest.mynews.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class HomeAdapter extends FragmentStatePagerAdapter {

	private FragmentManager fragmentManager;
	private List<Fragment> fragmentList = new ArrayList<Fragment>();

	public HomeAdapter(FragmentManager fm) {
		super(fm);
		this.fragmentManager = fm;
	}

	public HomeAdapter(FragmentManager fm, List<Fragment> fragmentList) {
		super(fm);
		this.fragmentManager = fm;
		this.fragmentList = fragmentList;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}

}
