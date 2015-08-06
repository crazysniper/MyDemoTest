package com.gftest.myappclient.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;

public class NotificationActivity extends BaseActivity implements OnClickListener {

	private static final String TAG = "NotificationActivity";
	private static final String INTENT_ACTION = "cn.lynn.noti.action.receiver";

	private static final int NORMAL_NOTI_ID = 0x1;
	private static final int INBOX_NOTI_ID = 0x2;
	private static final int BIGTEXT_NOTI_ID = 0x3;
	private static final int BIGPICTURE_NOTI_ID = 0x4;
	private static final int PROGRESS_NOTI_ID = 0x5;
	private static final int CUSTOM_NOTI_ID = 0x6;
	private static final int FORWARD_REGULAR_NOTI_ID = 0x7;
	private static final int FORWARD_SPECIAL_NOTI_ID = 0x8;
	private static int normalNum, inboxNum, bigtextNum, bigpictureNum, progressNum;

	private Button normalBtn, inboxBtn, bigtextBtn, bigpictureBtn, progressBtn, customBtn, dismissBtn, forwardRegularBtn, forwardSpecialBtn;
	private Bitmap icon;
	private NotificationManager notificationManager;

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(INTENT_ACTION)) {
				Toast.makeText(NotificationActivity.this, "正在播放：苏打绿的《小情歌》", Toast.LENGTH_LONG).show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		initView();
	}

	private void initView() {
		icon = BitmapFactory.decodeResource(getResources(), R.drawable.notify);

		// 从SystemManager的系统服务容器的上下文中通过Bind IPC方式获取通知服务
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		normalBtn = (Button) findViewById(R.id.normalBtn);
		inboxBtn = (Button) findViewById(R.id.inboxBtn);
		bigtextBtn = (Button) findViewById(R.id.bigtextBtn);
		bigpictureBtn = (Button) findViewById(R.id.bigpictureBtn);
		progressBtn = (Button) findViewById(R.id.progressBtn);
		customBtn = (Button) findViewById(R.id.customBtn);
		dismissBtn = (Button) findViewById(R.id.dismissBtn);
		forwardRegularBtn = (Button) findViewById(R.id.forwardRegularBtn);
		forwardSpecialBtn = (Button) findViewById(R.id.forwardSpecialBtn);

		normalBtn.setOnClickListener(this);
		inboxBtn.setOnClickListener(this);
		bigtextBtn.setOnClickListener(this);
		bigpictureBtn.setOnClickListener(this);
		progressBtn.setOnClickListener(this);
		customBtn.setOnClickListener(this);
		dismissBtn.setOnClickListener(this);
		forwardRegularBtn.setOnClickListener(this);
		forwardSpecialBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.normalBtn:
			showNormalNoti();
			break;
		case R.id.inboxBtn:
			showInboxNoti();
			break;
		case R.id.bigtextBtn:
			showBigTextNoti();
			break;
		case R.id.bigpictureBtn:
			showBigPictureNoti();
			break;
		case R.id.progressBtn:
			showProgressNoti();
			break;
		case R.id.customBtn:
			showCustomNoti();
			break;
		case R.id.dismissBtn:
			dismissAllNotis();
			break;
		case R.id.forwardRegularBtn:
			showForwardRegularNoti();
			break;
		case R.id.forwardSpecialBtn:
			showForwardSpecialNoti();
			break;
		}
	}

	/**
	 * 正常样式通知
	 */
	private void showNormalNoti() {
		Notification normalNoti = new NotificationCompat.Builder(this).setAutoCancel(true) // 设置自动清除
				.setContentInfo(String.valueOf(++normalNum)) // 设置附加内容
				.setContentTitle("内容标题") // 设置内容标题
				.setContentText("内容文本") // 设置内容文本
				.setDefaults(Notification.DEFAULT_ALL) // 设置使用所有默认值（声音、震动、闪屏等）
				.setLargeIcon(icon) // 设置大型图标
				.setSmallIcon(R.drawable.stat_notify_gmail) // 设置小型图标
				.setTicker("正常样式通知") // 设置状态栏提示信息
				.build();
		notificationManager.notify(NORMAL_NOTI_ID, normalNoti);
	}

	/**
	 * 收件箱样式通知
	 */
	private void showInboxNoti() {
		Notification inboxNoti = new NotificationCompat.Builder(this).setAutoCancel(true).setContentInfo(String.valueOf(++inboxNum)).setContentTitle("收件箱样式通知标题").setContentText("收件箱样式通知内容").setDefaults(Notification.DEFAULT_ALL).setLargeIcon(icon).setSmallIcon(R.drawable.stat_notify_gmail).setTicker("收件箱样式通知").setStyle(new NotificationCompat.InboxStyle() // 设置通知样式为收件箱样式
				.addLine("M.Lynn reminder").addLine("M.Lynn launch").addLine("M.Lynn hello").setSummaryText("+3 more")) // 设置在细节区域底端添加一行文本
				.build();
		notificationManager.notify(INBOX_NOTI_ID, inboxNoti);
	}

	/**
	 * 大文本样式通知
	 */
	private void showBigTextNoti() {
		Notification bigtextNoti = new NotificationCompat.Builder(this).setAutoCancel(true).setContentInfo(String.valueOf(++bigtextNum)).setContentTitle("大文本样式通知标题").setContentText("大文本样式通知内容").setDefaults(Notification.DEFAULT_ALL).setLargeIcon(icon).setSmallIcon(R.drawable.stat_notify_gmail).setTicker("大文本样式通知").setStyle(new NotificationCompat.BigTextStyle() // 设置通知样式为大型文本样式
				.bigText("Helper class for generating large-format notifications that include a lot of text. This class is a \"rebuilder\": It attaches to a Builder object and modifies its behavior, like so.")).build();
		notificationManager.notify(BIGTEXT_NOTI_ID, bigtextNoti);
	}

	/**
	 * 大图片样式通知
	 */
	private void showBigPictureNoti() {
		Notification bigpictureNoti = new NotificationCompat.Builder(this).setAutoCancel(true).setContentInfo(String.valueOf(++bigpictureNum)).setContentTitle("大图片样式通知标题").setContentText("大图片样式通知内容").setDefaults(Notification.DEFAULT_ALL).setLargeIcon(icon).setSmallIcon(R.drawable.stat_notify_gmail).setTicker("大图片样式通知").setStyle(new NotificationCompat.BigPictureStyle() // 设置通知样式为大型图片样式
				.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.fantasy))).build();
		notificationManager.notify(BIGPICTURE_NOTI_ID, bigpictureNoti);
	}

	/**
	 * 显示进度通知
	 */
	private void showProgressNoti() {
		final NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setContentInfo(String.valueOf(++progressNum)).setContentTitle("Picture Download").setContentText("显示进度通知内容").setDefaults(Notification.DEFAULT_ALL).setLargeIcon(icon).setSmallIcon(R.drawable.stat_notify_gmail).setTicker("显示进度通知").setOngoing(true);
		// Start a lengthy operation in a background thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				int incr;
				// Do the "lengthy" operation 20 times
				for (incr = 0; incr <= 100; incr += 5) {
					builder.setProgress(100, incr, false);
					notificationManager.notify(PROGRESS_NOTI_ID, builder.build());

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Log.d(TAG, "sleep failure");
					}
				}
				builder.setContentText("下载完成").setProgress(0, 0, false).setOngoing(false);
				notificationManager.notify(PROGRESS_NOTI_ID, builder.build());
			}
		}).start();
	}

	/**
	 * 自定义样式通知
	 */
	private void showCustomNoti() {
		RemoteViews views = new RemoteViews(getPackageName(), R.layout.custom);
		Intent intent = new Intent(INTENT_ACTION);
		intent.putExtra("isPlay", true);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
		views.setOnClickPendingIntent(R.id.play_pause_music, pendingIntent);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setContent(views).setDefaults(Notification.DEFAULT_ALL).setLargeIcon(icon).setSmallIcon(R.drawable.music_icon).setTicker("自定义样式通知").setOngoing(true);
		notificationManager.notify(CUSTOM_NOTI_ID, builder.build());
	}

	/**
	 * 导航到NotiRegularActivity通知
	 */
	private void showForwardRegularNoti() {
		Notification forwardRegularNoti = new NotificationCompat.Builder(this).setContentTitle("Forward NotiRegularActivity").setContentText("lynnli1229@gmail.com").setSmallIcon(R.drawable.stat_notify_gmail).setContentIntent(backHeapStack()).build();
		notificationManager.notify(FORWARD_REGULAR_NOTI_ID, forwardRegularNoti);
		finish();
	}

	/**
	 * 导航到NotiSpecialActivity通知
	 */
	private void showForwardSpecialNoti() {
		Notification forwardSpecialNoti = new NotificationCompat.Builder(this).setContentTitle("Forward NotiSpecialActivity").setContentText("lynnli1229@gmail.com").setSmallIcon(R.drawable.stat_notify_gmail).setContentIntent(backHomeScreen()).build();
		notificationManager.notify(FORWARD_SPECIAL_NOTI_ID, forwardSpecialNoti);
		finish();
	}

	/**
	 * 清除所有通知
	 */
	private void dismissAllNotis() {
		notificationManager.cancelAll();
	}

	/**
	 * 按应用中正常工作流程返回
	 * 
	 * @return PendingIntent
	 */
	private PendingIntent backHeapStack() {
		return TaskStackBuilder.create(this).addParentStack(NotificationActivity.class).addNextIntent(new Intent(this, NotificationActivity.class)).getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	/**
	 * 直接返回主屏，不走应用正常工作流程
	 * 
	 * @return PendingIntent
	 */
	private PendingIntent backHomeScreen() {
		Intent intent = new Intent(this, NotificationActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	@Override
	public void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction(INTENT_ACTION);
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}

}
