package com.example.viewpagerdemo.fragment;

import com.example.viewpagerdemo.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class MyFragment extends Fragment {
	private Activity activity;
	private View view;
	private LayoutInflater layoutInflater;

	private ImageView mImageView;
	private ProgressBar progressBar;
	private TextView index;
	private int position;

	public MyFragment() {
	}

	public MyFragment(Activity activity, int position) {
		this.activity = activity;
		this.position = position;
		System.out.println("position=" + position);
	}

	/** 不可见时判断是否需要加载数据 */
	private boolean invisibleHasdLoaded = false;
	/** 可见时判断是否需要加载数据 */
	private boolean visibleHasdLoaded = false;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		System.out.println("position=" + position + "____isVisibleToUser=" + isVisibleToUser);
		if (isVisibleToUser) {
			System.out.println("可见visibleHasdLoaded==" + visibleHasdLoaded);
			handler.sendEmptyMessage(1);
			if (!visibleHasdLoaded) {
				visibleHasdLoaded = true;
			} else {
			}
		} else {
			System.out.println("不可见invisibleHasdLoaded=" + invisibleHasdLoaded);
			if (!invisibleHasdLoaded) {
				invisibleHasdLoaded = true;
			}
		}
	}

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
		layoutInflater = LayoutInflater.from(activity);
		view = layoutInflater.inflate(R.layout.image_detail_fragment, null);
		index = (TextView) view.findViewById(R.id.index);
		mImageView = (ImageView) view.findViewById(R.id.image);
		progressBar = (ProgressBar) view.findViewById(R.id.loading);

		System.out.println("oncreateView ");
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				System.out.println("handler ");
				index.setText("第" + position + "个");
				break;
			default:
				break;
			}
		}
	};
}
