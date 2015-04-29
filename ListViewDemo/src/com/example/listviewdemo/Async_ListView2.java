package com.example.listviewdemo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.listviewdemo.adapter.Adapter2;
import com.example.listviewdemo.async.MyTask2;
import com.example.listviewdemo.utils.TaskUtil;

public class Async_ListView2 extends Activity {

	private ListView listView;
	private List<JSONObject> jsonList = new ArrayList<JSONObject>();
	private MyTask2 async;
	private Adapter2 adapter;
	private String url = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview2);
		initView();
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.listView);

		adapter = new Adapter2(Async_ListView2.this, jsonList);
		listView.setAdapter(adapter);
		async = new MyTask2(Async_ListView2.this, adapter, url);
		TaskUtil.execute(async);
	}
}
