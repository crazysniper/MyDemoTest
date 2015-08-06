package com.gftest.myappclient.wangyi;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.gftest.myappclient.R;
import com.gftest.myappclient.utils.HttpUtils;
import com.gftest.myappclient.utils.Utils;
import com.gftest.myappclient.xlistview.XListView;
import com.gftest.myappclient.xlistview.XListView.IXListViewListener;

@SuppressLint("ValidFragment")
public class QuanFragment_Item extends com.gftest.myappclient.BaseFragment implements IXListViewListener, OnItemClickListener {
	private View view, headerView;
	private Activity activity;
	private LayoutInflater layoutInflater;

	private XListView listView;
	private TextView quanTitle, quanPostNum, quanReplyNum;
	private QuanFragment_Item_Adapter adapter;

	private List<JSONObject> jsonList = new ArrayList<JSONObject>();
	/** 置顶帖 */
	private List<JSONObject> topJsonList = new ArrayList<JSONObject>();

	private static final int WHAT_DID_LOAD_CACHE = 1;// 加载缓存
	private static final int WHAT_DID_REFRESH = 2;// 刷新
	private static final int WHAT_DID_LOAD_MORE = 3;// 载入更多
	private static final int WHAT_DID_LOAD_NO_MORE = 4;// 无更多
	private static final int WHAT_DID_LOAD_NO_DATA = 5;// 没有数据
	private static final int WHAT_DID_REFRESH_AFTER_CACHE = 6;// 在加载完缓存之后触发刷新
	private static final int WHAT_DID_STOP_LOAD = 7;
	private static final int WHAT_DID_REFRESH2 = 8;// 超过一段时间，刷新

	private String birthday = "";
	/** 不可见时判断是否需要加载数据 */
	private boolean invisibleHasdLoaded = false;
	/** 可见时判断是否需要加载数据 */
	private boolean visibleHasdLoaded = false;
	private String lastUpdateTimeKey = "quan_last_update_time_key_";
	private String lastUpdateTime = "";
	private String cacheFileName = "";
	private String qid = "";

	public QuanFragment_Item() {
	}

	public QuanFragment_Item(Activity activity, String qid, int index) {
		this.activity = activity;
		this.qid = qid;
		lastUpdateTimeKey = lastUpdateTimeKey + qid;
		cacheFileName = "quan_" + qid + ".txt";
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			if (!visibleHasdLoaded) {
				visibleHasdLoaded = true;
				handler.sendEmptyMessageDelayed(WHAT_DID_REFRESH_AFTER_CACHE, 600);
			} else {
				lastUpdateTime = Utils.getSharedPreferences(activity, lastUpdateTimeKey, String.valueOf(System.currentTimeMillis()));
				long last = Long.parseLong(lastUpdateTime);
				long now = System.currentTimeMillis();
				if (now - last > 5 * 60 * 1000) {
					Utils.saveSharedPreferences(activity, lastUpdateTimeKey, String.valueOf(now));
					handler.sendEmptyMessageDelayed(WHAT_DID_REFRESH2, 400);
				}
			}
		} else {
			if (!invisibleHasdLoaded) {
				invisibleHasdLoaded = true;
				JSONObject jsonObject = Utils.readJsonData(activity, cacheFileName);
				if (jsonObject != null && "1".equals(jsonObject.optString("ret"))) {
					Message msg = Message.obtain();
					msg.what = WHAT_DID_LOAD_CACHE;
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
		layoutInflater = LayoutInflater.from(activity);
		view = layoutInflater.inflate(R.layout.fragment_wangyi_item, null);

		listView = (XListView) view.findViewById(R.id.listView);
		headerView = layoutInflater.inflate(R.layout.wangyi_item_header, null);
		quanTitle = (TextView) headerView.findViewById(R.id.quanTitle);
		quanPostNum = (TextView) headerView.findViewById(R.id.quanPostNum);
		quanReplyNum = (TextView) headerView.findViewById(R.id.quanReplyNum);

		lastUpdateTime = Utils.getSharedPreferences(activity, lastUpdateTimeKey, String.valueOf(System.currentTimeMillis()));
		// listView.setLastUpdateTime(lastUpdateTime);

		birthday = Utils.getSharedPreferences(activity, "birthday", "2014-12-24");

		listView.addHeaderView(headerView, null, false);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);

		adapter = new QuanFragment_Item_Adapter(activity, jsonList, topJsonList);
		listView.setAdapter(adapter);

		String name = QuanUtils.getInstance().getName(qid);
		quanTitle.setText(name);

		listView.setOnItemClickListener(this);
		setData();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Toast.makeText(activity, "点击的下标是" + position, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRefresh() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// System.out.println("--》》刷新url=" + url);
				JSONObject jsonObject = HttpUtils.getHttpJsonObject("");
				// System.out.println("刷新=" + jsonObject);
				if (jsonObject != null && "1".equals(jsonObject.optString("ret"))) {
					Utils.saveJsonData(activity, cacheFileName, jsonObject);
					Message msg = Message.obtain();
					msg.what = WHAT_DID_REFRESH;
					msg.obj = jsonObject;
					handler.sendMessage(msg);
				} else {
					handler.sendEmptyMessage(WHAT_DID_STOP_LOAD);
				}
			}
		}).start();
	}

	@Override
	public void onLoadMore() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (jsonList == null || jsonList.size() == 0) {
					handler.sendEmptyMessage(WHAT_DID_LOAD_NO_MORE);
					return;
				}
				JSONObject lastJsonObject = jsonList.get(jsonList.size() - 1);
				String dated = lastJsonObject.optString("dated");
				// System.out.println("加载更多=" + (url + "&dated=" + dated));
				JSONObject jsonObject = HttpUtils.getHttpJsonObject("");
				if (jsonObject != null) {
					Message msg = Message.obtain();
					msg.what = WHAT_DID_LOAD_MORE;
					msg.obj = jsonObject;
					handler.sendMessage(msg);
				} else {
					handler.sendEmptyMessage(WHAT_DID_LOAD_NO_MORE);
				}
			}
		}).start();
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_DID_LOAD_CACHE:
				JSONObject jsonObject1 = (JSONObject) msg.obj;
				setData(jsonObject1);
				adapter.notifyDataSetChanged();
				stopLoad();
				break;
			case WHAT_DID_REFRESH:
				JSONObject jsonObject2 = (JSONObject) msg.obj;
				setData(jsonObject2);
				adapter.notifyDataSetChanged();
				stopLoad();
				break;
			case WHAT_DID_LOAD_NO_DATA:
				stopLoad();
				break;
			case WHAT_DID_LOAD_MORE:
				JSONObject jsonObject3 = (JSONObject) msg.obj;
				JSONArray jsonArray3 = jsonObject3.optJSONArray("data");
				int length3 = jsonArray3.length();
				for (int i = 0; i < length3; i++) {
					jsonList.add(jsonArray3.optJSONObject(i));
				}
				adapter.notifyDataSetChanged();
				stopLoad();
				break;
			case WHAT_DID_LOAD_NO_MORE:
				adapter.notifyDataSetChanged();
				stopLoad();
				break;
			case WHAT_DID_REFRESH_AFTER_CACHE:
				// listView.setFooterDisplay(false);
				break;
			case WHAT_DID_STOP_LOAD:
				stopLoad();
				break;
			case WHAT_DID_REFRESH2:
				listView.setSelection(0);
				sendEmptyMessageDelayed(WHAT_DID_REFRESH_AFTER_CACHE, 200);
				break;
			default:
				break;
			}
		}
	};

	private void setData(JSONObject jsonObject) {
		jsonList.clear();
		topJsonList.clear();
		String postNum = jsonObject.optString("post_num");
		String replyNum = jsonObject.optString("reply_num");
		showHeader(postNum, replyNum);
		JSONArray jsonArray = jsonObject.optJSONArray("data");
		int length = jsonArray.length();
		for (int i = 0; i < length; i++) {
			jsonList.add(jsonArray.optJSONObject(i));
			if ("1".equals(jsonArray.optJSONObject(i).optString("post_type"))) {
				topJsonList.add(jsonArray.optJSONObject(i));
			}
		}
	}

	/**
	 * 
	 * @param postNum
	 *            帖子数
	 * @param replyNum
	 *            回帖数
	 */
	private void showHeader(String postNum, String replyNum) {
		quanPostNum.setText("帖子：" + postNum);
		quanReplyNum.setText("回帖：" + replyNum);
	}

	protected void stopLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();

		lastUpdateTime = String.valueOf(System.currentTimeMillis());
		Utils.saveSharedPreferences(activity, lastUpdateTimeKey, lastUpdateTime);
	}

	private void setData() {
		JSONObject jsonObject = null;
		try {
			for (int i = 0; i < 20; i++) {
				jsonObject = new JSONObject();
				jsonObject.put("id", "100" + i);
				jsonObject.put("avatar", "");
				jsonObject.put("nickname", "哈喽" + i);
				jsonObject.put("age_str", "已怀孕");
				jsonObject.put("showdated", "1分钟前");
				jsonObject.put("title", "标题" + i);
				jsonObject.put("re_num", String.valueOf(i));
				jsonObject.put("post_type", String.valueOf((i % 3)));
				jsonObject.put("gold_added", i % 2);
				JSONArray jsonArray = new JSONArray();
				jsonObject.put("pic_small", jsonArray);
				jsonList.add(jsonObject);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
