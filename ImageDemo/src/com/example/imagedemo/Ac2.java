package com.example.imagedemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.imagedemo.adapter.PhotoWallAdapter;
import com.example.imagedemo.utils.Images;

/**
 * http://blog.csdn.net/guolin_blog/article/details/9526203#comments
 * 
 * @author guolin
 * 
 */
public class Ac2 extends Activity {

	/**
	 * 用于展示照片墙的GridView
	 */
	private GridView mPhotoWall;

	/**
	 * GridView的适配器
	 */
	private PhotoWallAdapter adapter;

	private List<String> urlList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_2);
		mPhotoWall = (GridView) findViewById(R.id.photo_wall);
		int length = Images.imageThumbUrls.length;
		for (int i = 0; i < length; i++) {
			urlList.add(Images.imageThumbUrls[i]);
		}

		adapter = new PhotoWallAdapter(this, 0, Images.imageThumbUrls, mPhotoWall);
		mPhotoWall.setAdapter(adapter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 退出程序时结束所有的下载任务
		adapter.cancelAllTasks();
	}

}