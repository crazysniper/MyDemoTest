package com.example.listviewdemo.qq;

import java.util.Date;
import java.util.LinkedList;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.listviewdemo.R;

public class QQActivity4 extends Activity {

	private LinkedList<PeopleStudentBean> sList = null;
	private LinkedList<PeopleTeacherBean> tList = null;
	private LinkedList<Bean> beans = null;

	/** 聊天message 格式 */
	private ListView listView;
	/** 信息编辑框 */
	private EditText edt;
	/** 信息发送按钮 */
	private Button btnEnter;

	private CustomAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qq4);
		listView = (ListView) findViewById(R.id.lvMessages);
		edt = (EditText) findViewById(R.id.edt);
		btnEnter = (Button) findViewById(R.id.enter);

		sList = new LinkedList<PeopleStudentBean>();
		tList = new LinkedList<PeopleTeacherBean>();
		beans = new LinkedList<Bean>();
		String[] msg = new String[] { "你好！", "你也在金象工作吗？", "我在天安门扫大街呢，这里可舒服了！", "原来你也细化这个工作啊，我这里还招人呢，你来不？来的话，我一句话的事儿！", "呵呵，你好！", "是的，你在哪里呢？", "吼吼，这么便宜的事儿？！，我怎么没有遇到呢。", "恩，好啊 好啊。那等着我。。。" };

		// 0 是教师； 1 是学生
		for (int i = 0; i < 4; i++) {
			sList.add(new PeopleStudentBean(msg[i], R.drawable.you, "", 1));
			tList.add(new PeopleTeacherBean(msg[i + 4], R.drawable.me, "", 0));
		}

		// 归放到 同一个 类集合Bean中
		for (int j = 0; j < sList.size(); j++) {
			beans.add(sList.get(j));
			beans.add(tList.get(j));
		}
		adapter = new CustomAdapter(this, beans);
		listView.setAdapter(adapter);

		initViewsMethod();
		onHandleMethod();
	}

	/** 处理listView 的 item方法 */
	private void initViewsMethod() {
		listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
				menu.setHeaderTitle("提示：");
				menu.setHeaderIcon(android.R.drawable.stat_notify_error);
				menu.add(0, 0, 1, "删除");
				menu.add(1, 1, 0, "取消");
			}
		});
	}

	/** 处理发送信息的方法 */
	public void onHandleMethod() {
		btnEnter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String txt = edt.getText().toString().trim();
				if (TextUtils.isEmpty(txt)) {
					Toast.makeText(getApplicationContext(), "发送内容不能为空 !", Toast.LENGTH_SHORT).show();
					return;
				}
				adapter.addItemNotifiChange(new Bean(txt, R.drawable.me, new Date() + "", 0));
				edt.setText("");
				listView.setSelection(beans.size() - 1);
			}
		});
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			Bean bean = (Bean) adapter.getItem(info.position);
			beans.remove(bean);
			adapter.notifyDataSetChanged();
			break;
		}
		return super.onContextItemSelected(item);
	}

}
