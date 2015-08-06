package com.gftest.myappclient.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.gftest.myappclient.R;
import com.gftest.myappclient.ui.home.Fragment1.OpenMenuListener;
import com.gftest.myappclient.wangyi.WangyiFragment;

public class Home3 extends FragmentActivity implements OpenMenuListener {

	private FragmentTabHost fragmentTabHost;
	// private Class<?>[] fragments = { Fragment1.class, Fragment2.class,
	// Fragment3.class, Fragment4.class, Fragment5_3.class };
	private Class<?>[] fragments = { Fragment1.class, Fragment2.class, Fragment3.class, Fragment4.class, WangyiFragment.class };
	private View view1, view2, view3, view4, view5;
	private LayoutInflater layoutInflater;

	private String[] tagArray = { "tab1", "tab2", "tab3", "tab4", "tab5" };
	private int backCode = 0;
	private int curTab = 1;

	private UpdateQuanPopListener updateQuanPopListener;

	public interface UpdateQuanPopListener {
		public boolean checkPop();

		public void closePop();
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		System.out.println("___________Home onCreate");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		initView();
	}

	@Override
	public void onAttachFragment(Fragment fragment) {
		try {
			updateQuanPopListener = (UpdateQuanPopListener) fragment;
		} catch (Exception e) {
		}
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
		layoutInflater = LayoutInflater.from(Home3.this);
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
		fragmentTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				if ("tab1".equals(tabId)) {
					curTab = 1;
				} else if ("tab2".equals(tabId)) {
					curTab = 2;
				} else if ("tab3".equals(tabId)) {
					curTab = 3;
				} else if ("tab4".equals(tabId)) {
					curTab = 4;
				} else if ("tab5".equals(tabId)) {
					curTab = 5;
				}
			}
		});
	}

	public void openLeftMenu() {
	}

	public void openRightMenu() {
	}

	@Override
	public void onBackPressed() {
		if (curTab == 5 && updateQuanPopListener != null && updateQuanPopListener.checkPop()) {
			updateQuanPopListener.closePop();
			return;
		}
		if (backCode == 0) {
			Toast.makeText(Home3.this, "再按一次退出", Toast.LENGTH_SHORT).show();
		} else {
			Home3.this.finish();
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
