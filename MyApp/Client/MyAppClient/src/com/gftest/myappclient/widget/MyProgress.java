package com.gftest.myappclient.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.gftest.myappclient.R;

public class MyProgress extends View {

	private Paint roundPaint;
	private Paint roundProgressPaint;
	private Paint textPaint;

	private int myRoundColor;
	private int myRoudProgressColor;
	private float myRoudWidth;

	private int myTextColor;
	private float myTextSize;
	private boolean myShowText;

	private int myMax;
	private int myStyle;
	public static final int STROKE = 0;
	public static final int FILL = 1;

	private int currentProgress = 0;

	public MyProgress(Context context) {
		this(context, null);
	}

	public MyProgress(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyProgress(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		roundPaint = new Paint();
		roundProgressPaint = new Paint();
		textPaint = new Paint();

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.myProgress);
		myRoundColor = typedArray.getColor(R.styleable.myProgress_myRoundColor, Color.RED);
		myRoudProgressColor = typedArray.getColor(R.styleable.myProgress_myRoudProgressColor, Color.GREEN);
		myRoudWidth = typedArray.getDimension(R.styleable.myProgress_myRoudWidth, 2);
		myTextColor = typedArray.getColor(R.styleable.myProgress_myTextColor, Color.RED);
		myTextSize = typedArray.getDimension(R.styleable.myProgress_myTextSize, 14);
		myShowText = typedArray.getBoolean(R.styleable.myProgress_myShowText, false);
		myMax = typedArray.getInteger(R.styleable.myProgress_myMax, 100);
		myStyle = typedArray.getInt(R.styleable.myProgress_myStyle, 0);

		typedArray.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int centre = getWidth() / 2;// 获取圆心的x坐标
		int radius = (int) (centre - myRoudWidth / 2);// 圆环的半径

		roundPaint.setColor(myRoundColor);// 设置圆环的颜色
		if (myStyle == 0) {
			roundPaint.setStyle(Style.STROKE);
		} else {
			roundPaint.setStyle(Style.FILL);
		}
		roundPaint.setStrokeWidth(myRoudWidth);// 设置圆环的宽度
		roundPaint.setAntiAlias(true);// 消除锯齿
		canvas.drawCircle(centre, centre, radius, roundPaint);

		textPaint.setColor(myTextColor);
		textPaint.setTextSize(myTextSize);
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);
		int percent = (int) (currentProgress / (float) myMax * 100);
		System.out.println("currentProgress=" + currentProgress);
		System.out.println("percent=" + percent);
		float textWidth = textPaint.measureText(percent + "%");

		if (myShowText && myStyle == STROKE) {
			canvas.drawText(percent + "%", centre - textWidth / 2, centre + textWidth / 2, textPaint);
		}

		roundProgressPaint.setColor(myRoudProgressColor);
		roundProgressPaint.setStrokeWidth(myRoudWidth);
		RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
		switch (myStyle) {
		case STROKE:
			roundProgressPaint.setStyle(Style.STROKE);
			// 逆时针是负数
			canvas.drawArc(oval, -90, 360 * currentProgress / myMax, false, roundProgressPaint);
			break;
		case FILL:
			roundProgressPaint.setStyle(Style.FILL_AND_STROKE);
			if (currentProgress != 0) {
				canvas.drawArc(oval, -90, 360 * currentProgress / myMax, true, roundProgressPaint);
			}
			break;
		}
	}

	public synchronized void setProgress(int currentProgress) {
		if (currentProgress < 0) {
			return;
		}
		if (currentProgress > myMax) {
			currentProgress = myMax;
		}

		if (currentProgress <= myMax) {
			this.currentProgress = currentProgress;
			postInvalidate();
		}
	}

	public void startProgress(final int currentProgress1) {
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// while (currentProgress <= currentProgress1) {
		// // System.out.println("currentProgress=" + currentProgress);
		// setProgress(currentProgress);
		// currentProgress++;
		// try {
		// Thread.sleep(50);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// }).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				int sleepTime = 10;
				int slowDown = currentProgress1 / 4 * 3;
				int curPos = 0;
				while (curPos <= currentProgress1) {
					if (curPos > slowDown) {
						sleepTime++;
					}
					curPos++;
					setProgress(curPos);
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
