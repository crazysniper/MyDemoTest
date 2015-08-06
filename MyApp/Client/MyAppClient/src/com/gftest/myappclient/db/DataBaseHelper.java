package com.gftest.myappclient.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库操作类
 * 
 */
public class DataBaseHelper extends SQLiteOpenHelper {
	/**
	 * 数据库名字
	 */
	public static final String DATABASE_NAME = "xiangxm.db";
	/**
	 * 表的名字
	 */
	public static final String TABLE_NAME = "t_download";

	/**
	 * 数据库版本，。
	 */
	private static final int VERSION = 1;

	/**
	 * 构造器
	 * 
	 * @param context
	 */
	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE_NAME
				+ " (id integer primary key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
		Log.i("--------->", "数据库表创建成功") ;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}
