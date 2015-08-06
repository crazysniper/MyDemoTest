package com.example.imagedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.example.imagedemo.image3.FileUtils;
import com.example.imagedemo.image3.ImageAdapter3;
import com.example.imagedemo.utils.Images;

/**
 * http://blog.csdn.net/xiaanming/article/details/9825113
 * 
 * @author Administrator
 * 
 */
public class Image3 extends Activity {
	private GridView mGridView;
	private String[] imageThumbUrls = Images.imageThumbUrls;
	private ImageAdapter3 mImageAdapter;
	private FileUtils fileUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image3);
		fileUtils = new FileUtils(this);
		mGridView = (GridView) findViewById(R.id.gridView);
		mImageAdapter = new ImageAdapter3(this, mGridView, imageThumbUrls);
		mGridView.setAdapter(mImageAdapter);
	}

	@Override
	protected void onDestroy() {
		mImageAdapter.cancelTask();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add("删除手机中图片缓存");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			fileUtils.deleteFile();
			Toast.makeText(getApplication(), "清空缓存成功", Toast.LENGTH_SHORT).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
