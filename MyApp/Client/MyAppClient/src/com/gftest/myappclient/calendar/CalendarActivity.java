package com.gftest.myappclient.calendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gftest.myappclient.R;

public class CalendarActivity extends Activity {
	/** Called when the activity is first created. */
	private CalendarAdapter calV = null;
	private GridView gridView = null;
	private TextView topText = null;
	private static int jumpMonth = 0; // 每次点击按钮，增加或减去一个月,默认为0（即显示当前月）
	private static int jumpYear = 0; // 点击超过一年，则增加或者减去一年,默认为0(即当前年)
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	private String currentDate = "";

	// 上一月和下一月的按钮
	TextView previous;
	TextView next;
	LinearLayout main;

	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy MM");
	SimpleDateFormat format2 = new SimpleDateFormat("MMM yyyy", Locale.CHINA);

	@SuppressLint("SimpleDateFormat")
	public CalendarActivity() {
		System.out.println("CalendarActivity");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		currentDate = sdf.format(date);
		year_c = Integer.parseInt(currentDate.split("-")[0]);
		month_c = Integer.parseInt(currentDate.split("-")[1]);
		day_c = Integer.parseInt(currentDate.split("-")[2]);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_calendar);
		System.out.println("onCreate");

		calV = new CalendarAdapter(this, getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);

		previous = (TextView) findViewById(R.id.previous);
		next = (TextView) findViewById(R.id.next);
		main = (LinearLayout) findViewById(R.id.main);
		topText = (TextView) findViewById(R.id.toptext);
		gridView = (GridView) findViewById(R.id.gridView1);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

				String scheduleDay = calV.getDateByClickItem(position).split("\\.")[0]; // 这一天的阳历
				String titleYear = calV.getShowYear();
				String titleMonth = calV.getShowMonth();
				int startPosition = calV.getStartPositon();
				int endPosition = calV.getEndPosition();

				if (position >= startPosition && position <= endPosition) {
					Toast.makeText(CalendarActivity.this, "被点击的日期 : " + titleYear + "年" + titleMonth + "月" + scheduleDay + "日", Toast.LENGTH_LONG).show();
					calV.setCurrentFlag(position);
					calV.notifyDataSetChanged();
				} else if (position < startPosition) {
					getPreviousMonth();
				} else if (position > endPosition) {
					getNextMonth();
				} else {
					Toast.makeText(CalendarActivity.this, "No", Toast.LENGTH_LONG).show();
				}
			}
		});
		gridView.setAdapter(calV);

		addTextToTopTextView(topText);

		previous.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getPreviousMonth();
			}
		});

		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getNextMonth();
			}
		});

	}

	private void getPreviousMonth() {
		jumpMonth--;
		calV = new CalendarAdapter(CalendarActivity.this, getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
		gridView.setAdapter(calV);
		addTextToTopTextView(topText);
	}

	private void getNextMonth() {
		jumpMonth++;
		calV = new CalendarAdapter(CalendarActivity.this, getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
		gridView.setAdapter(calV);
		addTextToTopTextView(topText);
	}

	private void addTextToTopTextView(TextView view) {
		StringBuffer textDate = new StringBuffer();
		String datestr = calV.getShowYear() + " " + calV.getShowMonth();
		// try {
		// textDate.append(format2.format(format1.parse(datestr)));
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		view.setText(datestr);
		view.setTextColor(Color.WHITE);
		view.setTypeface(Typeface.DEFAULT_BOLD);

	}
}