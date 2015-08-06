package com.gftest.myappclient.ui.fragment3;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gftest.myappclient.BaseFragment;
import com.gftest.myappclient.R;
import com.gftest.myappclient.wangyi.Indicator;
import com.gftest.myappclient.widget.MyViewPager;

public class Fragment5_3 extends BaseFragment implements OnClickListener {
	private LayoutInflater layoutInflater;
	private Activity activity;
	private View view;

	private MyViewPager viewPager;
	private Indicator mIndicator;
	private TextView mTabOne, mTabTwo, mTabThree, mTabFour;
	private TextView show;
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private Fragment5_3_ViewPagerAdapter adapter;

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
		view = layoutInflater.inflate(R.layout.fragment_5_2, null);

		viewPager = (MyViewPager) view.findViewById(R.id.viewPager);
		mIndicator = (Indicator) view.findViewById(R.id.indicator);
		mTabOne = (TextView) view.findViewById(R.id.tab_one);
		mTabTwo = (TextView) view.findViewById(R.id.tab_two);
		mTabThree = (TextView) view.findViewById(R.id.tab_three);
		mTabFour = (TextView) view.findViewById(R.id.tab_four);
		show = (TextView) view.findViewById(R.id.show);

		fragmentList.clear();
		for (int i = 1; i < 5; i++) {
			Fragment5_3_item fragment5_item = new Fragment5_3_item("文件" + i, activity);
			fragmentList.add(fragment5_item);
		}

		adapter = new Fragment5_3_ViewPagerAdapter(getActivity().getSupportFragmentManager(), viewPager, fragmentList);

		adapter.setOnExtraPageChangeListener(new Fragment5_3_ViewPagerAdapter.OnExtraPageChangeListener() {
			@Override
			public void onExtraPageSelected(int arg0) {
				setColor(arg0);
			}

			@Override
			public void onExtraPageScrolled(int arg0, float arg1, int arg2) {
				mIndicator.scroll(arg0, arg1);
			}
		});

		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(0);
		viewPager.setOffscreenPageLimit(1);

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

	public void setColor(int position) {
		mTabOne.setTextColor(Color.parseColor("#909090"));
		mTabTwo.setTextColor(Color.parseColor("#909090"));
		mTabThree.setTextColor(Color.parseColor("#909090"));
		mTabFour.setTextColor(Color.parseColor("#909090"));
		switch (position) {
		case 0:
			mTabOne.setTextColor(Color.parseColor("#51c024"));
			break;
		case 1:
			mTabTwo.setTextColor(Color.parseColor("#51c024"));
			break;
		case 2:
			mTabThree.setTextColor(Color.parseColor("#51c024"));
			break;
		case 3:
			mTabFour.setTextColor(Color.parseColor("#51c024"));
			break;
		default:
			break;
		}
	}

}
