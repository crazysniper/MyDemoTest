package com.gftest.myappclient.wangyi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import android.view.ViewGroup;

public class QuanFragmentViewPagerAdapter extends FragmentStatePagerAdapter {

	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private List<String> titleList = new ArrayList<String>();
	private FragmentManager fragmentManager;

	public QuanFragmentViewPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
		this.fragmentManager = fragmentManager;
	}

	public QuanFragmentViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, List<String> titleList) {
		super(fragmentManager);
		this.fragmentManager = fragmentManager;
		this.fragmentList = fragmentList;
		this.titleList = titleList;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titleList.get(position);
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragmentList.get(arg0);
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	public void setFragments(List<Fragment> fragmentList) {
		if (this.fragmentList != null) {
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			for (Fragment fragment : this.fragmentList) {
				fragmentTransaction.remove(fragment);
			}
			fragmentTransaction.commit();
			fragmentTransaction = null;
			fragmentManager.executePendingTransactions();
		}
		this.fragmentList = fragmentList;
		notifyDataSetChanged();
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		Object obj = super.instantiateItem(container, position);
		return obj;
	}
}
