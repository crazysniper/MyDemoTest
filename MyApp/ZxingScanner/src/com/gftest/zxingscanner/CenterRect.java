package com.gftest.zxingscanner;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class CenterRect extends View {

	public CenterRect(Context context) {
		this(context, null);
	}

	public CenterRect(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CenterRect(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

}
