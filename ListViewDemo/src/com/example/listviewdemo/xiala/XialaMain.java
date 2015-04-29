package com.example.listviewdemo.xiala;

import com.example.listviewdemo.R;
import com.example.listviewdemo.xiala.xiala1.Xiala1;
import com.example.listviewdemo.xiala.xiala2.Xiala2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class XialaMain extends Activity implements OnClickListener {

	private Button xiala1, xiala2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiala_main);
		initView();
	}

	private void initView() {
		xiala1 = (Button) findViewById(R.id.xiala1);
		xiala2 = (Button) findViewById(R.id.xiala2);

		xiala1.setOnClickListener(this);
		xiala2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.xiala1:
			startActivity(new Intent(XialaMain.this, Xiala1.class));
			break;
		case R.id.xiala2:
			startActivity(new Intent(XialaMain.this, Xiala2.class));
			break;
		}
	}
}
