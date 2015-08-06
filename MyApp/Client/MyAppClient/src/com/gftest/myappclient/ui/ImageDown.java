package com.gftest.myappclient.ui;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;
import com.gftest.myappclient.file.FileDownloader;
import com.gftest.myappclient.listener.DownloadProgressListener;
import com.gftest.myappclient.utils.Utils;

public class ImageDown extends BaseActivity implements OnClickListener {
	private EditText downloadpathText;
	private TextView resultView;
	private ProgressBar progressBar;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_down);
		initView();
	}

	private void initView() {
		downloadpathText = (EditText) this.findViewById(R.id.path);
		progressBar = (ProgressBar) this.findViewById(R.id.downloadbar);
		resultView = (TextView) this.findViewById(R.id.resultView);
		button = (Button) this.findViewById(R.id.button);

		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button:
			String path = downloadpathText.getText().toString();
			System.out.println("path=" + path);
			if (Utils.isSdExists()) {
				download(path, new File(Environment.getExternalStorageDirectory() + "/myappclient/download"));
			} else {
				Toast.makeText(ImageDown.this, "SDCard不存在或者写保护", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

	/**
	 * 主线程(UI线程) 对于显示控件的界面更新只是由UI线程负责，如果是在非UI线程更新控件的属性值，更新后的显示界面不会反映到屏幕上
	 * 
	 * @param path
	 * @param savedir
	 */
	private void download(final String path, final File savedir) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				FileDownloader loader = new FileDownloader(ImageDown.this, path, savedir, 3);// 构建文件下载器
				progressBar.setMax(loader.getFileSize());// 设置进度条的最大刻度为文件的长度

				try {
					loader.download(new DownloadProgressListener() {
						@Override
						public void onDownloadSize(int size) {// 实时获知文件已经下载的数据长度
							Message msg = new Message();
							msg.what = 1;
							msg.getData().putInt("size", size);
							handler.sendMessage(msg);// 发送消息
						}
					});
				} catch (Exception e) {
					handler.obtainMessage(-1).sendToTarget();
				}
			}
		}).start();
	}

	/**
	 * 当Handler被创建会关联到创建它的当前线程的消息队列，该类用于往消息队列发送消息 消息队列中的消息由当前线程内部进行处理
	 */
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				progressBar.setProgress(msg.getData().getInt("size"));
				float num = (float) progressBar.getProgress() / (float) progressBar.getMax();
				int result = (int) (num * 100);
				resultView.setText(result + "%");

				if (progressBar.getProgress() == progressBar.getMax()) {
					Toast.makeText(ImageDown.this, "下载完成", Toast.LENGTH_SHORT).show();
				}
				break;
			case -1:
				Toast.makeText(ImageDown.this, "下载失败", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

}
