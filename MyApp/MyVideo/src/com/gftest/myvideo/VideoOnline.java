package com.gftest.myvideo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class VideoOnline extends Activity implements OnClickListener, OnSeekBarChangeListener {
	private SurfaceView surfaceView;
	private Button btnPlayUrl, btnPause, btnStop;
	private SeekBar skbProgress;

	private VideoPlayer player;

	private int progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_videoonline);
		initView();
	}

	private void initView() {
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		btnPlayUrl = (Button) findViewById(R.id.btnPlayUrl);
		btnPause = (Button) findViewById(R.id.btnPause);
		btnStop = (Button) findViewById(R.id.btnStop);
		skbProgress = (SeekBar) findViewById(R.id.skbProgress);

		btnPlayUrl.setOnClickListener(this);
		btnPause.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		skbProgress.setOnSeekBarChangeListener(this);

		player = new VideoPlayer(surfaceView, skbProgress);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnPlayUrl:
			String url = "http://61.153.87.228/mp4/86/224463186.h264_1.040052010054EFC8860A02BD30C97B9F526352-AC69-ADA9-7AA8-000289181132.mp4?key=e665522227483b958bbe2e5501392b004065eef16e&playtype=52&tk=155012700720679631790849693&brt=52&bc=0&xid=040052010054EFC8860A02BD30C97B9F526352-AC69-ADA9-7AA8-000289181132&nt=0&nw=0&bs=0&ispid=410000031&rc=200&inf=12&si=un&npc=1625&pp=0&ul=0&mt=0&sid=95001&pc=0&cip=49.74.84.246&id=tudou&hf=0&hd=52&sta=0&ssid=0&itemid=289181132&fi=0&sz=7461908";
			player.playUrl(url);
			break;
		case R.id.btnPause:
			player.pause();
			break;
		case R.id.btnStop:
			player.stop();
			break;
		default:
			break;
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		this.progress = progress * player.mediaPlayer.getDuration() / seekBar.getMax();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
//		player.mediaPlayer.seekTo(progress);
		Message msg = handler.obtainMessage(1);
		msg.obj = seekBar;
		msg.arg1 = this.progress;
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				player.mediaPlayer.seekTo(msg.arg1);
				break;

			default:
				break;
			}
		}
	};
}
