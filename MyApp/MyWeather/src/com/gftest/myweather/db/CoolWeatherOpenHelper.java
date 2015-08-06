package com.gftest.myweather.db;

import com.gftest.myweather.constant.Constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CoolWeatherOpenHelper extends SQLiteOpenHelper {

	/**
	 * Province表建表语句
	 */
	public static final String CREATE_PROVINCE = "create table " + Constants.TABLE_PROVINCE + "(" + Constants.TABLE_PROVINCE_KEY1 + " integer primary key autoincrement," + Constants.TABLE_PROVINCE_KEY2 + " text," + Constants.TABLE_PROVINCE_KEY3 + " text)";
	/**
	 * City表建表语句
	 */
	public static final String CREATE_CITY = "create table " + Constants.TABLE_CITY + "(" + Constants.TABLE_CITY_KEY1 + " integer primary key autoincrement," + Constants.TABLE_CITY_KEY2 + " text," + Constants.TABLE_CITY_KEY3 + " text," + Constants.TABLE_CITY_KEY4 + " integer)";
	/**
	 * Country表建表语句
	 */
	public static final String CREATE_COUNTRY = "create table " + Constants.TABLE_COUNTRY + "(" + Constants.TABLE_COUNTRY_KEY1 + " integer primary key autoincrement," + Constants.TABLE_COUNTRY_KEY2 + " text," + Constants.TABLE_COUNTRY_KEY3 + " text," + Constants.TABLE_COUNTRY_KEY4 + " integer)";

	public CoolWeatherOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("省:" + CREATE_PROVINCE);
		System.out.println("市:" + CREATE_CITY);
		System.out.println("县:" + CREATE_COUNTRY);
		db.execSQL(CREATE_PROVINCE);
		db.execSQL(CREATE_CITY);
		db.execSQL(CREATE_COUNTRY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
