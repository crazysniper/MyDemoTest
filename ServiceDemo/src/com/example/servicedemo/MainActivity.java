package com.example.servicedemo;

import com.example.servicedemo.music.MusicActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Button music;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		music = (Button) findViewById(R.id.music);
		music.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.music:
			Intent intent = new Intent(MainActivity.this, MusicActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
