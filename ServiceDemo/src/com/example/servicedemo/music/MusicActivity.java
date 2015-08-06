package com.example.servicedemo.music;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.servicedemo.R;

public class MusicActivity extends Activity implements OnClickListener {

	private Button play, pause, stop, close, exit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music);
		initView();
	}

	private void initView() {
		play = (Button) findViewById(R.id.play);
		pause = (Button) findViewById(R.id.pause);
		stop = (Button) findViewById(R.id.stop);
		close = (Button) findViewById(R.id.close);
		exit = (Button) findViewById(R.id.exit);

		play.setOnClickListener(this);
		pause.setOnClickListener(this);
		stop.setOnClickListener(this);
		close.setOnClickListener(this);
		exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		int op = -1;
		intent.setAction("com.example.servicedemo.music.MusicService");
		switch (v.getId()) {
		case R.id.play:
			op = 1;
			break;
		case R.id.pause:
			op = 2;
			break;
		case R.id.stop:
			op = 3;
			break;
		case R.id.exit:
			op = 4;
			stopService(intent);
			this.finish();
			break;
		case R.id.close:
			this.finish();
			break;
		}
		System.out.println("11111111111");
		Bundle bundle = new Bundle();
		bundle.putInt("op", op);
		intent.putExtras(bundle);
		startService(intent);
	}
}