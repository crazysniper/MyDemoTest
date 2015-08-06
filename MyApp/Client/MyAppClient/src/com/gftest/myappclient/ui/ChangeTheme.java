package com.gftest.myappclient.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;
import com.gftest.myappclient.utils.ToastUtils;

public class ChangeTheme extends BaseActivity implements OnClickListener {
	private boolean blFlag = false;
	private ImageView ivBook;
	private Button btnSet;
	private Button btnGet;
	private ToastUtils toastUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTheme(R.style.MyThemeDefault);// setTheme一定要在setContentView之前被调用
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
		ivBook.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == btnSet || v == ivBook) {
			if (blFlag) {
				setTheme(R.style.MyThemeDefault);
				blFlag = false;
			} else if (!blFlag) {
				setTheme(R.style.MyThemeNight);
				blFlag = true;
			}
			setContentView(R.layout.activity_change_theme);
			btnSet = (Button) findViewById(R.id.btnSet);
			btnGet = (Button) findViewById(R.id.btnGet);
			ivBook = (ImageView) findViewById(R.id.ivBook);
			btnSet.setOnClickListener(this);
			btnGet.setOnClickListener(this);
			ivBook.setOnClickListener(this);
		} else if (v == btnGet) {
			toastUtils.showToast(ChangeTheme.this, "blFlag: " + blFlag, Toast.LENGTH_SHORT);
		}
	}

}
