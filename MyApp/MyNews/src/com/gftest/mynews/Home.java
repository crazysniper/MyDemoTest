package com.gftest.mynews;

import java.util.ArrayList;
import java.util.List;

import com.gftest.mynews.slidingmenu.SlidingMenu;
import com.gftest.mynews.slidingmenu.app.SlidingFragmentActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Window;

public class Home extends SlidingFragmentActivity {

	private ViewPager viewPager;
	private List<Fragment> fragmentList = new ArrayList<Fragment>();

	private SlidingMenu menu;

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		initView();
		initMenu();
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewPager);
	}

	private void initMenu() {
		Fragment leftFragment = new LeftFragment();
		setBehindContentView(R.layout.menu_left_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_left_frame, leftFragment).commit();
		SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT_RIGHT);

		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);

		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.setSecondaryShadowDrawable(R.drawable.shadow);
		menu.setSecondaryMenu(R.layout.menu_right_frame);
		Fragment rightFragment = new RightFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_right_frame, rightFragment).commit();
	}

	public void openLeftMenu() {
		getSlidingMenu().showMenu();
	}

	public void openRightMenu() {
		getSlidingMenu().showSecondaryMenu();
	}

}
