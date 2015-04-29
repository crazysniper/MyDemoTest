package com.example.listviewdemo.async;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.BaseAdapter;

import com.example.listviewdemo.adapter.Adapter2;
import com.example.listviewdemo.utils.HttpUtils;

public class MyTask2 extends AsyncTask<String, Void, List<JSONObject>> {

	private BaseAdapter baseAdapter;
	private Activity activity;
	private List<JSONObject> jsonList = new ArrayList<JSONObject>();
	private String url;

	public MyTask2(Activity activity, Adapter2 adapter, String url) {
		this.activity = activity;
		this.baseAdapter = adapter;
		this.url = url;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected List<JSONObject> doInBackground(String... params) {
		JSONObject jsonObject = HttpUtils.getJsonObject(url);
		try {
			if (jsonObject != null && jsonObject.getInt("ret") == 1) {
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
		baseAdapter.notifyDataSetChanged();
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
