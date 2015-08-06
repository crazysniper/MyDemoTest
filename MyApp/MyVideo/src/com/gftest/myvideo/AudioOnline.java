package com.gftest.myvideo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class AudioOnline extends Activity implements OnClickListener, OnSeekBarChangeListener {

	private Button btnPlayUrl, btnPause, btnStop;
	private SeekBar skbProgress;

	private Player player;

	private int progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audioonline);
		initView();
	}

	private void initView() {
		btnPlayUrl = (Button) findViewById(R.id.btnPlayUrl);
		btnPause = (Button) findViewById(R.id.btnPause);
		btnStop = (Button) findViewById(R.id.btnStop);
		skbProgress = (SeekBar) findViewById(R.id.skbProgress);

		btnPlayUrl.setOnClickListener(this);
		btnPause.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		skbProgress.setOnSeekBarChangeListener(this);

		player = new Player(skbProgress);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnPlayUrl:
			String url = "http://yinyueshiting.baidu.com/data2/music/137081688/137078183169200128.mp3?xcode=4d753ce041af15cd89cddfe7ae41fd450b86fc0e5b731aec";
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
	public void onStopTrackingTouch(SeekBar seekBar) {// seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
		// seekBar.setProgress(this.progress);
		Message msg = handler.obtainMessage(1);
		msg.obj = seekBar;
		msg.arg1 = this.progress;
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				SeekBar seekBar = (SeekBar) msg.obj;
				seekBar.setProgress(msg.arg1);
				break;

			default:
				break;
			}
		}
	};

}
