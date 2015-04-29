package com.example.listviewdemo.question;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.listviewdemo.R;
import com.example.listviewdemo.qq.Msg;
import com.example.listviewdemo.qq.QQAdapter;

public class QuestionActivity extends Activity implements OnClickListener {
	private Button btn1, btn2;
	private TextView answer;
	private ListView listView;
	private QQAdapter adapter;
	private List<Msg> msgList = new ArrayList<Msg>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_main);
		initView();
	}

	private void initView() {
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		answer = (TextView) findViewById(R.id.answer);
		listView = (ListView) findViewById(R.id.listView);
		adapter = new QQAdapter(QuestionActivity.this, msgList);
		listView.setAdapter(adapter);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		initData();

		listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				/**
				 * firstVisibleItem表示在现时屏幕第一个ListItem(部分显示的ListItem也算)
				 * 在整个ListView的位置（下标从0开始）
				 **/
				System.out.println("firstVisibleItem=" + String.valueOf(firstVisibleItem));
				/** visibleItemCount表示在现时屏幕可以见到的ListItem(部分显示的ListItem也算)总数 **/
				System.out.println("visibleItemCount=" + String.valueOf(visibleItemCount));
				/** totalItemCount表示ListView的ListItem总数 **/
				System.out.println("totalItemCount=" + String.valueOf(totalItemCount));

				/**
				 * listView.getFirstVisiblePosition()表示在现时屏幕第一个ListItem(
				 * 第一个ListItem部分显示也算) 在整个ListView的位置（下标从0开始）
				 **/
				System.out.println("firstPosition=" + String.valueOf(listView.getFirstVisiblePosition()));
				/**
				 * listView.getLastVisiblePosition()表示在现时屏幕最后一个ListItem(
				 * 最后ListItem要完全显示出来才算) 在整个ListView的位置（下标从0开始）
				 **/
				System.out.println("lasPosition=" + String.valueOf(listView.getLastVisiblePosition()));
			}
		});
	}

	private void initData() {
		Msg msg = new Msg();
		msg.setContent("哈喽，美女");
		msg.setType(Msg.TYPE_RECEIVED);
		msgList.add(msg);

		msg = new Msg();
		msg.setContent("你好，帅哥");
		msg.setType(Msg.TYPE_SEND);
		msgList.add(msg);

		msg = new Msg();
		msg.setContent("很高兴见到你");
		msg.setType(Msg.TYPE_RECEIVED);
		msgList.add(msg);

		msg = new Msg();
		msg.setContent("我也是");
		msg.setType(Msg.TYPE_SEND);
		msgList.add(msg);

		msg = new Msg();
		msg.setContent("最近如何啊");
		msg.setType(Msg.TYPE_RECEIVED);
		msgList.add(msg);

		msg = new Msg();
		msg.setContent("挺好的，你呢");
		msg.setType(Msg.TYPE_SEND);
		msgList.add(msg);

		msg = new Msg();
		msg.setContent("我也挺好的");
		msg.setType(Msg.TYPE_RECEIVED);
		msgList.add(msg);

		msg = new Msg();
		msg.setContent("哈哈哈");
		msg.setType(Msg.TYPE_SEND);
		msgList.add(msg);

		msg = new Msg();
		msg.setContent("再见啦");
		msg.setType(Msg.TYPE_RECEIVED);
		msgList.add(msg);

		msg = new Msg();
		msg.setContent("嗯，再见");
		msg.setType(Msg.TYPE_SEND);
		msgList.add(msg);

		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			answer.setText("getCount()=" + listView.getCount() + "__getChildCount()=" + listView.getChildCount());
			System.out.println("getCount() 返回的值。也就是“所包含的 Item 总个数”。 ");
			System.out.println("getChildCount） 返回的是显示层面上的“所包含的子 View 个数”。 ");
			System.out.println("当 ListView 中的 Item 比较少无需滚动即可全部显示时，二者是等价的；当 Item 个数较多需要滚动才能浏览全部的话， getChildCount() < getCount() 其中 getChildCount() 返回的是当前可见的 Item 个数。");
			break;
		case R.id.btn2:
			System.out.println("添加headerView的方法addHeaderView()必须放在setAdapter之前，想给listview添加头部则必须在给其绑定adapter前添加，否则会报错。原因是当我们在调用setAdapter时，android会先判断当前listview是否已经添加header，如果已经添加则会生存一个新的tempadapter,这个新的tempadapter包含我们设置adapter所有内容以及listview的header和footer。所以当我们在给listview添加了header后在程序中调用listview.getadapter时，返回的tempadapter而不是我们通过setadapter传进来的adapter。如果没有设置adapter则tempadapter与我们自己的adapter是一样的。listview.getAdapter.getCount()方法返回值会比我们预期的要大，原因是添加了header");
			answer.setText("添加headerView的方法addHeaderView()必须放在setAdapter之前，想给listview添加头部则必须在给其绑定adapter前添加，否则会报错。原因是当我们在调用setAdapter时，android会先判断当前listview是否已经添加header，如果已经添加则会生存一个新的tempadapter,这个新的tempadapter包含我们设置adapter所有内容以及listview的header和footer。所以当我们在给listview添加了header后在程序中调用listview.getadapter时，返回的tempadapter而不是我们通过setadapter传进来的adapter。如果没有设置adapter则tempadapter与我们自己的adapter是一样的。listview.getAdapter.getCount()方法返回值会比我们预期的要大，原因是添加了header");
			break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
}
