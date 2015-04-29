package com.example.dialogdemo;

import com.example.dialogdemo.MyDialog.MyDialogListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private static final String TAG = "MainActivity";

	/** 普通对话框 */
	private Button btnGeneral;
	/** 多按钮的普通对话框1 */
	private Button btnButtons1;
	/** 多按钮的普通对话框2 */
	private Button btnButtons2;
	/** 列表选择对话框 */
	private Button btnListView;
	/** 单选列表选择对话框 */
	private Button btnListViewSingle;
	/** 多选列表选择对话框 */
	private Button btnListViewMulti;
	/** 滚动等待对话框 */
	private Button btnProgressDialog;
	/** 进度条对话框 */
	private Button btnProgressDialogH;
	/** 自定义对话框 */
	private Button btnCustomDialog;
	/** 从底部弹出的对话框 */
	private Button dialogFromBottom;
	/** 从底部弹出的对话框 */
	private Button dialogFromBottom2;
	/** 完全自定义 */
	private Button dialog1;
	private Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		btnGeneral = (Button) findViewById(R.id.btnGeneral);
		btnButtons1 = (Button) findViewById(R.id.btnButtons1);
		btnButtons2 = (Button) findViewById(R.id.btnButtons2);
		btnListView = (Button) findViewById(R.id.btnListView);
		btnListViewSingle = (Button) findViewById(R.id.btnListViewSingle);
		btnListViewMulti = (Button) findViewById(R.id.btnListViewMulti);
		btnProgressDialog = (Button) findViewById(R.id.btnProgressDialog);
		btnProgressDialogH = (Button) findViewById(R.id.btnProgressDialogH);
		btnCustomDialog = (Button) findViewById(R.id.btnCustomDialog);
		dialogFromBottom = (Button) findViewById(R.id.dialogFromBottom);
		dialogFromBottom2 = (Button) findViewById(R.id.dialogFromBottom2);
		dialog1 = (Button) findViewById(R.id.dialog1);
		btn = (Button) findViewById(R.id.btn);

		btnGeneral.setOnClickListener(this);
		btnButtons1.setOnClickListener(this);
		btnButtons2.setOnClickListener(this);
		btnListView.setOnClickListener(this);
		btnListViewSingle.setOnClickListener(this);
		btnListViewMulti.setOnClickListener(this);
		btnProgressDialog.setOnClickListener(this);
		btnProgressDialogH.setOnClickListener(this);
		btnCustomDialog.setOnClickListener(this);
		dialogFromBottom.setOnClickListener(this);
		dialogFromBottom2.setOnClickListener(this);
		dialog1.setOnClickListener(this);
		btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnGeneral:
			btnGeneral();
			break;
		case R.id.btnButtons1:
			btnButtons1();
			break;
		case R.id.btnButtons2:
			btnButtons2();
			break;
		case R.id.btnListView:
			btnListView();
			break;
		case R.id.btnListViewSingle:
			btnListViewSingle();
			break;
		case R.id.btnListViewMulti:
			btnListViewMulti();
			break;
		case R.id.btnProgressDialog:
			btnProgressDialog();
			break;
		case R.id.btnProgressDialogH:
			btnProgressDialogH();
			break;
		case R.id.btnCustomDialog:
			btnCustomDialog();
			break;
		case R.id.dialogFromBottom:
			dialogFromBottom();
			break;
		case R.id.dialogFromBottom2:
			dialogFromBottom2();
			break;
		case R.id.dialog1:
			dialog1();
			break;
		case R.id.btn:
			btn();
		}
	}

	/**
	 * 创建普通的对话框
	 */
	private void btnGeneral() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("普通对话框");
		builder.setMessage("这是一个普通的对话框");
		builder.setIcon(R.drawable.ic_launcher);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
			}
		});
		builder.show();
	}

	/**
	 * 创建多按钮普通的对话框
	 */
	private void btnButtons1() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("多按钮普通对话框");
		builder.setMessage("这是一个多按钮普通的对话框");
		builder.setIcon(R.drawable.ic_launcher);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

	/**
	 * 创建多按钮普通的对话框
	 */
	private void btnButtons2() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("多按钮普通对话框");
		builder.setMessage("这是一个多按钮普通的对话框");
		builder.setIcon(R.drawable.ic_launcher);
		builder.setPositiveButton("确定", listener);
		builder.setNegativeButton("取消", listener);
		builder.setNeutralButton("中性", listener);
		builder.show();
	}

	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
				break;
			case DialogInterface.BUTTON_NEUTRAL:
				Toast.makeText(MainActivity.this, "中性", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
				break;
			}
		}
	};

	/**
	 * 创建列表形式的对话框
	 */
	private void btnListView() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final String items[] = { "A", "B", "C" };
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();
			}
		});
		builder.show();
	}

	/**
	 * 创建单选列表的对话框
	 */
	private void btnListViewSingle() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final String items[] = { "A", "B", "C" };
		builder.setTitle("选择一个字母");
		/**
		 * 默认选择第二个，下标从0开始
		 */
		builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String select_item = items[which].toString();
				Toast.makeText(MainActivity.this, "选择的是" + select_item, Toast.LENGTH_SHORT).show();
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.i(TAG, "which=" + which);// -1
				// 为什么会输出-1，因为此处设置的是PositiveButton，而BUTTON_POSITIVE = -1;
			}
		});
		builder.show();
	}

	/**
	 * 
	 * 创建多选列表的对话框
	 */
	private void btnListViewMulti() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final String items[] = { "A", "B", "C" };
		builder.setTitle("选择多个个字母");
		boolean checkedItems[] = { false, true, false };
		builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				String select_item = items[which].toString();
				Toast.makeText(MainActivity.this, "选择的是" + select_item, Toast.LENGTH_SHORT).show();
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.i(TAG, "which=" + which);// -1
			}
		});
		builder.show();
	}

	/**
	 * 滚动等待对话框
	 */
	private void btnProgressDialog() {
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setIcon(R.drawable.ic_launcher);
		dialog.setTitle("等待");
		dialog.setMessage("正在加载...");
		dialog.show();
		dialog.setCancelable(true);// 设置为不可取消，按返回键不可取消dialog
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				dialog.dismiss();
			}
		}).start();
	}

	/**
	 * 进度条对话框
	 */
	private void btnProgressDialogH() {

	}

	/**
	 * 自定义Dialog
	 */
	private void btnCustomDialog() {
		View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(view);
		final AlertDialog dialog = builder.create();
		Button btnCustom = (Button) view.findViewById(R.id.btnCustom);
		btnCustom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	/**
	 * 从底部弹出的对话框
	 */
	private void dialogFromBottom() {
		A a = new A();
		a.setA(2);
		System.out.println("a1为：" + a.getA());
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;

		View view = LayoutInflater.from(this).inflate(R.layout.dialog2, null);
		final Dialog dialog = new Dialog(this, R.style.custom);
		Window window = dialog.getWindow();
		window.setGravity(Gravity.BOTTOM);

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		view.setLayoutParams(layoutParams);
		dialog.setContentView(view);
		TextView confirm_btn = (TextView) view.findViewById(R.id.confirm_btn);
		TextView cancel_btn = (TextView) view.findViewById(R.id.cancel_btn);

		confirm_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		cancel_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}

	public void dialogFromBottom2() {
		A a = new A();
		System.out.println("a2为：" + a.getA());

		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		System.out.println("输出宽度：" + width);
		System.out.println("输出高度：" + height);

		WindowManager wm = getWindowManager();
		int width2 = wm.getDefaultDisplay().getWidth();
		int height2 = wm.getDefaultDisplay().getHeight();
		System.out.println("输出宽度2：" + width2);
		System.out.println("输出高度2：" + height2);
		// 上面的2中获取屏幕的宽度，高度结果是一致的

		View view = LayoutInflater.from(this).inflate(R.layout.dialog2, null);
		final Dialog dialog = new Dialog(this, R.style.myDialog);
		Window window = dialog.getWindow();
		window.setGravity(Gravity.BOTTOM); // 此处可以设置imageDialog显示的位置，是对话框相对整个activity的ui的位置
		// window.setWindowAnimations(R.style.dialogstyle); // 添加动画

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, LayoutParams.WRAP_CONTENT);
//		layoutParams.gravity=Gravity.BOTTOM;// // 此处可以设置imageDialog显示的位置无效
		layoutParams.leftMargin=10;
		dialog.setContentView(view, layoutParams);
		TextView confirm_btn = (TextView) view.findViewById(R.id.confirm_btn);
		TextView cancel_btn = (TextView) view.findViewById(R.id.cancel_btn);
		confirm_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		cancel_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}

	private MyDialog dialog;

	private void dialog1() {
		dialog = new MyDialog(MainActivity.this, R.style.myDialog);
		dialog.show();
		dialog.setNickname("hahhaha");
		dialog.setPsd("mima");
		dialog.setTitle("完全自定义");
		dialog.setMyDialogListener(new MyDialogListener() {
			@Override
			public void onCancel() {
				dialog.dismiss();
			}

			@Override
			public void onComfirm() {
				Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void btn() {
		Intent intent = new Intent(MainActivity.this, NextActivity.class);
		startActivity(intent);
	}
}
