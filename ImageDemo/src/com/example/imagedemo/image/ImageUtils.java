package com.example.imagedemo.image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.media.ExifInterface;
import android.os.Environment;

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
	 * 以最省内存的方式读取本地资源的图片
	 * 
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

	public static String SaveImageToSd(Bitmap bitmap, String dir, String name) {
		String img_uri = null;
		String file_dir = null;
		try {
			File sdCardDir = Environment.getExternalStorageDirectory();
			img_uri = sdCardDir + dir + name;
			file_dir = sdCardDir + dir;

			File file1 = new File(file_dir);
			if (!file1.exists()) {
				file1.mkdir();
			}

			File file = new File(img_uri);

			if (file.exists()) {
				file.delete();
			}

			FileOutputStream out = new FileOutputStream(img_uri);

			// System.out.println("out:"+out);

			if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return img_uri;
	}

	public static Bitmap getImage(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;

		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;

		int scale = 1;
		scale = computeSampleSize(newOpts, -1, 480 * 600);

		// System.out.println("scale:"+scale);

		newOpts.inTempStorage = new byte[100 * 1024];
		newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
		newOpts.inPurgeable = true;
		newOpts.inSampleSize = scale;// 设置缩放比例
		newOpts.inInputShareable = true;
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return bitmap;
		// return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
	}

	public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static ByteArrayInputStream Bitmap2InputStream2(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		ByteArrayInputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			// 方向
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			// System.out.println("orientation=" + orientation);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	public static byte[] getBitmapByte(Bitmap bitmap) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	public static Bitmap getBitmapByPath(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTempStorage = new byte[100 * 1024];
		options.inPreferredConfig = Bitmap.Config.RGB_565;// 指定decode到内存中，手机中所采用的编码，可选值定义在Bitmap.Config中。缺省值是ARGB_8888
		options.inPurgeable = true;// 允许清除
		options.inSampleSize = 2;// 设置decode时的缩放比例
		options.inInputShareable = true;
		Bitmap bm = BitmapFactory.decodeFile(path, options);
		int degree = readPictureDegree(path);
		if (degree != 0) {
			Bitmap bm2 = bm;
			bm = ImageUtils.rotaingImageView(degree, bm2);
			if (bm2 != null && !bm2.isRecycled()) {
				bm2 = null;
			}
		}
		return bm;
	}

}
