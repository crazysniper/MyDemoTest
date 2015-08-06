package com.gftest.myappclient.ui.home;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gftest.myappclient.BaseFragment;
import com.gftest.myappclient.R;
import com.gftest.myappclient.wangyi.Indicator;

public class Fragment5 extends BaseFragment implements OnClickListener {
	private LayoutInflater layoutInflater;
	private Activity activity;
	private View view;

	private ViewPager viewPager;
	private Indicator mIndicator;
	private TextView mTabOne, mTabTwo, mTabThree, mTabFour;
	private TextView show;
	private List<TextView> mViews = new ArrayList<TextView>(4);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			initView();
			return view;
		} else {
			ViewGroup viewGroup = (ViewGroup) view.getParent();
			if (viewGroup != null) {
				viewGroup.removeAllViews();
			}
			return view;
		}
	}

	private void initView() {
		activity = getActivity();
		layoutInflater = activity.getLayoutInflater();
		view = layoutInflater.inflate(R.layout.fragment_5, null);

		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		mIndicator = (Indicator) view.findViewById(R.id.indicator);
		mTabOne = (TextView) view.findViewById(R.id.tab_one);
		mTabTwo = (TextView) view.findViewById(R.id.tab_two);
		mTabThree = (TextView) view.findViewById(R.id.tab_three);
		mTabFour = (TextView) view.findViewById(R.id.tab_four);
		show = (TextView) view.findViewById(R.id.show);

		for (int i = 1; i < 5; i++) {
			TextView tv = new TextView(activity);
			tv.setText("hello android" + i);
			mViews.add(tv);
		}
		viewPager.setAdapter(pagerAdapter);

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				mIndicator.scroll(position, positionOffset);
			}

			@Override
			public void onPageScrollStateChanged(int position) {

			}
		});

		mTabOne.setOnClickListener(this);
		mTabTwo.setOnClickListener(this);
		mTabThree.setOnClickListener(this);
		mTabFour.setOnClickListener(this);
		show.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tab_one:
			viewPager.setCurrentItem(0);
			break;
		case R.id.tab_two:
			viewPager.setCurrentItem(1);
			break;
		case R.id.tab_three:
			viewPager.setCurrentItem(2);
			break;
		case R.id.tab_four:
			viewPager.setCurrentItem(3);
			break;
		case R.id.show:
			System.out.println("111111111111");
			break;
		}
	}

	PagerAdapter pagerAdapter = new PagerAdapter() {
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			return mViews.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = mViews.get(position);
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mViews.get(position));
		}
	};
}
