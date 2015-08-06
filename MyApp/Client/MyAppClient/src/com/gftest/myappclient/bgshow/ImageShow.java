package com.gftest.myappclient.bgshow;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.EdgeEffectCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;

/**
 * 显示大图
 * 
 * @author Administrator
 * 
 */
public class ImageShow extends BaseActivity implements OnClickListener {

	private ViewPager mViewPager;
	private TextView curNum, desc, time;
	private LinearLayout bottom_layout;
	private MyPageAdapter adapter;
	private ImageView back;

	private EdgeEffectCompat leftEdge;
	private EdgeEffectCompat rightEdge;

	/** 已选图片的路径 */
	private List<String> pathList = new ArrayList<String>();
	private List<String> contentList = new ArrayList<String>();
	private List<String> timeList = new ArrayList<String>();
	private int curItem = 0, type = 0;
	private String content = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_show);
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		bottom_layout = (LinearLayout) findViewById(R.id.bottom_layout);
		back = (ImageView) findViewById(R.id.back);
		time = (TextView) findViewById(R.id.time);
		desc = (TextView) findViewById(R.id.desc);
		curNum = (TextView) findViewById(R.id.curItem);

		String url = getIntent().getExtras().getString("url");
		type = getIntent().getExtras().getInt("type");
		bottom_layout.setVisibility(View.GONE);
		pathList.clear();
		contentList.clear();
		timeList.clear();

		curNum.setText((curItem + 1) + "/" + pathList.size());

		try {
			Field leftEdgeField = mViewPager.getClass().getDeclaredField("mLeftEdge");
			Field rightEdgeField = mViewPager.getClass().getDeclaredField("mRightEdge");
			if (leftEdgeField != null && rightEdgeField != null) {
				leftEdgeField.setAccessible(true);
				rightEdgeField.setAccessible(true);
				leftEdge = (EdgeEffectCompat) leftEdgeField.get(mViewPager);
				rightEdge = (EdgeEffectCompat) rightEdgeField.get(mViewPager);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		adapter = new MyPageAdapter();
		mViewPager.setAdapter(adapter);
		mViewPager.setEnabled(false);
		mViewPager.setCurrentItem(curItem);

		back.setOnClickListener(this);
		mViewPager.setOnPageChangeListener(pageChangeListener);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		}
	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
		public void onPageSelected(int arg0) {
			curNum.setText((arg0 + 1) + "/" + pathList.size());
			if (type == 2) {
				time.setText(timeList.get(arg0));
				content = contentList.get(arg0);
				if (TextUtils.isEmpty(content)) {
					desc.setVisibility(View.GONE);
				} else {
					desc.setVisibility(View.VISIBLE);
					desc.setText(content);
				}
			}
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
			if (leftEdge != null && rightEdge != null) {
				leftEdge.finish();
				rightEdge.finish();
				leftEdge.setSize(0, 0);
				rightEdge.setSize(0, 0);
			}
		}

		public void onPageScrollStateChanged(int arg0) {
		}
	};

	class MyPageAdapter extends PagerAdapter {
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			String imagePath = pathList.get(position);
			UrlTouchImageView iv = new UrlTouchImageView(ImageShow.this);
			iv.setUrl(imagePath, position);
			iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			container.addView(iv);
			return iv;
		}

		public int getCount() {
			return pathList.size();
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = (View) object;
			container.removeView(view);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}

	@Override
	public void onBackPressed() {
		ImageShow.this.finish();
	}

}
