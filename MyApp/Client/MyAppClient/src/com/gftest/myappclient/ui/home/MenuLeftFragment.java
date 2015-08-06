package com.gftest.myappclient.ui.home;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.gftest.myappclient.BaseFragment;
import com.gftest.myappclient.R;

public class MenuLeftFragment extends BaseFragment {

	private View view;
	private LayoutInflater layoutInflater;
	private Activity activity;
	private ListView mCategories;
	private List<String> mDatas = Arrays.asList("聊天", "发现", "通讯录", "朋友圈", "订阅号");
	private ListAdapter mAdapter;

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
		view = layoutInflater.inflate(R.layout.frame_left_menu, null);

		mCategories = (ListView) view.findViewById(R.id.id_listview_categories);
		mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mDatas);
		mCategories.setAdapter(mAdapter);
	}

}
