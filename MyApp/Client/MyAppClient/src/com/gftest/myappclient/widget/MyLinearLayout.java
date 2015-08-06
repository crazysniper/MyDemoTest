package com.gftest.myappclient.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gftest.myappclient.R;
import com.gftest.myappclient.widget.DayProgress.DayProgressListener;

public class MyLinearLayout extends LinearLayout {

	private LayoutInflater layoutInflater;
	private View view;
	private DayProgress circle1, circle2, circle3, circle4;
	private ProgressBar progress1, progress2, progress3;
	private TextView tv1, tv2, tv3, tv4;
	private int progressBarMax = 1000;

	public MyLinearLayout(Context context) {
		this(context, null);
	}

	public MyLinearLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setOrientation(LinearLayout.HORIZONTAL);
		layoutInflater = LayoutInflater.from(context);
		view = layoutInflater.inflate(R.layout.jifen_qiandao, null);
		addView(view);

		circle1 = (DayProgress) view.findViewById(R.id.circle1);
		circle2 = (DayProgress) view.findViewById(R.id.circle2);
		circle3 = (DayProgress) view.findViewById(R.id.circle3);
		circle4 = (DayProgress) view.findViewById(R.id.circle4);
		progress1 = (ProgressBar) view.findViewById(R.id.progress1);
		progress2 = (ProgressBar) view.findViewById(R.id.progress2);
		progress3 = (ProgressBar) view.findViewById(R.id.progress3);

		progress1.setMax(progressBarMax);
		progress2.setMax(progressBarMax);
		progress3.setMax(progressBarMax);

		tv1 = (TextView) view.findViewById(R.id.tv1);
		tv2 = (TextView) view.findViewById(R.id.tv2);
		tv3 = (TextView) view.findViewById(R.id.tv3);
		tv4 = (TextView) view.findViewById(R.id.tv4);
	}

	public void change() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					int index = 0;
					while (true) {
						Thread.sleep(1000);
						changeByXunhuan(index);
						index = (++index) % 4;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void changeByXunhuan(int level) {
		switch (level) {
		case 0:// 显示到1
			circle1.back();
			circle2.back();
			circle3.back();
			circle4.back();
			circle1.changeArc();
			progress1.setProgress(0);
			progress2.setProgress(0);
			progress3.setProgress(0);
			changeText(1);
			break;
		case 1:// 显示到2
			circle2.back();
			circle3.back();
			circle4.back();
			progress1.setProgress(0);
			progress2.setProgress(0);
			progress3.setProgress(0);
			int pr = 0;
			while (pr < progressBarMax) {
				pr++;
				progress1.setProgress(pr);
			}
			circle2.changeArc();
			changeText(2);
			break;
		case 2:// 显示到第3个
			circle3.back();
			circle4.back();
			progress2.setProgress(0);
			progress3.setProgress(0);
			int pr2 = 0;
			while (pr2 < progressBarMax) {
				pr2++;
				progress2.setProgress(pr2);
			}
			circle3.changeArc();
			changeText(3);
			break;
		case 3:// 显示到第4个
			circle4.back();
			progress3.setProgress(0);
			int pr4 = 0;
			while (pr4 < progressBarMax) {
				pr4++;
				progress3.setProgress(pr4);
			}
			circle4.changeArc();
			changeText(4);
			break;

		default:
			break;
		}
	}

	/**
	 * 指定位置的动画效果，不循环
	 * 
	 * @param level
	 */
	private void changeCircle(int level) {
		switch (level) {
		case 0:
			circle2.back();
			circle3.back();
			circle4.back();
			circle1.changeArc();
			progress1.setProgress(0);
			progress2.setProgress(0);
			progress3.setProgress(0);
			changeText(1);
			break;
		case 1:
			circle2.back();
			circle3.back();
			circle4.back();
			circle1.changeArc();
			progress1.setProgress(0);
			progress2.setProgress(0);
			progress3.setProgress(0);
			changeText(1);
			circle1.setDayProgressListener(new DayProgressListener() {
				@Override
				public void finished() {
					int pr = 0;
					while (pr < progressBarMax) {
						pr++;
						progress1.setProgress(pr);
					}
					circle2.changeArc();
					changeText(2);
				}
			});
			break;
		case 2:
			circle2.back();
			circle3.back();
			circle4.back();
			circle1.changeArc();
			progress1.setProgress(0);
			progress2.setProgress(0);
			progress3.setProgress(0);
			changeText(1);
			circle1.setDayProgressListener(new DayProgressListener() {
				@Override
				public void finished() {
					int pr = 0;
					while (pr < progressBarMax) {
						pr++;
						progress1.setProgress(pr);
					}
					circle2.changeArc();
					changeText(2);
				}
			});
			circle2.setDayProgressListener(new DayProgressListener() {
				@Override
				public void finished() {
					int pr = 0;
					while (pr < progressBarMax) {
						pr++;
						progress2.setProgress(pr);
					}
					circle3.changeArc();
					changeText(3);
				}
			});
			break;
		case 3:
			circle2.back();
			circle3.back();
			circle4.back();
			circle1.changeArc();
			progress1.setProgress(0);
			progress2.setProgress(0);
			progress3.setProgress(0);
			changeText(1);
			circle1.setDayProgressListener(new DayProgressListener() {
				@Override
				public void finished() {
					int pr = 0;
					while (pr < progressBarMax) {
						pr++;
						progress1.setProgress(pr);
					}
					circle2.changeArc();
					changeText(2);
				}
			});
			circle2.setDayProgressListener(new DayProgressListener() {
				@Override
				public void finished() {
					new Thread(new Runnable() {
						@Override
						public void run() {

						}
					}).start();
					int pr = 0;
					while (pr < progressBarMax) {
						pr++;
						progress2.setProgress(pr);
					}
					circle3.changeArc();
					changeText(3);
				}
			});
			circle3.setDayProgressListener(new DayProgressListener() {
				@Override
				public void finished() {
					int pr = 0;
					while (pr < progressBarMax) {
						pr++;
						progress3.setProgress(pr);
					}
					circle4.changeArc();
					changeText(4);
				}
			});
			break;

		default:
			break;
		}
	}

	private void changeProgressbar(int level) {
		progress1.setProgress(0);
		progress2.setProgress(0);
		progress3.setProgress(0);
		switch (level) {
		case 0:
			break;
		case 1:
			progress1.setProgress(100);
			break;
		case 2:
			progress1.setProgress(100);
			progress2.setProgress(100);
			break;
		case 3:
			progress1.setProgress(100);
			progress2.setProgress(100);
			progress3.setProgress(100);
			break;
		default:
			break;
		}

	}

	private void changeText(int level) {
		handler.sendEmptyMessage(level);
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			tv1.setTextColor(Color.parseColor("#b4afab"));
			tv2.setTextColor(Color.parseColor("#b4afab"));
			tv3.setTextColor(Color.parseColor("#b4afab"));
			tv4.setTextColor(Color.parseColor("#b4afab"));
			switch (msg.what) {
			case 1:
				tv1.setTextColor(Color.parseColor("#f0840c"));
				break;
			case 2:
				tv1.setTextColor(Color.parseColor("#f0840c"));
				tv2.setTextColor(Color.parseColor("#f0840c"));
				break;
			case 3:
				tv1.setTextColor(Color.parseColor("#f0840c"));
				tv2.setTextColor(Color.parseColor("#f0840c"));
				tv3.setTextColor(Color.parseColor("#f0840c"));
				break;
			case 4:
				tv1.setTextColor(Color.parseColor("#f0840c"));
				tv2.setTextColor(Color.parseColor("#f0840c"));
				tv3.setTextColor(Color.parseColor("#f0840c"));
				tv4.setTextColor(Color.parseColor("#f0840c"));
				break;

			default:
				break;
			}
		}
	};

}
