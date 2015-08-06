package com.gftest.myweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gftest.myweather.base.BaseActivity;
import com.gftest.myweather.service.AutoUpdateService;
import com.gftest.myweather.utils.HttpCallbackListener;
import com.gftest.myweather.utils.HttpUtils;
import com.gftest.myweather.utils.Utility;

public class WeatherActivity extends BaseActivity implements OnClickListener {

	private TextView city_name, publish_text, current_date, weather_desp, temp1, temp2;
	private LinearLayout weather_info_layout, adLayout;
	private Button switch_city, refresh_weather;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		initView();
	}

	private void initView() {
		switch_city = (Button) findViewById(R.id.switch_city);
		refresh_weather = (Button) findViewById(R.id.refresh_weather);
		city_name = (TextView) findViewById(R.id.city_name);// 用于显示城市名
		publish_text = (TextView) findViewById(R.id.publish_text);// 用于显示发布时间
		current_date = (TextView) findViewById(R.id.current_date);// 用于显示当前日期
		weather_desp = (TextView) findViewById(R.id.weather_desp);// 用于显示天气描述信息
		temp1 = (TextView) findViewById(R.id.temp1);// 用于显示气温1
		temp2 = (TextView) findViewById(R.id.temp2);// 用于显示气温2
		weather_info_layout = (LinearLayout) findViewById(R.id.weather_info_layout);
		adLayout = (LinearLayout) findViewById(R.id.adLayout);

		String country_code = getIntent().getStringExtra("country_code");
		System.out.println("country_code=" + country_code);
		if (!TextUtils.isEmpty(country_code)) {// 有县级代号时就去查询天气
			publish_text.setText("同步中...");
			weather_info_layout.setVisibility(View.INVISIBLE);
			city_name.setVisibility(View.INVISIBLE);
			queryWeatherCode(country_code);
		} else {// 没有县级代号时就直接显示本地天气
			showWeather();
		}

		switch_city.setOnClickListener(this);
		refresh_weather.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.switch_city:
			Intent intent = new Intent(this, ChooseAreaActivity.class);
			intent.putExtra("from_weather_activity", true);
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.refresh_weather:
			publish_text.setText("同步中...");
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
			String weatherCode = sharedPreferences.getString("weather_code", "");
			if (!TextUtils.isEmpty(weatherCode)) {
				queryWeatherInfo(weatherCode);
			}
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 100) {
			switch (resultCode) {
			case ChooseAreaActivity.RESULT_CODE1:

				break;
			case ChooseAreaActivity.RESULT_CODE2:
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 查询县级代号所对应的天气代号。
	 */
	private void queryWeatherCode(String country_code) {
		String address = "http://www.weather.com.cn/data/list3/city" + country_code + ".xml";
		System.out.println("查询县级代号所对应的天气代号address=" + address);
		queryFromServer(address, "countryCode");
	}

	/**
	 * 查询天气代号所对应的天气。
	 */
	private void queryWeatherInfo(String weatherCode) {
		String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
		System.out.println("查询天气代号所对应的天气。address=" + address);
		queryFromServer(address, "weatherCode");
	}

	/**
	 * 根据传入的地址和类型去向服务器查询天气代号或者天气信息。
	 */
	private void queryFromServer(String address, final String type) {
		HttpUtils.sendHttpRequest(address, new HttpCallbackListener() {
			@Override
			public void onFinished(String respones) {
				if ("countryCode".equals(type)) {
					// 从服务器返回的数据中解析出天气代号
					String[] array = respones.split("\\|");
					if (array != null && array.length == 2) {
						String weatherCode = array[1];
						queryWeatherInfo(weatherCode);
					}
				} else if ("weatherCode".equals(type)) {
					Utility.handleWeatherResponse(WeatherActivity.this, respones);
					runOnUiThread(new Runnable() {
						public void run() {
							showWeather();
						}
					});
				}
			}

			@Override
			public void onError(Exception exception) {
				runOnUiThread(new Runnable() {
					public void run() {
						publish_text.setText("同步失败");
					}
				});
			}
		});
	}

	/**
	 * 从SharedPreferences文件中读取存储的天气信息，并显示到界面上。
	 */
	private void showWeather() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		city_name.setText(sharedPreferences.getString("city_name", ""));
		temp1.setText(sharedPreferences.getString("temp1", ""));
		temp2.setText(sharedPreferences.getString("temp2", ""));
		weather_desp.setText(sharedPreferences.getString("weather_desp", ""));
		publish_text.setText("今天" + sharedPreferences.getString("publish_time", "") + "发布");
		current_date.setText(sharedPreferences.getString("current_date", ""));
		weather_info_layout.setVisibility(View.VISIBLE);
		city_name.setVisibility(View.VISIBLE);

		// 激活AutoUpdateService这个服务
		// 这样只要一旦选中了某个城市并成功更新天气之后，AutoUpdateService就会一直在后台运行，并保证每8小时更新一次天气
		Intent service = new Intent(WeatherActivity.this, AutoUpdateService.class);
		startService(service);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
	}
}
