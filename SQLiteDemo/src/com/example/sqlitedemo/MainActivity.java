package com.example.sqlitedemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * SQLite由以下几个部分组成：SQL编译器、内核、后端以及附件。SQLite通过利用虚拟机和虚拟数据库引擎(VDBE)，是调试、
 * 修改和扩展SQLite的内核变得更加方便。所有SQL语句都被编译成易读的、可以在SQLite虚拟机中执行的程序集。<br>
 * 
 * 袖珍型的SQLite竟然可以支持高达2TB大小的数据库，每个数据库都是以单个文件的形式存在，这些数据都是以B-Tree的数据结构形式存储在磁盘上。<br>
 * 
 * 在事务处理方面，SQLite通过数据库级上的独占性和共享锁来实现独立事务处理。这意味着多个进程可以在同一时间从同一数据库读取数据，但只有一个可以写入数据。
 * 在某个进程或线程想数据库执行写操作之前，必须获得独占锁。在获得独占锁之后，其他的读或写操作将不会再发生。<br>
 * 
 * SQLite采用动态数据类型，当某个值插入到数据库时
 * ，SQLite将会检查它的类型，如果该类型与关联的列不匹配，SQLite则会尝试将该值转换成该列的类型，
 * 如果不能转换，则该值将作为本身的类型存储，SQLite称这为“弱类型”。但有一个特例，如果是INTEGER PRIMARY
 * KEY，则其他类型不会被转换，会报一个“datatype missmatch”的错误。<br>
 * 
 * 概括来讲，SQLite支持NULL、INTEGER、REAL、TEXT和BLOB数据类型，分别代表空值、整型值、浮点值、字符串文本、二进制对象。<br>
 * 
 * 目前没有可用于 SQLite 的网络服务器。从应用程序运行位于其他计算机上的 SQLite 的惟一方法是从网络共享运行。这样会导致一些问题，像 UNIX®
 * 和 Windows® 网络共享都存在文件锁定问题。还有由于与访问网络共享相关的延迟而带来的性能下降问题。 SQLite 只提供数据库级的锁定。
 * SQLite 没有用户帐户概念，而是根据文件系统确定所有数据库的权限。<br>
 * 
 * 由于资源占用少、性能良好和零管理成本，嵌入式数据库有了它的用武之地，像Android、iPhone都有内置的SQLite数据库供开发人员使用，
 * 它的易用性可以加快应用程序的开发，并使得复杂的数据存储变得轻松了许多。<br>
 * 
 * http://blog.csdn.net/liuhe688/article/details/6712782<br>
 * 
 * @author Gao
 * 
 */
public class MainActivity extends Activity implements OnClickListener {
	private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;
	private EditText stuName, stuid, stuName1;
	private TextView result_stu;

	private MyDataBaseOpenHelper helper;
	private ContentValues contentValues;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		stuName = (EditText) findViewById(R.id.stuName);
		btn4 = (Button) findViewById(R.id.btn4);
		btn5 = (Button) findViewById(R.id.btn5);
		btn6 = (Button) findViewById(R.id.btn6);
		result_stu = (TextView) findViewById(R.id.result_stu);
		stuid = (EditText) findViewById(R.id.stuid);
		stuName1 = (EditText) findViewById(R.id.stuName1);
		btn7 = (Button) findViewById(R.id.btn7);

		helper = new MyDataBaseOpenHelper(MainActivity.this, "student.db", null, 1);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);

		contentValues = new ContentValues();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			helper.getReadableDatabase();
			break;
		case R.id.btn2:
			helper.getReadableDatabase();
			break;
		case R.id.btn3:
			String name = stuName.getText().toString().trim();
			if (!TextUtils.isEmpty(name)) {
				SQLiteDatabase database = helper.getReadableDatabase();
				contentValues.clear();
				contentValues.put("nickname", name);
				long id = database.insert("student", null, contentValues);
				if (id == -1) {
					Toast.makeText(MainActivity.this, "插入失败，返回 -1", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "插入成功，当前插入下标是:" + id, Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.btn4:
			break;
		case R.id.btn5:
			SQLiteDatabase database = helper.getReadableDatabase();
			Cursor cursor = database.query("student", null, null, null, null, null, null);
			StringBuilder stringBuilder = new StringBuilder();
			if (cursor.moveToFirst()) {
				do {
					String id = cursor.getString(cursor.getColumnIndex("id"));
					String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
					stringBuilder.append("id=" + id + ",姓名=" + nickname + "\n");
				} while (cursor.moveToNext());
			}
			cursor.close();
			result_stu.setText(stringBuilder.toString());
			break;
		case R.id.btn7:
			if (TextUtils.isEmpty(stuid.getText().toString())) {
				return;
			}
			int id = Integer.parseInt(stuid.getText().toString());
			String name1 = stuName1.getText().toString().trim();
			contentValues.clear();
			if (!TextUtils.isEmpty(name1)) {
				SQLiteDatabase database2 = helper.getReadableDatabase();
				contentValues.put("id", id);
				contentValues.put("nickname", name1);
				int index = database2.update("student", contentValues, "id=?", new String[] { String.valueOf(id) });
				Toast.makeText(MainActivity.this, "修改数据下标=" + index, Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}
}
