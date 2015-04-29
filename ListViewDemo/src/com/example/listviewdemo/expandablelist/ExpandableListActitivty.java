package com.example.listviewdemo.expandablelist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.listviewdemo.R;

public class ExpandableListActitivty extends Activity {

	private List<String> groupList = new ArrayList<String>();
	private ArrayList<ArrayList<String>> childList = new ArrayList<ArrayList<String>>();
	private ArrayList<String> itemList = new ArrayList<String>();
	private ExpandableListView expandableListView;
	private MyExpandListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandlistview);
		expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

		adapter = new MyExpandListAdapter(ExpandableListActitivty.this, groupList, childList);
		expandableListView.setAdapter(adapter);

		expandableListView.setCacheColorHint(Color.TRANSPARENT);
		expandableListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		setData();
	}

	private void setData() {
		for (int i = 0; i < 4; i++) {
			groupList.add("title" + i);
		}

		itemList.add("item11");
		itemList.add("item12");
		childList.add(itemList);

		itemList = new ArrayList<String>();
		itemList.add("item21");
		itemList.add("item22");
		itemList.add("item23");
		childList.add(itemList);

		itemList = new ArrayList<String>();
		itemList.add("item31");
		itemList.add("item32");
		itemList.add("item33");
		itemList.add("item34");
		itemList.add("item35");
		itemList.add("item36");
		childList.add(itemList);

		itemList = new ArrayList<String>();
		itemList.add("item41");
		itemList.add("item42");
		itemList.add("item43");
		childList.add(itemList);

		// 这个是设定每个Group之前的那个图标
		// expandableListView.setGroupIndicator(null);
		expandableListView.setGroupIndicator(getResources().getDrawable(R.drawable.ic_launcher));
		expandableListView.expandGroup(0);// 展开第一个分组
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

}
