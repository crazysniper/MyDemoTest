package com.gftest.myvideo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Button audioOnline, videoOnline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		audioOnline = (Button) findViewById(R.id.audioOnline);
		videoOnline = (Button) findViewById(R.id.videoOnline);

		audioOnline.setOnClickListener(this);
		videoOnline.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.audioOnline:
			intent = new Intent(MainActivity.this, AudioOnline.class);
			break;
		case R.id.videoOnline:
			intent = new Intent(MainActivity.this, VideoOnline.class);
			break;
		default:
			break;
		}
		startActivity(intent);
	}

}
