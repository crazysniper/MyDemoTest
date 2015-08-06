package com.gftest.myappclient.wangyi;

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
		quanEntity = new QuanEntity("11", "推荐");
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("12", "热门");
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("13", "体育");
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("14", "社会");
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("15", "当地");
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("16", "国际");
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("17", "军事");
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("18", "科技");
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("19", "娱乐");
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("20", "电影");
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("21", "段子");
		defaultQuanList.add(quanEntity);
		quanEntity = new QuanEntity("22", "美食");
		defaultQuanList.add(quanEntity);
	}

	/**
	 * 保存数据
	 * 
	 * @param quanList
	 */
	public void saveDefaultQuan(List<QuanEntity> quanList) {
		for (QuanEntity quanEntity : quanList) {
			quanDaoImpl.addQuan(quanEntity);
		}
	}

	/**
	 * 获取数据
	 * 
	 * @param quanList
	 */
	public List<QuanEntity> getQuanList() {
		List<QuanEntity> quanList = quanDaoImpl.getQuanList(null, null);
		if (quanList == null || quanList.size() == 0) {
			initDefaultQuan();
			return defaultQuanList;
		}
		return quanList;
	}

	/**
	 * 清除数据
	 */
	public void deleteAllQuan() {
		quanDaoImpl.clearQuan();
	}

	/**
	 * 初始化圈子数据
	 */
	private void initDefaultQuan() {
		deleteAllQuan();
		saveDefaultQuan(defaultQuanList);
	}

}
