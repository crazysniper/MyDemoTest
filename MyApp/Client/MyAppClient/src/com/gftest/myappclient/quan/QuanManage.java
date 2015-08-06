package com.gftest.myappclient.quan;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class QuanManage {

	private static QuanManage quanManage;
	private QuanDaoImpl quanDaoImpl;

	/** 默认的圈子列表 */
	public static List<QuanEntity> defaultQuanList = new ArrayList<QuanEntity>();

	private QuanManage(String... params) {
	}

	private QuanManage(Context context) {
		quanDaoImpl = QuanDaoImpl.getInstance(context);
	}

	public synchronized static QuanManage getInstance(Context context) {
		if (quanManage == null) {
			quanManage = new QuanManage(context);
		}
		return quanManage;
	}

	static {
		QuanEntity quanEntity;
		quanEntity = new QuanEntity("10001", "一", 1);
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("1", "二", 2);
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("2", "三", 3);
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("3", "四", 4);
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("4", "五", 5);
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("5", "六", 6);
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("6", "7", 7);
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("7", "8", 8);
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("8", "9", 9);
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("9", "10", 10);
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("10", "11", 11);
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("11", "12", 12);
		defaultQuanList.add(quanEntity);
	}

	public List<QuanEntity> getQuanList() {
		List<QuanEntity> quanList = quanDaoImpl.getQuanList(null, null);
		if (quanList == null || quanList.size() == 0) {
			initDefaultQuan();
			return defaultQuanList;
		}
		return quanList;
	}

	/**
	 * 初始化圈子数据
	 */
	private void initDefaultQuan() {
		deleteAllQuan();
		saveDefaultQuan(defaultQuanList);
	}

	/**
	 * 清除数据
	 */
	private void deleteAllQuan() {
		quanDaoImpl.clearQuan();
	}

	/**
	 * 保存默认数据
	 * 
	 * @param quanList
	 */
	private void saveDefaultQuan(List<QuanEntity> quanList) {
		for (QuanEntity quanEntity : quanList) {
			quanDaoImpl.addQuan(quanEntity);
		}
	}

	public void close() {
		quanDaoImpl.close();
	}

}
