package com.example.toastdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private Button btn1, btn2, btn3, btn4, btn5;
	Toast toast;
	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		btn5 = (Button) findViewById(R.id.btn5);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			Toast.makeText(MainActivity.this, "默认Toast样式", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn2:
			toast = Toast.makeText(getApplicationContext(), "自定义位置Toast", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			break;
		case R.id.btn3:
			toast = Toast.makeText(getApplicationContext(), "带图片的Toast", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			LinearLayout toastView = (LinearLayout) toast.getView();
			ImageView imageCodeProject = new ImageView(getApplicationContext());
			imageCodeProject.setImageResource(R.drawable.ic_launcher);
			toastView.addView(imageCodeProject, 0);
			toast.show();
			break;
		case R.id.btn4:
			LayoutInflater inflater = getLayoutInflater();
			View layout = inflater.inflate(R.layout.custom, (ViewGroup) findViewById(R.id.llToast));
			ImageView image = (ImageView) layout.findViewById(R.id.tvImageToast);
			image.setImageResource(R.drawable.ic_launcher);
			TextView title = (TextView) layout.findViewById(R.id.tvTitleToast);
			title.setText("Attention");
			TextView text = (TextView) layout.findViewById(R.id.tvTextToast);
			text.setText("完全自定义Toast");
			toast = new Toast(getApplicationContext());
			toast.setGravity(Gravity.RIGHT | Gravity.TOP, 12, 40);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(layout);
			toast.show();
			break;
		case R.id.btn5:
			new Thread(new Runnable() {
				public void run() {
					showToast();
				}
			}).start();
			break;
		}
	}

	public void showToast() {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), "我来自其他线程！", Toast.LENGTH_SHORT).show();

			}
		});
	}
}
