package com.gftest.myappclient.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gftest.myappclient.BaseFragment;

/**
 * Fragment生命周期详解
 * 
 * @author Gao
 * 
 */
public class Frament_Life extends BaseFragment {

	/**
	 * Fragment没有onRestart()这个方法
	 */

	/**
	 * 当一个Fragment对象关联到一个Activity时调用。
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		System.out.println("Frament_Life onAttach");
	}

	/**
	 * 初始创建Fragment对象时调用。
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("Frament_Life onCreate");
	}

	/**
	 * 创建与Fragment对象关联的View视图时调用。
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		System.out.println("Frament_Life onActivityCreated");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/**
	 * 当Activity对象完成自己的onCreate方法时调用。
	 * 当activity的onCreated()方法返回后调用此方法
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		System.out.println("Frament_Life onActivityCreated");
	}

	/**
	 * Fragment对象在ui可见时调用。
	 */
	@Override
	public void onStart() {
		super.onStart();
		System.out.println("Frament_Life onStart");
	}

	/**
	 * Fragment对象的ui可以与用户交互时调用。
	 */
	@Override
	public void onResume() {
		super.onResume();
		System.out.println("Frament_Life onResume");
	}

	/**
	 * 用户将要离开fragment时,系统调用这个方法作为第一个指示(然而它不总是意味着fragment将被销毁.)
	 * 在当前用户会话结束之前,通常应当在这里提交任何应该持久化的变化(因为用户有可能不会返回).
	 * 
	 * Fragment对象可见，但不可交互。有Activity对象转为onPause状态时调用。
	 */
	@Override
	public void onPause() {
		super.onPause();
		System.out.println("Frament_Life onPause");
	}

	/**
	 * 有空间完全遮挡；或者宿主Activity对象转为onStop状态时调用。
	 */
	@Override
	public void onStop() {
		super.onStop();
		System.out.println("Frament_Life onStop");
	}

	/**
	 * Fragment对象清理view资源时调用，也就是移除fragment中的视图。
	 *  当fragment中的视图被移除的时候，调用这个方法。
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		System.out.println("Frament_Life onDestroyView");
	}

	/**
	 * Fragment对象完成对象清理View资源时调用。
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println("Frament_Life onDestroy");
	}

	/**
	 * Fragment对象没有与Activity对象关联时调用。
	 * 当fragment和activity分离的时候，调用这个方法。
	 */
	@Override
	public void onDetach() {
		super.onDetach();
		System.out.println("Frament_Life onDetach");
	}
}
