package com.gftest.myappclient.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;

/**
 * onSaveInstanceState()方法并不是总会被调用的，只有系统为了节省内存资源而强制销毁Activity时才会调用，
 * 所以应当仅仅通过重写onSaveInstanceState()方法来保存一些临时数据，而不是持久数据。要保存持久数据，应该使用onPause()方法
 * 
 * @author Gao
 * 
 */
public class SaveInstanceStateActivity extends BaseActivity {
	EditText mEditText; // EditText对象，用于输入内容
	private static final String KEY1 = "editTextValues"; // 用于保存EditText对象内容的键
	private static final String TAG = "SaveInstanceStateActivity"; // Log输出过滤器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_onsaveinstancestate);
		mEditText = (EditText) this.findViewById(R.id.edittext);

		// 如果Bundle对象中保存有所需对象的内容，则恢复该对象内容
		if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY1)) {
			mEditText.setText(savedInstanceState.getString(KEY1));
			System.out.println("savedInstanceState.getString(KEY1)=" + savedInstanceState.getString(KEY1));
		}
		Log.i(TAG, "-->onCreate()");
	}

	/**
	 * Function : onSaveInstanceState方法，销毁Activity时调用 Author : 博客园-依旧淡然
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		String editTextValues = mEditText.getText().toString(); // 获取EditText对象内容
		outState.putString(KEY1, editTextValues); // 以键值对的形式保存EditText对象内容
		Log.i(TAG, "-->onSaveInstanceState()");
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
