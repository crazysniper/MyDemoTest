package com.gftest.myappclient.db;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * 实现数据库的增删该查功能。
 * 
 */
public class DBTools {
	private DataBaseHelper dbh;

	public DBTools(Context context) {
		dbh = new DataBaseHelper(context);
	}

	/**
	 * 获取每条线程已经下载的文件长度
	 * 
	 * @param path对应的下载路径
	 * @return 对应线程已经下载的进度。
	 */
	public Map<Integer, Integer> getData(String path) {
		SQLiteDatabase db = dbh.getReadableDatabase();
		Cursor cursor = db.rawQuery("select threadid, downlength from  " + DataBaseHelper.TABLE_NAME + "  where downpath=?", new String[] { path });
		Map<Integer, Integer> data = new HashMap<Integer, Integer>();

		while (cursor.moveToNext()) {
			data.put(cursor.getInt(0), cursor.getInt(1));
		}

		cursor.close();
		db.close();
		return data;
	}

	/**
	 * 保存每条线程已经下载的文件长度
	 * 
	 * @param path对应的下载路径
	 * @param map
	 *            键值对存放，key:线程id；value:对应线程下载的长度
	 */
	public void save(String path, Map<Integer, Integer> map) {
		SQLiteDatabase db = dbh.getWritableDatabase();
		db.beginTransaction();
		try {
			for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
				db.execSQL("insert into  " + DataBaseHelper.TABLE_NAME + "  (downpath, threadid, downlength) values(?,?,?)", new Object[] { path, entry.getKey(), entry.getValue() });
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		db.close();
	}

	/**
	 * 该方法更新线程下载进度。
	 * 
	 * @param path对应的下载路径
	 * @param map
	 *            键值对存放，key:线程id；value:对应线程下载的长度
	 */
	public void update(String path, Map<Integer, Integer> map) {
		SQLiteDatabase db = dbh.getWritableDatabase();
		// 开启事务
		db.beginTransaction();
		try {
			for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
				db.execSQL("update  " + DataBaseHelper.TABLE_NAME + "  set downlength=? where downpath=? and threadid=?", new Object[] { entry.getValue(), path, entry.getKey() });
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		db.close();
	}

	/**
	 * @param path删除对应下载路径的下载记录
	 */
	public void delete(String path) {
		SQLiteDatabase db = dbh.getWritableDatabase();
		db.execSQL("delete from  " + DataBaseHelper.TABLE_NAME + "  where downpath=?", new Object[] { path });
		db.close();
	}
}
