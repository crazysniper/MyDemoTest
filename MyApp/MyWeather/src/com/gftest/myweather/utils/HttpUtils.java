package com.gftest.myweather.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

	public static void sendHttpRequest(final String address, final HttpCallbackListener httpCallbackListener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(10000);
					connection.setReadTimeout(10000);

					InputStream inputStream = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
					StringBuilder response = new StringBuilder();
					String line = "";
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					if (httpCallbackListener != null) {// 回调onFinish()方法
						httpCallbackListener.onFinished(response.toString());
					}
				} catch (Exception e) {
					if (httpCallbackListener != null) {// 回调onError()方法
						httpCallbackListener.onError(e);
					}
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}
