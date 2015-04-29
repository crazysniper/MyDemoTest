package com.example.listviewdemo.focus;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.example.listviewdemo.R;
import com.example.listviewdemo.adapter.FocusAdapter;

/**
 * 1.将ListView中的Item布局中的子控件focusable属性设置为false
 * 2.在getView方法中设置button.setFocusable(false)
 * 
 * @author Gao
 * 
 */
public class FocusActivity extends Activity implements OnItemClickListener, OnItemLongClickListener {
	private ListView listView;
	private FocusAdapter adapter;

	private List<String> dataList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_focus);
		listView = (ListView) findViewById(R.id.listView);

		for (int i = 0; i < 10; i++) {
			dataList.add(String.valueOf(i));
		}
		System.out.println("大小=" + dataList.size());
		adapter = new FocusAdapter(FocusActivity.this, dataList);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		System.out.println("点击下标=" + id);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		System.out.println("长按下标=" + id);
		return true;
	}
}
