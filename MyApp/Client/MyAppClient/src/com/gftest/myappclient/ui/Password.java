package com.gftest.myappclient.ui;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;

/**
 * 切换密码明文和密文
 * 
 * @author Gao
 * 
 */
public class Password extends BaseActivity {

	private EditText psw;
	private CheckBox isVisible;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password);
		initView();
	}

	private void initView() {
		psw = (EditText) findViewById(R.id.psw);
		isVisible = (CheckBox) findViewById(R.id.isVisible);

		isVisible.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {// 设置为明文显示
					psw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				} else {// 设置为密文显示
					psw.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
			}
		});

	}

}
