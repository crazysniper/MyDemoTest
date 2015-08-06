package com.example.webviewdemo;

import java.io.IOException;
import java.io.StringReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * PULL解析器的运行方式和SAX类似，都是基于事件的模式。不同的是，在PULL解析过程中，我们需要自己获取产生的事件然后做相应的操作，
 * 而不像SAX那样由处理器触发一种事件的方法，执行我们的代码。PULL解析器小巧轻便，解析速度快，简单易用，非常适合在Android移动设备中使用，
 * Android系统内部在解析各种XML时也是用PULL解析器。
 * 
 */
public class PullXml extends Activity implements OnClickListener {

	private Button pull_xml;
	private TextView pull_xml_result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pull_xml);
		initView();
	}

	private void initView() {
		pull_xml = (Button) findViewById(R.id.pull_xml);
		pull_xml_result = (TextView) findViewById(R.id.pull_xml_result);

		pull_xml.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pull_xml:
			System.out.println("开始解析");
			new Thread(new Runnable() {
				@Override
				public void run() {
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet("http://192.168.0.205:8080/MyApp/get_data.xml");
					try {
						HttpResponse httpResponse = httpClient.execute(httpGet);
						System.out.println("httpResponse.getStatusLine().getStatusCode()=" + httpResponse.getStatusLine().getStatusCode());
						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							HttpEntity httpEntity = httpResponse.getEntity();
							String result = EntityUtils.toString(httpEntity, "utf-8");
							System.out.println("result=" + result);
							parseXmlWithPull(result);
						}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
			break;
		}
	}

	/**
	 * 这里首先要获取到一个XmlPullParserFactory的实例，并借助这个实例得到XmlPullParser对象，
	 * 然后调用XmlPullParser的setInput
	 * ()方法将服务器返回的XML数据设置进去就可以开始解析了。解析的过程也是非常简单，通过getEventType
	 * ()可以得到当前的解析事件，然后在一个while循环中不断地进行解析
	 * ，如果当前的解析事件不等于XmlPullParser.END_DOCUMENT，
	 * 说明解析工作还没完成，调用next()方法后可以获取下一个解析事件。
	 * 在while循环中，我们通过getName()方法得到当前结点的名字，如果发现结点名等于id
	 * 、name或version，就调用nextText()方法来获取结点内具体的内容，每当解析完一个app结点后就将获取到的内容打印出来。
	 * 
	 * @param resuString
	 */
	private void parseXmlWithPull(String resuString) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(resuString));
			int eventType = xmlPullParser.getEventType();
			String id = "";
			String name = "";
			String version = "";
			while (eventType != XmlPullParser.END_DOCUMENT) {// 说明解析工作还没完成
				String nodeName = xmlPullParser.getName();// 得到当前结点的名字
				switch (eventType) {
				case XmlPullParser.START_TAG:// 开始解析某个结点
					if ("id".equals(nodeName)) {
						id = xmlPullParser.nextText();// nextText()方法来获取结点内具体的内容
					} else if ("name".equals(nodeName)) {
						name = xmlPullParser.nextText();
					} else if ("version".equals(nodeName)) {
						version = xmlPullParser.nextText();
					}
					break;
				case XmlPullParser.END_TAG: {// 完成解析某个结点
					if ("app".equals(nodeName)) {
						Log.i("MainActivity", "id is " + id);
						Log.i("MainActivity", "name is " + name);
						Log.i("MainActivity", "version is " + version);
					}
					break;
				}
				default:
					break;
				}
				eventType = xmlPullParser.next();// 调用next()方法后可以获取下一个解析事件
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

	}
}
