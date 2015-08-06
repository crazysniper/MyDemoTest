package com.example.paintdemo.mywidget;

import com.example.paintdemo.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 1）需要继承 View 这个类，所有的组件都是继承于这个类
 * 
 * 2）并要实现一个带Context参数的构造函数,因为父类中，没有隐式无参的构造函数
 * 
 * 3）需重写父类中的onDraw方法，一切的画图操作将在这进行
 * 
 * 
 * 自定义 view,需要实现 一个显式的构造函数，重写 onDraw 方法，一切的操作都在该方法上进行
 * 
 * @author Administrator
 * 
 */
public class CanvasView extends View {

	private Activity context;

	public CanvasView(Activity context) {
		super(context);
		this.context = context;
	}

	public CanvasView(Context context, AttributeSet attribute) {
		super(context, attribute);
	}

	/**
	 * 
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.RED);// 设置颜色
		paint.setTextSize(100);// 设置字体大小

		paint.setStyle(Paint.Style.STROKE);// 让画出的图形是空心的
		paint.setStrokeWidth(5);// 设置画出的线的粗细程度
		DisplayMetrics displayMetrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(displayMetrics);
		int width = displayMetrics.widthPixels;
		int height = displayMetrics.heightPixels;
		System.out.println("屏幕宽度是：" + width);// 540
		System.out.println("屏幕高度是：" + height);// 960

		Rect rect = new Rect();
		context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);// /取得整个视图部分,注意，如果你要设置标题样式，这个必须出现在标题样式之后，否则会出错
		int top = rect.top;// 状态栏的高度，所以rect.height,rect.width分别是系统的高度的宽度
		View v = context.getWindow().findViewById(Window.ID_ANDROID_CONTENT);// /获得根视图
		int top2 = v.getTop();// 状态栏标题栏的总高度,所以标题栏的高度为top2-top
		int width2 = v.getWidth();// 视图的宽度,这个宽度好像总是最大的那个
		int height2 = v.getHeight();// 视图的高度，不包括状态栏和标题栏
		System.out.println("状态栏的高度是：" + top);// 38
		System.out.println("状态栏标题栏的总高度：" + top2);// 110
		System.out.println("标题栏的高度：" + (top2 - top));// 72
		System.out.println("屏幕宽度是：" + width2);// 540
		System.out.println("屏幕高度是：" + height2);// 850

		// 画出一根线
		canvas.drawLine(0, 0, 200, 200, paint);// 从左上角往右下角斜画，长度200
		// 画矩形
		canvas.drawRect(0, 0, width, height - top2, paint);// 从左上角往右下角斜画，宽度540，高度960
		// 画圆
		canvas.drawCircle(200, 200, 100, paint);// 从左上角往右下角斜，半径100，距离左边和上边各是200
		// 画出字符串 drawText(String text, float x, float y, Paint paint)，y 是 基准线，不是
		// 字符串的 底部
		canvas.drawText("gaofeng", 50, 170, paint);

		// 绘制图片
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher), 240, 240, paint);
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher), 300, 600, paint);
		super.onDraw(canvas);
	}
}
