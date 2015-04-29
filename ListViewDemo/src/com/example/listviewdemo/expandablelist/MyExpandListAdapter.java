package com.example.listviewdemo.expandablelist;

import java.util.ArrayList;
import java.util.List;

import com.example.listviewdemo.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyExpandListAdapter extends BaseExpandableListAdapter {

	private List<String> groupList = new ArrayList<String>();
	private ArrayList<ArrayList<String>> childList = new ArrayList<ArrayList<String>>();
	private int selectedGroupPosition = -1;
	private int selectedChildPosition = -1;
	private Context context;
	private LayoutInflater layoutInflater;

	public MyExpandListAdapter(Context context, List<String> groupList, ArrayList<ArrayList<String>> childList) {
		this.context = context;
		this.groupList = groupList;
		this.childList = childList;
		layoutInflater = LayoutInflater.from(this.context);
	}

	public void setSelectedPosition(int selectedGroupPosition, int selectedChildPosition) {
		this.selectedGroupPosition = selectedGroupPosition;
		this.selectedChildPosition = selectedChildPosition;
	}

	/**
	 * 获取组的个数
	 */
	@Override
	public int getGroupCount() {
		return groupList.size();
	}

	/**
	 * 获取指定组中的数据
	 */
	@Override
	public Object getGroup(int groupPosition) {
		return groupList.get(groupPosition);
	}

	/**
	 * 获取指定组的ID，这个组ID必须是唯一的
	 */
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/**
	 * 获取指定组中的子元素个数
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		return childList.get(groupPosition).size();
	}

	/**
	 * 获取指定组中的指定子元素数据。
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childList.get(groupPosition).get(childPosition);
	}

	/**
	 * 获取指定组中的指定子元素ID，这个ID在组里一定是唯一的
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	/**
	 * 组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们。
	 * 
	 * @return 如果为TRUE，意味着相同的ID永远引用相同的对象。
	 */
	@Override
	public boolean hasStableIds() {
		return true;
	}

	/**
	 * 获取显示指定组的视图对象。这个方法仅返回关于组的视图对象，要想获取子元素的视图对象，就需要调用getChildView(int, int,
	 * boolean, View, ViewGroup)。
	 * 
	 * @param groupPosition
	 *            组位置（决定返回哪个视图）
	 * @param isExpanded
	 *            该组是展开状态还是伸缩状态
	 * @param convertView
	 *            重用已有的视图对象。注意：在使用前你应该检查一下这个视图对象是否非空并且这个对象的类型是否合适。由此引伸出，
	 *            如果该对象不能被转换并显示正确的数据，这个方法就会调用getGroupView(int, boolean, View,
	 *            ViewGroup)来创建一个视图(View)对象。
	 * @param parent
	 *            返回的视图对象始终依附于的视图组。
	 * 
	 * @return 返回指定组的视图对象
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		System.out.println("getGroupView groupPosition=" + groupPosition + "__isExpanded=" + isExpanded);
		GroupViewHolder groupViewHolder = null;
		if (convertView == null) {
			groupViewHolder = new GroupViewHolder();
			convertView = layoutInflater.inflate(R.layout.expandablelistview_groups, null);
			groupViewHolder.textGroup = (TextView) convertView.findViewById(R.id.textGroup);
			groupViewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
			convertView.setTag(groupViewHolder);
		} else {
			groupViewHolder = (GroupViewHolder) convertView.getTag();
		}
		groupViewHolder.textGroup.setText(getGroup(groupPosition).toString());
		if (isExpanded) {
			groupViewHolder.imageView.setBackgroundResource(R.drawable.arraw_down);
		} else {
			groupViewHolder.imageView.setBackgroundResource(R.drawable.arraw_up);
		}
		return convertView;
	}

	/**
	 * 获取一个视图对象，显示指定组中的指定子元素数据。
	 * 
	 * @param groupPosition
	 *            位置（该组内部含有子元素）
	 * @param childPosition
	 *            子元素位置（决定返回哪个视图）
	 * @param isLastChild
	 *            子元素是否处于组中的最后一个
	 * @param convertView
	 *            重用已有的视图(View)对象。注意：在使用前你应该检查一下这个视图对象是否非空并且这个对象的类型是否合适。
	 *            由此引伸出，如果该对象不能被转换并显示正确的数据，这个方法就会调用getChildView(int, int,
	 *            boolean, View, ViewGroup)来创建一个视图(View)对象。
	 * @param parent
	 *            返回的视图(View)对象始终依附于的视图组。
	 * 
	 * @return 指定位置上的子元素返回的视图对象
	 */
	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		System.out.println("getChildView groupPosition=" + groupPosition + "__childPosition=" + childPosition + "__isLastChild=" + isLastChild);
		ChildViewHolder childViewHolder = null;
		if (convertView == null) {
			childViewHolder = new ChildViewHolder();
			convertView = layoutInflater.inflate(R.layout.expandablelistview_child, null);
			childViewHolder.textChild = (TextView) convertView.findViewById(R.id.textChild);
			convertView.setTag(childViewHolder);
		} else {
			childViewHolder = (ChildViewHolder) convertView.getTag();
		}
		childViewHolder.textChild.setText(getChild(groupPosition, childPosition).toString());
		if (groupPosition == selectedGroupPosition) {
			if (childPosition == selectedChildPosition) {
				childViewHolder.textChild.setBackgroundColor(0xffb6ddee);
			} else {
				childViewHolder.textChild.setBackgroundColor(Color.TRANSPARENT);
			}
		}

		childViewHolder.textChild.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setSelectedPosition(groupPosition, childPosition);
				notifyDataSetChanged();
			}
		});
		return convertView;
	}

	/**
	 * 是否选中指定位置上的子元素。
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	private class GroupViewHolder {
		private TextView textGroup;
		private ImageView imageView;
	}

	private class ChildViewHolder {
		private TextView textChild;
	}
}
