package com.gftest.myappclient.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;

public class SharedPreferencesActivitiy extends BaseActivity implements OnClickListener {

	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shared);
		initView();
	}

	private void initView() {
		sharedPreferences = getSharedPreferences("myappclient", Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		editor.putString("null", null);
		String aaString=sharedPreferences.getString("null","");
		System.out.println("aaString="+aaString);
		editor.commit();
	}

	@Override
	public void onClick(View v) {

	}

}
