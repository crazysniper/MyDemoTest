package com.gftest.mynews;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gftest.mynews.base.BaseFragment;

public class LeftFragment extends BaseFragment {

	private View view;
	private LayoutInflater layoutInflater;
	private Activity activity;

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
		view = layoutInflater.inflate(R.layout.fragment_left, null);
	}
}
