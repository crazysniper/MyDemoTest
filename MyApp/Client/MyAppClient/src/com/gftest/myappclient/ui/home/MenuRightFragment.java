package com.gftest.myappclient.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gftest.myappclient.BaseFragment;
import com.gftest.myappclient.R;

public class MenuRightFragment extends BaseFragment {

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
				viewGroup.removeView(view);
			}
		}
		return view;
	}

	private void initView() {
		activity = getActivity();
		layoutInflater = LayoutInflater.from(activity);
		view = layoutInflater.inflate(R.layout.frame_right_menu, null);
	}

}
