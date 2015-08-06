package com.example.paintdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.example.paintdemo.mywidget.BrushView;

/**
 * http://www.dapps.net/dev/android-dev/building-android-paint-application.html
 * 
 * @author Administrator
 * 
 */
public class PaintActivity1 extends Activity implements OnClickListener {

	private Button btn;
	private BrushView brushView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paint1);
		btn = (Button) findViewById(R.id.btn);

		int height = btn.getHeight();// onCreate()里面获取控件的高度是0.因为当OnCreate函数发生时，只是提供了数据初始化的机会，此时还没有正式绘制图形。
		System.out.println("底部button的高度：" + height);

		ViewTreeObserver vto = btn.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				btn.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				System.out.println("控件高度：" + btn.getHeight());
				System.out.println("控件宽度：" + btn.getWidth());
			}
		});

		brushView = new BrushView(this);
		// setContentView(brushView);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		// layoutParams.gravity = Gravity.BOTTOM;
		addContentView(brushView, layoutParams);
		addContentView(brushView.btnButton, brushView.params);
		// addContentView(brushView.backButton, brushView.params2);

		btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn:
			Toast.makeText(PaintActivity1.this, "点击了根布局中的button", Toast.LENGTH_SHORT).show();
			brushView.postInvalidate();
			break;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
}
