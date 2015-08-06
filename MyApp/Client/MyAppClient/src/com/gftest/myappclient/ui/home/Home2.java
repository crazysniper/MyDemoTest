package com.gftest.myappclient.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

import com.gftest.myappclient.R;
import com.gftest.myappclient.slidingmenu.lib.SlidingMenu;
import com.gftest.myappclient.slidingmenu.lib.app.SlidingFragmentActivity;
import com.gftest.myappclient.ui.home.Fragment1.OpenMenuListener;

public class Home2 extends SlidingFragmentActivity implements OpenMenuListener {

	private FragmentTabHost fragmentTabHost;
	private Class<?>[] fragments = { Fragment1.class, Fragment2.class, Fragment3.class, Fragment4.class, Fragment5.class };
	private View view1, view2, view3, view4, view5;
	private LayoutInflater layoutInflater;

	private String[] tagArray = { "tab1", "tab2", "tab3", "tab4", "tab5" };
	private int backCode = 0;

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		initView();
		initMenu();
	}

	private void initView() {
		layoutInflater = LayoutInflater.from(Home2.this);
		fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		view1 = layoutInflater.inflate(R.layout.home_tab_bottom_item1, null);
		view2 = layoutInflater.inflate(R.layout.home_tab_bottom_item2, null);
		view3 = layoutInflater.inflate(R.layout.home_tab_bottom_item3, null);
		view4 = layoutInflater.inflate(R.layout.home_tab_bottom_item4, null);
		view5 = layoutInflater.inflate(R.layout.home_tab_bottom_item5, null);

		TabSpec tabSpec1 = fragmentTabHost.newTabSpec(tagArray[0]).setIndicator(view1);
		TabSpec tabSpec2 = fragmentTabHost.newTabSpec(tagArray[1]).setIndicator(view2);
		TabSpec tabSpec3 = fragmentTabHost.newTabSpec(tagArray[2]).setIndicator(view3);
		TabSpec tabSpec4 = fragmentTabHost.newTabSpec(tagArray[3]).setIndicator(view4);
		TabSpec tabSpec5 = fragmentTabHost.newTabSpec(tagArray[4]).setIndicator(view5);

		fragmentTabHost.addTab(tabSpec1, fragments[0], null);
		fragmentTabHost.addTab(tabSpec2, fragments[1], null);
		fragmentTabHost.addTab(tabSpec3, fragments[2], null);
		fragmentTabHost.addTab(tabSpec4, fragments[3], null);
		fragmentTabHost.addTab(tabSpec5, fragments[4], null);

		fragmentTabHost.setCurrentTab(0);
	}

	private void initMenu() {
		Fragment leftFragment = new MenuLeftFragment();
		setBehindContentView(R.layout.left_menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.left_menu_frame, leftFragment);
		SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT_RIGHT);

		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);

		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.setSecondaryShadowDrawable(R.drawable.shadow);
		menu.setSecondaryMenu(R.layout.right_menu_frame);
		Fragment rightFragment = new MenuRightFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.right_menu_frame, rightFragment);
	}

	public void openLeftMenu() {
		getSlidingMenu().showMenu();
	}

	public void openRightMenu() {
		getSlidingMenu().showSecondaryMenu();
	}

	@Override
	public void onBackPressed() {
		if (backCode == 0) {
			Toast.makeText(Home2.this, "再按一次退出", Toast.LENGTH_SHORT).show();
		} else {
			Home2.this.finish();
		}
		backCode++;
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				backCode = 0;
			}
		}, 2000);
	}

}
