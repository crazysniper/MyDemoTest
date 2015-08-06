package com.gftest.myappclient.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.gftest.myappclient.R;
import com.gftest.myappclient.utils.Utils;

public class DayProgress extends View {

	/** 圆Paint */
	private Paint cirlePaint;
	/** 圆弧Paint */
	private Paint arcPaint;
	/** 文字Paint */
	private Paint textPaint;

	private RectF rectF;
	private int x, y;

	/** 圆的半径 */
	private int radius = 50;
	/** 圆弧的颜色 */
	int arc_bg = Color.WHITE;
	/** 圆的背景颜色 */
	int circle_bg = Color.BLACK;

	private float startAngle = 0;
	private float sweepAngle = 0;
	private float textSize = 33;
	private int textcolor;
	private String textmsg = "+2";

	private int max = 0;
	private int progress = 0;

	/** 0-pi，长度=101 */
	private final static double[] PERCENT_TO_ARC = { 0, 0.364413d, 0.4616d, 0.530831d, 0.586699d, 0.634474d, 0.676734d, 0.714958d, 0.750081d, 0.782736d, 0.813377d, 0.842337d, 0.869872d, 0.896184d, 0.921432d, 0.945747d, 0.969237d, 0.991993d, 1.01409d, 1.0356d, 1.05657d, 1.07706d, 1.0971d, 1.11674d, 1.13601d, 1.15494d, 1.17356d, 1.19189d, 1.20996d, 1.22779d, 1.24539d, 1.26279d, 1.27999d, 1.29702d, 1.31389d, 1.33061d, 1.3472d, 1.36366d, 1.38d, 1.39625d, 1.4124d, 1.42847d, 1.44446d, 1.46039d, 1.47627d, 1.49209d, 1.50788d, 1.52364d, 1.53937d, 1.55509d, 0.5 * Math.PI, 1.58651d, 1.60222d, 1.61796d, 1.63371d, 1.6495d, 1.66533d, 1.6812d, 1.69713d, 1.71313d, 1.72919d, 1.74535d, 1.76159d, 1.77794d, 1.7944d, 1.81098d, 1.8277d, 1.84457d, 1.8616d, 1.8788d, 1.8962d, 1.9138d, 1.93163d, 1.9497d, 1.96803d,
			1.98665d, 2.00558d, 2.02485d, 2.04449d, 2.06454d, 2.08502d, 2.10599d, 2.1275d, 2.1496d, 2.17236d, 2.19585d, 2.22016d, 2.24541d, 2.27172d, 2.29926d, 2.32822d, 2.35886d, 2.39151d, 2.42663d, 2.46486d, 2.50712d, 2.55489d, 2.61076d, 2.67999d, 2.77718d, Math.PI };

	/** 0-180度的角度，长度=101 */
	private final static double[] PERCENT_TO_ANGLE = { 0.0, 20.87932689970087, 26.447731823238804, 30.414375934709003, 33.61537654454588, 36.352682410783395, 38.77400205300625, 40.964075929114315, 42.9764755929523, 44.847469272952004, 46.60306925301236, 48.26235502771122, 49.83999431660394, 51.34756086715217, 52.794164708298474, 54.18731158715907, 55.53318944792137, 56.837012206521074, 58.103077046421646, 59.33550926374806, 60.53700176013739, 61.710992282360436, 62.85919970380261, 63.984488813439555, 65.08857848465665, 66.1731875908393, 67.24003500537289, 68.29026664384769, 69.32560137964909, 70.34718512836734, 71.35559084779759, 72.35253741132523, 73.33802481895025, 74.31377194405803, 75.28035174444373, 76.23833717790248, 77.1888741600245, 78.13196269080984, 79.0681757280536,
			79.99923214514119, 80.92455898427748, 81.8453021610527, 82.7614616754669, 83.6741834431103, 84.58404042177803, 85.49045965367499, 86.39516001218658, 87.29814149731276, 88.19940410905353, 89.1000937629992, 90.0, 90.90032715530023, 91.80044385145077, 92.70227942098667, 93.60468794831772, 94.50938830682928, 95.41638049652137, 96.325664517394, 97.23838628503741, 98.15511875724673, 99.07528897622683, 100.00118877315823, 100.9316722324507, 101.86845822748958, 102.81154675827493, 103.76151078260183, 104.71949621606056, 105.68607601644626, 106.66182314155404, 107.64731054917908, 108.64425711270671, 109.65266283213694, 110.67424658085521, 111.70958131665661, 112.75981295513141, 113.82666036966499, 114.91126947584766, 116.01535914706473, 117.1406482567017, 118.28942863593899,
			119.46284620036691, 120.66433869675623, 121.89677091408264, 123.16300764132176, 124.4670595830395, 125.81293744380183, 127.20579784376484, 128.65251627647018, 130.1599682354594, 131.73789400324964, 133.3971797779485, 135.15272246222935, 137.02342966333148, 139.03565743983094, 141.2260750906161, 143.64739473283896, 146.3844141201789, 149.5855293215748, 153.5521161372655, 159.12069294814196, 180.0 };

	public DayProgress(Context context) {
		this(context, null);
	}

	public DayProgress(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DayProgress(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.jifen_qiaodao_circle);
		arc_bg = typedArray.getColor(R.styleable.jifen_qiaodao_circle_arc_bg, Color.parseColor("#fecd19"));
		circle_bg = typedArray.getColor(R.styleable.jifen_qiaodao_circle_circle_bg, Color.parseColor("#eee9e5"));
		startAngle = typedArray.getFloat(R.styleable.jifen_qiaodao_circle_startAngle, 0.0f);
		sweepAngle = typedArray.getFloat(R.styleable.jifen_qiaodao_circle_sweepAngle, 0.0f);
		textSize = typedArray.getFloat(R.styleable.jifen_qiaodao_circle_textsize, 11);
		textcolor = typedArray.getColor(R.styleable.jifen_qiaodao_circle_textcolor, Color.parseColor("#a9a9a9"));
		textSize = Utils.sp2px(context, textSize);
		textmsg = typedArray.getString(R.styleable.jifen_qiaodao_circle_textmsg);
		if (TextUtils.isEmpty(textmsg)) {
			textmsg = "+2";
		}
		typedArray.recycle();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		initView();
	}

	private void initView() {
		setMax(36);
		cirlePaint = new Paint();
		cirlePaint.setAntiAlias(true);
		cirlePaint.setStyle(Style.FILL);
		cirlePaint.setColor(circle_bg);
		int width = getWidth();// 在xml中 里面设置android:layout_width，这个获取的是这里面的值
		int height = getHeight();// 在xml中 里面设置android:layout_height，这个获取的是这里面的值
		x = width / 2;
		y = height / 2;
		radius = Math.min(x, y);

		arcPaint = new Paint();
		arcPaint.setAntiAlias(true);
		arcPaint.setStyle(Style.FILL);
		arcPaint.setColor(arc_bg);
		rectF = new RectF();
		rectF.left = x - radius;
		rectF.bottom = y + radius;
		rectF.top = y - radius;
		rectF.right = x + radius;

		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setTextSize(textSize);// 单位是px
		textPaint.setColor(textcolor);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawCircle(x, y, radius, cirlePaint);

		/**
		 * 
		 * http://blog.sina.com.cn/s/blog_783ede0301012im3.html<br>
		 * oval :指定圆弧的外轮廓矩形区域。 <br>
		 * startAngle: 圆弧起始角度，单位为度。 <br>
		 * sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度。 <br>
		 * useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。<br>
		 * paint: 绘制圆弧的画板属性，如颜色，是否填充等。<br>
		 */
		// canvas.drawArc(rectF, startAngle, sweepAngle, true, arcPaint);

		int percent = getProgress() * 100 / getMax();
		float startAngle;
		/**
		 * when mFillMode = rising_water,need draw triangle
		 */
		Path path = new Path();
		if (percent < 50) {
			startAngle = (float) (180 - PERCENT_TO_ANGLE[percent]);
			canvas.drawArc(rectF, startAngle, (float) (PERCENT_TO_ANGLE[percent] * 2), true, arcPaint);

			float rCos = (float) (radius * Math.cos(PERCENT_TO_ARC[percent]));
			float rSin = (float) (radius * Math.sin(PERCENT_TO_ARC[percent]));
			path.moveTo(x, y);// triangle start 绘制的绘制起点
			path.lineTo(x - rCos, y - rSin); // 该方法实现的仅仅是两点连成一线的绘制线路，参数是下一个点的坐标
			path.lineTo(x - rCos, y + rSin);
			path.lineTo(x, y);
			path.close(); // siege of triangle
			canvas.drawPath(path, cirlePaint);
			// cirlePaint.setStyle(Paint.Style.STROKE);
			// cirlePaint.setStrokeWidth(1.0f);
			// canvas.drawPath(path, cirlePaint);
			textcolor = Color.parseColor("#a9a9a9");
		} else {
			textcolor = Color.WHITE;
			startAngle = (float) (180 - PERCENT_TO_ANGLE[percent]);
			canvas.drawArc(rectF, startAngle, (float) (PERCENT_TO_ANGLE[percent] * 2), true, arcPaint);

			float rSin = (float) (radius * Math.sin((PERCENT_TO_ARC[percent] - Math.PI / 2)));
			float rCos = (float) (radius * Math.cos((PERCENT_TO_ARC[percent] - Math.PI / 2)));
			path.moveTo(x, y);// triangle start绘制的绘制起点
			path.lineTo(x + rSin, y + rCos);
			path.lineTo(x + rSin, y - rCos);
			path.lineTo(x, y);
			path.close(); // siege of triangle
			canvas.drawPath(path, arcPaint);

			// arcPaint.setStyle(Paint.Style.STROKE);
			// arcPaint.setStrokeWidth(1.0f);
			// canvas.drawPath(path, arcPaint);
		}

		textPaint.setColor(textcolor);
		int textWidth = (int) textPaint.measureText(textmsg);
		canvas.drawText(textmsg, x - textWidth / 2, y + 5, textPaint);
	}

	public void changeArc() {
		setProgress(0);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (getProgress() < getMax()) {
						Thread.sleep(30);
						int progress = getProgress() + 2;
						setProgress(progress > getMax() ? getMax() : progress);
						postInvalidate();
					}
					if(dayProgressListener!=null){
						dayProgressListener.finished();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void back(){
		setProgress(0);
		new Thread(new Runnable() {
			@Override
			public void run() {
				postInvalidate();
			}
		}).start();
	}
	
	
	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	private DayProgressListener dayProgressListener;

	public interface DayProgressListener {
		public void finished();
	}

	public void setDayProgressListener(DayProgressListener dayProgressListener) {
		this.dayProgressListener = dayProgressListener;
	}
}
