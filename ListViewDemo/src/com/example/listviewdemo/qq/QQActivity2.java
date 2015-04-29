package com.example.listviewdemo.qq;

import java.util.ArrayList;
import java.util.List;

import com.example.listviewdemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class QQActivity2 extends Activity {

	private ListView listView;
	private MyCustomAdapter adapter;
	private List<String> mData = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qq2);
		initView();
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.listView);
		adapter = new MyCustomAdapter(QQActivity2.this, mData);
		listView.setAdapter(adapter);

		for (int i = 1; i < 50; i++) {
			mData.add("item " + i);
			if (i % 4 == 0) {
				adapter.addSeparatorItem("separator " + i);
			}
		}

		adapter.notifyDataSetChanged();
	}
}
