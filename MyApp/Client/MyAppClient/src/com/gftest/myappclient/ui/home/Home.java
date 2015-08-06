package com.gftest.myappclient.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.gftest.myappclient.R;
import com.gftest.myappclient.slidingmenu.lib.SlidingMenu;
import com.gftest.myappclient.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.gftest.myappclient.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.gftest.myappclient.ui.home.Fragment1.OpenMenuListener;

/**
 * 设置两侧菜单Menu的
 * 
 * @author Gao
 * 
 */
public class Home extends FragmentActivity implements OpenMenuListener {

	private FragmentTabHost fragmentTabHost;
	private Class<?>[] fragments = { Fragment1.class, Fragment2.class, Fragment3.class, Fragment4.class, Fragment5_2.class };
	private View view1, view2, view3, view4, view5;
	private LayoutInflater layoutInflater;
	private SlidingMenu menu;

	private String[] tagArray = { "tab1", "tab2", "tab3", "tab4", "tab5" };
	private int backCode = 0;
	private boolean menuOpen = false;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		System.out.println("___________Home onCreate");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		initView();
		initMenu();
	}

	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);
		System.out.println("___________Home onAttachFragment");
	}

	@Override
	protected void onStart() {
		super.onStart();
		System.out.println("___________Home onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("___________Home onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		System.out.println("___________Home onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		System.out.println("___________Home onStop");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		System.out.println("___________Home onRestart");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("___________Home onDestroy");
	}

	private void initView() {
		layoutInflater = LayoutInflater.from(Home.this);
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
		menu = new SlidingMenu(Home.this);
		menu.setMode(SlidingMenu.LEFT_RIGHT);// 设置两侧菜单

		// menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 设置触摸屏幕的模式
		menu.setShadowWidthRes(R.dimen.shadow_width);// 根据dimension资源文件的ID来设置阴影的宽度
		menu.setShadowDrawable(R.drawable.shadow);// 根据资源文件ID来设置滑动菜单的阴影效果
		menu.setSecondaryShadowDrawable(R.drawable.shadow);// 设置二级菜单的阴影效果

		int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		menu.setBehindWidth((int) (screenWidth * 0.75));// 设置SlidingMenu离屏幕的偏移量
		menu.setFadeDegree(0.35f); // 设置渐入渐出效果的值
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

		menu.setMenu(R.layout.menu_left_layout); // 为左侧滑菜单设置布局
		menu.setSecondaryMenu(R.layout.menu_right_layout);// 设置右边（二级）侧滑菜单

		menu.setOnOpenedListener(new OnOpenedListener() {
			@Override
			public void onOpened() {
				menuOpen = true;
			}
		});

		menu.setOnClosedListener(new OnClosedListener() {
			@Override
			public void onClosed() {
				menuOpen = false;
			}
		});
	}

	public void openLeftMenu() {
		menu.showMenu();
	}

	public void openRightMenu() {
		menu.showSecondaryMenu();
	}

	@Override
	public void onBackPressed() {
		System.out.println("menuOpen=" + menuOpen);
		if (menuOpen) {
			menu.showContent();
		} else {
			if (backCode == 0) {
				Toast.makeText(Home.this, "再按一次退出", Toast.LENGTH_SHORT).show();
			} else {
				Home.this.finish();
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

}
