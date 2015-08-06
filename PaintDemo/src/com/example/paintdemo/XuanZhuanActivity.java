package com.example.paintdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class XuanZhuanActivity extends Activity {

	private ImageView img2;
	private TextView tv1, tv2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xuanzhuan);
		initView();
	}

	private void initView() {
		img2 = (ImageView) findViewById(R.id.img2);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);

		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.attention);
		System.out.println("旋转之前的高度=" + bitmap.getHeight());
		System.out.println("旋转之前的宽度=" + bitmap.getWidth());
		System.out.println("---------");
		tv1.setText("旋转之前的高度=" + bitmap.getHeight() + "  " + "旋转之前的宽度=" + bitmap.getWidth());

		Matrix m = new Matrix();
		m.setRotate(90, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);// 旋转
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
		System.out.println("旋转之后的高度=" + bitmap.getHeight());
		System.out.println("旋转之后的宽度=" + bitmap.getWidth());
		tv2.setText("旋转之后的高度=" + bitmap.getHeight() + "  " + "旋转之后的宽度=" + bitmap.getWidth());
		img2.setImageBitmap(bitmap);
	}
}
