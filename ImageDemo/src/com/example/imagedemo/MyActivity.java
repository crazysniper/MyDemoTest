package com.example.imagedemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Gallery;

public class MyActivity extends Activity {
	public static HashMap<String, Bitmap> imagesCache = new HashMap<String, Bitmap>(); // 图片缓存
	private Gallery images_ga;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.url_connection_image);
		init();

	}

	private void init() {
		Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		imagesCache.put("background_non_load", image); // 设置缓存中默认的图片

		images_ga = (Gallery) findViewById(R.id.image_wall_gallery);
		List<String> urls = new ArrayList<String>(); // 图片地址List
		// 奶茶MM的图片，嘻嘻
		urls.add("http://fujian.xabbs.com/forum/201109/02/160646nn9hjjiimixvkxhe.jpg");
		urls.add("http://img1.cache.netease.com/catchpic/A/A9/A9D98040B397C366AE93E67871346561.jpg");
		urls.add("http://new.aliyiyao.com/UpFiles/Image/2011/01/13/nc_129393721364387442.jpg");
		urls.add("http://pic.viewpics.cn/2011/07/03/naichaMMzhangzetianzuixinzhaopian/18.jpg");
		urls.add("http://i1.sinaimg.cn/ent/m/c/2010-01-18/U1819P28T3D2847679F326DT20100118115712.jpg");
		urls.add("http://comic.sinaimg.cn/2011/0824/U5237P1157DT20110824161051.jpg");
		ImageAdapter imageAdapter = new ImageAdapter(urls, this);
		images_ga.setAdapter(imageAdapter);

	}

}