package com.example.listviewdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.listviewdemo.adapter.ListViewAdapter5;
import com.example.listviewdemo.model.StudentEntity;

public class ListView5 extends Activity implements OnClickListener, OnItemClickListener {

	private ListView listView;
	private Button btn1, btn2, btn3;
	private ListViewAdapter5 adapter;
	private List<StudentEntity> stuList = new ArrayList<StudentEntity>();
	private String aa = "";
	static int index = 1;
	private List<StudentEntity> stuList2 = new ArrayList<StudentEntity>();
	private List<String> aaList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview5);
		initView();
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.listView);
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);

		adapter = new ListViewAdapter5(this, stuList, aaList, stuList2);
		listView.setAdapter(adapter);
		setData();

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		listView.setOnItemClickListener(this);
	}

	private void setData() {
		stuList.clear();
		for (int i = 0; i < 20; i++) {
			StudentEntity entity = new StudentEntity();
			entity.setUid(i);
			entity.setUsername("小明" + i);
			stuList.add(entity);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			stuList.clear();
			int k = 20 * index;
			System.out.println("k=" + k);
			index++;
			for (int i = k; i < k + 20; i++) {
				StudentEntity entity = new StudentEntity();
				entity.setUid(i);
				entity.setUsername("小明" + i);
				stuList.add(entity);
			}
			adapter.notifyDataSetChanged();
			break;
		case R.id.btn2:
			aaList.clear();
			aa = "haha";
			aaList.add(aa);
			adapter.notifyDataSetChanged();
			break;
		case R.id.btn3:
			index = 1;
			stuList.clear();
			for (int i = 0; i < 20; i++) {
				StudentEntity entity = new StudentEntity();
				entity.setUid(i);
				entity.setUsername("小明" + i);
				stuList.add(entity);
			}
			adapter.notifyDataSetChanged();
			break;

		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		index = 0;
	}
}
