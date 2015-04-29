package com.example.listviewdemo.xiala.xiala2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class MyListView extends ListView implements OnScrollListener {

	/** 下拉过程的状态值 */
	private static final int RELEASE_TO_REFRESH = 0;
	/** 从下拉返回到不刷新的状态值 */
	private static final int PULL_TO_REFRESH = 1;
	/** 正在刷新的状态值 */
	private static final int REFRESHING = 2;
	private static final int DONE = 3;
	private static final int LOADING = 4;

	// 实际的padding的距离与界面上偏移距离的比例
	private static final int RATION = 3;
	private LayoutInflater layoutInflater;
	
	

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

	}

}
