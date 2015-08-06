package com.gftest.myweather.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gftest.myweather.constant.Constants;
import com.gftest.myweather.model.City;
import com.gftest.myweather.model.Country;
import com.gftest.myweather.model.Province;

public class CoolWeatherDB {

	private static CoolWeatherDB coolWeatherDB;
	private SQLiteDatabase db;

	private CoolWeatherDB(String... pStrings) {

	}

	/**
	 * 将构造方法私有化
	 * 
	 * @param context
	 */
	private CoolWeatherDB(Context context) {
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, Constants.DB_NAME, null, Constants.VERSION);
		db = dbHelper.getWritableDatabase();
	}

	public synchronized static CoolWeatherDB getInstance(Context context) {
		if (coolWeatherDB == null) {
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}

	/**
	 * 将Province实例存储到数据库
	 * 
	 * @param province
	 */
	public void saveProvince(Province province) {
		if (province != null) {
			ContentValues contentValues = new ContentValues();
			contentValues.put(Constants.TABLE_PROVINCE_KEY2, province.getProvinceName());
			contentValues.put(Constants.TABLE_PROVINCE_KEY3, province.getProvinceCode());
			long result = db.insert(Constants.TABLE_PROVINCE, null, contentValues);// 返回-1表示存储失败
			System.out.println("保存省的返回值=" + result);
		}
	}

	/**
	 * 从数据库读取全国所有的省份信息。
	 */
	public List<Province> loadProvinces() {
		List<Province> provinceList = new ArrayList<Province>();
		Cursor cursor = db.query(Constants.TABLE_PROVINCE, null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			System.out.println("cursor.moveToFirst()=true");
			Province province;
			do {
				province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex(Constants.TABLE_PROVINCE_KEY1)));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex(Constants.TABLE_PROVINCE_KEY2)));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex(Constants.TABLE_PROVINCE_KEY3)));
				provinceList.add(province);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		return provinceList;
	}

	/**
	 * 将City实例存储到数据库。
	 */
	public void saveCity(City city) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(Constants.TABLE_CITY_KEY2, city.getCityName());
		contentValues.put(Constants.TABLE_CITY_KEY3, city.getCityCode());
		contentValues.put(Constants.TABLE_CITY_KEY4, city.getProvinceId());
		long result = db.insert(Constants.TABLE_CITY, null, contentValues);
		System.out.println("保存市的返回值=" + result);
	}

	/**
	 * 从数据库读取某省下所有的城市信息。
	 */
	public List<City> loadCities(int provinceId) {
		List<City> cityList = new ArrayList<City>();
		Cursor cursor = db.query(Constants.TABLE_CITY, null, Constants.TABLE_CITY_KEY4 + "=?", new String[] { String.valueOf(provinceId) }, null, null, null);
		if (cursor.moveToFirst()) {
			City city;
			do {
				city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex(Constants.TABLE_CITY_KEY1)));
				city.setCityName(cursor.getString(cursor.getColumnIndex(Constants.TABLE_CITY_KEY2)));
				city.setCityCode(cursor.getString(cursor.getColumnIndex(Constants.TABLE_CITY_KEY3)));
				city.setProvinceId(provinceId);
				cityList.add(city);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		return cityList;
	}

	/**
	 * 将County实例存储到数据库。
	 */
	public void saveCountry(Country country) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(Constants.TABLE_COUNTRY_KEY2, country.getCountryName());
		contentValues.put(Constants.TABLE_COUNTRY_KEY3, country.getCountryCode());
		contentValues.put(Constants.TABLE_COUNTRY_KEY4, country.getCityId());
		long result = db.insert(Constants.TABLE_COUNTRY, null, contentValues);
		System.out.println("保存县的返回值=" + result);
	}

	public List<Country> loadCountries(int cityId) {
		List<Country> countryList = new ArrayList<Country>();
		Cursor cursor = db.query(Constants.TABLE_COUNTRY, null, Constants.TABLE_COUNTRY_KEY4 + "=?", new String[] { String.valueOf(cityId) }, null, null, null);
		if (cursor.moveToFirst()) {
			Country country;
			do {
				country = new Country();
				country.setId(cursor.getInt(cursor.getColumnIndex(Constants.TABLE_COUNTRY_KEY1)));
				country.setCountryName(cursor.getString(cursor.getColumnIndex(Constants.TABLE_COUNTRY_KEY2)));
				country.setCountryCode(cursor.getString(cursor.getColumnIndex(Constants.TABLE_COUNTRY_KEY3)));
				country.setCityId(cityId);
				countryList.add(country);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		return countryList;
	}
}
