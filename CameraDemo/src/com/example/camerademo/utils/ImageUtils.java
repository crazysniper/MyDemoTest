package com.example.camerademo.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class ImageUtils {

	/**
	 * 按照大小排序
	 * 
	 * @param s
	 * @return
	 */
	public static String[] sort(String[] s) {
		List<String> list = new ArrayList<String>(s.length);
		for (int i = 0; i < s.length; i++) {
			list.add(s[i]);
		}
		Collections.sort(list);
		return list.toArray(s);
	}

	/**
	 * 旋转图片
	 * 
	 * @param angle
	 *            >0:顺时针；<0:逆时针
	 * @param bitmap
	 *            原图片
	 * @return
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * 裁剪新图片
	 * 
	 * @param bgimage
	 *            源图片资源
	 * @param newWidth
	 *            缩放后宽度
	 * @param newHeight
	 *            缩放后高度
	 * @return
	 */
	public static Bitmap zoomImage(Bitmap bgimage, float newWidth, float newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
		return bitmap;
	}

	/**
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;

		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}
}
