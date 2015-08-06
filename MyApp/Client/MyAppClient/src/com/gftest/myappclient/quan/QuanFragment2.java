package com.gftest.myappclient.quan;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gftest.myappclient.R;
import com.gftest.myappclient.quan.DragGridView.CheckGridViewData;
import com.gftest.myappclient.utils.Utils;

public class QuanFragment2 extends Fragment implements OnClickListener, CheckGridViewData {

	private View view;
	private Activity activity;
	private LayoutInflater layoutInflater;

	private ViewPager viewPager;
	private HorizontalScrollView horizontalScrollView;
	private RelativeLayout header;
	private LinearLayout quanLayout, quanLayoutParent, parent;
	public ImageView rightShade;
	private ImageView moreQuan;
	private View line;

	private ImageView up;
	private TextView finish;
	private DragGridView quanGridView;
	private DragAdapter dragAdapter;

	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private QuanFragmentViewPagerAdapter adapter;
	private QuanManage quanManage;
	private List<QuanEntity> quanList = new ArrayList<QuanEntity>();
	private List<QuanEntity> quanList2 = new ArrayList<QuanEntity>();
	/** 当前选中的栏目 */
	private int quanSelectIndex = 0;
	private int screenWidth;
	/** Item宽度 */
	private int itemWidth = 0;
	private int height;
	private boolean hasChecked = false;

	private Animation in, out;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			initView();
		} else {
			ViewGroup viewGroup = (ViewGroup) view.getParent();
			if (viewGroup != null) {
				viewGroup.removeAllViews();
			}
		}
		return view;
	}

	private void initView() {
		activity = getActivity();
		layoutInflater = LayoutInflater.from(activity);
		view = layoutInflater.inflate(R.layout.fragment_quan2, null);

		in = AnimationUtils.loadAnimation(activity, R.anim.quan_pop_in);
		out = AnimationUtils.loadAnimation(activity, R.anim.quan_pop_out);

		header = (RelativeLayout) view.findViewById(R.id.header);
		parent = (LinearLayout) view.findViewById(R.id.parent);
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.horizontalScrollView);
		quanLayoutParent = (LinearLayout) view.findViewById(R.id.quanLayoutParent);
		quanLayout = (LinearLayout) view.findViewById(R.id.quanLayout);
		moreQuan = (ImageView) view.findViewById(R.id.moreQuan);
		line = (View) view.findViewById(R.id.line);

		up = (ImageView) view.findViewById(R.id.up);
		finish = (TextView) view.findViewById(R.id.finish);
		quanGridView = (DragGridView) view.findViewById(R.id.quanGridView);

		quanManage = QuanManage.getInstance(activity);
		screenWidth = Utils.getDeviceSize(activity).x;
		itemWidth = screenWidth / 4;

		height = Utils.getDeviceSize(activity).y - Utils.dip2px(activity, 44) - Utils.getStatusBarHeight(activity) - Utils.dip2px(activity, 50);
		System.out.println("height=" + height);

		dragAdapter = new DragAdapter(activity, quanList2);
		quanGridView.setAdapter(dragAdapter);

		initQuans();

		fragmentList.clear();
		QuanFragment_Item quanFragment_Item;
		for (int i = 0; i < 12; i++) {
			quanFragment_Item = new QuanFragment_Item(activity, quanList.get(i).getName(), quanList.get(i).getId());
			fragmentList.add(quanFragment_Item);
		}
		adapter = new QuanFragmentViewPagerAdapter(getChildFragmentManager(), fragmentList);

		viewPager.setAdapter(adapter);
		viewPager.setOffscreenPageLimit(1);
		parent.setVisibility(View.GONE);

		moreQuan.setOnClickListener(this);
		up.setOnClickListener(this);
		finish.setOnClickListener(this);
		quanGridView.setCheckGridViewData(this);

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				viewPager.setCurrentItem(arg0);
				selectTab(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.moreQuan:
			System.out.println("打开前");
			parent.startAnimation(in);
			parent.setVisibility(View.VISIBLE);
			break;
		case R.id.up:
			parent.startAnimation(out);
			parent.setVisibility(View.GONE);
			break;
		case R.id.finish:
			parent.startAnimation(out);
			parent.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	private void initQuans() {
		quanList = quanManage.getQuanList();
		quanList2.clear();
		quanList2.addAll(quanList);
		dragAdapter.notifyDataSetChanged();

		FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(activity, 42));
		quanLayoutParent.setOrientation(LinearLayout.VERTICAL);
		quanLayoutParent.setLayoutParams(layoutParams1);

		quanLayout.removeAllViews();
		int count = quanList.size();
		LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(itemWidth, LayoutParams.WRAP_CONTENT);
		layoutParams2.gravity = Gravity.CENTER;
		LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(itemWidth, Utils.dip2px(activity, 2));
		line.setLayoutParams(layoutParams3);

		TextView textView;
		QuanEntity quanEntity;

		for (int index = 0; index < count; index++) {
			quanEntity = quanList.get(index);
			textView = new TextView(activity);
			textView.setTextAppearance(activity, R.style.top_category_scroll_view_item_text);
			textView.setGravity(Gravity.CENTER);
			textView.setPadding(10, 5, 10, 5);
			textView.setId(index);
			textView.setTextSize(14);
			textView.setTag(quanEntity.getId());
			textView.setText(quanEntity.getName());
			textView.setTextColor(getResources().getColorStateList(R.drawable.quan_column_tv_selector));
			if (quanSelectIndex == index) {
				textView.setSelected(true);
			}
			textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println("下标=" + v.getId());
					System.out.println("count=" + quanLayout.getChildCount());
					for (int i = 0; i < quanLayout.getChildCount(); i++) {
						View localView = quanLayout.getChildAt(i);
						if (localView != v) {
							localView.setSelected(false);
						} else {
							localView.setSelected(true);
							viewPager.setCurrentItem(i);
						}
					}
				}
			});
			quanLayout.addView(textView, index, layoutParams2);
		}
	}

	/**
	 * 选择的Column里面的Tab
	 * */
	private void selectTab(int tab_postion) {
		int childCount = quanLayout.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View checkView = quanLayout.getChildAt(tab_postion);
			int k = checkView.getMeasuredWidth();
			int l = checkView.getLeft();
			System.out.println("i=" + i + "__k=" + k + "__l=" + l);
			System.out.println("_______");
			int i2 = l + k / 2 - screenWidth / 2;
			// rg_nav_content.getParent()).smoothScrollTo(i2, 0);
			horizontalScrollView.smoothScrollTo(i2, 0);
			// mColumnHorizontalScrollView.smoothScrollTo((position - 2) *
			// mItemWidth , 0);
		}
		// 判断是否选中
		for (int j = 0; j < childCount; j++) {
			View checkView = quanLayout.getChildAt(j);
			if (j == tab_postion) {
				checkView.setSelected(true);
			} else {
				checkView.setSelected(false);
			}

		}
	}

	@Override
	public void setChecked(Boolean flag) {
		hasChecked = flag;
		if (flag) {
		} else {
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		quanManage.close();
	}

}
