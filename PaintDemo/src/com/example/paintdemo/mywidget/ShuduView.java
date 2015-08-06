package com.example.paintdemo.mywidget;

import com.example.paintdemo.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.view.View;

/**
 * 九宫格的绘制 http://blog.csdn.net/hzc543806053/article/details/7675126
 * 
 * @author Administrator
 * 
 */
public class ShuduView extends View {

	/** 单元格的宽度 */
	private float witdth;
	/** 单元格的高度 */
	private float height;

	private Game game = new Game();

	private Context context;

	private String[] color;

	public ShuduView(Context context) {
		super(context);
		this.context = context;
		color = this.context.getResources().getStringArray(R.array.shudu_color);
	}

	/**
	 * 涉及到手机屏幕的切换问题比如 横屏 和竖屏，需要进行调整 w:整个 veiw 的宽度; h:整个 veiw 的高度
	 * 
	 * 
	 * 把整个屏幕分成九宫格的每个格子的宽度和高度
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		this.witdth = w / 9f;
		this.height = h / 9f;
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint backgroundPaint = new Paint();
		Paint hilitePaint = new Paint();
		Paint lightPaint = new Paint();
		Paint darkPaint = new Paint();

		backgroundPaint.setColor(Color.parseColor(color[0]));
		hilitePaint.setColor(Color.parseColor(color[1]));
		lightPaint.setColor(Color.parseColor(color[2]));
		darkPaint.setColor(Color.parseColor(color[3]));

		// 绘出整个屏幕的背景色
		canvas.drawRect(0, 0, this.witdth, this.height, backgroundPaint);

		// 画九宫格里面的 横线，纵线,每次画出的线要想达到某种效果，需画两条 之间格1像素的位置，且颜色也要搭配好
		for (int i = 0; i < 9; i++) {
			canvas.drawLine(0, i * height, this.getWidth(), i * height, lightPaint);
			canvas.drawLine(0, i * height + 1, this.getWidth(), i * height + 1, hilitePaint);
			canvas.drawLine(i * witdth, 0, i * witdth, this.getHeight(), lightPaint);
			canvas.drawLine(i * witdth + 1, 0, i * witdth + 1, this.getHeight(), hilitePaint);
		}
		// 把整个 屏幕的格子 分成9个大的 9 宫格，每个大的9宫格 里面又有9个小格， 实际上就是 用颜色比较深的线隔开
		for (int i = 0; i < 9; i++) {
			if (i % 3 != 0) {
				continue;
			}
			canvas.drawLine(0, i * height, this.getWidth(), i * height, darkPaint);
			canvas.drawLine(0, i * height + 1, this.getWidth(), i * height + 1, hilitePaint);
			canvas.drawLine(i * witdth, 0, i * witdth, this.getHeight(), darkPaint);
			canvas.drawLine(i * witdth + 1, 0, i * witdth + 1, this.getHeight(), hilitePaint);
		}

		// 设置在表格上显示的数字
		Paint numberPaint = new Paint();
		numberPaint.setColor(Color.RED);
		numberPaint.setStyle(Paint.Style.STROKE);// 让其画出来的东西是空的
		numberPaint.setTextSize(height * 0.75f);// 设置字体大小
		numberPaint.setTextAlign(Paint.Align.CENTER);// 让字体居中

		float x = witdth / 2f;
		// 调整字体的位置（度量） 比如居中，调整垂直方向上的居中
		FontMetrics fm = numberPaint.getFontMetrics();
		// 这些fm.ascent都是基于基准线而言
		float y = height / 2f - (fm.ascent + fm.descent) / 2;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				canvas.drawText(game.getTitleString(i, j), i * witdth + x, j * height + y, numberPaint);
			}
		}
		super.onDraw(canvas);
	}
}
