package com.gftest.myappclient.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.EdgeEffectCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;
import com.gftest.myappclient.adapter.ViewPagerAdapter;
import com.gftest.myappclient.ui.home.Home;
import com.gftest.myappclient.ui.home.Home3;
import com.gftest.myappclient.utils.Utils;

public class Guide extends BaseActivity implements OnPageChangeListener, OnClickListener {

	private ViewPager viewPager;
	private ViewPagerAdapter adapter;
	private LayoutInflater layoutInflater;
	private View view1, view2, view3;
	private TextView guide_3;

	private List<View> viewList = new ArrayList<View>();

	private EdgeEffectCompat leftEdge;
	private EdgeEffectCompat rightEdge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		initView();
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		layoutInflater = LayoutInflater.from(Guide.this);
		view1 = layoutInflater.inflate(R.layout.guide_view1, null);
		view2 = layoutInflater.inflate(R.layout.guide_view2, null);
		view3 = layoutInflater.inflate(R.layout.guide_view3, null);
		viewList.add(view1);
		viewList.add(view2);
		viewList.add(view3);

		guide_3 = (TextView) view3.findViewById(R.id.guide_3);

		adapter = new ViewPagerAdapter(viewList);
		viewPager.setAdapter(adapter);

		try {
			Field leftEdgeField = viewPager.getClass().getDeclaredField("mLeftEdge");
			Field rightEdgeField = viewPager.getClass().getDeclaredField("mRightEdge");
			if (leftEdgeField != null && rightEdgeField != null) {
				leftEdgeField.setAccessible(true);
				rightEdgeField.setAccessible(true);
				leftEdge = (EdgeEffectCompat) leftEdgeField.get(viewPager);
				rightEdge = (EdgeEffectCompat) rightEdgeField.get(viewPager);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		viewPager.setOnPageChangeListener(this);
		guide_3.setOnClickListener(this);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		if (leftEdge != null && rightEdge != null) {// 取消ViewPager的边界渐变色
			leftEdge.finish();
			rightEdge.finish();
			leftEdge.setSize(0, 0);
			rightEdge.setSize(0, 0);
		}
	}

	@Override
	public void onPageSelected(int arg0) {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.guide_3:
			Intent intent = new Intent(Guide.this, Home3.class);
			startActivity(intent);
			Utils.getInstance().saveSharedPreferences(Guide.this, "isFirst", "no");
			Guide.this.finish();
			break;
		}
	}

}
