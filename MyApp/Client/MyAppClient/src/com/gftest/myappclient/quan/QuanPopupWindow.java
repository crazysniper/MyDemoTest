package com.gftest.myappclient.quan;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.gftest.myappclient.R;
import com.gftest.myappclient.quan.DragGridView.CheckGridViewData;

public class QuanPopupWindow extends PopupWindow implements CheckGridViewData {

	private Context context;
	private LayoutInflater layoutInflater;
	private View view, anchor;
	private ImageView up;
	private DragGridView quanGridView;
	private TextView finish;

	private DragAdapter adapter;
	private Animation animation;

	private List<QuanEntity> quanList1 = new ArrayList<QuanEntity>();
	private List<QuanEntity> quanList2 = new ArrayList<QuanEntity>();
	private int height;

	private PopListener popListener;
	private boolean hasChecked = false;

	public QuanPopupWindow() {
		super();
	}

	public QuanPopupWindow(Context context) {
		super(context);
		this.context = context;
		initView();
	}

	public QuanPopupWindow(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public QuanPopupWindow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public QuanPopupWindow(Context context, List<QuanEntity> quanList, View anchor, int height) {
		this.context = context;
		this.quanList1 = quanList;
		this.quanList2 = quanList;
		this.anchor = anchor;
		this.height = height;
		initView();
	}

	public void setPopListener(PopListener popListener) {
		this.popListener = popListener;
	}

	private void initView() {
		layoutInflater = LayoutInflater.from(context);
		view = layoutInflater.inflate(R.layout.fragment_quan_pop, null);
		animation = AnimationUtils.loadAnimation(context, R.anim.quan_pop_out);

		up = (ImageView) view.findViewById(R.id.up);
		finish = (TextView) view.findViewById(R.id.finish);
		quanGridView = (DragGridView) view.findViewById(R.id.quanGridView);

		System.out.println("------------------------");
		System.out.println("打开后");
		for (QuanEntity quanEntity : quanList1) {
			System.out.println(quanEntity.getName());
		}

		adapter = new DragAdapter(context, quanList1);
		quanGridView.setAdapter(adapter);

		quanGridView.setCheckGridViewData(this);

		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(height);
		setContentView(view);
		setAnimationStyle(R.style.quan_pop_anim);

		setOutsideTouchable(true);
		setFocusable(true);
		setTouchable(true);

		ColorDrawable colorDrawable = new ColorDrawable(Color.WHITE);
		setBackgroundDrawable(colorDrawable);

		showAsDropDown(anchor);

		setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				System.out.println("监听pop的dismiss___hasChecked=" + hasChecked);
			}
		});

		up.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				view.startAnimation(animation);
				dismiss();
			}
		});

		finish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "完成", Toast.LENGTH_SHORT).show();
				view.startAnimation(animation);
				dismiss();
				popListener.setChangedQuan();
			}
		});

	}

	public interface PopListener {
		/** 监听到圈子顺序改变 */
		public void setChangedQuan();
	}

	@Override
	public void setChecked(Boolean flag) {
		hasChecked = flag;
		if (flag) {
			up.setVisibility(View.GONE);
			finish.setVisibility(View.VISIBLE);
		} else {
			up.setVisibility(View.VISIBLE);
			finish.setVisibility(View.GONE);
		}
	}

}
