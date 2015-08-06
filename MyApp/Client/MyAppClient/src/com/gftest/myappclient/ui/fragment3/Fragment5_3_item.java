package com.gftest.myappclient.ui.fragment3;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gftest.myappclient.BaseFragment;
import com.gftest.myappclient.R;
import com.gftest.myappclient.adapter.Fragment5_item_Adapter;
import com.gftest.myappclient.utils.HttpUtils;
import com.gftest.myappclient.utils.ToastUtils;
import com.gftest.myappclient.utils.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

@SuppressLint("ValidFragment")
public class Fragment5_3_item extends BaseFragment {
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
	private boolean invisibleHasdLoaded = false;
	/** 可见时判断是否需要加载数据 */
	private boolean visibleHasdLoaded = false;

	public Fragment5_3_item() {
	}

	public Fragment5_3_item(String id) {
		this.id = id;
	}

	public Fragment5_3_item(String id, Activity activity) {
		this.id = id;
		this.activity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("Fragment5_3_item", "onCreate__" + id);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i("Fragment5_3_item", "onActivityCreated__" + id);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.i("Fragment5_3_item", "onAttach__" + id);
		super.onAttach(activity);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		Log.i("Fragment5_3_item", "setUserVisibleHint__" + id);
		super.setUserVisibleHint(isVisibleToUser);
		System.out.println("index=" + id + "__isVisible=" + isVisible());
		System.out.println("index=" + id + "__getUserVisibleHint=" + getUserVisibleHint());
		System.out.println("index=" + id + "__isVisibleToUser=" + isVisibleToUser);
		if (getUserVisibleHint()) {
			if (!visibleHasdLoaded) {
				System.out.println("可见时没有开始载入数据" + "__index=" + id);
				visibleHasdLoaded = true;
				if (jsonObject == null || !jsonObject.optString("ret").equals("1")) {
					System.out.println("可见时没有获取到缓存后，重新获取数据" + "__index=" + id);
					handler.sendEmptyMessage(2);
				}
				System.out.println("可见时获取到缓存后,更新ui" + "__index=" + id);
			} else {
				System.out.println("可见时已经载过数据，就不进行任何操作了" + "__index=" + id);
			}
		} else {
			if (!invisibleHasdLoaded) {
				System.out.println("不可见时加载缓存数据" + "__index=" + id);
				invisibleHasdLoaded = true;
				jsonObject = Utils.readJsonData(activity, "quanzi_" + id);
				if (jsonObject != null && jsonObject.optString("ret").equals("1")) {
					System.out.println("不可见时能够加载到缓存数据" + "__index=" + id);
					Message msg = Message.obtain();
					msg.what = 1;
					msg.obj = jsonObject;
					handler.sendMessage(msg);
					// handler.sendMessageDelayed(msg, 10000);
				} else {
					System.out.println("不可见时加载不到" + "__index=" + id);
				}
			} else {
				System.out.println("不可见时不进行任何操作" + "__index=" + id);
			}
		}
	}

	@Override
	public void onPause() {
		Log.i("Fragment5_3_item", "onPause__" + id);
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.i("Fragment5_3_item", "onResume__" + id);
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i("Fragment5_3_item", "onCreateView__" + id);
		if (view == null) {
			initView();
		} else {
			ViewGroup viewGroup = (ViewGroup) view.getParent();
			if (viewGroup != null) {
				viewGroup.removeAllViews();
			}
		}
		System.out.println("onCreateView__" + id);
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
		System.out.println("initView____id=" + id);
		boolean flag = getUserVisibleHint();
		boolean flag2 = isVisible();
		System.out.println("getUserVisibleHint=" + flag + "___id=" + id);
		System.out.println("isVisible=" + flag2 + "___id=" + id);

		int height = getResources().getDisplayMetrics().heightPixels;
		int height2 = item5_tv.getLayoutParams().height;
		int height2_2 = Utils.dip2px(activity, 40);
		int height2_3 = item5_tv.getMeasuredHeight();
		int height3 = getStatusBarHeight(activity);

		System.out.println("顶部测量高度5=" + item5_tv.getHeight());// 0
		ViewTreeObserver vto2 = item5_tv.getViewTreeObserver();
		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				item5_tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				System.out.println("顶部测量高度4=" + item5_tv.getHeight());// 79
			}
		});

		System.out.println("屏幕高度=" + height);// 960
		System.out.println("顶部高度=" + height2);// -2
		System.out.println("顶部高度2=" + height2_2);// 60
		System.out.println("顶部测量高度3=" + height2_3);// 0
		System.out.println("顶部测量高度5=" + item5_tv.getHeight());// 0
		System.out.println("标题栏高度=" + height3);// 38
	}

	private void getData() {
		System.out.println("加载数据" + id);
		new Thread(new Runnable() {
			@Override
			public void run() {
				AsyncHttpClient client = new AsyncHttpClient();
				client.get(url, new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						if (statusCode == 200) {
							System.out.println("44444444");
							Utils.saveJsonData(activity, "quanzi_" + id, jsonObject);
							if (jsonObject != null && jsonObject.optString("ret").equals("1")) {
								System.out.println("555555555");
								Message msg = Message.obtain();
								msg.what = 1;
								msg.obj = jsonObject;
								handler.sendMessage(msg);
							} else {
								System.out.println("33333333");
								handler.sendEmptyMessage(0);
							}
						}else {
							System.out.println("222222222");
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
						super.onFailure(statusCode, headers, throwable, errorResponse);
						System.out.println("11111111");
					}

				});
//				JSONObject jsonObject = HttpUtils.getHttpJsonObject(url);
//				Utils.saveJsonData(activity, "quanzi_" + id, jsonObject);
//				if (jsonObject != null && jsonObject.optString("ret").equals("1")) {
//					Message msg = Message.obtain();
//					msg.what = 1;
//					msg.obj = jsonObject;
//					handler.sendMessage(msg);
//				} else {
//					handler.sendEmptyMessage(0);
//				}
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
				System.out.println("listview载入数据__" + id);
				JSONObject jsonObject = (JSONObject) msg.obj;
				JSONArray jsonArray = jsonObject.optJSONArray("data");
				int length = jsonArray.length();
				jsonList.clear();
				for (int i = 0; i < length; i++) {
					jsonList.add(jsonArray.optJSONObject(i));
				}
				adapter.notifyDataSetChanged();
				System.out.println("更新ui完成__" + id);
				break;
			case 2:
				getData();
				break;
			}
		}
	};

	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;

		int x = 0, statusBarHeight = 0;

		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return statusBarHeight;
	}
}
