package com.gftest.myappclient.db;

import com.gftest.myappclient.constants.Constants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountDBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "LoginTest.db";
	private static final String CREATE_TBL_LOGININFO = "create table " + Constants.LOGIN_ACCOUNT_DB_NAME + "(" + Constants.LOGIN_ACCOUNT_DB_KYE1 + " integer primary key autoincrement," + Constants.LOGIN_ACCOUNT_DB_KYE2 + " text," + Constants.LOGIN_ACCOUNT_DB_KYE3 + " text)";
	private SQLiteDatabase db;
	private static final int DB_VERSION = 1;

	public AccountDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	/**
	 * 当数据库被第一次创建时调用
	 */
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		this.db = arg0;
		System.out.println("CREATE_TBL_LOGININFO=" + CREATE_TBL_LOGININFO);
		db.execSQL(CREATE_TBL_LOGININFO);
	}

	/**
	 * 向指定数据库中插入一条数据
	 * 
	 * @param values
	 *            ContentValues 键值对， 相当于map
	 * @param tableName
	 */
	public void insert(ContentValues values, String tableName) {
		SQLiteDatabase db = getWritableDatabase();
		db.insert(tableName, null, values);
		db.close();
	}

	/**
	 * 返回表中所有数据。返回的游标是在调用方法处关闭
	 * 
	 * @param tableName
	 * @return
	 */
	public Cursor quertAll(String tableName) {
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.query(tableName, null, null, null, null, null, null);
		return cursor;
	}

	/**
	 * 根据ID删除一条数据
	 * 
	 * @param id
	 * @param tableName
	 */
	public void del(int id, String tableName) {
		if (db == null) {
			db = getWritableDatabase();
		}
		db.delete(tableName, Constants.LOGIN_ACCOUNT_DB_KYE1 + "=?", new String[] { String.valueOf(id) });
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}