package com.example.webviewdemo;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.webviewdemo.xml.Book;
import com.example.webviewdemo.xml.BookParser;
import com.example.webviewdemo.xml.DomBookParser;
import com.example.webviewdemo.xml.PullBookParser;
import com.example.webviewdemo.xml.SaxBookParser;

/**
 * SAX解析器操作起来太笨重，DOM不适合文档较大，内存较小的场景，唯有PULL轻巧灵活，速度快，占用内存小，使用非常顺手。
 * 
 */
public class XmlActivity extends Activity implements OnClickListener {

	private Button pull_xml, sax_xml, dom_xml, read_xml, write_xml, read_xml2, read_xml3;
	private BookParser parser;
	private List<Book> books;
	private static final String TAG = "XmlActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xml);
		initView();
	}

	private void initView() {
		pull_xml = (Button) findViewById(R.id.pull_xml);
		sax_xml = (Button) findViewById(R.id.sax_xml);
		dom_xml = (Button) findViewById(R.id.dom_xml);
		read_xml = (Button) findViewById(R.id.read_xml);
		write_xml = (Button) findViewById(R.id.write_xml);
		read_xml2 = (Button) findViewById(R.id.read_xml2);
		read_xml3 = (Button) findViewById(R.id.read_xml3);

		pull_xml.setOnClickListener(this);
		sax_xml.setOnClickListener(this);
		dom_xml.setOnClickListener(this);
		read_xml.setOnClickListener(this);
		write_xml.setOnClickListener(this);
		read_xml2.setOnClickListener(this);
		read_xml3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.pull_xml:
			intent = new Intent(XmlActivity.this, PullXml.class);
			break;
		case R.id.sax_xml:
			intent = new Intent(XmlActivity.this, SAXXml.class);
			break;
		case R.id.dom_xml:
			intent = new Intent(XmlActivity.this, DOMXml.class);
			break;
		case R.id.read_xml:// sax
			try {
				InputStream is = getAssets().open("books.xml");
				parser = new SaxBookParser(); // 创建SaxBookParser实例
				books = parser.parse(is); // 解析输入流
				for (Book book : books) {
					Log.i(TAG, book.toString());
				}
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
			return;
		case R.id.write_xml:
			try {
				String xml = parser.serialize(books); // 序列化
				FileOutputStream fos = openFileOutput("books2.xml", Context.MODE_PRIVATE);
				fos.write(xml.getBytes("UTF-8"));
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
			return;
		case R.id.read_xml2:// dom
			try {
				InputStream is = getAssets().open("books.xml");
				// parser = new SaxBookParser();
				parser = new DomBookParser();
				books = parser.parse(is);
				for (Book book : books) {
					Log.i(TAG, book.toString());
				}
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
			return;
		case R.id.read_xml3:// pull
			try {
				InputStream is = getAssets().open("books.xml");
				// parser = new SaxBookParser();
				// parser = new DomBookParser();
				parser = new PullBookParser();
				books = parser.parse(is);
				for (Book book : books) {
					Log.i(TAG, book.toString());
				}
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
			return;
		}
		startActivity(intent);
	}
}
