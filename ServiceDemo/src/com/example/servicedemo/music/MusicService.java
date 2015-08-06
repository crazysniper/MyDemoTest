package com.example.servicedemo.music;

import java.io.IOException;

import com.example.servicedemo.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {
	private static final String TAG = "MusicService";

	private MediaPlayer mediaPlayer = new MediaPlayer();

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "onCreate");
		if (mediaPlayer == null) {
			mediaPlayer = MediaPlayer.create(this, R.raw.shake_start);
			mediaPlayer.setLooping(false);// 不循环;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "onBind");
		return null;
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		Log.i(TAG, "onStart");
		super.onStart(intent, startId);
		if (intent != null) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				int op = bundle.getInt("op");
				System.out.println("op=" + op);
				switch (op) {
				case 1:
					play();
					break;
				case 2:
					pause();
					break;
				case 3:
					stop();
					break;
				}
			}
		}
	}

	private void play() {
		if (!mediaPlayer.isLooping()) {
			mediaPlayer.start();
		}
	}

	private void pause() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		}
	}

	private void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			try {
				mediaPlayer.prepare();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "onStartCommand");
		return super.onStartCommand(intent, flags, startId);

	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.i(TAG, "onUnbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		super.onDestroy();
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}
}
