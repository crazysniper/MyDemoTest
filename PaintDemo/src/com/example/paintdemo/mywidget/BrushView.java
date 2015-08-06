package com.example.paintdemo.mywidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * http://www.cnblogs.com/-OYK/archive/2011/10/25/2223624.html
 * 
 * @author Administrator
 * 
 */
public class BrushView extends View {

	/**
	 * 在这个应用中神奇的部分是android.graphics.Paint类。该类有我们所需要的方法来在canvas上画图。我们也需要android.
	 * graphics.Path类对象。按照Android文档，该类负责封装复合的几何路径，可以由直线线段、二次曲线、三次曲线组成。
	 * 听起来很复杂却的确用在了我们的应用里，它只会告诉我们用户触碰屏幕所绘制的路径。
	 */
	private Paint brush = new Paint();
	private Path path = new Path();
	public Button btnButton;
	public Button backButton;
	public LinearLayout.LayoutParams params;
	public LinearLayout.LayoutParams params2;
	private int w;
	private int h;
	private int marginBottom;

	public BrushView(Context context) {
		super(context);
		brush.setAntiAlias(true);// 设置画笔的锯齿效果
		brush.setColor(Color.RED);// 设置画笔颜色
		// 或者
		brush.setColor(Color.rgb(255, 0, 0));

		brush.setStyle(Paint.Style.STROKE);// 设置画笔风格，空心或者实心 STROKE：空心 FILL：实心
		brush.setStrokeJoin(Paint.Join.ROUND);// 设置绘制时各图形的结合方式，如平滑效果等
		brush.setStrokeWidth(10f);// 设置空心的边框宽度

		btnButton = new Button(context);
		btnButton.setText("清除");
		btnButton.setTextColor(Color.BLACK);
		params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		btnButton.setLayoutParams(params);
		btnButton.setGravity(Gravity.RIGHT);

		backButton = new Button(context);
		backButton.setText("撤销");
		backButton.setTextColor(Color.BLUE);
		params2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		backButton.setLayoutParams(params2);
		backButton.setGravity(Gravity.LEFT);

		btnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				path.reset();
				postInvalidate();// 更新画面
			}
		});
	}

	/**
	 * 一个Path对象只封装一个路径。我们需要给路径增加点，用来描绘用户触碰屏幕和拖动他的手指。所以我们需要处理View的一些事件，
	 * 它们能告诉我们用户正触摸的区域。这个事件称为onTouchEvent，我们需要增加一个方法来显示得处理它。这儿是让它启用的代码。
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float pointX = event.getX();// x坐标
		float pointY = event.getY();// y坐标
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			System.out.println("按下");
			System.out.println("x坐标：" + pointX);
			System.out.println("y坐标：" + pointY);
			path.moveTo(pointX, pointY);
			return true;
			// break;
		case MotionEvent.ACTION_MOVE:
			System.out.println("移动");
			System.out.println("x坐标：" + pointX);
			System.out.println("y坐标：" + pointY);
			path.lineTo(pointX, pointY);
			break;
		case MotionEvent.ACTION_UP:
			System.out.println("抬起");
			System.out.println("x坐标：" + pointX);
			System.out.println("y坐标：" + pointY);
			break;
		default:
			break;
		}
		postInvalidate();
		return false;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		this.w = w;
		this.h = h;
		super.onSizeChanged(w, h, oldw, oldh);
	}

	/**
	 * 实际进行绘画了。这个工作将由 onDraw 方法完成
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);

		System.out.println("距离底部：" + marginBottom);
		Rect rect = new Rect(0, 0, w, h - marginBottom);
		canvas.drawRect(rect, paint);
		canvas.drawPath(path, brush);
	}

	public BrushView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

}
