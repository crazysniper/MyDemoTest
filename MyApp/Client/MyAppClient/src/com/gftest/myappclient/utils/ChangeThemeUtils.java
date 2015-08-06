package com.gftest.myappclient.utils;

import android.app.Activity;
import android.content.Intent;

import com.gftest.myappclient.R;

public class ChangeThemeUtils {
	private static int sTheme;

	public final static int THEME_WHITE = 0;
	public final static int THEME_BLACK = 1;

	/**
	 * Set the theme of the Activity, and restart it by creating a new Activity
	 * of the same type.
	 */
	public static void changeToTheme(Activity activity, int theme) {
		sTheme = theme;
		activity.finish();

		activity.startActivity(new Intent(activity, activity.getClass()));
	}

	/** Set the theme of the activity, according to the configuration. */
	public static int onActivityCreateSetTheme(Activity activity) {
		switch (sTheme) {
		default:
		case 0:
			activity.setTheme(R.style.MyThemeDefault);
			return 1;
		case 1:
			activity.setTheme(R.style.MyThemeNight);
			return 0;
		}
	}
}
