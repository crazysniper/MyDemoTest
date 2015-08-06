package com.example.imagedemo.image;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class HttpUtils {

	/**
	 * http方式获取json
	 * 
	 * @param url
	 * @return
	 */
	public static JSONObject getHttpJsonObject(String url) {
		JSONObject jsonObject = null;
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				try {
					jsonObject = new JSONObject(result);
					return jsonObject;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 检测网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 保存json
	 * 
	 * @param context
	 * @param fileName
	 * @param content
	 */
	public static void saveJsonData(Context context, String fileName, JSONObject content) {
		if (content != null) {
			try {
				FileOutputStream outputStream = context.openFileOutput(fileName, Activity.MODE_PRIVATE);
				outputStream.write(content.toString().getBytes());
				outputStream.flush();
				outputStream.close();
				// System.out.println("to sdCard:"+content.toString().getBytes());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void saveJsonCache(Context context, String fileName, JSONArray content) {
		if (content != null) {
			try {
				FileOutputStream outputStream = context.openFileOutput(fileName, Activity.MODE_PRIVATE);
				outputStream.write(content.toString().getBytes());
				outputStream.flush();
				outputStream.close();
				// System.out.println("to sdCard:"+content.toString().getBytes());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取json
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static JSONObject readJsonData(Context context, String fileName) {
		try {
			FileInputStream inputStream = context.openFileInput(fileName);
			byte[] bytes = new byte[1024 * 3];
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			while (inputStream.read(bytes) != -1) {
				arrayOutputStream.write(bytes, 0, bytes.length);
			}
			inputStream.close();
			arrayOutputStream.close();
			String content = new String(arrayOutputStream.toByteArray());

			JSONObject obj;
			try {
				obj = new JSONObject(content);
				return obj;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static JSONArray readJsonCache(Context context, String fileName) {
		try {
			FileInputStream inputStream = context.openFileInput(fileName);
			byte[] bytes = new byte[1024 * 3];
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			while (inputStream.read(bytes) != -1) {
				arrayOutputStream.write(bytes, 0, bytes.length);
			}
			inputStream.close();
			arrayOutputStream.close();
			String content = new String(arrayOutputStream.toByteArray());

			JSONArray obj;
			try {
				obj = new JSONArray(content);
				return obj;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> readListData(Context context, String fileName) {
		try {
			FileInputStream inputStream = context.openFileInput(fileName);
			byte[] bytes = new byte[1024 * 3];
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			while (inputStream.read(bytes) != -1) {
				arrayOutputStream.write(bytes, 0, bytes.length);
			}
			inputStream.close();
			arrayOutputStream.close();
			String content = new String(arrayOutputStream.toByteArray());

			List<String> obj = new ArrayList<String>();
			JSONArray jsonArray = null;
			try {
				jsonArray = new JSONArray(content);
				for (int i = 0; i < jsonArray.length(); i++) {
					obj.add(jsonArray.getString(i));
				}
				return obj;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取网落图片资源
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getHttpBitmap(String url) {
		URL myFileURL;
		Bitmap bitmap = null;
		try {
			myFileURL = new URL(url);
			// 获得连接
			HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
			// 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
			conn.setConnectTimeout(6000);
			// 连接设置获得数据流
			conn.setDoInput(true);
			// 不使用缓存
			conn.setUseCaches(false);
			// 这句可有可无，没有影响
			// conn.connect();
			// 得到数据流
			InputStream is = conn.getInputStream();
			// 解析得到图片
			bitmap = BitmapFactory.decodeStream(is);
			// 关闭数据流
			is.close();
		} catch (Exception e) {
			// System.out.println("bit_error1" + e.toString());
		}
		return bitmap;
	}
}
