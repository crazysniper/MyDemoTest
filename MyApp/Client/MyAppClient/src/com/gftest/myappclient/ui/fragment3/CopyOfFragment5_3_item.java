package com.gftest.myappclient.ui.fragment3;

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
public class CopyOfFragment5_3_item extends BaseFragment {
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
	private boolean mHasLoadedOnce = false;

	public CopyOfFragment5_3_item() {
	}

	public CopyOfFragment5_3_item(String id) {
		this.id = id;
	}

	public CopyOfFragment5_3_item(String id, Activity activity) {
		this.id = id;
		this.activity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Bundle bundle = getArguments();
		// id = bundle.getString("index");
		super.onCreate(savedInstanceState);
	}

	private boolean isViewShown = false, isVisible = false;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			isVisible = true;
			System.out.println("index=" + id + "__isVisible=" + isVisible());
			System.out.println("index=" + id + "__getUserVisibleHint=" + getUserVisibleHint());
			// if (isVisible()) {
			System.out.println("index=" + id + "__isVisibleToUser=" + isVisibleToUser);
			System.out.println("hasdLoaded=" + hasdLoaded + "__index=" + id);
			System.out.println("hasdLoaded2=" + hasdLoaded2 + "__index=" + id);
			if (activity == null) {
				activity = getActivity();
				System.out.println("上下文为空" + "__index=" + id);
			} else {
				System.out.println("上下文不为空" + "__index=" + id);
			}

			if (isVisibleToUser) {
				if (!hasdLoaded2) {
					System.out.println("可见时没有开始载入数据" + "__index=" + id);
					hasdLoaded2 = true;
					if (jsonObject == null || !jsonObject.optString("ret").equals("1")) {
						System.out.println("可见时重新获取数据" + "__index=" + id);
						handler.sendEmptyMessage(2);
					}
					System.out.println("33333333" + "__index=" + id);
				} else {
					System.out.println("可见时已经载过数据" + "__index=" + id);
				}
			} else {
				if (!hasdLoaded) {
					System.out.println("55555555" + "__index=" + id);
					hasdLoaded = true;
					jsonObject = Utils.readJsonData(activity, "quanzi_" + id);
				} else {
					System.out.println("6666666666666" + "__index=" + id);
				}
			}
		} else {
			isVisible = false;
			if (!hasdLoaded) {
				System.out.println("不可见时加载缓存数据" + "__index=" + id);
				hasdLoaded = true;
				jsonObject = Utils.readJsonData(activity, "quanzi_" + id);
				if (jsonObject != null && jsonObject.optString("ret").equals("1")) {
					System.out.println("不可见时能够加载到缓存数据" + "__index=" + id);
					Message msg = Message.obtain();
					msg.what = 1;
					msg.obj = jsonObject;
					handler.sendMessage(msg);
				} else {
					System.out.println("不可见时加载不到" + "__index=" + id);
				}
			} else {
				System.out.println("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈" + "__index=" + id);
			}
		}
		// }
	}

	private void fetchData() {
		jsonObject = Utils.readJsonData(activity, "quanzi_" + id);
		if (jsonObject != null && jsonObject.optString("ret").equals("1")) {
			System.out.println("fetchData获取到缓存" + "__index=" + id);
			Message msg = Message.obtain();
			msg.what = 1;
			msg.obj = jsonObject;
			handler.sendMessage(msg);
		}
	}

	private boolean isPrepared;

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
		System.out.println("onCreateView__" + id);
		// isPrepared = true;
		// lazyLoad();
		return view;
	}

	protected void lazyLoad() {
		System.out.println("isPrepared=" + isPrepared + "_____isVisible=" + isVisible + "__index=" + id);
		System.out.println("判断=" + (!isPrepared || !isVisible) + "__index=" + id);
		if (!isPrepared || !isVisible) {
			System.out.println("结束" + "__index=" + id);
			return;
		}
		System.out.println("开始fetchData()" + "__index=" + id);
		fetchData();
		// 填充各控件的数据
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
		System.out.println("initView____id=" + id);
		boolean flag = getUserVisibleHint();
		boolean flag2 = isVisible();
		System.out.println("getUserVisibleHint=" + flag + "___id=" + id);
		System.out.println("isVisible=" + flag2 + "___id=" + id);
		// getData();
		// if (!isViewShown) {
		// System.out.println("initView____fetchData=" + id);
		// fetchData();
		// } else {
		// System.out.println("initView____不fetchData=" + id);
		// }
	}

	private void getData() {
		System.out.println("加载数据" + id);
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
