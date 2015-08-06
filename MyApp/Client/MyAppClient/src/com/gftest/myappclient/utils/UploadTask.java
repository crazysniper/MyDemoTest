package com.gftest.myappclient.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpConnection;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

public class UploadTask extends AsyncTask<Void, Void, JSONObject> {

	private String url;
	private Map<String, String> map = new HashMap<String, String>();
	private List<String> materialList = new ArrayList<String>();
	private List<String> stepList = new ArrayList<String>();
	private List<String> tipList = new ArrayList<String>();
	private List<String> pathList = new ArrayList<String>();

	public UploadTask(String url, Map<String, String> map, List<String> materialList, List<String> stepList, List<String> tipList, List<String> pathList) {
		this.url = url;
		this.map = map;
		this.materialList = materialList;
		this.stepList = stepList;
		this.tipList = tipList;
		this.pathList = pathList;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected JSONObject doInBackground(Void... params) {
		return upload(url, map, materialList, stepList, tipList, pathList);
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
	}

	private JSONObject upload(String url, Map<String, String> map, List<String> materialList, List<String> stepList, List<String> tipList, List<String> pathList) {
		URL url2 = null;
		try {
			url2 = new URL(url);// 创建一个URL对象
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();// 获取链接
			httpURLConnection.setReadTimeout(10000);// 设置读取超时
			httpURLConnection.setConnectTimeout(10000);// 设置超时时间
			httpURLConnection.setRequestMethod("POST");// 设置请求模式,注意，要大写
			httpURLConnection.setDoInput(true);// 设置允许输入
			httpURLConnection.setDoOutput(true);// 设置允许输出
			httpURLConnection.setUseCaches(false);

			/**
			 * --发送POST请求必须设置允许输出 --不要使用缓存,容易出现问题.
			 * --在开始用HttpURLConnection对象的setRequestProperty()设置,就是生成HTML文件头.
			 */
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");// 设置维持长连
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
//			httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));// 设置文件长度
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 设置文件类型

			OutputStream outputStream = httpURLConnection.getOutputStream();// 得到网络返回的输入输出流

			InputStream inputStream = httpURLConnection.getInputStream();// 得到网络返回的输入输出流

			httpURLConnection.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
