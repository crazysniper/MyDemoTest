package com.gftest.myappclient.ui;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;
import com.gftest.myappclient.utils.HttpUtils;
import com.gftest.myappclient.utils.ToastUtils;

/**
 * http://www.open-open.com/lib/view/open1333356387467.html
 * @author Gao
 *
 */
public class Register extends BaseActivity implements OnClickListener {

	private Button register_get, register_post;
	private EditText nickname, psd;
	private AutoCompleteTextView auto;

	private ToastUtils toastUtils;
	private String name, password;
	private String getUrl = "http://192.168.0.205:8080/MyApp/login?action=get&";
	private String postUrl = "http://192.168.0.205:8080/MyApp/login?action=post";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}

	private void initView() {
		toastUtils = ToastUtils.getInstance();
		nickname = (EditText) findViewById(R.id.nickname);
		psd = (EditText) findViewById(R.id.psd);
		auto = (AutoCompleteTextView) findViewById(R.id.auto);
		register_get = (Button) findViewById(R.id.register_get);
		register_post = (Button) findViewById(R.id.register_post);

		auto.setThreshold(1);// 输入1个字母就开始自动提示

		register_get.setOnClickListener(this);
		register_post.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		name = nickname.getText().toString().trim();
		password = psd.getText().toString().trim();
		System.out.println("用户名=" + name);
		System.out.println("密码=" + password);
		if (!check(name, password)) {
			toastUtils.showToast(Register.this, "没有填全", Toast.LENGTH_SHORT);
			return;
		}
		switch (v.getId()) {
		case R.id.register_get:
			new Thread(new Runnable() {
				@Override
				public void run() {
					String url1 = getUrl + "name=" + name + "&password=" + password;
					System.out.println("url1=" + url1);
					JSONObject jsonObject = HttpUtils.getHttpJsonObject(url1);
					System.out.println("json=" + jsonObject);
					if (jsonObject != null && "1".equals(jsonObject.optString("ret"))) {
						handler.sendEmptyMessage(1);
					} else {
						handler.sendEmptyMessage(0);
					}
				}
			}).start();
			break;
		case R.id.register_post:
			new Thread(new Runnable() {
				@Override
				public void run() {

				}
			}).start();
			break;
		default:
			break;
		}
	}

	private boolean check(String nickname, String psd) {
		if (TextUtils.isEmpty(nickname) || TextUtils.isEmpty(psd)) {
			return false;
		}
		return true;
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				toastUtils.showToast(Register.this, "注册成功", Toast.LENGTH_SHORT);
				break;
			case 0:
				toastUtils.showToast(Register.this, "注册失败", Toast.LENGTH_SHORT);
				break;
			}
		}
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
