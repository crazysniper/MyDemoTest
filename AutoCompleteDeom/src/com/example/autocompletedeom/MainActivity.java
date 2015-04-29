package com.example.autocompletedeom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private AutoCompleteTextView auto;
	private Button search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	public void initView() {
		auto = (AutoCompleteTextView) findViewById(R.id.auto);
		search = (Button) findViewById(R.id.search);
		search.setOnClickListener(this);
		initAutoComplete("history", auto);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search:
			saveHistory("history", auto);
			break;
		}
	}

	/**
	 * 把指定AutoCompleteTextView中内容保存到sharedPreference中指定的字符段
	 * 
	 * @param field
	 *            保存在sharedPreference中的字段名
	 * @param autoCompleteTextView
	 *            要操作的AutoCompleteTextView
	 */
	public void saveHistory(String field, AutoCompleteTextView auto) {
		String text = auto.getText().toString();
		SharedPreferences sp = getSharedPreferences("network_url", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		String longHistory = sp.getString(field, "nothing");
		if (!longHistory.contains(text + ",")) {
			StringBuilder sb = new StringBuilder(longHistory);
			sb.insert(0, text + ",");
			editor.putString("history", sb.toString()).commit();
		}
	}

	/**
	 * 初始化AutoCompleteTextView，最多显示5项提示，使 AutoCompleteTextView在一开始获得焦点时自动提示
	 * 
	 * @param field
	 *            保存在sharedPreference中的字段名
	 * @param autoCompleteTextView
	 *            要操作的AutoCompleteTextView
	 */
	private void initAutoComplete(String field, AutoCompleteTextView auto) {
		SharedPreferences sp = getSharedPreferences("network_url", Context.MODE_PRIVATE);
		String longHistory = sp.getString("history", "nothing");
		String[] histories = longHistory.split(",");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, histories);
		if (histories.length > 50) {
			String[] newHistories = new String[50];
			System.arraycopy(histories, 0, newHistories, 0, 50);
			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, newHistories);
		}
		auto.setAdapter(adapter);
		auto.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				AutoCompleteTextView view = (AutoCompleteTextView) v;
				if (hasFocus) {
					view.showDropDown();
				}
			}
		});

	}

	public void toSearchView(View v) {
		startActivity(new Intent(MainActivity.this,SearchView_1.class));
	}
}
