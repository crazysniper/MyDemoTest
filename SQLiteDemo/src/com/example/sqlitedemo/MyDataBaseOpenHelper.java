package com.example.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDataBaseOpenHelper extends SQLiteOpenHelper {

	private static final String sql_stu = "create table student(id integer primary key autoincrement,nickname text);";
	private static final String sql_tec = "create table teacher(id integer primary key autoincrement,nickname text)";

	private Context context;

	public MyDataBaseOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		System.out.println("MyDataBaseOpenHelper");
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		System.out.println("onCreate");
		arg0.execSQL(sql_stu);
		arg0.execSQL(sql_tec);
		Toast.makeText(context, "创建数据库", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		System.out.println("onUpgrade");
		System.out.println("arg1=" + arg1);
		System.out.println("arg2=" + arg2);
		arg0.execSQL("drop table if exists student");
		arg0.execSQL("drop table if exists teacher");
	}

}
