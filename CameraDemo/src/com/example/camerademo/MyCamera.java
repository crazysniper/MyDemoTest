package com.example.camerademo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.camerademo.mywidget.MyViewPagerAdapter;
import com.example.camerademo.mywidget.VTextView;
import com.example.camerademo.mywidget.VerticalAdapter;
import com.example.camerademo.mywidget.VerticalViewPager;
import com.example.camerademo.utils.ImageUtils;
import com.example.camerademo.utils.Utils;

/**
 */
@SuppressLint("NewApi")
public class MyCamera extends Activity implements Callback, OnClickListener, OnTouchListener {
	private ImageView take_pic, back, album, change_camera;
	private SurfaceView mySurfaceView;
	private SensorManager mSensorManager;
	private Sensor sensor1, sensor2;
	private ProgressDialog progressDialog;
	private SurfaceHolder holder;
	private Camera myCamera;// 相机声明
	private Typeface typeface;

	/** 手机竖屏显示的水印 */
	private ViewPager vPager1;
	/** 手机横屏显示的水印 */
	private VerticalViewPager vPager2;
	/** 手机竖屏显示的水印Adapter */
	private VerticalAdapter adapter2;
	/** 手机横屏显示的水印Adapter */
	private MyViewPagerAdapter adapter1;

	private Bitmap result_bm;

	/**
	 * w:屏幕宽度<br>
	 * h: 640分辨率屏幕高度<br>
	 * h2:屏幕高度 <br>
	 */
	private int w, h, h2;
	/**
	 * cur：当前选中的水印pager下标<br>
	 * click_state: 0：；<br>
	 * 1：；<br>
	 * 2：
	 */
	private int cur = 0, click_state = 0;
	/**
	 * 默认cur_o=1：竖屏 <br>
	 * cur_o = 2:横屏
	 */
	private int cur_o = 1;
	/**
	 * 默认now_camera=1，即默认打开后置摄像头 <br>
	 * 0：前置；1：后置
	 */
	private int now_camera = 1;
	private String filePath = "/sdcard/my";
	boolean isClicked = false;// 是否点击标识
	float aa0, aa1, aa2;
	int count = 0;
	boolean should_focus = true;

	/** 所有文件名 */
	public String[] allFiles;
	/** 默认版本号8 */
	private static final int DEFAULT_VERSION_VALUE = 8;
	/** 当前系统版本号 */
	@SuppressWarnings("deprecation")
	private static final String VERSION = android.os.Build.VERSION.SDK;

	/** 手机竖屏显示的水印的三个View */
	private ArrayList<View> viewList1 = new ArrayList<View>();
	/** 手机横屏显示的水印的三个View */
	private ArrayList<View> viewList2 = new ArrayList<View>();
	/** 三个水印（每个水印3个文案和一条白线，HashMap设置属性） */
	private ArrayList<ArrayList<HashMap<String, Object>>> add_img_list = new ArrayList<ArrayList<HashMap<String, Object>>>();
	/** 音效 */
	private Map<Integer, Integer> spMap;

	private Size size = null;
	/** 区分从心情日记跳转过来的 */
	private String type = "";
	private Uri uri;
	/** 4个 惨，逗，糗，美 */
	private int[] imgs = { R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher };
	/** 4个文案 */
	private String[] strs = { "1111111111", "2222222222", "333333333333", "444444444444" };
	private String city = "";

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensor1 = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);// 方位感应检测
		sensor2 = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);// 加速度感应检测
		mSensorManager.registerListener(mSensorEventListener, sensor1, SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(mSensorEventListener, sensor2, SensorManager.SENSOR_DELAY_NORMAL);

		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 无标题

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏

		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

		w = displayMetrics.widthPixels;// 屏幕宽度
		h = (int) (w * (640.0 / 480.0));// 屏幕高度
		h2 = displayMetrics.heightPixels;// 640分辨率屏幕高度

		if ((w + 0.0f) / h2 < (320.0f / 480.0f + 0.01) && (w + 0.0f) / h2 > (320.0f / 480.0f - 0.01)) {
			// 分辨率是320*480的时候
			setContentView(R.layout.activity_my_camera2);
		} else {
			setContentView(R.layout.activity_my_camera);
		}
		initView();
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		back = (ImageView) findViewById(R.id.close);// 返回
		take_pic = (ImageView) findViewById(R.id.take_pic);// 拍照
		album = (ImageView) findViewById(R.id.album);// 左下方的相册预览
		vPager1 = (ViewPager) findViewById(R.id.vPager1);
		vPager2 = (VerticalViewPager) findViewById(R.id.vPager2);
		change_camera = (ImageView) findViewById(R.id.change_camera);// 前后摄像头切换
		if (Utils.toInt(VERSION, 8) <= 8) {// 低于版本8，不显示前后摄像头切换的图标
			change_camera.setVisibility(View.GONE);
		}
		mySurfaceView = (SurfaceView) findViewById(R.id.surfaceView1);

		holder = mySurfaceView.getHolder();
		holder.addCallback(this);

		city = Utils.readSharedPreferences(MyCamera.this, "city");

		Intent intent = getIntent();
		if (intent != null && intent.getExtras() != null) {
			type = intent.getExtras().getString("type");// 表示从亲子相机过来的，而不是从发现过来的
		}

		// 设置该SurfaceView自己不维护缓冲,等待屏幕渲染引擎将内容推送到用户面前
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		mySurfaceView.setOnClickListener(this);
		take_pic.setOnClickListener(this);
		back.setOnClickListener(this);
		album.setOnClickListener(this);
		change_camera.setOnClickListener(this);

		vPager1.setOnTouchListener(this);
		vPager2.setOnTouchListener(this);

		setSmall();// 设置最新一张相片作为左下角的缩略图

		typeface = Typeface.DEFAULT;// 常规字体类型

		setData();// 设置水印文案内容，位置参数
		setAddImage();// 添加水印内容至ui上
		initViewPager();// 载入文案ViewPager
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.close:// 后退
			if (click_state == 0) {
				onBackPressed();
			} else if (click_state == 1) {
				take_pic.setImageResource(R.drawable.camera_take_l);
				mySurfaceView.setVisibility(View.VISIBLE);
				if (cur_o == 1) {// 竖屏
					vPager1.setVisibility(View.VISIBLE);
					vPager2.setVisibility(View.GONE);
				} else {// 横屏
					vPager2.setVisibility(View.VISIBLE);
					vPager1.setVisibility(View.GONE);
				}
				vPager1.setCurrentItem(cur);
				vPager2.setCurrentItem(cur);

				myCamera.startPreview();
				click_state = 0;
			}
			break;
		case R.id.album:// 查询亲子相机的相册
			break;
		case R.id.change_camera:// 切换前后镜头
			changeCamera();
			break;
		case R.id.surfaceView1:
			if (!isClicked) {
				try {
					myCamera.autoFocus(mAutoFocusCallBack); // 自动对焦后拍照
				} catch (Exception e) {
					e.printStackTrace();
				}
				isClicked = true;
			} else {
				myCamera.startPreview();// 开启预览
				isClicked = false;
			}
			break;
		case R.id.take_pic:// 拍照
			if (myCamera == null)
				return;
			if (click_state == 0) {
				click_state = 2;
				new Thread(new Runnable() {
					@Override
					public void run() {
					}
				}).start();
				progressDialog = new ProgressDialog(MyCamera.this);
				progressDialog.setMessage("处理中...");
				progressDialog.setCancelable(false);
				try {
					progressDialog.show();
					myCamera.takePicture(null, null, jpeg);
				} catch (Exception e) {
				}
			}
			break;
		}
	}

	/**
	 * 设置最新一张相片作为左下角的缩略图
	 */
	private void setSmall() {
		try {
			File folder = new File(filePath + "/");
			allFiles = folder.list();// 所有文件名
			if (allFiles.length == 0) {// 没有图片时，设置为空
				album.setImageBitmap(null);
			} else {// 有图片时，显示最后一张图片
				allFiles = ImageUtils.sort(allFiles);
				BitmapFactory.Options opts = new BitmapFactory.Options();
				opts.inTempStorage = new byte[100 * 1024];
				opts.inPreferredConfig = Bitmap.Config.RGB_565;
				opts.inPurgeable = true;
				opts.inSampleSize = 4;
				opts.inInputShareable = true;
				Bitmap bitmap = BitmapFactory.decodeFile(filePath + "/" + allFiles[allFiles.length - 1], opts);
				album.setImageBitmap(bitmap);
			}
		} catch (Exception e) {
			album.setImageBitmap(null);
		}
	}

	/**
	 * 设置水印文案内容，位置参数
	 */
	@SuppressLint("SimpleDateFormat")
	private void setData() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());
		String date = formatter.format(curDate);
		String camera_str = Utils.readSharedPreferences(MyCamera.this, "camera_str");// 距离出生9天-孕38周5天-
		String[] camera_strs = camera_str.split("-");// [距离出生9天,孕38周5天]

		ArrayList<HashMap<String, Object>> item;
		HashMap<String, Object> map;
		// width,height,x,y是int
		// size,x2,y2是float

		// 第一个水印
		item = new ArrayList<HashMap<String, Object>>();
		map = new HashMap<String, Object>();
		int water_height1 = Utils.dip2px(MyCamera.this, 137);
		map.put("img", R.drawable.ic_launcher);
		map.put("width", Utils.dip2px(MyCamera.this, 71));// 图片宽度
		map.put("height", water_height1);// 图片高度
		map.put("x", Utils.dip2px(MyCamera.this, 10));// 手机竖屏时,距离屏幕左侧距离
		map.put("y", (h - Utils.dip2px(MyCamera.this, 10) - water_height1));// 手机竖屏时,距离屏幕顶部距离
		// 在画面上：横屏时，距离屏幕左侧距离
		map.put("x2", Utils.dip2px(MyCamera.this, 10));// 横屏时，距离横屏的屏幕左侧距离
		map.put("y2", Utils.dip2px(MyCamera.this, 10));// 横屏时，距离横屏的屏幕底部距离
		item.add(map);

		map = new HashMap<String, Object>();
		int water_height2 = Utils.dip2px(MyCamera.this, 24);
		map.put("img", R.drawable.ic_launcher);
		map.put("width", Utils.dip2px(MyCamera.this, 24));// 图片宽度
		map.put("height", water_height2);// 图片高度
		map.put("x", Utils.dip2px(MyCamera.this, 67));// 手机竖屏时,距离屏幕左侧距离
		map.put("y", (h - Utils.dip2px(MyCamera.this, 23) - water_height2));// 手机竖屏时,距离屏幕顶部距离
		// 在画面上：横屏时，距离屏幕左侧距离
		map.put("x2", Utils.dip2px(MyCamera.this, 67));// 横屏时，距离横屏的屏幕左侧距离
		map.put("y2", Utils.dip2px(MyCamera.this, 23));// 横屏时，距离横屏的屏幕底部距离
		item.add(map);

		map = new HashMap<String, Object>();
		map.put("str", city + " " + date);
		map.put("size", Utils.dip2px(MyCamera.this, 17) * 1.0f);// 文字大小
		map.put("x", Utils.dip2px(MyCamera.this, 92));// 手机竖屏时,距离屏幕左侧距离
		map.put("y", (h - Utils.dip2px(MyCamera.this, 14) - Utils.sp2px(MyCamera.this, 17)));// 手机竖屏时,屏幕顶部距离
		// 横屏时，距离横屏的屏幕左侧距离
		map.put("x2", Utils.dip2px(MyCamera.this, 92) * 1.0f);//
		// 横屏时，距离横屏的屏幕顶部距离 17+14=31
		map.put("y2", (w - Utils.dip2px(MyCamera.this, 31)) * 1.0f);
		item.add(map);

		map = new HashMap<String, Object>();
		map.put("str", camera_strs[0]);
		map.put("size", Utils.dip2px(MyCamera.this, 17) * 1.0f);// 文字大小
		map.put("x", Utils.dip2px(MyCamera.this, 65));// 手机竖屏时,距离屏幕左侧距离
		map.put("y", (h - Utils.dip2px(MyCamera.this, 38) - Utils.sp2px(MyCamera.this, 17)));// 手机竖屏时,屏幕顶部距离
		// 横屏时，距离横屏的屏幕左侧距离
		map.put("x2", Utils.dip2px(MyCamera.this, 65) * 1.0f);//
		// 横屏时，距离横屏的屏幕顶部距离 17+38=55
		map.put("y2", (w - Utils.dip2px(MyCamera.this, 55)) * 1.0f);
		item.add(map);
		add_img_list.add(item);

		// 添加第二个水印
		item = new ArrayList<HashMap<String, Object>>();
		map = new HashMap<String, Object>();
		int water_height5 = Utils.dip2px(MyCamera.this, 32);
		map.put("img", R.drawable.ic_launcher);
		map.put("width", Utils.dip2px(MyCamera.this, 105));// 图片宽度
		map.put("height", water_height5);// 图片高度
		map.put("x", Utils.dip2px(MyCamera.this, 18));// 手机竖屏时,距离屏幕左侧距离
		map.put("y", (h - Utils.dip2px(MyCamera.this, 45) - water_height5));// 手机竖屏时,距离屏幕顶部距离
		// 在画面上：横屏时，距离屏幕左侧距离
		map.put("x2", Utils.dip2px(MyCamera.this, 18));// 横屏时，距离横屏的屏幕左侧距离
		map.put("y2", Utils.dip2px(MyCamera.this, 45));// 横屏时，距离横屏的屏幕底部距离
		item.add(map);

		map = new HashMap<String, Object>();
		int water_height6 = Utils.dip2px(MyCamera.this, 24);
		map.put("img", R.drawable.ic_launcher);
		map.put("width", Utils.dip2px(MyCamera.this, 24));// 图片宽度
		map.put("height", water_height6);// 图片高度
		map.put("x", Utils.dip2px(MyCamera.this, 23));// 手机竖屏时,距离屏幕左侧距离
		map.put("y", (h - Utils.dip2px(MyCamera.this, 18) - water_height6));// 手机竖屏时,距离屏幕顶部距离
		// 在画面上：横屏时，距离屏幕左侧距离
		map.put("x2", Utils.dip2px(MyCamera.this, 23));// 横屏时，距离横屏的屏幕左侧距离
		map.put("y2", Utils.dip2px(MyCamera.this, 18));// 横屏时，距离横屏的屏幕底部距离
		item.add(map);

		map = new HashMap<String, Object>();
		map.put("str", city + " " + date);
		map.put("size", Utils.dip2px(MyCamera.this, 17) * 1.0f);// 文字大小
		map.put("x", Utils.dip2px(MyCamera.this, 48));// 手机竖屏时,距离屏幕左侧距离
		map.put("y", (h - Utils.dip2px(MyCamera.this, 9) - Utils.sp2px(MyCamera.this, 17)));// 手机竖屏时,屏幕顶部距离
		// 横屏时，距离横屏的屏幕左侧距离
		map.put("x2", Utils.dip2px(MyCamera.this, 48) * 1.0f);//
		// 横屏时，距离横屏的屏幕顶部距离 17+9=26
		map.put("y2", (w - Utils.dip2px(MyCamera.this, 26)) * 1.0f);
		item.add(map);
		add_img_list.add(item);

		for (int i = 0; i < 4; i++) {
			item = new ArrayList<HashMap<String, Object>>();
			int height = Utils.dip2px(MyCamera.this, 89);
			map = new HashMap<String, Object>();
			map.put("img", imgs[i]);
			map.put("width", Utils.dip2px(MyCamera.this, 116));// 图片宽度
			map.put("height", Utils.dip2px(MyCamera.this, 89));// 图片高度
			map.put("x", Utils.dip2px(MyCamera.this, 9));// 手机竖屏时,距离屏幕左侧距离
			map.put("y", (h - Utils.dip2px(MyCamera.this, 9) - height));// 手机竖屏时,距离屏幕顶部距离
			map.put("x2", Utils.dip2px(MyCamera.this, 9));// 横屏时，距离横屏的屏幕左侧距离
			map.put("y2", Utils.dip2px(MyCamera.this, 9));// 横屏时，距离横屏的屏幕底部距离
			item.add(map);

			map = new HashMap<String, Object>();
			map.put("str", strs[i]);
			map.put("size", Utils.dip2px(MyCamera.this, 17) * 1.0f);// 文字大小
			map.put("x", Utils.dip2px(MyCamera.this, 130));
			map.put("y", (h - Utils.dip2px(MyCamera.this, 45) - Utils.sp2px(MyCamera.this, 17)));// 手机竖屏时,屏幕顶部距离
			// 横屏时，距离横屏的屏幕左侧距离
			map.put("x2", Utils.dip2px(MyCamera.this, 130) * 1.0f);
			// 横屏时，距离横屏的屏幕顶部距离
			map.put("y2", (w - Utils.dip2px(MyCamera.this, 62)) * 1.0f);// 45+17=62
			map.put("clickable", "1");
			item.add(map);

			map = new HashMap<String, Object>();
			map.put("str", date + " " + city);
			map.put("size", Utils.dip2px(MyCamera.this, 17) * 1.0f);// 文字大小
			map.put("x", Utils.dip2px(MyCamera.this, 130));// 手机竖屏时,距离屏幕左侧距离
			map.put("y", (h - Utils.dip2px(MyCamera.this, 15) - Utils.sp2px(MyCamera.this, 17)));// 手机竖屏时,屏幕顶部距离
			// 横屏时，距离横屏的屏幕左侧距离
			map.put("x2", Utils.dip2px(MyCamera.this, 130) * 1.0f);//
			// 横屏时，距离横屏的屏幕顶部距离 15+17=32;
			map.put("y2", (w - Utils.dip2px(MyCamera.this, 32)) * 1.0f);
			item.add(map);
			add_img_list.add(item);
		}
	}

	/**
	 * 添加水印内容至ui上
	 */
	public void setAddImage() {
		LayoutInflater lf = LayoutInflater.from(this);
		// 手机竖屏时
		for (int i = 0; i < add_img_list.size(); i++) {// 3个水印
			RelativeLayout view = (RelativeLayout) lf.inflate(R.layout.add_img_item, null);
			ArrayList<HashMap<String, Object>> item = add_img_list.get(i);
			for (int j = 0; j < item.size(); j++) {// 每个水印中3个内容
				HashMap<String, Object> map = item.get(j);
				if (map.get("str") != null) {// 文字
					// 添加内容
					final TextView tv = new TextView(this);
					DisplayMetrics dm = getResources().getDisplayMetrics();
					float value = dm.scaledDensity;// 字体缩放比例
					tv.setTextSize((Float) map.get("size") / value);
					tv.setTextColor(0xffffffff);
					tv.setShadowLayer(3f, 1, 1, 0xcc000000);
					if (Utils.toInt(VERSION, 8) > 8 && Utils.toInt(VERSION, 8) < 19) {
						tv.setTypeface(typeface);
					}
					tv.setText((String) map.get("str"));
					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					lp.setMargins((Integer) map.get("x"), ((Integer) map.get("y") - (int) ((Float) map.get("size") * 0.86)), 0, 0);

					tv.setLayoutParams(lp);
					tv.setTag(i + "-" + j);// 第几个水印的第几个文案
					tv.setSingleLine(true);
					view.addView(tv);

					// 最后一个水印的第一个文案可以点击，编辑
					if (map.get("clickable") != null && !map.get("clickable").equals("")) {
						tv.setMinWidth(200);
						tv.setOnClickListener(new TextView.OnClickListener() {
							@Override
							public void onClick(View arg0) {
								Toast.makeText(MyCamera.this, "最长可以输入11个字符", Toast.LENGTH_SHORT).show();
							}
						});
					}
				} else if (map.get("img") != null && !map.get("img").equals("")) {// 图片
					int img_width = (int) (Integer) map.get("width");
					int img_height = (int) (Integer) map.get("height");
					int marginLeft = (int) (Integer) map.get("x");
					int marginTop = (int) (Integer) map.get("y");
					ImageView imageView = new ImageView(MyCamera.this);
					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					lp.setMargins(marginLeft, marginTop, 0, 0);
					imageView.setLayoutParams(lp);

					// Bitmap bitmap =
					// BitmapFactory.decodeResource(getResources(), (int)
					// ((Integer) (map.get("img"))));
					Bitmap bitmap = ImageUtils.readBitMap(MyCamera.this, (int) ((Integer) (map.get("img"))));
					bitmap = ImageUtils.zoomImage(bitmap, img_width, img_height);// 裁剪新图片，指定宽度高度
					imageView.setImageBitmap(bitmap);
					view.addView(imageView);
				}
			}
			viewList1.add(view);
		}

		// 手机横屏时
		for (int i = 0; i < add_img_list.size(); i++) {
			RelativeLayout view = (RelativeLayout) lf.inflate(R.layout.add_img_item, null);
			ArrayList<HashMap<String, Object>> item = add_img_list.get(i);
			for (int j = 0; j < item.size(); j++) {
				HashMap<String, Object> map = item.get(j);
				if (map.get("str") != null) {// 文字
					final VTextView tv = new VTextView(this);
					DisplayMetrics dm = getResources().getDisplayMetrics();
					float value = dm.scaledDensity;// 字体缩放比例
					tv.setTextSize((Float) map.get("size") / value);
					tv.setTextColor(0xffffffff);
					tv.setShadowLayer(3f, 1, 1, 0xcc000000);
					if (Utils.toInt(VERSION, 8) > 8 && Utils.toInt(VERSION, 8) < 19) {
						tv.setTypeface(typeface);
					}
					tv.setText((String) map.get("str"));
					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					lp.setMargins(w - (int) ((Float) map.get("y2") - 0) - (int) ((Float) map.get("size") * 0.14), (int) ((Float) map.get("x2") - 0), 0, 0);

					tv.setLayoutParams(lp);
					tv.setTag(i + "-" + j);
					tv.setSingleLine(true);
					view.addView(tv);
					if (map.get("clickable") != null && !map.get("clickable").equals("")) {
						tv.setMinWidth(200);
						tv.setOnClickListener(new TextView.OnClickListener() {
							@Override
							public void onClick(View arg0) {
								Toast.makeText(MyCamera.this, "最长可以输入11个字符", Toast.LENGTH_SHORT).show();
							}
						});
					}
				} else if (map.get("img") != null && !map.get("img").equals("")) {// 图片
					ImageView imageView = new ImageView(MyCamera.this);
					int img_width = (int) (Integer) map.get("width");
					int img_height = (int) (Integer) map.get("height");
					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					// 显示文案到图片预览中 （还没有拍照）
					Matrix matrix = new Matrix();
					// Bitmap bitmap =
					// BitmapFactory.decodeResource(getResources(), (int)
					// ((Integer) (map.get("img"))));
					Bitmap bitmap = ImageUtils.readBitMap(MyCamera.this, (int) ((Integer) (map.get("img"))));
					bitmap = ImageUtils.zoomImage(bitmap, img_width, img_height);

					matrix.setRotate(90);// 顺时针旋转90度
					bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
					// 在画面上表示为： 横屏时:图片距离横屏屏幕底部的距离（因为此时横屏屏幕底部就是竖屏屏幕的左侧）
					int marginLeft = (int) (Integer) map.get("y2");
					// 在画面上表示为： 横屏时:图片距离横屏屏幕左侧的距离（因为此时横屏屏幕左侧就是竖屏屏幕的顶部）
					int marginTop = (int) (Integer) map.get("x2");
					lp.setMargins(marginLeft, marginTop, 0, 0);
					imageView.setLayoutParams(lp);
					imageView.setImageBitmap(bitmap);
					view.addView(imageView);
				}
			}
			viewList2.add(view);
		}
	}

	/**
	 * 载入文案ViewPager
	 */
	private void initViewPager() {
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(w, h);
		mySurfaceView.setLayoutParams(lp);
		vPager1.setLayoutParams(lp);
		vPager2.setLayoutParams(lp);

		adapter1 = new MyViewPagerAdapter(viewList1);
		vPager1.setAdapter(adapter1);
		vPager1.setCurrentItem(0);

		vPager1.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			public void onPageScrollStateChanged(int arg0) {
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageSelected(int arg0) {
				cur = arg0;
				showCanEdit(cur);
			}
		});

		adapter2 = new VerticalAdapter(viewList2);
		vPager2.setAdapter(adapter2);
		vPager2.setCurrentItem(0);
		vPager2.setOnPageChangeListener(new VerticalViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int state) {
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageSelected(int position) {
				cur = position;
				showCanEdit(cur);
			}
		});
		vPager1.setVisibility(View.VISIBLE);
		vPager2.setVisibility(View.GONE);
	}

	/**
	 * 创建jpeg图片回调数据对象
	 */
	PictureCallback jpeg = new PictureCallback() {
		@Override
		public void onPictureTaken(final byte[] data, Camera camera) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {// 获得图片
						BitmapFactory.Options opts = new BitmapFactory.Options();
						opts.inTempStorage = new byte[100 * 1024];
						opts.inPreferredConfig = Bitmap.Config.RGB_565;
						opts.inPurgeable = true;
						opts.inSampleSize = 1;
						opts.inInputShareable = true;
						Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
						Bitmap bm2 = null;
						// Log.i("---",""+now_camera);
						if (Utils.toInt(VERSION, 8) > 8) {
							if (now_camera == 1) {
								bm2 = ImageUtils.rotaingImageView(90, bm);// 旋转
							} else {
								bm2 = ImageUtils.rotaingImageView(-90, bm);
								bm2 = symmetry(bm2);
							}
						} else {
							bm2 = ImageUtils.rotaingImageView(90, bm);
						}
						bm.recycle();
						result_bm = drawWater(bm2);// 打水印
						if (cur_o == 2) {// 横屏
							Bitmap m = result_bm;
							result_bm = ImageUtils.rotaingImageView(-90, m);
							m.recycle();
						}

						isFolderExists(filePath);// 判断指定文件夹是否存在
						String str = filePath + "/pic" + System.currentTimeMillis() + ".jpg";
						File file = new File(str);
						BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
						result_bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);// 将图片压缩到流中
						bos.flush();// 输出
						bos.close();// 关闭
						// System.out.println(str);
						Message msg = Message.obtain();
						msg.what = 3;
						msg.obj = str;
						handler.sendMessage(msg);
						return;
					} catch (Exception e) {
						e.printStackTrace();
					}
					handler.sendEmptyMessage(2);
				}
			}).start();
		}
	};

	/**
	 * 判断指定文件夹是否存在
	 * 
	 * @param strFolder
	 *            文件夹路径
	 */
	public void isFolderExists(String strFolder) {
		boolean sdCardExist = Utils.isSdcardExisting();
		if (!sdCardExist) {// 如果不存在SD卡，进行提示
			handler.sendEmptyMessage(4);
		} else {// 如果存在SD卡，判断文件夹目录是否存在
			File dirFirstFile = new File(strFolder);
			if (!dirFirstFile.exists()) {// 判断文件夹目录是否存在
				dirFirstFile.mkdirs();// 如果不存在则创建
			}
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressLint("SimpleDateFormat")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				break;
			case 2:
				Toast.makeText(MyCamera.this, "存储失败！", Toast.LENGTH_LONG).show();
				click_state = 0;
				try {
					progressDialog.dismiss();
				} catch (Exception e) {
				}
				break;
			case 3:// 将图片压缩显示在左下角预览中
				BitmapFactory.Options opts = new BitmapFactory.Options();
				opts.inTempStorage = new byte[100 * 1024];
				opts.inPreferredConfig = Bitmap.Config.RGB_565;
				opts.inPurgeable = true;
				opts.inSampleSize = 4;
				opts.inInputShareable = true;
				Bitmap bitmap = BitmapFactory.decodeFile((String) msg.obj, opts);// 此时返回bm为空

				album.setImageBitmap(bitmap);
				result_bm.recycle();
				try {
					progressDialog.dismiss();
				} catch (Exception e) {
				}

				// 更新图库
				String tmp = Environment.getExternalStorageDirectory().toString();
				tmp = "file://" + tmp;

				try {
					sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse(tmp)));
				} catch (Exception e) {
				}

				myCamera.startPreview();
				click_state = 0;

				// 统计拍照次数
				String take_pic_msg = Utils.readSharedPreferences(MyCamera.this, "take_pic_msg");
				SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String date = sDateFormat.format(new java.util.Date());
				Utils.saveSharedPreferences(MyCamera.this, "take_pic_msg", take_pic_msg + "-!-" + msg.obj + "-----" + date);

				if ("diary".equals(type)) {
					File file = new File((String) msg.obj);
					if (file.exists()) {
						uri = Uri.fromFile(file);
						Intent intent = new Intent();
						intent.setData(uri);
						setResult(11, intent);
						MyCamera.this.finish();
					}
				}
				break;
			case 4:
				Toast.makeText(MyCamera.this, "请插入外部SD存储卡", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		if (myCamera == null) {
			return;
		}
		myCamera.startPreview();
	}

	private AutoFocusCallback mAutoFocusCallBack = new AutoFocusCallback() {
		@Override
		public void onAutoFocus(boolean success, Camera camera) {

		}
	};

	/**
	 * 绘制水印进拍好的图片
	 * 
	 * @param photo
	 * @return
	 */
	private Bitmap drawWater(Bitmap photo) {
		int width = photo.getWidth();
		int height = photo.getHeight();
		Bitmap add_bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		ArrayList<HashMap<String, Object>> item = add_img_list.get(cur);// 第几个水印的属性设置
		if (cur_o == 1) {// 竖屏
			for (int j = 0; j < item.size(); j++) {
				HashMap<String, Object> map = item.get(j);
				if (map.get("str") != null) {// 设置水印文字样式
					add_bm = addText(add_bm, (String) map.get("str"), (Float) map.get("size"), (Integer) map.get("x"), (Integer) map.get("y"));
				} else if (map.get("img") != null) {// 图片
					int img_width = (int) (Integer) map.get("width");
					int img_height = (int) (Integer) map.get("height");
					int marginLeft = (int) (Integer) map.get("x");
					int marginTop = (int) (Integer) map.get("y");
					BitmapFactory.Options opts = new BitmapFactory.Options();
					opts.inTempStorage = new byte[100 * 1024];
					opts.inPreferredConfig = Bitmap.Config.RGB_565;
					opts.inPurgeable = true;
					opts.inSampleSize = 1;
					opts.inInputShareable = true;
					int id = (int) (Integer) (item.get(j).get("img"));
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id, opts);
					bitmap = ImageUtils.zoomImage(bitmap, img_width, img_height);
					add_bm = addImg(add_bm, bitmap, marginLeft, marginTop);
					bitmap.recycle();
				}
			}
		} else {// 横屏
			add_bm = Bitmap.createBitmap(h, w, Bitmap.Config.ARGB_8888);

			for (int j = 0; j < item.size(); j++) {
				HashMap<String, Object> map = item.get(j);
				if (map.get("str") != null) {// 文字
					add_bm = addText(add_bm, (String) map.get("str"), (Float) map.get("size"), (Float) map.get("x2"), (Float) map.get("y2"));
				}
			}
			// 顺时针旋转90度文案，添加水印至拍好的图片
			Matrix m = new Matrix();
			m.setRotate(90, (float) add_bm.getWidth() / 2, (float) add_bm.getHeight() / 2);// 旋转
			Bitmap add_bm2 = Bitmap.createBitmap(add_bm, 0, 0, add_bm.getWidth(), add_bm.getHeight(), m, true);
			add_bm.recycle();
			add_bm = add_bm2;
			for (int j = 0; j < item.size(); j++) {
				HashMap<String, Object> map = item.get(j);
				if (map.get("img") != null) {// 图片 ，先旋转再设置位置
					int img_width = (int) (Integer) map.get("width");
					int img_height = (int) (Integer) map.get("height");
					BitmapFactory.Options opts = new BitmapFactory.Options();
					opts.inTempStorage = new byte[100 * 1024];
					opts.inPreferredConfig = Bitmap.Config.RGB_565;
					opts.inPurgeable = true;
					opts.inSampleSize = 1;
					opts.inInputShareable = true;
					int id = (int) (Integer) (item.get(j).get("img"));
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id, opts);
					bitmap = ImageUtils.zoomImage(bitmap, img_width, img_height);

					Matrix m2 = new Matrix();
					m2.setRotate(90);// 旋转
					Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m2, true);
					bitmap.recycle();
					bitmap = bitmap2;

					int marginLeft = (int) (Integer) map.get("y2");
					int marginTop = (int) (Integer) map.get("x2");
					add_bm = addImg(add_bm, bitmap, marginLeft, marginTop);
					bitmap2.recycle();
					bitmap.recycle();
				}
			}
		}

		Canvas canvas = new Canvas(photo);// 初始化画布 绘制的图像到icon上

		Paint photoPaint = new Paint(); // 建立画笔
		photoPaint.setDither(true); // 获取跟清晰的图像采样
		photoPaint.setFilterBitmap(true);// 过滤一些

		Rect dst = new Rect(0, 0, width, height);
		canvas.drawBitmap(add_bm, null, dst, photoPaint);
		add_bm.recycle();
		return photo;
	}

	/**
	 * 设置水印文字样式
	 * 
	 * @param in
	 * @param str
	 *            文字
	 * @param size
	 *            单位是Pain.setTextSize:px
	 * @param x
	 * @param y
	 * @return
	 */
	private Bitmap addText(Bitmap in, String str, float size, float x, float y) {
		// 初始化画布 绘制的图像到in上
		Canvas canvasTemp = new Canvas(in);
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
		textPaint.setDither(true); // 获取跟清晰的图像采样
		textPaint.setFilterBitmap(true);// 过滤一些
		textPaint.setTextSize(size);// 字体大小，单位是px
		if (Utils.toInt(VERSION, 8) > 8 && Utils.toInt(VERSION, 8) < 19) {
			textPaint.setTypeface(typeface);// 采用默认的宽度
		}
		textPaint.setColor(Color.WHITE);// 采用的颜色
		textPaint.setShadowLayer(3f, 1, 1, 0xcc000000);// 影音的设置
		canvasTemp.drawText(str, x, y, textPaint);// 绘制上去 字，开始未知x,y采用那只笔绘制
		return in;
	}

	/**
	 * 设置水印图片样式
	 * 
	 * @param in
	 *            拍照生成的图片
	 * @param tmp
	 *            水印图片
	 * @param left
	 *            marginLeft
	 * @param top
	 *            marginTop
	 * @return 添加图片水印之后的图片
	 */
	private Bitmap addImg(Bitmap in, Bitmap tmp, float left, float top) {
		Paint photoPaint = new Paint(); // 建立画笔
		photoPaint.setDither(true); // 获取跟清晰的图像采样
		photoPaint.setFilterBitmap(true);// 过滤一些
		Canvas canvasTemp = new Canvas(in);
		canvasTemp.drawBitmap(tmp, left, top, photoPaint);
		return in;
	}

	/**
	 * 传感器监听器
	 */
	private final SensorEventListener mSensorEventListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {// 加速器
				// 感应器有3个方向float类型的值，x,y,z,取值范围在-10到10之间
				float f1 = event.values[0];// x轴加速度,表示左右移动的加速度
				float f2 = event.values[1];// y轴加速度,表示前后移动的加速度
				float f3 = event.values[2];// z轴加速度,表示垂直方向的加速度
				// System.out.println(f1+"      "+f2+"      "+f3);
				if (Math.max(Math.max(f1, f2), f3) == f1) {
					if (cur_o == 1) { // 从竖屏切换到横屏时
						vPager2.setCurrentItem(cur);
						vPager1.setVisibility(View.GONE);
						vPager2.setVisibility(View.VISIBLE);
						cur_o = 2;
						showCanEdit(cur);
					}
				} else if (Math.max(Math.max(f1, f2), f3) == f2) {
					if (cur_o == 2) {// 从横屏切换到竖屏时
						vPager1.setCurrentItem(cur);
						vPager2.setVisibility(View.GONE);
						vPager1.setVisibility(View.VISIBLE);
						cur_o = 1;
						showCanEdit(cur);
					}
				}

				float a0 = event.values[0];
				float a1 = event.values[1];
				float a2 = event.values[2];
				count++;
				if (count >= 10) {
					aa0 = a0;
					aa1 = a1;
					aa2 = a2;
					count = 0;
				}
				if (myCamera != null) {
					if (Math.abs(a0 - aa0) < 1 && Math.abs(a1 - aa1) < 1 && Math.abs(a2 - aa2) < 1) {
						if (should_focus) {
							try {
								myCamera.autoFocus(mAutoFocusCallBack); // 自动对焦后拍照
							} catch (Exception e) {
								e.printStackTrace();
							}
							should_focus = false;
						}
					} else {
						should_focus = true;
					}
				}
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/**
		 * 修改第三个水印的第一个文案<br>
		 * requestCode =1: 第三个水印第一个文案的requestCode = 1<br>
		 * resultCode = 1：修改竖屏第三个水印第一个文案的resultCode = 1；<br>
		 * resultCode = 2：修改横屏第三个水印第一个文案的resultCode = 2；<br>
		 */
		if ((requestCode == 1 || requestCode == 2) && resultCode == 1) {
			String tag = data.getExtras().getString("tag");// 0-0
			String value = mySubString(data.getExtras().getString("value"), 11);
			String[] tag_s = tag.split("-");// 第几个水印的第几个文案
			int page = Integer.parseInt(tag_s[0]);
			int witch = Integer.parseInt(tag_s[1]);
			TextView tv = (TextView) (viewList1.get(page).findViewWithTag(tag));
			tv.setText(value);
			RelativeLayout p = (RelativeLayout) tv.getParent();
			viewList1.remove(p);
			viewList1.add(page, p);
			adapter1.notifyDataSetChanged();
			vPager1.setCurrentItem(cur);

			tv = (TextView) (viewList2.get(page).findViewWithTag(tag));
			tv.setText(value);
			p = (RelativeLayout) tv.getParent();
			viewList2.remove(p);
			viewList2.add(page, p);
			adapter2.notifyDataSetChanged();
			vPager2.setCurrentItem(cur);

			ArrayList<HashMap<String, Object>> item = add_img_list.get(page);
			HashMap<String, Object> map = item.get(witch);
			map.put("str", value);
		}
		setSmall();
	}

	/**
	 * 前置，后置镜头切换
	 */
	@SuppressWarnings("deprecation")
	private void changeCamera() {
		if (Utils.toInt(VERSION, 8) > 8) {
			try {
				Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
				int cameraCount = Camera.getNumberOfCameras();// 所有可用的摄像头个数

				for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
					Camera.getCameraInfo(camIdx, cameraInfo);// 得到每一个摄像头的信息
					if (now_camera == 1) {
						// 现在是后置，变更为前置
						if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {// 代表摄像头的方位，CAMERA_FACING_FRONT前置
																							// CAMERA_FACING_BACK后置
							if (myCamera != null) {
								myCamera.setPreviewCallback(null);
								myCamera.stopPreview();// 停掉原来摄像头的预览
								myCamera.release();// 释放资源
								myCamera = null;// 取消原来摄像头
							}
							try {
								myCamera = Camera.open(camIdx);// 打开当前选中的摄像头
							} catch (Exception e) {
								Toast.makeText(this, "相机正被其他应用使用，无法开启。", Toast.LENGTH_LONG).show();
								finish();
							}
							try {
								myCamera.setPreviewDisplay(holder);// 通过surfaceview显示取景画面
							} catch (IOException e) {
								e.printStackTrace();
							}
							now_camera = 0;
							break;
						}
					} else {
						// 现在是前置， 变更为后置
						if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {// 代表摄像头的方位，CAMERA_FACING_FRONT前置
																						// CAMERA_FACING_BACK后置
							if (myCamera != null) {
								myCamera.setPreviewCallback(null);
								myCamera.stopPreview();// 停掉原来摄像头的预览
								myCamera.release();// 释放资源
								myCamera = null;// 取消原来摄像头
							}
							try {
								myCamera = Camera.open(camIdx);// 打开当前选中的摄像头
							} catch (Exception e) {
								Toast.makeText(this, "相机正被其他应用使用，无法开启。", Toast.LENGTH_LONG).show();
								finish();
							}
							try {
								myCamera.setPreviewDisplay(holder);// 通过surfaceview显示取景画面
							} catch (Exception e) {
								e.printStackTrace();
							}
							now_camera = 1;
							break;
						}
					}
				}
			} catch (Exception e) {
				if (myCamera != null) {
					myCamera.setPreviewCallback(null);
					myCamera.stopPreview();// 停掉原来摄像头的预览
					myCamera.release();// 释放资源
					myCamera = null;// 取消原来摄像头
				}
				try {
					myCamera = Camera.open();// 打开当前选中的摄像头
					myCamera.startPreview();
				} catch (Exception e1) {
					Toast.makeText(this, "相机正被其他应用使用，无法开启。", Toast.LENGTH_LONG).show();
					finish();
				}
				try {
					myCamera.setPreviewDisplay(holder);// 通过surfaceview显示取景画面，设置显示面板控制器
				} catch (IOException e2) {
					e.printStackTrace();
				}
			}
			if (myCamera == null) {
				Toast.makeText(this, "相机异常，无法使用", Toast.LENGTH_SHORT).show();
				return;
			}
			// 设置参数并开始预览
			Camera.Parameters params = myCamera.getParameters(); // 获得已有的设置（默认）
			params.setPictureFormat(PixelFormat.JPEG);
			// 获取摄像头支持的各种分辨率
			List<Size> sizes = params.getSupportedPictureSizes();
			for (int i = 0; i < sizes.size(); i++) {
				float f = (sizes.get(i).height + 0.0f) / (sizes.get(i).width + 0.0f);
				if (f < 0.76f && f > 0.74f) {// 选出3:4比例的size
					if (size == null) {
						size = sizes.get(i);
					} else if (Math.abs(w - sizes.get(i).height) <= Math.abs(w - size.height)) {
						size = sizes.get(i);
					}
				}
			}
			if (size != null) {
				params.setPictureSize(size.width, size.height);
				// Log.i("---", size.width+"   "+size.height);
			} else {
				params.setPictureSize(h, w);
			}
			params.setPreviewSize(640, 480);

			if (Utils.toInt(VERSION, DEFAULT_VERSION_VALUE) > 15) {
				myCamera.setDisplayOrientation(90);
			} else {
				Method rotateMethod;
				try {
					rotateMethod = android.hardware.Camera.class.getMethod("setDisplayOrientation", int.class);
					rotateMethod.invoke(myCamera, 90);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				myCamera.setParameters(params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			myCamera.startPreview();
		} else {
			if (myCamera != null) {
				myCamera.setPreviewCallback(null);
				myCamera.stopPreview();// 停掉原来摄像头的预览
				myCamera.release();// 释放资源
				myCamera = null;// 取消原来摄像头
			}
			try {
				myCamera = Camera.open();
				myCamera.setPreviewDisplay(holder);// 通过surfaceview显示取景画面
				myCamera.setDisplayOrientation(90);
			} catch (Exception e1) {
				Toast.makeText(this, "相机正被其他应用使用，无法开启。", Toast.LENGTH_LONG).show();
				finish();
			}
			myCamera.startPreview();
		}
	}

	/**
	 * 截取字符串内容
	 * 
	 * @param text
	 *            内容
	 * @param length
	 *            截取长度
	 * @return
	 */
	public static String mySubString(String text, int length) {
		int textLength = text.length();
		int byteLength = 0;
		StringBuffer returnStr = new StringBuffer();
		for (int i = 0; i < textLength && byteLength < length * 2; i++) {
			String str_i = text.substring(i, i + 1);
			if (str_i.getBytes().length == 1) {// 英文
				byteLength++;
			} else {// 中文
				byteLength += 2;
			}
			returnStr.append(str_i);
		}
		// if (byteLength >= length * 2) {
		// returnStr.append("...");
		// }
		return returnStr.toString();
	}

	/**
	 * 生成镜面图片
	 * 
	 * @param in
	 * @return
	 */
	private Bitmap symmetry(Bitmap in) {
		Matrix matrix = new Matrix();
		matrix.preScale(-1, 1);
		Bitmap reflectionImage = Bitmap.createBitmap(in, 0, 0, in.getWidth(), in.getHeight(), matrix, false);
		in.recycle();
		return reflectionImage;
	}

	/**
	 * 设置最后一个水印第一个文案周边的白色虚线，3秒后消失
	 * 
	 * @param cur
	 *            当前第几个水印
	 */
	private void showCanEdit(int cur) {
		ArrayList<HashMap<String, Object>> item = add_img_list.get(cur);// 选中水印中的三个文案
		for (int j = 0; j < item.size(); j++) {
			HashMap<String, Object> map = item.get(j);
			if (map.get("str") != null) {
				final TextView tv1 = (TextView) (viewList1.get(cur).findViewWithTag(cur + "-" + j));
				final TextView tv2 = (TextView) (viewList2.get(cur).findViewWithTag(cur + "-" + j));
				if (map.get("clickable") != null && !map.get("clickable").equals("")) {
					tv1.setBackgroundResource(R.drawable.dash_bg);// 设置最后一个水印第一个文案周边的白色虚线
					tv2.setBackgroundResource(R.drawable.dash_bg);
					new Handler().postDelayed(new Runnable() {// 3秒后消失
								@SuppressWarnings("deprecation")
								@Override
								public void run() {
									tv1.setBackgroundDrawable(null);
									tv2.setBackgroundDrawable(null);
								}
							}, 3000);
				}
			}
		}
	}

	/**
	 * 开启相机
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// 开启相机
		if (myCamera == null) {
			if (now_camera == 1) {
				now_camera = 0;
			} else {
				now_camera = 1;
			}
			changeCamera();
		}
	}

	/**
	 * 关闭预览并释放资源
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (myCamera != null) {
			myCamera.setPreviewCallback(null);
			myCamera.stopPreview();
			myCamera.release();
			myCamera = null;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		MyCamera.this.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (result_bm != null && !result_bm.isRecycled()) {
			// result_bm.recycle();
			result_bm = null;
		}
		if (myCamera != null) {
			myCamera.setPreviewCallback(null);
			myCamera.stopPreview();
			myCamera.release();
			myCamera = null;
		}
		mSensorManager.unregisterListener(mSensorEventListener);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.vPager1:

			break;
		case R.id.vPager2:

			break;
		}
		return true;
	}
}