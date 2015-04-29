package com.example.camerademo;

import java.io.IOException;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * http://blog.csdn.net/redoffice/article/details/6716462
 * 
 */
public class SurfaceView_Activity extends Activity implements Callback, OnClickListener {

	private Button myButton, myButton2, myButton3;
	private Camera camera;
	private SurfaceHolder surfaceHolder;
	private SurfaceView surfaceView;
	private boolean isPreview = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_surfaceview);

		// 设置屏幕方向
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		initView();
	}

	private void initView() {
		myButton = (Button) findViewById(R.id.myButton);
		myButton2 = (Button) findViewById(R.id.myButton2);
		myButton3 = (Button) findViewById(R.id.myButton3);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		// surfaceview不维护自己的缓冲区，等待屏幕渲染引擎将内容推送到用户面前
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		myButton.setOnClickListener(this);
		myButton2.setOnClickListener(this);
		myButton3.setOnClickListener(this);
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

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
