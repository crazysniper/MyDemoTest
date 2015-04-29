package com.example.camerademo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.camerademo.utils.Utils;

public class Camera2 extends Activity implements Callback, OnClickListener {
	private Button myButton, myButton2, myButton3, myButton4, myButton5;
	private Camera camera;
	private SurfaceHolder surfaceHolder;
	private SurfaceView surfaceView;
	private boolean isPreview = false;

	private int screenWidth, screenHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 设置屏幕方向
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		setContentView(R.layout.activity_camera2);

		initView();
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		myButton = (Button) findViewById(R.id.myButton);
		myButton2 = (Button) findViewById(R.id.myButton2);
		myButton3 = (Button) findViewById(R.id.myButton3);
		myButton4 = (Button) findViewById(R.id.myButton4);
		myButton5 = (Button) findViewById(R.id.myButton5);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		// 得到SurfaceHolder对象
		surfaceHolder = surfaceView.getHolder();
		// 添加回调函数
		surfaceHolder.addCallback(this);
		// 设置该SurfaceView自己不维护缓冲
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

		screenWidth = displayMetrics.widthPixels;
		screenHeight = displayMetrics.heightPixels;

		myButton.setOnClickListener(this);
		myButton2.setOnClickListener(this);
		myButton3.setOnClickListener(this);
		myButton4.setOnClickListener(this);
		myButton5.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myButton:
			initCamera();
			break;
		case R.id.myButton2:
			if (camera != null && isPreview) {
				/**
				 * 结束程序时，调用Camera的StopPriview()结束取景预览，并调用release()方法释放资源。
				 */
				camera.stopPreview();
				camera.release();
				camera = null;
				isPreview = false;
			}
			break;
		case R.id.myButton3:
			onBackPressed();
			break;
		case R.id.myButton4:// 拍照
			if (camera != null) {
				/**
				 * 后面的2个参数PictureCallback ，分别对应原始图像、JPG图像
				 * 
				 * 图像数据可以在PictureCallback接口的void onPictureTaken(byte[] data,
				 * Camera camera)中获得
				 * 
				 * 
				 * 通常我们只关心JPG图像数据，此时前面两个PictureCallback接口参数可以直接传null
				 */
				camera.takePicture(null, null, pcb);
			}
			break;
		case R.id.myButton5:// 保存

			break;
		}
	}

	/**
	 * 开启相机
	 */
	private void initCamera() {
		if (!isPreview) {
			// 1.调用Camera的open()方法打开相机。
			camera = Camera.open();
		}
		if (camera != null && !isPreview) {
			try {
				// 2.调用Camera的setParameters()方法获取拍照参数。该方法返回一个Camera.Parameters对象。
				Parameters parameters = camera.getParameters();
				Size size = parameters.getPreviewSize();

				System.out.println("size.height=" + size.height);
				System.out.println("size.width=" + size.width);

				// 3.调用Camera.Paramers对象方法设置拍照参数，设置预览照片的大小
				// parameters.setPreviewSize(screenWidth, screenHeight / 2);
				// 每秒显示4帧
				parameters.setPreviewFrameRate(4);
				// 设置图片格式
				parameters.setPictureFormat(PixelFormat.JPEG);

				// 设置JPG照片的质量
				parameters.set("jpeg-quality", 85);
				// 设置照片的大小
				parameters.setPictureSize(screenWidth / 2, screenHeight / 4);
				// 4.调用Camera的setParameters，并将Camera.Paramers作为参数传入，这样即可对相机的拍照参数进行控制
				// camera.setParameters(parameters);
				/**
				 * 5.调用Camera的startPreview()方法开始预览取景,在预览取景之前需要调用
				 * Camera的setPreViewDisplay(SurfaceHolder
				 * holder)方法设置使用哪个SurfaceView来显示取景图片。 通过SurfaceView显示取景画面
				 */
				camera.setPreviewDisplay(surfaceHolder);
			} catch (IOException e) {
				e.printStackTrace();
			}
			/**
			 * 开始预览取景，在预览取景之前需要调用Camera的setPreViewDisplay(SurfaceHolder
			 * holder)方法设置使用哪个SurfaceView来显示取景图片
			 */
			camera.startPreview();

			// 自动对焦
			camera.autoFocus(cb);
			isPreview = true;
		}
	}

	/**
	 * 自动对焦
	 */
	AutoFocusCallback cb = new AutoFocusCallback() {
		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			if (success) {
				System.out.println("可以拍照");
			}
		}
	};

	/**
	 * 拍照后输出图片
	 */
	PictureCallback pcb = new PictureCallback() {
		/**
		 * 第一个参数就是图像数据， 第二个参数则是相机
		 */
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// 根据拍照所得的数据创建位图
			final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			LayoutInflater layoutInflater = LayoutInflater.from(Camera2.this);
			View view = layoutInflater.inflate(R.layout.camera_edit_dialog, null);
			final EditText editText = (EditText) view.findViewById(R.id.edit);
			Button save = (Button) view.findViewById(R.id.save);

			AlertDialog.Builder builder = new AlertDialog.Builder(Camera2.this);
			final AlertDialog dialog = builder.create();
			dialog.setView(view);
			builder.setView(view);
			save.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String content = editText.getText().toString().trim();
					System.out.println("content:" + content);
					if ("".equals(content)) {
						Toast.makeText(Camera2.this, "内容不可为空", Toast.LENGTH_SHORT).show();
					} else {
						if (Utils.isSdcardExisting()) {
							File file = new File(Environment.getExternalStorageDirectory(), content + ".jpg");
							FileOutputStream fileOutputStream = null;
							// 打开指定文件对应的输出流
							try {
								fileOutputStream = new FileOutputStream(file);
								// 把位图输出到指定文件中
								Matrix matrix = new Matrix();
								Bitmap bm = Bitmap.createBitmap(screenWidth, screenHeight, Config.ARGB_8888);// 固定所拍相片大小
								matrix.setScale((float) bm.getWidth() / (float) bitmap.getWidth(), (float) bm.getHeight() / (float) bitmap.getHeight());// 注意参数一定是float哦
								Canvas canvas = new Canvas(bm);// 用bm创建一个画布 ，
								// 可以往bm中画入东西了
								canvas.drawBitmap(bitmap, matrix, null);
								bm.compress(CompressFormat.JPEG, 40, fileOutputStream);
								fileOutputStream.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(Camera2.this, "sd卡不存在", Toast.LENGTH_SHORT).show();
						}
					}
					dialog.dismiss();
				}
			});
			dialog.show();
		}
	};

	/**
	 * 开启相机，刚进入Activity的时候就会执行
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		initCamera();
	}

	/**
	 * 在Surface的大小发生改变时激发
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	/**
	 * surface摧毁时，关闭预览并释放资源
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// 如果camera不为null ,释放摄像头
		if (camera != null) {
			// 7.结束程序时，调用Camera的StopPriview()结束取景预览，并调用release()方法释放资源.
			camera.setPreviewCallback(null);
			camera.stopPreview();
			camera.release();
			camera = null;
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
