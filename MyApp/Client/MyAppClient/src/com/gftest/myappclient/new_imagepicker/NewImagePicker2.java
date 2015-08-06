package com.gftest.myappclient.new_imagepicker;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gftest.myappclient.BaseActivity;
import com.gftest.myappclient.R;
import com.gftest.myappclient.new_imagepicker.ImageDirPopupWindow.ImageDirPopupWindowListener;
import com.gftest.myappclient.utils.ToastUtils;
import com.gftest.myappclient.utils.Utils;

@SuppressLint("DefaultLocale")
public class NewImagePicker2 extends BaseActivity implements OnClickListener, ImageDirPopupWindowListener {

	private GridView gridView;
	private RelativeLayout bottom;
	private TextView dirName, count;

	private ProgressDialog progressDialog;
	private ToastUtils toastUtils;
	 private MyAdapter adapter;
//	private MyAdapter2 adapter2;

	/** 临时的辅助类，用于防止同一个文件夹的多次扫描 */
	private HashSet<String> dirPathHashSet = new HashSet<String>();
	/** 扫描拿到所有的图片文件夹 */
	private List<ImageFolder> imageFolderList = new ArrayList<ImageFolder>();
	/** 包含最多图片的文件夹下的所有的图片路径 */
	private List<String> imagePathList = new ArrayList<String>();

	private int totalCount = 0;
	/** 存储文件夹中的图片数量 */
	private int mPicsSize;
	/** 图片数量最多的文件夹 */
	private File mImgDir;
	private String dirPath;

	private ImageDirPopupWindow imageDirPopupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_imagepicker);
		initView();
	}

	private void initView() {
		gridView = (GridView) findViewById(R.id.gridView);
		bottom = (RelativeLayout) findViewById(R.id.bottom);
		dirName = (TextView) findViewById(R.id.dirName);
		count = (TextView) findViewById(R.id.count);

		toastUtils = ToastUtils.getInstance();

		getImages();

		bottom.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bottom:
			// 相对某个控件的位置，有偏移，xoff 为 X 轴的偏移量，yoff 为 Y 轴的偏移量
			imageDirPopupWindow.showAsDropDown(bottom, 0, 0);
			// 设置背景颜色变暗
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.alpha = .3f;
			getWindow().setAttributes(lp);
			break;
		}
	}

	private void getImages() {
		if (!Utils.isSdExists()) {
			toastUtils.showToast(NewImagePicker2.this, "sd卡不存在", Toast.LENGTH_SHORT);
			return;
		}
		progressDialog = ProgressDialog.show(NewImagePicker2.this, null, "正在加载");
		new Thread(new Runnable() {
			@Override
			public void run() {
				String firstImage = null;
				Uri imageUri = Media.EXTERNAL_CONTENT_URI;
				ContentResolver contentResolver = getContentResolver();
				// 只查询jpeg和png的图片
				Cursor cursor = contentResolver.query(imageUri, null, Media.MIME_TYPE + "=? or " + Media.MIME_TYPE + "=?", new String[] { "image/jpeg", "image/png" }, Media.DATE_MODIFIED);
				if (cursor.moveToFirst()) {
					do {
						// 获取图片的路径
						String path = cursor.getString(cursor.getColumnIndex(Media.DATA));// 文件路径
						String id = cursor.getString(cursor.getColumnIndex(Media.BUCKET_ID));// 文件夹id
						String parentName = cursor.getString(cursor.getColumnIndex(Media.BUCKET_DISPLAY_NAME));// 文件夹名

						if (firstImage == null) {// 拿到第一张图片的路径
							firstImage = path;
						}
						File parentFile = new File(path).getParentFile(); // 获取该图片的父路径名
						if (parentFile == null) {
							continue;
						}
						String dirPath = parentFile.getAbsolutePath();
						ImageFolder imageFolder = null;
						// 利用一个HashSet防止多次扫描同一个文件夹
						if (dirPathHashSet.contains(id)) {
							continue;
						} else {
							dirPathHashSet.add(id);
							imageFolder = new ImageFolder();
							imageFolder.setDirPath(dirPath);
							imageFolder.setDirName(parentName);
							imageFolder.setFirstImgPath(path);
						}
						if (parentFile.list() == null) {
							continue;
						}
						int picSize = parentFile.list(new FilenameFilter() {
							@Override
							public boolean accept(File dir, String filename) {
								if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".png") || filename.toLowerCase().endsWith(".jpeg")) {
									return true;
								}
								return false;
							}
						}).length;
						totalCount += picSize;

						imageFolder.setCount(picSize);
						imageFolderList.add(imageFolder);

						if (picSize > mPicsSize) {// 选出包含图片最大的文件夹
							mPicsSize = picSize;
							mImgDir = parentFile;
						}
					} while (cursor.moveToNext());
				}
				if (cursor != null) {
					cursor.close();
				}
				dirPathHashSet = null;// 扫描完成，辅助的HashSet也就可以释放内存了
				handler.sendEmptyMessage(1);// 通知Handler扫描图片完成
			}
		}).start();
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				progressDialog.dismiss();
				data2View(); // 初始化展示文件夹的popupWindw
				initListDirPopupWindw();
				break;
			default:
				break;
			}
		}
	};

	private void data2View() {
		if (mImgDir == null) {
			toastUtils.showToast(NewImagePicker2.this, "没有扫描到", Toast.LENGTH_SHORT);
			return;
		}
		imagePathList = Arrays.asList(mImgDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".png") || filename.toLowerCase().endsWith(".jpeg")) {
					return true;
				}
				return false;
			}
		}));
		System.out.println("大小=" + imagePathList.size());
		dirPath = mImgDir.getAbsolutePath();
		System.out.println("dirPath=" + dirPath);
		adapter = new MyAdapter(NewImagePicker2.this, imagePathList, R.layout.grid_item, dirPath);
		gridView.setAdapter(adapter);
//		adapter2 = new MyAdapter2(NewImagePicker2.this, imagePathList, dirPath);
//		gridView.setAdapter(adapter2);
		
		count.setText(totalCount + "张");
	}

	protected void initListDirPopupWindw() {
		imageDirPopupWindow = new ImageDirPopupWindow(NewImagePicker2.this, imageFolderList);
		imageDirPopupWindow.setImageDirPopupWindowListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		System.out.println("选择下标=" + position);
		ImageFolder imageFolder = imageFolderList.get(position);

		mImgDir = new File(imageFolder.getDirPath());
		imagePathList = Arrays.asList(mImgDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".png") || filename.toLowerCase().endsWith(".jpeg")) {
					return true;
				}
				return false;
			}
		}));

		dirName.setText(imageFolder.getDirName());
		count.setText(imageFolder.getCount() + "张");

		adapter = new MyAdapter(NewImagePicker2.this, imagePathList, R.layout.grid_item, mImgDir.getAbsolutePath());
		gridView.setAdapter(adapter);
		
//		adapter2 = new MyAdapter2(NewImagePicker2.this, imagePathList, mImgDir.getAbsolutePath());
//		gridView.setAdapter(adapter2);
	}
}
