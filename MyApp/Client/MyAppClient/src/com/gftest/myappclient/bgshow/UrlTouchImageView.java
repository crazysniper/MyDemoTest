package com.gftest.myappclient.bgshow;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.gftest.myappclient.R;
import com.gftest.myappclient.bgshow.InputStreamWrapper.InputStreamProgressListener;
import com.gftest.myappclient.imageloader.ImageLoaderNew;

public class UrlTouchImageView extends RelativeLayout {
	protected ProgressBar mProgressBar;
	protected TouchImageView mImageView;
	protected ImageLoaderNew imageLoader;
	protected Context mContext;

	public UrlTouchImageView(Context ctx) {
		super(ctx);
		mContext = ctx;
		init();

	}

	public UrlTouchImageView(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
		mContext = ctx;
		init();
	}

	public TouchImageView getImageView() {
		return mImageView;
	}

	@SuppressWarnings("deprecation")
	protected void init() {
		setBackgroundColor(Color.parseColor("#000000"));
		mImageView = new TouchImageView(mContext);
		// imageLoader = new ImageLoaderTest(mContext);
		imageLoader = ImageLoaderNew.getInstance(mContext);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		mImageView.setLayoutParams(params);
		this.addView(mImageView);
		mImageView.setVisibility(GONE);

		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();

		mProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
		params = new LayoutParams(width / 2, 10);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		params.setMargins(30, 0, 30, 0);
		mProgressBar.setLayoutParams(params);
		mProgressBar.setIndeterminate(false);
		mProgressBar.setMax(100);
		mProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.upload_progressbar_bg));
		this.addView(mProgressBar);
	}

	public void setUrl(String imageUrl, int position) {
		imageLoader.displayBigImg(imageUrl, mImageView, R.drawable.ic_launcher, mProgressBar);
		// new ImageLoadTask().execute(imageUrl);
	}

	// No caching load
	public class ImageLoadTask extends AsyncTask<String, Integer, Bitmap> {
		@Override
		protected Bitmap doInBackground(String... strings) {
			String url = strings[0];
			Bitmap bitmap = imageLoader.showCacheBitmap(url);
			if (bitmap == null) {
				// bitmap = imageLoader.getBitmapFromUrl(url);
				// System.out.println("网络加载：" + url);
				try {
					URL aURL = new URL(url);
					URLConnection conn = aURL.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					int totalLen = conn.getContentLength();
					InputStreamWrapper bis = new InputStreamWrapper(is, 8192, totalLen);
					bis.setProgressListener(new InputStreamProgressListener() {
						@Override
						public void onProgress(float progressValue, long bytesLoaded, long bytesTotal) {
							publishProgress((int) (progressValue * 100));
						}
					});

					BitmapFactory.Options o2 = new BitmapFactory.Options();
					o2.inDither = false;
					o2.inPreferredConfig = null;
					o2.inPurgeable = true;
					o2.inInputShareable = true;
					int scale = 1;
					scale = computeSampleSize(o2, -1, 480 * 800);

					if (Build.MODEL.indexOf("DOOV") != -1 || Build.MODEL.indexOf("K-Touch") != -1 || Build.MODEL.indexOf("vivo") != -1) {
						scale = scale + 1;
					}
					o2.inSampleSize = scale;

					bitmap = BitmapFactory.decodeStream(bis, null, o2);
					bis.close();
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (bitmap == null) {
				mImageView.setScaleType(ScaleType.CENTER);
				bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
				mImageView.setImageBitmap(bitmap);
				try {
					if (bitmap != null && !bitmap.isRecycled()) {
						bitmap = null;
					}
				} catch (Exception e) {
				}
			} else {
				mImageView.setScaleType(ScaleType.MATRIX);
				mImageView.setImageBitmap(bitmap);
				try {
					if (bitmap != null && !bitmap.isRecycled()) {
						bitmap = null;
					}
				} catch (Exception e) {
				}
			}

			mImageView.setVisibility(VISIBLE);
			mProgressBar.setVisibility(GONE);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			mProgressBar.setProgress(values[0]);
		}
	}

	private int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
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

	private int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
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
}
