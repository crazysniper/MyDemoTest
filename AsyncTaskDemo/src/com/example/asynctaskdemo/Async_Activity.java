package com.example.asynctaskdemo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class Async_Activity extends Activity implements OnClickListener {

	private Button btn1, btn2, btn3;
	private ProgressBar progress;
	private FrameLayout frameLayout;
	private ImageView view;
	private Bitmap bitmap = null;
	ProgressDialog dialog = null;

	private static final String params = "http://upload.wikimedia.org/wikipedia/commons/thumb/e/ea/Hukou_Waterfall.jpg/800px-Hukou_Waterfall.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_async);
		initView();
	}

	private void initView() {
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		progress = (ProgressBar) this.findViewById(R.id.progress);
		frameLayout = (FrameLayout) this.findViewById(R.id.frameLayout);
		view = (ImageView) frameLayout.findViewById(R.id.image);

		progress.setVisibility(View.GONE);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			view.setVisibility(View.GONE);
			dialog = ProgressDialog.show(this, "", "下载数据，请稍等 …", true, true);
			// 启动一个后台线程
			new Thread(new Runnable() {
				@Override
				public void run() {
					// 这里下载数据
					try {
						URL url = new URL(params);
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setDoInput(true);
						conn.connect();
						InputStream inputStream = conn.getInputStream();
						bitmap = BitmapFactory.decodeStream(inputStream);
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
			break;
		case R.id.btn2:
			view.setVisibility(View.GONE);
			MyASyncTask yncTask = new MyASyncTask(this, frameLayout);
			yncTask.execute(params);
			break;
		case R.id.btn3:
			view.setVisibility(View.GONE);
			ExecutorService executorService = Executors.newFixedThreadPool(5);

			progress.setVisibility(View.VISIBLE);
			final Handler newhandler = new Handler();
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					try {
						URL newurl = new URL(params);
						HttpURLConnection conn = (HttpURLConnection) newurl.openConnection();
						conn.setDoInput(true);
						conn.connect();
						InputStream inputStream = conn.getInputStream();
						bitmap = BitmapFactory.decodeStream(inputStream);
						newhandler.post(new Runnable() {
							@Override
							public void run() {
								view.setVisibility(View.VISIBLE);
								view.setImageBitmap(bitmap);
								progress.setVisibility(View.GONE);
							}
						});
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			});

			break;

		}
	}

	/** 这里重写handleMessage方法，接受到子线程数据后更新UI **/
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				view.setVisibility(View.VISIBLE);
				view.setImageBitmap(bitmap);
				dialog.dismiss();
				break;
			}
		}
	};

	public class MyASyncTask extends AsyncTask<String, Integer, Bitmap> {
		private ViewGroup viewGroup;
		private Context context;

		public MyASyncTask(Context context, ViewGroup viewGroup) {
			this.context = context;
			this.viewGroup = viewGroup;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap = null;
			try {

				URL url = new URL(params[0]);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoInput(true);
				con.connect();
				InputStream inputStream = con.getInputStream();

				bitmap = BitmapFactory.decodeStream(inputStream);
				inputStream.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bitmap;
		}

		// 执行获得图片数据后，更新UI:显示图片，隐藏进度条
		@Override
		protected void onPostExecute(Bitmap Result) {
			view.setVisibility(View.VISIBLE);
			ImageView imgView = (ImageView) this.viewGroup.getChildAt(0);
			imgView.setImageBitmap(Result);
			ProgressBar bar = (ProgressBar) this.viewGroup.getChildAt(1);
			bar.setVisibility(View.GONE);
		}
	}

}