package com.gftest.myappclient.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;
import com.gftest.myappclient.widget.CircleProgressBar;
import com.gftest.myappclient.widget.HorizontalProgressBarView;
import com.gftest.myappclient.widget.MyProgress;
import com.gftest.myappclient.widget.RoundProgressBar;

public class Progress extends BaseActivity implements OnClickListener {

	private MyProgress myProgress;
	private CircleProgressBar progress1;
	private HorizontalProgressBarView progress2;
	private RoundProgressBar mRoundProgressBar1, mRoundProgressBar2, mRoundProgressBar3, mRoundProgressBar4, mRoundProgressBar5;
	private Button button1;
	private int progress = 0;

	private int i = 0;
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			progress2.setProgress(msg.what);
			if (i <= progress2.getMax()) {
				handler.sendEmptyMessageDelayed(i++, 50);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress);
		initView();
	}

	private void initView() {
		myProgress = (MyProgress) findViewById(R.id.myProgress);
		progress1 = (CircleProgressBar) findViewById(R.id.progress1);
		progress2 = (HorizontalProgressBarView) findViewById(R.id.progress2);

		mRoundProgressBar1 = (RoundProgressBar) findViewById(R.id.roundProgressBar1);
		mRoundProgressBar2 = (RoundProgressBar) findViewById(R.id.roundProgressBar2);
		mRoundProgressBar3 = (RoundProgressBar) findViewById(R.id.roundProgressBar3);
		mRoundProgressBar4 = (RoundProgressBar) findViewById(R.id.roundProgressBar4);
		mRoundProgressBar5 = (RoundProgressBar) findViewById(R.id.roundProgressBar5);
		button1 = (Button) findViewById(R.id.button1);

		progress1.showText(true);
		progress1.setBarBackgroundColor("#ffcbd7");// 背景颜色
		progress1.setProgressColor("#ff537b");

		progress2.setMax(100);

		button1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			handler.sendEmptyMessageDelayed(i++, 50);
			progress1.setProgressWithAnimation(100);
			myProgress.startProgress(65);
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (progress <= 100) {
						progress += 3;
						System.out.println(progress);
						mRoundProgressBar1.setProgress(progress);
						mRoundProgressBar2.setProgress(progress);
						mRoundProgressBar3.setProgress(progress);
						mRoundProgressBar4.setProgress(progress);
						mRoundProgressBar5.setProgress(progress);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
			break;
		default:
			break;
		}

	}

}
