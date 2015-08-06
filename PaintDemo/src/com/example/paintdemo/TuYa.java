package com.example.paintdemo;

import com.example.paintdemo.mywidget.TuYaView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * http://blog.csdn.net/csh159/article/details/7876148
 * 
 * @author Administrator
 * 
 */
public class TuYa extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private TuYaView handWrite = null;
	private Button clear = null;
	private Button save = null;
	private ImageView saveImage = null;

	public static Handler hh;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tuya);

		handWrite = (TuYaView) findViewById(R.id.handwriteview);
		save = (Button) findViewById(R.id.save);
		saveImage = (ImageView) findViewById(R.id.saveimage);
		clear = (Button) findViewById(R.id.clear);
		clear.setOnClickListener(this);
		save.setOnClickListener(this);
		handerl();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.clear:
			handWrite.clear();
			break;
		case R.id.save:
			Message msg = new Message();
			msg = Message.obtain(handWrite.handler1, 2);
			handWrite.handler1.sendMessage(msg);
			if (handWrite.saveImage() != null) {
				Log.i("RG", "111111111111111111111");

			} else {
				saveImage.setVisibility(View.GONE);
			}
			break;
		}
	}

	public void handerl() {
		hh = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				int what = msg.what;
				if (what == 3) {
					saveImage.setVisibility(View.VISIBLE);
					saveImage.setImageBitmap(handWrite.saveImage());
					handWrite.setImge();
				}
				super.handleMessage(msg);
			}

		};
	}

}