package com.gftest.myappclient.wangyi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuanDBHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "quanzi.db";
	public static final int DB_VERSION = 1;

	private String sql = "create table if not exists " + Constants.TABLE_NAME + "(_id integer primary key autoincrement," + Constants.TABLE_QUAN_COLUMN1 + " varchar(30)," + Constants.TABLE_QUAN_COLUMN2 + " varchar(30));";

	public QuanDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("sql=" + sql);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
