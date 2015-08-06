package com.example.webviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * DOM是基于树形结构的的节点或信息片段的集合，允许开发人员使用DOM
 * API遍历XML树、检索所需数据。分析该结构通常需要加载整个文档和构造树形结构，然后才可以检索和更新节点信息。
 * 由于DOM在内存中以树形结构存放，因此检索和更新效率会更高。但是对于特别大的文档，解析和加载整个文档将会很耗资源。
 * 
 */
public class DOMXml extends Activity implements OnClickListener {
	private Button dom_xml;
	private TextView dom_xml_result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dom_xml);
		initView();
	}

	private void initView() {
		dom_xml = (Button) findViewById(R.id.dom_xml);
		dom_xml_result = (TextView) findViewById(R.id.dom_xml_result);

		dom_xml.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dom_xml:
			break;
		}
	}
}
