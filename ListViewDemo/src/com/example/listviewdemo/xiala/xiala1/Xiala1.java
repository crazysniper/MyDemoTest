package com.example.listviewdemo.xiala.xiala1;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.example.listviewdemo.R;
import com.example.listviewdemo.xiala.xiala1.MyXiala1.PullToRefreshListener;

public class Xiala1 extends Activity implements PullToRefreshListener {

	private MyXiala1 xiala1_listView;
	private ListView listView;

	private Xiala1Adapter adapter;
	private List<String> letterList = new ArrayList<String>();
	private int index = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiala1);
		initView();
	}

	private void initView() {
		xiala1_listView = (MyXiala1) findViewById(R.id.xiala1_listView);
		listView = (ListView) findViewById(R.id.listView);

		adapter = new Xiala1Adapter(Xiala1.this, letterList);
		listView.setAdapter(adapter);

		xiala1_listView.setPullToRefreshListener(this, 0);

		setData();
	}

	@Override
	public void onRefresh() {
//		handler.sendEmptyMessage(2);
		handler.sendEmptyMessageDelayed(2, 3000);
	}

	@Override
	public void onLoadMore() {

	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				adapter.notifyDataSetChanged();
				break;
			case 2:
				setData();
				adapter.notifyDataSetChanged();
				xiala1_listView.finishRefreshing();
				break;
			default:
				break;
			}
		}
	};

	public void setData() {
		letterList.clear();
		for (int i = index; i < index + 10; i++) {
			letterList.add(i + "");
		}
		index = index + 10;
		handler.sendEmptyMessage(1);
	}

}
