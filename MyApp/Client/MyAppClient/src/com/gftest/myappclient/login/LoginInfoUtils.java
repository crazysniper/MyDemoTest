package com.gftest.myappclient.login;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.gftest.myappclient.constants.Constants;
import com.gftest.myappclient.db.AccountDBHelper;
import com.gftest.myappclient.model.DBLoginEntity;

public class LoginInfoUtils {
	private static LoginInfoUtils mHandler;
	private Context context;

	/**
	 * 私有构造函数
	 */
	private LoginInfoUtils(Context context) {
		this.context = context;
	}

	/**
	 * 单例模式——保证对象唯一性
	 * 
	 * @return
	 */
	public static LoginInfoUtils create(Context context) {
		if (mHandler == null) {
			mHandler = new LoginInfoUtils(context);
		}
		return mHandler;
	}

	/**
	 * 获取用户登陆信息
	 * 
	 * @return
	 */
	public List<DBLoginEntity> getLoginInfoObj() {
		List<DBLoginEntity> dbLoginEntityList = new ArrayList<DBLoginEntity>();
		AccountDBHelper dbHelper = new AccountDBHelper(context);
		Cursor cursor = dbHelper.quertAll(Constants.LOGIN_ACCOUNT_DB_NAME);
		if (cursor.moveToFirst()) {
			DBLoginEntity dbLoginEntity;
			do {
				dbLoginEntity = new DBLoginEntity();
				dbLoginEntity.setID(cursor.getInt(cursor.getColumnIndex(Constants.LOGIN_ACCOUNT_DB_KYE1)));
				dbLoginEntity.setUserName(cursor.getString(cursor.getColumnIndex(Constants.LOGIN_ACCOUNT_DB_KYE2)));
				dbLoginEntity.setPassword(cursor.getString(cursor.getColumnIndex(Constants.LOGIN_ACCOUNT_DB_KYE3)));
				dbLoginEntityList.add(dbLoginEntity);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		return dbLoginEntityList;
	}
}
