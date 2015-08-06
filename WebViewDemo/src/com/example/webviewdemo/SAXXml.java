package com.example.webviewdemo;

import java.io.StringReader;

import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * SAX(Simple API for
 * XML)解析器是一种基于事件的解析器，它的核心是事件处理模式，主要是围绕着事件源以及事件处理器来工作的。当事件源产生事件后
 * ，调用事件处理器相应的处理方法，一个事件就可以得到处理
 * 。在事件源调用事件处理器中特定方法的时候，还要传递给事件处理器相应事件的状态信息，这样事件处理器才能够根据提供的事件信息来决定自己的行为。
 * SAX解析器的优点是解析速度快，占用内存少。非常适合在Android移动设备中使用。
 * 
 * 
 * 
 * 
 * SAX是一个解析速度快并且占用内存少的xml解析器，非常适合用于Android等移动设备。
 * SAX解析XML文件采用的是事件驱动，也就是说，它并不需要解析完整个文档
 * ，在按内容顺序解析文档的过程中，SAX会判断当前读到的字符是否合法XML语法中的某部分
 * ，如果符合就会触发事件。所谓事件，其实就是一些回调（callback）方法，这些方法(事件)定义在ContentHandler接口。
 * 
 */
public class SAXXml extends Activity implements OnClickListener {
	private Button sax_xml;
	private TextView sax_xml_result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sax_xml);
		initView();
	}

	private void initView() {
		sax_xml = (Button) findViewById(R.id.sax_xml);
		sax_xml_result = (TextView) findViewById(R.id.sax_xml_result);

		sax_xml.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sax_xml:
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						HttpClient httpClient = new DefaultHttpClient();
						// 指定访问的服务器地址是电脑本机
						HttpGet httpGet = new HttpGet("http://192.168.0.205:8080/MyApp/get_data.xml");
						HttpResponse httpResponse = httpClient.execute(httpGet);
						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							// 请求和响应都成功了
							HttpEntity entity = httpResponse.getEntity();
							String response = EntityUtils.toString(entity, "utf-8");
							parseXMLWithSAX(response);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			break;
		}
	}

	private void parseXMLWithSAX(String xmlData) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			XMLReader xmlReader = factory.newSAXParser().getXMLReader();
			ContentHandler handler = new ContentHandler();
			// 将ContentHandler的实例设置到XMLReader中
			xmlReader.setContentHandler(handler);
			// 开始执行解析
			xmlReader.parse(new InputSource(new StringReader(xmlData)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * startElement()、characters()和endElement()这三个方法是有参数的，
	 * 从XML中解析出的数据就会以参数的形式传入到这些方法中
	 * 。需要注意的是，在获取结点中的内容时，characters()方法可能会被调用多次，一些换行符也被当作内容解析出来
	 * ，我们需要针对这种情况在代码中做好控制
	 * 
	 * 
	 * 使用SAX解析XML文件一般有以下五个步骤： 1、创建一个SAXParserFactory对象；
	 * 2、调用SAXParserFactory中的newSAXParser方法创建一个SAXParser对象；
	 * 3、然后在调用SAXParser中的getXMLReader方法获取一个XMLReader对象； 4、实例化一个DefaultHandler对象
	 * 5、连接事件源对象XMLReader到事件处理类DefaultHandler中
	 * 6、调用XMLReader的parse方法从输入源中获取到的xml数据 7、通过DefaultHandler返回我们需要的数据集合。
	 * 
	 * @author Gao
	 * 
	 */
	public class ContentHandler extends DefaultHandler {
		private String nodeName;
		private StringBuilder id;
		private StringBuilder name;
		private StringBuilder version;

		/**
		 * 在开始XML解析的时候调用<br>
		 * 接收文档开始的通知。当遇到文档的开头的时候，调用这个方法，可以在其中做一些预处理的工作。
		 */
		@Override
		public void startDocument() throws SAXException {
			id = new StringBuilder();
			name = new StringBuilder();
			version = new StringBuilder();
		}

		/**
		 * 在开始解析某个结点的时候调用<br>
		 * 接收元素开始的通知。当读到一个开始标签的时候，会触发这个方法。其中uri表示元素的命名空间
		 * localName表示元素的本地名称（不带前缀）；qName表示元素的限定名（带前缀）；atts 表示元素的属性集合
		 */
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			// 记录当前结点名
			nodeName = localName;
		}

		/**
		 * 在获取结点中内容的时候调用<br>
		 * 接收字符数据的通知。该方法用来处理在XML文件中读到的内容，第一个参数用于存放文件的内容，
		 * 后面两个参数是读到的字符串在这个数组中的起始位置和长度，使用new String(ch,start,length)就可以获取内容。
		 */
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			// 根据当前的结点名判断将内容添加到哪一个StringBuilder对象中
			if ("id".equals(nodeName)) {
				id.append(ch, start, length);
			} else if ("name".equals(nodeName)) {
				name.append(ch, start, length);
			} else if ("version".equals(nodeName)) {
				version.append(ch, start, length);
			}
		}

		/**
		 * 在完成解析某个结点的时候调用<br>
		 * 接收文档的结尾的通知。在遇到结束标签的时候，调用这个方法。其中，uri表示元素的命名空间；localName表示元素的本地名称（不带前缀）
		 * ；name表示元素的限定名（带前缀）
		 */
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if ("app".equals(localName)) {
				Log.i("ContentHandler", "id is " + id.toString().trim());
				Log.i("ContentHandler", "name is " + name.toString().trim());
				Log.i("ContentHandler", "version is " + version.toString().trim());
				// 最后要将StringBuilder清空掉
				id.setLength(0);
				name.setLength(0);
				version.setLength(0);
			}
		}

		/**
		 * 在完成整个XML解析的时候调用<br>
		 */
		@Override
		public void endDocument() throws SAXException {
			super.endDocument();
		}
	}
}
