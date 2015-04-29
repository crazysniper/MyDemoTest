package com.example.camerademo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

@SuppressLint("NewApi")
public class ChangeCamera extends Activity implements Callback, OnClickListener {
	private Button myButton, myButton2, myButton3, myButton4, myButton5, myButton6;
	private Camera camera;
	private SurfaceHolder surfaceHolder;
	private SurfaceView surfaceView;
	private boolean isPreview = false;
	private String filepath = "";// 照片保存路径
	private int cameraPosition = 1;// 0代表前置摄像头，1代表后置摄像头

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_camera);
		initView();
	}

	private void initView() {
		myButton = (Button) findViewById(R.id.myButton);
		myButton2 = (Button) findViewById(R.id.myButton2);
		myButton3 = (Button) findViewById(R.id.myButton3);
		myButton4 = (Button) findViewById(R.id.myButton4);
		myButton5 = (Button) findViewById(R.id.myButton5);
		myButton6 = (Button) findViewById(R.id.myButton6);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		myButton.setOnClickListener(this);
		myButton2.setOnClickListener(this);
		myButton3.setOnClickListener(this);
		myButton4.setOnClickListener(this);
		myButton5.setOnClickListener(this);
		myButton6.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myButton:
			initCamera();
			break;
		case R.id.myButton2:
			if (camera != null && isPreview) {
				camera.stopPreview();
				camera.release();
				camera = null;
				isPreview = false;
			}
			break;
		case R.id.myButton3:
			onBackPressed();
			break;

		case R.id.myButton6:
			changeCamera();
			break;
		}
	}

	/**
	 * 开启相机
	 */
	private void initCamera() {
		if (!isPreview) {
			camera = Camera.open();
		}
		if (camera != null && !isPreview) {
			try {
				camera.setPreviewDisplay(surfaceHolder);
			} catch (IOException e) {
				e.printStackTrace();
			}
			/**
			 * 开始预览取景，在预览取景之前需要调用Camera的setPreViewDisplay(SurfaceHolder
			 * holder)方法设置使用哪个SurfaceView来显示取景图片
			 */
			camera.startPreview();
			isPreview = true;
		}
	}

	private void changeCamera() {
		int count = 0;
		CameraInfo cameraInfo = new CameraInfo();
		count = Camera.getNumberOfCameras();// 得到摄像头的个数
		for (int i = 0; i < count; i++) {
			Camera.getCameraInfo(i, cameraInfo);// 得到每个摄像头的信息
			if(cameraPosition==0){
				
			}

		}
	}

	// 创建jpeg图片回调数据对象
	PictureCallback jpeg = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			try {
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				// 自定义文件保存路径 以拍摄时间区分命名
				filepath = "/sdcard/Messages/MyPictures/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
				File file = new File(filepath);
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);// 将图片压缩的流里面
				bos.flush();// 刷新此缓冲区的输出流
				bos.close();// 关闭此输出流并释放与此流有关的所有系统资源
				camera.stopPreview();// 关闭预览 处理数据
				camera.startPreview();// 数据处理完后继续开始预览
				bitmap.recycle();// 回收bitmap空间
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	/**
	 * 开启相机
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		initCamera();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	/**
	 * surface摧毁时 关闭预览并释放资源
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (camera != null) {
			camera.setPreviewCallback(null);
			camera.stopPreview();
			camera.release();
			camera = null;
		}
	}
}
