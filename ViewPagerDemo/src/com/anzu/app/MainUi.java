package com.anzu.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

import com.anzu.app.view.ReViewPager;
import com.anzu.app.view.ReViewPager.ViewListener;
import com.example.viewpagerdemo.R;

public class MainUi extends Activity {

	private ReViewPager mViewPager;
	private Integer[] img = { R.drawable.pic_1, R.drawable.pic_2, R.drawable.pic_3, R.drawable.pic_4 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main_ui);
		this.initData();
	}

	private void goActivity() {
		ReViewPager.isCenter = false;
		Intent intent = new Intent(this, SuccessUi.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
		startActivity(intent);
	}

	private void initData() {
		LayoutParams mParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		List<View> views = new ArrayList<View>();
		for (int i = 0; i < img.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(mParams);
			iv.setImageResource(img[i]);
			views.add(iv);
			if (i == img.length - 1) { // 在最后一页上点击图片触发的监听，如果不需要则不用编写此监听
				iv.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						goActivity();
					}
				});
			}
		}
		mViewPager = (ReViewPager) findViewById(R.id.reViewpager);
		mViewPager.setAdapter(views);
		mViewPager.setOnChangeListener(mListener, true);
	}

	private ViewListener mListener = new ViewListener() {

		@Override
		public void onEndPageSelected() {
			goActivity();
		}

		@Override
		public void onPageSelected(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
