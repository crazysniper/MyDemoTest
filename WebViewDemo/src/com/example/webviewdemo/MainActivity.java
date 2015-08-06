package com.example.webviewdemo;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;

/**
 * http://blog.csdn.net/t12x3456/article/details/13769731
 * 
 * 防止webview注入 http://blog.csdn.net/leehong2005/article/details/11808557
 * 
 */
public class MainActivity extends Activity implements OnClickListener {

	private Button open_webview, open_local_webview, xml;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		open_webview = (Button) findViewById(R.id.open_webview);
		open_local_webview = (Button) findViewById(R.id.open_local_webview);
		xml = (Button) findViewById(R.id.xml);

		open_webview.setOnClickListener(this);
		open_local_webview.setOnClickListener(this);
		xml.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.open_webview:
			intent = new Intent(MainActivity.this, WebViewActivity.class);
			break;
		case R.id.open_local_webview:
			intent = new Intent(MainActivity.this, LocalWebView.class);
			break;
		case R.id.xml:
			intent = new Intent(MainActivity.this, XmlActivity.class);
			break;
		}
		startActivity(intent);
	}

}
