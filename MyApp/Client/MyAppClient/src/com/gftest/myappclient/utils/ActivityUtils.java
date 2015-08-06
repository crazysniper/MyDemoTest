package com.gftest.myappclient.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * 随时随地退出程序
 * 
 * @author Gao
 * 
 */
public class ActivityUtils {

	public static List<Activity> activityList = new ArrayList<Activity>();

	public static void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public static void removeActivity(Activity activity) {
		activityList.remove(activity);
	}

	public static void finishAll() {
		for (Activity activity : activityList) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}

}
