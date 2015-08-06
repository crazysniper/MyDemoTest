package com.gftest.myweather;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gftest.myweather.base.BaseActivity;
import com.gftest.myweather.db.CoolWeatherDB;
import com.gftest.myweather.model.City;
import com.gftest.myweather.model.Country;
import com.gftest.myweather.model.Province;
import com.gftest.myweather.utils.HttpCallbackListener;
import com.gftest.myweather.utils.HttpUtils;
import com.gftest.myweather.utils.Utility;

/**
 * 选择城市
 * 
 * @author Gao
 * 
 */
public class ChooseAreaActivity extends BaseActivity implements OnItemClickListener {

	/** 是否从WeatherActivity中跳转过来 */
	private boolean isFromWeatherActivity;

	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTRY = 2;

	private ProgressDialog progressDialog;
	private TextView title;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private CoolWeatherDB coolWeatherDB;
	private List<String> dataList = new ArrayList<String>();

	/** 省列表 */
	private List<Province> provinceList = new ArrayList<Province>();
	/** 市列表 */
	private List<City> cityList = new ArrayList<City>();
	/** 县列表 */
	private List<Country> countryList = new ArrayList<Country>();

	/** 选中的省 */
	private Province selectedProvince;
	/** 选中的市 */
	private City selectedCity;

	/** 当前选中的级别 */
	private int currentLevel;

	public static final int RESULT_CODE1 = 100, RESULT_CODE2 = 101;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置没有标题
		setContentView(R.layout.activity_choose_area);
		initView();
	}

	private void initView() {
		title = (TextView) findViewById(R.id.title_text);
		listView = (ListView) findViewById(R.id.list_view);
		adapter = new ArrayAdapter<String>(ChooseAreaActivity.this, android.R.layout.simple_list_item_1, dataList);
		listView.setAdapter(adapter);

		isFromWeatherActivity = getIntent().getBooleanExtra("from_weather_activity", false);

		coolWeatherDB = CoolWeatherDB.getInstance(ChooseAreaActivity.this);

		listView.setOnItemClickListener(this);

		queryProvinces();// 加载省级数据
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (currentLevel == LEVEL_PROVINCE) {
			selectedProvince = provinceList.get(arg2);
			queryCities();
		} else if (currentLevel == LEVEL_CITY) {
			selectedCity = cityList.get(arg2);
			queryCounties();
		} else if (currentLevel == LEVEL_COUNTRY) {
			String countryCode = countryList.get(arg2).getCountryCode();
			Intent intent = new Intent();
			intent.putExtra("country_code", countryCode);
			setResult(RESULT_CODE2, intent);
			overridePendingTransition(R.anim.right_in, R.anim.right_out);
			ChooseAreaActivity.this.finish();
		}
	}

	/**
	 * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询。
	 */
	private void queryProvinces() {
		provinceList = coolWeatherDB.loadProvinces();
		title.setText("中国");
		if (provinceList.size() > 0) {
			dataList.clear();
			for (Province province : provinceList) {
				dataList.add(province.getProvinceName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			currentLevel = LEVEL_PROVINCE;
		} else {
			queryFromServer(null, "province");
		}
	}

	/**
	 * 查询选中省内所有的市，优先从数据库查询，如果没有查询到再去服务器上查询。
	 */
	private void queryCities() {
		cityList = coolWeatherDB.loadCities(selectedProvince.getId());
		if (cityList.size() > 0) {
			dataList.clear();
			for (City city : cityList) {
				dataList.add(city.getCityName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			title.setText(selectedProvince.getProvinceName());
			currentLevel = LEVEL_CITY;
		} else {
			queryFromServer(selectedProvince.getProvinceCode(), "city");
		}
	}

	/**
	 * 查询选中市内所有的县，优先从数据库查询，如果没有查询到再去服务器上查询。
	 */
	private void queryCounties() {
		countryList = coolWeatherDB.loadCountries(selectedCity.getId());
		if (countryList.size() > 0) {
			dataList.clear();
			for (Country country : countryList) {
				dataList.add(country.getCountryName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			title.setText(selectedCity.getCityName());
			currentLevel = LEVEL_COUNTRY;
		} else {
			queryFromServer(selectedCity.getCityCode(), "country");
		}
	}

	/**
	 * 根据传入的代号和类型从服务器上查询省市县数据。
	 */
	private void queryFromServer(String code, final String type) {
		String address;
		if (!TextUtils.isEmpty(code)) {
			address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
		} else {
			address = "http://www.weather.com.cn/data/list3/city.xml";
		}
		System.out.println("address=" + address);
		System.out.println("code=" + code);
		System.out.println("type=" + type);
		showProgressDialog();
		HttpUtils.sendHttpRequest(address, new HttpCallbackListener() {
			@Override
			public void onFinished(String response) {
				System.out.println("返回值=" + response);
				boolean result = false;
				if ("province".equals(type)) {
					result = Utility.handleProvincesResponse(coolWeatherDB, response);
				} else if ("city".equals(type)) {
					result = Utility.handleCitiesResponse(coolWeatherDB, response, selectedProvince.getId());
				} else if ("country".equals(type)) {
					result = Utility.handleCountriesResponse(coolWeatherDB, response, selectedCity.getId());
				}
				if (result) {
					runOnUiThread(new Runnable() {// 通过runOnUiThread()方法回到主线程处理逻辑
						public void run() {
							closeProgressDialog();
							if ("province".equals(type)) {
								queryProvinces();
							} else if ("city".equals(type)) {
								queryCities();
							} else if ("country".equals(type)) {
								queryCounties();
							}
						}
					});
				}
			}

			@Override
			public void onError(Exception exception) {
				runOnUiThread(new Runnable() {
					public void run() {
						closeProgressDialog();
						Toast.makeText(ChooseAreaActivity.this, "加载失败", Toast.LENGTH_LONG).show();
					}
				});
			}
		});
	}

	/**
	 * 显示进度对话框
	 */
	private void showProgressDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(ChooseAreaActivity.this);
			progressDialog.setMessage("正在加载...");
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}

	/**
	 * 关闭进度对话框
	 */
	private void closeProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	/**
	 * 捕获Back按键，根据当前的级别来判断，此时应该返回市列表、省列表、还是直接退出。
	 */
	@Override
	public void onBackPressed() {
		if (currentLevel == LEVEL_COUNTRY) {
			queryCities();
		} else if (currentLevel == LEVEL_CITY) {
			queryProvinces();
		} else {
			if (isFromWeatherActivity) {
				Intent intent = new Intent();
				setResult(RESULT_CODE1, intent);
				overridePendingTransition(R.anim.right_in, R.anim.right_out);
			}
			ChooseAreaActivity.this.finish();
		}
	}
}
