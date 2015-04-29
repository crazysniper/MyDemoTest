package com.example.viewpagerdemo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.ViewGroup;

import com.example.viewpagerdemo.R;

public class MyDemo extends FragmentActivity {

	private ViewPager viewPager;
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private ImagePagerAdapter2 adapter2;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_mydemo);
		initView();
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewPager);

		for (int i = 1; i < 5; i++) {
			MyFragment fragment = new MyFragment(MyDemo.this, i);
			fragmentList.add(fragment);
		}

		adapter2 = new ImagePagerAdapter2(getSupportFragmentManager(), fragmentList);
		viewPager.setAdapter(adapter2);
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				System.out.println("选中的页面是=" + arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	private class ImagePagerAdapter2 extends FragmentStatePagerAdapter {

		private List<Fragment> fragmentList = new ArrayList<Fragment>();
		private FragmentManager fragmentManager;

		public ImagePagerAdapter2(FragmentManager fragmentManager) {
			super(fragmentManager);
			this.fragmentManager = fragmentManager;
		}

		public ImagePagerAdapter2(FragmentManager fragmentManager, List<Fragment> fragmentList) {
			super(fragmentManager);
			this.fragmentManager = fragmentManager;
			this.fragmentList = fragmentList;
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
}
