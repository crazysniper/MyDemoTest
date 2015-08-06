package com.gftest.myappclient.wangyi;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QuanDaoImpl {

	private static QuanDaoImpl quanDaoImpl;
	private SQLiteDatabase database;

	private QuanDaoImpl(String... params) {
	}

	private QuanDaoImpl(Context context) {
		QuanDBHelper quanDBHelper = new QuanDBHelper(context);
		database = quanDBHelper.getWritableDatabase();
	}

	public synchronized static QuanDaoImpl getInstance(Context context) {
		if (quanDaoImpl == null) {
			quanDaoImpl = new QuanDaoImpl(context);
		}
		return quanDaoImpl;
	}

	/**
	 * 添加数据
	 * 
	 * @param quanEntity
	 * @return true:成功;false:失败
	 */
	public boolean addQuan(QuanEntity quanEntity) {
		boolean flag = false;
		if (quanEntity != null) {
			ContentValues contentValues = new ContentValues();
			contentValues.put(Constants.TABLE_QUAN_COLUMN1, quanEntity.getId());
			contentValues.put(Constants.TABLE_QUAN_COLUMN2, quanEntity.getName());
			long id = database.insert(Constants.TABLE_NAME, null, contentValues);
			flag = id == -1 ? true : false;
		}
		return flag;
	}

	/**
	 * 删除数据
	 * 
	 * @param selection
	 * @param selectionArgs
	 * @return true:成功;false:失败
	 */
	public boolean deleteQuan(String selection, String[] selectionArgs) {
		return false;
	}

	/**
	 * 更新数据
	 * 
	 * @param contentValues
	 * @param selection
	 * @param selectionArgs
	 * @return true:成功;false:失败
	 */
	public boolean updateQuan(ContentValues contentValues, String selection, String[] selectionArgs) {
		boolean flag = false;
		int count = database.update(Constants.TABLE_NAME, contentValues, selection, selectionArgs);
		flag = count > 0 ? true : false;
		return flag;
	}

	/**
	 * 查询符合条件的所有数据
	 * 
	 * @param selection
	 * @param selectionArgs
	 * @return List
	 */
	public List<QuanEntity> getQuanList(String selection, String[] selectionArgs) {
		List<QuanEntity> quanList = new ArrayList<QuanEntity>();
		Cursor cursor = database.query(Constants.TABLE_NAME, null, selection, selectionArgs, null, null, null);
		if (cursor.moveToFirst()) {
			QuanEntity quanEntity;
			do {
				quanEntity = new QuanEntity();
				quanEntity.setId(cursor.getString(cursor.getColumnIndex(Constants.TABLE_QUAN_COLUMN1)));
				quanEntity.setName(cursor.getString(cursor.getColumnIndex(Constants.TABLE_QUAN_COLUMN2)));
				quanList.add(quanEntity);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		return quanList;
	}

	/**
	 * 清除表中所有数据
	 * 
	 * @return
	 */
	public void clearQuan() {
		String sql1 = "DELETE FROM " + Constants.TABLE_NAME;
		String sql2 = "update sqlite_sequence set seq=0 where name='" + Constants.TABLE_NAME + "'";
		database.execSQL(sql1);
		database.execSQL(sql2);
	}
}
