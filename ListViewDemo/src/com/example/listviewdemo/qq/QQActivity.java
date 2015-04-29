package com.example.listviewdemo.qq;

import java.util.ArrayList;
import java.util.List;

import com.example.listviewdemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class QQActivity extends Activity implements OnClickListener {

	private ListView listView;
	private EditText editContent;
	private Button send;
	private QQAdapter adapter;
	private List<Msg> msgList = new ArrayList<Msg>();

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
		adapter = new QQAdapter(QQActivity.this, msgList);
		listView.setAdapter(adapter);

		send.setOnClickListener(this);
		initData();
	}

	private void initData() {
		Msg msg = new Msg();
		msg.setContent("哈喽，美女");
		msg.setType(Msg.TYPE_RECEIVED);
		msgList.add(msg);

		msg = new Msg();
		msg.setContent("你好，帅哥");
		msg.setType(Msg.TYPE_SEND);
		msgList.add(msg);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.send:
			String content = editContent.getText().toString().trim();
			if (!TextUtils.isEmpty(content)) {
				Msg msg = new Msg();
				msg.setContent(content);
				msg.setType(Msg.TYPE_SEND);
				msgList.add(msg);
				adapter.notifyDataSetChanged();
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
