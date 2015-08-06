package com.gftest.myappclient.widget;

import com.gftest.myappclient.utils.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 圆形进度条
 */
public class CircleProgressBar extends View {
	private Context context;
	private int maxProgress = 100;
	private int progress = 0;
	private int progressStrokeWidth = 0;
	private String barBackgroundColor = "#ff888888", progressColor = "#19B600", textColor = "#5787b6";
	private boolean hasText = true;
	// 画圆所在的距形区域
	RectF oval;
	Paint paint;

	public CircleProgressBar(Context context) {
		super(context);
		this.context = context;
		initView();
	}

	public CircleProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	public CircleProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initView();
	}

	private void initView() {
		oval = new RectF();
		paint = new Paint();
		progressStrokeWidth = Utils.dip2px(context, 5);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = getWidth();
		int height = getHeight();
		int min = Math.min(width, height);
		width = min;
		height = min;

		paint.setAntiAlias(true);// 设置画笔为抗锯齿
		paint.setColor(Color.parseColor(barBackgroundColor));
		// paint.setColor(Color.rgb(255, 111, 132));
		canvas.drawColor(Color.TRANSPARENT); // 白色背景
		paint.setStrokeWidth(progressStrokeWidth);// 画笔的宽度
		paint.setStyle(Style.STROKE);// 设置中空的样式

		oval.left = progressStrokeWidth / 2; // 左上角x
		oval.top = progressStrokeWidth / 2; // 左上角y
		oval.right = width - progressStrokeWidth / 2; // 左下角x
		oval.bottom = height - progressStrokeWidth / 2; // 右下角y

		canvas.drawArc(oval, -90, 360, false, paint); // 绘制白色圆圈，即进度条背景
		// paint.setColor(Color.rgb(0x57, 0x87, 0xb6));
		paint.setColor(Color.parseColor(progressColor));
		if (progress > maxProgress) {
			progress = maxProgress;
		}
		canvas.drawArc(oval, -90, ((float) progress / maxProgress) * 360, false, paint); // 绘制进度圆弧，这里是蓝色

		if (hasText) {
			paint.setColor(Color.parseColor(textColor));
			paint.setStrokeWidth(1);
			String text = progress + "%";
			int textHeight = height / 4;
			paint.setTextSize(textHeight);
			int textWidth = (int) paint.measureText(text, 0, text.length());
			paint.setStyle(Style.FILL);
			canvas.drawText(text, width / 2 - textWidth / 2, height / 2 + textHeight / 2, paint);
		}
	}

	public int getMaxProgress() {
		return maxProgress;
	}

	/**
	 * 默认为100
	 * 
	 * @param maxProgress
	 */
	public void setMaxProgress(int maxProgress) {
		this.maxProgress = maxProgress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
		this.invalidate();
	}

	/**
	 * 动画,满值是100
	 * 
	 * @param start
	 *            开始位置
	 * @param end
	 *            结束位置
	 * @param duration
	 *            持续时间（毫秒）
	 */
	public void setProgressWithAnimation(final int endPos) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int sleepTime = 10;
				int slowDown = endPos / 4 * 3;
				int curPos = 0;
				while (curPos <= endPos) {
					if (curPos > slowDown) {
						sleepTime++;
					}
					curPos++;
					setProgressNotInUiThread(curPos);
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/**
	 * 设置外层圈的背景16进制颜色
	 * 
	 * @param color
	 */
	public void setBarBackgroundColor(String color) {
		barBackgroundColor = color;
	}

	/**
	 * 设置进度条16进制颜色
	 * 
	 * @param color
	 */
	public void setProgressColor(String color) {
		progressColor = color;
	}

	/**
	 * 设置进度条中间的文字16进制颜色
	 * 
	 * @param color
	 */
	public void setTextColor(String color) {
		textColor = color;
	}

	/**
	 * 是否显示进度条中间的文字（即百分比）
	 * 
	 * @param flag
	 */
	public void showText(boolean flag) {
		hasText = flag;
	}

	/**
	 * 非UI线程调用
	 */
	public void setProgressNotInUiThread(int progress) {
		this.progress = progress;
		this.postInvalidate();
	}
}
