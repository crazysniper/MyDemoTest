package com.gftest.myweather.constant;

public class Constants {
	/** 数据库名 */
	public static final String DB_NAME = "cool_weather";
	/** 数据库版本 */
	public static final int VERSION = 1;

	/** 省的表名 */
	public static final String TABLE_PROVINCE = "Province";
	/** 省的字段 id */
	public static final String TABLE_PROVINCE_KEY1 = "id";
	/** 省的字段 province_name */
	public static final String TABLE_PROVINCE_KEY2 = "province_name";
	/** 省的字段 province_code */
	public static final String TABLE_PROVINCE_KEY3 = "province_code";

	/** 市的表名 */
	public static final String TABLE_CITY = "City";
	/** 市的字段 id */
	public static final String TABLE_CITY_KEY1 = "id";
	/** 市的字段 city_name */
	public static final String TABLE_CITY_KEY2 = "city_name";
	/** 市的字段 city_code */
	public static final String TABLE_CITY_KEY3 = "city_code";
	/** 市的字段 province_id */
	public static final String TABLE_CITY_KEY4 = "province_id";

	/** 县的表名 */
	public static final String TABLE_COUNTRY = "Country";
	/** 县的字段 id */
	public static final String TABLE_COUNTRY_KEY1 = "id";
	/** 县的字段 country_name */
	public static final String TABLE_COUNTRY_KEY2 = "country_name";
	/** 县的字段 country_code */
	public static final String TABLE_COUNTRY_KEY3 = "country_code";
	/** 县的字段 city_id */
	public static final String TABLE_COUNTRY_KEY4 = "city_id";
}
