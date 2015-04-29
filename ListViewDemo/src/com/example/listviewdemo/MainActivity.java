package com.example.listviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.listviewdemo.edit.EditMain;
import com.example.listviewdemo.expandablelist.ExpandableListActitivty;
import com.example.listviewdemo.focus.FocusActivity;
import com.example.listviewdemo.qq.QQMain;
import com.example.listviewdemo.question.QuestionActivity;
import com.example.listviewdemo.xiala.XialaMain;

public class MainActivity extends Activity implements OnClickListener {

	private Button btn1, btn2, btn2_2, btn3, btn4, list_silde_del, tuodong, btn5, btn6, btn7, focus;
	private Button xiala, ExpandableListActitivty, edit;

	private TextView numTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	public void initView() {
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn2_2 = (Button) findViewById(R.id.btn2_2);
		list_silde_del = (Button) findViewById(R.id.list_silde_del);
		btn3 = (Button) findViewById(R.id.btn3);
		tuodong = (Button) findViewById(R.id.tuodong);
		btn4 = (Button) findViewById(R.id.btn4);
		btn5 = (Button) findViewById(R.id.btn5);
		numTv = (TextView) findViewById(R.id.num);
		btn6 = (Button) findViewById(R.id.btn6);
		btn7 = (Button) findViewById(R.id.btn7);
		xiala = (Button) findViewById(R.id.xiala);
		ExpandableListActitivty = (Button) findViewById(R.id.ExpandableListActitivty);
		edit = (Button) findViewById(R.id.edit);
		focus = (Button) findViewById(R.id.focus);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn2_2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		list_silde_del.setOnClickListener(this);
		tuodong.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		xiala.setOnClickListener(this);
		ExpandableListActitivty.setOnClickListener(this);
		edit.setOnClickListener(this);
		focus.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn1:
			intent = new Intent(MainActivity.this, ListView1.class);
			break;
		case R.id.btn2:
			intent = new Intent(MainActivity.this, Async_ListView.class);
			break;
		case R.id.btn2_2:
			intent = new Intent(MainActivity.this, Async_ListView.class);
			break;
		case R.id.btn3:
			intent = new Intent(MainActivity.this, Async3.class);
			break;
		case R.id.list_silde_del:
			intent = new Intent(MainActivity.this, List_Slide_Del_Activity.class);
			break;
		case R.id.tuodong:
			intent = new Intent(MainActivity.this, TuodongListView.class);
			break;
		case R.id.btn4:
			intent = new Intent(MainActivity.this, Async4.class);
			break;
		case R.id.btn5:
			intent = new Intent(MainActivity.this, ListView5.class);
			break;
		case R.id.btn6:
			intent = new Intent(MainActivity.this, QQMain.class);
			break;
		case R.id.btn7:
			intent = new Intent(MainActivity.this, QuestionActivity.class);
			break;
		case R.id.xiala:
			intent = new Intent(MainActivity.this, XialaMain.class);
			break;
		case R.id.ExpandableListActitivty:
			intent = new Intent(MainActivity.this, ExpandableListActitivty.class);
			break;
		case R.id.edit:
			intent = new Intent(MainActivity.this, EditMain.class);
			break;
		case R.id.focus:
			intent = new Intent(MainActivity.this, FocusActivity.class);
			break;
		}
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	public static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				int arg1 = msg.arg1;
				System.out.println("arg1=" + arg1);
				break;
			}
		}
	};
}
