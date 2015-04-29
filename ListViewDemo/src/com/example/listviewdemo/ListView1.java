package com.example.listviewdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * http://blog.csdn.net/forlong401/article/details/7935644
 * 
 * @author Administrator
 * 
 */
public class ListView1 extends Activity implements OnItemClickListener, OnItemLongClickListener, OnItemSelectedListener, OnTouchListener {
	private ListView listView;
	private List<Integer> arrayList = new ArrayList<Integer>();
	private ArrayAdapter<Integer> adapter;
	private LayoutInflater layoutInflater;
	private View headerView, footerView;
	private TextView headerTv, footerTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview1);

		initView();
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.listView);
		layoutInflater = LayoutInflater.from(this);
		headerView = (View) layoutInflater.inflate(R.layout.headerview, null);
		footerView = (View) layoutInflater.inflate(R.layout.footerview, null);
		headerTv = (TextView) headerView.findViewById(R.id.header_tv);
		footerTv = (TextView) footerView.findViewById(R.id.footer_tv);

		arrayList = getData();
		adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_expandable_list_item_1, arrayList);
		listView.addHeaderView(headerView);
		listView.addFooterView(footerView);

		System.out.println("getChildCount=" + listView.getChildCount());// 0
		System.out.println("********");

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		// 需要实现onItemSelected ,onNothingSelected
		listView.setOnItemSelectedListener(this);
		listView.setOnTouchListener(this);

		listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				System.out.println("first=" + listView.getFirstVisiblePosition());
				System.out.println("last=" + listView.getLastVisiblePosition());
				System.out.println("___________________");
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

			}
		});

		// 显示listview的headerview，放在第一个
		// listView.setSelectionAfterHeaderView();

		// 设置选中的时候的背景
		// listView.setSelector(getResources().getDrawable(R.drawable.selector));
		listView.setSelector(new ColorDrawable(Color.RED));

		// 直接在一个手机屏幕的显示的item列表的第一个，显示listview中数据(包括headView)的第四个
		listView.setSelection(3);

		System.out.println("listView.getHeight()=" + listView.getHeight());// 0
		System.out.println("listView.getFooterViewsCount()=" + listView.getFooterViewsCount());// 1
		System.out.println("listView.getHeaderViewsCount()=" + listView.getHeaderViewsCount());// 1
		System.out.println("listView.getFirstVisiblePosition()=" + listView.getFirstVisiblePosition());// 0
		System.out.println("listView.getLastVisiblePosition()=" + listView.getLastVisiblePosition());// -1
		System.out.println("listView.getCount=" + listView.getCount());// headerView+20+footerView=22;
		System.out.println("listView.getChildCount=" + listView.getChildCount());// 0
		System.out.println("adapter.getCount=" + adapter.getCount());// 除去headerView和footerView的剩余个数=20

		listView.post(new Runnable() {
			@Override
			public void run() {
				System.out.println("--------------");
				System.out.println("getChildCount=" + listView.getChildCount());// 9，屏幕中可见的ListView的个数
				/**
				 * 为什么在上面的getChildCount返回的个数是0，而这边返回的值是9
				 * 
				 * http://www.cnblogs.com/linlf03/archive/2013/06/06/3120408.
				 * html<br>
				 * 
				 * 
				 * 原來setAdapter是非同步(asynchronous)。
				 * 只要在setAdapter()之后，加入post函式去更新ListView的ChildView即可。
				 */
			}
		});

	}

	public List<Integer> getData() {
		for (int i = 0; i < 20; i++) {
			arrayList.add(i);
		}
		return arrayList;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		System.out.println("itemClick");
		System.out.println("在ListView中的下标：" + position);
		System.out.println("在List中的下标：" + id);
		AlertDialog.Builder builder = new AlertDialog.Builder(ListView1.this);
		builder.setTitle("onItemClick标题");
		builder.setCancelable(true);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("确定");
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	/**
	 * 如果回调函数处理了长按事件，返回真；否则返回假。
	 * 
	 * 长按抬起的时候才会触发
	 * 
	 * 
	 * 返回true：有震动 ； 返回false：没有震动
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		System.out.println("onItemLongClick");
		System.out.println("在ListView中的下标：" + position);
		System.out.println("在List中的下标：" + id);
		AlertDialog.Builder builder = new AlertDialog.Builder(ListView1.this);
		builder.setTitle("onItemLongClick标题");
		builder.setCancelable(true);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("确定");
				dialog.dismiss();
			}
		});
		builder.create().show();
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		System.out.println("onItemSelected");
		System.out.println("在ListView中的下标：" + position);
		System.out.println("在List中的下标：" + id);

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		System.out.println("onNothingSelected");
	}

	/**
	 * 当onTouch返回值为true的时候，会屏蔽onItemClick和onItemLongClick事件
	 * 
	 * 当onTouch返回false的时候，不会屏蔽
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		System.out.println("ontouch");
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			System.out.println("按下");
			break;
		case MotionEvent.ACTION_MOVE:
			System.out.println("移动");
			break;
		case MotionEvent.ACTION_UP:
			System.out.println("抬起");
			break;
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
}
