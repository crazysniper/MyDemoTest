package com.gftest.myappclient.new_imagepicker;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.gftest.myappclient.R;

/**
 * http://www.cnblogs.com/frydsh/p/3557179.html<br>
 * http://www.sjsjw.com/kf_mobile/article/12_27128_18388.asp<br>
 * http://blog.csdn.net/geeklei/article/details/39684495<br>
 * 
 * @author Gao
 * 
 */
public class ImageDirPopupWindow extends PopupWindow {

	private View view;
	private ListView listView;
	private Context context;
	private LayoutInflater layoutInflater;
	private List<ImageFolder> imageFolderList = new ArrayList<ImageFolder>();

	private ImageDirPopupWindowAdapter adapter;
	private ImageDirPopupWindowListener listener;

	public ImageDirPopupWindow() {
		super();
	}

	public ImageDirPopupWindow(Context context) {
		super(context);
	}

	public ImageDirPopupWindow(Context context, List<ImageFolder> imageFolderList) {
		super(context);
		this.context = context;
		this.imageFolderList = imageFolderList;
		initView();
	}

	private void initView() {
		layoutInflater = LayoutInflater.from(this.context);
		view = layoutInflater.inflate(R.layout.new_imagepicker_pop_list_dir, null);

		setContentView(view);
		setAnimationStyle(R.style.anim_popup_dir);
		int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.7);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(height);
		setOutsideTouchable(true);// 设置允许在外点击消失
		setTouchable(true);
		setFocusable(true);

		// 如果需要PopupWindow响应返回键，那么必须给PopupWindow设置一个背景才行，下面是通常的做法：
		ColorDrawable colorDrawable = new ColorDrawable(0xb0000000);
		setBackgroundDrawable(colorDrawable);// 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景

		// 软键盘不会挡着popupwindow
		setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

		setTouchInterceptor(new OnTouchListener() {// 监听触屏事件
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					dismiss();
					return true;
				}
				return false;
			}
		});

		listView = (ListView) view.findViewById(R.id.listView);

		adapter = new ImageDirPopupWindowAdapter(this.context, this.imageFolderList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (listener != null) {
					listener.onItemClick(parent, view, position, id);
				}
				dismiss();
			}
		});

		setOnDismissListener(new OnDismissListener() {// 监听菜单的关闭事件。popupwindow消失之后，背景恢复
			@Override
			public void onDismiss() {// 设置背景颜色变暗
				WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
				lp.alpha = 1.0f;
				((Activity) context).getWindow().setAttributes(lp);
			}
		});
	}

	public interface ImageDirPopupWindowListener {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id);
	}

	public void setImageDirPopupWindowListener(ImageDirPopupWindowListener listener) {
		this.listener = listener;
	}
}
