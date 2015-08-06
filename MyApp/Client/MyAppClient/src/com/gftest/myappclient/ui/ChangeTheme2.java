package com.gftest.myappclient.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;
import com.gftest.myappclient.utils.ChangeThemeUtils;
import com.gftest.myappclient.utils.ToastUtils;

public class ChangeTheme2 extends BaseActivity implements OnClickListener {
	private boolean blFlag = false;
	private ImageView ivBook;
	private Button btnSet;
	private Button btnGet;
	private ToastUtils toastUtils;
	private int sTheme;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sTheme = ChangeThemeUtils.onActivityCreateSetTheme(ChangeTheme2.this);
		setContentView(R.layout.activity_change_theme);
		initView();
	}

	private void initView() {
		toastUtils = ToastUtils.getInstance();
		btnSet = (Button) findViewById(R.id.btnSet);
		btnGet = (Button) findViewById(R.id.btnGet);
		ivBook = (ImageView) findViewById(R.id.ivBook);

		btnSet.setOnClickListener(this);
		btnGet.setOnClickListener(this);
		ivBook.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSet:
			ChangeThemeUtils.changeToTheme(ChangeTheme2.this, sTheme);
			break;
		case R.id.btnGet:
			toastUtils.showToast(ChangeTheme2.this, "blFlag: " + (sTheme == 1 ? true : false), Toast.LENGTH_SHORT);
			break;
		default:
			break;
		}
	}

}
