package com.example.listviewdemo.qq;

import java.util.ArrayList;
import java.util.List;

import com.example.listviewdemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class QQActivity3 extends Activity implements OnClickListener {

	private ListView listView;
	private EditText editContent;
	private Button send;
	private QQAdapter3 adapter;
	private List<String> msgList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qq);
		initView();
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.listView);
		editContent = (EditText) findViewById(R.id.editContent);
		send = (Button) findViewById(R.id.send);
		adapter = new QQAdapter3(QQActivity3.this, msgList);
		listView.setAdapter(adapter);

		send.setOnClickListener(this);
		initData();
	}

	private void initData() {
		msgList.add("哈喽，美女");
		msgList.add("你好，帅哥");
		msgList.add("不好");
		handler.sendEmptyMessage(1);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				adapter.notifyDataSetChanged();
				break;

			default:
				break;
			}

		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.send:
			String content = editContent.getText().toString().trim();
			System.out.println("content=" + content);
			if (!TextUtils.isEmpty(content)) {
				msgList.add(content);
				handler.sendEmptyMessage(1);
				// listView.setSelection(msgList.size() - 1);
				listView.setSelection(msgList.size());
				editContent.setText("");
			}
			break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

}
