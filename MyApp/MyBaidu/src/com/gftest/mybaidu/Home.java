package com.gftest.mybaidu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;

public class Home extends BaseActivity implements OnClickListener {
	private MapView mMapView = null;
	private BaiduMap baiduMap;
	private Marker marker;

	private Button traffic, satellite;
	private Button addLocation, clear, reset;
	private Button search, location;
	private EditText search_content;

	/** 默认没有开启交通图 */
	private boolean isTraffic = false;
	/** 默认没有开启交通图 */
	private boolean isSatellite = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_home);
		initView();
	}

	private void initView() {
		mMapView = (MapView) findViewById(R.id.bmapView);
		traffic = (Button) findViewById(R.id.traffic);
		satellite = (Button) findViewById(R.id.satellite);
		addLocation = (Button) findViewById(R.id.addLocation);
		clear = (Button) findViewById(R.id.clear);
		reset = (Button) findViewById(R.id.reset);
		search = (Button) findViewById(R.id.search);
		location = (Button) findViewById(R.id.location);
		search_content = (EditText) findViewById(R.id.search_content);

		baiduMap = mMapView.getMap();

		traffic.setOnClickListener(this);
		satellite.setOnClickListener(this);
		addLocation.setOnClickListener(this);
		clear.setOnClickListener(this);
		reset.setOnClickListener(this);
		search.setOnClickListener(this);
		location.setOnClickListener(this);

		search_content.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {
					search.setEnabled(true);
					search.setTextColor(Color.GREEN);
				} else {
					search.setEnabled(false);
					search.setTextColor(Color.RED);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.traffic:
			changeTraffic();
			break;
		case R.id.satellite:
			changeSatellite();
			break;
		case R.id.addLocation:// 定义Maker坐标点
			LatLng point = new LatLng(39.963175, 116.400244);
			// 构建Marker图标
			BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher);
			// 构建MarkerOption，用于在地图上添加Marker
			// 设置marker的位置,marker图标,手势拖拽
			OverlayOptions option = new MarkerOptions().position(point).icon(bitmap).draggable(true);

			// 在地图上添加Marker，并显示
			marker = (Marker) baiduMap.addOverlay(option);

			addLocation.setEnabled(false);
			clear.setEnabled(true);
			reset.setEnabled(true);
			break;
		case R.id.clear:
			marker.remove();
			addLocation.setEnabled(true);
			clear.setEnabled(false);
			reset.setEnabled(false);
			break;
		case R.id.reset:
			marker.remove();
			addLocation.setEnabled(true);
			clear.setEnabled(false);
			reset.setEnabled(false);
			break;
		case R.id.search:
			String content = search_content.getText().toString().trim();
			if (TextUtils.isEmpty(content)) {
				Toast.makeText(Home.this, "请输入检索地址", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(Home.this, "还未实现", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.location:
			startActivity(new Intent(Home.this, Location.class));
			break;
		default:
			break;
		}
	}

	/**
	 * 切换交通图
	 */
	private void changeTraffic() {
		if (isTraffic) {
			baiduMap.setTrafficEnabled(false);// 关闭交通图
			isTraffic = false;
			traffic.setText("开通交通图");
		} else {
			baiduMap.setTrafficEnabled(true);// 开启交通图
			isTraffic = true;
			traffic.setText("关闭交通图");
		}
	}

	/**
	 * 切换卫星地图
	 */
	private void changeSatellite() {
		if (isSatellite) {
			baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);// 关闭卫星地图
			isSatellite = false;
			satellite.setText("开通卫星地图");
		} else {
			baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);// 开启卫星地图
			isSatellite = true;
			satellite.setText("关闭卫星地图");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	private class MyPoiOverlay extends PoiOverlay {
		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			return true;
		}
	}
}
