package com.gftest.myappclient.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;

public class TimerActivity extends BaseActivity implements OnClickListener {

	private Button timerBtn1, timerCancel1;
	private TextView tv1;
	private Timer timer1;

	/** 默认还没有取消 */
	private boolean isCancel = false;

	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timer);
		initView();
	}

	private void initView() {
		timerBtn1 = (Button) findViewById(R.id.timerBtn1);
		timerCancel1 = (Button) findViewById(R.id.timerCancel1);
		tv1 = (TextView) findViewById(R.id.tv1);

		timerBtn1.setOnClickListener(this);
		timerCancel1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.timerBtn1:
			timer1 = new Timer();//
			if (!isCancel) {// 还没有取消
				timer1.schedule(new TimerTask() {
					@Override
					public void run() {
						handler.sendEmptyMessage(1);
					}
				}, 3000, 1000);
			} else {// 继续
				isCancel = false;
				timer1.schedule(new TimerTask() {
					@Override
					public void run() {
						handler.sendEmptyMessage(1);
					}
				}, 0, 1000);
			}

			break;
		case R.id.timerCancel1:
			if (timer1 != null) {
				timer1.cancel();// Timer.cancel()是取消所有的计时器，如果要继续，则要重新实例化Timer
				isCancel = true;
			} else {
				System.out.println("先开启计时器，再取消");
			}
			break;
		default:
			break;
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				tv1.setText(String.valueOf(count++));
				break;

			default:
				break;
			}
		}
	};
}
