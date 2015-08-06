package com.gftest.myappclient.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author xiangxm
 */
public class Common {

	/**
	 * 格式化文件大
	 * 
	 * @param volume
	 *            文件大小
	 * @return 格式化的字符
	 */
	public static String getVolume(long volume) {
		float num = 1.0F;
		String str = null;
		if (volume < 1024) {
			str = volume + "B";
		} else if (volume < 1048576) {
			num = num * volume / 1024;
			str = String.format("%.1f", num) + "K";
		} else if (volume < 1073741824) {
			num = num * volume / 1048576;
			str = String.format("%.1f", num) + "M";
		} else if (volume < 1099511627776L) {
			num = num * volume / 1073741824;
			str = String.format("%.1f", num) + "G";
		}
		return str;
	}

	/**
	 * 
	 * 设置网络参数
	 */
	public static HttpURLConnection getHttpParams(URL httpUrl) {
		HttpURLConnection http;
		try {
			http = (HttpURLConnection) httpUrl.openConnection();
			http.setConnectTimeout(5 * 1000);
			http.setRequestMethod("GET");
			http.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			http.setRequestProperty("Accept-Language", "zh-CN");
			http.setRequestProperty("Referer", httpUrl.toString());
			http.setRequestProperty("Charset", "UTF-8");

			http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
			http.setRequestProperty("Connection", "Keep-Alive");
			return http;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
