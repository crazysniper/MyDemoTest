package com.gftest.myappclient.ui;

import java.io.File;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;
import com.gftest.myappclient.download.FileDownloader2;
import com.gftest.myappclient.utils.Common;

public class ImageDown2 extends BaseActivity implements OnClickListener {

	Button btn_download;
	MyHandler myHandler;

	String downloadPath = "http://gdown.baidu.com/data/wisegame/653346a13ab69081/yixin_146.apk";
	String fileName = "易信.apk";

	String saveDir = Environment.getExternalStorageDirectory() + "/mytest/";

	/** 文件总长度 */
	public int contentTotalSize = 0;
	/** 成功下载 */
	public static int DOWNLOAD_COMPLETE = 1;
	/** 下载失败 */
	public static int DOWNLOAD_FAILED = 2;
	/** 下载状态 */
	public static int DOWNLOADING_PROCESS = 3;
	/** 通知栏管理 */
	public NotificationManager notificationManager;
	/** 通知栏 */
	public Notification notification;
	public PendingIntent updatePendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_down2);

		myHandler = new MyHandler();
		btn_download = (Button) findViewById(R.id.btn_download);
		initData();
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		this.notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		this.notification = new Notification();
		// 设置通知栏显示内容
		notification.icon = R.drawable.ic_launcher;
		notification.tickerText = "智慧园区新消息提醒";
		// 自定义通知栏视图
		notification.contentView = new RemoteViews(getPackageName(), R.layout.custom_notifycation_layout);
		notification.contentView.setTextViewText(R.id.txt_notifycation_title, "准备下载...");
		btn_download.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_download:
			new Thread(new Runnable() {
				@Override
				public void run() {
					// 开启3个线程进行下载
					final FileDownloader2 loader = new FileDownloader2(ImageDown2.this, downloadPath, saveDir, 3);
					contentTotalSize = loader.getFileSize();
					try {
						loader.startDownload(new FileDownloader2.DownloadProgressListener() {
							@Override
							public void onDownloadSize(int size) {// 实时获知文件已经下载的数据长度
								Message msg = myHandler.obtainMessage(DOWNLOADING_PROCESS, size);
								myHandler.sendMessage(msg);

							}
						});
					} catch (Exception e) {
						myHandler.sendEmptyMessage(DOWNLOAD_FAILED);
					}
				}
			}).start();
			break;
		}
	}

	/**
	 * 处理消息
	 */
	class MyHandler extends Handler {

		@SuppressWarnings("deprecation")
		@Override
		public void handleMessage(Message msg) {

			int what = msg.what;
			if (what == DOWNLOAD_COMPLETE) {// 下载完成后处理操作
				// 点击安装PendingIntent
				Uri uri = Uri.fromFile(new File(saveDir, fileName));
				Intent installIntent = new Intent(Intent.ACTION_VIEW);
				installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
				updatePendingIntent = PendingIntent.getActivity(ImageDown2.this, 0, installIntent, 0);
				notification.flags = Notification.FLAG_AUTO_CANCEL;
				notification.defaults = Notification.DEFAULT_SOUND;// 铃声提醒
				notification.setLatestEventInfo(ImageDown2.this, "易信", "下载完成,立即安装。", updatePendingIntent);
				notificationManager.notify(0, notification);

			} else if (what == DOWNLOAD_FAILED) {
				// 下载失败
				notification.setLatestEventInfo(ImageDown2.this, "易信", "下载失败", updatePendingIntent);
				notificationManager.notify(0, notification);

			} else if (what == DOWNLOADING_PROCESS) {
				// 更新下载过程中的进度提示
				int process = (Integer) msg.obj;
				notification.contentView.setTextViewText(R.id.txt_notifycation_title, "最新版正在下载" + "：" + Common.getVolume(process) + "/" + Common.getVolume(contentTotalSize));
				notification.contentView.setProgressBar(R.id.notifycation_progressbar, contentTotalSize, process, false);
				notificationManager.notify(0, notification);

				if (process == contentTotalSize) {
					Log.i("版本更新下载最后结束大小：", Common.getVolume(process));
					myHandler.sendEmptyMessage(DOWNLOAD_COMPLETE);
					// 下载成功
				}
			}
		}
	}

}
