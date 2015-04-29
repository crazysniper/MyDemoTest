package com.example.autocompletedeom;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.TextView;
import android.widget.Toast;

public class SearchView_1 extends Activity {
	private SearchView searchView;
	private Context context;
	private MyHandler handler;

	// schedule executor
	private ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(10);
	private String currentSearchTip;
	private TextView searchInfo;

	private InputMethodManager inputMethodManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_view_demo);
		
		context=SearchView_1.this;
		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		handler = new MyHandler();
		searchInfo = (TextView) findViewById(R.id.search_info);

		// set title style
		ActionBar bar = getActionBar();
		bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM);
		setTitle(" ");
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View customActionBarView = inflater.inflate(R.layout.search_view_demo_title, null);

		searchView = (SearchView) customActionBarView.findViewById(R.id.search_view);
		searchView.setVisibility(View.VISIBLE);
		searchView.setIconifiedByDefault(true);
		searchView.setIconified(false);
		if (Build.VERSION.SDK_INT >= 14) {
			// when edittest is empty, don't show cancal button
			searchView.onActionViewExpanded();
		}
		// 表示点击取消按钮listener，默认点击搜索输入框
		searchView.setOnCloseListener(new OnCloseListener() {
			// 编辑框内容为空点击取消的x按钮，编辑框收缩，可在onClose中返回true
			@Override
			public boolean onClose() {
				// to avoid click x button and the edittext hidden
				return true;
			}
		});
		// 表示输入框文字listener，包括public boolean
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			// 开始搜索listener
			public boolean onQueryTextSubmit(String query) {
				searchInfo.setText("search submit result");
				hideSoftInput();
				Toast.makeText(context, "begin search", Toast.LENGTH_SHORT).show();
				return true;
			}

			// 输入框内容变化listener
			// 输入框内容每次变化时将一个数据获取线程SearchTipThread放到ScheduledExecutorService中，
			// 500ms后执行，在线程执行时判断当前输入框内容和要搜索内容，若相等则继续执行，否则直接返回，
			// 避免不必要的数据获取和多个搜索提示同时出现。
			public boolean onQueryTextChange(String newText) {
				if (newText != null && newText.length() > 0) {
					currentSearchTip = newText;
					showSearchTip(newText);
				}
				return true;
			}
		});
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		bar.setCustomView(customActionBarView, params);

		// 表示默认输入法弹出
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
	}

	public void showSearchTip(String newText) {
		schedule(new SearchTipThread(newText), 500);
	}

	class SearchTipThread implements Runnable {
		String newText;

		public SearchTipThread(String newText) {
			this.newText = newText;
		}

		public void run() {
			if (newText != null && newText.equals(currentSearchTip)) {
				handler.sendMessage(handler.obtainMessage(1, newText + " search tip"));
			}
		}
	}

	public ScheduledFuture<?> schedule(Runnable command, long delayTimeMills) {
		return scheduledExecutor.schedule(command, delayTimeMills, TimeUnit.MILLISECONDS);
	}

	@SuppressLint("HandlerLeak")
	private class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				searchInfo.setText((String) msg.obj);
				Toast.makeText(context, (String) msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}
		}
	}

	private void hideSoftInput() {
		if (inputMethodManager != null) {
			View v = SearchView_1.this.getCurrentFocus();
			if (v == null) {
				return;
			}

			inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			searchView.clearFocus();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			onBackPressed();
			return true;
		}
		}
		return false;
	}
}
