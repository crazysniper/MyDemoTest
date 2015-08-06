package com.gftest.myappclient.ui.viewpager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gftest.myappclient.BaseFragment;
import com.gftest.myappclient.R;
import com.gftest.myappclient.adapter.Fragment5_item_Adapter;
import com.gftest.myappclient.utils.HttpUtils;
import com.gftest.myappclient.utils.ToastUtils;
import com.gftest.myappclient.utils.Utils;

@SuppressLint("ValidFragment")
public class ViewPager_Fragment_Item extends BaseFragment {
	private LayoutInflater layoutInflater;
	private Activity activity;
	private View view;

	private TextView item5_tv;
	private ListView listView;
	private Fragment5_item_Adapter adapter;
	private ToastUtils toastUtils;

	private JSONObject jsonObject = new JSONObject();
	private List<JSONObject> jsonList = new ArrayList<JSONObject>();
	private String id = "";
	private String url = "";

	/** 不可见时判断是否需要加载数据 */
	private boolean hasdLoaded = false;
	/** 可见时判断是否需要加载数据 */
	private boolean hasdLoaded2 = false;

	public ViewPager_Fragment_Item() {
	}

	public ViewPager_Fragment_Item(String id) {
		this.id = id;
	}

	public ViewPager_Fragment_Item(String id, Activity activity) {
		this.id = id;
		this.activity = activity;
	}

	@Override
	public boolean getUserVisibleHint() {
		return super.getUserVisibleHint();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			System.out.println("index=" + id + "isVisible=" + isVisible());
			System.out.println("index=" + id + "getUserVisibleHint=" + getUserVisibleHint());
			System.out.println("index=" + id + "__isVisibleToUser=" + isVisibleToUser);
			System.out.println("hasdLoaded=" + hasdLoaded + "__index=" + id);
			System.out.println("hasdLoaded2=" + hasdLoaded2 + "__index=" + id);
			if (isVisibleToUser) {
				if (!hasdLoaded2) {
					hasdLoaded2 = true;
					if (jsonObject == null || !jsonObject.optString("ret").equals("1")) {
						handler.sendEmptyMessage(2);
					}
				}
			}
		} else {
			if (!hasdLoaded) {
				hasdLoaded = true;
				jsonObject = Utils.readJsonData(activity, "quanzi_" + id);
				if (jsonObject != null && jsonObject.optString("ret").equals("1")) {
					Message msg = Message.obtain();
					msg.what = 1;
					msg.obj = jsonObject;
					handler.sendMessage(msg);
				}
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
		activity = getActivity();
		layoutInflater = activity.getLayoutInflater();
		toastUtils = ToastUtils.getInstance();
		view = layoutInflater.inflate(R.layout.fragment_5_item, null);

		item5_tv = (TextView) view.findViewById(R.id.item5_tv);
		listView = (ListView) view.findViewById(R.id.listView);

		adapter = new Fragment5_item_Adapter(activity, jsonList);
		listView.setAdapter(adapter);

		item5_tv.setText(id);
	}

	private void getData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				JSONObject jsonObject = HttpUtils.getHttpJsonObject(url);
				Utils.saveJsonData(activity, "quanzi_" + id, jsonObject);
				if (jsonObject != null && jsonObject.optString("ret").equals("1")) {
					Message msg = Message.obtain();
					msg.what = 1;
					msg.obj = jsonObject;
					handler.sendMessage(msg);
				} else {
					handler.sendEmptyMessage(0);
				}
			}
		}).start();
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				toastUtils.showToast(activity, "网络异常", Toast.LENGTH_SHORT);
				break;
			case 1:
				JSONObject jsonObject = (JSONObject) msg.obj;
				JSONArray jsonArray = jsonObject.optJSONArray("data");
				int length = jsonArray.length();
				jsonList.clear();
				for (int i = 0; i < length; i++) {
					jsonList.add(jsonArray.optJSONObject(i));
				}
				adapter.notifyDataSetChanged();
				break;
			case 2:
				getData();
				break;
			}
		}
	};

}
