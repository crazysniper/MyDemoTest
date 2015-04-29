package com.example.viewpagerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

public class JavaRelativeLayout extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(this.addRelativeLayout());
	}

	private RelativeLayout addRelativeLayout() {
		RelativeLayout container = new RelativeLayout(this);
		Button btn1 = new Button(this);
		btn1.setId(11);
		btn1.setText("上");
		Button btn2 = new Button(this);
		btn2.setId(12);
		btn2.setText("下");
		Button btn3 = new Button(this);
		btn3.setId(13);
		btn3.setText("左");
		Button btn4 = new Button(this);
		btn4.setId(14);
		btn4.setText("右");
		Button btn5 = new Button(this);
		btn5.setId(15);
		btn5.setText("中");

		Button btn6 = new Button(this);
		btn6.setText("顶部");
		btn6.setId(21);

		Button btn7 = new Button(this);
		btn7.setText("左边");
		btn7.setId(22);

		Button btn8 = new Button(this);
		btn8.setText("右边");
		btn8.setId(23);

		Button btn9 = new Button(this);
		btn9.setText("底部");
		btn9.setId(24);

		RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(100, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(lp1);
		RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(lp1);
		RelativeLayout.LayoutParams lp4 = new RelativeLayout.LayoutParams(lp1);
		RelativeLayout.LayoutParams lp5 = new RelativeLayout.LayoutParams(lp1);
		RelativeLayout.LayoutParams lp6 = new RelativeLayout.LayoutParams(lp1);
		RelativeLayout.LayoutParams lp7 = new RelativeLayout.LayoutParams(lp1);
		RelativeLayout.LayoutParams lp8 = new RelativeLayout.LayoutParams(lp1);
		RelativeLayout.LayoutParams lp9 = new RelativeLayout.LayoutParams(lp1);
		lp5.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);// 中

		lp6.addRule(RelativeLayout.ALIGN_PARENT_TOP);// 顶部
		lp6.addRule(RelativeLayout.CENTER_HORIZONTAL);

		lp7.addRule(RelativeLayout.ALIGN_PARENT_LEFT);// 左边
		lp7.addRule(RelativeLayout.CENTER_VERTICAL);

		lp8.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);// 右边
		lp8.addRule(RelativeLayout.CENTER_VERTICAL);

		lp9.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);// 底部
		lp9.addRule(RelativeLayout.CENTER_HORIZONTAL);

		lp1.addRule(RelativeLayout.ABOVE, btn5.getId());// 上
		lp1.addRule(RelativeLayout.ALIGN_LEFT, btn5.getId());
		lp2.addRule(RelativeLayout.BELOW, btn5.getId());// 下
		lp2.addRule(RelativeLayout.ALIGN_LEFT, btn5.getId());
		lp3.addRule(RelativeLayout.LEFT_OF, btn5.getId());// 左
		lp3.addRule(RelativeLayout.ALIGN_BASELINE, btn5.getId());
		lp4.addRule(RelativeLayout.RIGHT_OF, btn5.getId());// 右
		lp4.addRule(RelativeLayout.ALIGN_TOP, btn5.getId());

		container.addView(btn5, lp5);
		container.addView(btn1, lp1);
		container.addView(btn2, lp2);
		container.addView(btn3, lp3);
		container.addView(btn4, lp4);
		container.addView(btn6, lp6);
		container.addView(btn7, lp7);
		container.addView(btn8, lp8);
		container.addView(btn9, lp9);

		return container;
	}
}
