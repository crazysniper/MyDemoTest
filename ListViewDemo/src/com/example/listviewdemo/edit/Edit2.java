package com.example.listviewdemo.edit;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.listviewdemo.R;

public class Edit2 extends Activity {

	private EditText edit3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit2);
		edit3 = (EditText) findViewById(R.id.edit3);

//		edit3.setFocusable(true);
//		edit3.setFocusableInTouchMode(true);
		edit3.requestFocus();

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

}
