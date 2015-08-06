package com.gftest.myappclient.wangyi;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.gftest.myappclient.R;

/**
 * http://blog.csdn.net/qibin0506/article/details/42046559
 * 
 * @author Gao
 * 
 */
public class Indicator extends LinearLayout {
	private Paint mPaint; // 画指示符的paint

	private int mTop; // 指示符的top
	private int mLeft; // 指示符的left
	private int mWidth; // 指示符的width
	private int mHeight = 5; // 指示符的高度，固定了
	private int mColor; // 指示符的颜色
	private int mChildCount; // 子item的个数，用于计算指示符的宽度

	public Indicator(Context context) {
		this(context, null);
	}

	public Indicator(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public Indicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// 因为ViewGroup默认是不走onDraw方法的，因为ViewGroup是不需要绘制的，需要绘制的是ViewGroup的子item，这里我们设置一下背景颜色，ViewGroup就会走onDraw方法去绘制它自己的背景，那么我们需要onDraw吗？
		// 当然需要，我们要在onDraw中绘制指示符。
		setBackgroundColor(Color.TRANSPARENT); // 必须设置背景，否则onDraw不执行

		// 获取自定义属性 指示符的颜色
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Indicator, 0, 0);
		mColor = ta.getColor(R.styleable.Indicator_color, 0X0000FF);
		ta.recycle();

		// 初始化paint
		mPaint = new Paint();
		mPaint.setColor(mColor);
		mPaint.setAntiAlias(true);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mChildCount = getChildCount(); // 获取子item的个数
		// System.out.println("getChildCount=" + getChildCount());
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mTop = getMeasuredHeight(); // 测量的高度即指示符的顶部位置
		int width = getMeasuredWidth(); // 获取测量的总宽度
		int height = mTop + mHeight; // 重新定义一下测量的高度
		mWidth = width / mChildCount; // 指示符的宽度为总宽度/item的个数

		// System.out.println("width=" + width);
		// System.out.println("mTop=" + mTop);
		// System.out.println("height=" + height);
		// System.out.println("mWidth=" + mWidth);
		setMeasuredDimension(width, height);
	}

	/**
	 * 指示符滚动
	 * 
	 * @param position
	 *            现在的位置
	 * @param offset
	 *            偏移量 0 ~ 1
	 */
	public void scroll(int position, float offset) {
		/**
		 * 看看left的值是如何计算的，position和offset相加再乘指示符的宽度，为什么呢？
		 * 想想，position的值是值当前ViewPager显示第几页
		 * ，也就是当前是第几个tab，offset指的是从当前页偏移了百分之几，也就是说偏移量是一个0~1的值，这样(position +
		 * offset) * mWidth的结果也巧好就是我们需要的矩形的left的值。
		 */
		mLeft = (int) ((position + offset) * mWidth);
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// 圈出一个矩形
		Rect rect = new Rect(mLeft, mTop, mLeft + mWidth, mTop + mHeight);
		canvas.drawRect(rect, mPaint); // 绘制该矩形
		super.onDraw(canvas);
	}

	
	
}
