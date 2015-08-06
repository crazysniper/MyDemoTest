package com.gftest.myappclient.quan;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gftest.myappclient.R;

@SuppressLint("ValidFragment")
public class QuanFragment_Item extends Fragment {
	private View view;
	private Activity activity;
	private LayoutInflater layoutInflater;

	private TextView title;

	private JSONObject jsonObject = new JSONObject();
	private String tag = "", birthday = "";
	/** 不可见时判断是否需要加载数据 */
	private boolean invisibleHasdLoaded = false;
	/** 可见时判断是否需要加载数据 */
	private boolean visibleHasdLoaded = false;
	private String url = "";

	public QuanFragment_Item() {
	}

	public QuanFragment_Item(Activity activity, String tag, String qid) {
		this.activity = activity;
		this.tag = tag;
		url = url + qid;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		// System.out.println("tag=" + tag + "__isVisible=" + isVisible());
		// System.out.println("tag=" + tag + "__getUserVisibleHint=" +
		// getUserVisibleHint());
		// System.out.println("tag=" + tag + "__isVisibleToUser=" +
		// isVisibleToUser);
		if (isVisibleToUser) {
			if (!visibleHasdLoaded) {
				visibleHasdLoaded = true;
				if (jsonObject == null || !jsonObject.optString("ret").equals("1")) {
					// System.out.println("可见时没有获取到缓存后，重新获取数据" + "__tag=" +
					// tag);
				}
				// System.out.println("可见时获取到缓存后,更新ui" + "__tag=" + tag);
			} else {
				// System.out.println("可见时已经载过数据，就不进行任何操作了" + "__tag=" + tag);
			}
		} else {
			if (!invisibleHasdLoaded) {
				invisibleHasdLoaded = true;
				if (jsonObject != null && "1".equals(jsonObject.optString("ret"))) {
					// System.out.println("不可见时能够加载到缓存数据" + "__tag=" + tag);
				} else {
					// System.out.println("不可见时加载不到" + "__tag=" + tag);
				}
			} else {
				// System.out.println("不可见时不进行任何操作" + "__tag=" + tag);
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
		view = layoutInflater.inflate(R.layout.fragment_quan2_item, null);

		title = (TextView) view.findViewById(R.id.title);

		url = url + "&btime=" + birthday;

		title.setText(tag);
	}

	@Override
	public void onResume() {
		super.onResume();
		System.out.println("item_onresume");
	}
}
