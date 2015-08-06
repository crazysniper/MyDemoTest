package com.gftest.myappclient.ui.viewpager;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gftest.myappclient.R;
import com.gftest.myappclient.utils.Utils;

public class ViewPager_Fragment extends FragmentActivity implements OnClickListener {
	private ViewPager viewPager;
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private ViewPager_Fragment_Adapter adapter;
	private TextView line;
	private TextView mTabOne, mTabTwo, mTabThree, mTabFour;
	private TextView show;
	/** Item宽度 */
	private int itemWidth = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_viewpager);
		initView();
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		line = (TextView) findViewById(R.id.line);
		mTabOne = (TextView) findViewById(R.id.tab_one);
		mTabTwo = (TextView) findViewById(R.id.tab_two);
		mTabThree = (TextView) findViewById(R.id.tab_three);
		mTabFour = (TextView) findViewById(R.id.tab_four);
		show = (TextView) findViewById(R.id.show);

		itemWidth = (Utils.getDeviceSize(ViewPager_Fragment.this).x - Utils.dip2px(ViewPager_Fragment.this, 42)) / 4;
		LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(itemWidth, Utils.dip2px(ViewPager_Fragment.this, 2));
		line.setLayoutParams(layoutParams3);

		fragmentList.clear();
		for (int i = 1; i < 5; i++) {
			ViewPager_Fragment_Item fragment5_item = new ViewPager_Fragment_Item("文件" + i, ViewPager_Fragment.this);
			fragmentList.add(fragment5_item);
		}
		adapter = new ViewPager_Fragment_Adapter(getSupportFragmentManager(), fragmentList);
		viewPager.setAdapter(adapter);
		viewPager.setOffscreenPageLimit(1);

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// viewPager.setCurrentItem(position);
				// lineScroll(position * itemWidth);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				int left = (int) (positionOffset * itemWidth) + position * itemWidth;
				lineScroll(left);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
		mTabOne.setOnClickListener(this);
		mTabTwo.setOnClickListener(this);
		mTabThree.setOnClickListener(this);
		mTabFour.setOnClickListener(this);
		show.setOnClickListener(this);
	}

	/**
	 * 线条滑动
	 * 
	 * @param left
	 */
	private void lineScroll(int left) {
		LinearLayout.LayoutParams layoutParams = ((LinearLayout.LayoutParams) line.getLayoutParams());
		layoutParams.leftMargin = left;
		line.setLayoutParams(layoutParams);
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
}
