package com.gftest.mybaidu;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class Location extends BaseActivity implements OnClickListener {
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();

	private Button location, online_location, offline_location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		initView();
	}

	private void initView() {
		location = (Button) findViewById(R.id.location);
		online_location = (Button) findViewById(R.id.online_location);
		offline_location = (Button) findViewById(R.id.offline_location);

		// register();

		location.setOnClickListener(this);
		online_location.setOnClickListener(this);
		offline_location.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.location:
			getLoation();
			break;
		case R.id.online_location:
			getOnlineLoation();
			break;
		case R.id.offline_location:
			getOfflineLoation();
			break;
		default:
			break;
		}
	}

	private void register() {
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类,须在主线程中声明。Context需要时全进程有效的context,推荐用getApplicationConext获取全进程有效的context
		mLocationClient.registerLocationListener(myListener); // 注册监听函数,当没有注册监听函数时，无法发起网络请求。

		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式，Hight_Accuracy高精度、Battery_Saving低功耗、Device_Sensors仅设备(GPS)
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02

		/**
		 * 当所设的整数值大于等于1000（ms）时，定位SDK内部使用定时定位模式。调用requestLocation()后，每隔设定的时间，
		 * 定位SDK就会进行一次定位 。如果定位SDK根据定位依据发现位置没有发生变化，就不会发起网络请求，返回上一次定位的结果；
		 * 如果发现位置改变，就进行网络请求进行定位，得到新的定位结果。定时定位时，调用一次requestLocation，会定时监听到定位结果。 <br>
		 * 
		 * 当不设此项，或者所设的整数值小于1000（ms）时，采用一次定位模式。每调用一次requestLocation()，
		 * 定位SDK会发起一次定位。请求定位与监听结果一一对应。 <br>
		 * 
		 * 设定了定时定位后，可以热切换成一次定位，需要重新设置时间间隔小于1000（ms）
		 * 即可。locationClient对象stop后，将不再进行定位
		 * 。如果设定了定时定位模式后，多次调用requestLocation（），则是每隔一段时间进行一次定位
		 * ，同时额外的定位请求也会进行定位，但频率不会超过1秒一次。
		 */
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息。设置是否要返回地址信息，默认为无地址信息。
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向。在网络定位中，获取手机机头所指的方向
		mLocationClient.setLocOption(option);

		mLocationClient.start();
	}

	/**
	 * 在线定位
	 */
	private void getOnlineLoation() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			int resultCode = mLocationClient.requestLocation();// 发起定位，异步获取当前位置。因为是异步的，所以立即返回，不会引起阻塞。定位结果在ReceiveListener的方法OnReceive方法的参数中返回
			System.out.println("发起在线定位请求成功resultCode=" + resultCode);
			/**
			 * 当定位SDK从定位依据判定，位置和上一次没发生变化，而且上一次定位结果可用时，则不会发起网络请求，而是返回上一次的定位结果。
			 * 返回值： 0：正常发起了定位。 <br>
			 * 1：服务没有启动。 <br>
			 * 2：没有监听函数。 <br>
			 * 6：请求间隔过短。 前后两次请求定位时间间隔不能小于1000ms。
			 */
		} else {
			System.out.println("发起在线定位请求失败");
		}
	}

	/**
	 * 离线定位
	 */
	private void getOfflineLoation() {
		/**
		 * 请求离线定位： 离线定位功能：用户请求过得基站定位结果会缓存在本地文件。离线定位结果为缓存结果，精度低于在线的定位结果。
		 * 离线定位结果没有地址信息。
		 */
		if (mLocationClient != null && mLocationClient.isStarted()) {
			int resultCode = mLocationClient.requestLocation();// 发起离线定位，异步获取当前位置。因为是异步的，所以立即返回，不会引起阻塞。定位结果在ReceiveListener的方法OnReceive方法的参数中返回。
			System.out.println("发起离线定位请求成功resultCode=" + resultCode);
			/**
			 * 返回值：<br>
			 * 0：正常发起了定位。<br>
			 * 1：服务没有启动。<br>
			 * 2：没有监听函数。
			 */
		} else {
			System.out.println("发起离线定位请求失败");
		}
	}

	private void getLoation() {
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类,须在主线程中声明。Context需要时全进程有效的context,推荐用getApplicationConext获取全进程有效的context
		mLocationClient.registerLocationListener(myListener); // 注册监听函数,当没有注册监听函数时，无法发起网络请求。

		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式，Hight_Accuracy高精度、Battery_Saving低功耗、Device_Sensors仅设备(GPS)
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02

		/**
		 * 当所设的整数值大于等于1000（ms）时，定位SDK内部使用定时定位模式。调用requestLocation()后，每隔设定的时间，
		 * 定位SDK就会进行一次定位 。如果定位SDK根据定位依据发现位置没有发生变化，就不会发起网络请求，返回上一次定位的结果；
		 * 如果发现位置改变，就进行网络请求进行定位，得到新的定位结果。定时定位时，调用一次requestLocation，会定时监听到定位结果。 <br>
		 * 
		 * 当不设此项，或者所设的整数值小于1000（ms）时，采用一次定位模式。每调用一次requestLocation()，
		 * 定位SDK会发起一次定位。请求定位与监听结果一一对应。 <br>
		 * 
		 * 设定了定时定位后，可以热切换成一次定位，需要重新设置时间间隔小于1000（ms）
		 * 即可。locationClient对象stop后，将不再进行定位
		 * 。如果设定了定时定位模式后，多次调用requestLocation（），则是每隔一段时间进行一次定位
		 * ，同时额外的定位请求也会进行定位，但频率不会超过1秒一次。
		 */
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息。设置是否要返回地址信息，默认为无地址信息。
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向。在网络定位中，获取手机机头所指的方向
		mLocationClient.setLocOption(option);

		mLocationClient.start();

		if (mLocationClient != null && mLocationClient.isStarted()) {
			int resultCode = mLocationClient.requestLocation();// 发起定位，异步获取当前位置。因为是异步的，所以立即返回，不会引起阻塞。定位结果在ReceiveListener的方法OnReceive方法的参数中返回
			System.out.println("resultCode=" + resultCode);
			/**
			 * 当定位SDK从定位依据判定，位置和上一次没发生变化，而且上一次定位结果可用时，则不会发起网络请求，而是返回上一次的定位结果。
			 * 返回值： 0：正常发起了定位。 <br>
			 * 1：服务没有启动。 <br>
			 * 2：没有监听函数。 <br>
			 * 6：请求间隔过短。 前后两次请求定位时间间隔不能小于1000ms。
			 */
		}
	}

	public class MyLocationListener implements BDLocationListener {
		/**
		 * 1.接收异步返回的定位结果，参数是BDLocation类型参数。
		 */
		@Override
		public void onReceiveLocation(BDLocation location) {
			/**
			 * BDLocation类，封装了定位SDK的定位结果，在BDLocationListener的onReceive方法中获取。
			 * 通过该类用户可以获取error code，位置的坐标，精度半径等信息
			 */
			if (location == null) {
				return;
			}
			System.out.println("location.getLatitude()=" + location.getLatitude());// 纬度
			System.out.println("location.getLongitude()=" + location.getLongitude());// 经度
			// 判断是否有定位精度半径,true;false
			System.out.println("location.hasRadius ()=" + location.hasRadius());
			// 获取定位精度半径，单位是米 true:有数据；false:0.0
			System.out.println("location.getRadius()=" + location.getRadius());

			/**
			 * 61 ： GPS定位结果<br>
			 * 62 ： 扫描整合定位依据失败。此时定位结果无效。<br>
			 * 63 ： 网络异常，没有成功向服务器发起请求。此时定位结果无效。<br>
			 * 65 ： 定位缓存的结果。 <br>
			 * 66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果<br>
			 * 67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果 <br>
			 * 68 ： 网络连接失败时，查找本地离线定位时对应的返回结果 <br>
			 * 161： 表示网络定位结果 <br>
			 * 162~167： 服务端定位失败 <br>
			 * 502：key参数错误<br>
			 * 505：key不存在或者非法 <br>
			 * 601：key服务被开发者自己禁用<br>
			 * 602：key mcode不匹配 <br>
			 * 501～700：key验证失败
			 */
			System.out.println("location.getLocType()=" + location.getLocType());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
				System.out.println("GPS定位");
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
				System.out.println("网络定位");
				// 江苏省南京市玄武区珠江路600号
				System.out.println("location.getAddrStr()=" + location.getAddrStr());// 获取反地理编码，只有使用网络定位的情况下，才能获取当前位置的反地理编码描述。
				// 卫星数 -1
				System.out.println("location.getSatelliteNumber()=" + location.getSatelliteNumber());
				// 获取省份信息 江苏省
				System.out.println("location.getProvince ()=" + location.getProvince());
				// 获取城市信息 南京市
				System.out.println("location.getCity ()=" + location.getCity());
				// 获取区县信息 玄武区
				System.out.println("location.getDistrict ()=" + location.getDistrict());
			}
			if (mLocationClient.isStarted()) {
				mLocationClient.stop();// 调用stop之后，设置的参数LocationClientOption仍然保留
			}
		}
	}
}
