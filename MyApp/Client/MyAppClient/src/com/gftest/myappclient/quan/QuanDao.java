package com.gftest.myappclient.quan;

import java.util.List;

import android.content.ContentValues;

public interface QuanDao {
	/**
	 * 添加数据
	 * 
	 * @param quanEntity
	 * @return true:成功;false:失败
	 */
	public boolean addQuan(QuanEntity quanEntity);

	/**
	 * 删除数据
	 * 
	 * @param selection
	 * @param selectionArgs
	 * @return true:成功;false:失败
	 */
	public boolean deleteQuan(String selection, String[] selectionArgs);

	/**
	 * 更新数据
	 * 
	 * @param contentValues
	 * @param selection
	 * @param selectionArgs
	 * @return true:成功;false:失败
	 */
	public boolean updateQuan(ContentValues contentValues, String selection, String[] selectionArgs);

	/**
	 * 查询符合条件的所有数据
	 * 
	 * @param selection
	 * @param selectionArgs
	 * @return List
	 */
	public List<QuanEntity> getQuanList(String selection, String[] selectionArgs);

	/**
	 * 清除表中所有数据
	 * 
	 * @return
	 */
	public void clearQuan();
}
