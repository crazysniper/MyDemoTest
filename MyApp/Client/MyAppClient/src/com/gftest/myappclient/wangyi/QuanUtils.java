package com.gftest.myappclient.wangyi;

import java.util.HashMap;
import java.util.Map;

import com.gftest.myappclient.R;

public class QuanUtils {

	private static QuanUtils quanUtils;

	private Map<String, Integer> map = new HashMap<String, Integer>();
	private Map<String, String> map2 = new HashMap<String, String>();

	private QuanUtils() {
		map.put("11", R.drawable.ic_launcher);
		map.put("12", R.drawable.ic_launcher);
		map.put("13", R.drawable.ic_launcher);
		map.put("14", R.drawable.ic_launcher);
		map.put("15", R.drawable.ic_launcher);
		map.put("16", R.drawable.ic_launcher);
		map.put("17", R.drawable.ic_launcher);
		map.put("18", R.drawable.ic_launcher);
		map.put("19", R.drawable.ic_launcher);
		map.put("20", R.drawable.ic_launcher);
		map.put("21", R.drawable.ic_launcher);
		map.put("22", R.drawable.ic_launcher);

		map2.put("11", "推荐");
		map2.put("12", "热门");
		map2.put("13", "体育");
		map2.put("14", "社会");
		map2.put("15", "当地");
		map2.put("16", "国际");
		map2.put("17", "军事");
		map2.put("18", "科技");
		map2.put("19", "娱乐");
		map2.put("20", "电影");
		map2.put("21", "段子");
		map2.put("22", "美食");
	}

	public static QuanUtils getInstance() {
		if (quanUtils == null) {
			quanUtils = new QuanUtils();
		}
		return quanUtils;
	}

	public Integer getId(String qid) {
		return map.get(qid);
	}

	/**
	 * 圈子id所对应的圈子名
	 * 
	 * @param qid
	 * @return
	 */
	public String getName(String qid) {
		return map2.get(qid);
	}

}
