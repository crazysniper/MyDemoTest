package com.example.listviewdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.listviewdemo.adapter.DelAdapter;
import com.example.listviewdemo.utils.ToastUtils;

public class List_Slide_Del_Activity extends Activity implements OnItemClickListener {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_slide_del);
		initView();
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(this);

		List<String> list = new ArrayList<String>();
		DelAdapter adapter = new DelAdapter(List_Slide_Del_Activity.this, list);
		listView.setAdapter(adapter);

		for (int i = 0; i < 20; i++) {
			list.add("哈喽" + i);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (parent == listView) {
			ToastUtils.showToast(List_Slide_Del_Activity.this, "点击的是：" + id, Toast.LENGTH_SHORT);
			Button del_btn = (Button) view.findViewById(R.id.del_btn);
			del_btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

				}
			});
		}
	}
}
