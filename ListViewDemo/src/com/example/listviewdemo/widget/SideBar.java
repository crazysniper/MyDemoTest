package com.example.listviewdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class SideBar extends View {

	private String[] nameHead = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#" };
	private int choose = -1;
	private Paint paint = new Paint();
	private TextView myText;
	private int length = nameHead.length;

	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

	public SideBar(Context context) {
		super(context);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setMyText(TextView myText) {
		this.myText = myText;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获得焦点改变颜色
		int width = getWidth();// 获取对应宽度
		int height = getHeight();// 获得对应高度

		int singleHeight = height / length;// 获取每个字母的高度

		for (int i = 0; i < length; i++) {
			paint.setColor(Color.parseColor("#414141"));
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(20);
			// 选中的状态
			if (i == choose) {
				paint.setColor(Color.parseColor("#3399ff"));
				paint.setFakeBoldText(true);
			}
			// x坐标等于中间-字符串宽度的一半.
			float xPos = width / 2 - paint.measureText(nameHead[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(nameHead[i], xPos, yPos, paint);
			paint.reset();// 重置画笔
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();// 点击y坐标
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		// 点击y坐标所占总高度的比例*nameHead数组的长度就等于点击nameHead中的个数
		final int c = (int) (y / getHeight() * length);
		switch (action) {
		case MotionEvent.ACTION_UP:
			setBackgroundDrawable(new ColorDrawable(0x000000000));
			choose = -1;
			invalidate();
			if (myText != null) {
				myText.setVisibility(View.INVISIBLE);
			}
			break;
		default:
//			setBackgroundResource(R.drawable.sidebar_background);
			if (oldChoose != c) {
				if (c >= 0 && c < length) {
					listener.onTouchingLetterChanged(nameHead[c]);
				}
				if (myText != null) {
					myText.setText(nameHead[c]);
					myText.setVisibility(View.VISIBLE);
				}
				choose = c;
				invalidate();
			}
			break;
		}
		return true;
		// return super.dispatchTouchEvent(event);
	}

	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;

	}
}
