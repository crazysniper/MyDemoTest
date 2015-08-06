package com.gftest.zxingscanner;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity implements OnClickListener {

	private Button btn1;
	private TextView result;
	private ImageView resultBitmap;

	private static final int SCANNER_REQUEST_CODE = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		btn1 = (Button) findViewById(R.id.btn1);
		result = (TextView) findViewById(R.id.result);
		resultBitmap = (ImageView) findViewById(R.id.resultBitmap);

		btn1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn1:
			intent = new Intent(MainActivity.this, CodeMainActivity.class);
			intent.putExtra("key", "value");
			break;

		default:
			break;
		}
		startActivityForResult(intent, SCANNER_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SCANNER_REQUEST_CODE && resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();

		}
	}

}
