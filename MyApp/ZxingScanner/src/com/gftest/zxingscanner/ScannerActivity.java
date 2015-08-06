package com.gftest.zxingscanner;

import android.annotation.TargetApi;
import android.app.Activity;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ScannerActivity extends Activity implements OnClickListener, Callback {

	private Button back;
	private SurfaceView surfaceView;
	private CenterRect centerRect;

	private Camera camera;
	private SurfaceHolder surfaceHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanner);
		initView();
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	private void initView() {
		back = (Button) findViewById(R.id.back);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		centerRect = (CenterRect) findViewById(R.id.centerRect);

		surfaceHolder = surfaceView.getHolder();

		surfaceHolder.addCallback(this);
		back.setOnClickListener(this);

		// String value = getIntent().getStringExtra("key");

		if (android.os.Build.VERSION.SDK_INT > 8) {
			Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
			int camerCount = Camera.getNumberOfCameras();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;

		default:
			break;
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

}
