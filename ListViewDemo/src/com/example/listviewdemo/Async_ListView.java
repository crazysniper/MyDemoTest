package com.example.listviewdemo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.example.listviewdemo.adapter.Adapter2;
import com.example.listviewdemo.utils.HttpUtils;

public class Async_ListView extends Activity {

	private ListView listView;
	private List<JSONObject> jsonList = new ArrayList<JSONObject>();
	private MyTask async;
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

		adapter = new Adapter2(Async_ListView.this, jsonList);
		listView.setAdapter(adapter);
		async = new MyTask();
		async.execute(url);
	}

	public class MyTask extends AsyncTask<String, Void, List<JSONObject>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected List<JSONObject> doInBackground(String... params) {
			System.out.println("111111111111111");
			JSONObject jsonObject = HttpUtils.getJsonObject(url);
			try {
				if (jsonObject != null && jsonObject.getInt("ret") == 1) {
					System.out.println("xxxxxxxxxxxxxxx");
					List<JSONObject> jsonList = new ArrayList<JSONObject>();
					jsonList = getJsonToList(jsonObject);
					return jsonList;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<JSONObject> result) {
			System.out.println("000000000000");
			super.onPostExecute(result);
			jsonList.clear();
			jsonList.addAll(result);
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 将json转成list
	 * 
	 * @param jsonObject
	 * @return
	 */
	public List<JSONObject> getJsonToList(JSONObject jsonObject) {
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		try {
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			int length = jsonArray.length();
			for (int i = 0; i < length; i++) {
				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
				jsonList.add(jsonObject2);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonList;
	}
}
