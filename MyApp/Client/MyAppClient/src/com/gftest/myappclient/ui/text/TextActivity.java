package com.gftest.myappclient.ui.text;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.SeekBar;

import com.gftest.myappclient.R;

public class TextActivity extends ActionBarActivity {

	private SeekBar mSeekBar;
	private MatchTextView mMatchTextView;
	private MatchButton mButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text);

		mMatchTextView = (MatchTextView) findViewById(R.id.mMatchTextView);
		mSeekBar = (SeekBar) findViewById(R.id.mSeekBar);
		mButton = (MatchButton) findViewById(R.id.mButton);

		mSeekBar.setProgress(100);
		mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				mMatchTextView.setProgress(progress * 1f / 100);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MatchDialog matchDialog = new MatchDialog();
				getSupportFragmentManager().beginTransaction().add(matchDialog, "matchDialog").commit();
			}
		});
	}

}
