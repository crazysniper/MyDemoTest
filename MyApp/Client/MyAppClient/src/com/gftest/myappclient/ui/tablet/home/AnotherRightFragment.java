package com.gftest.myappclient.ui.tablet.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gftest.myappclient.BaseFragment;
import com.gftest.myappclient.R;

public class AnotherRightFragment extends BaseFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tablet_another_right_fragment, container, false);
		return view;
	}

}
