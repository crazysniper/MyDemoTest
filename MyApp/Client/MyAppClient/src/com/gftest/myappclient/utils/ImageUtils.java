package com.gftest.myappclient.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class ImageUtils {
	/**
	 * 圆形头像
	 * 
	 * @param bitmap
	 * @param r
	 * @param flag
	 *            true:设置r起作用，否则r默认为10
	 * @return
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int r, boolean flag) {
		try {
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
			int pixels = 10;
			if (flag) {
				pixels = bitmap.getWidth() / 2 + r;
			}
			Canvas canvas = new Canvas(output);
			final int color = 0xffffffff;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			final RectF rectF = new RectF(rect);
			final float roundPx = pixels;
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
			return output;
		} catch (Exception e) {
			return bitmap;
		}
	}

	/**
	 * 裁剪新图片
	 * 
	 * @param bm
	 * @param newWidth
	 *            新宽度
	 * @return
	 */
	public static Bitmap zoomImg(Bitmap bm, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleWidth);
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
		return newbm;
	}

	/**
	 * 加载本地图片
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitmap(Context context, int resId) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		InputStream inputStream = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(inputStream, null, options);
	}

	/**
	 * 得到图片字节流 数组大小
	 */
	public static byte[] readStream(InputStream inputStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inputStream.close();
		return outStream.toByteArray();
	}
}
