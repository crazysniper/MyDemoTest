package com.gftest.myappclient.wangyi;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.EdgeEffectCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gftest.myappclient.BaseFragment;
import com.gftest.myappclient.R;
import com.gftest.myappclient.ui.home.Home3.UpdateQuanPopListener;
import com.gftest.myappclient.utils.Utils;
import com.gftest.myappclient.wangyi.DragGridView.CheckGridViewData;

public class WangyiFragment extends BaseFragment implements OnClickListener, OnPageChangeListener, OnItemClickListener, CheckGridViewData, UpdateQuanPopListener {

	private View view;
	private Activity activity;
	private LayoutInflater layoutInflater;

	private PagerSlidingTabStrip pagerSlidingTabStrip;
	private ViewPager viewPager;
	private LinearLayout parentLayout;
	private RelativeLayout moreQuan;

	private RelativeLayout finish;
	private DragGridView quanGridView;
	private DragAdapter dragAdapter;
	private Animation in, out;

	private List<String> titleList = new ArrayList<String>();
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private List<QuanEntity> quanList = new ArrayList<QuanEntity>();
	private List<QuanEntity> quanList2 = new ArrayList<QuanEntity>();
	private List<String> selectedNameList = new ArrayList<String>();

	private QuanFragmentViewPagerAdapter adapter;
	private QuanManage quanManage;

	private EdgeEffectCompat leftEdge;
	private EdgeEffectCompat rightEdge;

	/** 当前选中的栏目 */
	private int quanSelectIndex = 0;
	private String quanSelectedName = "";

	/** 排序是否有更新 */
	private boolean hasUpdate = false;
	/** onBack时判断是否显示pop */
	private boolean isShow = false;

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
		view = layoutInflater.inflate(R.layout.fragment_wangyi, null);
		in = AnimationUtils.loadAnimation(activity, R.anim.more_quan_in);
		out = AnimationUtils.loadAnimation(activity, R.anim.more_quan_out);

		quanManage = QuanManage.getInstance(activity);

		pagerSlidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.pagerSlidingTabStrip);
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		moreQuan = (RelativeLayout) view.findViewById(R.id.moreQuan);

		parentLayout = (LinearLayout) view.findViewById(R.id.parentLayout);
		finish = (RelativeLayout) view.findViewById(R.id.finish);
		quanGridView = (DragGridView) view.findViewById(R.id.quanGridView);

		dragAdapter = new DragAdapter(activity, quanList2, selectedNameList);
		quanGridView.setAdapter(dragAdapter);

		adapter = new QuanFragmentViewPagerAdapter(getChildFragmentManager(), fragmentList, titleList);
		viewPager.setAdapter(adapter);
		viewPager.setOffscreenPageLimit(1);

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

		moreQuan.setOnClickListener(this);
		finish.setOnClickListener(this);
		quanGridView.setCheckGridViewData(this);
		quanGridView.setOnItemClickListener(this);
		pagerSlidingTabStrip.setOnPageChangeListener(this);

		quanList = quanManage.getQuanList();
		quanSelectedName = quanList.get(quanSelectIndex).getName();
		selectedNameList.add(quanSelectedName);
		initPager();
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
		quanSelectIndex = arg0;
		quanSelectedName = quanList.get(arg0).getName();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (hasUpdate) {
			TextView text_item = (TextView) view.findViewById(R.id.text_item);
			if (text_item == null) {
				return;
			}
			quanSelectedName = text_item.getText().toString();
			quanList.clear();
			quanList.addAll(quanList2);
			int count = quanList2.size();
			for (int i = 0; i < count; i++) {
				if (quanSelectedName.equals(quanList2.get(i).getName())) {
					quanSelectIndex = i;
					break;
				}
			}
			initPager();
			viewPager.setCurrentItem(quanSelectIndex);

			new MyTask().execute();
		} else {
			viewPager.setCurrentItem(position);
		}
		handler.sendEmptyMessageDelayed(1, 400);
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				isShow = true;
				parentLayout.startAnimation(in);
				parentLayout.setVisibility(View.VISIBLE);
				break;
			case 1:
				hasUpdate = false;
				isShow = false;
				parentLayout.startAnimation(out);
				parentLayout.setVisibility(View.GONE);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.moreQuan:
			quanList2.clear();
			quanList2.addAll(quanList);
			selectedNameList.clear();
			selectedNameList.add(quanSelectedName);
			dragAdapter.notifyDataSetChanged();
			handler.sendEmptyMessage(0);
			break;
		case R.id.finish:
			if (!hasUpdate) {
				updateQuan(false);
			} else {
				updateQuan(true);
			}
			handler.sendEmptyMessage(1);
			break;
		default:
			break;
		}
	}

	/**
	 * 画面
	 */
	private void initPager() {
		fragmentList.clear();
		titleList.clear();
		int count = quanList.size();
		QuanFragment_Item quanFragment_Item;
		for (int i = 0; i < count; i++) {
			quanFragment_Item = new QuanFragment_Item(activity, quanList.get(i).getId(), i);
			fragmentList.add(quanFragment_Item);
			System.out.println("名称="+quanList.get(i).getName());
			titleList.add(quanList.get(i).getName());
		}
		adapter.notifyDataSetChanged();
		pagerSlidingTabStrip.setViewPager(viewPager);
	}

	/**
	 * 修改圈子排序
	 * 
	 * @param flag
	 *            false:用不着修改
	 */
	private void updateQuan(boolean flag) {
		if (!flag) {
			quanList2.clear();
			quanList2.addAll(quanList);
			selectedNameList.clear();
			selectedNameList.add(quanSelectedName);
			dragAdapter.notifyDataSetChanged();
		} else {
			quanSelectedName = quanList.get(quanSelectIndex).getName();
			quanList.clear();
			quanList.addAll(quanList2);
			int count = quanList2.size();
			for (int i = 0; i < count; i++) {
				if (quanSelectedName.equals(quanList2.get(i).getName())) {
					quanSelectIndex = i;
					break;
				}
			}

			initPager();
			viewPager.setCurrentItem(quanSelectIndex);

			new MyTask().execute();
		}
	}

	public class MyTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			quanManage.deleteAllQuan();
			quanManage.saveDefaultQuan(quanList2);
			return null;
		}
	}

	@Override
	public void setChecked(Boolean flag) {
		hasUpdate = flag;
	}

	@Override
	public boolean checkPop() {
		return isShow;
	}

	@Override
	public void closePop() {
		if (!hasUpdate) {
			updateQuan(false);
		} else {
			updateQuan(true);
		}
		handler.sendEmptyMessage(1);
	}

}
